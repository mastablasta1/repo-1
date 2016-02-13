package edu.idziak.planner.gui.grid.panel.contextmenu;

import edu.idziak.planner.domain.entity.GridModel;
import edu.idziak.planner.gui.grid.panel.GridPanelController;

public class EmptyCellContextMenu extends GridPanelContextMenu {

    public EmptyCellContextMenu(GridPanelController gridPanelController) {
        super(gridPanelController);

        addItem("Add entity", e -> {
            GridModel model = gridPanelController.getGridModel();
            model.putEntity(gridPosition);
            model.fireModelChangedEvent();
        });

        addItem("Add obstacle", e -> {
            GridModel model = gridPanelController.getGridModel();
            model.putObstacle(gridPosition);
            model.fireModelChangedEvent();
        });
    }
}
