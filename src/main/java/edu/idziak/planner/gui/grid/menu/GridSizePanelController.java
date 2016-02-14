package edu.idziak.planner.gui.grid.menu;

import edu.idziak.planner.domain.logic.GridController;
import edu.idziak.planner.util.Utils;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Optional;

public class GridSizePanelController {

    private GridController gridController;

    private JPanel gridSizeControlsPanel;
    private JLabel gridSizeControlsLabel;
    private JLabel xGridSizeLabel;
    private JLabel yGridSizeLabel;
    private JTextField xGridSizeTextField;
    private JTextField yGridSizeTextField;

    private final KeyAdapter keyListener = new KeyAdapter() {
        @Override
        public void keyTyped(KeyEvent e) {
            if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                Optional<Integer> optGridSizeX = Utils.parseInt(xGridSizeTextField.getText());
                Optional<Integer> optGridSizeY = Utils.parseInt(yGridSizeTextField.getText());
                if (!optGridSizeX.isPresent() || !optGridSizeY.isPresent()
                        || optGridSizeX.get() < 1 || optGridSizeY.get() < 1) {
                    return; // TODO warning
                }
                gridController.resizeModel(optGridSizeX.get(), optGridSizeY.get());
            }
        }
    };

    public GridSizePanelController(GridController gridController) {
        this.gridController = gridController;

        gridSizeControlsPanel = new JPanel(new GridBagLayout());
        gridSizeControlsPanel.setMaximumSize(new Dimension(200, 200));
        initComponents();
    }

    private void initComponents() {
        gridSizeControlsLabel = new JLabel("Grid size:");
        xGridSizeLabel = new JLabel("X");
        yGridSizeLabel = new JLabel("Y");
        xGridSizeTextField = new JTextField("10", 5);
        yGridSizeTextField = new JTextField("10", 5);

        xGridSizeTextField.addKeyListener(keyListener);
        yGridSizeTextField.addKeyListener(keyListener);

        GridBagConstraints c = new GridBagConstraints();

        // controls label
        c.fill = GridBagConstraints.VERTICAL;
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.WEST;
        c.insets = new Insets(0, 0, 5, 0);
        gridSizeControlsPanel.add(gridSizeControlsLabel, c);

        // x label
        c.fill = GridBagConstraints.VERTICAL;
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 0;
        c.insets = new Insets(0, 0, 0, 5);
        gridSizeControlsPanel.add(xGridSizeLabel, c);

        // x text field
        c.fill = GridBagConstraints.VERTICAL;
        c.gridx = 1;
        c.gridy = 1;
        c.weightx = 1;
        c.anchor = GridBagConstraints.WEST;
        c.insets = new Insets(0, 0, 0, 0);
        gridSizeControlsPanel.add(xGridSizeTextField, c);

        // y label
        c.fill = GridBagConstraints.VERTICAL;
        c.gridx = 0;
        c.gridy = 2;
        c.weightx = 0;
        c.anchor = GridBagConstraints.WEST;
        c.insets = new Insets(0, 0, 0, 5);
        gridSizeControlsPanel.add(yGridSizeLabel, c);

        // y text field
        c.fill = GridBagConstraints.VERTICAL;
        c.gridx = 1;
        c.gridy = 2;
        gridSizeControlsPanel.add(yGridSizeTextField, c);
    }

    public JComponent getView() {
        return gridSizeControlsPanel;
    }
}
