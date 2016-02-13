package edu.idziak.planner.gui.grid.panel.contextmenu;

import edu.idziak.planner.domain.entity.Position;
import edu.idziak.planner.gui.grid.ContextMenu;
import edu.idziak.planner.gui.grid.panel.GridPanelController;

public class GridPanelContextMenu extends ContextMenu {

    protected Position gridPosition;

    public GridPanelContextMenu(GridPanelController gridPanelController) {
        super(gridPanelController.getView().getPanel());
    }

    public void show(int x, int y, Position gridPosition) {
        this.gridPosition = gridPosition;
        show(x, y);
    }
}
