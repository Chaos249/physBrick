package View;

import Components.Brick;
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
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class LevelSelectView {

    public Scene levelSelectScene;
    public Group root;

    public ImageView selectLevelImage;

    public Button backButton;
    public Button levelButton;

    public GameView currentGame;

    public ArrayList<Button> levelSelection;

    public LevelSelectView(Stage primaryStage, Scene menuScene, ArrayList arr) throws FileNotFoundException {
        this.root = new Group();
        this.levelSelectScene = new Scene(this.root, Utils.WIDTH, Utils.HEIGHT, Color.BLACK);

        DisplayElements.EightiesAnim(PhysBrick.DEBUG, primaryStage, this.root, Color.BLUEVIOLET);

        this.selectLevelImage = MakeSelectLevelImage();
        this.backButton = initBackButton(primaryStage, menuScene);

        //this.levelButton = new View.LevelSelectButton(primaryStage, menuScene,this.root, 0, arr, 0, 0).levelButton;
        LevelSelectButton.MakeButtons(primaryStage, menuScene, this.root, arr);
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
        this.backButton.setOpacity(0.8);
        this.backButton.setPadding(Insets.EMPTY);

        this.backButton.setTranslateX(-190);
        this.backButton.setTranslateY(-40);

        this.backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                primaryStage.setScene(menuScene);
            }
        });

        this.root.getChildren().add(backButton);
        return backButton;
    }

    public ImageView MakeSelectLevelImage() throws FileNotFoundException {
        FileInputStream select_input = new FileInputStream("src/resources/image/select_level.jpg");
        Image image = new Image(select_input);
        ImageView imageView = new ImageView(image);

        imageView.setLayoutX((Utils.WIDTH / 2) - 790); // -1576 * 0.45
        imageView.setLayoutY(Utils.HEIGHT - 1200);
        imageView.setScaleX(0.45);
        imageView.setScaleY(0.45);
        imageView.setOpacity(0.8);
        this.root.getChildren().add(imageView);
        return imageView;
    }

    public static ArrayList<Brick> MakeBrickLayout1(Group root) {
        ArrayList<Brick> brickList = new ArrayList<>();
        // bottom row
        try { // for the custom font loading a try catch block is needed
            Brick brick1 = new Brick(Utils.WIDTH / 2, Utils.HEIGHT / 2 - 100, 50, 999, Color.CRIMSON, root);
            brick1.initDurabilityText(root);
            Brick brick2 = new Brick(Utils.WIDTH / 2 - 55, Utils.HEIGHT / 2 - 100, 50, 10, Color.WHITE, root);
            brick2.initDurabilityText(root);
            Brick brick3 = new Brick(Utils.WIDTH / 2 + 55, Utils.HEIGHT / 2 - 100, 50, 25, Color.WHITE, root);
            brick3.initDurabilityText(root);
            // top row
            Brick brick4 = new Brick(Utils.WIDTH / 2 + 55, Utils.HEIGHT / 2 - 155, 50, 5, Color.WHITE, root);
            brick4.initDurabilityText(root);
            Brick brick5 = new Brick(Utils.WIDTH / 2, Utils.HEIGHT / 2 - 155, 50, 5, Color.WHITE, root);
            brick5.initDurabilityText(root);
            Brick brick6 = new Brick(Utils.WIDTH / 2 - 55, Utils.HEIGHT / 2 - 155, 50, 5, Color.WHITE, root);
            brick6.initDurabilityText(root);
            brickList.add(brick1);
            brickList.add(brick2);
            brickList.add(brick3);
            brickList.add(brick4);
            brickList.add(brick5);
            brickList.add(brick6);
        } catch (FileNotFoundException e){
            System.out.println(e);
        }

        return brickList;
    }
}
