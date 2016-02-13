package edu.idziak.planner.gui.grid.panel.contextmenu;

import edu.idziak.planner.domain.entity.GridModel;
import edu.idziak.planner.gui.grid.panel.GridPanelController;

public class ObstacleCellContextMenu extends GridPanelContextMenu {

    public ObstacleCellContextMenu(GridPanelController gridPanelController) {
        super(gridPanelController);

        addItem("Remove", e -> {
            GridModel model = gridPanelController.getGridModel();
            model.clearCell(gridPosition);
            model.fireModelChangedEvent();
        });
    }
}
