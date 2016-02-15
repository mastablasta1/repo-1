package edu.idziak.planner.domain.entity;

import com.google.common.base.Preconditions;
import edu.idziak.planner.util.Observable;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

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
        return isPositionWithinBounds(position.getX(), position.getY());
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
        return getCell(gridPos.getX(), gridPos.getY());
    }

    public Optional<GridCell> getCell(int x, int y) {
        Preconditions.checkArgument(isPositionWithinBounds(x, y), "Position out of bounds");
        return Optional.ofNullable(grid[x][y]);
    }

    private boolean isPositionWithinBounds(int x, int y) {
        return x >= 0
                && y >= 0
                && x < grid.length
                && y < grid[0].length;
    }

    public boolean isEmpty(Position gridPos) {
        return !getCell(gridPos).isPresent();
    }

    public void clearCell(Position gridPosition) {
        int x = gridPosition.getX();
        int y = gridPosition.getY();
        GridCell gridCell = grid[x][y];

        if (gridCell instanceof EntityCell) {
            EntityDestinationCell destination = ((EntityCell) gridCell).getDestination();
            if (destination != null) {
                Position position = destination.getPosition();
                grid[position.getX()][position.getY()] = null;
            }
            deletePathsOfCell(gridCell);
        } else if (gridCell instanceof EntityDestinationCell) {
            EntityCell entityCell = ((EntityDestinationCell) gridCell).getEntityCell();
            entityCell.setDestination(null);
            deletePathsOfCell(gridCell);
        }

        grid[x][y] = null;
    }

    private void deletePathsOfCell(GridCell gridCell) {
        gridPaths = gridPaths.stream()
                .filter(gridPath -> gridPath.getStart() != gridCell
                        && gridPath.getEnd() != gridCell)
                .collect(Collectors.toSet());
    }

    public void resizeModel(int x, int y) {
        Preconditions.checkArgument(x > 0 && y > 0, "Negative size");
        if (x < grid.length) {
            for (int i = x; i < grid.length; i++) {
                for (int j = 0; j < grid[0].length; j++) {
                    clearCell(Position.of(i, j));
                }
            }
        }

        if (y < grid[0].length) {
            for (int i = 0; i < grid.length; i++) {
                for (int j = y; j < grid[0].length; j++) {
                    clearCell(Position.of(i, j));
                }
            }
        }

        GridCell[][] newGrid = new GridCell[x][y];

        for (int i = 0; i < Math.min(x, grid.length); i++) {
            System.arraycopy(grid[i], 0, newGrid[i], 0, Math.min(y, grid[i].length));
        }
        grid = newGrid;
    }

    public void clearAll() {
        gridPaths = new HashSet<>();
        grid = new GridCell[grid.length][grid[0].length];
        entityCounter = 0;
    }

    public static boolean isObstacleCell(GridCell cell) {
        return cell instanceof ObstacleCell;
    }
}
