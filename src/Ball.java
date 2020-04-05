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

	public Ball(float posX, float posY){
		this(posX, posY, Utils.BALL_SIZE, BodyType.KINEMATIC,Color.RED);
		this.posX = posX;
		this.posY = posY;
	}

	public Ball(float posX, float posY, int radius, BodyType bodyType, Color color){
		this.posX = posX;
		this.posY = posY;
		this.radius = radius;
		this.bodyType = bodyType;
		this.color = color;
		node = create();
	}

	private Node create(){
		//Create an UI for ball - JavaFX code
		Circle ball = new Circle();
		ball.setRadius(radius);
		ball.setStroke(this.color); //set look and feel
		ball.setStrokeWidth(2.5);
		ball.setLayoutX(Utils.toPixelPosX(posX));
		ball.setLayoutY(Utils.toPixelPosY(posY));
		ball.setCache(true); //Cache this object for better performance

		//Create an JBox2D body defination for ball.
		BodyDef bd = new BodyDef();
		bd.type = bodyType;
		bd.position.set(posX, posY);

		CircleShape cs = new CircleShape();
		cs.m_radius = radius * 0.1f;  //We need to convert radius to JBox2D equivalent

		// Create a fixture for ball
		FixtureDef fd = new FixtureDef();
		fd.shape = cs;
		fd.density = 10f; //10 yields best results
		fd.restitution = 0.2f;

		Body body = Utils.world.createBody(bd);
		body.createFixture(fd);
		body.m_gravityScale = 8f;
		ball.setUserData(body);

		return ball;
	}
}