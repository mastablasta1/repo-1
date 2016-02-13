package edu.idziak.planner.domain.entity;

import com.google.common.base.Preconditions;

public class EntityCell extends GridCell {
    private EntityDestinationCell destination;
    private String label;

    public EntityDestinationCell getDestination() {
        return destination;
    }

    void setDestination(EntityDestinationCell destination) {
        if (destination != null) {
            Preconditions.checkArgument(destination.getEntityCell() == this, "Not a destination for this entity cell");
        }
        this.destination = destination;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
