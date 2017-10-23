package application;

import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Rotate;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;

public class Jbox2DCirc extends JBox2DObj{

    public Jbox2DCirc(Vec2 position, Vec2 size) {
        super(position, size);

        gc=Main.canvas.getGraphicsContext2D();

        CircleShape circle =new CircleShape();
        circle.setRadius(size.x);

        fixtureDef.shape = circle;
        //fixtureDef.density = (float) (Math.PI * Math.pow(size.x,2)/2);
        fixtureDef.density = 1;
        fixtureDef.friction = 0.3f;
        fixtureDef.restitution=0.7f;

        objBody = Main.JBox.world.createBody(objDef);
        objBody.createFixture(fixtureDef);
        col= Color.rgb(0, 0, 0, 1);

    }

    @Override
    public void drawPixelObj(){

        gc.setStroke(col);
        gc.setLineWidth(2);

        Vec2 pos = Main.JBox.getBodyPixelCoord(objBody);
        float a = objBody.getAngle();

        //rotate pixel rect according to body minus angle, because y-axes is flipped
        gc.save();

        gc.translate(pos.x,pos.y);
        gc.rotate(Math.toDegrees(-a));

        gc.strokeOval(-Main.JBox.scalarWorldToPixels(this.size.x), -Main.JBox.scalarWorldToPixels(this.size.y),
                2 * Main.JBox.scalarWorldToPixels(this.size.x),
                2 * Main.JBox.scalarWorldToPixels(this.size.y));

        gc.restore();

        if (this.done()) this.isDead=true;

    }
}
