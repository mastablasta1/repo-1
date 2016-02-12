package com.idziak.planner.domain.entity;

import com.google.common.base.Preconditions;
import com.idziak.planner.util.Observable;

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
        modelChangeObservable = new Observable<>();
    }

    public Optional<GridCell> getCell(int xPos, int yPos) {
        if (xPos >= getWidth() || xPos < 0 || yPos >= getHeight() || yPos < 0)
            throw new ArrayIndexOutOfBoundsException("Exceeded size of grid");
        return Optional.ofNullable(grid[xPos][yPos]);
    }

    public int getWidth() {
        return grid.length;
    }

    public int getHeight() {
        return grid[0].length;
    }

    private void putCell(int xPos, int yPos, GridCell cell) {
        Preconditions.checkState(grid[xPos][yPos] == null, "Cell not empty");
        Preconditions.checkState(!contains(cell), "Grid already contains this cell");
        grid[xPos][yPos] = cell;
    }

    private boolean contains(GridCell entity) {
        return findCellPosition(entity).isPresent();
    }

    public Optional<Position> findCellPosition(GridCell cell) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (cell == grid[i][j]) {
                    return Optional.of(Position.of(i, j));
                }
            }
        }
        return Optional.empty();
    }

    public void clearCell(int xPos, int yPos) {
        GridCell gridCell = grid[xPos][yPos];

        if (gridCell instanceof EntityCell) {
            EntityDestinationCell destination = ((EntityCell) gridCell).getDestination();
            if (destination != null) {
                Optional<Position> optPosition = findCellPosition(destination);
                if (!optPosition.isPresent())
                    throw new AssertionError("Destination not found, but set");
                Position position = optPosition.get();
                grid[position.getX()][position.getY()] = null;
            }
        } else if (gridCell instanceof EntityDestinationCell) {
            EntityCell entityCell = ((EntityDestinationCell) gridCell).getEntityCell();
            entityCell.setDestination(null);
        }

        grid[xPos][yPos] = null;
    }

    public void addModelChangeListener(Consumer<GridModel> listener) {
        modelChangeObservable.addListener(listener);
    }

    public void fireModelChanged() {
        modelChangeObservable.notifyObservers(this);
    }

    public void putEntity(int gridX, int gridY) {
        EntityCell cell = new EntityCell();
        cell.setLabel(String.valueOf(++entityCounter));
        putCell(gridX, gridY, cell);
    }

    public void putObstacle(int gridX, int gridY) {
        putCell(gridX, gridY, new ObstacleCell());
    }


    public void putDestination(EntityCell entity, int gridX, int gridY) {
        Preconditions.checkState(entity.getDestination() == null, "Destination already present");
        EntityDestinationCell destinationCell = new EntityDestinationCell(entity);
        entity.setDestination(destinationCell);
        putCell(gridX, gridY, destinationCell);
    }

/*
    public Map<EntityCell, EntityDestinationCell> getEntityDestinationMap() {
        HashMap<EntityCell, EntityDestinationCell> map = new HashMap<>();

        for (GridCell[] row : grid) {
            for (GridCell gridCell : row) {
                if (isEntityCell(gridCell)) {
                    EntityCell entityCell = (EntityCell) gridCell;
                    EntityDestinationCell destination = entityCell.getDestination();
                    if (destination != null) {
                        map.put(entityCell, destination);
                    }
                }
            }
        }
        return map;
    }
*/

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
}
