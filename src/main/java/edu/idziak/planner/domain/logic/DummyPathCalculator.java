package edu.idziak.planner.domain.logic;

import edu.idziak.planner.domain.entity.EntityCell;
import edu.idziak.planner.domain.entity.EntityDestinationCell;
import edu.idziak.planner.domain.entity.GridModel;
import edu.idziak.planner.domain.entity.GridPath;
import edu.idziak.planner.domain.entity.Position;

import java.util.Optional;

public class DummyPathCalculator implements PathCalculator {

    @Override
    public Optional<GridPath> calculatePath(GridModel gridModel, EntityCell entityCell, EntityDestinationCell destinationCell) {

        Position startPos = entityCell.getPosition();
        Position endPos = destinationCell.getPosition();

        GridPath.Builder pathBuilder = GridPath.builder();
        pathBuilder.start(entityCell);
        pathBuilder.end(destinationCell);

        pathBuilder.addPoint(Position.of(endPos.getX(), startPos.getY()));

        return Optional.of(pathBuilder.build());
    }
}
