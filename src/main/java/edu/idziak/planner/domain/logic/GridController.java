package edu.idziak.planner.domain.logic;

import edu.idziak.planner.domain.entity.EntityCell;
import edu.idziak.planner.domain.entity.EntityDestinationCell;
import edu.idziak.planner.domain.entity.GridModel;
import edu.idziak.planner.domain.entity.GridPath;

import java.util.Optional;
import java.util.Set;

public class GridController {

    private PathCalculator pathCalculator;
    private GridModel gridModel;

    public GridController() {
        pathCalculator = new PathCalculator();
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
            Optional<GridPath> path = pathCalculator.findPath(gridModel, entity, destination);
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
}
