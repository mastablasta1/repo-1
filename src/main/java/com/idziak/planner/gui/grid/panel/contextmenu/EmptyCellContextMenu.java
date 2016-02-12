package com.idziak.planner.gui.grid.panel.contextmenu;

import com.idziak.planner.domain.entity.GridModel;
import com.idziak.planner.gui.grid.ContextMenu;
import com.idziak.planner.gui.grid.panel.GridPanelController;

public class EmptyCellContextMenu extends ContextMenu {
    private int gridX;
    private int gridY;

    public EmptyCellContextMenu(GridPanelController gridPanelController) {
        super(gridPanelController.getView());

        addItem("Add entity", e -> {
            GridModel model = gridPanelController.getGridModel();
            model.putEntity(gridX, gridY);
            model.fireModelChanged();
        });

        addItem("Add obstacle", e -> {
            GridModel model = gridPanelController.getGridModel();
            model.putObstacle(gridX, gridY);
            model.fireModelChanged();
        });
    }

    public void show(int x, int y, int gridX, int gridY) {
        this.gridX = gridX;
        this.gridY = gridY;
        show(x, y);
    }
}
