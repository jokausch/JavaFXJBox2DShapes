package application;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;

class JBox2DCustomShape {

    double [] xPoints,yPoints;
    boolean isDead=false;
    int edges, radius;
    GraphicsContext g=Main.canvas.getGraphicsContext2D();
    Vec2[] vertices;

    // We need to keep track of a Body and a width and height
    Body body;

    // Constructor
//    JBox2DCustomShape(float x, float y) {
//        // Add the box to the box2d world
//        makeBody(new Vec2(x, y));
//        Main.numOfBodies++;
//    }

    JBox2DCustomShape(float x, float y, int edges, int radius) {
        // Add the box to the box2d world
        Main.numOfBodies++;
        this.edges=edges;
        this.radius=radius;
        makeBody(new Vec2(x, y));
    }

    // This function removes the particle from the box2d world
    void killBody() {
        Main.JBox.destroyBody(body);
    }

    // Is the particle ready for deletion?
    boolean done() {
        // Let's find the screen position of the particle
        Vec2 pos = Main.JBox.getBodyPixelCoord(body);
        // Is it off the bottom of the screen?
        if (pos.y > Main.HEIGHT) {
            killBody();
            return true;
        }
        return false;
    }

    // Drawing the box
    void display() {
        // We look at each body and get its screen position
        Vec2 pos = Main.JBox.getBodyPixelCoord(body);
        // Get its angle of rotation
        float a = body.getAngle();

        Fixture f = body.getFixtureList();
        PolygonShape ps = (PolygonShape) f.getShape();
        xPoints = new double[ps.getVertexCount()];
        yPoints = new double[ps.getVertexCount()];

        g.save();
        g.translate(pos.x,pos.y);
        g.rotate(Math.toDegrees(-a));

        //println(vertices.length);
        // For every vertex, convert to pixel vector
        for (int i = 0; i < ps.getVertexCount(); i++) {
            Vec2 v = Main.JBox.vectorWorldToPixels(ps.getVertex(i));
            xPoints[i]=v.x;
            yPoints[i]=v.y;
        }

        g.setLineWidth(2);
        g.setStroke(Color.rgb(0, 0, 255, 1));
        g.strokePolygon(xPoints, yPoints, ps.getVertexCount());
        g.restore();

        if (this.done()) this.isDead=true;
    }

    void makeBody(Vec2 center) {

        PolygonShape sd = new PolygonShape();

        vertices = new Vec2[this.edges];

        vertices=JBox2DPolyGen.createPolyJBox2DVec(this.edges, this.radius);

//        vertices[0] = Main.JBox.vectorPixelsToWorld(new Vec2(-15, 25));
//        vertices[1] = Main.JBox.vectorPixelsToWorld(new Vec2(15, 0));
//        vertices[2] = Main.JBox.vectorPixelsToWorld(new Vec2(20, -15));
//        vertices[3] = Main.JBox.vectorPixelsToWorld(new Vec2(-10, -10));

        sd.set(vertices, vertices.length);

        // Define the body and make it from the shape
        BodyDef bd = new BodyDef();
        bd.type = BodyType.DYNAMIC;
        bd.position.set(Main.JBox.coordPixelsToWorld(center));

        body = Main.JBox.createBody(bd);
        body.createFixture(sd, 1.0f);

        // Give it some initial random velocity
        body.setLinearVelocity(new Vec2(Utils.randomNumberInRange(-5, 5), Utils.randomNumberInRange(2, 5)));
        body.setAngularVelocity(Utils.randomNumberInRange(-5, 5));
    }
}