package application;


import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;

public class Jbox2DRect extends JBox2DObj{


    public Jbox2DRect(Vec2 position, Vec2 size) {
        super(position, size);

        PolygonShape rect = new PolygonShape();
        rect.setAsBox(this.size.x, this.size.y);

        fixtureDef.shape = rect;
        //fixtureDef.density = this.size.x*this.size.y / 10f;
        fixtureDef.density = 1;

        objBody = Main.JBox.world.createBody(objDef);
        objBody.createFixture(fixtureDef);

        gc=Main.canvas.getGraphicsContext2D();

    }

    @Override
    public void drawPixelObj(){

        gc.setStroke(col);
        gc.setLineWidth(2);

        Vec2 pos = Main.JBox.getBodyPixelCoord(objBody);
        // Get its angle of rotation
        float a = objBody.getAngle();

        gc.save();

        gc.translate(pos.x,pos.y);
        gc.rotate(Math.toDegrees(-a));

        gc.strokeRoundRect(-Main.JBox.scalarWorldToPixels(this.size.x),-Main.JBox.scalarWorldToPixels(this.size.y),
                2 * Main.JBox.scalarWorldToPixels(this.size.x),
                2 * Main.JBox.scalarWorldToPixels(this.size.y),10,10);

        gc.restore();

        if (this.done()) this.isDead=true;
    }
}
