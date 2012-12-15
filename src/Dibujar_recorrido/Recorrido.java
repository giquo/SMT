/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Dibujar_recorrido;

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

    public Recorrido() {
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
    
    
}
