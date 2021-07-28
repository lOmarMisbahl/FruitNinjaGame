import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import java.io.File;


public class Sound {
    private String status;

    private MediaPlayer mediaPlayer;

    Sound(String filePath) {
        //System.out.println(filePath);
        Media hit = new Media(new File(filePath).toURI().toString());
        mediaPlayer = new MediaPlayer(hit);
        mediaPlayer.setAutoPlay(false);
    }

    Sound(String filePath, boolean always) {
        //  System.out.println(filePath);
        Media hit = new Media(new File(filePath).toURI().toString());
        mediaPlayer = new MediaPlayer(hit);
        if (always)
            mediaPlayer.setOnEndOfMedia(() -> mediaPlayer.seek(Duration.ZERO));
    }


    void play() {
        mediaPlayer.play();
        status = "play";
    }


    @SuppressWarnings("unused")
    public void pause() {
        if (status.equals("paused")) {
            System.out.println("audio is already paused");
            return;
        }
        mediaPlayer.pause();
        status = "paused";
    }


    void stop() {
        mediaPlayer.stop();
    }

}
