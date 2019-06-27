package sample.util;

public abstract class EventWrapper<T extends Event> {
    private T event;

    public EventWrapper(T event) {
        this.event = event;
    }

    public T getEvent() { return event; }
}
