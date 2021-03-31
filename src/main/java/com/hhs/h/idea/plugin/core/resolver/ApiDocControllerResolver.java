package com.hhs.h.idea.plugin.core.resolver;

import com.intellij.openapi.vfs.VirtualFile;

/**
 * java controller类解析器
 *
 * @author hhs
 * @since 2021/3/31 14:35
 */
public class ApiDocControllerResolver extends BaseApiDocResolver {

    @Override
    protected boolean support(VirtualFile virtualFile) {
        if (virtualFile == null) {
            return false;
        }

        // 必须要Controller结尾
        return virtualFile.getName().endsWith("Controller.java");
    }

}
