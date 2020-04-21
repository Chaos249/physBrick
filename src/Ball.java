import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

public class Ball{

	//JavaFX UI for ball
	public Node node;

	//X and Y position of the ball in JBox2D world
	private float posX;
	private float posY;

	//Ball radius in pixels
	private int radius;

	private Color color;

	private BodyType bodyType;

	public Ball(Group root, float posX, float posY, int radius, Color color){
		this.posX = posX;
		this.posY = posY;
		this.radius = radius;
		this.bodyType = BodyType.DYNAMIC;
		this.color = color;
		node = create();
		root.getChildren().add(node);
	}

	private Node create(){
		// javafx code
		Circle ball = new Circle();
		ball.setRadius(radius);
		ball.setStroke(this.color); //set look and feel
		ball.setStrokeWidth(2.5);
		ball.setLayoutX(Utils.toPixelPosX(posX));
		ball.setLayoutY(Utils.toPixelPosY(posY));
		ball.setCache(true); //Cache this object for better performance

		// jbox2d code
		// body def
		BodyDef bd = new BodyDef();
		bd.type = bodyType;
		bd.position.set(posX, posY);

		// shape
		CircleShape cs = new CircleShape();
		cs.m_radius = radius * 0.1f;

		// fixture
		FixtureDef fd = new FixtureDef();
		fd.shape = cs;
		fd.density = 10f; //10 yields best results
		fd.restitution = 0.2f;

		// body
		Body body = Utils.world.createBody(bd);
		body.createFixture(fd);
		body.m_gravityScale = 8f;

		ball.setUserData(body); // important, sets the jbox2d object as the javafx nodes userdata

		return ball;
	}
}