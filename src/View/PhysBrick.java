package View;

import ElementsUtil.DisplayElements;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class PhysBrick extends Application {

    // start game immediately and disables music if true
    public final static boolean DEBUG = true;

    public static double VOLUME = 0.5;

    public static void main(String[] args) {
        Application.launch(args);
    }

    // javafx main start
    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {

        primaryStage.setTitle("");
        primaryStage.setFullScreen(false);
        primaryStage.setResizable(false);

        Group defaultRoot = new Group();
        ArrayList arr = DisplayElements.EightiesAnim(DEBUG, primaryStage, defaultRoot, Color.CYAN);

        // makes the scene for the credits view
        TitleView tv = new TitleView(primaryStage);
        primaryStage.setScene(tv.titleScene);

        CreditsView cv = new CreditsView(primaryStage, tv.titleScene);

        SettingsView sv = new SettingsView(primaryStage, tv.titleScene);

        LevelSelectView lsv = new LevelSelectView(primaryStage, tv.titleScene, arr);

        tv.playButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                primaryStage.setScene(lsv.levelSelectScene);
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