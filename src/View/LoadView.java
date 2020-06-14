package View;

import ElementsUtil.Utils;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;

public class LoadView {

    public Group root = new Group();
    public Scene loadScene;
    public AnimationTimer countdown;
    public  boolean game_on = false;

    public LoadView(Stage stage, Scene titleScene) {

        Scale scale = new Scale(PhysBrick.SCALE_FACTOR, PhysBrick.SCALE_FACTOR);
        scale.setPivotX(0);
        scale.setPivotY(0);
        titleScene.getRoot().getTransforms().setAll(scale);
        stage.setMaxHeight(Utils.WIDTH * PhysBrick.SCALE_FACTOR + 40);
        stage.setMaxWidth(Utils.HEIGHT * PhysBrick.SCALE_FACTOR);
        stage.setMinHeight(Utils.WIDTH * PhysBrick.SCALE_FACTOR + 40);
        stage.setMinWidth(Utils.WIDTH * PhysBrick.SCALE_FACTOR);

        loadScene = new Scene(root, Utils.WIDTH, Utils.HEIGHT, Color.BLACK);

        MakeLoadText();
        this.countdown = Countdown(stage, titleScene);
        stage.setScene(loadScene);
        stage.show();
    }

    public void MakeLoadText() {
        Text text = new Text();
        text.setText("LOADING");
        text.setFont(Font.loadFont(LoadView.class.getResource("/font/start.ttf").toString(), 30));
        text.setFill(Color.WHITE);
        text.setLayoutX(Utils.WIDTH * PhysBrick.SCALE_FACTOR * 0.5 - text.getBoundsInParent().getWidth() / 2);
        text.setTranslateY(Utils.HEIGHT * PhysBrick.SCALE_FACTOR * 0.5 - text.getBoundsInParent().getHeight() / 2);
        this.root.getChildren().add(text);
    }

    public AnimationTimer Countdown(Stage stage, Scene scene) {
        AnimationTimer at = new AnimationTimer() {
            int startTime = 383; // 380 is best
            int count = 1;
            @Override
            public void handle(long now) {
                if (count % startTime == 0 && game_on == false) {
                    stage.setScene(scene);
                    setGameOn();
                    mechanize();
                }
                count += 1;
            }
        };
        at.start();
        return at;
    }

    public void setGameOn() {
        this.game_on = true;
    }

    public void  mechanize() {
        if (game_on) {
            System.out.println("mechanized");
            this.countdown.stop();
        }
    }
}
