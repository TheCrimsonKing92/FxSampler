package sample.util;

import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;

public abstract class Manager<T> {
    private Vector<T> current = new Vector<>();
    private Vector<T> toRemove = new Vector<>();

    public <R extends T> void add(R toAdd) {
        if (toAdd == null || current.contains(toAdd)) {
            return;
        }

        current.add(toAdd);
    }

    public void clear() {
        current.removeAll(toRemove);
        toRemove.clear();
    }

    public <R extends T> void addAll(List<R> toAdd) {
        if (toAdd == null || toAdd.isEmpty()) {
            return;
        }

        current.addAll(toAdd);
    }

    public List<T> getCurrent() {
        return current.stream()
                .filter(instance -> !toRemove.contains(instance))
                .collect(Collectors.toList());
    }

    public <R extends T> void remove(R instance) {
        if (instance == null) {
            return;
        }

        markForRemoval(instance);
    }

    private <R extends T> void markForRemoval(T entity) {
        toRemove.add(entity);
    }
}
