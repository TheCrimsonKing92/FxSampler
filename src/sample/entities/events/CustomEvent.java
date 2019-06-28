package sample.entities.events;

import javafx.event.Event;
import javafx.event.EventType;

public abstract class CustomEvent extends Event {
    public static final EventType<CustomEvent> CUSTOM_EVENT_TYPE = new EventType<>("CUSTOM_EVENT");

    public CustomEvent(EventType<? extends Event> eventType) {
        super(eventType);
    }
}
