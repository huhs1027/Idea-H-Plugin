package com.hhs.h.idea.plugin.core.resolver;

import com.google.common.collect.Lists;
import com.hhs.h.idea.plugin.module.dto.ApiClassDTO;
import com.hhs.h.idea.plugin.module.dto.ApiMethodDTO;
import com.hhs.h.idea.plugin.utils.ArrayUtil;
import com.intellij.codeInsight.AnnotationUtil;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.intellij.psi.javadoc.PsiDocComment;

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
    private String[] CONTROLLER_ANNON = new String[]{
            "org.springframework.stereotype.Controller"
            , "org.springframework.web.bind.annotation.RestController"
    };

    /**
     * controller层的路由
     */
    private String[] CONTROLLER_PATH_ANNON = new String[]{"org.springframework.web.bind.annotation.RequestMapping"};

    @Override
    protected boolean support(VirtualFile virtualFile) {
        if (virtualFile == null) {
            return false;
        }

        // 必须要Controller结尾
        return virtualFile.getName().endsWith("Controller.java");
    }

    @Override
    protected ApiClassDTO buildApiClass(PsiJavaFile psiJavaFile) {

        // 按照规范一个controller.java只会有一个class
        PsiClass[] classes = psiJavaFile.getClasses();
        if (ArrayUtil.isEmpty(classes)) {
            return null;
        }

        PsiClass psiClass = classes[0];
        // 判断是否符合注解
        PsiAnnotation annotation = AnnotationUtil.findAnnotation(psiClass, CONTROLLER_ANNON);
        if (annotation == null) {
            return null;
        }

        // 找类url
        List<String> pathUrlList = getPathUrl(psiClass);

        // 类型校验有效， 开始找method
        PsiMethod[] methods = psiClass.getMethods();

        ArrayList<ApiMethodDTO> methodDTOList = Lists.newArrayList();

        if (!ArrayUtil.isEmpty(methods)) {
            // 查方法是否符合path规则
            for (PsiMethod method : methods) {
                ApiMethodDTO apiMethodDTO = new ApiMethodDTO();
                apiMethodDTO.setQualifiedName(method.getName());

                PsiDocComment docComment = method.getDocComment();
                if (docComment != null) {
                    apiMethodDTO.setDesc(getDocment(docComment.getText()));
                }

                PsiAnnotation methodAnnon = AnnotationUtil.findAnnotation(psiClass, CONTROLLER_PATH_ANNON);
                if (methodAnnon != null) {
                    // 找路径
                    List<String> methodPathUrl = getPathUrl(method);
                    ArrayList<String> allPathList = Lists.newArrayList();

                    if (!ArrayUtil.isEmpty(methodPathUrl)) {
                        // 组装url
                        for (String mPath : methodPathUrl) {
                            for (String s : pathUrlList) {
                                allPathList.add(s + mPath);
                            }
                        }
                    }

                    apiMethodDTO.setPathUrlList(allPathList);
                }

                methodDTOList.add(apiMethodDTO);
            }
        }


        // 记录
        ApiClassDTO apiClassDTO = new ApiClassDTO();
        apiClassDTO.setQualifiedName(psiClass.getQualifiedName());
        apiClassDTO.setMethodList(methodDTOList);

        return apiClassDTO;
    }

    private String getDocment(String text) {
        ArrayList<String> objects = Lists.newArrayList();
        String[] textArray = text.split("\n");
        for (int i = 0; i < textArray.length; i++) {
            String line = textArray[i].trim();
            if (line.contains("@")) {
                break;
            }
            line = line.replaceAll("(?:\\*|\\/\\*\\*|@param|@return|\\/)", "");
            objects.add(line);
        }

        return StringUtil.join(objects, " \n ");
    }

    /**
     * 获取路径url
     *
     * @param listOwner 支持注解的元素
     * @return List<String>
     */
    private List<String> getPathUrl(PsiModifierListOwner listOwner) {
        ArrayList<String> arrayList = Lists.newArrayList();
        PsiAnnotation annotation = AnnotationUtil.findAnnotation(listOwner, CONTROLLER_PATH_ANNON);
        if (annotation == null) {
            return arrayList;
        }
        PsiAnnotationMemberValue value = annotation.findAttributeValue("value");
        if (value == null) {
            value = annotation.findAttributeValue("path");
        }

        if (value != null) {
            PsiElement[] children = value.getChildren();
            if (!ArrayUtil.isEmpty(children)) {
                for (PsiElement child : children) {
                    arrayList.add(StringUtil.replace(child.getText(), "\"", ""));
                }
            }
        }

        return arrayList;
    }

}
