package application;

import java.io.Serializable;

/**
 * Created by COSI on 14/05/2016.
 */
public class PointCanvas implements Serializable{


    private double x;
    private double y;
    private int idcliente;

    public Figura getF() {
        return f;
    }

    public void setF(Figura f) {
        this.f = f;
    }

    private Figura f;

    public double getSizeb() {
        return sizeb;
    }

    public void setSizeb(double sizeb) {
        this.sizeb = sizeb;
    }

    public int getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(int idcliente) {
        this.idcliente = idcliente;
    }

    private double sizeb;

    public PointCanvas(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public PointCanvas() {
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public int getId() {
        return idcliente;
    }

    public void setId(int id) {
        this.idcliente = id;
    }

}
