/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Dibujar_recorrido;

/**
 *
 * @author Usuario
 */
public class ParadaBus {
    private String idEstacion;
    private int x;
    private int y;

    public ParadaBus() {
    }

    public ParadaBus(String idEstacion, int x, int y) {
        this.idEstacion = idEstacion;
        this.x = x;
        this.y = y;
    }

    public String getIdEstacion() {
        return idEstacion;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setIdEstacion(String idEstacion) {
        this.idEstacion = idEstacion;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
    
}
