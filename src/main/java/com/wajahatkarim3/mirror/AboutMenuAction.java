package com.wajahatkarim3.mirror;

import com.intellij.ide.BrowserUtil;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;

/**
 * The Menu Action in Tools menu of IntelliJ Java IDE and Android Studio to show the Side Mirror resository (https://github.com/wajahatkarim3/SideMirror)
 * @date 22/03/2020
 * @author Wajahat Karim (https://wajahatkarim.com)
 */
public class AboutMenuAction extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        BrowserUtil.browse("https://github.com/wajahatkarim3/SideMirror");
    }
}
