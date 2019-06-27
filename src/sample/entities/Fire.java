package sample.entities;

import javafx.scene.control.ChoiceDialog;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import sample.entities.interfaces.Clickable;
import sample.util.Point;

public class Fire extends TickableSprite implements Clickable {
    private static Image fire;
    private ChoiceDialog<String> dialogReference;
    private int health = 10;
    private int maxHealth = 10;
    private String messageReference;
    private int minHealth = 1;
    private int waneBuffer = 0;
    private int waneMax = 10;

    static {
        fire = Manager.getImage("Fire1");
    }

    public Fire() {
        this(Point.of(180, 150));
    }

    public Fire(Point location) {
        this(fire, location, 24, 18);
    }

    public Fire(Image image, Point location, double height, double width) {
        super(image, location, height, width);
    }

    public Fire(Image image, Point location, double height, double width, double xVelocity, double yVelocity) {
        super(image, location, height, width, xVelocity, yVelocity);
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

        // check dialog
        /*  
        if (dialogReference != null) {
            String result = dialogReference.getResult();
            if (result != null && !result.isEmpty()) {
                if (result.equalsIgnoreCase("Close")) {
                    dialogReference.close();
                    dialogReference = null;
                } else if (result.equalsIgnoreCase("Feed")) {
                    feed();
                }
            }
        }
        */
    }

    private void die() {
        removeMessage();
        Manager.remove(this);
    }

    private void feed() {
        health = health + 1;
    }

    private void placeMessage() {
        Point toPlace = getLocation().plusX(getWidth() + 5).plusY(10);
        messageReference = "Current Health: " + health;
        // Manager.add(new MessagePane(messageReference, toPlace, Font.font("Times New Roman", FontWeight.NORMAL, 16), 7));
        Manager.add(new MessagePane(messageReference, toPlace, Font.font("Times New Roman", FontWeight.NORMAL, 12), 0));
    }

    private void removeMessage() {
        if (messageReference != null) {
            Manager.remove(messageReference);
            messageReference = null;
        }
    }

    private void replaceMessage() {
        removeMessage();
        placeMessage();
    }
}
