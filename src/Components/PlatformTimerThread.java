package Components;

import javafx.application.Platform;

/**
 *  CURRENTLY BROKEN - PLATFORM GETS SMALL AGAIN TOO QUICKLY
 */

public class PlatformTimerThread implements Runnable{

    GamePlatform plat;

    public PlatformTimerThread(GamePlatform plat) {
        this.plat = plat;
    }

    public void runNow() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if (plat.enlarged == true) {
                    plat.enlargePlatform();
                } else {
                    plat.ensmallPlatform();
                    plat.extensionTimer = 0;
                }
            }
        });
    }

    @Override
    public void run() {
        if (plat.enlarged == false) {
            plat.enlarged = true;
            runNow();
            while (plat.extensionTimer < plat.maxExtensionTime) {
                try {
                    Thread.sleep(250);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                plat.extensionTimer ++;
            }
            plat.enlarged = false;
            runNow();
        }
        if (plat.enlarged == true) {
            plat.extensionTimer = 0;
        }

    }
}


