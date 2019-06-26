package sample.resources;

import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

public class ImageLoader {
    private static Map<String, Image> images = new HashMap<>();

    private ImageLoader(){}

    public static Image getImage(String fileName) {
        return getImage(fileName, "png");
    }

    public static Image getImage(String fileName, String extension) {
        String search = fileName + "." + extension;
        if (images.containsKey(search)) {
            return images.get(search);
        }

        Image toLoad = new Image(getResource(search));
        images.put(search, toLoad);
        return toLoad;
    }

    private static String getResource(String fileName) { return ImageLoader.class.getResource(fileName).toString(); }
}
