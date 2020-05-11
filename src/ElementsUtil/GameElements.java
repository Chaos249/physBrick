package ElementsUtil;

import Components.Ball;
import Components.Platform;
import View.GameView;
import View.PhysBrick;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.FixtureDef;

import java.util.ArrayList;

public class GameElements {

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

    public static void MakeAddBallMouseEvent(Scene scene, Group root, ArrayList<Ball> balls, Button homeButton) {
        EventHandler<MouseEvent> addBall = new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                float dragX = (float) me.getSceneX();
                float dragY = (float) me.getSceneY();

                // Draw ball on this location
                Ball ball = new Ball(root, Utils.toPosX(dragX), Utils.toPosY(dragY), Utils.RandomColor());
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

    public static void DebugHotkeys(Scene gameScene, Platform plat, ArrayList<Ball> balls) {
        gameScene.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.J) {
                    plat.enlargePlatform();
                }
                if (keyEvent.getCode() == KeyCode.I) {
                    Ball.embiggenBalls(balls);
                }
            }
        });
        gameScene.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.K) {
                    plat.ensmallPlatform();
                }
                if (keyEvent.getCode() == KeyCode.O) {
                    Ball.microBalls(balls);
                }
            }
        });
    }

}
