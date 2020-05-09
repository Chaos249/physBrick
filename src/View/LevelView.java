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

import static View.PhysBrick.VOLUME;
import static View.PhysBrick.buttonSound;

public class LevelView {

    public Scene levelSelectScene;
    public Group root;

    public ImageView selectLevelImage;

    public Button backButton;

    public LevelView(Stage primaryStage, Scene menuScene, ArrayList arr) throws FileNotFoundException {
        this.root = new Group();
        this.levelSelectScene = new Scene(this.root, Utils.WIDTH, Utils.HEIGHT, Color.BLACK);

        DisplayElements.EightiesAnim(PhysBrick.DEBUG, primaryStage, this.root, Color.BLUEVIOLET);

        this.selectLevelImage = MakeSelectLevelImage();
        this.backButton = initBackButton(primaryStage, menuScene);

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
                buttonSound.setVolume(VOLUME * 1.25);
                buttonSound.play();
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

        try { // for the custom font loading a try catch block is needed

            // bottom row
            Brick brick1 = new Brick(Utils.WIDTH / 2, Utils.HEIGHT / 2 - 265, 52, 10, Color.WHITE, root);
            brick1.initDurabilityText(root);
            Brick brick2 = new Brick(Utils.WIDTH / 2 - 55, Utils.HEIGHT / 2 - 265, 52, 10, Color.WHITE, root);
            brick2.initDurabilityText(root);
            Brick brick3 = new Brick(Utils.WIDTH / 2 + 55, Utils.HEIGHT / 2 - 265, 52, 10, Color.WHITE, root);
            brick3.initDurabilityText(root);
            Brick brick9 = new Brick(Utils.WIDTH / 2 - 605, Utils.HEIGHT / 2 - 265, 52, 5, Color.WHITE, root);
            brick9.initDurabilityText(root);
            Brick brick10 = new Brick(Utils.WIDTH / 2 - 550, Utils.HEIGHT / 2 - 265, 52, 5, Color.WHITE, root);
            brick10.initDurabilityText(root);
            Brick brick12 = new Brick(Utils.WIDTH / 2 - 110, Utils.HEIGHT / 2 - 265, 52, 10, Color.WHITE, root);
            brick12.initDurabilityText(root);
            Brick brick21 = new Brick(Utils.WIDTH / 2 + 550, Utils.HEIGHT / 2 - 265, 52, 5, Color.WHITE, root);
            brick21.initDurabilityText(root);
            Brick brick22 = new Brick(Utils.WIDTH / 2 + 495, Utils.HEIGHT / 2 - 265, 52, 5, Color.WHITE, root);
            brick22.initDurabilityText(root);

            // top row
            Brick brick4 = new Brick(Utils.WIDTH / 2 + 55, Utils.HEIGHT / 2 - 320, 52, 10, Color.WHITE, root);
            brick4.initDurabilityText(root);
            Brick brick5 = new Brick(Utils.WIDTH / 2, Utils.HEIGHT / 2 - 320, 52, 10, Color.WHITE, root);
            brick5.initDurabilityText(root);
            Brick brick6 = new Brick(Utils.WIDTH / 2 - 55, Utils.HEIGHT / 2 - 320, 52, 10, Color.WHITE, root);
            brick6.initDurabilityText(root);
            Brick brick7 = new Brick(Utils.WIDTH / 2 - 605, Utils.HEIGHT / 2 - 320, 52, 5, Color.WHITE, root);
            brick7.initDurabilityText(root);
            Brick brick8 = new Brick(Utils.WIDTH / 2 - 550, Utils.HEIGHT / 2 - 320, 52, 5, Color.WHITE, root);
            brick8.initDurabilityText(root);
            Brick brick11 = new Brick(Utils.WIDTH / 2 - 110, Utils.HEIGHT / 2 - 320, 52, 10, Color.WHITE, root);
            brick11.initDurabilityText(root);
            Brick brick19 = new Brick(Utils.WIDTH / 2 + 550, Utils.HEIGHT / 2 - 320, 52, 5, Color.WHITE, root);
            brick19.initDurabilityText(root);
            Brick brick23 = new Brick(Utils.WIDTH / 2 + 495, Utils.HEIGHT / 2 - 320, 52, 5, Color.WHITE, root);
            brick23.initDurabilityText(root);

            // top top row
            Brick brick13 = new Brick(Utils.WIDTH / 2 + 55, Utils.HEIGHT / 2 - 375, 52, 10, Color.WHITE, root);
            brick13.initDurabilityText(root);
            Brick brick14 = new Brick(Utils.WIDTH / 2, Utils.HEIGHT / 2 - 375, 52, 10, Color.WHITE, root);
            brick14.initDurabilityText(root);
            Brick brick15 = new Brick(Utils.WIDTH / 2 - 55, Utils.HEIGHT / 2 - 375, 52, 10, Color.WHITE, root);
            brick15.initDurabilityText(root);
            Brick brick16 = new Brick(Utils.WIDTH / 2 - 605, Utils.HEIGHT / 2 - 375, 52, 5, Color.WHITE, root);
            brick16.initDurabilityText(root);
            Brick brick17 = new Brick(Utils.WIDTH / 2 - 550, Utils.HEIGHT / 2 - 375, 52, 5, Color.WHITE, root);
            brick17.initDurabilityText(root);
            Brick brick18 = new Brick(Utils.WIDTH / 2 - 110, Utils.HEIGHT / 2 - 375, 52, 10, Color.WHITE, root);
            brick18.initDurabilityText(root);
            Brick brick20 = new Brick(Utils.WIDTH / 2 + 550, Utils.HEIGHT / 2 - 375, 52, 5, Color.WHITE, root);
            brick20.initDurabilityText(root);
            Brick brick24 = new Brick(Utils.WIDTH / 2 + 495, Utils.HEIGHT / 2 - 375, 52, 5, Color.WHITE, root);
            brick24.initDurabilityText(root);

            brickList.add(brick1);
            brickList.add(brick2);
            brickList.add(brick3);
            brickList.add(brick4);
            brickList.add(brick5);
            brickList.add(brick6);
            brickList.add(brick7);
            brickList.add(brick8);
            brickList.add(brick9);
            brickList.add(brick10);
            brickList.add(brick11);
            brickList.add(brick12);
            brickList.add(brick13);
            brickList.add(brick14);
            brickList.add(brick15);
            brickList.add(brick16);
            brickList.add(brick17);
            brickList.add(brick18);
            brickList.add(brick19);
            brickList.add(brick20);
            brickList.add(brick21);
            brickList.add(brick22);
            brickList.add(brick23);
            brickList.add(brick24);

        } catch (FileNotFoundException e){
            System.out.println(e);
        }

        return brickList;
    }

    public static ArrayList<Brick> MakeBrickLayout2(Group root) {
        ArrayList<Brick> brickList = new ArrayList<>();

        try { // for the custom font loading a try catch block is needed

            // bottom row
            Brick brick1 = new Brick(Utils.WIDTH / 2, Utils.HEIGHT / 2 - 265, 52, 10, Color.WHITE, root);
            brick1.initDurabilityText(root);
            Brick brick2 = new Brick(Utils.WIDTH / 2 - 55, Utils.HEIGHT / 2 - 265, 52, 10, Color.WHITE, root);
            brick2.initDurabilityText(root);
            Brick brick3 = new Brick(Utils.WIDTH / 2 + 55, Utils.HEIGHT / 2 - 265, 52, 10, Color.WHITE, root);
            brick3.initDurabilityText(root);
            Brick brick7 = new Brick(Utils.WIDTH / 2 - 110, Utils.HEIGHT / 2 - 265, 52, 10, Color.WHITE, root);
            brick7.initDurabilityText(root);
            Brick brick27 = new Brick(Utils.WIDTH / 2 + 110, Utils.HEIGHT / 2 - 265, 52, 10, Color.WHITE, root);
            brick27.initDurabilityText(root);
            Brick brick28 = new Brick(Utils.WIDTH / 2 - 165, Utils.HEIGHT / 2 - 265, 52, 10, Color.WHITE, root);
            brick28.initDurabilityText(root);
            Brick brick29 = new Brick(Utils.WIDTH / 2 + 550, Utils.HEIGHT / 2 - 265, 52, 10, Color.WHITE, root);
            brick29.initDurabilityText(root);
            Brick brick30 = new Brick(Utils.WIDTH / 2 - 605, Utils.HEIGHT / 2 - 265, 52, 10, Color.WHITE, root);
            brick30.initDurabilityText(root);
            Brick brick31 = new Brick(Utils.WIDTH / 2 + 495, Utils.HEIGHT / 2 - 265, 52, 10, Color.WHITE, root);
            brick31.initDurabilityText(root);
            Brick brick32 = new Brick(Utils.WIDTH / 2 - 550, Utils.HEIGHT / 2 - 265, 52, 10, Color.WHITE, root);
            brick32.initDurabilityText(root);
            Brick brick33 = new Brick(Utils.WIDTH / 2 + 440, Utils.HEIGHT / 2 - 265, 52, 10, Color.WHITE, root);
            brick33.initDurabilityText(root);
            Brick brick34 = new Brick(Utils.WIDTH / 2 - 495, Utils.HEIGHT / 2 - 265, 52, 10, Color.WHITE, root);
            brick34.initDurabilityText(root);

            // top row
            Brick brick4 = new Brick(Utils.WIDTH / 2 + 55, Utils.HEIGHT / 2 - 320, 52, 10, Color.WHITE, root);
            brick4.initDurabilityText(root);
            Brick brick5 = new Brick(Utils.WIDTH / 2, Utils.HEIGHT / 2 - 320, 52, 10, Color.WHITE, root);
            brick5.initDurabilityText(root);
            Brick brick6 = new Brick(Utils.WIDTH / 2 - 55, Utils.HEIGHT / 2 - 320, 52, 10, Color.WHITE, root);
            brick6.initDurabilityText(root);
            Brick brick8 = new Brick(Utils.WIDTH / 2 - 110, Utils.HEIGHT / 2 - 320, 52, 10, Color.WHITE, root);
            brick8.initDurabilityText(root);
            Brick brick23 = new Brick(Utils.WIDTH / 2 - 165, Utils.HEIGHT / 2 - 320, 52, 10, Color.WHITE, root);
            brick23.initDurabilityText(root);
            Brick brick24 = new Brick(Utils.WIDTH / 2 - 220, Utils.HEIGHT / 2 - 320, 52, 10, Color.WHITE, root);
            brick24.initDurabilityText(root);
            Brick brick25 = new Brick(Utils.WIDTH / 2 + 110, Utils.HEIGHT / 2 - 320, 52, 10, Color.WHITE, root);
            brick25.initDurabilityText(root);
            Brick brick26 = new Brick(Utils.WIDTH / 2 + 165, Utils.HEIGHT / 2 - 320, 52, 10, Color.WHITE, root);
            brick26.initDurabilityText(root);
            Brick brick35 = new Brick(Utils.WIDTH / 2 - 605, Utils.HEIGHT / 2 - 320, 52, 10, Color.WHITE, root);
            brick35.initDurabilityText(root);
            Brick brick36 = new Brick(Utils.WIDTH / 2 + 550, Utils.HEIGHT / 2 - 320, 52, 10, Color.WHITE, root);
            brick36.initDurabilityText(root);
            Brick brick37 = new Brick(Utils.WIDTH / 2 + 495, Utils.HEIGHT / 2 - 320, 52, 10, Color.WHITE, root);
            brick37.initDurabilityText(root);
            Brick brick38 = new Brick(Utils.WIDTH / 2 - 550, Utils.HEIGHT / 2 - 320, 52, 10, Color.WHITE, root);
            brick38.initDurabilityText(root);

            // top top row
            Brick brick9 = new Brick(Utils.WIDTH / 2 + 55, Utils.HEIGHT / 2 - 375, 52, 10, Color.WHITE, root);
            brick9.initDurabilityText(root);
            Brick brick10 = new Brick(Utils.WIDTH / 2, Utils.HEIGHT / 2 - 375, 52, 10, Color.WHITE, root);
            brick10.initDurabilityText(root);
            Brick brick11 = new Brick(Utils.WIDTH / 2 - 55, Utils.HEIGHT / 2 - 375, 52, 10, Color.WHITE, root);
            brick11.initDurabilityText(root);
            Brick brick12 = new Brick(Utils.WIDTH / 2 - 110, Utils.HEIGHT / 2 - 375, 52, 10, Color.WHITE, root);
            brick12.initDurabilityText(root);
            Brick brick21 = new Brick(Utils.WIDTH / 2 + 110, Utils.HEIGHT / 2 - 375, 52, 10, Color.WHITE, root);
            brick21.initDurabilityText(root);
            Brick brick22 = new Brick(Utils.WIDTH / 2 - 165, Utils.HEIGHT / 2 - 375, 52, 10, Color.WHITE, root);
            brick22.initDurabilityText(root);
            Brick brick39 = new Brick(Utils.WIDTH / 2 + 550, Utils.HEIGHT / 2 - 375, 52, 10, Color.WHITE, root);
            brick39.initDurabilityText(root);
            Brick brick40 = new Brick(Utils.WIDTH / 2 - 605, Utils.HEIGHT / 2 - 375, 52, 10, Color.WHITE, root);
            brick40.initDurabilityText(root);
            Brick brick41 = new Brick(Utils.WIDTH / 2 + 495, Utils.HEIGHT / 2 - 375, 52, 10, Color.WHITE, root);
            brick41.initDurabilityText(root);
            Brick brick42 = new Brick(Utils.WIDTH / 2 - 550, Utils.HEIGHT / 2 - 375, 52, 10, Color.WHITE, root);
            brick42.initDurabilityText(root);
            Brick brick43 = new Brick(Utils.WIDTH / 2 + 440, Utils.HEIGHT / 2 - 375, 52, 10, Color.WHITE, root);
            brick43.initDurabilityText(root);
            Brick brick44 = new Brick(Utils.WIDTH / 2 - 495, Utils.HEIGHT / 2 - 375, 52, 10, Color.WHITE, root);
            brick44.initDurabilityText(root);

            // top top top row
            Brick brick13 = new Brick(Utils.WIDTH / 2 + 55, Utils.HEIGHT / 2 - 430, 52, 10, Color.WHITE, root);
            brick13.initDurabilityText(root);
            Brick brick14 = new Brick(Utils.WIDTH / 2, Utils.HEIGHT / 2 - 430, 52, 10, Color.WHITE, root);
            brick14.initDurabilityText(root);
            Brick brick15 = new Brick(Utils.WIDTH / 2 - 55, Utils.HEIGHT / 2 - 430, 52, 10, Color.WHITE, root);
            brick15.initDurabilityText(root);
            Brick brick16 = new Brick(Utils.WIDTH / 2 - 110, Utils.HEIGHT / 2 - 430, 52, 10, Color.WHITE, root);
            brick16.initDurabilityText(root);
            Brick brick17 = new Brick(Utils.WIDTH / 2 - 165, Utils.HEIGHT / 2 - 430, 52, 10, Color.WHITE, root);
            brick17.initDurabilityText(root);
            Brick brick18 = new Brick(Utils.WIDTH / 2 - 220, Utils.HEIGHT / 2 - 430, 52, 10, Color.WHITE, root);
            brick18.initDurabilityText(root);
            Brick brick19 = new Brick(Utils.WIDTH / 2 + 110, Utils.HEIGHT / 2 - 430, 52, 10, Color.WHITE, root);
            brick19.initDurabilityText(root);
            Brick brick20 = new Brick(Utils.WIDTH / 2 + 165, Utils.HEIGHT / 2 - 430, 52, 10, Color.WHITE, root);
            brick20.initDurabilityText(root);
            Brick brick45 = new Brick(Utils.WIDTH / 2 - 605, Utils.HEIGHT / 2 - 430, 52, 10, Color.WHITE, root);
            brick45.initDurabilityText(root);
            Brick brick46 = new Brick(Utils.WIDTH / 2 + 550, Utils.HEIGHT / 2 - 430, 52, 10, Color.WHITE, root);
            brick46.initDurabilityText(root);
            Brick brick47 = new Brick(Utils.WIDTH / 2 + 495, Utils.HEIGHT / 2 - 430, 52, 10, Color.WHITE, root);
            brick47.initDurabilityText(root);
            Brick brick48 = new Brick(Utils.WIDTH / 2 - 550, Utils.HEIGHT / 2 - 430, 52, 10, Color.WHITE, root);
            brick48.initDurabilityText(root);

            brickList.add(brick1);
            brickList.add(brick2);
            brickList.add(brick3);
            brickList.add(brick4);
            brickList.add(brick5);
            brickList.add(brick6);
            brickList.add(brick7);
            brickList.add(brick8);
            brickList.add(brick9);
            brickList.add(brick10);
            brickList.add(brick11);
            brickList.add(brick12);
            brickList.add(brick13);
            brickList.add(brick14);
            brickList.add(brick15);
            brickList.add(brick16);
            brickList.add(brick17);
            brickList.add(brick18);
            brickList.add(brick19);
            brickList.add(brick20);
            brickList.add(brick21);
            brickList.add(brick22);
            brickList.add(brick23);
            brickList.add(brick24);
            brickList.add(brick25);
            brickList.add(brick26);
            brickList.add(brick27);
            brickList.add(brick28);
            brickList.add(brick29);
            brickList.add(brick30);
            brickList.add(brick31);
            brickList.add(brick32);
            brickList.add(brick33);
            brickList.add(brick34);
            brickList.add(brick35);
            brickList.add(brick36);
            brickList.add(brick37);
            brickList.add(brick38);
            brickList.add(brick39);
            brickList.add(brick40);
            brickList.add(brick41);
            brickList.add(brick42);
            brickList.add(brick43);
            brickList.add(brick44);
            brickList.add(brick45);
            brickList.add(brick46);
            brickList.add(brick47);
            brickList.add(brick48);

        } catch (FileNotFoundException e){
            System.out.println(e);
        }

        return brickList;
    }

    public static ArrayList<Brick> MakeBrickLayout3(Group root) {
        ArrayList<Brick> brickList = new ArrayList<>();

        try { // for the custom font loading a try catch block is needed

            // bottom row


            // top row
            Brick brick4 = new Brick(Utils.WIDTH / 2 + 55, Utils.HEIGHT / 2 - 375, 52, 10, Color.WHITE, root);
            brick4.initDurabilityText(root);
            Brick brick5 = new Brick(Utils.WIDTH / 2, Utils.HEIGHT / 2 - 375, 52, 10, Color.WHITE, root);
            brick5.initDurabilityText(root);
            Brick brick6 = new Brick(Utils.WIDTH / 2 - 55, Utils.HEIGHT / 2 - 375, 52, 10, Color.WHITE, root);
            brick6.initDurabilityText(root);
            Brick brick7 = new Brick(Utils.WIDTH / 2 - 605, Utils.HEIGHT / 2 - 375, 52, 10, Color.WHITE, root);
            brick7.initDurabilityText(root);
            Brick brick8 = new Brick(Utils.WIDTH / 2 - 550, Utils.HEIGHT / 2 - 375, 52, 10, Color.WHITE, root);
            brick8.initDurabilityText(root);
            Brick brick11 = new Brick(Utils.WIDTH / 2 - 110, Utils.HEIGHT / 2 - 375, 52, 10, Color.WHITE, root);
            brick11.initDurabilityText(root);
            Brick brick19 = new Brick(Utils.WIDTH / 2 + 550, Utils.HEIGHT / 2 - 375, 52, 10, Color.WHITE, root);
            brick19.initDurabilityText(root);
            Brick brick23 = new Brick(Utils.WIDTH / 2 + 495, Utils.HEIGHT / 2 - 375, 52, 10, Color.WHITE, root);
            brick23.initDurabilityText(root);

            // top top row
            Brick brick13 = new Brick(Utils.WIDTH / 2 + 55, Utils.HEIGHT / 2 - 430, 52, 10, Color.WHITE, root);
            brick13.initDurabilityText(root);
            Brick brick14 = new Brick(Utils.WIDTH / 2, Utils.HEIGHT / 2 - 430, 52, 10, Color.WHITE, root);
            brick14.initDurabilityText(root);
            Brick brick15 = new Brick(Utils.WIDTH / 2 - 55, Utils.HEIGHT / 2 - 430, 52, 10, Color.WHITE, root);
            brick15.initDurabilityText(root);
            Brick brick16 = new Brick(Utils.WIDTH / 2 - 605, Utils.HEIGHT / 2 - 430, 52, 10, Color.WHITE, root);
            brick16.initDurabilityText(root);
            Brick brick17 = new Brick(Utils.WIDTH / 2 - 550, Utils.HEIGHT / 2 - 430, 52, 10, Color.WHITE, root);
            brick17.initDurabilityText(root);
            Brick brick18 = new Brick(Utils.WIDTH / 2 - 110, Utils.HEIGHT / 2 - 430, 52, 10, Color.WHITE, root);
            brick18.initDurabilityText(root);
            Brick brick20 = new Brick(Utils.WIDTH / 2 + 550, Utils.HEIGHT / 2 - 430, 52, 10, Color.WHITE, root);
            brick20.initDurabilityText(root);
            Brick brick24 = new Brick(Utils.WIDTH / 2 + 495, Utils.HEIGHT / 2 - 430, 52, 10, Color.WHITE, root);
            brick24.initDurabilityText(root);

            //brickList.add(brick1);
            //brickList.add(brick2);
            //brickList.add(brick3);
            brickList.add(brick4);
            brickList.add(brick5);
            brickList.add(brick6);
            brickList.add(brick7);
            brickList.add(brick8);
            //brickList.add(brick9);
            //brickList.add(brick10);
            brickList.add(brick11);
            //brickList.add(brick12);
            brickList.add(brick13);
            brickList.add(brick14);
            brickList.add(brick15);
            brickList.add(brick16);
            brickList.add(brick17);
            brickList.add(brick18);
            brickList.add(brick19);
            brickList.add(brick20);
            //brickList.add(brick21);
            //brickList.add(brick22);
            brickList.add(brick23);
            brickList.add(brick24);

        } catch (FileNotFoundException e){
            System.out.println(e);
        }

        return brickList;
    }
}
