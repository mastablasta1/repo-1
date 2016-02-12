package com.idziak.planner.gui.grid.panel;

import com.google.common.base.Preconditions;
import com.idziak.planner.domain.entity.*;
import com.idziak.planner.domain.logic.GridController;
import com.idziak.planner.gui.grid.panel.contextmenu.EmptyCellContextMenu;
import com.idziak.planner.gui.grid.panel.contextmenu.EntityCellContextMenu;
import com.idziak.planner.gui.grid.panel.contextmenu.EntityDestinationCellContextMenu;
import com.idziak.planner.gui.grid.panel.contextmenu.ObstacleCellContextMenu;

import java.util.Optional;

public class GridPanelController {

    private final GridPanelView gridPanelView;
    private final GridPanelModel gridPanelModel;
    private final GridController gridController;

    // CONTEXT MENUS
    private final EmptyCellContextMenu emptyCellContextMenu;
    private final ObstacleCellContextMenu obstacleContextMenu;
    private final EntityCellContextMenu entityContextMenu;
    private final EntityDestinationCellContextMenu entityDestinationContextMenu;

    // MODES
    private Mode activeMode;
    private EntityCell addDestinationMode_entityCell;

    public GridPanelController(GridController gridController) {
        this.gridController = gridController;

        gridPanelModel = new GridPanelModel();
        gridPanelModel.setCellWidth(60);
        gridPanelModel.setGridModel(gridController.getModel());

        gridPanelView = new GridPanelView(gridPanelModel, this);

        emptyCellContextMenu = new EmptyCellContextMenu(this);
        obstacleContextMenu = new ObstacleCellContextMenu(this);
        entityContextMenu = new EntityCellContextMenu(this);
        entityDestinationContextMenu = new EntityDestinationCellContextMenu(this);

        gridController.getModel().addModelChangeListener(gridModel1 -> {
            gridController.updatePaths();
            gridPanelView.repaint();
        });
    }

    public GridPanelView getView() {
        return gridPanelView;
    }

    public void openContextMenu(int x, int y, int gridX, int gridY) {
        Optional<GridCell> optCell = gridPanelModel.getGridModel().getCell(gridX, gridY);

        if (optCell.isPresent()) {
            // handle Entity, Obstacle, EntityTarget
            GridCell cell = optCell.get();

            if (cell instanceof ObstacleCell) {
                obstacleContextMenu.show(x, y, gridX, gridY);
            } else if (cell instanceof EntityCell) {
                boolean isDestinationPresent = ((EntityCell) cell).getDestination() != null;
                entityContextMenu.setEnabledAddDestinationOption(!isDestinationPresent);
                entityContextMenu.show(x, y, gridX, gridY);

            } else if (cell instanceof EntityDestinationCell) {
                entityDestinationContextMenu.show(x, y, gridX, gridY);
            }

        } else {
            emptyCellContextMenu.show(x, y, gridX, gridY);
        }
    }

    public GridModel getGridModel() {
        return gridPanelModel.getGridModel();
    }

    private void enableMode(Mode newMode) {
        Preconditions.checkNotNull(newMode);
        Preconditions.checkState(activeMode == null, "Controller already in active mode");
        activeMode = newMode;
    }

    public void enableAddDestinationMode(EntityCell entityCell) {
        enableMode(Mode.ADD_DESTINATION);
        addDestinationMode_entityCell = entityCell;
    }

    public void gridLeftClicked(int gridX, int gridY) {
        GridModel gridModel = gridPanelModel.getGridModel();
        Optional<GridCell> cell = gridModel.getCell(gridX, gridY);

        if (activeMode == Mode.ADD_DESTINATION) {
            if (cell.isPresent()) {
                // TODO tooltip message: select empty cell
                return;
            }
            gridModel.putDestination(addDestinationMode_entityCell, gridX, gridY);
            gridModel.fireModelChanged();
            activeMode = null;
        }

    }

    public enum Mode {
        ADD_DESTINATION
    }
}
