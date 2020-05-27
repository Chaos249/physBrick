package Components;

import javafx.application.Platform;

public class LaserFiringThread implements Runnable {

    GamePlatform plat;

    public LaserFiringThread(GamePlatform plat) {
        this.plat = plat;
    }

    public void runNow() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                plat.setActiveLaserThread(this);
                plat.engageLasers();
            }
        });
    }

    @Override
    public void run() {
        if (plat.laserOn == false) {
            plat.laserOn = true;
            while (plat.laserTimer < plat.maxLaserTime) {
                try {
                    Thread.sleep(250);
                    runNow();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                plat.laserTimer ++;
            }
            if (plat.laserTimer >= plat.maxLaserTime) {
                System.out.println("laser off");
                plat.laserOn = false;
                plat.laserTimer = 0;
            }
        } else {
            plat.laserTimer = 0;
        }

    }
}
