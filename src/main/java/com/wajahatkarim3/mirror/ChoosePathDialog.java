package com.wajahatkarim3.mirror;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Condition;
import com.intellij.openapi.vfs.VirtualFile;

import javax.swing.*;
import java.awt.event.*;

/**
 * The dialog to allow the user to choose the path of either scrcpy (https://github.com/Genymobile/scrcpy) or
 * Vysor (https://www.vysor.io/) EXE files. Once the path is set, then the action will directly open the file to mirror the connected
 * Android device in the computer.
 * @date 22/03/2020
 * @author Wajahat Karim (https://wajahatkarim.com)
 */
public class ChoosePathDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField mirrorPath;
    private JButton browseButton;

    Project project;
    MirrorPathSelectedCallback pathSelectedCallback;

    public ChoosePathDialog(final Project project, MirrorPathSelectedCallback pathSelected) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        this.project = project;
        pathSelectedCallback = pathSelected;

        String savedMirrorPath = PropertiesComponent.getInstance().getValue(MirrorLauncher.MIRROR_PATH_KEY);
        if (savedMirrorPath != null && !savedMirrorPath.isEmpty())
        {
            mirrorPath.setText(savedMirrorPath);
        }


        browseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileChooserDescriptor descriptor = new FileChooserDescriptor(true, false, false, false, false, false);
                descriptor.setShowFileSystemRoots(true);
                descriptor.withFileFilter(new Condition<VirtualFile>() {
                    @Override
                    public boolean value(VirtualFile virtualFile) {
                        return virtualFile != null && virtualFile.getExtension() != null && virtualFile.getExtension().equalsIgnoreCase("exe");
                    }
                });

                VirtualFile virtualFile = FileChooser.chooseFile(descriptor, project, null);
                if (virtualFile != null)
                {
                    mirrorPath.setText(virtualFile.getCanonicalPath());
                }
            }
        });

        buttonOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!mirrorPath.getText().isEmpty())
                {
                    PropertiesComponent.getInstance().setValue(MirrorLauncher.MIRROR_PATH_KEY, mirrorPath.getText());
                    if (pathSelected != null) {
                        pathSelected.onPathSelected(mirrorPath.getText());
                    }
                    onCancel();
                }
                else
                {
                    JOptionPane.showMessageDialog(mirrorPath, "You must select a valid EXE file!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
        });

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        buttonCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}
