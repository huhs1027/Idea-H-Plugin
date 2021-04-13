package com.hhs.h.idea.plugin.core.resolver;

import com.google.common.collect.Lists;
import com.hhs.h.idea.plugin.module.dto.ApiClassDTO;
import com.hhs.h.idea.plugin.module.dto.ApiFieldDTO;
import com.hhs.h.idea.plugin.module.dto.ApiMethodDTO;
import com.hhs.h.idea.plugin.utils.ArrayUtil;
import com.hhs.h.idea.plugin.utils.JavadocUtils;
import com.intellij.codeInsight.AnnotationUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.intellij.psi.javadoc.PsiDocComment;
import com.intellij.psi.util.PsiTreeUtil;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * java controller类解析器
 *
 * @author hhs
 * @since 2021/3/31 14:35
 */
public class ApiDocControllerResolver extends BaseApiDocResolver {

    /**
     * controller层的注解
     */
    private List<String> CONTROLLER_ANNON_LIST =
            Lists.newArrayList("org.springframework.stereotype.Controller"
                    , "org.springframework.web.bind.annotation.RestController"
            );

    /**
     * controller层的路由
     */
    private List<String> CONTROLLER_PATH_ANNON_LIST = Lists.newArrayList(
            "org.springframework.web.bind.annotation.RequestMapping"
    );

    /**
     * 忽略的参数列表
     */
    private List<String> IGNORE_PARAM_LIST = Lists.newArrayList(
            "javax.servlet.http.HttpServletRequest",
            "javax.servlet.http.HttpServletResponse"
    );

    @Override
    protected boolean support(VirtualFile virtualFile) {
        if (virtualFile == null) {
            return false;
        }

        // 测试的不要
        if (virtualFile.getPath().contains("test/java")) {
            return false;
        }

        // 必须要Controller结尾
        return virtualFile.getName().endsWith("Controller.java");
    }

    @Override
    protected ApiClassDTO buildApiClass(PsiJavaFile psiJavaFile) {

        // 获取符合的类
        PsiClass psiClass = this.getSupportJavaClass(psiJavaFile);
        if (psiClass == null) {
            return null;
        }

        // 类数据
        ApiClassDTO apiClassDTO = new ApiClassDTO();
        apiClassDTO.setName(psiClass.getQualifiedName());

        // 描述
        apiClassDTO.setTitle(this.getTitle(psiClass.getDocComment()));
        apiClassDTO.setDocDesc(this.getDesc(apiClassDTO.getTitle(), psiClass.getDocComment()));

        // 接口方法
        apiClassDTO.setMethodList(this.getSupportJavaMethod(psiClass));

        return apiClassDTO;
    }

    /**
     * 获取支持的java方法
     *
     * @param psiClass java类
     * @return List<ApiMethodDTO>
     */
    private List<ApiMethodDTO> getSupportJavaMethod(PsiClass psiClass) {

        ArrayList<ApiMethodDTO> methodDTOArrayList = Lists.newArrayList();

        for (PsiMethod psiMethod : psiClass.getMethods()) {
            if (!this.isSupportMethod(psiMethod)) {
                continue;
            }

            // 组装
            ApiMethodDTO apiMethodDTO = new ApiMethodDTO();
            apiMethodDTO.setName(psiMethod.getName());
            apiMethodDTO.setTitle(this.getTitle(psiMethod.getDocComment()));
            apiMethodDTO.setDesc(this.getDesc(apiMethodDTO.getTitle(), psiMethod.getDocComment()));

            // 入参出参
            apiMethodDTO.setParamList(this.getParamList(psiMethod));
            apiMethodDTO.setReturnList(null);

            // 需要通过解析注解
            apiMethodDTO.setMethod(null);
            apiMethodDTO.setPathUrlList(null);
            apiMethodDTO.setContentType(null);


            methodDTOArrayList.add(apiMethodDTO);
        }
        return methodDTOArrayList;
    }

    /**
     * 通过psiMethod获取参数列表
     *
     * @param psiMethod 方法描述
     * @return List<ApiParamDTO>
     */
    private List<ApiFieldDTO> getParamList(PsiMethod psiMethod) {
        // 获取参数列表
        PsiParameterList parameterList = psiMethod.getParameterList();
        if (ArrayUtil.isEmpty(parameterList)) {
            return Lists.newArrayList();
        }

        // 获取入参类型
        ArrayList<ApiFieldDTO> apiFieldDTOList = Lists.newArrayList();

        for (PsiParameter psiParameter : parameterList.getParameters()) {
            PsiType type = psiParameter.getType();
            // 过滤无需展示的类型
            if (this.isIgnoreParam(type)) {
                continue;
            }

            // 解析参数，组装参数DTO
            String paramName = psiParameter.getName();
            String paramDesc = JavadocUtils.findTagDesc(psiMethod.getDocComment(), "param", paramName);

            // 组装
            apiFieldDTOList.add(buildApiField(type, paramName, paramDesc));
        }

        return apiFieldDTOList;

    }

    private ApiFieldDTO buildApiField(PsiType type, String name, String desc) {
        ApiFieldDTO apiField = new ApiFieldDTO();
        apiField.setDesc(desc);
        apiField.setName(name);
        apiField.setType(type.getPresentableText());
        //apiField.setChildren(extractFieldList(type, 0));

        return apiField;
    }


    /**
     * 获取支持的java类
     *
     * @param psiJavaFile java文件
     * @return java类
     */
    private PsiClass getSupportJavaClass(PsiJavaFile psiJavaFile) {
        List<PsiClass> classElements = PsiTreeUtil.getChildrenOfTypeAsList(psiJavaFile, PsiClass.class);
        for (PsiClass classElement : classElements) {
            if (this.isSupportClass(classElement)) {
                return classElement;
            }
        }
        return null;
    }

    /**
     * 判断是否支持的class
     *
     * @param psiClass class描述
     * @return true 支持 false不支持
     */
    private boolean isSupportClass(PsiClass psiClass) {
        if (psiClass == null) {
            return false;
        }

        // 判断是否有@Controller注解
        return AnnotationUtil.isAnnotated(psiClass, CONTROLLER_ANNON_LIST);
    }

    /**
     * 判断是否支持的method
     *
     * @param psiMethod method描述
     * @return true支持，false不支持
     */
    private boolean isSupportMethod(PsiMethod psiMethod) {
        return AnnotationUtil.isAnnotated(psiMethod, CONTROLLER_PATH_ANNON_LIST);
    }

    /**
     * 判断是否需要过滤的参数类型
     *
     * @param type 参数类型
     * @return true过滤 false不过滤
     */
    private boolean isIgnoreParam(PsiType type) {
        return IGNORE_PARAM_LIST.contains(type.getCanonicalText());
    }

    /**
     * 获取标题，取注释第一行
     *
     * @param docComment 注释doc
     * @return 标题
     */
    private String getTitle(PsiDocComment docComment) {
        if (docComment == null) {
            return "";
        }

        List<String> docDescList = JavadocUtils.findDocDescription(docComment);

        for (String docLine : docDescList) {
            String desc = docLine.trim();
            if (StringUtils.isNotBlank(desc)) {
                return desc.trim();
            }
        }

        return "";
    }

    /**
     * 获取描述，去掉第一行
     *
     * @param docComment 获取描述
     * @return 描述
     */
    private String getDesc(String title, PsiDocComment docComment) {
        if (docComment == null) {
            return "";
        }

        if (title == null) {
            title = "";
        }

        List<String> docDescList = JavadocUtils.findDocDescription(docComment);

        List<String> docResult = new ArrayList<>();
        for (String docLine : docDescList) {
            String desc = docLine.trim();
            if (StringUtils.isNotBlank(desc)) {
                String trim = desc.trim();
                // 标题 过滤掉
                if (!title.equals(trim)) {
                    docResult.add(trim);
                }
            }
        }

        return String.join("\n", docResult);
    }

}
