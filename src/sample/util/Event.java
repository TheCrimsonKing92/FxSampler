package sample.util;

public abstract class Event {
    private InternalEventType type;

    public Event(InternalEventType type) {
        this.type = type;
    }

    public InternalEventType getType()  { return type; }
}
