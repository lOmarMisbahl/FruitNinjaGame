package GameObjects;

import javafx.scene.image.Image;

import java.util.concurrent.ThreadLocalRandom;

public class Slice implements GameObject {
    private double x;
    private double y;
    private double velocity;
    private Image[] myImage = new Image[1];
    private boolean slowed = false;
    private boolean isSliced = false;

    public Slice(double x,double y,Image Current) {
        this.x =x;
        this.y = y;
        myImage[0] = Current;

        velocity = -ThreadLocalRandom.current().nextInt(25, 35);

    }

    @Override
    public double getXlocation() {

        if (x >= 600 ){
            return  1000;
        }

        return x;
    }

    @Override
    public double getYlocation() {
        if (y  >= 375){
            return  1000;
        }
        return y;
    }


    @Override
    public Boolean isSliced() {
        return isSliced;
    }

    @Override
    public Boolean hasMovedOffScreen() {

        return (getYlocation() > 375) || (getXlocation() > 600);
    }

    @Override
    public void slice() {
        isSliced = true;
    }

    @Override
    public void slowFruit() {
        slowed = true;
    }

    @Override
    public void backToNormal() {
        slowed = false;
    }

    @Override
    public void move(double time) {

        //the time is almost constant = 0.15 this condition is used to handle frame drops if occurred
        if (time < 0.15) {
            time = slowed ? 0.05 : 0.15;
        }

        y = y - (time * velocity);


    }

    @Override
    public Image[] getImages() {

        return myImage;
    }
}
