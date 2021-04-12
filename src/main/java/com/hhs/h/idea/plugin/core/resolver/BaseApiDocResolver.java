package com.hhs.h.idea.plugin.core.resolver;

import com.hhs.h.idea.plugin.module.dto.ApiClassDTO;
import com.hhs.h.idea.plugin.module.dto.ApiDocDTO;
import com.hhs.h.idea.plugin.utils.ArrayUtil;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.util.PsiTreeUtil;
import git4idea.branch.GitBranchUtil;
import git4idea.repo.GitRepository;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author hhs
 * @since 2021/3/31 14:39
 */
public abstract class BaseApiDocResolver {

    private static final Logger log = Logger.getInstance(BaseApiDocResolver.class);

    /**
     * 解析文件->ApiDocDTO
     *
     * @param project      表示当前项目
     * @param virtualFiles 筛选出来的,选取范围内的,所有java文件
     * @return ApiDocDTO 不符合返回null
     */
    public ApiDocDTO resolver(Project project, List<VirtualFile> virtualFiles) {
        if (ArrayUtil.isEmpty(virtualFiles)) {
            return null;
        }

        // 筛选
        List<VirtualFile> filterFileList = virtualFiles.stream()
                .filter(this::support)
                .collect(Collectors.toList());

        if (ArrayUtil.isEmpty(filterFileList)) {
            return null;
        }

        return this.buildApiDoc(project, filterFileList);
    }

    /**
     * 创建apiDoc
     *
     * @param project      当前项目
     * @param virtualFiles 虚拟文件列表
     * @return ApiDocDTO
     */
    private ApiDocDTO buildApiDoc(Project project, List<VirtualFile> virtualFiles) {
        List<ApiClassDTO> apiClassDTOS = virtualFiles.stream().map(virtualFile -> {
            PsiJavaFile psiFile = this.toPsiFile(project, virtualFile);
            // 转换java类描述
            return this.buildApiClass(psiFile);
        })
                // 过滤null
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        if (ArrayUtil.isEmpty(apiClassDTOS)) {
            return null;
        }

        ApiDocDTO apiDocDTO = new ApiDocDTO();
        apiDocDTO.setApiClassDTOList(apiClassDTOS);
        // 拿到git分支名称
        GitRepository currentRepository = GitBranchUtil.getCurrentRepository(project);
        if (currentRepository != null) {
            apiDocDTO.setGitBranchName(currentRepository.getCurrentBranchName());
        }
        return apiDocDTO;
    }


    /**
     * 转类型描述
     *
     * @param psiClass psiClass
     * @return ApiClassDTO
     */

    /**
     * 虚拟文件转 psi文件
     *
     * @param project     当前项目
     * @param virtualFile 虚拟文件
     * @return PsiFile
     */
    protected PsiJavaFile toPsiFile(Project project, VirtualFile virtualFile) {
        return (PsiJavaFile) PsiManager.getInstance(project).findFile(virtualFile);
    }

    /**
     * 判断是否支持的解析类型, 子类实现
     *
     * @param virtualFile java虚拟文件
     * @return true符合 false不符合
     */
    protected abstract boolean support(VirtualFile virtualFile);

    /**
     * 虚拟文件转java类描述，子类实现
     *
     * @param psiJavaFile psi java文件
     * @return ApiClassDTO
     */
    protected abstract ApiClassDTO buildApiClass(PsiJavaFile psiJavaFile);

}
