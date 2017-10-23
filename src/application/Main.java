package application;
	
import javafx.animation.AnimationTimer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.jbox2d.collision.shapes.ChainShape;
import org.jbox2d.common.Vec2;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;

import java.util.ArrayList;
import java.util.Random;


public class Main extends Application {
	
	static Box2DProcessing JBox;
	static Canvas canvas=new Canvas();
	GraphicsContext g;
	static float HEIGHT;
	static float WIDTH;
	static ArrayList<JBox2DCustomShape> shapeList=new ArrayList<>();
    static ArrayList<JBox2DObj> objList = new ArrayList<>();
	static  double[] polyCoordsX,polyCoordsY;
    static ChainShape chainShape=new ChainShape();
    double frameRate;
    double old;
    int cycleCount;
    static long numOfBodies;
    Random rand=new Random();
    float tempSize;

	@Override
	public void start(Stage primaryStage) {
		BorderPane root = new BorderPane();
        Pane centerPane = new Pane();
        centerPane.autosize();
        centerPane.setStyle("-fx-background-color: #ADD8E6;");
        centerPane.getChildren().add(canvas);
        root.setCenter(centerPane);

        canvas.widthProperty().bind(centerPane.widthProperty());
        canvas.heightProperty().bind(centerPane.heightProperty());

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("JavaFX JBox2D Stuff by JoKa 2017");
        primaryStage.setResizable(false);
        primaryStage.show();

        HEIGHT= (float)canvas.getHeight();
        WIDTH= (float)canvas.getWidth();

        JBox = new Box2DProcessing(20);
        g = canvas.getGraphicsContext2D();

        JBox.createWorld(new Vec2(0, -9.81f), true, true);
        createWorldBorders();

        canvas.setOnMouseDragged((MouseEvent event) -> {


        });

        canvas.setOnMouseClicked((MouseEvent event) -> {


        });


        AnimationTimer animator = new AnimationTimer() {
            @Override
            public void handle(long currentNanoTime) {

                cycleCount++;

                if (currentNanoTime - old >= 1000000000) {
                    frameRate = 1000000000 / ((currentNanoTime - old) / cycleCount);
                    cycleCount = 0;
                    old = System.nanoTime();
                }

                JBox.step();

                g.clearRect(0, 0, WIDTH, HEIGHT);

                g.setLineWidth(4);
                g.setStroke(Color.rgb(0, 153, 0, 1));
                g.setFill(Color.rgb(80, 57, 49, 1));
                g.fillPolygon(polyCoordsX,polyCoordsY,7);
                g.strokePolyline(polyCoordsX,polyCoordsY,7);

                if (rand.nextFloat() < 0.02) {

                    shapeList.add(new JBox2DCustomShape(WIDTH/2, HEIGHT/6,Utils.randomNumberInRange(3,7),
                            Utils.randomNumberInRange(10,20)));

                }

                if (rand.nextFloat() < 0.05) {

                    objList.add(new Jbox2DRect(new Vec2(Main.JBox.coordPixelsToWorld(WIDTH/2, HEIGHT/6)),
                            new Vec2(Utils.randomFloatNumberInRange(0.5f, 1.5f),
                                    Utils.randomFloatNumberInRange(0.2f, 1.5f))));

                }

                if (rand.nextFloat() < 0.04) {

                    tempSize=Utils.randomFloatNumberInRange(0.5f, 1.5f);
                    objList.add(new Jbox2DCirc(new Vec2(Main.JBox.coordPixelsToWorld(WIDTH/2, HEIGHT/6)),
                            new Vec2(tempSize,tempSize)));

                }

                shapeList.stream().forEach(e-> e.display());
                shapeList.removeIf(e-> e.isDead);

                objList.stream().forEach(e -> e.drawPixelObj());
                objList.removeIf(e-> e.isDead);

                g.setFill(Color.rgb(255, 255, 255, 1));
                g.setFont(new Font(14));
                g.fillText("Number of Bodies in Scene: " + JBox.world.getBodyCount(), 300, 570);
                g.fillText("Sum of Bodies generated: " + Main.numOfBodies, 300, 590);
                g.fillText("Curent Framerate: " + Math.round(frameRate), 10, 590);

            }
        };
        animator.start();
	}



    public void createWorldBorders(){

        polyCoordsX = new double[7];
        polyCoordsY = new double[7];

        polyCoordsX[0]=0;
        polyCoordsY[0]=500;
        polyCoordsX[1]=200;
        polyCoordsY[1]=530;
        polyCoordsX[2]=400;
        polyCoordsY[2]=420;
        polyCoordsX[3]=700;
        polyCoordsY[3]=570;
        polyCoordsX[4]=800;
        polyCoordsY[4]=590;
        polyCoordsX[5]=800;
        polyCoordsY[5]=600;
        polyCoordsX[6]=0;
        polyCoordsY[6]=600;

        Vec2 v1 = JBox.coordPixelsToWorld((float) polyCoordsX[0],(float)polyCoordsY[0]);
        Vec2 v2 = JBox.coordPixelsToWorld((float) polyCoordsX[1],(float)polyCoordsY[1]);
        Vec2 v3 = JBox.coordPixelsToWorld((float) polyCoordsX[2],(float)polyCoordsY[2]);
        Vec2 v4 = JBox.coordPixelsToWorld((float) polyCoordsX[3],(float)polyCoordsY[3]);
        Vec2 v5 = JBox.coordPixelsToWorld((float) polyCoordsX[4],(float)polyCoordsY[4]);
        Vec2 v6 = JBox.coordPixelsToWorld((float) polyCoordsX[5],(float)polyCoordsY[5]);
        Vec2 v7 = JBox.coordPixelsToWorld((float) polyCoordsX[6],(float)polyCoordsY[6]);

        chainShape.createLoop(new Vec2[]{v1, v2, v3, v4,v5,v6,v7}, 7);

        BodyDef chainBodyDef = new BodyDef();
        chainBodyDef.type = BodyType.STATIC;
        Body chainBody = JBox.world.createBody(chainBodyDef);
        chainBody.createFixture(chainShape, 0).setUserData("STATIC");
    }


	
	public static void main(String[] args) {
		launch(args);
	}
}
