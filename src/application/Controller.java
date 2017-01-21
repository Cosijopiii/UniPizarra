/**
 * Sample Skeleton for 'Pizarra.fxml' Controller Class
 */

package application;

import javafx.animation.AnimationTimer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import serverSide.GraphicsObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.xml.stream.EventFilter;
import javax.xml.stream.events.XMLEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

public class Controller {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;
    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;
    @FXML // fx:id="canvas"
    private Canvas canvas; // Value injected by FXMLLoader
    @FXML
    private StackPane stakpane;
    @FXML
    private ColorPicker CoPicker;
    @FXML
    private ComboBox<String> comboFiguras;
    private GraphicsContext gc;
    private GraphicsObject gO;
    private Point2D po;
    private int id=GenerarR(0,1000000);
    private Color  colorB=Color.BLACK;
    String op="mano alazada";
    boolean b;
    @FXML
    private Button btnPintar;

    @FXML
    private Slider Slider;

    @FXML
    private Button btnBorrar;
    private  boolean eraser=false;

    private  boolean paint=false;
    @FXML
    void MouseBorrar(ActionEvent event) {

        eraser=true;
        gc.setStroke(Color.WHITE);
    }
    @FXML
    void MousePintar(ActionEvent event) {
        paint=true;
        gc.setStroke(colorB);
    }
    public static int GenerarR(int min, int max) {
        return min + (int) (Math.random() * ((max - min) + 1));
    }



    @FXML
    void actionCombo(ActionEvent event) {
        switch (comboFiguras.getSelectionModel().getSelectedItem()){
            case "Circulo":
                    op="Circulo";
                break;
            case "Cuadrado":
                    op="Cuadrado";
                break;
            case "Mano alzada":
                    op="Mano alzada";
                break;
        }
    }

    @FXML
    void MousePressed(MouseEvent e) throws RemoteException {
        gc.beginPath();
        po = new Point2D(e.getX(), e.getY());
        if(eraser ){
            op="Mano alzada";
        }
        if(paint){
            op="Mano alzada";
        }
        if(op.equals("Circulo")){
            gc.fillOval(po.getX(),po.getY(),gc.getLineWidth()*5,gc.getLineWidth()*5);
            Figura f=new Figura("Circulo",po.getX(),po.getY(),gc.getLineWidth()*5);
            PointCanvas p=new PointCanvas();
            p.setId(id);
            p.setF(f);
            gO.setPoint(p);

        }
        if(op.equals("Cuadrado")){
            gc.fillRect(po.getX(),po.getY(),gc.getLineWidth()*5,gc.getLineWidth()*5);
            Figura f=new Figura("Cuadrado",po.getX(),po.getY(),gc.getLineWidth()*5);
            PointCanvas p=new PointCanvas();
            p.setId(id);
            p.setF(f);
            gO.setPoint(p);

        }
        if(op.equals("Mano alzada")) {
            gc.moveTo(e.getX(), e.getY());
            gc.stroke();
            PointCanvas p=new PointCanvas(e.getX(),e.getY());
            Figura f=new Figura("move",po.getX(),po.getY(),gc.getLineWidth()*5);
            p.setId(id);
            p.setF(f);
            gO.setPoint(p);
        }
    }


    @FXML
    void MouseRelease(MouseEvent e) throws RemoteException {
        gO.setPoint(null);
        PointCanvas p=new PointCanvas(e.getX(),e.getY());
        Figura f=new Figura("relased",po.getX(),po.getY(),gc.getLineWidth()*5);
        p.setId(id);
        p.setF(f);
        gO.setPoint(p);
        System.out.println("released: "+e.getX()+" "+e.getY());
        PointCanvas D=gO.putPoint();
        if(D!=null)
        System.out.println("server "+D.getX()+" "+D.getY());

    }
    @FXML
    void MouseDragged(MouseEvent e) throws RemoteException {
        gc.lineTo(e.getX(), e.getY());
        gc.stroke();
        PointCanvas p=new PointCanvas(e.getX(),e.getY());
        p.setId(id);
        gO.setPoint(p);
    }
    private void drawLines(){
        AnimationTimer x=new AnimationTimer() {
            @Override
            public void handle(long now) {
                try {
                    PointCanvas p2=gO.putPoint();
                    if(p2!=null ){
                        if(p2.getId()!=id) {


                            if(p2.getF()==null) {
                                System.out.println(p2.getX()+" "+p2.getY());
                                gc.lineTo(p2.getX(), p2.getY());

                                gc.setLineWidth(p2.getSizeb());
                                gc.stroke();
                            }

                            if(p2.getF()!=null){
                                if(p2.getF().getTipo().equals("move")){

                                    gc.beginPath();
                                    gc.moveTo(p2.getX(), p2.getY());
                                    gc.stroke();
                                }
                                if(p2.getF().getTipo().equals("relased")){
                                    gc.beginPath();
                                    gc.stroke();
                                }
                                if(p2.getF().getTipo().equals("Circulo")){
                                    gc.fillOval(p2.getF().getX(),p2.getF().getY(),p2.getF().getSize(),p2.getF().getSize());
                                }
                                if (p2.getF().getTipo().equals("Cuadrado")){
                                    gc.fillRect(p2.getF().getX(),p2.getF().getY(),p2.getF().getSize(),p2.getF().getSize());

                                }
                            }
                        }else {
                            b=true;
                        }

                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        };
        x.start();
    }

    @FXML
    void changeColor(ActionEvent event) {
       // brush=createBrush(CoPicker.getValue());
        gc.setStroke(CoPicker.getValue());
        colorB=CoPicker.getValue();
    }
    private void initDraw() {
        gc.setFill(Color.BLACK);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1);
        gc.fill();
    }
    @FXML
        // This method is called by the FXMLLoader when initialization is complete
    void initialize() throws MalformedURLException, RemoteException, NotBoundException {
        String ip= JOptionPane.showInputDialog("Introdusca ip");
        gO = (GraphicsObject) Naming.lookup("rmi://"+ip+":6013/Lista");
       comboFiguras.getItems().addAll("Circulo", "Cuadrado","Mano alzada");
        comboFiguras.getSelectionModel().select(2);
        gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.WHITE);
        gc.fillRect(0,0,canvas.getWidth(),canvas.getHeight());
        initDraw();
        drawLines();

        Slider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                gc.setLineWidth(newValue.doubleValue());
            }
        });
    }
}
