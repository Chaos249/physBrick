import javafx.scene.paint.Color;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Utils {
    //Create a JBox2D world.
    public static final World world = new World(new Vec2(0.0f, -10.0f));

    //Screen width and height
    public static final int WIDTH = 1280; //1280
    public static final int HEIGHT = 1280; //960

    //Ball radius in pixel
    public static final int BALL_SIZE = 8;

    // brick size
    public static final int SML_BRICK_SIZE = 5;
    public static final int MED_BRICK_SIZE = 10;
    public static final int LRG_BRICK_SIZE = 15;

    //Total number of balls
    //public final static int NO_OF_BALLS = 1;

    //Convert a JBox2D x coordinate to a JavaFX pixel x coordinate
    public static float toPixelPosX(float posX) {
        float x = WIDTH*posX / 100.0f;
        return x;
    }

    //Convert a JavaFX pixel x coordinate to a JBox2D x coordinate
    public static float toPosX(float posX) {
        float x = (posX*100.0f*1.0f)/WIDTH;
        return x;
    }

    //Convert a JBox2D y coordinate to a JavaFX pixel y coordinate
    public static float toPixelPosY(float posY) {
        float y = HEIGHT - (1.0f*HEIGHT) * posY / 100.0f;
        return y;
    }

    //Convert a JavaFX pixel y coordinate to a JBox2D y coordinate
    public static float toPosY(float posY) {
        float y = 100.0f - ((posY * 100 * 1.0f) /HEIGHT) ;
        return y;
    }

    //Convert a JBox2D width to pixel width
    public static float toPixelWidth(float width) {
        return WIDTH*width / 100.0f;
    }

    //Convert a JBox2D height to pixel height
    public static float toPixelHeight(float height) {
        return HEIGHT*height/100.0f;
    }

    // MY SHIT NOW
    public static Color RandomColor() {
        ArrayList<Color> colors = new ArrayList<Color>(Arrays.asList(Color.RED, Color.YELLOW, Color.LIMEGREEN, Color.AQUA, Color.BLUEVIOLET, Color.CORAL));
        Random random = new Random();
        int randomInteger = random.nextInt(6);
        return colors.get(randomInteger);
    }

    public static Color LightningColor() {
        ArrayList<Color> colors = new ArrayList<>(Arrays.asList(Color.AQUA, Color.DEEPPINK));
        Random random = new Random();
        int randomInteger = random.nextInt(2);
        return colors.get(randomInteger);
    }

    //Convert a JBox2D x coordinate to a JavaFX pixel x coordinate
    public static float toPixelPosXPlat(float posX) {
        float x = (WIDTH*posX / 100.0f);
        return x;
    }

    //Convert a JBox2D y coordinate to a JavaFX pixel y coordinate
    public static float toPixelPosYPlat(float posY) {
        float y = (HEIGHT - (1.0f*HEIGHT) * posY / 100.0f);
        return y;
    }
}