package GameObjects;

import javafx.scene.image.Image;

import java.io.File;

public class Pear extends Fruit {
    @Override
    public Image[] getImages() {
        Image[] Arr;
        Arr = new Image[3];
        File file = new File(System.getProperty("user.dir") + "/src/GUI/Sprites/Pear.png");
        Image image = new Image(file.toURI().toString());
        Arr[0] = image;
        file = new File(System.getProperty("user.dir") + "/src/GUI/Sprites/PearLeft.png");
        image = new Image(file.toURI().toString());
        Arr[1] = image;
        file = new File(System.getProperty("user.dir") + "/src/GUI/Sprites/PearRight.png");
        image = new Image(file.toURI().toString());
        Arr[2] = image;
        return Arr;
    }
}
