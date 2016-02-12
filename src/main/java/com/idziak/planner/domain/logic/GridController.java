package com.idziak.planner.domain.logic;

import com.idziak.planner.domain.entity.EntityCell;
import com.idziak.planner.domain.entity.EntityDestinationCell;
import com.idziak.planner.domain.entity.GridModel;
import com.idziak.planner.domain.entity.GridPath;

import java.util.Optional;
import java.util.Set;

public class GridController {

    private PathCalculator pathCalculator;
    private GridModel gridModel;

    public GridController() {
        gridModel = new GridModel(10, 10);
    }

    public void updatePaths() {
        Set<EntityCell> entities = gridModel.getAllEntities();
        for (EntityCell entity : entities) {
            EntityDestinationCell destination = entity.getDestination();
            Optional<GridPath> path = pathCalculator.findPath(gridModel, entity, destination);

        }
    }

    public GridModel getModel() {
        return gridModel;
    }
}
