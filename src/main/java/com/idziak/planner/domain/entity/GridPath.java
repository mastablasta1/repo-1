package com.idziak.planner.domain.entity;

import java.util.ArrayList;
import java.util.List;

public class GridPath {
    private final EntityCell start;
    private final EntityDestinationCell end;
    private final List<Position> points;

    private GridPath(EntityCell start, EntityDestinationCell end, List<Position> points) {
        this.start = start;
        this.end = end;
        this.points = points;
    }

    public static Builder builder() {
        return new Builder();
    }

    public List<Position> getPoints() {
        return points;
    }

    public EntityCell getStart() {
        return start;
    }

    public EntityDestinationCell getEnd() {
        return end;
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
    }
}
