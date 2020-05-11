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
            Brick brick1 = new Brick(Utils.WIDTH / 2, Utils.HEIGHT / 2 - 265, 52, 10, Color.LIME, root);
            brick1.initDurabilityText(root);
            Brick brick2 = new Brick(Utils.WIDTH / 2 - 55, Utils.HEIGHT / 2 - 265, 52, 10, Color.LIME, root);
            brick2.initDurabilityText(root);
            Brick brick3 = new Brick(Utils.WIDTH / 2 + 55, Utils.HEIGHT / 2 - 265, 52, 10, Color.LIME, root);
            brick3.initDurabilityText(root);
            Brick brick9 = new Brick(Utils.WIDTH / 2 - 605, Utils.HEIGHT / 2 - 265, 52, 5, Color.WHITE, root);
            brick9.initDurabilityText(root);
            Brick brick10 = new Brick(Utils.WIDTH / 2 - 550, Utils.HEIGHT / 2 - 265, 52, 5, Color.WHITE, root);
            brick10.initDurabilityText(root);
            Brick brick12 = new Brick(Utils.WIDTH / 2 - 110, Utils.HEIGHT / 2 - 265, 52, 10, Color.LIME, root);
            brick12.initDurabilityText(root);
            Brick brick21 = new Brick(Utils.WIDTH / 2 + 550, Utils.HEIGHT / 2 - 265, 52, 5, Color.WHITE, root);
            brick21.initDurabilityText(root);
            Brick brick22 = new Brick(Utils.WIDTH / 2 + 495, Utils.HEIGHT / 2 - 265, 52, 5, Color.WHITE, root);
            brick22.initDurabilityText(root);

            // top row
            Brick brick4 = new Brick(Utils.WIDTH / 2 + 55, Utils.HEIGHT / 2 - 320, 52, 10, Color.LIME, root);
            brick4.initDurabilityText(root);
            Brick brick5 = new Brick(Utils.WIDTH / 2, Utils.HEIGHT / 2 - 320, 52, 20, Color.DEEPPINK, root);
            brick5.initDurabilityText(root);
            Brick brick6 = new Brick(Utils.WIDTH / 2 - 55, Utils.HEIGHT / 2 - 320, 52, 20, Color.DEEPPINK, root);
            brick6.initDurabilityText(root);
            Brick brick7 = new Brick(Utils.WIDTH / 2 - 605, Utils.HEIGHT / 2 - 320, 52, 5, Color.WHITE, root);
            brick7.initDurabilityText(root);
            Brick brick8 = new Brick(Utils.WIDTH / 2 - 550, Utils.HEIGHT / 2 - 320, 52, 5, Color.WHITE, root);
            brick8.initDurabilityText(root);
            Brick brick11 = new Brick(Utils.WIDTH / 2 - 110, Utils.HEIGHT / 2 - 320, 52, 10, Color.LIME, root);
            brick11.initDurabilityText(root);
            Brick brick19 = new Brick(Utils.WIDTH / 2 + 550, Utils.HEIGHT / 2 - 320, 52, 5, Color.WHITE, root);
            brick19.initDurabilityText(root);
            Brick brick23 = new Brick(Utils.WIDTH / 2 + 495, Utils.HEIGHT / 2 - 320, 52, 5, Color.WHITE, root);
            brick23.initDurabilityText(root);

            // top top row
            Brick brick13 = new Brick(Utils.WIDTH / 2 + 55, Utils.HEIGHT / 2 - 375, 52, 10, Color.LIME, root);
            brick13.initDurabilityText(root);
            Brick brick14 = new Brick(Utils.WIDTH / 2, Utils.HEIGHT / 2 - 375, 52, 10, Color.LIME, root);
            brick14.initDurabilityText(root);
            Brick brick15 = new Brick(Utils.WIDTH / 2 - 55, Utils.HEIGHT / 2 - 375, 52, 10, Color.LIME, root);
            brick15.initDurabilityText(root);
            Brick brick16 = new Brick(Utils.WIDTH / 2 - 605, Utils.HEIGHT / 2 - 375, 52, 5, Color.WHITE, root);
            brick16.initDurabilityText(root);
            Brick brick17 = new Brick(Utils.WIDTH / 2 - 550, Utils.HEIGHT / 2 - 375, 52, 5, Color.WHITE, root);
            brick17.initDurabilityText(root);
            Brick brick18 = new Brick(Utils.WIDTH / 2 - 110, Utils.HEIGHT / 2 - 375, 52, 10, Color.LIME, root);
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
            Brick brick1 = new Brick(Utils.WIDTH / 2, Utils.HEIGHT / 2 - 265, 52, 30, Color.ORANGE, root);
            brick1.initDurabilityText(root);
            Brick brick2 = new Brick(Utils.WIDTH / 2 - 55, Utils.HEIGHT / 2 - 265, 52, 30, Color.ORANGE, root);
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
            Brick brick5 = new Brick(Utils.WIDTH / 2, Utils.HEIGHT / 2 - 320, 52, 30, Color.ORANGE, root);
            brick5.initDurabilityText(root);
            Brick brick6 = new Brick(Utils.WIDTH / 2 - 55, Utils.HEIGHT / 2 - 320, 52, 30, Color.ORANGE, root);
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
            Brick brick10 = new Brick(Utils.WIDTH / 2, Utils.HEIGHT / 2 - 375, 52, 30, Color.ORANGE, root);
            brick10.initDurabilityText(root);
            Brick brick11 = new Brick(Utils.WIDTH / 2 - 55, Utils.HEIGHT / 2 - 375, 52, 30, Color.ORANGE, root);
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
            Brick brick14 = new Brick(Utils.WIDTH / 2, Utils.HEIGHT / 2 - 430, 52, 30, Color.ORANGE, root);
            brick14.initDurabilityText(root);
            Brick brick15 = new Brick(Utils.WIDTH / 2 - 55, Utils.HEIGHT / 2 - 430, 52, 30, Color.ORANGE, root);
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
            Brick brick1 = new Brick(Utils.WIDTH / 2 - 605, Utils.HEIGHT / 2 - 100, 52, 10, Color.WHITE, root);
            brick1.initDurabilityText(root);
            Brick brick2 = new Brick(Utils.WIDTH / 2 - 550, Utils.HEIGHT / 2 - 100, 52, 10, Color.WHITE, root);
            brick2.initDurabilityText(root);
            Brick brick3 = new Brick(Utils.WIDTH / 2 + 550, Utils.HEIGHT / 2 - 100, 52, 10, Color.WHITE, root);
            brick3.initDurabilityText(root);
            Brick brick4 = new Brick(Utils.WIDTH / 2 + 495, Utils.HEIGHT / 2 - 100, 52, 10, Color.WHITE, root);
            brick4.initDurabilityText(root);
            Brick brick13 = new Brick(Utils.WIDTH / 2, Utils.HEIGHT / 2 - 100, 52, 10, Color.WHITE, root);
            brick13.initDurabilityText(root);
            Brick brick18 = new Brick(Utils.WIDTH / 2 - 55, Utils.HEIGHT / 2 - 100, 52, 10, Color.WHITE, root);
            brick18.initDurabilityText(root);

            Brick brick29 = new Brick(Utils.WIDTH / 2 - 330, Utils.HEIGHT / 2 - 100, 52, 10, Color.WHITE, root);
            brick29.initDurabilityText(root);
            Brick brick30 = new Brick(Utils.WIDTH / 2 + 275, Utils.HEIGHT / 2 - 100, 52, 10, Color.WHITE, root);
            brick30.initDurabilityText(root);


            // less bottom row
            Brick brick9 = new Brick(Utils.WIDTH / 2 - 605, Utils.HEIGHT / 2 - 155, 52, 10, Color.WHITE, root);
            brick9.initDurabilityText(root);
            Brick brick10 = new Brick(Utils.WIDTH / 2 - 550, Utils.HEIGHT / 2 - 155, 52, 10, Color.WHITE, root);
            brick10.initDurabilityText(root);
            Brick brick11 = new Brick(Utils.WIDTH / 2 + 550, Utils.HEIGHT / 2 - 155, 52, 10, Color.WHITE, root);
            brick11.initDurabilityText(root);
            Brick brick12 = new Brick(Utils.WIDTH / 2 + 495, Utils.HEIGHT / 2 - 155, 52, 10, Color.WHITE, root);
            brick12.initDurabilityText(root);
            Brick brick21 = new Brick(Utils.WIDTH / 2, Utils.HEIGHT / 2 - 155, 52, 10, Color.WHITE, root);
            brick21.initDurabilityText(root);
            Brick brick22 = new Brick(Utils.WIDTH / 2 - 55, Utils.HEIGHT / 2 - 155, 52, 10, Color.WHITE, root);
            brick22.initDurabilityText(root);

            // middle row
            Brick brick25 = new Brick(Utils.WIDTH / 2 - 330, Utils.HEIGHT / 2 - 265, 52, 10, Color.WHITE, root);
            brick25.initDurabilityText(root);
            Brick brick26 = new Brick(Utils.WIDTH / 2 + 275, Utils.HEIGHT / 2 - 265, 52, 10, Color.WHITE, root);
            brick26.initDurabilityText(root);

            // top row
            Brick brick5 = new Brick(Utils.WIDTH / 2, Utils.HEIGHT / 2 - 375, 52, 10, Color.WHITE, root);
            brick5.initDurabilityText(root);
            Brick brick6 = new Brick(Utils.WIDTH / 2 - 55, Utils.HEIGHT / 2 - 375, 52, 10, Color.WHITE, root);
            brick6.initDurabilityText(root);
            Brick brick7 = new Brick(Utils.WIDTH / 2 - 605, Utils.HEIGHT / 2 - 375, 52, 10, Color.WHITE, root);
            brick7.initDurabilityText(root);
            Brick brick8 = new Brick(Utils.WIDTH / 2 - 550, Utils.HEIGHT / 2 - 375, 52, 10, Color.WHITE, root);
            brick8.initDurabilityText(root);
            Brick brick19 = new Brick(Utils.WIDTH / 2 + 550, Utils.HEIGHT / 2 - 375, 52, 10, Color.WHITE, root);
            brick19.initDurabilityText(root);
            Brick brick23 = new Brick(Utils.WIDTH / 2 + 495, Utils.HEIGHT / 2 - 375, 52, 10, Color.WHITE, root);
            brick23.initDurabilityText(root);

            // top top row
            Brick brick14 = new Brick(Utils.WIDTH / 2, Utils.HEIGHT / 2 - 430, 52, 10, Color.WHITE, root);
            brick14.initDurabilityText(root);
            Brick brick15 = new Brick(Utils.WIDTH / 2 - 55, Utils.HEIGHT / 2 - 430, 52, 10, Color.WHITE, root);
            brick15.initDurabilityText(root);
            Brick brick16 = new Brick(Utils.WIDTH / 2 - 605, Utils.HEIGHT / 2 - 430, 52, 10, Color.WHITE, root);
            brick16.initDurabilityText(root);
            Brick brick17 = new Brick(Utils.WIDTH / 2 - 550, Utils.HEIGHT / 2 - 430, 52, 10, Color.WHITE, root);
            brick17.initDurabilityText(root);
            Brick brick20 = new Brick(Utils.WIDTH / 2 + 550, Utils.HEIGHT / 2 - 430, 52, 10, Color.WHITE, root);
            brick20.initDurabilityText(root);
            Brick brick24 = new Brick(Utils.WIDTH / 2 + 495, Utils.HEIGHT / 2 - 430, 52, 10, Color.WHITE, root);
            brick24.initDurabilityText(root);

            Brick brick27 = new Brick(Utils.WIDTH / 2 - 330, Utils.HEIGHT / 2 - 430, 52, 10, Color.WHITE, root);
            brick27.initDurabilityText(root);
            Brick brick28 = new Brick(Utils.WIDTH / 2 + 275, Utils.HEIGHT / 2 - 430, 52, 10, Color.WHITE, root);
            brick28.initDurabilityText(root);

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

        } catch (FileNotFoundException e) {
            System.out.println(e);
        }

        return brickList;
    }

    public static ArrayList<Brick> MakeBrickLayout4(Group root) {
        ArrayList<Brick> brickList = new ArrayList<>();
        try {

            // bottom row
            Brick brick1 = new Brick(Utils.WIDTH / 2, Utils.HEIGHT / 2 - 100, 52, 10, Color.WHITE, root);
            brick1.initDurabilityText(root);
            Brick brick2 = new Brick(Utils.WIDTH / 2 - 55, Utils.HEIGHT / 2 - 100, 52, 10, Color.WHITE, root);
            brick2.initDurabilityText(root);
            Brick brick3 = new Brick(Utils.WIDTH / 2 + 55, Utils.HEIGHT / 2 - 100, 52, 10, Color.WHITE, root);
            brick3.initDurabilityText(root);
            Brick brick4 = new Brick(Utils.WIDTH / 2 - 110, Utils.HEIGHT / 2 - 100, 52, 10, Color.WHITE, root);
            brick4.initDurabilityText(root);
            Brick brick5 = new Brick(Utils.WIDTH / 2 + 110, Utils.HEIGHT / 2 - 100, 52, 10, Color.WHITE, root);
            brick5.initDurabilityText(root);
            Brick brick6 = new Brick(Utils.WIDTH / 2 - 165, Utils.HEIGHT / 2 - 100, 52, 10, Color.WHITE, root);
            brick6.initDurabilityText(root);
            Brick brick7 = new Brick(Utils.WIDTH / 2 + 165, Utils.HEIGHT / 2 - 100, 52, 10, Color.WHITE, root);
            brick7.initDurabilityText(root);
            Brick brick8 = new Brick(Utils.WIDTH / 2 - 220, Utils.HEIGHT / 2 - 100, 52, 10, Color.WHITE, root);
            brick8.initDurabilityText(root);
            Brick brick9 = new Brick(Utils.WIDTH / 2 + 220, Utils.HEIGHT / 2 - 100, 52, 10, Color.WHITE, root);
            brick9.initDurabilityText(root);
            Brick brick10 = new Brick(Utils.WIDTH / 2 - 275, Utils.HEIGHT / 2 - 100, 52, 10, Color.WHITE, root);
            brick10.initDurabilityText(root);
            Brick brick11 = new Brick(Utils.WIDTH / 2 + 275, Utils.HEIGHT / 2 - 100, 52, 10, Color.WHITE, root);
            brick11.initDurabilityText(root);
            Brick brick12 = new Brick(Utils.WIDTH / 2 - 330, Utils.HEIGHT / 2 - 100, 52, 10, Color.WHITE, root);
            brick12.initDurabilityText(root);
            Brick brick13 = new Brick(Utils.WIDTH / 2 + 330, Utils.HEIGHT / 2 - 100, 52, 10, Color.WHITE, root);
            brick13.initDurabilityText(root);
            Brick brick14 = new Brick(Utils.WIDTH / 2 - 385, Utils.HEIGHT / 2 - 100, 52, 10, Color.WHITE, root);
            brick14.initDurabilityText(root);
            Brick brick15 = new Brick(Utils.WIDTH / 2 + 385, Utils.HEIGHT / 2 - 100, 52, 10, Color.WHITE, root);
            brick15.initDurabilityText(root);
            Brick brick16 = new Brick(Utils.WIDTH / 2 - 440, Utils.HEIGHT / 2 - 100, 52, 10, Color.WHITE, root);
            brick16.initDurabilityText(root);
            Brick brick17 = new Brick(Utils.WIDTH / 2 + 440, Utils.HEIGHT / 2 - 100, 52, 10, Color.WHITE, root);
            brick17.initDurabilityText(root);
            Brick brick18 = new Brick(Utils.WIDTH / 2 - 495, Utils.HEIGHT / 2 - 100, 52, 10, Color.WHITE, root);
            brick18.initDurabilityText(root);
            Brick brick19 = new Brick(Utils.WIDTH / 2 + 495, Utils.HEIGHT / 2 - 100, 52, 10, Color.WHITE, root);
            brick19.initDurabilityText(root);
            Brick brick20 = new Brick(Utils.WIDTH / 2 - 550, Utils.HEIGHT / 2 - 100, 52, 10, Color.WHITE, root);
            brick20.initDurabilityText(root);
            Brick brick21 = new Brick(Utils.WIDTH / 2 + 550, Utils.HEIGHT / 2 - 100, 52, 10, Color.WHITE, root);
            brick21.initDurabilityText(root);
            Brick brick22 = new Brick(Utils.WIDTH / 2 - 605, Utils.HEIGHT / 2 - 100, 52, 10, Color.WHITE, root);
            brick22.initDurabilityText(root);


            // small side pillars
            Brick brick45 = new Brick(Utils.WIDTH / 2 - 440, Utils.HEIGHT / 2 - 155, 52, 10, Color.WHITE, root);
            brick45.initDurabilityText(root);
            Brick brick46 = new Brick(Utils.WIDTH / 2 + 385, Utils.HEIGHT / 2 - 155, 52, 10, Color.WHITE, root);
            brick46.initDurabilityText(root);

            Brick brick47 = new Brick(Utils.WIDTH / 2 - 440, Utils.HEIGHT / 2 - 210, 52, 10, Color.WHITE, root);
            brick47.initDurabilityText(root);
            Brick brick48 = new Brick(Utils.WIDTH / 2 + 385, Utils.HEIGHT / 2 - 210, 52, 10, Color.WHITE, root);
            brick48.initDurabilityText(root);

            Brick brick49= new Brick(Utils.WIDTH / 2 - 440, Utils.HEIGHT / 2 - 330, 52, 10, Color.WHITE, root);
            brick49.initDurabilityText(root);
            Brick brick50 = new Brick(Utils.WIDTH / 2 + 385, Utils.HEIGHT / 2 - 330, 52, 10, Color.WHITE, root);
            brick50.initDurabilityText(root);

            Brick brick51 = new Brick(Utils.WIDTH / 2 - 440, Utils.HEIGHT / 2 - 385, 52, 10, Color.WHITE, root);
            brick51.initDurabilityText(root);
            Brick brick52 = new Brick(Utils.WIDTH / 2 + 385, Utils.HEIGHT / 2 - 385, 52, 10, Color.WHITE, root);
            brick52.initDurabilityText(root);

            // long middle-ish pillars
            Brick brick53 = new Brick(Utils.WIDTH / 2 - 275, Utils.HEIGHT / 2 - 385, 52, 10, Color.WHITE, root);
            brick53.initDurabilityText(root);
            Brick brick54 = new Brick(Utils.WIDTH / 2 + 220, Utils.HEIGHT / 2 - 385, 52, 10, Color.WHITE, root);
            brick54.initDurabilityText(root);
            Brick brick55 = new Brick(Utils.WIDTH / 2 - 275, Utils.HEIGHT / 2 - 330, 52, 10, Color.WHITE, root);
            brick55.initDurabilityText(root);
            Brick brick56 = new Brick(Utils.WIDTH / 2 + 220, Utils.HEIGHT / 2 - 330, 52, 10, Color.WHITE, root);
            brick56.initDurabilityText(root);
            Brick brick57 = new Brick(Utils.WIDTH / 2 - 275, Utils.HEIGHT / 2 - 275, 52, 10, Color.WHITE, root);
            brick57.initDurabilityText(root);
            Brick brick58 = new Brick(Utils.WIDTH / 2 + 220, Utils.HEIGHT / 2 - 275, 52, 10, Color.WHITE, root);
            brick58.initDurabilityText(root);
            Brick brick59 = new Brick(Utils.WIDTH / 2 - 275, Utils.HEIGHT / 2 - 220, 52, 10, Color.WHITE, root);
            brick59.initDurabilityText(root);
            Brick brick60 = new Brick(Utils.WIDTH / 2 + 220, Utils.HEIGHT / 2 - 220, 52, 10, Color.WHITE, root);
            brick60.initDurabilityText(root);


            // middle small pillars
            Brick brick61 = new Brick(Utils.WIDTH / 2 - 110, Utils.HEIGHT / 2 - 155, 52, 10, Color.WHITE, root);
            brick61.initDurabilityText(root);
            Brick brick62 = new Brick(Utils.WIDTH / 2 + 55, Utils.HEIGHT / 2 - 155, 52, 10, Color.WHITE, root);
            brick62.initDurabilityText(root);

            Brick brick63 = new Brick(Utils.WIDTH / 2 - 110, Utils.HEIGHT / 2 - 210, 52, 10, Color.WHITE, root);
            brick63.initDurabilityText(root);
            Brick brick64 = new Brick(Utils.WIDTH / 2 + 55, Utils.HEIGHT / 2 - 210, 52, 10, Color.WHITE, root);
            brick64.initDurabilityText(root);

            Brick brick65 = new Brick(Utils.WIDTH / 2 - 110, Utils.HEIGHT / 2 - 330, 52, 10, Color.WHITE, root);
            brick65.initDurabilityText(root);
            Brick brick66 = new Brick(Utils.WIDTH / 2 + 55, Utils.HEIGHT / 2 - 330, 52, 10, Color.WHITE, root);
            brick66.initDurabilityText(root);

            Brick brick67 = new Brick(Utils.WIDTH / 2 - 110, Utils.HEIGHT / 2 - 385, 52, 10, Color.WHITE, root);
            brick67.initDurabilityText(root);
            Brick brick68 = new Brick(Utils.WIDTH / 2 + 55, Utils.HEIGHT / 2 - 385, 52, 10, Color.WHITE, root);
            brick68.initDurabilityText(root);


            // top row
            Brick brick23 = new Brick(Utils.WIDTH / 2, Utils.HEIGHT / 2 - 440, 52, 10, Color.WHITE, root);
            brick23.initDurabilityText(root);
            Brick brick24 = new Brick(Utils.WIDTH / 2 - 55, Utils.HEIGHT / 2 - 440, 52, 10, Color.WHITE, root);
            brick24.initDurabilityText(root);
            Brick brick25 = new Brick(Utils.WIDTH / 2 + 55, Utils.HEIGHT / 2 - 440, 52, 10, Color.WHITE, root);
            brick25.initDurabilityText(root);
            Brick brick26 = new Brick(Utils.WIDTH / 2 - 110, Utils.HEIGHT / 2 - 440, 52, 10, Color.WHITE, root);
            brick26.initDurabilityText(root);
            Brick brick27 = new Brick(Utils.WIDTH / 2 + 110, Utils.HEIGHT / 2 - 440, 52, 10, Color.WHITE, root);
            brick27.initDurabilityText(root);
            Brick brick28 = new Brick(Utils.WIDTH / 2 - 165, Utils.HEIGHT / 2 - 440, 52, 10, Color.WHITE, root);
            brick28.initDurabilityText(root);
            Brick brick29 = new Brick(Utils.WIDTH / 2 + 165, Utils.HEIGHT / 2 - 440, 52, 10, Color.WHITE, root);
            brick29.initDurabilityText(root);
            Brick brick30 = new Brick(Utils.WIDTH / 2 - 220, Utils.HEIGHT / 2 - 440, 52, 10, Color.WHITE, root);
            brick30.initDurabilityText(root);
            Brick brick31 = new Brick(Utils.WIDTH / 2 + 220, Utils.HEIGHT / 2 - 440, 52, 10, Color.WHITE, root);
            brick31.initDurabilityText(root);
            Brick brick32 = new Brick(Utils.WIDTH / 2 - 275, Utils.HEIGHT / 2 - 440, 52, 10, Color.WHITE, root);
            brick32.initDurabilityText(root);
            Brick brick33 = new Brick(Utils.WIDTH / 2 + 275, Utils.HEIGHT / 2 - 440, 52, 10, Color.WHITE, root);
            brick33.initDurabilityText(root);
            Brick brick34 = new Brick(Utils.WIDTH / 2 - 330, Utils.HEIGHT / 2 - 440, 52, 10, Color.WHITE, root);
            brick34.initDurabilityText(root);
            Brick brick35 = new Brick(Utils.WIDTH / 2 + 330, Utils.HEIGHT / 2 - 440, 52, 10, Color.WHITE, root);
            brick35.initDurabilityText(root);
            Brick brick36 = new Brick(Utils.WIDTH / 2 - 385, Utils.HEIGHT / 2 - 440, 52, 10, Color.WHITE, root);
            brick36.initDurabilityText(root);
            Brick brick37 = new Brick(Utils.WIDTH / 2 + 385, Utils.HEIGHT / 2 - 440, 52, 10, Color.WHITE, root);
            brick37.initDurabilityText(root);
            Brick brick38 = new Brick(Utils.WIDTH / 2 - 440, Utils.HEIGHT / 2 - 440, 52, 10, Color.WHITE, root);
            brick38.initDurabilityText(root);
            Brick brick39 = new Brick(Utils.WIDTH / 2 + 440, Utils.HEIGHT / 2 - 440, 52, 10, Color.WHITE, root);
            brick39.initDurabilityText(root);
            Brick brick40 = new Brick(Utils.WIDTH / 2 - 495, Utils.HEIGHT / 2 - 440, 52, 10, Color.WHITE, root);
            brick40.initDurabilityText(root);
            Brick brick41 = new Brick(Utils.WIDTH / 2 + 495, Utils.HEIGHT / 2 - 440, 52, 10, Color.WHITE, root);
            brick41.initDurabilityText(root);
            Brick brick42 = new Brick(Utils.WIDTH / 2 - 550, Utils.HEIGHT / 2 - 440, 52, 10, Color.WHITE, root);
            brick42.initDurabilityText(root);
            Brick brick43 = new Brick(Utils.WIDTH / 2 + 550, Utils.HEIGHT / 2 - 440, 52, 10, Color.WHITE, root);
            brick43.initDurabilityText(root);
            Brick brick44 = new Brick(Utils.WIDTH / 2 - 605, Utils.HEIGHT / 2 - 440, 52, 10, Color.WHITE, root);
            brick44.initDurabilityText(root);


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
            brickList.add(brick49);
            brickList.add(brick50);
            brickList.add(brick51);
            brickList.add(brick52);
            brickList.add(brick53);
            brickList.add(brick54);
            brickList.add(brick55);
            brickList.add(brick56);
            brickList.add(brick57);
            brickList.add(brick58);
            brickList.add(brick59);
            brickList.add(brick60);
            brickList.add(brick61);
            brickList.add(brick62);
            brickList.add(brick63);
            brickList.add(brick64);
            brickList.add(brick65);
            brickList.add(brick66);
            brickList.add(brick67);
            brickList.add(brick68);

        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
        return brickList;
    }

    public static ArrayList<Brick> MakeBrickLayout5(Group root) {
        ArrayList<Brick> brickList = new ArrayList<>();
        try {
            // top row
            Brick brick23 = new Brick(Utils.WIDTH / 2, Utils.HEIGHT / 2 - 440, 52, 10, Color.WHITE, root);
            brick23.initDurabilityText(root);
            Brick brick24 = new Brick(Utils.WIDTH / 2 - 55, Utils.HEIGHT / 2 - 440, 52, 10, Color.WHITE, root);
            brick24.initDurabilityText(root);
            Brick brick25 = new Brick(Utils.WIDTH / 2 + 55, Utils.HEIGHT / 2 - 440, 52, 10, Color.WHITE, root);
            brick25.initDurabilityText(root);
            Brick brick26 = new Brick(Utils.WIDTH / 2 - 110, Utils.HEIGHT / 2 - 440, 52, 10, Color.WHITE, root);
            brick26.initDurabilityText(root);
            Brick brick27 = new Brick(Utils.WIDTH / 2 + 110, Utils.HEIGHT / 2 - 440, 52, 10, Color.WHITE, root);
            brick27.initDurabilityText(root);
            Brick brick28 = new Brick(Utils.WIDTH / 2 - 165, Utils.HEIGHT / 2 - 440, 52, 10, Color.WHITE, root);
            brick28.initDurabilityText(root);

            Brick brick31 = new Brick(Utils.WIDTH / 2 + 220, Utils.HEIGHT / 2 - 440, 52, 10, Color.WHITE, root);
            brick31.initDurabilityText(root);
            Brick brick32 = new Brick(Utils.WIDTH / 2 - 275, Utils.HEIGHT / 2 - 440, 52, 10, Color.WHITE, root);
            brick32.initDurabilityText(root);
            Brick brick33 = new Brick(Utils.WIDTH / 2 + 275, Utils.HEIGHT / 2 - 440, 52, 10, Color.WHITE, root);
            brick33.initDurabilityText(root);
            Brick brick34 = new Brick(Utils.WIDTH / 2 - 330, Utils.HEIGHT / 2 - 440, 52, 10, Color.WHITE, root);
            brick34.initDurabilityText(root);

            Brick brick37 = new Brick(Utils.WIDTH / 2 + 385, Utils.HEIGHT / 2 - 440, 52, 10, Color.WHITE, root);
            brick37.initDurabilityText(root);
            Brick brick38 = new Brick(Utils.WIDTH / 2 - 440, Utils.HEIGHT / 2 - 440, 52, 10, Color.WHITE, root);
            brick38.initDurabilityText(root);
            Brick brick39 = new Brick(Utils.WIDTH / 2 + 440, Utils.HEIGHT / 2 - 440, 52, 10, Color.WHITE, root);
            brick39.initDurabilityText(root);
            Brick brick40 = new Brick(Utils.WIDTH / 2 - 495, Utils.HEIGHT / 2 - 440, 52, 10, Color.WHITE, root);
            brick40.initDurabilityText(root);
            Brick brick41 = new Brick(Utils.WIDTH / 2 + 495, Utils.HEIGHT / 2 - 440, 52, 10, Color.WHITE, root);
            brick41.initDurabilityText(root);
            Brick brick42 = new Brick(Utils.WIDTH / 2 - 550, Utils.HEIGHT / 2 - 440, 52, 10, Color.WHITE, root);
            brick42.initDurabilityText(root);
            Brick brick43 = new Brick(Utils.WIDTH / 2 + 550, Utils.HEIGHT / 2 - 440, 52, 10, Color.WHITE, root);
            brick43.initDurabilityText(root);
            Brick brick44 = new Brick(Utils.WIDTH / 2 - 605, Utils.HEIGHT / 2 - 440, 52, 10, Color.WHITE, root);
            brick44.initDurabilityText(root);

            // - 1 from top row
            Brick brick1 = new Brick(Utils.WIDTH / 2 + 165, Utils.HEIGHT / 2 - 385, 52, 10, Color.WHITE, root);
            brick1.initDurabilityText(root);
            Brick brick2 = new Brick(Utils.WIDTH / 2 - 220, Utils.HEIGHT / 2 - 385, 52, 10, Color.WHITE, root);
            brick2.initDurabilityText(root);
            Brick brick3 = new Brick(Utils.WIDTH / 2 + 220, Utils.HEIGHT / 2 - 385, 52, 10, Color.WHITE, root);
            brick3.initDurabilityText(root);
            Brick brick4 = new Brick(Utils.WIDTH / 2 - 275, Utils.HEIGHT / 2 - 385, 52, 10, Color.WHITE, root);
            brick4.initDurabilityText(root);

            Brick brick5 = new Brick(Utils.WIDTH / 2 + 330, Utils.HEIGHT / 2 - 385, 52, 10, Color.WHITE, root);
            brick5.initDurabilityText(root);
            Brick brick6 = new Brick(Utils.WIDTH / 2 - 385, Utils.HEIGHT / 2 - 385, 52, 10, Color.WHITE, root);
            brick6.initDurabilityText(root);
            Brick brick7 = new Brick(Utils.WIDTH / 2 + 385, Utils.HEIGHT / 2 - 385, 52, 10, Color.WHITE, root);
            brick7.initDurabilityText(root);
            Brick brick8 = new Brick(Utils.WIDTH / 2 - 440, Utils.HEIGHT / 2 - 385, 52, 10, Color.WHITE, root);
            brick8.initDurabilityText(root);

            // - 2 from top row
            Brick brick9 = new Brick(Utils.WIDTH / 2 + 110, Utils.HEIGHT / 2 - 330, 52, 10, Color.WHITE, root);
            brick9.initDurabilityText(root);
            Brick brick10 = new Brick(Utils.WIDTH / 2 - 165, Utils.HEIGHT / 2 - 330, 52, 10, Color.WHITE, root);
            brick10.initDurabilityText(root);
            Brick brick11 = new Brick(Utils.WIDTH / 2 + 165, Utils.HEIGHT / 2 - 330, 52, 10, Color.WHITE, root);
            brick11.initDurabilityText(root);
            Brick brick12 = new Brick(Utils.WIDTH / 2 - 220, Utils.HEIGHT / 2 - 330, 52, 10, Color.WHITE, root);
            brick12.initDurabilityText(root);

            Brick brick13 = new Brick(Utils.WIDTH / 2 + 275, Utils.HEIGHT / 2 - 330, 52, 10, Color.WHITE, root);
            brick13.initDurabilityText(root);
            Brick brick14 = new Brick(Utils.WIDTH / 2 - 330, Utils.HEIGHT / 2 - 330, 52, 10, Color.WHITE, root);
            brick14.initDurabilityText(root);
            Brick brick15 = new Brick(Utils.WIDTH / 2 + 330, Utils.HEIGHT / 2 - 330, 52, 10, Color.WHITE, root);
            brick15.initDurabilityText(root);
            Brick brick16 = new Brick(Utils.WIDTH / 2 - 385, Utils.HEIGHT / 2 - 330, 52, 10, Color.WHITE, root);
            brick16.initDurabilityText(root);

            // - 3 from top row
            Brick brick17 = new Brick(Utils.WIDTH / 2 + 55, Utils.HEIGHT / 2 - 275, 52, 10, Color.WHITE, root);
            brick17.initDurabilityText(root);
            Brick brick18 = new Brick(Utils.WIDTH / 2 - 110, Utils.HEIGHT / 2 - 275, 52, 10, Color.WHITE, root);
            brick18.initDurabilityText(root);
            Brick brick19 = new Brick(Utils.WIDTH / 2 + 110, Utils.HEIGHT / 2 - 275, 52, 10, Color.WHITE, root);
            brick19.initDurabilityText(root);
            Brick brick20 = new Brick(Utils.WIDTH / 2 - 165, Utils.HEIGHT / 2 - 275, 52, 10, Color.WHITE, root);
            brick20.initDurabilityText(root);

            Brick brick21 = new Brick(Utils.WIDTH / 2 + 220, Utils.HEIGHT / 2 - 275, 52, 10, Color.WHITE, root);
            brick21.initDurabilityText(root);
            Brick brick22 = new Brick(Utils.WIDTH / 2 - 275, Utils.HEIGHT / 2 - 275, 52, 10, Color.WHITE, root);
            brick22.initDurabilityText(root);
            Brick brick45 = new Brick(Utils.WIDTH / 2 + 275, Utils.HEIGHT / 2 - 275, 52, 10, Color.WHITE, root);
            brick45.initDurabilityText(root);
            Brick brick46 = new Brick(Utils.WIDTH / 2 - 330, Utils.HEIGHT / 2 - 275, 52, 10, Color.WHITE, root);
            brick46.initDurabilityText(root);

            // - 4 from top row
            Brick brick47 = new Brick(Utils.WIDTH / 2 + 0, Utils.HEIGHT / 2 - 220, 52, 10, Color.WHITE, root);
            brick47.initDurabilityText(root);
            Brick brick48 = new Brick(Utils.WIDTH / 2 - 55, Utils.HEIGHT / 2 - 220, 52, 10, Color.WHITE, root);
            brick48.initDurabilityText(root);
            Brick brick49 = new Brick(Utils.WIDTH / 2 + 55, Utils.HEIGHT / 2 - 220, 52, 10, Color.WHITE, root);
            brick49.initDurabilityText(root);
            Brick brick50 = new Brick(Utils.WIDTH / 2 - 110, Utils.HEIGHT / 2 - 220, 52, 10, Color.WHITE, root);
            brick50.initDurabilityText(root);

            Brick brick51 = new Brick(Utils.WIDTH / 2 + 165, Utils.HEIGHT / 2 - 220, 52, 10, Color.WHITE, root);
            brick51.initDurabilityText(root);
            Brick brick52 = new Brick(Utils.WIDTH / 2 - 220, Utils.HEIGHT / 2 - 220, 52, 10, Color.WHITE, root);
            brick52.initDurabilityText(root);
            Brick brick53 = new Brick(Utils.WIDTH / 2 + 220, Utils.HEIGHT / 2 - 220, 52, 10, Color.WHITE, root);
            brick53.initDurabilityText(root);
            Brick brick54 = new Brick(Utils.WIDTH / 2 - 275, Utils.HEIGHT / 2 - 220, 52, 10, Color.WHITE, root);
            brick54.initDurabilityText(root);

            // - 5 from top row
            Brick brick55 = new Brick(Utils.WIDTH / 2 + 0, Utils.HEIGHT / 2 - 165, 52, 10, Color.WHITE, root);
            brick55.initDurabilityText(root);
            Brick brick56 = new Brick(Utils.WIDTH / 2 - 55, Utils.HEIGHT / 2 - 165, 52, 10, Color.WHITE, root);
            brick56.initDurabilityText(root);
            Brick brick57 = new Brick(Utils.WIDTH / 2 + 55, Utils.HEIGHT / 2 - 165, 52, 10, Color.WHITE, root);
            brick57.initDurabilityText(root);
            Brick brick58 = new Brick(Utils.WIDTH / 2 - 110, Utils.HEIGHT / 2 - 165, 52, 10, Color.WHITE, root);
            brick58.initDurabilityText(root);

            Brick brick59 = new Brick(Utils.WIDTH / 2 + 110, Utils.HEIGHT / 2 - 165, 52, 10, Color.WHITE, root);
            brick59.initDurabilityText(root);
            Brick brick60 = new Brick(Utils.WIDTH / 2 - 165, Utils.HEIGHT / 2 - 165, 52, 10, Color.WHITE, root);
            brick60.initDurabilityText(root);
            Brick brick61 = new Brick(Utils.WIDTH / 2 + 165, Utils.HEIGHT / 2 - 165, 52, 10, Color.WHITE, root);
            brick61.initDurabilityText(root);
            Brick brick62 = new Brick(Utils.WIDTH / 2 - 220, Utils.HEIGHT / 2 - 165, 52, 10, Color.WHITE, root);
            brick62.initDurabilityText(root);

            // bottom row
            Brick brick71 = new Brick(Utils.WIDTH / 2 + 220, Utils.HEIGHT / 2 - 110, 52, 10, Color.WHITE, root);
            brick71.initDurabilityText(root);
            Brick brick72 = new Brick(Utils.WIDTH / 2 - 275, Utils.HEIGHT / 2 - 110, 52, 10, Color.WHITE, root);
            brick72.initDurabilityText(root);
            Brick brick73 = new Brick(Utils.WIDTH / 2 + 275, Utils.HEIGHT / 2 - 110, 52, 10, Color.WHITE, root);
            brick73.initDurabilityText(root);
            Brick brick74 = new Brick(Utils.WIDTH / 2 - 330, Utils.HEIGHT / 2 - 110, 52, 10, Color.WHITE, root);
            brick74.initDurabilityText(root);
            Brick brick75 = new Brick(Utils.WIDTH / 2 + 330, Utils.HEIGHT / 2 - 110, 52, 10, Color.WHITE, root);
            brick75.initDurabilityText(root);
            Brick brick76 = new Brick(Utils.WIDTH / 2 - 385, Utils.HEIGHT / 2 - 110, 52, 10, Color.WHITE, root);
            brick76.initDurabilityText(root);
            Brick brick77 = new Brick(Utils.WIDTH / 2 + 385, Utils.HEIGHT / 2 - 110, 52, 10, Color.WHITE, root);
            brick77.initDurabilityText(root);
            Brick brick78 = new Brick(Utils.WIDTH / 2 - 440, Utils.HEIGHT / 2 - 110, 52, 10, Color.WHITE, root);
            brick78.initDurabilityText(root);
            Brick brick79 = new Brick(Utils.WIDTH / 2 + 440, Utils.HEIGHT / 2 - 110, 52, 10, Color.WHITE, root);
            brick79.initDurabilityText(root);
            Brick brick80 = new Brick(Utils.WIDTH / 2 - 495, Utils.HEIGHT / 2 - 110, 52, 10, Color.WHITE, root);
            brick80.initDurabilityText(root);
            Brick brick81 = new Brick(Utils.WIDTH / 2 + 495, Utils.HEIGHT / 2 - 110, 52, 10, Color.WHITE, root);
            brick81.initDurabilityText(root);
            Brick brick82 = new Brick(Utils.WIDTH / 2 - 550, Utils.HEIGHT / 2 - 110, 52, 10, Color.WHITE, root);
            brick82.initDurabilityText(root);
            Brick brick83 = new Brick(Utils.WIDTH / 2 + 550, Utils.HEIGHT / 2 - 110, 52, 10, Color.WHITE, root);
            brick83.initDurabilityText(root);
            Brick brick84 = new Brick(Utils.WIDTH / 2 - 605, Utils.HEIGHT / 2 - 110, 52, 10, Color.WHITE, root);
            brick84.initDurabilityText(root);

            // big fucker
            Brick brick85 = new Brick(Utils.WIDTH / 2 - (55 / 2), Utils.HEIGHT / 2 - 358, 52, 100, Color.CRIMSON, root);
            brick85.initDurabilityText(root);

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

            brickList.add(brick31);
            brickList.add(brick32);
            brickList.add(brick33);
            brickList.add(brick34);

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
            brickList.add(brick49);
            brickList.add(brick50);
            brickList.add(brick51);
            brickList.add(brick52);
            brickList.add(brick53);
            brickList.add(brick54);
            brickList.add(brick55);
            brickList.add(brick56);
            brickList.add(brick57);
            brickList.add(brick58);
            brickList.add(brick59);
            brickList.add(brick60);
            brickList.add(brick61);
            brickList.add(brick62);

            brickList.add(brick71);
            brickList.add(brick72);
            brickList.add(brick73);
            brickList.add(brick74);
            brickList.add(brick75);
            brickList.add(brick76);
            brickList.add(brick77);
            brickList.add(brick78);
            brickList.add(brick79);
            brickList.add(brick80);
            brickList.add(brick81);
            brickList.add(brick82);
            brickList.add(brick83);
            brickList.add(brick84);
            brickList.add(brick85);

        } catch (FileNotFoundException e) {
            System.out.println(e);
        }

        return brickList;
    }
}

