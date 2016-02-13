package edu.idziak.planner.gui.grid.menu;

import javax.swing.JComponent;
import javax.swing.JTabbedPane;
import java.awt.Dimension;

public class TabbedMenuController {

    private GridMenuController gridMenuController;

    private JTabbedPane tabbedPane;

    public TabbedMenuController() {
        gridMenuController = new GridMenuController();

        tabbedPane = new JTabbedPane();
        tabbedPane.setPreferredSize(new Dimension(200, 300));
        tabbedPane.addTab("Grid", gridMenuController.getView());
    }

    public JComponent getView() {
        return tabbedPane;
    }
}
