package com.idziak.planner.gui.grid.panel;

import com.google.common.base.Strings;
import com.idziak.planner.domain.entity.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.util.Optional;

public class GridPanelView extends JPanel {
    private GridPanelModel gridPanelModel;
    private GridPanelController gridPanelController;

    public GridPanelView(GridPanelModel gridPanelModel, GridPanelController gridPanelController) {
        this.gridPanelModel = gridPanelModel;
        this.gridPanelController = gridPanelController;
        setBackground(Color.WHITE);
        addMouseListener(mouseAdapter);
    }

    private final MouseAdapter mouseAdapter = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            int gridX = e.getX() / gridPanelModel.getCellWidth();
            int gridY = e.getY() / gridPanelModel.getCellWidth();

            if (SwingUtilities.isRightMouseButton(e)) {
                gridPanelController.openContextMenu(e.getX(), e.getY(), gridX, gridY);
            } else if (SwingUtilities.isLeftMouseButton(e)) {
                gridPanelController.gridLeftClicked(gridX, gridY);
            }
        }
    };

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        drawGrid(g2);
        drawCells(g2);
    }

    private void drawCells(Graphics2D g) {
        int width = gridPanelModel.getGridModel().getWidth();
        int height = gridPanelModel.getGridModel().getHeight();
        int cellWidth = gridPanelModel.getCellWidth();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                GridModel gridModel = gridPanelModel.getGridModel();
                Optional<GridCell> optCell = gridModel.getCell(i, j);
                if (optCell.isPresent()) {
                    GridCell cell = optCell.get();
                    g.setClip(cellWidth * i, cellWidth * j, cellWidth, cellWidth);
                    if (cell instanceof EntityCell) {
                        EntityCell entityCell = (EntityCell) cell;
                        drawEntity(g, entityCell.getLabel());
                    } else if (cell instanceof ObstacleCell) {
                        drawObstacle(g);
                    } else if (cell instanceof EntityDestinationCell) {
                        EntityCell entityCell = ((EntityDestinationCell) cell).getEntityCell();
                        drawEntityDestination(g, entityCell.getLabel());
                    }
                }
            }
        }
    }

    private void drawEntityDestination(Graphics2D g, String label) {
        g.setColor(Color.GREEN);
        drawCellRectangle(g);
        drawLabel(g, label);
    }

    private void drawObstacle(Graphics2D g) {
        g.setColor(Color.BLACK);
        drawCellRectangle(g);
    }

    private void drawEntity(Graphics2D g, String label) {
        g.setColor(Color.ORANGE);
        drawCellRectangle(g);
        drawLabel(g, label);
    }

    private void drawLabel(Graphics2D g, String label) {
        if (Strings.isNullOrEmpty(label))
            return;

        Rectangle bounds = g.getClipBounds();
        int centerX = (int) bounds.getCenterX();
        int centerY = (int) bounds.getCenterY();
        FontMetrics fontMetrics = g.getFontMetrics();
        Rectangle2D stringBounds = fontMetrics.getStringBounds(label, g);

        g.setPaint(Color.BLACK);
        g.drawString(label, centerX - (int) stringBounds.getWidth() / 2, centerY);
    }

    private void drawCellRectangle(Graphics2D g) {
        int margin = 10;

        Rectangle clipBounds = g.getClipBounds();
        int cellX = (int) clipBounds.getX();
        int cellY = (int) clipBounds.getY();
        int width = (int) clipBounds.getWidth();
        int height = (int) clipBounds.getHeight();

        g.fillRect(cellX + margin, cellY + margin, width - 2 * margin, height - 2 * margin);
    }

    private void drawGrid(Graphics2D g) {
        GridModel gridModel = gridPanelModel.getGridModel();
        int height = gridModel.getHeight();
        int width = gridModel.getWidth();
        int cellWidth = gridPanelModel.getCellWidth();
        int x, y;

        // vertical lines
        y = height * cellWidth;
        for (int i = 0; i <= width; i++) {
            x = i * cellWidth;
            g.drawLine(x, 0, x, y);
        }

        // horizontal lines
        x = width * cellWidth;
        for (int i = 0; i <= height; i++) {
            y = i * cellWidth;
            g.drawLine(0, y, x, y);
        }
    }
}
