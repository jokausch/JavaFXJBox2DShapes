package application;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class JBox2DPolyGen {

    static float offset = 5;
    static ArrayList<Vec2> edgeList = new ArrayList<>();
    static double[][] polyPoints;
    static Vec2[] polyPointsJBox;
    static PolygonShape poly = new PolygonShape();

    public JBox2DPolyGen(PVector position, int edges, float r) {

    }

    public static double[][] createPolyPixel(PVector position, int edges, float r) {

        polyPoints = new double[2][edges];

        float deltaAngle = (float) Math.PI * 2f / edges;

        for (int i = 0; i < edges; i++) {

            offset = Utils.randomFloatNumberInRange(0f, r);
            polyPoints[0][i] = (offset + r) * Math.cos(i * deltaAngle) + position.x;
            polyPoints[1][i] = (offset + r) * Math.sin(i * deltaAngle) + position.y;
        }

        return polyPoints;
    }


    public static PolygonShape createPolyJBox2D(int edges, float r) {


        polyPointsJBox = new Vec2[edges];

        float deltaAngle = (float) Math.PI * 2f / edges;

        for (int i = 0; i < edges; i++) {

            offset = Utils.randomFloatNumberInRange(0f, r);

            edgeList.add(new Vec2((float) ((offset + r) * Math.cos(i * deltaAngle)),
                    (float) ((offset + r) * Math.sin(i * deltaAngle))));

        }
        sortPointsClockwise(edgeList);

        for (int i = 0; i < edgeList.size(); i++) {

            polyPointsJBox[i] = Main.JBox.vectorPixelsToWorld(edgeList.get(i));
        }
        poly.set(polyPointsJBox, polyPointsJBox.length);

        edgeList.clear();
        return poly;
    }

    public static Vec2[] createPolyJBox2DVec(int edges, float r) {


        polyPointsJBox = new Vec2[edges];

        float deltaAngle = (float) Math.PI * 2f / edges;

        for (int i = 0; i < edges; i++) {

            offset = Utils.randomFloatNumberInRange(0f, r);

            edgeList.add(new Vec2((float) ((offset + r) * Math.cos(i * deltaAngle)),
                    (float) ((offset + r) * Math.sin(i * deltaAngle))));

        }

        sortPointsClockwise(edgeList);

        for (int i = 0; i < edges; i++) {
            polyPointsJBox[i] = Main.JBox.vectorPixelsToWorld(edgeList.get(i));
        }
        edgeList.clear();
        return polyPointsJBox;
    }


    public static void sortPointsClockwise(ArrayList<Vec2> points) {
        float averageX = 0;
        float averageY = 0;

        for (Vec2 point : points) {
            averageX += point.x;
            averageY += point.y;
        }

        final float finalAverageX = averageX / points.size();
        final float finalAverageY = averageY / points.size();

        Comparator<Vec2> comparator = new Comparator<Vec2>() {
            public int compare(Vec2 lhs, Vec2 rhs) {
                double lhsAngle = Math.atan2(lhs.y - finalAverageY, lhs.x - finalAverageX);
                double rhsAngle = Math.atan2(rhs.y - finalAverageY, rhs.x - finalAverageX);

                // Depending on the coordinate system, you might need to reverse these two conditions
                if (lhsAngle > rhsAngle) return -1;
                if (lhsAngle < rhsAngle) return 1;

                return 0;
            }
        };

        Collections.sort(points, comparator);
    }

    public static void sortPointsCounterClockwise(ArrayList<Vec2> points) {
        sortPointsClockwise(points);
        Collections.reverse(points);
    }

}
