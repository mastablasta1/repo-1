package com.idziak.planner.domain.logic;

import com.idziak.planner.domain.entity.*;

import java.util.Optional;

public class PathCalculator {

    public Optional<GridPath> findPath(GridModel gridModel, EntityCell entityCell, EntityDestinationCell destinationCell) {

        Position startPos = entityCell.getPosition();
        Position endPos = destinationCell.getPosition();

        GridPath.Builder pathBuilder = GridPath.builder();
        pathBuilder.start(entityCell);
        pathBuilder.end(destinationCell);

        pathBuilder.addPoint(startPos);
        pathBuilder.addPoint(Position.of(endPos.getX(), startPos.getY()));
        pathBuilder.addPoint(endPos);

        return Optional.of(pathBuilder.build());
    }
}
