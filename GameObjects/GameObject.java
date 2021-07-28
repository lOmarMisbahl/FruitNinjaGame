package GameObjects;

import javafx.scene.image.Image;

public interface GameObject {

    /*
     *@return X location of game object
     */
    double getXlocation();

    /*
     *@return Y location of game object
     */
    double getYlocation();

    /*
     *@return whether the object is sliced or not
     */
    Boolean isSliced();

    /*
     *@return whether the object is dropped off the screen or not
     */
    Boolean hasMovedOffScreen();

    /*
     *it is used to slice the object
     */
    void slice();

    /*
    *it is used to move the object on the screen
    @param deltaTime: time elapsed since the object is thrown
    it is used calculate the new position of
    fruit object.

    */
    void move(double time);

    /*
    *@return at least two images of the object, one when it is
    sliced and one when it is not.
    */
    Image[] getImages();

    void slowFruit();

    void backToNormal();

}
