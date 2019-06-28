package sample.entities;

import javafx.event.EventTarget;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import sample.entities.events.AddMessageEvent;
import sample.entities.events.RemoveEntityEvent;
import sample.entities.events.RemoveMessageEvent;
import sample.entities.interfaces.Clickable;
import sample.resources.ImageLoader;
import sample.util.Point;

public class Fire extends TickableSprite implements Clickable {
    private static Image fire;
    private EventTarget eventTarget;
    private int health = 10;
    private int maxHealth = 10;
    private Message messageReference;
    private int minHealth = 1;
    private int waneBuffer = 0;
    private int waneMax = 10;

    static {
        fire = ImageLoader.getImage("Fire1");
    }

    public Fire(EventTarget eventTarget) {
        this(eventTarget, Point.of(180, 150));
    }

    public Fire(EventTarget eventTarget, Point location) {
        this(eventTarget, fire, location, 24, 18);
    }

    public Fire(EventTarget eventTarget, Image image, Point location, double height, double width) {
        super(image, location, height, width);
        this.eventTarget = eventTarget;
    }

    public Fire(EventTarget eventTarget, Image image, Point location, double height, double width, double xVelocity, double yVelocity) {
        super(image, location, height, width, xVelocity, yVelocity);
        this.eventTarget = eventTarget;
    }

    @Override
    public void onClick(Point point) {
        if (messageReference == null) {
            placeMessage();
        } else {
            removeMessage();
        }
    }

    @Override
    public void tick(double ticks) {
        boolean healthChanged = false;
        waneBuffer = waneBuffer + (int) ticks;

        while (waneBuffer > waneMax) {
            health = health - 1;
            waneBuffer = waneBuffer - waneMax;
            healthChanged = true;
        }

        if (health < minHealth) {
            die();
        }

        if (healthChanged && messageReference != null) {
            replaceMessage();
        }
    }

    private void die() {
        removeMessage();
        RemoveEntityEvent.fire(eventTarget, this);
    }

    private void feed() {
        health = health + 1;
    }

    private void placeMessage() {
        if (messageReference == null) {
            Point toPlace = getLocation().plusX(getWidth() + 5).plusY(10);
            messageReference = new MessagePane(eventTarget, "Current Health: " + health, toPlace, Font.font("Times New Roman", FontWeight.NORMAL, 12), true, 0);
            AddMessageEvent.fire(eventTarget, messageReference);
        }
    }

    private void removeMessage() {
        if (messageReference != null) {
            RemoveMessageEvent.fire(eventTarget, messageReference);
            messageReference = null;
        }
    }

    private void replaceMessage() {
        removeMessage();
        placeMessage();
    }
}
