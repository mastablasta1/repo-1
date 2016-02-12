package com.idziak.planner.domain.entity;

public class EntityDestinationCell extends GridCell {
    private EntityCell entityCell;

    public EntityDestinationCell(EntityCell entityCell) {
        this.entityCell = entityCell;
    }

    public EntityCell getEntityCell() {
        return entityCell;
    }
}
