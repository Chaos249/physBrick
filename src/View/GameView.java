package View;

import Components.Ball;
import Components.Brick;
import Components.Platform;
import Components.UserInput;
import ElementsUtil.DisplayElements;
import ElementsUtil.GameElements;
import ElementsUtil.Utils;
import javafx.animation.FadeTransition;
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
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
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

    public Scene gameScene;
    public Group root;

    public Button homeButton;

    public Platform plat;
    public Ball startBall;
    public ArrayList<Ball> balls;

    public int levelNumber;
    public ArrayList<Brick> gameLayout;

    public int score;
    public Text scoreText;

    public int timer;
    public Text timerText;

    public ImageView gameOverImage;
    public Button retryButton;
    public ImageView levelCompleteImage;
    public Button nextLevelButton;

    public Timeline timeline = new Timeline();

    public boolean gameover = false;
    public boolean levelComplete = false;

    public GameView(Stage primaryStage, Scene menuScene, int levelNumber, ArrayList arr) throws FileNotFoundException {
        this.root = new Group();
        this.gameScene = new Scene(root, Utils.WIDTH, Utils.HEIGHT, Color.BLACK);

        this.world = new World(new Vec2(0.0f, -10.0f));
        this.plat = new Platform(this.root, Utils.toPosX(Utils.WIDTH / 2), Utils.toPosX((Utils.HEIGHT / 2)), Color.WHITE);
        this.startBall = new Ball(this.root, Utils.toPosX(Utils.WIDTH / 2), Utils.toPosX((Utils.HEIGHT / 2) + 40), Utils.RandomColor());
        this.balls = new ArrayList<>();
        this.balls.add(this.startBall);

        this.homeButton = initHomeButton(primaryStage, menuScene);

        this.scoreText = initScore();

        this.timerText = initTimer();

        this.gameOverImage = MakeGameOverImage();
        this.retryButton = MakeRetryButton(primaryStage, menuScene, arr, levelNumber);
        this.levelCompleteImage = MakeLevelCompleteImage();
        this.nextLevelButton = MakeNextLevelButton(primaryStage, menuScene, arr, levelNumber);

        timeline.setCycleCount(Timeline.INDEFINITE);
        Duration duration = Duration.seconds(1.0 / 60.0); // duration for frame.

        UserInput.MakeMouseJointEventHandler(gameScene, plat); // mouse joint control and platform placement // this is not being deleted by stripandclip
        GameLoop(duration, timeline);

        GameElements.MakeBoundingBox(); // bounding box
        GameElements.MakeAddBallMouseEvent(gameScene, root, balls, homeButton);

        this.levelNumber = levelNumber;
        switch (levelNumber) {
            case 1:
                this.gameLayout = LevelView.MakeBrickLayout1(root);
                break;
            case 2:
                this.gameLayout = LevelView.MakeBrickLayout2(root);
                break;
            case 3:
                this.gameLayout = LevelView.MakeBrickLayout3(root);
                break;
            case 4:
                this.gameLayout = LevelView.MakeBrickLayout4(root);
                break;
            case 5:
                this.gameLayout = LevelView.MakeBrickLayout4(root);
                break;
            default:
                this.gameLayout = LevelView.MakeBrickLayout2(root);
        }
        timeline.playFromStart();
    }

    public void GameLoop(Duration duration, Timeline timeline) {
        EventHandler<ActionEvent> loopEvent = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                startBall.node.toFront();
                // create time step. Set Iteration count 8 for velocity and 3 for positions
                // this executes 60 times per second theoretically (it fucks up hard when minimized)
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
                        boolean broken = brick.BreakCheck(root);
                        brick.damageDurability((float) damageAmount); // usually 1
                        UpdateScore();
                        if (broken) {
                            Ball ball = new Ball(root, Utils.toPosX((float)brick.node.getLayoutX()), Utils.toPosY((float)brick.node.getLayoutY()), Utils.RandomColor());
                            balls.add(ball);
                        }
                    }
                }
                homeButton.toFront();

                if (allBricksBroken(gameLayout)) { // level complete after this check
                    if (levelComplete == false) { // boolean check to only play animation once
                        levelCompleteImage.setVisible(true);
                        nextLevelButton.setVisible(true);
                        try {
                            PlayLevelCompleteAnim();
                        } catch (FileNotFoundException e) {
                            System.out.println(e);
                        }
                        levelComplete = true;
                    }
                    nextLevelButton.toFront();
                    levelCompleteImage.toFront();
                    return;
                }
                if (allBallsLost(balls)) { // game over after check
                    if (gameover == false) { // boolean check to only play animation once
                        gameOverImage.setVisible(true);
                        retryButton.setVisible(true);
                        PlayGameOverAnim();
                        gameover = true;
                    }
                    gameOverImage.toFront();
                    retryButton.toFront();
                    return;
                }

                UpdateTimer();
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
                StripAndClip(); // tries to delete game object to save memory and not have other dumb shit happen
                primaryStage.setScene(menuScene);
                buttonSound.setVolume(VOLUME * 1.25);
                buttonSound.play();
            }
        });

        this.root.getChildren().add(homeButton);
        return homeButton;
    }

    public Text initScore() throws FileNotFoundException {
        this.score = 0;
        Text scoreText = new Text();
        scoreText.setText("SCORE:" + this.score);
        scoreText.setOpacity(0.8);
        scoreText.setFont(Font.loadFont(new FileInputStream("src/resources/start.ttf"), 25));
        scoreText.setFill(Color.WHITE);
        scoreText.setTranslateX((Utils.WIDTH / 2) - 400);
        scoreText.setTranslateY((Utils.HEIGHT / 2) - 565);
        this.root.getChildren().add(scoreText);

        return scoreText;
    }

    public void UpdateScore() {
        this.score += 1;
        this.scoreText.setText("SCORE:" + this.score);
    }

    public Text initTimer() throws FileNotFoundException {
        this.timer = 0;
        Text timerText = new Text();
        timerText.setText("TIME:" + "0");
        timerText.setOpacity(0.8);
        timerText.setFont(Font.loadFont(new FileInputStream("src/resources/start.ttf"), 25));
        timerText.setFill(Color.WHITE);
        timerText.setTranslateX((Utils.WIDTH / 2) + 250);
        timerText.setTranslateY((Utils.HEIGHT / 2) - 565);
        this.root.getChildren().add(timerText);

        return timerText;
    }

    public void UpdateTimer() {
        this.timer += 1;
        if (timer % 30 == 0){
            this.timerText.setText("TIME:" + timer / 30);
        }
    }

    public boolean allBricksBroken(ArrayList<Brick> gameLayout) {
        for (Brick brick : gameLayout) {
            if (brick.durabilityText.isVisible()){
                return false;
            }
        }
        return true;
    }

    public boolean allBallsLost(ArrayList<Ball> balls) {
        for (Ball ball : balls) {
            if (ball.node.getLayoutY() < Utils.HEIGHT) {
                return false;
            }
        }
        return true;
    }

    public ImageView MakeLevelCompleteImage() throws FileNotFoundException {
        FileInputStream fis = new FileInputStream("src/resources/image/level_complete.jpg");
        Image image = new Image(fis);
        ImageView imageView = new ImageView(image);
        imageView.setLayoutX((Utils.WIDTH / 2) - 851);
        imageView.setLayoutY(Utils.HEIGHT - 1100);
        imageView.setOpacity(0);

        imageView.setScaleX(0.58);
        imageView.setScaleY(0.58);

        imageView.setVisible(false);

        this.root.getChildren().add(imageView);

        return imageView;
    }

    public Button MakeNextLevelButton(Stage primaryStage, Scene gameScene, ArrayList arr, int levelNumber) throws FileNotFoundException {
        this.nextLevelButton = new Button();
        this.nextLevelButton.setOpacity(0);
        this.nextLevelButton.setVisible(false);
        FileInputStream fis = new FileInputStream("src/resources/image/next_level.jpg");
        Image play_img = new Image(fis);
        ImageView btngraphic = new ImageView(play_img);

        double btnScale = 0.85;
        this.nextLevelButton.setGraphic(btngraphic);
        this.nextLevelButton.setScaleX(btnScale);
        this.nextLevelButton.setScaleY(btnScale);
        this.nextLevelButton.setPadding(Insets.EMPTY);
        this.nextLevelButton.setTranslateX((Utils.WIDTH / 2) - 370);
        this.nextLevelButton.setTranslateY((Utils.HEIGHT / 2) - 130);

        this.nextLevelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                StripAndClip();
                try {
                    GameView gv = new GameView(primaryStage, gameScene,levelNumber + 1, arr);
                    DisplayElements.AddEightiesMatrixRoot(gv.root, arr);
                    DisplayElements.ChangeLinesColor(Utils.RandomColor(), (ArrayList<Line>) arr.get(0), (ArrayList<Line>) arr.get(1));
                    primaryStage.setScene(gv.gameScene);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                buttonSound.setVolume(VOLUME * 1.25);
                buttonSound.play();
            }
        });

        this.root.getChildren().add(nextLevelButton);
        return nextLevelButton;
    }

    public ImageView MakeGameOverImage() throws FileNotFoundException {
        FileInputStream fis = new FileInputStream("src/resources/image/gameover.jpg");
        Image image = new Image(fis);
        ImageView imageView = new ImageView(image);
        imageView.setLayoutX((Utils.WIDTH / 2) - 525);
        imageView.setLayoutY(Utils.HEIGHT - 1100);
        imageView.setOpacity(0);

        imageView.setScaleX(0.6);
        imageView.setScaleY(0.6);

        imageView.setVisible(false);

        this.root.getChildren().add(imageView);

        return imageView;
    }

    public Button MakeRetryButton(Stage primaryStage, Scene gameScene, ArrayList arr, int levelNumber) throws FileNotFoundException {
        this.retryButton = new Button();
        this.retryButton.setOpacity(0);
        this.retryButton.setVisible(false);
        FileInputStream fis = new FileInputStream("src/resources/image/retry.jpg");
        Image play_img = new Image(fis);
        ImageView btngraphic = new ImageView(play_img);

        double btnScale = 0.4;
        this.retryButton.setGraphic(btngraphic);
        this.retryButton.setScaleX(btnScale);
        this.retryButton.setScaleY(btnScale);
        this.retryButton.setPadding(Insets.EMPTY);
        this.retryButton.setTranslateX((Utils.WIDTH / 2) - 370);
        this.retryButton.setTranslateY((Utils.HEIGHT / 2) - 200);

        this.retryButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                StripAndClip();
                try {
                    GameView gv = new GameView(primaryStage, gameScene,levelNumber, arr);
                    DisplayElements.AddEightiesMatrixRoot(gv.root, arr);
                    DisplayElements.ChangeLinesColor(Utils.RandomColor(), (ArrayList<Line>) arr.get(0), (ArrayList<Line>) arr.get(1));
                    primaryStage.setScene(gv.gameScene);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                buttonSound.setVolume(VOLUME * 1.25);
                buttonSound.play();
            }
        });

        this.root.getChildren().add(retryButton);
        return retryButton;
    }

    public void PlayLevelCompleteAnim() throws FileNotFoundException {
        FadeTransition ft = new FadeTransition();
        ft.setNode(this.levelCompleteImage);
        ft.setDuration(Duration.seconds(0.15));
        ft.setFromValue(0);
        ft.setToValue(0.9);
        ft.setAutoReverse(false);

        if (this.levelNumber != 5) {
            FadeTransition btnft = new FadeTransition();
            btnft.setNode(this.nextLevelButton);
            btnft.setDuration(Duration.seconds(0.15));
            btnft.setFromValue(0);
            btnft.setToValue(0.9);
            btnft.setAutoReverse(false);
            btnft.play();
        } else {
            Text endText = new Text();
            endText.setText("YOU HAVE COMPLETED ALL LEVELS!");
            endText.setOpacity(0.8);
            endText.setFont(Font.loadFont(new FileInputStream("src/resources/start.ttf"), 30));
            endText.setFill(Color.WHITE);
            endText.setTranslateX((Utils.WIDTH / 2) - 450);
            endText.setTranslateY((Utils.HEIGHT / 2) - 140);
            this.root.getChildren().add(endText);
            nextLevelButton.setVisible(false);
        }
        ft.play();
    }

    public void PlayGameOverAnim() {
        FadeTransition ft = new FadeTransition();
        ft.setNode(this.gameOverImage);
        ft.setDuration(Duration.seconds(0.15));
        ft.setFromValue(0);
        ft.setToValue(0.9);
        ft.setAutoReverse(false);

        FadeTransition btnft = new FadeTransition();
        btnft.setNode(this.retryButton);
        btnft.setDuration(Duration.seconds(0.15));
        btnft.setFromValue(0);
        btnft.setToValue(0.9);
        btnft.setAutoReverse(false);

        btnft.play();
        ft.play();
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
        this.balls = null;
        this.startBall = null;
        this.retryButton = null;
        this.nextLevelButton = null;
        this.levelCompleteImage = null;
        this.gameOverImage = null;
        Runtime.getRuntime().gc();
        System.out.println("stripped and clipped");
    }
}
