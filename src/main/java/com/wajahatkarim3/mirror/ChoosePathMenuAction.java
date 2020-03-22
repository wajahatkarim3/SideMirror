package com.wajahatkarim3.mirror;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.fileEditor.OpenFileDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.io.File;

/**
 * The Menu Action in Tools menu of IntelliJ Java IDE and Android Studio show a confirmation
 * dialog to allow the user to choose the path of either scrcpy (https://github.com/Genymobile/scrcpy) or
 * Vysor (https://www.vysor.io/) EXE files. Once the path is set, then the action will directly open the file to mirror the connected
 * Android device in the computer.
 * @date 22/03/2020
 * @author Wajahat Karim (https://wajahatkarim.com)
 */
public class ChoosePathMenuAction extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
        Project project = anActionEvent.getProject();
        showChoosePathDialog(project);
    }

    private void showChoosePathDialog(Project project)
    {
        ChoosePathDialog pathDialog = new ChoosePathDialog(project, null);
        pathDialog.setTitle("Choose scrcpy EXE path");
        pathDialog.setSize(new Dimension(300, 150));
        pathDialog.setMaximumSize(new Dimension(300, 150));
        pathDialog.setPreferredSize(new Dimension(300, 150));
        pathDialog.setMinimumSize(new Dimension(300, 150));
        pathDialog.setLocationByPlatform(true);
        pathDialog.setLocationRelativeTo(null);
        pathDialog.setVisible(true);
    }
}
