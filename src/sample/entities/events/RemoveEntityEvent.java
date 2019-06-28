package sample.entities.events;

import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;
import sample.entities.Entity;

public class RemoveEntityEvent extends CustomEvent {
    public static final EventType<RemoveEntityEvent> ENTITY_REMOVED = new EventType<>(CUSTOM_EVENT_TYPE, "ENTITY_REMOVED");

    private Entity entity;

    public RemoveEntityEvent(Entity entity) {
        super(ENTITY_REMOVED);
        this.entity = entity;
    }

    public Entity getEntity() { return entity; }

    public static void fire(EventTarget target, Entity entity) {
        RemoveEntityEvent event = new RemoveEntityEvent(entity);
        Event.fireEvent(target, event);
    }
}
