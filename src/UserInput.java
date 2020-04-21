import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.joints.MouseJoint;
import org.jbox2d.dynamics.joints.MouseJointDef;

public class UserInput {

    public static MouseJointDef makeMouseJoint(Platform plat){
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

        return jointDef;
    }

    /**
     * PLATFORM PLACEMENT
     */
    // mouse joint control and platform placement
    public static void MakeMouseJointEventHandler(Scene scene, Platform plat) {
        MouseJointDef jointDef = UserInput.makeMouseJoint(plat);
        MouseJoint m_joint = (MouseJoint) Utils.world.createJoint(jointDef);
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
    }
}
