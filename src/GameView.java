import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.util.Duration;
import org.jbox2d.dynamics.Body;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class GameView {

    Scene gameScene;

    Group root;

    Button homeButton;

    Platform plat;
    ArrayList<Ball> balls;

    ArrayList<Brick> gameLayout;

    public GameView(Stage primaryStage, Scene menuScene) throws FileNotFoundException {
        this.root = new Group();
        this.gameScene = new Scene(root, Utils.WIDTH, Utils.HEIGHT, Color.BLACK);

        DisplayElements.EightiesAnim(PhysBrick.DEBUG, primaryStage, this.root, Utils.RandomColor());

        this.homeButton = initHomeButton(primaryStage, menuScene);

        this.plat = new Platform(this.root, Utils.toPosX(Utils.WIDTH / 2), Utils.toPosX(Utils.HEIGHT / 2), Color.WHITE);

        this.balls = new ArrayList<>();

        final Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        Duration duration = Duration.seconds(1.0 / 60.0); // duration for frame.
        // mouse joint control and platform placement
        UserInput.MakeMouseJointEventHandler(gameScene, plat);
        GameLoop(duration, timeline);

        GameElements.MakeBoundingBox();
        GameElements.MakeAddBallMouseEvent(gameScene, root, balls, homeButton);
        this.gameLayout = LevelSelectView.MakeBrickLayout1(this.root);

        this.root.getChildren().add(homeButton);

        timeline.playFromStart();
    }

    public void GameLoop(Duration duration, Timeline timeline) {
        EventHandler<ActionEvent> action = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                // create time step. Set Iteration count 8 for velocity and 3 for positions
                Utils.world.step(1.0f / 60.f, 8, 3); //velocityIterations 8 position 3

                // move balls to the new position computed by JBox2D
                Body body_p = (Body) plat.node.getUserData();

                // grab mouse position
                float xpos_p = Utils.toPixelPosX(body_p.getPosition().x);
                float ypos_p = Utils.toPixelPosY(body_p.getPosition().y);

                // updates the platform on screen
                plat.node.setLayoutX(xpos_p - Platform.width / 2);
                plat.node.setLayoutY(ypos_p - Platform.height / 2);

                for (Brick brick : gameLayout) { // contact currently way too fast, possibly make StillContacted method
                    if (brick.BallContacted()) {
                        brick.breakCheck(root);
                        brick.damageDurabilityText(0.1f); // usually 1
                    }
                }

                // updates the balls on screen
                for (Ball ball : balls) {
                    Body body_b = (Body) ball.node.getUserData();
                    float xpos = Utils.toPixelPosX(body_b.getPosition().x);
                    float ypos = Utils.toPixelPosY(body_b.getPosition().y);

                    ball.node.setLayoutX(xpos);
                    ball.node.setLayoutY(ypos);
                }
            }
        };
        KeyFrame frame = new KeyFrame(duration, action, null, null);
        timeline.getKeyFrames().add(frame);
    }

    public Button initHomeButton(Stage primaryStage, Scene menuScene) throws FileNotFoundException {
        this.homeButton = new Button();
        FileInputStream play_input = new FileInputStream("src/resources/image/home.jpg");
        Image play_img = new Image(play_input);
        ImageView btngraphic = new ImageView(play_img);

        double btnScale = 0.2;
        this.homeButton.setGraphic(btngraphic);
        this.homeButton.setScaleX(btnScale);
        this.homeButton.setScaleY(btnScale);
        this.homeButton.setOpacity(0.85);
        this.homeButton.setPadding(Insets.EMPTY);

        this.homeButton.setTranslateX(-150);
        this.homeButton.setTranslateY(-150);

        this.homeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                primaryStage.setScene(menuScene);
            }
        });

        return homeButton;
    }
}
