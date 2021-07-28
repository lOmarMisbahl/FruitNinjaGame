package GameObjects;

import java.util.concurrent.ThreadLocalRandom;

public abstract class Fruit implements GameObject {
    private double x;
    private double y;
    private int angle;
    private double velocityX;
    private double velocityY;

    private boolean slowed = false;
    private boolean isSliced = false;

    public Fruit() {
        x = ThreadLocalRandom.current().nextInt(50, 550);
        y = 370;
        angle = 0;
        velocityX = -(x - 300) / 13;
        velocityY = -ThreadLocalRandom.current().nextInt(25, 35);
        //System.out.println("Velocity is "+ velocityY);
    }

    @Override
    public double getXlocation() {

        if (x >= 600) {
            return 1000;
        }

        return x;
    }

    @Override
    public double getYlocation() {
        if (y >= 375) {
            return 1000;
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


        if (time < 0.15) {
            time = slowed ? 0.05 : 0.15;
        }

        x = x + (time * velocityX);
        y = y + (time * velocityY);

        double gravityX = 0;
        velocityX = velocityX + (time * gravityX);
        double gravityY = 2;
        velocityY = velocityY + (time * gravityY);
        angle++;
        if (angle >= 360) {
            angle = 0;
        }

    }


}
