package sample.entities;

import javafx.scene.control.ChoiceDialog;
import javafx.scene.image.Image;
import sample.Manager;
import sample.entities.interfaces.Clickable;
import sample.util.Point;

import java.util.Optional;

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
        fire = Manager.getImage("Fire1.png");
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
    public void onClick() {
        if (messageReference == null) {
            placeMessage();
            placeDialog();
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

    private void placeDialog() {
        Point toPlace = getLocation().plusX(getWidth() + 5).plusY(30);
        ChoiceDialog<String> dialog = new ChoiceDialog<>("Close", "Feed");
        dialog.setHeaderText("Campfire");
        dialog.setTitle("Title?");
        dialog.setContentText("Choice? ");
        dialog.setX(toPlace.getX());
        dialog.setY(toPlace.getY());

        dialogReference = dialog;
        dialog.show();
    }

    private void placeMessage() {
        Point toPlace = getLocation().plusX(getWidth() + 5).plusY(10);
        messageReference = "Current Health: " + health;
        Manager.add(new Message(messageReference, toPlace, 24));
    }

    private void removeDialog() {

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
