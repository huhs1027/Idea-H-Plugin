package com.hhs.h.idea.plugin.action;

import com.hhs.h.idea.plugin.core.processor.ApiDocProcessor;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;

/**
 * api事件
 *
 * @author hhs
 * @since 2021/3/30 15:32
 */
public class ApiDocsAction extends AnAction {

    private static final Logger log = Logger.getInstance(ApiDocsAction.class);

    /**
     * api生成
     *
     * @param e 事件
     */
    @Override
    public void actionPerformed(AnActionEvent e) {
        // 获取项目信息
        Project project = e.getData(PlatformDataKeys.PROJECT);

        // 获取虚拟文件信息
        VirtualFile[] virtualFiles = e.getData(CommonDataKeys.VIRTUAL_FILE_ARRAY);

        ApplicationManager.getApplication().executeOnPooledThread(() -> {

            // 确保没有write bio再执行
            ApplicationManager.getApplication().runReadAction(() -> {
                // 处理事件
                new ApiDocProcessor().process(project, virtualFiles);
            });

            // 执行完了之后打印日志
            ApplicationManager.getApplication().invokeLater(() -> Messages.showMessageDialog(project, "这是消息体", "这是标题", Messages.getInformationIcon()));
        });

    }
}
