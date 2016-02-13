package edu.idziak.planner.gui.grid.panel.contextmenu;

import edu.idziak.planner.domain.entity.EntityCell;
import edu.idziak.planner.domain.entity.GridCell;
import edu.idziak.planner.domain.entity.GridModel;
import edu.idziak.planner.gui.grid.panel.GridPanelController;

import javax.swing.JMenuItem;
import java.util.Optional;

public class EntityCellContextMenu extends GridPanelContextMenu {
    private final JMenuItem addDestinationMenuItem;

    public EntityCellContextMenu(GridPanelController gridPanelController) {
        super(gridPanelController);

        addItem("Remove", e -> {
            GridModel model = gridPanelController.getGridModel();
            model.clearCell(gridPosition);
            model.fireModelChangedEvent();
        });

        addDestinationMenuItem = addItem("Add destination", e -> {
            GridModel model = gridPanelController.getGridModel();
            Optional<GridCell> optCell = model.getCell(gridPosition);
            gridPanelController.enableAddDestinationMode((EntityCell) optCell.get());
        });
    }

    public void setEnabledAddDestinationOption(boolean b) {
        addDestinationMenuItem.setEnabled(b);
    }


}
