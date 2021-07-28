import GameObjects.GameObject;
import GameObjects.TimeFreezer;
import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class GameController implements Initializable {


    public Label timer;
    private KTimer kTimer = new KTimer();
    private KTimer comboTime = new KTimer();
    private KTimer freezeTime = new KTimer();
    public Label comboLabel;
    public Label freezerLabel;
    public Label doublerLabel;

    private boolean freezed = false;
    @FXML
    private AnchorPane Anchor;
    private GameEngine myGameEngine;
    private static boolean init = false;

    @FXML
    private ImageView Live3;

    @FXML
    private ImageView Live2;

    @FXML
    private ImageView Live1;

    @FXML
    private Label Score;

    @FXML
    private Label BestScore;

    @FXML
    private Button ReturnToMenuB;

    class BackgroundTask implements Runnable {
        Sound BackgroundMusic;

        @Override
        public void run() {
            BackgroundMusic = new Sound(System.getProperty("user.dir") + "/src/Sound/Background.mp3", true);
            BackgroundMusic.play();
        }

        void stop() {
            BackgroundMusic.stop();
        }
    }

    private BackgroundTask myBackGroundTask;
    private AnimationTimer gameLoop;
    private Map<ImageView, GameObject> myObjects = new HashMap<>();
    private ArrayList<ImageView> ImageViewArray = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        myGameEngine = GameEngine.getInstance();
        if (!init) {

            init = true;
        }
        if (url.toString().contains("Game")) {

            myBackGroundTask = new BackgroundTask();
            myBackGroundTask.run();

            if (myGameEngine.isArcade) {
                Arcade();
            } else {
                Normal();
            }
            gameLoop.start();

        }
    }

    private void Arcade() {
        comboLabel.setVisible(false);
        freezerLabel.setVisible(false);
        doublerLabel.setVisible(false);

        gameLoop = new AnimationTimer() {
            long lastNanoTime;
            boolean runonce = false;
            double timecalc = 0;
            double bombrate = 0;
            double difficulty = 5;
            int fps = 60;

            public void handle(long currentNanoTime) {

                if (!runonce) {
                    lastNanoTime = System.nanoTime();
                    kTimer.startTimer(60000, true);

                    newFruit();
                    runonce = true;

                }
                double elapsedTime = (currentNanoTime - lastNanoTime) / 1000000000.0;
                //noinspection IntegerDivisionInFloatingPointContext
                if (elapsedTime >= (1 / fps)) {
                    //System.out.println("Frame took " + elapsedTime);
                    lastNanoTime = currentNanoTime;
                    if (kTimer.getTime() <= 0) {
                        for (ImageView I : ImageViewArray) {
                            Anchor.getChildren().remove(I);
                        }
                        ImageViewArray.clear();
                        myObjects.clear();
                        kTimer.stopTimer();
                        this.stop();
                        Notification("Game Over !");
                    }
                    hideLives();
                    Score.setText(String.valueOf(myGameEngine.getScore()));
                    BestScore.setText("BEST:" + myGameEngine.ArcadeBestScore);
                    timer.setText(kTimer.getSspTime().get().substring(0, 5));
                    checkSpecials();
                    timecalc += elapsedTime;
                    bombrate += elapsedTime;
                    if (timecalc > 1) {
                        newFruit();
                        timecalc = 0;
                    }
                    if (bombrate > difficulty) {
                        newBomb();
                        difficulty *= 0.99;
                        bombrate = 0;
                    }
                    update(elapsedTime);
                }

            }
        };

    }

    private void Normal() {
        myGameEngine.newGame();
        comboLabel.setVisible(false);
        freezerLabel.setVisible(false);
        doublerLabel.setVisible(false);


        gameLoop = new AnimationTimer() {
            long lastNanoTime;
            boolean runonce = false;
            double timecalc = 0;
            double bombrate = 0;
            double difficulty = 5;
            int fps = 60;

            public void handle(long currentNanoTime) {


                if (!runonce) {
                    lastNanoTime = System.nanoTime();
                    kTimer.startTimer(0, false);
                    newFruit();
                    runonce = true;

                }
                double elapsedTime = (currentNanoTime - lastNanoTime) / 1000000000.0;
                //noinspection IntegerDivisionInFloatingPointContext
                if (elapsedTime >= (1 / fps)) {
                    lastNanoTime = currentNanoTime;
                    if (myGameEngine.getLives() == 0) {
                        for (ImageView I : ImageViewArray) {
                            Anchor.getChildren().remove(I);
                        }
                        ImageViewArray.clear();
                        myObjects.clear();
                        kTimer.stopTimer();
                        timer.setText("00:00");

                        this.stop();
                        Notification("Game Over !");
                    }
                    showLives();
                    Score.setText(String.valueOf(myGameEngine.getScore()));
                    BestScore.setText("BEST:" + myGameEngine.NormalBestScore);
                    timer.setText(kTimer.getSspTime().get().substring(0, 5));
                    checkSpecials();
                    timecalc += elapsedTime;
                    bombrate += elapsedTime;
                    if (timecalc > 1) {
                        newFruit();
                        timecalc = 0;
                    }
                    if (bombrate > difficulty) {
                        newBomb();
                        difficulty *= 0.96;
                        bombrate = 0;
                    }
                    update(elapsedTime);


                }
            }
        };

    }

    private void newFruit() {
        GameObject Fruit = myGameEngine.createGameObject();
        ImageView FruitView;
        FruitView = new ImageView(Fruit.getImages()[0]);
        FruitView.setFitHeight(50);
        FruitView.setFitWidth(50);
        FruitView.setPreserveRatio(true);
        myObjects.put(FruitView, Fruit);
        ImageViewArray.add(FruitView);
        Anchor.getChildren().add(FruitView);
        FruitView.setLayoutX(0);
        FruitView.setLayoutY(Anchor.getHeight() + 50);
        if (freezed) {
            Fruit.slowFruit();
        }
        FruitView.setOnMouseEntered(this::Slice);


    }

    private void newBomb() {
        GameObject Fruit = myGameEngine.createBomb();
        ImageView FruitView;
        FruitView = new ImageView(Fruit.getImages()[0]);
        FruitView.setFitHeight(50);
        FruitView.setFitWidth(50);
        myObjects.put(FruitView, Fruit);
        ImageViewArray.add(FruitView);
        Anchor.getChildren().add(FruitView);
        FruitView.setLayoutX(0);
        FruitView.setLayoutY(Anchor.getHeight() + 50);
        if (freezed) {
            Fruit.slowFruit();
        }
        FruitView.setOnMouseEntered(this::Slice);

    }

    private void hideLives() {
        Live1.setVisible(false);
        Live2.setVisible(false);
        Live3.setVisible(false);
    }

    private void checkSpecials() {
        if (freezeTime.getTime() > 0) {
            freezerLabel.setVisible(true);
            freezerLabel.setText("FREEZE:" + freezeTime.getSspTime().get().substring(3, 5));
        } else if (freezeTime.getTime() <= 0 && freezed) {
            for (GameObject object :
                    myObjects.values()) {
                object.backToNormal();
            }
            freezeTime.stopTimer();
            freezerLabel.setVisible(false);
            freezed = false;
        } else {
            freezeTime.stopTimer();
            freezerLabel.setVisible(false);
        }

        if (myGameEngine.isDoubling()) {
            doublerLabel.setVisible(true);
            doublerLabel.setText("X " + myGameEngine.getDoublerValue());
        } else {
            myGameEngine.setDoublerValue();
            doublerLabel.setVisible(false);
        }
    }

    private void showLives() {
        if (myGameEngine.getLives() == 0) {

            myGameEngine.newGame();
            Live1.setVisible(false);
            Live2.setVisible(false);
            Live3.setVisible(false);
        } else if (myGameEngine.getLives() == 1) {
            Live1.setVisible(true);
            Live2.setVisible(false);
            Live3.setVisible(false);
        } else if (myGameEngine.getLives() == 2) {
            Live1.setVisible(true);
            Live2.setVisible(true);
            Live3.setVisible(false);
        } else if (myGameEngine.getLives() == 3) {
            Live1.setVisible(true);
            Live2.setVisible(true);
            Live3.setVisible(true);
        }
    }

    private void Slice(MouseEvent event) {

        if (comboTime.getTime() > 0) {
            myGameEngine.combo(2);
            comboLabel.setVisible(true);
        } else {
            comboTime = new KTimer();
            comboTime.startTimer(300, true);
            comboLabel.setVisible(false);
        }
        ImageView Current = (ImageView) (event.getSource());
        fade(Current);
        GameObject CurrentObject = myObjects.get(Current);
        try {
            newSlice(Current.getLayoutX() - 20, Current.getLayoutY(), CurrentObject.getImages()[1]);
            newSlice(Current.getLayoutX() + 20, Current.getLayoutY(), CurrentObject.getImages()[2]);

        } catch (Exception ignored) {
        }
        if (CurrentObject instanceof TimeFreezer) {
            freezed = true;
            freezeTime = new KTimer();
            freezeTime.startTimer(15000, true);
            freezerLabel.setVisible(true);
            for (GameObject object :
                    myObjects.values()) {
                object.slowFruit();
            }
        }

        myGameEngine.sliceObjects(CurrentObject);
        Anchor.getChildren().remove(Current);
        ImageViewArray.remove(Current);
        myObjects.remove(Current);
        Sound sound = new Sound(System.getProperty("user.dir") + "/src/Sound/Slice.mp3");
        sound.play();


    }

    private void newSlice(double x, double y, Image Current) {
        GameObject Fruit = myGameEngine.createSlice(x, y, Current);
        ImageView FruitView;
        FruitView = new ImageView(Current);
        FruitView.setFitHeight(50);
        FruitView.setFitWidth(25);
        ImageViewArray.add(FruitView);
        myObjects.put(FruitView, Fruit);
        Anchor.getChildren().add(FruitView);
        FruitView.setLayoutX(x);
        FruitView.setLayoutY(y);


    }

    private void update(double elapsedTime) {

        myGameEngine.updateObjectsLocations(elapsedTime);
        for (ImageView I :
                ImageViewArray) {
            GameObject J = myObjects.get(I);
            I.setLayoutX(J.getXlocation());
            I.setLayoutY(J.getYlocation());
            I.getTransforms().add(new Rotate(1, 0, 0));
        }
        for (ImageView I :
                ImageViewArray) {
            GameObject J = myObjects.get(I);
            if (J.hasMovedOffScreen()) {
                myObjects.remove(I);
                ImageViewArray.remove(I);
                Anchor.getChildren().remove(I);
                break;
            }
        }
    }

    private void fade(ImageView FruitView) {
        FadeTransition ft = new FadeTransition(Duration.millis(100), FruitView);
        ft.setFromValue(1.0);
        ft.setToValue(0.1);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();

    }

    @FXML
    private void ReturnToMenu() {
        gameLoop.stop();
        myBackGroundTask.stop();
        Stage mainStage = (Stage) ReturnToMenuB.getScene().getWindow();

        try {
            Parent root = FXMLLoader.load(getClass().getResource("GUI/Menu.fxml"));
            Scene scene = new Scene(root);
            mainStage.setScene(scene);
        } catch (Exception ignored) {
        }
    }

    @SuppressWarnings("SameParameterValue")
    private void Notification(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, message, ButtonType.OK);
        alert.show();
        alert.getResult();
    }

    @FXML
    void mouseClicked() {
    }

    @FXML
    void mouseReleased() {
    }
}