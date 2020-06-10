package Components;

import javafx.scene.Scene;
import javafx.scene.paint.Color;

public class LightingThread implements Runnable{

    Scene scene;

    public LightingThread(Scene scene) {
        this.scene = scene;
    }

    @Override
    public void run() {
        scene.setFill(Color.WHITE);
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        scene.setFill(Color.BLACK);
    }
}
