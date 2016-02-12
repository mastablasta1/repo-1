package com.idziak.planner.domain.logic;

import com.idziak.planner.domain.entity.*;

import java.util.Optional;

public class PathFinder {

    public Optional<GridPath> findPath(GridModel gridModel, EntityCell entityCell, EntityDestinationCell destinationCell) {

        Optional<Position> optStartPos = gridModel.findCellPosition(entityCell);
        Optional<Position> optEndPos = gridModel.findCellPosition(destinationCell);
        if (!optStartPos.isPresent() || !optEndPos.isPresent()) {
            throw new IllegalArgumentException("Grid model does not contain given cells");
        }
        Position startPos = optStartPos.get();
        Position endPos = optEndPos.get();

        GridPath.Builder pathBuilder = GridPath.builder();
        pathBuilder.start(entityCell);
        pathBuilder.end(destinationCell);

        pathBuilder.addPoint(startPos);
        pathBuilder.addPoint(Position.of(endPos.getX(), startPos.getY()));
        pathBuilder.addPoint(endPos);

        return Optional.of(pathBuilder.build());
    }
}
