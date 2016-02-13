package edu.idziak.planner.gui.grid.menu;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GridSizePanelController {
    private JPanel gridSizeControlsPanel;
    private JLabel gridSizeControlsLabel;
    private JLabel xGridSizeLabel;
    private JLabel yGridSizeLabel;
    private JTextField xGridSizeTextField;
    private JTextField yGridSizeTextField;

    public GridSizePanelController() {
        gridSizeControlsLabel = new JLabel("Grid size:");
        xGridSizeLabel = new JLabel("X");
        yGridSizeLabel = new JLabel("Y");
        xGridSizeTextField = new JTextField("10");
        yGridSizeTextField = new JTextField("10");

        gridSizeControlsPanel = new JPanel();

        gridSizeControlsPanel.add(gridSizeControlsLabel);
        gridSizeControlsPanel.add(xGridSizeLabel);
        gridSizeControlsPanel.add(xGridSizeTextField);
        gridSizeControlsPanel.add(yGridSizeLabel);
        gridSizeControlsPanel.add(yGridSizeTextField);
    }

    public JComponent getView() {
        return gridSizeControlsPanel;
    }
}
