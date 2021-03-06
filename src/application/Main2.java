package application;

import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Main2 extends Application {

    private static double SCENE_WIDTH = 1280;
    private static double SCENE_HEIGHT = 720;

    static Random random = new Random();

    Canvas canvas;
    Canvas copyCanvas;
    GraphicsContext graphicsContext;
    GraphicsContext copyGraphicsContext;

    AnimationTimer loop;

    Point2D mouseLocation = new Point2D( 0, 0);
    boolean mousePressed = false;
    Point2D prevMouseLocation = new Point2D( 0, 0);

    Scene scene;

    private Image brush = createBrush();
    double brushWidthHalf = brush.getWidth() / 2.0;
    double brushHeightHalf = brush.getHeight() / 2.0;



    @Override
    public void start(Stage primaryStage) {

        BorderPane root = new BorderPane();

        canvas = new Canvas( SCENE_WIDTH / 2, SCENE_HEIGHT);
        graphicsContext = canvas.getGraphicsContext2D();

        copyCanvas = new Canvas( SCENE_WIDTH / 2, SCENE_HEIGHT);
        copyGraphicsContext = canvas.getGraphicsContext2D();

        HBox hBox = new HBox();
        hBox.getChildren().addAll(canvas, copyCanvas);

        root.setCenter(hBox);

        scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);

        primaryStage.setScene(scene);
        primaryStage.show();

        addListeners();

        startAnimation();


    }

    private void startAnimation() {

        loop = new AnimationTimer() {

            @Override
            public void handle(long now) {

                if( mousePressed) {

                    // try this
                    // graphicsContext.drawImage( brush, mouseLocation.getX() - brushWidthHalf, mouseLocation.getY() - brushHeightHalf);

                    // then this
                    bresenhamLine( prevMouseLocation.getX(), prevMouseLocation.getY(), mouseLocation.getX(), mouseLocation.getY());
                }

                prevMouseLocation = new Point2D( mouseLocation.getX(), mouseLocation.getY());

                copyCanvas();
            }
        };
        loop.start();

    }

    private void copyCanvas() {

        SnapshotParameters params = new SnapshotParameters();
        //TODO Esta imagen es la que se enviara ta todos los usuarios
        WritableImage image = canvas.snapshot(params, null);

        copyCanvas.getGraphicsContext2D().drawImage(image, 0, 0);

    }

    //Bresenham-Algorithmus
    private void bresenhamLine(double x0, double y0, double x1, double y1)
    {
      double dx =  Math.abs(x1-x0), sx = x0<x1 ? 1. : -1.;
      double dy = -Math.abs(y1-y0), sy = y0<y1 ? 1. : -1.;
      
      double err = dx+dy, e2; /* error value e_xy */

      while( true){
    	
        graphicsContext.drawImage( brush, x0 - brushWidthHalf, y0 - brushHeightHalf);
        if (x0==x1 && y0==y1) break;
        
        e2 = 2.*err;
        if (e2 > dy) { 
        	err += dy;
        	x0 += sx; 
        } /* e_xy+e_x > 0 */
        if (e2 < dx) { 
        	err += dx; y0 += sy; 
        } /* e_xy+e_y < 0 */
      }
    }


    private void addListeners() {

        scene.addEventFilter(MouseEvent.ANY, e -> {

            mouseLocation = new Point2D(e.getX(), e.getY());

            mousePressed = e.isPrimaryButtonDown();

        });


    }


    private static Image createImage(Node node) {

        WritableImage wi;

        SnapshotParameters parameters = new SnapshotParameters();
        parameters.setFill(Color.TRANSPARENT);

        int imageWidth = (int) node.getBoundsInLocal().getWidth();
        int imageHeight = (int) node.getBoundsInLocal().getHeight();

        wi = new WritableImage(imageWidth, imageHeight);
        node.snapshot(parameters, wi);

        return wi;

    }


    private static Image createBrush() {

        // create gradient image with given color
        Rectangle brush = new Rectangle(0,0,1,1);
        brush.setStroke(Color.RED);
        brush.setFill(Color.RED);

        // create image
        return createImage(brush);

    }


    public static void main(String[] args) {
        launch(args);
    }
}