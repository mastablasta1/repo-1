package edu.idziak.planner.domain.entity;

import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.List;

public class GridPath {
    private final EntityCell start;
    private final EntityDestinationCell end;
    private final List<Position> intermediatePoints;

    private GridPath(EntityCell start, EntityDestinationCell end, List<Position> intermediatePoints) {
        this.start = start;
        this.end = end;
        this.intermediatePoints = ImmutableList.copyOf(intermediatePoints);
    }

    public static Builder builder() {
        return new Builder();
    }

    public EntityCell getStart() {
        return start;
    }

    public EntityDestinationCell getEnd() {
        return end;
    }

    public List<Position> getIntermediatePoints() {
        return new ArrayList<>(intermediatePoints);
    }

    public List<Position> getAllPoints() {
        List<Position> points = new ArrayList<>(intermediatePoints);
        points.add(0, start.getPosition());
        points.add(end.getPosition());
        return points;
    }

    public static class Builder {
        private EntityCell start;
        private EntityDestinationCell end;
        private List<Position> points = new ArrayList<>();

        public void start(EntityCell start) {
            this.start = start;
        }

        public void end(EntityDestinationCell end) {
            this.end = end;
        }

        public void addPoint(Position point) {
            this.points.add(point);
        }

        public GridPath build() {
            return new GridPath(start, end, points);
        }

        public EntityCell getStart() {
            return start;
        }

        public void points(List<Position> points) {
            this.points = new ArrayList<>(points);
        }
    }
}
