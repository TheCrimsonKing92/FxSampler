package sample.util;

public class ApplicationEvent<T> extends Event {
    private T body;

    public ApplicationEvent(T body) {
        super(InternalEventType.APPLICATION);
        this.body = body;
    }

    public T getBody() { return body; }
}
