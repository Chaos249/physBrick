import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

public class Platform{

    //JavaFX UI for ball
    public Node node;

    //X and Y position of the ball in JBox2D world
    private float posX;
    private float posY;

    private float prevX;
    private float prevY;

    //Ball radius in pixels
    public static float width = 100;
    public static float height = 25;

    private Color color;

    private BodyType bodyType;
    private float restitution = 1f;

    public Platform(float posX, float posY){
        this(posX, posY, BodyType.DYNAMIC,Color.WHITE);
        this.posX = posX;
        this.posY = posY;
        this.prevX = prevX;
        this.prevY = prevY;
    }

    public Platform(float posX, float posY, BodyType bodyType, Color color){
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.bodyType = bodyType;
        this.color = color;
        node = create();
    }

//    public void setRestitution(float magVel, Body body){
//        body.getFixtureList().setRestitution(1f);
//        if (magVel < 10){
//            body.getFixtureList().setRestitution(1f);
//        } else {
//            body.getFixtureList().setRestitution(body.getFixtureList().m_restitution * ((magVel / 170)));
//            System.out.println(body.getFixtureList().getRestitution());
//        }
//    }
//
//    public static float xVel(float x, float prev_x) {
//        double x_velocity = (x - prev_x) / 0.0166;
//        return (float) x_velocity;
//    }
//
//    public static float yVel(float y, float prev_y) {
//        double y_velocity = (y - prev_y) / 0.0166;
//        return (float) y_velocity;
//    }
//
//    public static float magVel(float x, float y){
//        float resultant = (float) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
//        return resultant;
//    }
//
//    public void setPrevX(float prevX){
//        this.prevX = prevX;
//    }
//
//    public void setPrevY(float prevY) {
//        this.prevY = prevY;
//    }
//
//    public void setPosX(float posX){
//        this.posX = posX;
//    }
//
//    public void setPosY(float posY) {
//        this.posY = posY;
//    }
//
//    public float getPosX(){
//        return this.posX;
//    }
//
//    public float getPosY(){
//        return this.posY;
//    }
//
//    public float getPrevX(){
//        return this.prevX;
//    }
//
//    public float getPrevY(){
//        return this.prevY;
//    }

    private Node create(){
        //Create an UI for ball - JavaFX code
        Rectangle rec = new Rectangle();
        rec.setWidth(this.width);
        rec.setHeight(this.height);
        rec.setStroke(Color.WHITE); //set look and feel
        rec.setLayoutX(Utils.toPixelPosXPlat(posX));
        rec.setLayoutY(Utils.toPixelPosYPlat(posY));
        rec.setCache(true); //Cache this object for better performance
        rec.setVisible(false);

        //Create an JBox2D body defination for ball.
        BodyDef bd = new BodyDef();
        bd.type = bodyType;
        bd.position.set(posX, posY);
        bd.fixedRotation = true;
        bd.angularDamping = 0.8f;
        bd.bullet = true;

        PolygonShape platform_rectangle = new PolygonShape();
        platform_rectangle.setAsBox((Utils.toPosY(this.height) * 0.1f)/2.54f,(float) ((Utils.toPosX(this.width) * 0.1f) * 1.3)); // x and y are backwards

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
        rec.setUserData(body);

        return rec;
    }
}
