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
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

import static View.PhysBrick.VOLUME;
import static View.PhysBrick.buttonSound;

public class SettingsView {

    public Stage stage;
    public Scene settingsScene;
    public Scene menuScene;

    public Group root;

    public Button backButton;

    public ImageView volumeImage;
    public Slider volumeSlider;

    public ImageView scaleImage;
    public Slider scaleSlider;
    public Text scaleText;

    public static Media media = new Media(SettingsView.class.getResource("/sound/titlemusic.wav").toString());
    public static MediaPlayer musicPlayer = new MediaPlayer(media);

    public SettingsView(Stage primaryStage, Scene menuScene) throws FileNotFoundException {
        this.stage = primaryStage;
        this.root = new Group();
        this.settingsScene = new Scene(this.root, Utils.WIDTH, Utils.HEIGHT, Color.BLACK);
        this.menuScene = menuScene;

        DisplayElements.EightiesAnim(PhysBrick.DEBUG, primaryStage, this.root, Color.CYAN);

        this.backButton = initBackButton(primaryStage, menuScene);

        this.volumeSlider = initVolumeSlider();
        this.volumeImage = MakeVolumeImage();

        this.scaleSlider = initScaleSlider();
        this.scaleImage = MakeScaleImage();
        this.scaleText = MakeScaleText();

        Scale scale = new Scale(PhysBrick.SCALE_FACTOR, PhysBrick.SCALE_FACTOR);
        scale.setPivotX(0);
        scale.setPivotY(0);
        settingsScene.getRoot().getTransforms().setAll(scale);
    }

    public static Button MakeSettingsButton(Group root) throws FileNotFoundException {
        final Button btn = new Button();
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
                setScale();
                buttonSound.setVolume(VOLUME * 1.25);
                buttonSound.play();
                scaleText.setVisible(false);
            }
        });

        this.root.getChildren().add(backButton);
        return backButton;
    }

    public Slider initVolumeSlider() {
        musicPlayer.setVolume(0.1);
        musicPlayer.setCycleCount(999999999);

        Slider volumeSlider = new Slider(0, 100, 50);
        volumeSlider.setMax(0.5);
        volumeSlider.setMin(0.0001);

        volumeSlider.setPrefWidth(862);
        volumeSlider.setTranslateX(Utils.WIDTH / 2 - 431);
        volumeSlider.setTranslateY(Utils.HEIGHT / 2 - 200);
        volumeSlider.setId("custom-slider");

        volumeSlider.setValue(0.25);

        settingsScene.getStylesheets().add("css_resources/slider.css"); ///resources/image/

        volumeSlider.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                VOLUME = volumeSlider.getValue();
                musicPlayer.setVolume(volumeSlider.getValue() * 0.75);
            }
        });
        this.root.getChildren().add(volumeSlider);
        return volumeSlider;
    }

    public ImageView MakeVolumeImage() {
        Image image = new Image(SettingsView.class.getResource("/image/volume.jpg").toString());
        ImageView imageView = new ImageView(image);
        imageView.setLayoutX((Utils.WIDTH * 0.5) - 460);
        imageView.setLayoutY(Utils.HEIGHT - 1100);
        imageView.setOpacity(0.8);

        imageView.setScaleX(0.6);
        imageView.setScaleY(0.6);

        this.root.getChildren().add(imageView);
        return imageView;
    }

    public ImageView MakeScaleImage() {
        Image image = new Image(SettingsView.class.getResource("/image/scale.jpg").toString());
        ImageView imageView = new ImageView(image);
        imageView.setLayoutX((Utils.WIDTH * 0.5) - imageView.getBoundsInParent().getWidth() * 0.5);
        imageView.setLayoutY(Utils.HEIGHT - 700);
        imageView.setOpacity(0.8);

        imageView.setScaleX(0.6);
        imageView.setScaleY(0.6);
        this.root.getChildren().add(imageView);
        return imageView;
    }

    public Slider initScaleSlider() {
        Slider scaleSlider = new Slider(0, 100, 50);
        scaleSlider.setMax(1f);
        scaleSlider.setMin(0.5f);

        scaleSlider.setPrefWidth(862);
        scaleSlider.setTranslateX(Utils.WIDTH * 0.5 - 431);
        scaleSlider.setTranslateY(Utils.HEIGHT * 0.5 + 170);
        scaleSlider.setId("custom-slider");

        scaleSlider.setValue(0.75);

        settingsScene.getStylesheets().add("css_resources/slider.css");

        scaleSlider.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                PhysBrick.SCALE_FACTOR = (float) scaleSlider.getValue();
                scaleText.setVisible(true);
            }
        });
        this.root.getChildren().add(scaleSlider);
        return scaleSlider;
    }

    public Text MakeScaleText() {
        Text scaleText = new Text("Changes will apply after leaving this screen");
        scaleText.setOpacity(0.8);
        scaleText.setFont(Font.loadFont(CreditsView.class.getResource("/font/start.ttf").toString(), 25));
        scaleText.setFill(Color.WHITE);
        scaleText.setTranslateX((Utils.WIDTH * 0.5) - scaleText.getBoundsInParent().getWidth() * 0.5);
        scaleText.setTranslateY((Utils.HEIGHT * 0.5) + 150);
        scaleText.setVisible(false);

        root.getChildren().add(scaleText);
        return scaleText;
    }

    public void setScale() {
        Scale scale = new Scale(PhysBrick.SCALE_FACTOR, PhysBrick.SCALE_FACTOR);
        scale.setPivotX(0);
        scale.setPivotY(0);
        settingsScene.getRoot().getTransforms().setAll(scale);
        menuScene.getRoot().getTransforms().setAll(scale);
        stage.setMaxHeight(Utils.WIDTH * PhysBrick.SCALE_FACTOR + 40);
        stage.setMaxWidth(Utils.HEIGHT * PhysBrick.SCALE_FACTOR);
        stage.setMinHeight(Utils.WIDTH * PhysBrick.SCALE_FACTOR + 40);
        stage.setMinWidth(Utils.WIDTH * PhysBrick.SCALE_FACTOR);
    }
}
