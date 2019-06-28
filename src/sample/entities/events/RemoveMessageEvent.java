package sample.entities.events;

import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;
import sample.entities.Message;

public class RemoveMessageEvent extends CustomEvent {
    public static final EventType<RemoveMessageEvent> MESSAGE_REMOVED = new EventType<>(CUSTOM_EVENT_TYPE, "MESSAGE_REMOVED");

    private Message message;

    public RemoveMessageEvent(Message message) {
        super(MESSAGE_REMOVED);
        this.message = message;
    }

    public Message getMessage() { return message; }

    public static void fire(EventTarget target, Message message) {
        RemoveMessageEvent event = new RemoveMessageEvent(message);
        Event.fireEvent(target, event);
    }
}
