package Components;

import ElementsUtil.GameElements;
import View.PhysBrick;
import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Line;
import javafx.util.Duration;

import java.util.ArrayList;

public class PowerUp {

    public String type;
    public ImageView node;
    public PathTransition pt;
    public GamePlatform plat;
    public ArrayList<Ball> balls;
    public float xPos;
    public float yPos;

    public Group root;

    public boolean collision = false;

    public PowerUp(Group root, String type, GamePlatform plat, float posX, float posY , ArrayList<Ball> balls) {
        this.root = root;
        this.type = type;
        this.plat = plat;
        this.balls = balls;
        this.node = MakePowerUp();
        this.xPos = posX;
        this.yPos = posY;
        this.pt = MakePathTransition();
    }

    public boolean checkCollision() {
        if (this.node != null) {
            return plat.node.getBoundsInParent().intersects(this.node.getBoundsInParent());
        } else {
            return false;
        }
    }

    public ImageView MakePowerUp() { // not visible by default
        Image image = new Image(PowerUp.class.getResource("/image/" + this.type + ".png").toString());
        ImageView imageView = new ImageView(image);
        imageView.setScaleX(1.5);
        imageView.setScaleY(1.5);
        imageView.setVisible(false);
        this.root.getChildren().add(imageView);
        return imageView;
    }

    public PathTransition MakePathTransition() {
        Line path = new Line();
        path.setStartX(this.xPos);
        path.setEndX(this.xPos);
        path.setStartY(this.yPos);
        path.setEndY(this.yPos + 1500);

        PathTransition pt = new PathTransition();
        pt.setDuration(Duration.seconds(4));
        pt.setPath(path);
        pt.setNode(this.node);
        pt.setCycleCount(1);
        pt.setInterpolator(Interpolator.LINEAR);
        return pt;
    }

    public void sendPowerUp() {
        this.node.setVisible(true);
        this.pt.play();
        CheckPowerUpCollide();
    }

    public void CheckPowerUpCollide() {
        this.node.boundsInParentProperty().addListener(new ChangeListener<Bounds>() {
            @Override
            public void changed(ObservableValue<? extends Bounds> observableValue, Bounds bounds, Bounds t1) {
                if (checkCollision() && collision == false) { // checkCollision()
                    collision = true;
                    node.setVisible(false);
                    GameElements.powerupSound.setVolume(PhysBrick.VOLUME);
                    GameElements.powerupSound.play();
                    switch (type) {
                        case "strawberry_platform":
                            Thread strawberry = new Thread(new PlatformTimerThread(plat)); // PlatformTimerThread
                            strawberry.start();
                            plat.node.boundsInParentProperty().removeListener(this);
                            break;
                        case "cherry_balls":
                            Ball.embiggenBalls(balls);
                            plat.node.boundsInParentProperty().removeListener(this);
                            break;
                        case "banana_laser":
                            Thread banana = new Thread(new LaserFiringThread(plat));
                            banana.start();
                            plat.node.boundsInParentProperty().removeListener(this);
                            break;
                        default:
                            System.out.println("error cannot find correct powerup");
                    }
                }
                if (node != null && node.getTranslateY() > 1500) { // deletes powerup node and its listener if it falls bellow screen
                    plat.node.boundsInParentProperty().removeListener(this);
                    node = null;
                }
            }
        });
    }
}
