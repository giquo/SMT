/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Dibujar_recorrido;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 *
 * @author Usuario
 */
public class Recorrido {
    String nombreRuta;
    String origen;
    String destino;
    ArrayList<ParadaBus> paradas;
    double distanciaTotal;

    public Recorrido() {
    }

    public double getDistanciaTotal() {
        return distanciaTotal;
    }

    public String getDestino() {
        return destino;
    }

    public String getNombreRuta() {
        return nombreRuta;
    }

    public String getOrigen() {
        return origen;
    }

    public ArrayList<ParadaBus> getParadas() {
        return paradas;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public void setNombreRuta(String nombreRuta) {
        this.nombreRuta = nombreRuta;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public void setParadas(ArrayList<ParadaBus> paradas) {
        this.paradas = paradas;
    }
    
    public void calcularDistanciaTotal() {
        double distanciaEnPixeles=0;
        for (int i=1; i<paradas.size(); i++){
            ParadaBus u1 = paradas.get(i-1);
            ParadaBus u2 = paradas.get(i);
            distanciaEnPixeles+= Math.sqrt(Math.pow((u2.getX()-u1.getX()),2)+Math.pow((u2.getY()-u1.getY()),2));
        }
        
        distanciaTotal=distanciaEnPixeles*0.0082;//Conversion a km
        System.out.println("Distancia total recorrido calculada="+distanciaTotal+" Km");
    }
}
