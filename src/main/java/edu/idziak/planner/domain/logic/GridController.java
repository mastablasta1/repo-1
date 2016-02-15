package edu.idziak.planner.domain.logic;

import edu.idziak.planner.domain.entity.EntityCell;
import edu.idziak.planner.domain.entity.EntityDestinationCell;
import edu.idziak.planner.domain.entity.GridModel;
import edu.idziak.planner.domain.entity.GridPath;
import edu.idziak.planner.gui.grid.panel.GridPanelController;

import java.util.Optional;
import java.util.Set;

public class GridController {

    private PathCalculator pathCalculator;
    private GridModel gridModel;
    private GridPanelController gridPanelController;

    public GridController() {
        pathCalculator = new AStarPathCalculator();
        gridModel = new GridModel(10, 10);
    }

    public void recalculatePaths() {
        Set<EntityCell> entities = gridModel.getAllEntities();
        gridModel.clearPaths();
        for (EntityCell entity : entities) {
            EntityDestinationCell destination = entity.getDestination();
            if (destination == null) {
                continue;
            }
            Optional<GridPath> path = pathCalculator.calculatePath(gridModel, entity, destination);
            if (!path.isPresent()) {
                continue;
            }
            GridPath gridPath = path.get();
            gridModel.addPath(gridPath);
        }
    }

    public GridModel getModel() {
        return gridModel;
    }

    public void resizeModel(int x, int y) {
        gridModel.resizeModel(x, y);
        gridModel.fireModelChangedEvent();
    }

    public void enableAddObstaclesMode() {
        gridPanelController.enableAddObstaclesMode();
    }

    public void clearActiveOperation() {
        gridPanelController.clearActiveMode();
    }

    public void setGridPanelController(GridPanelController gridPanelController) {
        this.gridPanelController = gridPanelController;
    }
}
