package View;

import Components.*;
import ElementsUtil.DisplayElements;
import ElementsUtil.GameElements;
import ElementsUtil.Utils;
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import static View.PhysBrick.VOLUME;
import static View.PhysBrick.buttonSound;


/**
 * THIS CLASS IS VERY EMBARRASSING PLEASE LEAVE AND NEVER COME BACK!!!!!!!!!!!!!!
 */
public class GameView {

    public static World world;

    public static Scene gameScene;
    public static Group root;

    public Button homeButton;

    public static GamePlatform plat;
    public Ball startBall;
    public static ArrayList<Ball> balls;

    public int levelNumber;
    public static ArrayList<Brick> gameLayout;

    public static int score;
    public static Text scoreText;

    public int timer;
    public Text timerText;

    public Text startText;

    public ImageView gameOverImage;
    public Button retryButton;
    public ImageView levelCompleteImage;
    public Button nextLevelButton;
    public Button creditsButton;

    public Timeline timeline = new Timeline();

    public boolean gameover = false;
    public boolean levelComplete = false;
    public boolean paused = false;
    public boolean gameStarted = false;
    public AudioClip pauseSound = new AudioClip("file:src/resources/sound/pause.wav");
    public Text pauseText;

    public static String gameMusicPath = new File("src/resources/sound/gamemusic.wav").getAbsolutePath();
    public static Media gameMusic = new Media(new File(gameMusicPath).toURI().toString());
    public static MediaPlayer gameMusicPlayer = new MediaPlayer(gameMusic);

    public GameView(Stage primaryStage, Scene menuScene, int levelNumber, ArrayList arr) throws FileNotFoundException {
        this.root = new Group();
        this.gameScene = new Scene(root, Utils.WIDTH, Utils.HEIGHT, Color.BLACK);

        this.world = new World(new Vec2(0.0f, -10.0f));
        this.plat = new GamePlatform(this.root, Utils.toPosX(Utils.WIDTH / 2) - 3.8f, Utils.toPosX((Utils.HEIGHT / 2)) - 10, Color.WHITE);

        this.startBall = new Ball(this.root, Utils.toPosX(Utils.WIDTH / 2), Utils.toPosX((Utils.HEIGHT / 2) + 0), Utils.RandomColor());
        this.balls = new ArrayList<>();
        this.balls.add(this.startBall);

        this.homeButton = initHomeButton(primaryStage, menuScene);

        this.scoreText = initScore();

        this.timerText = initTimer();

        SettingsView.musicPlayer.stop();

        this.gameMusicPlayer.setCycleCount(999999999);
        this.gameMusicPlayer.setVolume(VOLUME * 0.75);
        if (!PhysBrick.DEBUG) {
            this.gameMusicPlayer.play();
        }

        this.gameOverImage = MakeGameOverImage();
        this.retryButton = MakeRetryButton(primaryStage, menuScene, arr, levelNumber);
        this.levelCompleteImage = MakeLevelCompleteImage();
        this.nextLevelButton = MakeNextLevelButton(primaryStage, menuScene, arr, levelNumber);
        this.startText = MakeStartText();
        this.pauseText = MakePauseText();

        this.creditsButton = CreditsView.MakeCreditButton(root);
        this.creditsButton.setOpacity(0);
        this.creditsButton.setVisible(false);
        this.creditsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    StripAndClip();
                    CreditsView newCv = new CreditsView(primaryStage, menuScene, arr, false);
                    primaryStage.setScene(newCv.creditsScene);
                    buttonSound.setVolume(VOLUME * 1.25);
                    buttonSound.play();
                    DisplayElements.CustomLightning(newCv.creditsScene, newCv.root, 8000, 50, 0.5f);
                    gameMusicPlayer.stop();
                    if (!PhysBrick.DEBUG) {
                        SettingsView.musicPlayer.play();
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        timeline.setCycleCount(Timeline.INDEFINITE);
        Duration duration = Duration.seconds(1.0 / 60.0); // duration for frame

        UserInput.MakeMouseJointEventHandler(gameScene, plat); // mouse joint control and platform placement // this is not being deleted by stripandclip
        GameLoop(duration, timeline);

        GameElements.MakeBoundingBox(); // bounding box
        if (PhysBrick.DEBUG) {
            GameElements.MakeAddBallMouseEvent(gameScene, root, balls, homeButton);
        }

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
                this.gameLayout = LevelView.MakeBrickLayout5(root);
                break;
            default:
                this.gameLayout = LevelView.MakeBrickLayout1(root);
        }

        gameScene.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.ESCAPE && paused == false) {
                    timeline.pause();
                    paused = true;
                    pauseSound.setVolume(VOLUME * 2.5);
                    if (!PhysBrick.DEBUG) {
                        gameMusicPlayer.pause();
                    }
                    pauseSound.play();
                    pauseText.toFront();
                    pauseText.setVisible(true);
                }
                else if (keyEvent.getCode() == KeyCode.ESCAPE && paused == true) {
                    if (gameStarted == true) {
                        timeline.play();
                    }
                    paused = false;
                    pauseSound.setVolume(VOLUME * 2.5);
                    pauseSound.play();
                    if (!PhysBrick.DEBUG) {
                        gameMusicPlayer.play();
                    }
                    pauseText.setVisible(false);
                }
            }
        });

        if (!(timeline.getStatus() == Animation.Status.PAUSED)) {
            gameScene.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    if (paused == false) {
                        timeline.playFromStart();
                        gameStarted = true;
                        startText.setVisible(false);
                    }
                }
            });
        }

        if (PhysBrick.DEBUG) { // this causes pausing to break for some reason
            GameElements.MakeAddLightningButtonMouseEvent(gameScene, root);
            GameElements.DebugHotkeys(gameScene, plat, balls, root, gameLayout);
        }
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
                plat.node.setLayoutX(xpos_p - GamePlatform.width / 2);
                plat.node.setLayoutY(ypos_p - GamePlatform.height / 2);
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
                        boolean broken = brick.BreakCheck(gameScene, root);
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
                    creditsButton.toFront();
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

                gameMusicPlayer.stop();
                if (!PhysBrick.DEBUG) {
                    SettingsView.musicPlayer.play();
                }
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

    public static void UpdateScore() {
        score += 1;
        scoreText.setText("SCORE:" + score);
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

            this.creditsButton.setVisible(true);
            FadeTransition creditft = new FadeTransition();
            creditft.setNode(this.creditsButton);
            creditft.setDuration(Duration.seconds(0.15));
            creditft.setFromValue(0);
            creditft.setToValue(0.9);
            creditft.setAutoReverse(false);
            creditft.play();

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

    public Text MakeStartText() throws FileNotFoundException {
        Text startText = new Text();
        startText.setText("CLICK TO START!");
        startText.setOpacity(0.8);
        startText.setFont(Font.loadFont(new FileInputStream("src/resources/start.ttf"), 30));
        startText.setFill(Color.WHITE);
        startText.setTranslateX((Utils.WIDTH / 2) - 220);
        startText.setTranslateY((Utils.HEIGHT / 2) + 80);

        this.root.getChildren().add(startText);

        return startText;
    }

    public Text MakePauseText() throws FileNotFoundException {
        Text pauseText = new Text();
        pauseText.setText("PAUSED");
        pauseText.setOpacity(0.8);
        pauseText.setFont(Font.loadFont(new FileInputStream("src/resources/start.ttf"), 30));
        pauseText.setFill(Color.WHITE);
        pauseText.setTranslateX((Utils.WIDTH / 2) - 90);
        pauseText.setTranslateY((Utils.HEIGHT / 2) + 35);
        pauseText.setVisible(false);

        this.root.getChildren().add(pauseText);

        return pauseText;
    }

    public void StripAndClip() { // deletes all objects in parent object to save memory and not fuck other shit up
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
        Runtime.getRuntime().gc(); // java garbage cleaner
        System.out.println("stripped and clipped");
    }
}
