package edu.idziak.planner.gui.grid.panel.contextmenu;

import edu.idziak.planner.domain.entity.GridModel;
import edu.idziak.planner.gui.grid.panel.GridPanelController;

public class EntityDestinationCellContextMenu extends GridPanelContextMenu {

    public EntityDestinationCellContextMenu(GridPanelController gridPanelController) {
        super(gridPanelController);

        addItem("Remove", e -> {
            GridModel model = gridPanelController.getGridModel();
            model.clearCell(gridPosition);
            model.fireModelChangedEvent();
        });
    }

}
