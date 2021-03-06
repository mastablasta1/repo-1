package edu.idziak.planner.domain.logic;

import edu.idziak.planner.domain.entity.EntityCell;
import edu.idziak.planner.domain.entity.EntityDestinationCell;
import edu.idziak.planner.domain.entity.GridModel;
import edu.idziak.planner.domain.entity.GridPath;

import java.util.Optional;

public interface PathCalculator {
    Optional<GridPath> calculatePath(GridModel gridModel, EntityCell entityCell, EntityDestinationCell destinationCell);
}
