package com.hhs.h.idea.plugin.extensions;

import com.intellij.openapi.vcs.CheckinProjectPanel;
import com.intellij.openapi.vcs.changes.CommitContext;
import com.intellij.openapi.vcs.checkin.CheckinHandler;
import com.intellij.openapi.vcs.checkin.CheckinHandlerFactory;
import org.jetbrains.annotations.NotNull;

/**
 * @author hhs
 * @since 2021/3/31 16:17
 */
public class CheckinHandlerFactoryApiDocHandler extends CheckinHandlerFactory {

    @Override
    public @NotNull CheckinHandler createHandler(@NotNull CheckinProjectPanel panel, @NotNull CommitContext commitContext) {
        return null;
    }
}
