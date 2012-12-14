package Dibujar_recorrido;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Dibujo extends JPanel {

    private Image imagenMapa;
    private Image imagenEstacion;
    private String nombreRuta;
    private ArrayList<ParadaBus> ubicaciones;


    public Dibujo() {
        this.setIgnoreRepaint(true); 
            imagenMapa = new ImageIcon("D:/Mis imágenes/megamapa.png").getImage();
            imagenEstacion = new ImageIcon("D:/Mis imágenes/estacion.png").getImage();
            this.setSize(1904, 3007);
            this.setAutoscrolls(true); 
    }

    
    public void setScale(int value) {
        setSize(1904,3007);
    }

    
    @Override
    public void paint(Graphics g) {
        g.setColor(Color.red); 
        if (imagenMapa != null) {
            g.drawImage(imagenMapa, 0, 0,this);
            setOpaque(false);
        } else {
            setOpaque(true);
        }
        //super.paint(g);
        dibujarRuta(g);
        dibujarEstaciones(g);
    }
    
    public void dibujarRuta(Graphics g) {
	    Graphics2D g2 = (Graphics2D) g;
		// dibujamos el mapa
		g2.drawImage(imagenMapa,0,0,this);
        
        //System.out.println("se van a dibujar " + ubicaciones.size() + " ubicaciones");
        
        for (int i=1; i<ubicaciones.size(); i++) {
            ParadaBus u1 = ubicaciones.get(i-1);
            ParadaBus u2 = ubicaciones.get(i);
            g2.drawLine(u1.getX(), u1.getY(), u2.getX(), u2.getY());
            g2.drawLine(u1.getX()+1, u1.getY(), u2.getX()+1, u2.getY());
            g2.drawLine(u1.getX(), u1.getY()+1, u2.getX(), u2.getY()+1);
            g2.drawString(u1.getIdEstacion(), u1.getX()-(imagenEstacion.getWidth(this)/2), u1.getY()-(imagenEstacion.getHeight(this)/2));
            g2.drawString(u2.getIdEstacion(), u2.getX()-(imagenEstacion.getWidth(this)/2), u2.getY()-(imagenEstacion.getHeight(this)/2));
            //g2.drawImage(imagenEstacion, u1.getX()-(imagenEstacion.getWidth(this)/2), u1.getY()-(imagenEstacion.getHeight(this)/2), this);
            //g2.drawImage(imagenEstacion, u2.getX()-(imagenEstacion.getWidth(this)/2), u2.getY()-(imagenEstacion.getHeight(this)/2), this);
        }
    }
    
    public void setUbicaciones(ArrayList<ParadaBus> ubicaciones) {
        this.ubicaciones = ubicaciones;
    }
    
    public void dibujarEstaciones(Graphics g) {
        
        for (int i=0; i<ubicaciones.size(); i++) {
            g.drawImage(imagenEstacion, ubicaciones.get(i).getX()-(imagenEstacion.getWidth(this)/2), ubicaciones.get(i).getY()-(imagenEstacion.getHeight(this)/2), this);
        }
    }
}
