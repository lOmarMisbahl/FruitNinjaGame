package GameObjects;

import javafx.scene.image.Image;

import java.io.File;

public class Heart extends Bomb {
    @Override
    public Image[] getImages() {
        Image[] Arr;
        Arr = new Image[3];
        File file = new File(System.getProperty("user.dir") + "/src/GUI/Sprites/Heart.png");
        Image image = new Image(file.toURI().toString());
        Arr[0] = image;
        return Arr;
    }
}
