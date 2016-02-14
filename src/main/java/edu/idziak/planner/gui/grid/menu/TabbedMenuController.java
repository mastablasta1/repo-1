package edu.idziak.planner.gui.grid.menu;

import edu.idziak.planner.domain.logic.GridController;

import javax.swing.JComponent;
import javax.swing.JTabbedPane;
import java.awt.Dimension;

public class TabbedMenuController {

    private GridMenuController gridMenuController;

    private JTabbedPane tabbedPane;

    public TabbedMenuController(GridController gridController) {
        gridMenuController = new GridMenuController(gridController);

        tabbedPane = new JTabbedPane();
        tabbedPane.setPreferredSize(new Dimension(200, 300));
        tabbedPane.addTab("Grid", gridMenuController.getView());
    }

    public JComponent getView() {
        return tabbedPane;
    }
}
