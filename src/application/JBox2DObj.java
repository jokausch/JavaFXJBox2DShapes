package application;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

interface IDrawPixels{
    void drawPixelObj();
    void killBody();
    boolean done();
}

public abstract class JBox2DObj implements IDrawPixels {

    Body objBody;
    Vec2 position;
    Vec2 size;
    BodyDef objDef = new BodyDef();
    FixtureDef fixtureDef = new FixtureDef();
    String name;
    GraphicsContext gc;
    boolean isDead;

    Color col;


    public JBox2DObj(Vec2 position, Vec2 size){

        this.position=position;
        this.size=size;

        this.name="RECTANGLE";

        // create rectangle
        objDef.type = BodyType.DYNAMIC; //movable
        objDef.position.set(this.position);
        objDef.angle=(float) Math.toRadians(Utils.randomNumberInRange(-30,30));


        fixtureDef.density = 1;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution=0.5f;
        fixtureDef.userData=this;

        col=Color.rgb(255, 0, 0, 1);
        Main.numOfBodies++;

    }

    // This function removes the bodies from the box2d world
    public void killBody() {
        Main.JBox.destroyBody(objBody);
    }


    // Is the particle ready for deletion?
    public boolean done() {
        // screen position of the body
        Vec2 pos = Main.JBox.getBodyPixelCoord(objBody);
        // Is it off the bottom of the screen?
        if (pos.y > Main.HEIGHT) {
            killBody();
            return true;
        }
        return false;
    }

}
