import javafx.animation.AnimationTimer;
import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.util.Duration;
import sub_util.Node;
import sub_util.RRT;
import sub_util.Vertex;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;

public class DisplayElements {

    public static AudioClip thunder = new AudioClip("file:src/resources/thunder.wav"); //thunder.wav

    //creates title image
    public static ImageView MakeTitle(Group root) throws FileNotFoundException {
        FileInputStream title_input = new FileInputStream("src/resources/title.jpg");
        Image image = new Image(title_input);
        ImageView imageView = new ImageView(image);
        imageView.setLayoutX((Utils.WIDTH / 2) - 380);
        imageView.setLayoutY(Utils.HEIGHT - 1200);
        imageView.setOpacity(0.8);
        root.getChildren().add(imageView);
        return imageView;
    }

    // creates plat button
    public static Button MakePlayButton(Group root) throws FileNotFoundException {
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
        btn.setPadding(Insets.EMPTY);

        root.getChildren().add(btn);
        return btn;
    }

    // creates credit button
    public static Button MakeCreditButton(Group root) throws FileNotFoundException {
        final Button btn = new Button();
        FileInputStream play_input = new FileInputStream("src/resources/credit.jpg");
        Image play_img = new Image(play_input);
        ImageView btngraphic = new ImageView(play_img);
        double btnScale = 0.3;

        btn.setLayoutX((Utils.WIDTH / 7));
        btn.setLayoutY((Utils.HEIGHT - 250));
        btn.setGraphic(btngraphic);
        btn.setScaleX(btnScale);
        btn.setScaleY(btnScale);
        btn.setOpacity(0.7);
        btn.setPadding(Insets.EMPTY);

        root.getChildren().add(btn);
        return btn;
    }

    // generates rrt configuration
    public static ArrayList<Line> MakeLightning(int iterations) {
        ArrayList<Line> lightning = new ArrayList<>();
        RRT rrt = new RRT();
        rrt.setLineLength(12);
        Color color = Utils.LightningColor();

        Random rand = new Random();
        Vertex ver = new Vertex(rand.nextInt(Utils.WIDTH), 0); //y = Utils.HEIGHT or 0
        Node startNode = new Node(ver);
        rrt.executeRRT(iterations, startNode);

        for (Line line : rrt.rrtLines) {
            line.setStrokeWidth(2);
            line.setStroke(color); // steelblue, slateblue, cornflower
            line.setVisible(false);
            lightning.add(line);
        }
        return lightning;
    }

    // generates rrt and draws the configuration and then undraws it
    public static AnimationTimer DrawLightning(Group root, ArrayList<Line> lines, int lightningSpeed, double soundRate) {
        AnimationTimer at = new AnimationTimer() {
            boolean playAudio = false;
            boolean flip = false;
            int count = 1;
            int iter = 0;

            @Override
            public void handle(long l) {
                if (playAudio == false) {
                    thunder.setVolume(0.1);
                    thunder.setBalance(0.1);
                    thunder.setRate(soundRate); //0.3 transition
                    thunder.play();
                    playAudio = true;
                }
                if (count % 1 == 0 && iter < lines.size() && flip == false) {
                    for (int i = 0; i < lightningSpeed; i++) {
                        if (iter + 1 > lines.size()) {
                            break;
                        }
                        lines.get(iter).setVisible(true);
                        iter++;
                    }
                    if (iter + 1 >= lines.size()) {
                        flip = true;
                        iter--;
                    }
                }
                if (count % 1 == 0 && flip == true) {
                    for (int i = 0; i < lightningSpeed; i++) {
                        if (iter == -1) {
                            break;
                        }
                        lines.get(iter).setVisible(false);
                        root.getChildren().remove(lines.get(iter));
                        iter--;
                    }
                }
                count++;
            }
        };
        return at;
    }

    /**
     * CODE FOR DRAWING 80s STYLE LINE FIELD
     */
    // creates path for horizontal lines to follow in title graphic
    public static Line drawXPath(int line_offset) {
        //Path path = Background.drawBackground();
        Line line = new Line();
        line.setStroke(Color.MAGENTA);
        line.setOpacity(0.4);
        line.setStartX(0);
        line.setEndX(Utils.WIDTH);
        line.setStartY(Utils.HEIGHT / 2 + 100);
        line.setEndY(Utils.HEIGHT / 2 + 100);

        Line path = new Line();
        path.setStartX(Utils.WIDTH / 2);
        path.setEndX(Utils.WIDTH / 2);
        path.setStartY(Utils.HEIGHT / 2 + 100);
        path.setEndY(Utils.HEIGHT + 2);

        PathTransition pt = new PathTransition();
        pt.setNode(line);
        pt.setDuration(Duration.seconds(8));
        pt.setPath(path);
        pt.setCycleCount(1);
        pt.setInterpolator(Interpolator.SPLINE(0.8, 0.1, 0.8, 0.1));
        line.setUserData(pt);

        return line;
    }

    // creates vertical lines for title graphic
    public static Line drawYLine(int angle) {
        Line line = new Line();
        line.rotateProperty();
        line.setStroke(Color.MAGENTA);
        line.setOpacity(0.4);
        line.setStartX((Utils.WIDTH / 2) * angle);
        line.setEndX((Utils.WIDTH / 2));
        line.setStartY(Utils.HEIGHT + angle);
        line.setEndY(Utils.HEIGHT / 2 + 100);

        return line;
    }

    // draws the horizontal lines for title graphic
    public static ArrayList<Line> MakeXLines(Group root) {
        int xlineAmt = 1000;
        ArrayList<Line> lines = new ArrayList<>();
        for (int i = 0; i < xlineAmt; i++) {
            Line line = DisplayElements.drawXPath(i);
            lines.add(line);
        }
        root.getChildren().addAll(lines);
        return lines;
    }

    // draws the vertical lines for title graphic
    public static ArrayList<Line> MakeYLines(Group root) {
        int ylineAmt = 1000;
        ArrayList<Line> lines = new ArrayList<>();
        for (int i = 0; i < ylineAmt; i++) {
            Line line1 = DisplayElements.drawYLine(i);
            Line line2 = DisplayElements.drawYLine(i * -1);
            lines.add(line1);
            lines.add(line2);
        }
        root.getChildren().addAll(lines);
        return lines;
    }

    // changes title graphic color
    public static void ChangeLinesColor(Color color, ArrayList<Line> xlines, ArrayList<Line> ylines) {
        for (Line line : xlines) {
            line.setStroke(color);
        }
        for (Line line : ylines) {
            line.setStroke(color);
        }
    }


}
