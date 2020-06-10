package ElementsUtil;

import Components.*;
import View.GameView;
import javafx.animation.AnimationTimer;
import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.AudioClip;
import javafx.scene.shape.Line;
import javafx.util.Duration;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.FixtureDef;
import sun.plugin2.message.GetAppletMessage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;

public class GameElements {

//    public static AudioClip powerupSound = new AudioClip("file:resources/sound/powerup.wav");
//    public static AudioClip enlargeSound = new AudioClip("file:resources/sound/enlarge.wav");
    public static AudioClip powerupSound = new AudioClip(GameElements.class.getResource("/sound/powerup.wav").toString());
    public static AudioClip enlargeSound = new AudioClip(GameElements.class.getResource("/sound/enlarge.wav").toString());

    public static void addGround(float width, float height) { // disabled for real game, only exists for testing
        PolygonShape ps = new PolygonShape();
        ps.setAsBox(width,height);

        FixtureDef fd = new FixtureDef();
        fd.shape = ps;

        BodyDef bd = new BodyDef();
        bd.position= new Vec2(0.0f,-10f);

        GameView.world.createBody(bd).createFixture(fd);
    }

    public static void addCeiling(float width, float height) {
        PolygonShape ps = new PolygonShape();
        ps.setAsBox(width,height);

        FixtureDef fd = new FixtureDef();
        fd.shape = ps;
        fd.restitution = 0.7f; // BOUNCY CEILING !!?!?!?!?!?!?!?!??!?!?!

        BodyDef bd = new BodyDef();
        bd.position= new Vec2(0.0f,110f);

        GameView.world.createBody(bd).createFixture(fd);
    }

    //This method creates a walls.
    public static void addWall(float posX, float posY, float width, float height) {
        PolygonShape ps = new PolygonShape();
        ps.setAsBox(width,height);

        FixtureDef fd = new FixtureDef();
        fd.shape = ps;
        fd.density = 1.0f;
        fd.friction = 0.3f;
        fd.restitution = 0.5f; // SLIGHTLY BOUNCY WALLS

        BodyDef bd = new BodyDef();
        bd.position.set(posX, posY);

        GameView.world.createBody(bd).createFixture(fd);
    }

    public static void MakeBoundingBox(){
        // bounding box
        //addGround(100, 11); // disabled for real game, only exists for testing
        addWall(0,100,1,105); //Left wall
        addWall(100,100,1,105); //Right wall
        addCeiling(100, 11);
    }

    public static void rollPowerUp(Group root, GamePlatform plat, Brick brick, ArrayList<Ball> balls) {
        if (Utils.randomChance(10)){
            Random r = new Random();
            int powerUp = r.nextInt(3);
            switch (powerUp){
                case 0:
                    PowerUp pStrawberry = new PowerUp(root, new String("strawberry_platform"), plat, (float) brick.node.getLayoutX(), (float) brick.node.getLayoutY(), balls);
                    pStrawberry.sendPowerUp();
                    break;
                case 1:
                    PowerUp pCherry = new PowerUp(root, new String("cherry_balls"), plat,(float) brick.node.getLayoutX(), (float) brick.node.getLayoutY(), balls);
                    pCherry.sendPowerUp();
                    break;
                case 2:
                    PowerUp pBanana = new PowerUp(root, new String("banana_laser"), plat,(float) brick.node.getLayoutX(), (float) brick.node.getLayoutY(), balls);
                    pBanana.sendPowerUp();
                    break;
                default:
                    System.out.println("error cannot find correct powerup");
            }
        }
    }

    /**
     * DEBUG FUNCTIONALITY BELOW - COMPLETELY NONESSENTIAL
     */
    public static void MakeAddBallMouseEvent(Scene scene, Group root, ArrayList<Ball> balls, Button homeButton) {
        EventHandler<MouseEvent> addBall = new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                float dragX = (float) me.getSceneX();
                float dragY = (float) me.getSceneY();

                // Draw ball on this location
                Ball ball = new Ball(root, Utils.toPosXScaled(dragX), Utils.toPosYScaled(dragY), Utils.RandomColor());
                balls.add(ball);
                homeButton.toFront();
            }
        };
        scene.setOnMouseDragged(addBall);
    }

    public static void MakeAddLightningButtonMouseEvent(Scene scene, Group root) {
        EventHandler<KeyEvent> lightningKey = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.L) {
                    ArrayList<Line> RRT = DisplayElements.MakeLightning(800); //800
                    root.getChildren().addAll(RRT);
                    AnimationTimer at = DisplayElements.DrawLightning(scene, root, RRT, 35, 0.8); //35, 0.8
                    at.start();
                }
            }
        };
        scene.setOnKeyPressed(lightningKey);
    }

    public static void DebugHotkeys(Scene gameScene, GamePlatform plat, ArrayList<Ball> balls, Group root, ArrayList<Brick> gameLayout) {
        gameScene.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.J) {
                    plat.enlargePlatform();
                }
                if (keyEvent.getCode() == KeyCode.I) {
                    Ball.embiggenBalls(balls);
                }
                if (keyEvent.getCode() == KeyCode.K) {
                    plat.ensmallPlatform();
                }
                if (keyEvent.getCode() == KeyCode.O) {
                    Ball.microBalls(balls);
                }

            }
        });
        gameScene.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.P) {
                    plat.shootLasers(gameScene, gameLayout);
                }
                if (keyEvent.getCode() == KeyCode.M) {
                    testPowerUp(root, plat, balls);
                }
            }
        });
    }

    public static void testPowerUp(Group root, GamePlatform plat, ArrayList<Ball> balls) {
        if (Utils.randomChance(2)){
            Random r = new Random();
            int powerUp = r.nextInt(3);
            switch (powerUp){
                case 0:
                    PowerUp pStrawberry = new PowerUp(root, new String("strawberry_platform"), plat, Utils.WIDTH / 2, Utils.WIDTH / 2, balls);
                    pStrawberry.sendPowerUp();
                    break;
                case 1:
                    PowerUp pCherry = new PowerUp(root, new String("cherry_balls"), plat,Utils.WIDTH / 2, Utils.WIDTH / 2, balls);
                    pCherry.sendPowerUp();
                    break;
                case 2:
                    PowerUp pBanana = new PowerUp(root, new String("banana_laser"), plat,Utils.WIDTH / 2, Utils.WIDTH / 2, balls);
                    pBanana.sendPowerUp();
                    break;
                default:
                    System.out.println("error cannot find correct powerup");
            }
        }
    }

}
