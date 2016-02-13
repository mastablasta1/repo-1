package edu.idziak.planner.gui.grid.menu;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

public class GridMenuController {

    private GridSizePanelController gridSizePanelController;

    private JPanel panel;

    private JButton addEntitiesButton;
    private JButton addObstaclesButton;
    private JButton removeObjectsButton;
    private JButton clearAllButton;

    public GridMenuController() {

        gridSizePanelController = new GridSizePanelController();

        addEntitiesButton = new JButton("Add entities");
        addObstaclesButton = new JButton("Add obstacles");
        removeObjectsButton = new JButton("Remove objects");
        clearAllButton = new JButton("Remove all");

        panel = new JPanel();
        panel.add(gridSizePanelController.getView());
        panel.add(addEntitiesButton);
        panel.add(addObstaclesButton);
        panel.add(removeObjectsButton);
        panel.add(clearAllButton);
    }

    public JComponent getView() {
        return panel;
    }
}
