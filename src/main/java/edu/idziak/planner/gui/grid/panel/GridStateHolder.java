package edu.idziak.planner.gui.grid.panel;

import edu.idziak.planner.domain.entity.EntityCell;
import edu.idziak.planner.domain.entity.GridCell;
import edu.idziak.planner.util.StateHolder;

public class GridStateHolder extends StateHolder<GridPanelController.Mode> {
    EntityCell addDestinationMode_entityCell;
    GridCell draggingMode_cell;
}
