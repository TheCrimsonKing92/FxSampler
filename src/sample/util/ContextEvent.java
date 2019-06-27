package sample.util;

public class ContextEvent<T> extends Event {
    private T body;

    public ContextEvent(T body) {
        super(EventType.CONTEXT);
        this.body = body;
    }

    public T getBody() { return body; }
}
