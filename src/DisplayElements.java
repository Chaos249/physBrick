import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class DisplayElements {

    public static Line drawXPath(int line_offset) {
        //Path path = Background.drawBackground();
        Line line = new Line();
        line.setStroke(Color.MAGENTA);
        line.setStartX(0);
        line.setEndX(Utils.WIDTH);
        line.setStartY(Utils.HEIGHT/2 + 100);
        line.setEndY(Utils.HEIGHT/2 + 100);

        Line path = new Line();
        path.setStartX(Utils.WIDTH/2);
        path.setEndX(Utils.WIDTH/2);
        path.setStartY(Utils.HEIGHT/2 + 100);
        path.setEndY(Utils.HEIGHT);

        PathTransition pt = new PathTransition();
        pt.setNode(line);
        pt.setDuration(Duration.seconds(8));
        pt.setPath(path);
        pt.setCycleCount(1);
        pt.setInterpolator(Interpolator.SPLINE(0.8, 0.1, 0.8, 0.1));
        line.setUserData(pt);

        return line;
    }

    public static Line drawYLine(int angle){
        Line line = new Line();
        line.rotateProperty();
        line.setStroke(Color.MAGENTA);
        line.setStartX((Utils.WIDTH/2) * angle);
        line.setEndX((Utils.WIDTH/2));
        line.setStartY(Utils.HEIGHT + angle);
        line.setEndY(Utils.HEIGHT/2 + 100);

        return line;
    }

    public static ArrayList<Line> makeXLines(){
        int xlineAmt = 1000;
        ArrayList<Line> lines = new ArrayList<>();
        for (int i = 0; i < xlineAmt; i++){
            Line line = DisplayElements.drawXPath(i);
            lines.add(line);
        }

         return lines;
    }

    public static ArrayList<Line> makeYLines() {
        int ylineAmt = 1000;
        ArrayList<Line> lines = new ArrayList<>();
        for (int i = 0; i < ylineAmt; i++){
            Line line1 = DisplayElements.drawYLine(i);
            Line line2 = DisplayElements.drawYLine(i * -1);
            lines.add(line1);
            lines.add(line2);
        }
        return lines;
    }

    public static ImageView makeTitle() throws FileNotFoundException {
        FileInputStream title_input = new FileInputStream("src/resources/title.jpg");
        Image image = new Image(title_input);
        ImageView imageView = new ImageView(image);
        imageView.setLayoutX((Utils.WIDTH/2) - 380);
        imageView.setLayoutY(Utils.HEIGHT - 1200);
        imageView.setOpacity(0.8);

        return imageView;
    }

    public static Button makePlayButton() throws FileNotFoundException {
        final Button btn = new Button();
        FileInputStream play_input = new FileInputStream("src/resources/play.jpg");
        Image play_img = new Image(play_input);
        ImageView btngraphic = new ImageView(play_img);
        double btnScale = 0.5;

        btn.setLayoutX((Utils.WIDTH / 4.85));
        btn.setLayoutY((Utils.HEIGHT - 550));
        btn.setGraphic(btngraphic);
        btn.setScaleX(btnScale);
        btn.setScaleY(btnScale);
        btn.setOpacity(0.8);

        return btn;
    }

    public static Button makeCreditButton() throws FileNotFoundException {
        final Button btn = new Button();
        FileInputStream play_input = new FileInputStream("src/resources/credit.jpg");
        Image play_img = new Image(play_input);
        ImageView btngraphic = new ImageView(play_img);
        double btnScale = 0.3;

        btn.setLayoutX((Utils.WIDTH / 7.3));
        btn.setLayoutY((Utils.HEIGHT - 250));
        btn.setGraphic(btngraphic);
        btn.setScaleX(btnScale);
        btn.setScaleY(btnScale);
        btn.setOpacity(0.78);

        return btn;
    }
}
