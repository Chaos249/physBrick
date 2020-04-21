import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.dynamics.*;
import org.jbox2d.dynamics.contacts.ContactEdge;

import java.util.ArrayList;

public class Brick {

    // javafx node
    public Node node;

    //X and Y position of the ball in JBox2D world
    private float posX;
    private float posY;

    // size of box in pixels
    private int size;

    public int durability;
    public Text durabilityText;

    private Color color;

    private BodyType bodyType;

    public Group root;

    public Brick(float posX, float posY, int size, int durability, Color color, Group root) {
        this.posX = posX;
        this.posY = posY;
        this.size = size;
        this.bodyType = BodyType.KINEMATIC;
        this.color = color;
        this.root = root;
        this.durability = durability;
        this.durabilityText = durabilityText;
        node = create();
        root.getChildren().add(node);
    }

    private Node create() {
        // Create an UI for brick - JavaFX code
        Rectangle brick = new Rectangle();
        brick.setWidth(this.size);
        brick.setHeight(this.size);
        brick.setStroke(this.color); //set look and feel
        brick.setStrokeWidth(2.5);
        brick.setLayoutX(posX);
        brick.setLayoutY(posY);
        brick.setVisible(false); // made invisible by default to be turned on later --!!should probably be fixed!!--
        brick.setCache(true); // Cache this object for better performance

        //this.initDurabilityText(root);

        //Create an JBox2D body definition for brick
        BodyDef bd = new BodyDef();
        bd.type = bodyType;
        bd.bullet = true;
        bd.position.set(Utils.toPosX(posX) + (((this.size / 2) - 5.4f) * 0.1f), Utils.toPosY(posY) - (((this.size / 2) - 5.2f) * 0.1f));

        PolygonShape ss = new PolygonShape();
        ss.setAsBox((this.size)  * 0.04f, (this.size) * 0.038f); //x : * 0.0393f, y : * 0.13f

        // Create a fixture for brick
        FixtureDef fd = new FixtureDef();
        fd.shape = ss;
        fd.density = 10f; //10 yields best results
        fd.restitution = 0.2f;

        Body body = Utils.world.createBody(bd);
        body.createFixture(fd);
        body.m_gravityScale = 8f;
        brick.setUserData(body);

        return brick;
    }

    public boolean BallContacted() {
        Body body = (Body) this.node.getUserData();
        ContactEdge contactEdge = body.getContactList();
        if (contactEdge != null) {
            if (contactEdge.contact.getFixtureB().getShape() instanceof CircleShape) {
                return true;
            }
        }
        return false;
    }

    public void initDurabilityText(Group root) {
        Text durText = new Text(Integer.toString(durability));
        this.durabilityText = durText;
        formatText(durText);
        durText.setLayoutY((posY + this.size * 0.5) + 2);
        durText.setFill(Color.WHITE);
        durText.setFont(Font.font("Source Code Pro"));
        durText.setVisible(false);
        root.getChildren().add(durText);
    }

    public void damageDurabilityText(int damageAmount) {
        this.durability -= damageAmount;
        this.durabilityText.setText(Integer.toString(this.durability));
        formatText(durabilityText);
    }

    public void formatText(Text durabilityText) {
        if (this.durability >= 100) {
            durabilityText.setLayoutX((posX + this.size * 0.5) - 10);
            durabilityText.setScaleX(2 * (this.size / 50));
            durabilityText.setScaleY(2 * (this.size / 50));
        } else if (this.durability >= 10) {
            durabilityText.setLayoutX((posX + this.size * 0.5) - 6.7);
            durabilityText.setScaleX(3 * (this.size / 50));
            durabilityText.setScaleY(3 * (this.size / 50));
        } else {
            durabilityText.setLayoutX((posX + this.size * 0.5) - 3.5);
            durabilityText.setScaleX(4 * (this.size / 50));
            durabilityText.setScaleY(4 * (this.size / 50));
        }
    }

    public void breakCheck(Group root) {
        if (this.durability == 1) { // stays one step after 1 for some reason
            this.durabilityText.setVisible(false);
            this.playBreakEffect();
            Utils.world.destroyBody((Body) this.node.getUserData());
            ArrayList<Line> RRT = DisplayElements.MakeLightning(400); //800
            root.getChildren().addAll(RRT);
            AnimationTimer at = DisplayElements.DrawLightning(root, RRT, 30, 0.8); //35, 0.8
            at.start();
        }
    }

    public void playBreakEffect() {
        FadeTransition ft = new FadeTransition();
        ft.setNode(this.node);
        ft.setDuration(Duration.seconds(0.5));
        ft.setFromValue(1);
        ft.setToValue(0);
        ft.setAutoReverse(false);

        ft.play();
    }
}