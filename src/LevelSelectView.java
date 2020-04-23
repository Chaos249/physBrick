import javafx.scene.Group;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class LevelSelectView {

    public static void ShowLevelSelect() {
        
    }

    public static ArrayList<Brick> MakeBrickLayout1(Group root){
        ArrayList<Brick> brickList = new ArrayList<>();
        // bottom row
        Brick brick1 = new Brick(Utils.WIDTH / 2, Utils.HEIGHT / 2 - 100, 50, 2, Color.CRIMSON, root);
        brick1.initDurabilityText(root);
        Brick brick2 = new Brick(Utils.WIDTH / 2 - 55, Utils.HEIGHT / 2 - 100, 50, 2, Color.WHITE, root);
        brick2.initDurabilityText(root);
        Brick brick3 = new Brick(Utils.WIDTH / 2 + 55, Utils.HEIGHT / 2 - 100, 50, 2, Color.WHITE, root);
        brick3.initDurabilityText(root);
        // top row
        Brick brick4 = new Brick(Utils.WIDTH / 2 + 55, Utils.HEIGHT / 2 - 155, 50, 2, Color.WHITE, root);
        brick4.initDurabilityText(root);
        Brick brick5 = new Brick(Utils.WIDTH / 2, Utils.HEIGHT / 2 - 155, 50, 2, Color.WHITE, root);
        brick5.initDurabilityText(root);
        Brick brick6 = new Brick(Utils.WIDTH / 2 - 55, Utils.HEIGHT / 2 - 155, 50, 2, Color.WHITE, root);
        brick6.initDurabilityText(root);

        brickList.add(brick1);
        brickList.add(brick2);
        brickList.add(brick3);
        brickList.add(brick4);
        brickList.add(brick5);
        brickList.add(brick6);
        return brickList;
    }
}
