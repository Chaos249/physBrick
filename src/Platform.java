import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

public class Platform{

    // javafx node
    public Node node;

    // X and Y position of the ball in JBox2D world
    private float posX;
    private float posY;

    // platform dimensions in pixels
    public static float width = 100;
    public static float height = 25;

    private Color color;

    private BodyType bodyType;

    public Platform(Group root, float posX, float posY, Color color) {
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
        //View.Utils.toPosY(this.height) * 0.1f)/2.54f, View.Utils.toPosX(this.width) * 0.1f) * 1.3)

        // fixture
        FixtureDef fd = new FixtureDef();
        fd.shape = platform_rectangle;
        fd.density = 1f; //1f
        fd.restitution = 0.1f;
        fd.friction = 8f; //8f

        // body
        Body body = Utils.world.createBody(bd);
        body.createFixture(fd);
        body.m_gravityScale = 1f;
        body.setAngularVelocity(0);

        rec.setUserData(body); // important, sets the jbox2d object as the javafx nodes userdata

        return rec;
    }
}
