package sample.util;

import sample.entities.Entity;
import sample.entities.Player;

import java.util.Optional;

public class EntityManager extends Manager<Entity> {
    public Player getPlayer() {
        Optional<Player> maybe = getCurrent().stream()
                                             .filter(Player.class::isInstance)
                                             .map(Player.class::cast)
                                             .findFirst();
        return maybe.orElse(null);
    }

    public boolean hasPlayer() { return getPlayer() != null; }
}
