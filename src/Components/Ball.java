package Components;

import ElementsUtil.GameElements;
import ElementsUtil.Utils;
import View.GameView;
import View.PhysBrick;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;

import java.util.ArrayList;

public class Ball {

	//JavaFX UI for ball
	public Node node;

	//X and Y position of the ball in JBox2D world
	private float posX;
	private float posY;

	public  static int currentRadius = 12;

	private Color color;

	private BodyType bodyType;

	public Body ballBody;

	public Ball(Group root, float posX, float posY, Color color) {
		this.posX = posX;
		this.posY = posY;
		this.currentRadius = currentRadius;
		this.bodyType = BodyType.DYNAMIC;
		this.color = color;
		node = create();
		root.getChildren().add(node);
	}

	private Node create() {
		// javafx code
		Circle ball = new Circle();
		ball.setRadius(currentRadius);
		ball.setStroke(this.color); //set look and feel
		ball.setStrokeWidth(2.5);
		ball.setLayoutX(Utils.toPixelPosX(posX));
		ball.setLayoutY(Utils.toPixelPosY(posY));
		ball.setCache(true); //Cache this object for better performance
		ball.setOpacity(0.9);

		// jbox2d code
		// body def
		BodyDef bd = new BodyDef();
		bd.type = bodyType;
		bd.position.set(posX, posY);
		bd.bullet = true; // questionable

		// shape
		CircleShape cs = new CircleShape();
		cs.m_radius = currentRadius * 0.1f;

		// fixture
		FixtureDef fd = new FixtureDef();
		fd.shape = cs;
		fd.density = 10f; //10 yields best results
		fd.restitution = 0.2f;

		// body
		Body body = GameView.world.createBody(bd);
		body.createFixture(fd);
		body.m_gravityScale = 8f;
		body.setAngularDamping(15f);

		this.ballBody = body;

		ball.setUserData(body); // important, sets the jbox2d object as the javafx nodes userdata

		return ball;
	}

	public double getVelocityInfo() {
		Body body = (Body) this.node.getUserData();
		Vec2 velocityVector = body.getLinearVelocity();
		double delta = Math.pow(velocityVector.x, 2) + Math.pow(velocityVector.y, 2);
		return Math.sqrt(delta);
	}

	public static void embiggenBalls(ArrayList<Ball> balls) {
		for (Ball ball : balls) {
			Fixture fixture = ball.ballBody.getFixtureList();
			CircleShape cs = (CircleShape) fixture.getShape();
			cs.setRadius((currentRadius * 2) * 0.1f);

			Circle circle = (Circle) ball.node;
			circle.setRadius(currentRadius * 2);
		}
		GameElements.enlargeSound.setVolume(PhysBrick.VOLUME);
		GameElements.enlargeSound.play();
	}

	public static void microBalls(ArrayList<Ball> balls) {
		for (Ball ball : balls) {
			Fixture fixture = ball.ballBody.getFixtureList();
			CircleShape cs = (CircleShape) fixture.getShape();
			cs.setRadius((currentRadius) * 0.1f);

			Circle circle = (Circle) ball.node;
			circle.setRadius(currentRadius);
		}
	}
}