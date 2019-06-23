package sample.entities.interfaces;

public interface Moveable extends Collideable {
    void move(double x, double y);
    void moveX(double x);
    void moveY(double y);
    void revertLast();
}
