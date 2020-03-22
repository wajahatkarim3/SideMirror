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
 * The Action button on the main toolbar of IntelliJ Java IDE and Android Studio to launch the mirror directly.
 * First time, it will show a confirmation dialog to allow the user to choose the path of either scrcpy (https://github.com/Genymobile/scrcpy) or
 * Vysor (https://www.vysor.io/) EXE files. Once the path is set, then the action will directly open the file to mirror the connected
 * Android device in the computer.
 * @date 22/03/2020
 * @author Wajahat Karim (https://wajahatkarim.com)
 */
public class MirrorLauncher extends AnAction {

    public static final String MIRROR_PATH_KEY = "mirrorPathKey";

    @Override
    public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
        Project project = anActionEvent.getProject();

        //PropertiesComponent.getInstance().setValue(MIRROR_PATH_KEY, null);
        if (PropertiesComponent.getInstance().isValueSet(MIRROR_PATH_KEY))
        {
            String mirrorPath = PropertiesComponent.getInstance().getValue(MIRROR_PATH_KEY);
            if (mirrorPath != null && !mirrorPath.isEmpty())
            {
                launchScrcpy(mirrorPath, project);
            }
            else
            {
                showChoosePathDialog(project);
            }
        }
        else
        {
            showChoosePathDialog(project);
        }
    }

    private void launchScrcpy(String mirrorPath, Project project)
    {
        VirtualFile file = LocalFileSystem.getInstance().findFileByIoFile(new File(mirrorPath));
        if (file == null) return;

        new OpenFileDescriptor(project, file).navigate(true);
    }

    private void showChoosePathDialog(Project project)
    {
        ChoosePathDialog pathDialog = new ChoosePathDialog(project, new MirrorPathSelectedCallback() {
            @Override
            public void onPathSelected(String path) {
                launchScrcpy(path, project);
            }
        });
        pathDialog.setTitle("Choose scrcpy / Vysor EXE path.");
        pathDialog.setSize(new Dimension(300, 150));
        pathDialog.setMaximumSize(new Dimension(300, 150));
        pathDialog.setPreferredSize(new Dimension(300, 150));
        pathDialog.setMinimumSize(new Dimension(300, 150));
        pathDialog.setLocationByPlatform(true);
        pathDialog.setLocationRelativeTo(null);
        pathDialog.setVisible(true);
    }
}
