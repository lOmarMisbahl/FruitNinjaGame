import GameObjects.*;
import javafx.scene.image.Image;

import java.util.ArrayList;

public class GameEngine implements GameActions {
    private static GameEngine ourInstance = new GameEngine();
    private ArrayList<GameObject> ActiveObjects = new ArrayList<>();
    private ArrayList<GameObject> Slices = new ArrayList<>();

    private int Lives;
    private int Score;
    int NormalBestScore = 0;
    int ArcadeBestScore = 0;
    boolean isArcade = false;
    private FileManager FileManager = new FileManager();
    private Save save = new Save();
    private Load load = new Load();

    private KTimer doublerTimer = new KTimer();
    private int doublerValue = 0;


    static GameEngine getInstance() {
        return ourInstance;
    }

    private GameEngine() {

    }

    void newGame() {
        Lives = 3;
        Score = 0;
        ActiveObjects = new ArrayList<>();
        ourInstance.loadGame();
        isArcade = false;
        doublerTimer.moveToTime(-1);
    }

    void newArcade() {
        Score = 0;
        ActiveObjects = new ArrayList<>();
        ourInstance.loadGame();
        isArcade = true;
    }

    @Override
    public GameObject createGameObject() {
        Factory myFactory = new Factory();
        GameObject Temp = myFactory.getRandomObject();
        ActiveObjects.add(Temp);
        return Temp;
    }

    GameObject createBomb() {
        Factory myFactory = new Factory();
        GameObject Temp = myFactory.getBomb();
        ActiveObjects.add(Temp);
        return Temp;
    }

    GameObject createSlice(double x, double y, Image Current) {

        GameObject Temp = new Slice(x, y, Current);
        Slices.add(Temp);
        return Temp;
    }

    @Override
    public void updateObjectsLocations(double time) {
        for (GameObject I :
                ActiveObjects) {
            if (I.hasMovedOffScreen() && I instanceof Bomb) {
                ActiveObjects.remove(I);
                break;
            }
            if (I.hasMovedOffScreen() && !(I instanceof SpecialFruit)) {
                Lives--;
                ActiveObjects.remove(I);
                break;
            } else
                I.move(time);

        }
        for (GameObject I :
                Slices) {
            if (I.hasMovedOffScreen()) {
                Slices.remove(I);
                break;
            } else
                I.move(time);
        }
    }

    @Override
    public void sliceObjects(GameObject Object) {
        if (!isArcade) {
            if (Object instanceof Bomb) {
                if (Object instanceof Fatal) {
                    Lives = 0;
                    doublerTimer.moveToTime(-1);
                } else if (Object instanceof Heart) {
                    if (Lives < 3) {
                        Lives++;
                    } else {
                        Score++;
                    }
                } else {
                    Lives--;
                }
                return;
            }
        } else {
            if (Object instanceof Bomb) {
                if (Object instanceof Fatal) {
                    Score -= 10;
                    if (Score < 0) {
                        Score = 0;
                    }
                    doublerTimer.moveToTime(-1);
                } else if (Object instanceof Heart) {
                    Score += 5;
                } else {
                    Score -= 5;
                    if (Score < 0) {
                        Score = 0;
                    }
                }
                return;
            }
        }
        if (Object instanceof DoubleScorer) {
            doublerTimer = new KTimer();
            doublerTimer.startTimer(15000, true);
            doublerValue += 2;
        }
        if (!Object.isSliced()) {
            ActiveObjects.remove(Object);
            Object.slice();
            Score += doublerValue == 0 ? 1 : doublerValue;
            if (isArcade) {
                if (Score > ArcadeBestScore) {
                    ArcadeBestScore = Score;
                    ourInstance.saveGame();
                }
            } else {

                if (Score > NormalBestScore) {
                    NormalBestScore = Score;
                    ourInstance.saveGame();
                }
            }

        }
    }

    int getLives() {
        return Lives;
    }

    int getScore() {
        return Score;
    }

    @SuppressWarnings("SameParameterValue")
    void combo(int amount) {
        Score += amount;
    }

    boolean isDoubling() {
        return doublerTimer.getTime() > 0;
    }

    int getDoublerValue() {
        return doublerValue;
    }

    void setDoublerValue() {
        this.doublerValue = 0;
    }

    @Override
    public void saveGame() {
        FileManager.Command(save);

    }

    @Override
    public void loadGame() {
        FileManager.Command(load);

    }

}
