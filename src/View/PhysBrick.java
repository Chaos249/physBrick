package View;

import ElementsUtil.DisplayElements;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

import java.util.ArrayList;

public class PhysBrick extends Application {
    // start game immediately and disables music if true
    public final static boolean DEBUG = false;
    public final static float SCALE_FACTOR = 0.8f;
    public static double VOLUME = 0.25;

    //public static AudioClip buttonSound = new AudioClip("file:resources/sound/button.wav");//
    public static AudioClip buttonSound = new AudioClip(PhysBrick.class.getResource("/sound/button.wav").toString());

    public static void main(String[] args) {
        Application.launch(args);
    }

    // javafx main start
    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
        System.out.println("starting");

        primaryStage.setTitle("");
        primaryStage.setFullScreen(false);
        primaryStage.setResizable(true);

        Group defaultRoot = new Group();
        ArrayList arr = DisplayElements.EightiesAnim(DEBUG, primaryStage, defaultRoot, Color.CYAN);

        // makes the scene for the credits view
        TitleView tv = new TitleView(primaryStage);
        primaryStage.setScene(tv.titleScene);
        if (DEBUG) { VOLUME = 0.05; }
        else { LoadView loadView = new LoadView(primaryStage, tv.titleScene); }

        CreditsView cv = new CreditsView(primaryStage, tv.titleScene, arr, true);

        SettingsView sv = new SettingsView(primaryStage, tv.titleScene);

        LevelView lsv = new LevelView(primaryStage, tv.titleScene, arr);

        tv.playButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                primaryStage.setScene(lsv.levelSelectScene);
                buttonSound.setVolume(VOLUME * 1.25);
                buttonSound.play();
            }
        });

        tv.settingsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                primaryStage.setScene(sv.settingsScene);
                buttonSound.setVolume(VOLUME * 1.25);
                buttonSound.play();
            }
        });

        tv.creditButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                primaryStage.setScene(cv.creditsScene);
                buttonSound.setVolume(VOLUME * 1.25);
                buttonSound.play();
                DisplayElements.CustomLightning(cv.creditsScene, cv.root, 4000, 60, 0.4f);
            }
        });
    }
}