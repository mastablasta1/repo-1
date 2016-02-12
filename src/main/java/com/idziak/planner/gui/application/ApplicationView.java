package com.idziak.planner.gui.application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ApplicationView {

    private ApplicationController applicationController;
    private JFrame mainFrame;

    public ApplicationView(ApplicationController applicationController) throws HeadlessException {
        this.applicationController = applicationController;

        mainFrame = new JFrame();
        mainFrame.setLayout(new BorderLayout());
        mainFrame.setSize(800, 600);
        mainFrame.setTitle("Multientity planner");
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        mainFrame.setBackground(Color.WHITE);
        mainFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                applicationController.closeApplication();
            }
        });

        mainFrame.add(applicationController.getLeftSideView(), BorderLayout.LINE_START);
        mainFrame.add(applicationController.getContentView(), BorderLayout.CENTER);
    }

    public void setVisible(boolean visible) {
        mainFrame.setVisible(visible);
    }

    public void dispose() {
        mainFrame.dispose();
    }
}
