package edu.idziak.planner.gui.grid.menu;

import edu.idziak.planner.domain.logic.GridController;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

public class GridMenuController {

    private final GridController gridController;
    private GridSizePanelController gridSizePanelController;

    private JPanel panel;

    private JButton addEntitiesButton;
    private JButton addObstaclesButton;
    private JButton removeObjectsButton;
    private JButton clearAllButton;

    public GridMenuController(GridController gridController) {
        this.gridController = gridController;
        gridSizePanelController = new GridSizePanelController(this.gridController);

        addEntitiesButton = new JButton("Add entities");
        addObstaclesButton = new JButton("Add obstacles");
        removeObjectsButton = new JButton("Remove objects");
        clearAllButton = new JButton("Remove all");

        clearAllButton.addActionListener(e -> {
            gridController.getModel().clearAll();
            gridController.getModel().fireModelChangedEvent();
        });

        addObstaclesButton.addActionListener(e -> gridController.enableAddObstaclesMode());


        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        Box box = Box.createVerticalBox();

        box.add(gridSizePanelController.getView());
        box.add(addEntitiesButton);
        box.add(addObstaclesButton);
        box.add(removeObjectsButton);
        box.add(clearAllButton);
        box.add(Box.createVerticalGlue());

        panel.setAlignmentX(0);
        panel.add(box);
    }

    public JComponent getView() {
        return panel;
    }
}
