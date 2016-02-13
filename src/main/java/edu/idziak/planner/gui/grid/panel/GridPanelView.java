package edu.idziak.planner.gui.grid.panel;

import edu.idziak.planner.domain.entity.Position;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Optional;

public class GridPanelView {
    private GridPanelModel gridPanelModel;
    private GridPanelController gridPanelController;

    private GridJPanel gridPanel;
    private JScrollPane scrollPane;

    public GridPanelView(GridPanelModel gridPanelModel, GridPanelController gridPanelController) {
        this.gridPanelModel = gridPanelModel;
        this.gridPanelController = gridPanelController;
        gridPanel = new GridJPanel(gridPanelModel);
        gridPanel.setBackground(Color.WHITE);
        gridPanel.addMouseListener(mouseAdapter);
        gridPanel.addMouseMotionListener(mouseAdapter);

        scrollPane = new JScrollPane(gridPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(10);
        scrollPane.getHorizontalScrollBar().setUnitIncrement(10);
    }

    private final MouseAdapter mouseAdapter = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            Optional<Position> optPosition = toGridPosition(Position.of(e.getX(), e.getY()));
            if (!optPosition.isPresent())
                return;
            Position pos = optPosition.get();

            if (SwingUtilities.isRightMouseButton(e)) {
                gridPanelController.openContextMenu(e.getX(), e.getY(), pos);
            } else if (SwingUtilities.isLeftMouseButton(e)) {
                gridPanelController.gridLeftMouseClicked(pos);
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
            Optional<Position> optPosition = toGridPosition(Position.of(e.getX(), e.getY()));
            if (!optPosition.isPresent())
                return;
            Position gridPos = optPosition.get();

            if (SwingUtilities.isLeftMouseButton(e)) {
                gridPanelController.gridLeftMousePressed(gridPos);
            }
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            Optional<Position> optPosition = toGridPosition(Position.of(e.getX(), e.getY()));
            if (!optPosition.isPresent())
                return;
            Position gridPos = optPosition.get();

            if (SwingUtilities.isLeftMouseButton(e)) {
                gridPanelController.gridLeftMouseDragged(gridPos);
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            gridPanelController.gridMouseReleased();
        }
    };

    private Optional<Position> toGridPosition(Position position) {
        Position gridPosition = Position.of(
                position.getX() / gridPanelModel.getCellWidth(),
                position.getY() / gridPanelModel.getCellWidth());
        if (!gridPanelModel.getGridModel().isPositionWithinBounds(gridPosition)) {
            return Optional.empty();
        }
        return Optional.of(gridPosition);
    }

    public void repaint() {
        gridPanel.repaint();
    }

    public JComponent getScrollPane() {
        return scrollPane;
    }

    public JComponent getPanel() {
        return gridPanel;
    }
}
