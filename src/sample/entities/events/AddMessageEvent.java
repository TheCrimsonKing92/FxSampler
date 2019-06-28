package sample.entities.events;

import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;
import sample.entities.Message;

public class AddMessageEvent extends CustomEvent {
    public static final EventType<AddMessageEvent> MESSAGE_ADDED = new EventType<>(CUSTOM_EVENT_TYPE, "MESSAGE_ADDED");

    private final Message message;

    public AddMessageEvent(Message message) {
        super(MESSAGE_ADDED);
        this.message = message;
    }

    public Message getMessage() { return message; }

    public static void fire(EventTarget eventTarget, Message message) {
        Event.fireEvent(eventTarget, new AddMessageEvent(message));
    }
}
