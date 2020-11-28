package model.Interfaces;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public interface IMakeNoiseGuard {
    String musicFile = "src/asserts/sounds/bomb.mp3";     // For example
    Media sound = new Media(new File(musicFile).toURI().toString());
    MediaPlayer mediaPlayer = new MediaPlayer(sound);
    void makeNoise();
}
