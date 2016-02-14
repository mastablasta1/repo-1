package edu.idziak.planner.gui.grid.panel;

import edu.idziak.planner.domain.entity.EntityCell;
import edu.idziak.planner.domain.entity.EntityDestinationCell;
import edu.idziak.planner.domain.entity.GridCell;
import edu.idziak.planner.domain.entity.GridModel;
import edu.idziak.planner.domain.entity.ObstacleCell;
import edu.idziak.planner.domain.entity.Position;
import edu.idziak.planner.domain.logic.GridController;
import edu.idziak.planner.gui.grid.panel.contextmenu.EmptyCellContextMenu;
import edu.idziak.planner.gui.grid.panel.contextmenu.EntityCellContextMenu;
import edu.idziak.planner.gui.grid.panel.contextmenu.EntityDestinationCellContextMenu;
import edu.idziak.planner.gui.grid.panel.contextmenu.ObstacleCellContextMenu;

import java.util.Optional;

public class GridPanelController {

    private final GridPanelView gridPanelView;
    private final GridPanelModel gridPanelModel;
    private final GridController gridController;
    private final GridModel gridModel;
    private final GridStateHolder gridStateHolder;

    // CONTEXT MENUS
    private final EmptyCellContextMenu emptyCellContextMenu;
    private final ObstacleCellContextMenu obstacleContextMenu;
    private final EntityCellContextMenu entityContextMenu;
    private final EntityDestinationCellContextMenu entityDestinationContextMenu;

    public GridPanelController(GridController gridController) {
        this.gridController = gridController;
        gridController.setGridPanelController(this);
        gridStateHolder = new GridStateHolder();
        gridStateHolder.setLogState(true);

        gridPanelModel = new GridPanelModel();
        gridPanelModel.setCellWidth(50);
        gridPanelModel.setGridModel(gridController.getModel());

        gridPanelView = new GridPanelView(gridPanelModel, this);

        emptyCellContextMenu = new EmptyCellContextMenu(this);
        obstacleContextMenu = new ObstacleCellContextMenu(this);
        entityContextMenu = new EntityCellContextMenu(this);
        entityDestinationContextMenu = new EntityDestinationCellContextMenu(this);

        gridController.getModel().addModelChangeListener(gridModel1 -> {
            gridController.recalculatePaths();
            gridPanelView.repaint();
        });
        gridModel = gridPanelModel.getGridModel();
    }

    public GridPanelView getView() {
        return gridPanelView;
    }

    public void openContextMenu(int x, int y, Position gridPosition) {
        Optional<GridCell> optCell = gridModel.getCell(gridPosition);

        if (optCell.isPresent()) {
            // handle Entity, Obstacle, EntityTarget
            GridCell cell = optCell.get();

            if (cell instanceof ObstacleCell) {
                obstacleContextMenu.show(x, y, gridPosition);
            } else if (cell instanceof EntityCell) {
                boolean isDestinationPresent = ((EntityCell) cell).getDestination() != null;
                entityContextMenu.setEnabledAddDestinationOption(!isDestinationPresent);
                entityContextMenu.show(x, y, gridPosition);

            } else if (cell instanceof EntityDestinationCell) {
                entityDestinationContextMenu.show(x, y, gridPosition);
            }

        } else {
            emptyCellContextMenu.show(x, y, gridPosition);
        }
    }

    public GridModel getGridModel() {
        return gridModel;
    }

    public void enableAddDestinationMode(EntityCell entityCell) {
        gridStateHolder.setState(Mode.ADD_DESTINATION);
        gridStateHolder.addDestinationMode_entityCell = entityCell;
    }

    public void enableAddObstaclesMode() {
        if (gridStateHolder.isStateNull()) {
            gridStateHolder.setState(Mode.ADD_OBSTACLES);
        }
    }

    public void gridLeftMouseClicked(Position gridPos) {
    }

    public void gridLeftMousePressed(Position gridPos) {
        Optional<GridCell> cell = gridModel.getCell(gridPos);

        if (gridStateHolder.stateEquals(Mode.ADD_DESTINATION)) {
            if (cell.isPresent()) {
                // TODO tooltip message: select empty cell
                return;
            }
            gridModel.putDestination(gridStateHolder.addDestinationMode_entityCell, gridPos);
            gridModel.fireModelChangedEvent();
            gridStateHolder.setState(null);
        } else if (gridStateHolder.stateEquals(Mode.ADD_OBSTACLES)) {
            if (gridModel.getCell(gridPos).isPresent()) {
                return;
            }
            gridModel.putObstacle(gridPos);
            gridModel.fireModelChangedEvent();
        } else if (gridStateHolder.stateEquals(Mode.REMOVE_OBJECTS)) {
            // TODO
        } else if (gridStateHolder.isStateNull() && cell.isPresent()) {
            gridStateHolder.setState(Mode.DRAGGING);
            gridStateHolder.draggingMode_cell = cell.get();
        }
    }

    public void gridLeftMouseDragged(Position gridPos) {
        if (gridStateHolder.stateEquals(Mode.DRAGGING)) {
            if (!gridPos.equals(gridStateHolder.draggingMode_cell.getPosition())
                    && gridModel.isEmpty(gridPos)) {
                gridModel.moveCell(gridStateHolder.draggingMode_cell, gridPos);
                gridModel.fireModelChangedEvent();
            }
        } else if (gridStateHolder.stateEquals(Mode.ADD_OBSTACLES)) {
            if (gridModel.getCell(gridPos).isPresent()) {
                return;
            }
            gridModel.putObstacle(gridPos);
            gridModel.fireModelChangedEvent();
        }
    }

    public void gridMouseReleased() {
        if (gridStateHolder.stateEquals(Mode.DRAGGING)) {
            gridStateHolder.setState(null);
        }
    }

    public void clearActiveMode() {
        gridStateHolder.setState(null);
    }

    public enum Mode {
        ADD_DESTINATION, ADD_OBSTACLES, DRAGGING, REMOVE_OBJECTS
    }
}
