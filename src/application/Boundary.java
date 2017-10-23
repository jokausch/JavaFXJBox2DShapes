package application;
// The Nature of Code

// <http://www.shiffman.net/teaching/nature>
// Spring 2012
// Box2DProcessing example

// A fixed boundary class (now incorporates angle)

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;

class Boundary {

	GraphicsContext gc = Main.canvas.getGraphicsContext2D();

	// A boundary is a simple rectangle with x,y,width,and height
	float x;
	float y;
	float w;
	float h;
	// But we also have to make a body for box2d to know about it
	Body b;

	Boundary(float x_, float y_, float w_, float h_, float a) {
		x = x_;
		y = y_;
		w = w_;
		h = h_;

		// Define the polygon
		PolygonShape sd = new PolygonShape();
		// Figure out the box2d coordinates
		float box2dW = Main.JBox.scalarPixelsToWorld(w / 2);
		float box2dH = Main.JBox.scalarPixelsToWorld(h / 2);
		// We're just a box
		sd.setAsBox(box2dW, box2dH);

		// Create the body
		BodyDef bd = new BodyDef();
		bd.type = BodyType.STATIC;
		bd.angle = (float) Math.toRadians(a);
		bd.position.set(Main.JBox.coordPixelsToWorld(x + w / 2, y + h / 2));
		b = Main.JBox.createBody(bd);

		// Attached the shape to the body using a Fixture
		b.createFixture(sd, 1);
	}

	// Draw the boundary, it doesn't move so we don't have to ask the Body for
	// location
	void display() {

		gc.setStroke(Color.BLACK);

		// rotate pixel rect according to body minus angle, because y-axes is flipped
		gc.save();
		gc.translate(x + w / 2, y + h / 2);
		gc.rotate(-(float) Math.toDegrees(b.getAngle()));

		gc.fillRect(-w / 2, -h / 2, w, h);

		gc.restore();

	}
}