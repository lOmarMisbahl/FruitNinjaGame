import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class MenuController implements Initializable {

    @FXML
    private Button Switching;
    @FXML
    private Button Exiting;
    @FXML
    private Button SwitchingArcade;
    private GameEngine myGameEngine = GameEngine.getInstance();

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

    public void initialize(URL url, ResourceBundle rb) {
        myBackGroundTask = new BackgroundTask();
        myBackGroundTask.run();
    }

    @FXML
    private void Game() {
        Stage mainStage = (Stage) Switching.getScene().getWindow();
        myGameEngine.newGame();
        myBackGroundTask.stop();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("GUI/Game.fxml"));
            Scene scene = new Scene(root);
            mainStage.setScene(scene);
        } catch (Exception ignored) {
        }
    }

    @FXML
    private void Exit() {
        Stage mainStage = (Stage) Exiting.getScene().getWindow();
        myBackGroundTask.stop();
        try {
            mainStage.close();
        } catch (Exception ignored) {
        }
    }

    @FXML
    private void ArcadeGame() {
        Stage mainStage = (Stage) SwitchingArcade.getScene().getWindow();
        myGameEngine.newArcade();
        myBackGroundTask.stop();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("GUI/Game.fxml"));
            Scene scene = new Scene(root);
            mainStage.setScene(scene);
        } catch (Exception ignored) {
        }
    }
}
