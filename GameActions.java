import GameObjects.GameObject;

public interface GameActions {
    /*
     *@return created game object
     */
    GameObject createGameObject();

    /*
     * update moving objects locations
     */
    void updateObjectsLocations(double time);

    /*
    * it is used to slice fruits located in your swiping region
    This method can take your swiping region as parameters (they
    depend on how you calculate it).
    */
    void sliceObjects(GameObject Object);

    /*
     *saves the current state of the game
     */
    void saveGame();

    /*
     *loads the last saved state of the game
     */
    void loadGame();

}


