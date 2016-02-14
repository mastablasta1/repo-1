package edu.idziak.planner.gui.application;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
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
        JPanel contentPane = (JPanel) mainFrame.getContentPane();
        contentPane.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "escape");
        contentPane.getActionMap().put("escape", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                applicationController.keyPressed(KeyEvent.VK_ESCAPE);
            }
        });
    }

    public void setVisible(boolean visible) {
        mainFrame.setVisible(visible);
    }

    public void dispose() {
        mainFrame.dispose();
    }
}
