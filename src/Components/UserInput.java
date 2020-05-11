package Components;

import ElementsUtil.Utils;
import View.GameView;
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
        Body ground = GameView.world.createBody(ground_bodydef);
        FixtureDef groundFd = new FixtureDef();
        groundFd.shape = ground_shape;
        groundFd.isSensor = false;
        ground.createFixture(groundFd);

        MouseJointDef jointDef = new MouseJointDef();
        jointDef.bodyA = ground;
        jointDef.bodyB = (Body) plat.node.getUserData();
        jointDef.collideConnected = false;
        jointDef.frequencyHz = 5000f; //5000f works well
        jointDef.maxForce = 100000f; //100000f works well
        //jointDef.dampingRatio = 10f;

        return jointDef;
    }

    /**
     * PLATFORM PLACEMENT
     */
    // mouse joint control and platform placement
    public static void MakeMouseJointEventHandler(Scene scene, Platform plat) {
        MouseJointDef jointDef = UserInput.makeMouseJoint(plat);
        MouseJoint m_joint = (MouseJoint) GameView.world.createJoint(jointDef);
        EventHandler<MouseEvent> mouseEvent = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                float Xmouse = (float) mouseEvent.getX();
                float Ymouse = (float) mouseEvent.getY();

                Vec2 vec = new Vec2(Utils.toPosX(Xmouse) - (Utils.WIDTH / 25.6f) + 3.8f, Utils.toPosY(Ymouse) - (Utils.HEIGHT / 25.9f) + 10); //50, 37
                m_joint.setTarget(vec);
            }
        };
        scene.setOnMouseMoved(mouseEvent);
    }
}
