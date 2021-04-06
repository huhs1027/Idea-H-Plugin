package com.hhs.h.idea.plugin.core.processor;

import com.hhs.h.idea.plugin.utils.ArrayUtil;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.vfs.VirtualFile;

import java.util.List;

/**
 * @author hhs
 * @since 2021/3/31 13:48
 */
public abstract class AbsProcessor {

    /**
     * 递归选择文件类型
     *
     * @param filterFileList   结果文件列表
     * @param virtualFileArray 表示文件(目录)列表
     * @param fileType         文件类型
     */
    protected void recursionSelectFileTypeAndCache(List<VirtualFile> filterFileList, VirtualFile[] virtualFileArray, FileType fileType) {

        if (filterFileList == null || ArrayUtil.isEmpty(virtualFileArray) || fileType == null) {
            return;
        }

        for (VirtualFile virtualFile : virtualFileArray) {
            // 目录的话就递归下去
            if (virtualFile.isDirectory()) {
                this.recursionSelectFileTypeAndCache(filterFileList, virtualFile.getChildren(), fileType);
            } else {
                if (virtualFile.getFileType().equals(fileType)) {
                    // 缓存
                    filterFileList.add(virtualFile);
                }
            }
        }
    }

}
