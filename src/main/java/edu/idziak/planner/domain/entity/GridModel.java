package edu.idziak.planner.domain.entity;

import com.google.common.base.Preconditions;
import edu.idziak.planner.util.Observable;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

public class GridModel {

    private GridCell[][] grid;
    private Observable<GridModel> modelChangeObservable;
    private int entityCounter = 0;
    private Set<GridPath> gridPaths;

    public GridModel(int xSize, int ySize) {
        Preconditions.checkArgument(xSize > 0);
        Preconditions.checkArgument(ySize > 0);
        grid = new GridCell[xSize][ySize];
        gridPaths = new HashSet<>();
        modelChangeObservable = new Observable<>();
    }

    public int getWidth() {
        return grid.length;
    }

    public int getHeight() {
        return grid[0].length;
    }

    private void putCell(Position position, GridCell cell) {
        int xPos = position.getX();
        int yPos = position.getY();
        Preconditions.checkState(grid[xPos][yPos] == null, "Cell not empty");
        Preconditions.checkState(!contains(cell), "Grid already contains this cell");
        grid[xPos][yPos] = cell;
        cell.setPosition(Position.of(xPos, yPos));
    }

    private boolean contains(GridCell cell) {
        for (GridCell[] row : grid) {
            for (GridCell gridCell : row) {
                if (cell == gridCell) {
                    return true;
                }
            }
        }
        return false;
    }

    public void moveCell(GridCell cell, Position position) {
        Preconditions.checkArgument(isPositionWithinBounds(position), "Position out of bounds");
        grid[cell.getPosition().getX()][cell.getPosition().getY()] = null;
        putCell(position, cell);
    }

    public boolean isPositionWithinBounds(Position position) {
        Preconditions.checkNotNull(position, "Position null");
        return position.getX() >= 0
                && position.getY() >= 0
                && position.getX() < grid.length
                && position.getY() < grid[0].length;
    }


    public void addModelChangeListener(Consumer<GridModel> listener) {
        modelChangeObservable.addListener(listener);
    }

    public void fireModelChangedEvent() {
        modelChangeObservable.notifyObservers(this);
    }

    public void putEntity(Position position) {
        EntityCell cell = new EntityCell();
        cell.setLabel(String.valueOf(++entityCounter));
        putCell(position, cell);
    }

    public void putObstacle(Position position) {
        putCell(position, new ObstacleCell());
    }

    public void putDestination(EntityCell entity, Position gridPosition) {
        Preconditions.checkState(entity.getDestination() == null, "Destination already present");
        EntityDestinationCell destinationCell = new EntityDestinationCell(entity);
        entity.setDestination(destinationCell);
        putCell(gridPosition, destinationCell);
    }

    public static boolean isEntityCell(GridCell gridCell) {
        return gridCell instanceof EntityCell;
    }

    public Set<EntityCell> getAllEntities() {
        Set<EntityCell> entities = new HashSet<>();
        for (GridCell[] row : grid) {
            for (GridCell gridCell : row) {
                if (isEntityCell(gridCell)) {
                    entities.add(((EntityCell) gridCell));
                }
            }
        }
        return entities;
    }

    public void addPath(GridPath gridPath) {
        // TODO validate
        gridPaths.add(gridPath);
    }

    public void clearPaths() {
        gridPaths.clear();
    }

    public Set<GridPath> getPaths() {
        return gridPaths;
    }

    public Optional<GridCell> getCell(Position gridPos) {
        Preconditions.checkArgument(isPositionWithinBounds(gridPos), "Position out of bounds");
        return Optional.ofNullable(grid[gridPos.getX()][gridPos.getY()]);
    }

    public boolean isEmpty(Position gridPos) {
        return getCell(gridPos).isPresent();
    }

    public void clearCell(Position gridPosition) {
        int xPos = gridPosition.getX();
        int yPos = gridPosition.getY();
        GridCell gridCell = grid[xPos][yPos];

        if (gridCell instanceof EntityCell) {
            EntityDestinationCell destination = ((EntityCell) gridCell).getDestination();
            if (destination != null) {
                Position position = destination.getPosition();
                grid[position.getX()][position.getY()] = null;
            }
        } else if (gridCell instanceof EntityDestinationCell) {
            EntityCell entityCell = ((EntityDestinationCell) gridCell).getEntityCell();
            entityCell.setDestination(null);
        }

        grid[xPos][yPos] = null;
    }
}
