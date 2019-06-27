package sample.contexts;

import javafx.scene.Node;
import sample.util.EntityManager;

public abstract class ApplicationContext<T extends Node> {
    private EntityManager entityManager = new EntityManager();
    private T node;
    private double xOffset = 0;
    private double yOffset = 0;

    public ApplicationContext(T node) {
        this.node = node;
    }

    public ApplicationContext(T node, double xOffset, double yOffset) {
        this(node);
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    public T getNode() { return node; }
    protected double getxOffset() {
        return xOffset;
    }
    protected double getyOffset() {
        return yOffset;
    }

    public abstract void render();
    public abstract void update(double delta);
}
