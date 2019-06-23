package sample.entities.interfaces;

public interface Tickable {
    default boolean canTick() {
        return false;
    }

    default void tick(double ticks) { }
}
