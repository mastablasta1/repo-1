package edu.idziak.planner.gui.grid.panel;

import edu.idziak.planner.domain.entity.GridModel;

public class GridPanelModel {

    private GridModel gridModel;

    private int cellWidth;

    public void setGridModel(GridModel gridModel) {
        this.gridModel = gridModel;
    }

    public void setCellWidth(int cellWidth) {
        this.cellWidth = cellWidth;
    }

    public GridModel getGridModel() {
        return gridModel;
    }

    public int getCellWidth() {
        return cellWidth;
    }
}
