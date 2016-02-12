package com.idziak.planner.gui.grid.panel.contextmenu;

import com.idziak.planner.domain.entity.EntityCell;
import com.idziak.planner.domain.entity.GridCell;
import com.idziak.planner.domain.entity.GridModel;
import com.idziak.planner.gui.grid.ContextMenu;
import com.idziak.planner.gui.grid.panel.GridPanelController;

import javax.swing.*;
import java.util.Optional;

public class EntityCellContextMenu extends ContextMenu {
    private final JMenuItem addDestinationMenuItem;
    private int gridX;
    private int gridY;

    public EntityCellContextMenu(GridPanelController gridPanelController) {
        super(gridPanelController.getView());

        addItem("Remove", e -> {
            GridModel model = gridPanelController.getGridModel();
            model.clearCell(gridX, gridY);
            model.fireModelChanged();
        });

        addDestinationMenuItem = addItem("Add destination", e -> {
            GridModel model = gridPanelController.getGridModel();
            Optional<GridCell> optCell = model.getCell(gridX, gridY);
            gridPanelController.enableAddDestinationMode((EntityCell) optCell.get());
        });
    }

    public void setEnabledAddDestinationOption(boolean b) {
        addDestinationMenuItem.setEnabled(b);
    }

    public void show(int x, int y, int gridX, int gridY) {
        this.gridY = gridY;
        this.gridX = gridX;
        show(x, y);
    }

}
