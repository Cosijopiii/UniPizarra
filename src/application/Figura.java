package application;

import java.io.Serializable;

/**
 * Created by COSI on 18/05/2016.
 */
public class Figura implements Serializable {

    String tipo;
    double x;
    double y;
    double size;

    public Figura(String tipo, double x, double y, double size) {
        this.tipo = tipo;
        this.x = x;
        this.y = y;
        this.size = size;
    }

    public String getTipo() {
        return tipo;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getSize() {
        return size;
    }
}
