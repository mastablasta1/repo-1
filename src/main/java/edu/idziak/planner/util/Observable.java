package edu.idziak.planner.util;

import com.google.common.base.Preconditions;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public class Observable<T> {
    private final Set<Consumer<T>> observers = new HashSet<>();

    public void addListener(Consumer<T> observer) {
        Preconditions.checkNotNull(observer);
        observers.add(observer);
    }

    public void notifyObservers(T event) {
        observers.forEach(consumer -> consumer.accept(event));
    }
}
