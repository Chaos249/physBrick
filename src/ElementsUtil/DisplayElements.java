package ElementsUtil;

import Components.LightingThread;
import View.PhysBrick;
import View.SettingsView;
import javafx.animation.AnimationTimer;
import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;
import SubUtil.Node;
import SubUtil.RRT;
import SubUtil.Vertex;
import java.util.ArrayList;
import java.util.Random;

public class DisplayElements {

    public static AudioClip thunder = new AudioClip("file:src/resources/sound/thunder.wav"); //thunder.wav

    // generates rrt configuration
    public static ArrayList<Line> MakeLightning(int iterations) {
        ArrayList<Line> lightning = new ArrayList<>();
        RRT rrt = new RRT();
        rrt.setLineLength(12);
        Color color = Utils.LightningColor();

        Random rand = new Random();
        Vertex ver = new Vertex(rand.nextInt(Utils.WIDTH), 0); //y = View.UtilElements.Utils.HEIGHT or 0
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
    public static AnimationTimer DrawLightning(Scene scene, Group root, ArrayList<Line> lines, int lightningSpeed, double soundRate) {
        AnimationTimer at = new AnimationTimer() {
            boolean playAudio = false;
            boolean flip = false;
            int count = 1;
            int iter = 0;

            @Override
            public void handle(long l) {
                if (playAudio == false) {
                    thunder.setVolume(PhysBrick.VOLUME / 2);
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
        Thread thread = new Thread(new LightingThread(scene));
        thread.start();
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

    // adds new 80s tron grid to passed in root
    public static ArrayList<ArrayList<Line>> EightiesAnim(boolean debug_preload, Stage primaryStage, Group root, Color color) {
        ArrayList<Line> xlines = DisplayElements.MakeXLines(root);
        ArrayList<Line> ylines = DisplayElements.MakeYLines(root);
        ChangeLinesColor(color, xlines, ylines);

        AnimationTimer at = new AnimationTimer() {
            int startTime = 380; // 380 is best
            boolean game_on = false;
            int count = 0;
            int xline_idx = 0;

            @Override
            public void handle(long l) {
                // disables annoyances
                if (debug_preload) {
                    startTime = 1;
                }
                // sends the horizontal lines for title graphic
                if (count % 80 == 0 && xline_idx < xlines.size()) {
                    PathTransition pt = (PathTransition) xlines.get(xline_idx).getUserData();
                    pt.play();
                    xline_idx += 1;
                    if ((xline_idx + 1) == xlines.size()) {
                        xline_idx = 0;
                    }
                }
                count += 1;
                //starts the program
                if (count % startTime == 0 && game_on == false) {
                    primaryStage.show();
                    if (PhysBrick.DEBUG == false) {
                        SettingsView.musicPlayer.play();
                    }
                    game_on = true;
                }
            }
        };
        at.start();

        ArrayList<ArrayList<Line>> arr = new ArrayList<>();
        arr.add(xlines);
        arr.add(ylines);

        return arr;
    }

    // this only exists to add already playing animations to new game levels because they get dynamically created by user
    public static void AddEightiesMatrixRoot(Group root, ArrayList<ArrayList<Line>> arr) {
        for (ArrayList<Line> lines : arr) {
            root.getChildren().addAll(lines);
        }
    }

    public static void CustomLightning(Scene scene, Group root, int iterations, int lightningSpeed, float soundRate) {
        ArrayList<Line> RRT = DisplayElements.MakeLightning(iterations); //800
        root.getChildren().addAll(RRT);
        AnimationTimer at = DisplayElements.DrawLightning(scene, root, RRT, lightningSpeed, soundRate); //35, 0.8
        at.start();
    }
}
