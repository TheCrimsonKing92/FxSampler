package sample.resources;

import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

public class ImageLoader {
    private static Map<String, Image> images = new HashMap<>();

    private ImageLoader(){}

    public static Image getImage(String fileName) {
        if (images.containsKey(fileName)) {
            return images.get(fileName);
        }

        Image toLoad = new Image(getResource(fileName));
        images.put(fileName, toLoad);
        return toLoad;
    }

    private static String getResource(String fileName) {
        return ImageLoader.class.getResource(fileName).toString();
    }
}
