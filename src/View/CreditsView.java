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
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class CreditsView {

    Scene creditsScene;

    Group root;

    Text authorText;
    Text musicText;
    Text miscText;

    Button backButton;

    public CreditsView(Stage primaryStage, Scene menuScene) throws FileNotFoundException {

        this.root = new Group();
        this.creditsScene = new Scene(root, Utils.WIDTH, Utils.HEIGHT, Color.BLACK);

        DisplayElements.EightiesAnim(PhysBrick.DEBUG, primaryStage, this.root, Color.RED);

        this.authorText = new Text("Kyle Yagloski");

        this.musicText = new Text("Placeholder");

        this.miscText = new Text("Placeholder");

        this.backButton = initBackButton(primaryStage, menuScene);

        root.getChildren().add(authorText);
        root.getChildren().add(musicText);
        root.getChildren().add(miscText);
        root.getChildren().add(backButton);
    }

    // creates credit button
    public static Button MakeCreditButton(Group root) throws FileNotFoundException {
        final Button btn = new Button();
        FileInputStream play_input = new FileInputStream("src/resources/image/credit.jpg");
        Image play_img = new Image(play_input);
        ImageView btngraphic = new ImageView(play_img);
        double btnScale = 0.3;

        btn.setLayoutX((Utils.WIDTH / 6.9));
        btn.setLayoutY((Utils.HEIGHT - 250));
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
            }
        });

        return backButton;
    }
}
