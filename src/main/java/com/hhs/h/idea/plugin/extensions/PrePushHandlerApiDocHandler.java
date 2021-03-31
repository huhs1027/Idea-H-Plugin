package com.hhs.h.idea.plugin.extensions;

import com.intellij.dvcs.push.PrePushHandler;
import com.intellij.dvcs.push.PushInfo;
import com.intellij.openapi.progress.ProgressIndicator;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author hhs
 * @since 2021/3/31 16:17
 */
public class PrePushHandlerApiDocHandler implements PrePushHandler {

    @Override
    public @NotNull @Nls(capitalization = Nls.Capitalization.Title) String getPresentableName() {
        return null;
    }

    @Override
    public @NotNull Result handle(@NotNull List<PushInfo> pushDetails, @NotNull ProgressIndicator indicator) {
        return null;
    }
}
