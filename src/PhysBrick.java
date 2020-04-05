import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.joints.MouseJoint;
import org.jbox2d.dynamics.joints.MouseJointDef;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class PhysBrick extends Application {

	// start game immediately and disables music if true
	public static boolean DEBUG = true;

	public static void main(String[] args) {
		Application.launch(args);
	}

	// javafx main start
	@Override
	public void start(Stage primaryStage) throws FileNotFoundException {

		primaryStage.setTitle("");
		primaryStage.setFullScreen(false);
		primaryStage.setResizable(false);

		// javafx display containers
		Group root = new Group(); //Create a group for holding all objects on the screen
		final Scene scene = new Scene(root, Utils.WIDTH, Utils.HEIGHT,Color.BLACK);

		// game elements
		Platform plat = new Platform(Utils.toPosX(Utils.WIDTH/2), Utils.toPosX(Utils.HEIGHT/2));
		root.getChildren().add(plat.node);
		ArrayList<Ball> balls = new ArrayList<Ball>();

		// mouse joint
		PolygonShape ground_shape = new PolygonShape();
		ground_shape.setAsBox(0, 0);
		BodyDef ground_bodydef = new BodyDef();
		ground_bodydef.type = BodyType.STATIC;
		Body ground = Utils.world.createBody(ground_bodydef);
		FixtureDef groundFd = new FixtureDef();
		groundFd.shape = ground_shape;
		groundFd.isSensor = false;
		ground.createFixture(groundFd);

		MouseJointDef jointDef = new MouseJointDef();
		jointDef.bodyA = ground;
		jointDef.bodyB = (Body) plat.node.getUserData();
		jointDef.collideConnected = false;
		jointDef.frequencyHz = 5000f; //500f works well
		jointDef.maxForce = 100000000f; //100000f works well
		//jointDef.dampingRatio = 10f;

		MouseJoint m_joint = (MouseJoint) Utils.world.createJoint(jointDef);

		// bounding box
		Utils.addGround(100, 11);
		Utils.addWall(0,100,1,105); //Left wall
		Utils.addWall(100,100,1,105); //Right wall
		Utils.addCeiling(100, 11);


		final Timeline timeline = new Timeline();
		timeline.setCycleCount(Timeline.INDEFINITE);
		Duration duration = Duration.seconds(1.0/60.0); // duration for frame.

		// draw balls
		EventHandler<MouseEvent> addBall= new EventHandler<MouseEvent>(){
			public void handle(MouseEvent me) {

				float dragX = (float) me.getSceneX();
				float dragY = (float) me.getSceneY();

				//Draw ball on this location. Set balls body type to static.
				Ball hurdle = new Ball(Utils.toPosX(dragX), Utils.toPosY(dragY),12,BodyType.DYNAMIC, Utils.randomColor());
				balls.add(hurdle);

				//Add ball to the root group
				root.getChildren().add(hurdle.node);

			}
		};
		scene.setOnMouseDragged(addBall);

		// mouse joint control
		EventHandler<MouseEvent> mouseEvent = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				Body body_p = (Body) plat.node.getUserData();
				float Xmouse = (float) mouseEvent.getX();
				float Ymouse = (float) mouseEvent.getY();

				Vec2 vec = new Vec2(Utils.toPosX(Xmouse) - (Utils.WIDTH / 25.6f), Utils.toPosY(Ymouse) - (Utils.HEIGHT / 25.9f)); //50, 37
				m_joint.setTarget(vec);
			}
		};
		scene.setOnMouseMoved(mouseEvent);

		// game loop
		// on trigger it executes a world time step and moves the game elements to new position
		EventHandler<ActionEvent> ae = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				//Create time step. Set Iteration count 8 for velocity and 3 for positions
				Utils.world.step(1.0f/60.f, 8, 3);

				//Move balls to the new position computed by JBox2D
				Body body_p = (Body)plat.node.getUserData();

				float xpos_p = Utils.toPixelPosX(body_p.getPosition().x);
				float ypos_p = Utils.toPixelPosY(body_p.getPosition().y);

				plat.node.setLayoutX(xpos_p - Platform.width/2);
				plat.node.setLayoutY(ypos_p - Platform.height/2);

				for (int i=0; i < balls.size(); i++){
					Body body_b = (Body)balls.get(i).node.getUserData();
					float xpos = Utils.toPixelPosX(body_b.getPosition().x);
					float ypos = Utils.toPixelPosY(body_b.getPosition().y);

					balls.get(i).node.setLayoutX(xpos);
					balls.get(i).node.setLayoutY(ypos);
				}
			}
		};
		KeyFrame frame = new KeyFrame(duration, ae, null,null);
		timeline.getKeyFrames().add(frame);

		// title graphic
		ArrayList<Line> xlines = DisplayElements.makeXLines();
		ArrayList<Line> ylines = DisplayElements.makeYLines();
		root.getChildren().addAll(xlines);
		root.getChildren().addAll(ylines);

		// title image
		ImageView title_view = DisplayElements.makeTitle();
		root.getChildren().add(title_view);

		// play button
		Button play_btn = DisplayElements.makePlayButton();
		root.getChildren().add(play_btn);

		//credit button
		Button credit_btn = DisplayElements.makeCreditButton();
		root.getChildren().add(credit_btn);

		// music
		AudioClip music = new AudioClip(this.getClass().getResource("Common-Fight.wav").toString());
		music.setVolume(0.1);

		// timer that start the game
		AnimationTimer at = new AnimationTimer() {
			int startTime = 380; // 380 is best
			boolean music_on = true;
			boolean game_on = false;
			int count = 0;
			int xline_idx = 0;
			@Override
			public void handle(long l) {
				if (DEBUG){
					startTime = 1;
					music_on = false;
				}
				if (music.isPlaying() == false && game_on && music_on){
					music.play();
				}
				if (count % 80 == 0 && xline_idx < xlines.size()){
					PathTransition pt = (PathTransition) xlines.get(xline_idx).getUserData();
					pt.play();
					xline_idx += 1;
				}
				count += 1;
				if (count % startTime == 0 && game_on == false){
					primaryStage.show();
					if (music_on){
						music.play();
					}
					game_on = true;
				}
			}
		};

		// btn transition to game from title
		play_btn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				timeline.playFromStart();
				play_btn.setVisible(false);
				credit_btn.setVisible(false);
				Rectangle p = (Rectangle) plat.node;
				title_view.setVisible(false);
				plat.node.setVisible(true);
				for (Line line : xlines){
					line.setVisible(false);
				}
				for (Line line : ylines){
					line.setVisible(false);
				}
				at.stop();

				music.stop();
			}
		});

		primaryStage.setScene(scene);

		// starts ui
		at.start();
	}
}