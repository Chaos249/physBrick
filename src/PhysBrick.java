import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

public class PhysBrick extends Application {

    // start game immediately and disables music if true
    public final static boolean DEBUG = true;

    public static void main(String[] args) {
        Application.launch(args);
    }

    // javafx main start
    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {

        primaryStage.setTitle("");
        primaryStage.setFullScreen(false);
        primaryStage.setResizable(false);

        // music
        AudioClip music = new AudioClip(this.getClass().getResource("resources/sound/Untitled.wav").toString());
        music.setVolume(0.1);

        // makes the scene for the credits view
        TitleView tv = new TitleView(primaryStage);
        primaryStage.setScene(tv.titleScene);

        CreditsView cv = new CreditsView(primaryStage, tv.titleScene);
        SettingsView sv = new SettingsView(primaryStage, tv.titleScene);
        GameView gv = new GameView(primaryStage, tv.titleScene);

        tv.playButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                primaryStage.setScene(gv.gameScene);
            }
        });

        tv.settingsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                primaryStage.setScene(sv.settingsScene);
            }
        });

        tv.creditButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                primaryStage.setScene(cv.creditsScene);
            }
        });


    }
}