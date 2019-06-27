package sample.util;

import sample.entities.Entity;
import sample.entities.Player;

import java.util.List;
import java.util.Optional;
import java.util.Vector;
import java.util.stream.Collectors;

public class EntityManager {
    private Vector<Entity> entities = new Vector<>();
    private Vector<Entity> toRemove = new Vector<>();

    public <T extends Entity> void add(T entity) {
        if (entity == null || entities.contains(entity)) {
            return;
        }

        entities.add(entity);
    }

    public <T extends Entity> void addAll(List<T> toAdd) {
        if (toAdd == null || toAdd.isEmpty()) {
            return;
        }

        entities.addAll(toAdd);
    }

    public List<Entity> getEntities() {
        return entities.stream()
                       .filter(entity -> !toRemove.contains(entity))
                       .collect(Collectors.toList());
    }

    public Player getPlayer() {
        Optional<Entity> maybePlayer = entities.stream().filter(Player.class::isInstance).findFirst();
        return (Player) maybePlayer.orElse(null);
    }

    public boolean hasPlayer() { return getPlayer() != null; }

    public <T extends Entity> void remove(T entity) {
        if (entity == null) {
            return;
        }

        markForRemoval(entity);
    }

    public void removeEntities() {
        entities.removeAll(toRemove);
        toRemove.clear();
    }

    private <T extends Entity> void markForRemoval(T entity) {
        toRemove.add(entity);
    }
}
