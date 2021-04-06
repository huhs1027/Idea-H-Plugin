package com.hhs.h.idea.plugin.core.processor;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.hhs.h.idea.plugin.core.resolver.ApiDocControllerResolver;
import com.hhs.h.idea.plugin.core.resolver.BaseApiDocResolver;
import com.hhs.h.idea.plugin.module.dto.ApiDocDTO;
import com.intellij.ide.highlighter.JavaFileType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.apache.commons.lang.ArrayUtils;

import java.util.List;

/**
 * @author hhs
 * @since 2021/3/31 10:44
 */
public class ApiDocProcessor extends AbsProcessor {

    private List<VirtualFile> virtualFileList;

    public ApiDocProcessor() {
        init();
    }

    /**
     * 初始化方法
     */
    private void init() {
        virtualFileList = Lists.newArrayListWithCapacity(1024);
    }

    /**
     * 执行处理逻辑
     *
     * @param project 项目
     * @param virtualFiles 虚拟文件(目录)
     */
    public void process(Project project, VirtualFile[] virtualFiles) {

        if (project == null || ArrayUtils.isEmpty(virtualFiles)) {
            return;
        }

        // 递归筛选文件
        super.recursionSelectFileTypeAndCache(virtualFileList, virtualFiles, JavaFileType.INSTANCE);

        // 目前只支持restful api,先写死,后面需要可以做成可配置的
        BaseApiDocResolver apiDocResolver = new ApiDocControllerResolver();

        // 执行核心逻辑, 转换DTO
        ApiDocDTO apiDocDTO = apiDocResolver.resolver(project, virtualFileList);

        if (apiDocDTO != null) {

            // todo 目前就先打印下,后面可以做成导出或者调接口存储之类的
            System.out.println(new Gson().toJson(apiDocDTO));
        }

    }

}
