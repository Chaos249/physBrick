package sub_util;

import javafx.scene.shape.Line;

import java.awt.geom.Point2D;
import java.util.*;

public class RRT {

    public static final int SCREEN_SIZE = 1280;
    public ArrayList<Node> rrtNodes = new ArrayList<>();
    public ArrayList<Line> rrtLines = new ArrayList<>();

    public RRT() {
        this.rrtNodes = rrtNodes;
        this.rrtLines = rrtLines;
    }

    private int numOfConnections = 5;
    private int rrtMultiplier = 8;
    private int optimiseDistance = 40;

    public void setLineLength(int m){
        rrtMultiplier = m;
    }

    public void executeRRT(int iter, Node start) {
        this.rrtNodes.add(start);
        Random rand = new Random();

        for (int i = 0; i < iter; i++) {
            int randX = rand.nextInt(SCREEN_SIZE);
            int randY = rand.nextInt(SCREEN_SIZE);

            double closestDist = 999999;
            Node closestNode = null;
            boolean tooClose = false;

            for (Node node : this.rrtNodes) {
                double dist = Math.sqrt((node.point.x - randX) * (node.point.x - randX) + (node.point.y - randY) * (node.point.y - randY));
                if (dist < 10) {
                    tooClose = true;
                }
                if (dist < closestDist) {
                    closestDist = dist;
                    closestNode = node;
                }
            }
            if (tooClose == true || closestNode == null) {
                continue; // skips current iteration
            }
            double deltaX = rrtMultiplier * ((randX - closestNode.point.x) / closestDist);
            double deltaY = rrtMultiplier * ((randY - closestNode.point.y) / closestDist);
            float newX = (float) deltaX + closestNode.point.x;
            float newY = (float) deltaY + closestNode.point.y;
            boolean collision = false;

            Vertex ver = new Vertex(newX, newY);
            Node newNode = new Node(closestNode, ver);
            this.rrtNodes.add(newNode);

            Line line = new Line();
            line.setStartX(closestNode.point.x);
            line.setEndX(newX);
            line.setStartY(closestNode.point.y);
            line.setEndY(newY);
            this.rrtLines.add(line);
        }
        this.rrtLines = rrtLines;
    }
}
