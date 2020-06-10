package Components;

import ElementsUtil.GameElements;
import ElementsUtil.Utils;
import View.GameView;
import View.PhysBrick;
import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.dynamics.*;

import java.util.ArrayList;

public class GamePlatform {

    // javafx node
    public Node node;

    public Group root;

    // X and Y position of the ball in JBox2D world
    private float posX;
    private float posY;

    // platform dimensions in pixels
    public static float width = 100; // 100
    public static float height = 25; // 25

    private Color color;

    private BodyType bodyType;

    public boolean laserOn = false;
    public int laserTimer = 0;
    public int maxLaserTime = 50;

    public Runnable activeLaserThread;

    public boolean enlarged = false;
    public int extensionTimer = 0;
    public int maxExtensionTime = 50;

    //public static AudioClip laserSound = new AudioClip("file:resources/sound/laser.wav");
    public static AudioClip laserSound = new AudioClip(GamePlatform.class.getResource("/sound/laser.wav").toString());

    public GamePlatform(Group root, float posX, float posY, Color color) {
        this.root = root;
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.bodyType = BodyType.DYNAMIC;
        this.color = color;
        node = create();
        root.getChildren().add(node);
    }

    private Node create() {
        // javafx code
        Rectangle rec = new Rectangle();
        rec.setWidth(this.width);
        rec.setHeight(this.height);
        rec.setStroke(Color.WHITE); //set look and feel
        rec.setStrokeWidth(3);
        rec.setLayoutX(Utils.toPixelPosXPlat(posX));
        rec.setLayoutY(Utils.toPixelPosYPlat(posY));
        rec.setCache(true); //Cache this object for better performance
        rec.setOpacity(0.9);

        // jbox2d code
        // body def
        BodyDef bd = new BodyDef();
        bd.type = bodyType;
        bd.position.set(posX, posY);
        bd.fixedRotation = true;
        bd.angularDamping = 0.8f;
        bd.bullet = true;

        // shape
        PolygonShape platform_rectangle = new PolygonShape();
        platform_rectangle.setAsBox(Utils.toPosY(this.height) * 0.0393f, Utils.toPosX(this.width) * 0.13f); // x and y are backwards

        // fixture
        FixtureDef fd = new FixtureDef();
        fd.shape = platform_rectangle;
        fd.density = 1f; //1f
        fd.restitution = 0.1f;
        fd.friction = 8f; //8f

        // body
        Body body = GameView.world.createBody(bd);
        body.createFixture(fd);
        body.m_gravityScale = 1f;
        body.setAngularVelocity(0);

        rec.setUserData(body); // important, sets the jbox2d object as the javafx nodes userdata

        return rec;
    }

    public void enlargePlatform() {
        Body body = (Body) this.node.getUserData();
        Fixture fixture = body.getFixtureList();
        PolygonShape shape = (PolygonShape) fixture.getShape();
        shape.setAsBox(Utils.toPosY(this.height) * 0.081f, Utils.toPosX(this.width) * 0.13f);

        Rectangle rect = (Rectangle) this.node;
        rect.setTranslateX(width*2 - width*2.5);
        rect.setWidth(width * 2);

        GameElements.enlargeSound.setVolume(PhysBrick.VOLUME);
        GameElements.enlargeSound.play();
    }

    public void ensmallPlatform() {
        Body body = (Body) this.node.getUserData();
        Fixture fixture = body.getFixtureList();
        PolygonShape shape = (PolygonShape) fixture.getShape();
        shape.setAsBox(Utils.toPosY(this.height) * 0.0393f, Utils.toPosX(this.width) * 0.13f);

        Rectangle rect = (Rectangle) this.node;
        rect.setTranslateX(width*2 - width*2);
        rect.setWidth(width);
    }

    public Rectangle MakeLaser(float posX, float posY) {
        Rectangle laser = new Rectangle();
        laser.setHeight(40);
        laser.setWidth(1);
        laser.setTranslateX(posX);
        laser.setTranslateY(posY);

        laser.setStroke(Color.RED);
        laser.setStrokeWidth(4);

        return laser;
    }

    public void shootLasers(Scene scene, ArrayList<Brick> gameLayout) {
        if (GameView.gameLayout != null && this.root.equals(GameView.root)) {
            float px = (float) this.node.getLayoutX() + width / 2;
            float py = (float) this.node.getLayoutY() - (height / 2) - 10;

            float px1 = px + 35;
            float px2 = px - 35;

            Rectangle laser1 = MakeLaser(px1 , py);
            Rectangle laser2 = MakeLaser(px2 , py);

            Line path1 = new Line();
            path1.setStartX(px1);
            path1.setEndX(px1);
            path1.setStartY(py);
            path1.setEndY(py - 10000);

            Line path2 = new Line();
            path2.setStartX(px2);
            path2.setEndX(px2);
            path2.setStartY(py);
            path2.setEndY(py - 10000);

            root.getChildren().add(laser1);
            root.getChildren().add(laser2);

            PathTransition pt1 = new PathTransition();
            pt1.setDuration(Duration.seconds(5));
            pt1.setPath(path1);
            pt1.setNode(laser1);
            pt1.setCycleCount(1);
            pt1.setInterpolator(Interpolator.LINEAR);

            PathTransition pt2 = new PathTransition();
            pt2.setDuration(Duration.seconds(5));
            pt2.setPath(path2);
            pt2.setNode(laser2);
            pt2.setCycleCount(1);
            pt2.setInterpolator(Interpolator.LINEAR);

            pt1.play();
            pt2.play();

            laserSound.setVolume(PhysBrick.VOLUME);
            laserSound.play();

            Thread thread1 = new Thread(new LaserThread(scene, root, laser1, gameLayout));
            Thread thread2 = new Thread(new LaserThread(scene, root, laser2, gameLayout));
            thread1.start();
            thread2.start();
        }
    }

    public void engageLasers() {
        shootLasers(GameView.gameScene, GameView.gameLayout);
    }

    public void setActiveLaserThread(Runnable thread) {
        this.activeLaserThread = thread;
    }

}


