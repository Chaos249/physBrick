package View;

import ElementsUtil.DisplayElements;
import ElementsUtil.Utils;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static View.PhysBrick.VOLUME;
import static View.PhysBrick.buttonSound;

public class SettingsView {

    public Scene settingsScene;

    public Group root;

    public Button backButton;

    public ImageView volumeImage;
    public Slider volumeSlider;

    //public static String musicPath = new File("resources/sound/titlemusic.wav").getAbsolutePath();
    //public static Media music = new Media(new File(musicPath).toURI().toString());
    public static Media media = new Media(SettingsView.class.getResource("/sound/titlemusic.wav").toString());
    public static MediaPlayer musicPlayer = new MediaPlayer(media);

    public SettingsView(Stage primaryStage, Scene menuScene) throws FileNotFoundException {
        this.root = new Group();
        this.settingsScene = new Scene(this.root, Utils.WIDTH, Utils.HEIGHT, Color.BLACK);

        DisplayElements.EightiesAnim(PhysBrick.DEBUG, primaryStage, this.root, Color.CYAN);

        this.backButton = initBackButton(primaryStage, menuScene);
        this.volumeSlider = initSlider();

        this.volumeImage = MakeVolumeImage();

        this.root.getChildren().add(volumeSlider);

        Scale scale = new Scale(PhysBrick.SCALE_FACTOR, PhysBrick.SCALE_FACTOR);
        scale.setPivotX(0);
        scale.setPivotY(0);
        settingsScene.getRoot().getTransforms().setAll(scale);
    }

    public static Button MakeSettingsButton(Group root) throws FileNotFoundException {
        final Button btn = new Button();
        //FileInputStream play_input = new FileInputStream("resources/image/settings.jpg");
        //Image play_img = new Image(play_input);
        Image play_img = new Image(SettingsView.class.getResource("/image/settings.jpg").toString());
        ImageView btngraphic = new ImageView(play_img);
        double btnScale = 0.6;

        btn.setLayoutX((Utils.WIDTH / 2) - 348); // /14f
        btn.setLayoutY((Utils.HEIGHT - 325)); //-373
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
        //FileInputStream play_input = new FileInputStream("resources/image/back.jpg");
        //Image play_img = new Image(play_input);
        Image play_img = new Image(SettingsView.class.getResource("/image/back.jpg").toString());
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

    public Slider initSlider() {
        musicPlayer.setVolume(0.1);
        musicPlayer.setCycleCount(999999999);

        Slider volumeSlider = new Slider(0, 100, 50);
        volumeSlider.setMax(0.5);
        volumeSlider.setMin(0.0001);

        volumeSlider.setPrefWidth(862);
        volumeSlider.setTranslateX(Utils.WIDTH / 2 - 431);
        volumeSlider.setTranslateY(Utils.HEIGHT / 2- 100);
        volumeSlider.setId("custom-slider");

        volumeSlider.setOpacity(0.8);
        volumeSlider.setValue(0.25);

        settingsScene.getStylesheets().add("css_resources/slider.css"); ///resources/image/

        volumeSlider.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                VOLUME = volumeSlider.getValue();
                musicPlayer.setVolume(volumeSlider.getValue() * 0.75);
            }
        });

        return volumeSlider;
    }

    public ImageView MakeVolumeImage() throws FileNotFoundException {
        //FileInputStream volume_input = new FileInputStream("resources/image/volume.jpg");
        //Image image = new Image(volume_input);
        Image image = new Image(SettingsView.class.getResource("/image/volume.jpg").toString());
        ImageView imageView = new ImageView(image);
        imageView.setLayoutX((Utils.WIDTH / 2) - 460);
        imageView.setLayoutY(Utils.HEIGHT - 1000);
        imageView.setOpacity(0.8);

        imageView.setScaleX(0.6);
        imageView.setScaleY(0.6);

        this.root.getChildren().add(imageView);
        return imageView;
    }

}
