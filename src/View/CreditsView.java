package View;

import ElementsUtil.DisplayElements;
import ElementsUtil.Utils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import static View.PhysBrick.VOLUME;
import static View.PhysBrick.buttonSound;

public class CreditsView {

    public Scene creditsScene;

    public Group root;

    public Text authorText;
    public Text userText;
    public Text miscText;

    public Button backButton;

    public CreditsView(Stage primaryStage, Scene menuScene, ArrayList arr, boolean titleView) throws FileNotFoundException {

        this.root = new Group();
        this.creditsScene = new Scene(root, Utils.WIDTH, Utils.HEIGHT, Color.BLACK);

        if (titleView) { // shitty way to do this but it fixes the 80s graphic not appearing after game completion bug
            DisplayElements.EightiesAnim(PhysBrick.DEBUG, primaryStage, this.root, Color.RED);
        } else {
            DisplayElements.AddEightiesMatrixRoot(this.root, arr);
            DisplayElements.ChangeLinesColor(Color.RED, (ArrayList<Line>) arr.get(0), (ArrayList<Line>) arr.get(1));
        }

        this.authorText = new Text("AUTHOR: KYLE YAGLOSKI");
        this.authorText.setOpacity(0.8);
        this.authorText.setFont(Font.loadFont(new FileInputStream("src/resources/start.ttf"), 25));
        this.authorText.setFill(Color.WHITE);
        this.authorText.setTranslateX((Utils.WIDTH / 2) - 275);
        this.authorText.setTranslateY((Utils.HEIGHT / 2) - 400);

        this.userText = new Text("AKA \"CHAOS249\"");
        this.userText.setOpacity(0.8);
        this.userText.setFont(Font.loadFont(new FileInputStream("src/resources/start.ttf"), 25));
        this.userText.setFill(Color.WHITE);
        this.userText.setTranslateX((Utils.WIDTH / 2) - 185);
        this.userText.setTranslateY((Utils.HEIGHT / 2) - 350);

        this.miscText = new Text("PATREON:");

        this.backButton = initBackButton(primaryStage, menuScene);

        root.getChildren().add(authorText);
        root.getChildren().add(userText);
        root.getChildren().add(miscText);
        root.getChildren().add(backButton);
    }

    // creates credit button
    public static Button MakeCreditButton(Group root) throws FileNotFoundException {
        final Button btn = new Button();
        FileInputStream play_input = new FileInputStream("src/resources/image/credit.jpg");
        Image play_img = new Image(play_input);
        ImageView btngraphic = new ImageView(play_img);
        double btnScale = 0.6;

        btn.setLayoutX((Utils.WIDTH / 2) - 282); // /6.9
        btn.setLayoutY((Utils.HEIGHT) - 185); // -250
        btn.setGraphic(btngraphic);
        btn.setScaleX(btnScale);
        btn.setScaleY(btnScale);
        btn.setOpacity(0.8);
        btn.setPadding(Insets.EMPTY);

        root.getChildren().add(btn);
        return btn;
    }

    public Button initBackButton(Stage primaryStage, Scene menuScene) throws FileNotFoundException {
        this.backButton = new Button();
        FileInputStream play_input = new FileInputStream("src/resources/image/back.jpg");
        Image play_img = new Image(play_input);
        ImageView btngraphic = new ImageView(play_img);

        double btnScale = 0.4;
        this.backButton.setGraphic(btngraphic);
        this.backButton.setScaleX(btnScale);
        this.backButton.setScaleY(btnScale);
        this.backButton.setOpacity(0.85);
        this.backButton.setPadding(Insets.EMPTY);

        this.backButton.setTranslateX(-190);
        this.backButton.setTranslateY(-40);

        this.backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                primaryStage.setScene(menuScene);
                buttonSound.setVolume(VOLUME * 1.25);
                buttonSound.play();
            }
        });

        return backButton;
    }
}
