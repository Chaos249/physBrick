package View;

import Components.Ball;
import Components.Brick;
import Components.Platform;
import Components.UserInput;
import ElementsUtil.GameElements;
import ElementsUtil.Utils;
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
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import static View.PhysBrick.VOLUME;
import static View.PhysBrick.buttonSound;

public class GameView {

    public static World world;

    public  Scene gameScene;
    public Group root;

    public Button homeButton;

    public Platform plat;
    public ArrayList<Ball> balls;

    public ArrayList<Brick> gameLayout;

    public Timeline timeline = new Timeline();

    public GameView(Stage primaryStage, Scene menuScene, int levelNumber) throws FileNotFoundException {
        this.root = new Group();
        this.gameScene = new Scene(root, Utils.WIDTH, Utils.HEIGHT, Color.BLACK);

        this.world = new World(new Vec2(0.0f, -10.0f));
        this.plat = new Platform(this.root, Utils.toPosX(Utils.WIDTH / 2), Utils.toPosX(Utils.HEIGHT / 2), Color.WHITE);
        this.balls = new ArrayList<>();

        this.homeButton = initHomeButton(primaryStage, menuScene);

        timeline.setCycleCount(Timeline.INDEFINITE);
        Duration duration = Duration.seconds(1.0 / 60.0); // duration for frame.

        UserInput.MakeMouseJointEventHandler(gameScene, plat); // mouse joint control and platform placement // this is not being deleted by stripandclip
        GameLoop(duration, timeline);

        GameElements.MakeBoundingBox(); // bounding box
        GameElements.MakeAddBallMouseEvent(gameScene, root, balls, homeButton);

        if (levelNumber == 1) {
            this.gameLayout = LevelView.MakeBrickLayout1(root);
        }
        else if (levelNumber == 2) {
            this.gameLayout = LevelView.MakeBrickLayout2(root);
        }
        else if (levelNumber == 3) {
            this.gameLayout = LevelView.MakeBrickLayout3(root);
        }
        else if (levelNumber == 4) {
            this.gameLayout = LevelView.MakeBrickLayout2(root);
        }
        else if (levelNumber == 5) {
            this.gameLayout = LevelView.MakeBrickLayout2(root);
        } else {
            this.gameLayout = LevelView.MakeBrickLayout2(root);
        }


        timeline.playFromStart();
    }

    public void GameLoop(Duration duration, Timeline timeline) {
        EventHandler<ActionEvent> loopEvent = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {

                // create time step. Set Iteration count 8 for velocity and 3 for positions
                world.step(1.0f / 60.f, 8, 3); //velocityIterations 8 position 3

                // move balls to the new position computed by JBox2D
                Body body_p = (Body) plat.node.getUserData();

                // grab mouse position
                float xpos_p = Utils.toPixelPosX(body_p.getPosition().x);
                float ypos_p = Utils.toPixelPosY(body_p.getPosition().y);

                // updates the platform on screen
                plat.node.setLayoutX(xpos_p - Platform.width / 2);
                plat.node.setLayoutY(ypos_p - Platform.height / 2);
                plat.node.toFront();

                // updates the balls on screen
                for (Ball ball : balls) {
                    Body body_b = (Body) ball.node.getUserData();
                    float xpos = Utils.toPixelPosX(body_b.getPosition().x);
                    float ypos = Utils.toPixelPosY(body_b.getPosition().y);

                    ball.node.setLayoutX(xpos);
                    ball.node.setLayoutY(ypos);
                }

                // damage check loop
                for (Brick brick : gameLayout) {
                    if (brick.CheckContact()) {
                        double damageAmount = 0.195;
                        brick.BreakCheck(root, balls);
                        brick.damageDurability((float) damageAmount); // usually 1
                    }
                }
                homeButton.toFront();
            }
        };
        KeyFrame frame = new KeyFrame(duration, loopEvent, null, null);
        timeline.getKeyFrames().add(frame);
    }

    public Button initHomeButton(Stage primaryStage, Scene menuScene) throws FileNotFoundException {
        double btnScale = 0.2;
        this.homeButton = new Button();
        FileInputStream play_input = new FileInputStream("src/resources/image/home.jpg");
        Image play_img = new Image(play_input);
        ImageView btngraphic = new ImageView(play_img);

        this.homeButton.setGraphic(btngraphic);
        this.homeButton.setScaleX(btnScale);
        this.homeButton.setScaleY(btnScale);
        this.homeButton.setOpacity(0.92);
        this.homeButton.setPadding(Insets.EMPTY);
        this.homeButton.setTranslateX(-150);
        this.homeButton.setTranslateY(-150);

        this.homeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                StripAndClip(); // tries to deletes game object to save memory and not have other dumb shit happen
                primaryStage.setScene(menuScene);
                buttonSound.setVolume(VOLUME * 1.25);
                buttonSound.play();
            }
        });

        this.root.getChildren().add(homeButton);
        return homeButton;
    }

    public void StripAndClip() {
        world = null;
        this.timeline.stop();
        this.timeline = null;
        this.homeButton = null;
        this.gameScene = null;
        this.root = null;
        this.gameLayout = null;
        this.plat = null;
        Runtime.getRuntime().gc();

        System.out.println("stripped and clipped");
    }
}
