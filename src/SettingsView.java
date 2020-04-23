import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class SettingsView {

    Scene settingsScene;

    Group root;

    Button backButton;

    ImageView volumeImage;
    Slider volumeSlider;

    public SettingsView(Stage primaryStage, Scene menuScene) throws FileNotFoundException {
        this.root = new Group();
        this.settingsScene = new Scene(this.root, Utils.WIDTH, Utils.HEIGHT, Color.BLACK);

        DisplayElements.EightiesAnim(PhysBrick.DEBUG, primaryStage, this.root, Color.CYAN);

        this.backButton = initBackButton(primaryStage, menuScene);
        this.volumeSlider = initSlider();
        this.volumeImage = MakeVolumeImage();

        this.root.getChildren().add(backButton);
        this.root.getChildren().add(volumeSlider);
    }

    public static Button MakeSettingsButton(Group root) throws FileNotFoundException {
        final Button btn = new Button();
        FileInputStream play_input = new FileInputStream("src/resources/image/settings.jpg");
        Image play_img = new Image(play_input);
        ImageView btngraphic = new ImageView(play_img);
        double btnScale = 0.3;

        btn.setLayoutX((Utils.WIDTH / 14f));
        btn.setLayoutY((Utils.HEIGHT - 365));
        btn.setGraphic(btngraphic);
        btn.setScaleX(btnScale);
        btn.setScaleY(btnScale);
        btn.setOpacity(0.85);
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

        return backButton;
    }

    public Slider initSlider() {

        Slider volumeSlider = new Slider(0, 100, 50);
        volumeSlider.setMax(1);
        volumeSlider.setMin(0);

        volumeSlider.setPrefWidth(862);

        volumeSlider.setTranslateX(Utils.WIDTH / 2 - 431);
        volumeSlider.setTranslateY(Utils.HEIGHT / 2- 100);

        volumeSlider.setId("custom-slider");

        volumeSlider.setOpacity(0.8);

        settingsScene.getStylesheets().add("/resources/image/slider.css");

        return volumeSlider;
    }

    public ImageView MakeVolumeImage() throws FileNotFoundException {
        FileInputStream volume_input = new FileInputStream("src/resources/image/volume.jpg");
        Image image = new Image(volume_input);
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
