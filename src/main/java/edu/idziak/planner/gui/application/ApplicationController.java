package edu.idziak.planner.gui.application;

import edu.idziak.planner.domain.logic.GridController;
import edu.idziak.planner.gui.GUIUtils;
import edu.idziak.planner.gui.grid.menu.TabbedMenuController;
import edu.idziak.planner.gui.grid.panel.GridPanelController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.JComponent;

public class ApplicationController {
    private static final Logger LOG = LoggerFactory.getLogger(ApplicationController.class);

    private final ApplicationView applicationView;
    private final GridPanelController gridPanelController;
    private final TabbedMenuController tabbedMenuController;

    public ApplicationController() {
        GUIUtils.checkOnGUIThread();
        GUIUtils.setLookAndFeel();

        GridController gridController = new GridController();

        tabbedMenuController = new TabbedMenuController();
        gridPanelController = new GridPanelController(gridController);

        applicationView = new ApplicationView(this);
        applicationView.setVisible(true);
    }

    public void closeApplication() {
        applicationView.dispose();
    }

    public JComponent getLeftSideView() {
        return tabbedMenuController.getView();
    }

    public JComponent getContentView() {
        return gridPanelController.getView().getScrollPane();
    }
}
