package com.idziak.planner.gui.grid.panel.contextmenu;

import com.idziak.planner.domain.entity.GridModel;
import com.idziak.planner.gui.grid.ContextMenu;
import com.idziak.planner.gui.grid.panel.GridPanelController;

public class ObstacleCellContextMenu extends ContextMenu {

    private int gridX;
    private int gridY;

    public ObstacleCellContextMenu(GridPanelController gridPanelController) {
        super(gridPanelController.getView());

        addItem("Remove", e -> {
            GridModel model = gridPanelController.getGridModel();
            model.clearCell(gridX, gridY);
            model.fireModelChanged();
        });
    }

    public void show(int x, int y, int gridX, int gridY) {
        this.gridY = gridY;
        this.gridX = gridX;
        show(x, y);
    }
}
