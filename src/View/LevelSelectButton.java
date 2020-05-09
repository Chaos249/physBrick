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
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import static View.PhysBrick.VOLUME;
import static View.PhysBrick.buttonSound;

public class LevelSelectButton {

    public Text levelNumberText;

    public Button levelButton;

    public ArrayList<Brick> gameLayout;

    public LevelSelectButton(Stage primaryStage, Scene menuScene, Group root, int levelNumber, ArrayList arr, int posX, int posY) throws FileNotFoundException {
        this.levelButton = new Button();
        this.levelNumberText = new Text(Integer.toString(levelNumber));
        this.levelNumberText.setFill(Color.WHITE);
        this.levelNumberText.setFont(Font.loadFont(new FileInputStream("src/resources/start.ttf"), 12)); // source code pro
        this.levelNumberText.setMouseTransparent(true);
        this.levelNumberText.setOpacity(0.8);

        FileInputStream box_input = new FileInputStream("src/resources/image/level_box.jpg");
        Image box_img = new Image(box_input);
        ImageView btngraphic = new ImageView(box_img);

        this.levelButton.setGraphic(btngraphic);

        this.levelButton.setScaleX(0.72); // 0.3
        this.levelButton.setScaleY(0.72); // 0.3

        this.levelButton.setTranslateX(posX);
        this.levelButton.setTranslateY(posY);

        this.levelNumberText.setScaleX(6);
        this.levelNumberText.setScaleY(6);
        this.levelNumberText.setTranslateX(posX + 80); // 201.5
        this.levelNumberText.setTranslateY(posY + 97); // 220

        this.levelButton.setPadding(Insets.EMPTY);

        root.getChildren().add(levelButton);
        root.getChildren().add(levelNumberText);

        levelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    GameView gv = new GameView(primaryStage, menuScene, levelNumber);

                    DisplayElements.AddEightiesMatrixRoot(gv.root, arr);
                    DisplayElements.ChangeLinesColor(Utils.RandomColor(), (ArrayList<Line>) arr.get(0), (ArrayList<Line>) arr.get(1));
                    primaryStage.setScene(gv.gameScene);
                    buttonSound.setVolume(VOLUME * 1.25);
                    buttonSound.play();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static ArrayList<Button> MakeButtons(Stage primaryStage, Scene menuScene, Group root, ArrayList graphic) throws FileNotFoundException {
        ArrayList<Button> buttons = new ArrayList<>();
        int levelAmount = 5; // only doing 5 levels for now

        int posX = 254;
        int posY = 280;

        int xOffset = 150;
        int yOffset = 150;

        int levelNumber = 1;
        for (int i = 1; i <= levelAmount; i++) { // only doing 5 levels for now
            LevelSelectButton butt = new LevelSelectButton(primaryStage, menuScene, root, levelNumber, graphic, posX, posY);
            posX += xOffset;
            if (i % 5 == 0) {
                posY += yOffset;
                posX = 254;
            }
            levelNumber += 1;
            buttons.add(butt.levelButton);
        }

        return buttons;
    }
}
