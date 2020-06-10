package Components;

import View.GameView;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.Rectangle;
import javafx.application.Platform;

import java.util.ArrayList;

public class LaserThread implements Runnable{

    Rectangle laser;

    ArrayList<Brick> gameLayout;

    Scene scene;
    Group root;

    Brick collidedBrick;

    public LaserThread(Scene scene, Group root, Rectangle laser, ArrayList<Brick> gameLayout) {
        this.laser = laser;
        this.gameLayout = gameLayout;
        this.scene = scene;
        this.root = root;
    }

    public void runNow() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                laser.setVisible(false);
                collidedBrick.damageDurability(0.9f);
                collidedBrick.BreakCheck(scene, root);
                GameView.UpdateScore();
            }
        });
    }

    @Override
    public void run() {
        boolean collision = false;
        while(!collision) {
            if (laser.getTranslateY() < -1000) {
                return;
            }
            if (gameLayout != null) {
                for (Brick brick : gameLayout) {
                    if (laser.getBoundsInParent().intersects(brick.node.getBoundsInParent()) && brick.broken == false) {
                        this.collidedBrick = brick;
                        collision = true;
                        runNow();
                    }
                }
            }
        }
    }
}
