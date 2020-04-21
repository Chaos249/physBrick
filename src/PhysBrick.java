import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.jbox2d.dynamics.Body;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class PhysBrick extends Application {

    // start game immediately and disables music if true
    public static boolean DEBUG = false;

    public static void main(String[] args) {
        Application.launch(args);
    }

    // javafx main start
    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {

        primaryStage.setTitle("");
        primaryStage.setFullScreen(false);
        primaryStage.setResizable(false);

        // javafx display containers
        Group root = new Group(); //Create a group for holding all objects on the screen
        final Scene scene = new Scene(root, Utils.WIDTH, Utils.HEIGHT, Color.BLACK);
        // game elements
        Platform plat = new Platform(root, Utils.toPosX(Utils.WIDTH / 2), Utils.toPosX(Utils.HEIGHT / 2), Color.WHITE);
        // creates boundaries
        GameElements.MakeBoundingBox(); // Jbox operation
        ArrayList<Ball> balls = new ArrayList<>(); // ball container

        // for game timing
        final Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        Duration duration = Duration.seconds(1.0 / 60.0); // duration for frame.
        // mouse joint control and platform placement
        UserInput.MakeMouseJointEventHandler(scene, plat);

        /*
         * LOADING GRAPHICAL RESOURCES
         */
        // 80s style graphic
        ArrayList<Line> xlines = DisplayElements.MakeXLines(root);
        ArrayList<Line> ylines = DisplayElements.MakeYLines(root);

        // title image
        ImageView title_view = DisplayElements.MakeTitle(root);

        // play button
        Button play_btn = DisplayElements.MakePlayButton(root);

        // credit button
        Button credit_btn = DisplayElements.MakeCreditButton(root);

        // music
        AudioClip music = new AudioClip(this.getClass().getResource("resources/Untitled.wav").toString());
        music.setVolume(0.1);

        // runs the lightning hotkey subsystem
        GameElements.MakeAddLightningButtonMouseEvent(scene, root);

        //add balls
        GameElements.MakeAddBallMouseEvent(scene, root, balls);

        // temp game layout
        ArrayList<Brick> gameLayout = GameElements.MakeBrickLayout1(root);

        /*
         * MAIN GAME LOOP
         */
        // on trigger it executes a world time step and moves the game elements to new position
        EventHandler<ActionEvent> action = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                // create time step. Set Iteration count 8 for velocity and 3 for positions
                Utils.world.step(1.0f / 60.f, 8, 3); //velocityIterations 8

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
                        brick.damageDurabilityText(1);
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

        /*
         * GRAPHICS LOOP
         */
        // timer that start the game
        AnimationTimer at = new AnimationTimer() {
            int startTime = 380; // 380 is best
            boolean music_on = true;
            boolean game_on = false;
            int count = 0;
            int xline_idx = 0;

            @Override
            public void handle(long l) {
                // disables annoyances
                if (DEBUG) {
                    startTime = 1;
                    music_on = false;
                }
                // loops music
                if (music.isPlaying() == false && game_on && music_on) {
                    music.play();
                }
                // sends the horizontal lines for title graphic
                if (count % 80 == 0 && xline_idx < xlines.size()) {
                    PathTransition pt = (PathTransition) xlines.get(xline_idx).getUserData();
                    pt.play();
                    xline_idx += 1;
                    if ((xline_idx + 1) == xlines.size()) {
                        xline_idx = 0;
                    }
                }
                count += 1;
                //starts the program
                if (count % startTime == 0 && game_on == false) {
                    primaryStage.show();
                    if (music_on) {
                        music.play();
                    }
                    game_on = true;
                }
            }
        };

        /**
         * MENU TRANSITION BUTTON
         */
        // btn transition to game from title
        play_btn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                timeline.playFromStart(); // starts physics
                //disables title graphics
                play_btn.setVisible(false);
                credit_btn.setVisible(false);
                title_view.setVisible(false);

                plat.node.setVisible(true); // turns on platform

                DisplayElements.ChangeLinesColor(Color.CYAN, xlines, ylines);
                //at.stop();
                for (Brick br : gameLayout) {
                    br.node.setVisible(true);
                    br.durabilityText.setVisible(true);
                }
                music.stop();
            }
        });

        primaryStage.setScene(scene);
        // starts ui
        at.start();
    }
}