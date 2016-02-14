package edu.idziak.planner.gui.grid.panel;

import com.google.common.base.Strings;
import edu.idziak.planner.domain.entity.EntityCell;
import edu.idziak.planner.domain.entity.EntityDestinationCell;
import edu.idziak.planner.domain.entity.GridCell;
import edu.idziak.planner.domain.entity.GridModel;
import edu.idziak.planner.domain.entity.GridPath;
import edu.idziak.planner.domain.entity.ObstacleCell;
import edu.idziak.planner.domain.entity.Position;

import javax.swing.JPanel;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class GridJPanel extends JPanel {
    private GridPanelModel gridPanelModel;
    private final GridModel gridModel;

    public GridJPanel(GridPanelModel gridPanelModel) {
        this.gridPanelModel = gridPanelModel;
        gridModel = gridPanelModel.getGridModel();
        resetPreferredSize();
    }

    public void resetPreferredSize() {
        setPreferredSize(new Dimension(
                gridModel.getWidth() * gridPanelModel.getCellWidth() + 1,
                gridModel.getHeight() * gridPanelModel.getCellWidth() + 1));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        resetPreferredSize();

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        drawGrid(g2);
        drawPaths(g2);
        drawCells(g2);
    }

    private void drawPaths(Graphics2D g) {
        GridModel gridModel = gridPanelModel.getGridModel();
        Set<GridPath> paths = gridModel.getPaths();

        g.setPaint(Color.BLACK);
        g.setStroke(new BasicStroke(2));
        for (GridPath path : paths) {
            List<Position> points = path.getAllPoints();
            Iterator<Position> it = points.iterator();
            Position pointA = it.next();
            while (it.hasNext()) {
                Position pointB = it.next();
                Position drawPointA = gridPosToCenteredPoint(pointA);
                Position drawPointB = gridPosToCenteredPoint(pointB);
                g.drawLine(drawPointA.getX(), drawPointA.getY(),
                        drawPointB.getX(), drawPointB.getY());
                pointA = pointB;
            }
        }
    }

    private Position gridPosToCenteredPoint(Position point) {
        int cellWidth = gridPanelModel.getCellWidth();
        return Position.of(
                point.getX() * cellWidth + cellWidth / 2,
                point.getY() * cellWidth + cellWidth / 2);
    }

    private void drawCells(Graphics2D g) {
        int width = gridPanelModel.getGridModel().getWidth();
        int height = gridPanelModel.getGridModel().getHeight();
        int cellWidth = gridPanelModel.getCellWidth();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                GridModel gridModel = gridPanelModel.getGridModel();
                Optional<GridCell> optCell = gridModel.getCell(Position.of(i, j));
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
