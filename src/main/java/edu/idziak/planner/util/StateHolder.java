package edu.idziak.planner.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StateHolder<T extends Enum> {
    private static final Logger LOG = LoggerFactory.getLogger(StateHolder.class);
    private T state;
    private boolean logState;

    public void setState(T newState) {
        if (logState && newState != state) {
            LOG.info("State changed, {} -> {}", state, newState);
        }
        this.state = newState;
    }

    public T getState() {
        return state;
    }

    public boolean isStateNull() {
        return state == null;
    }

    public boolean stateEquals(T t) {
        return t == state;
    }

    public void setLogState(boolean log) {
        logState = log;
    }
}
