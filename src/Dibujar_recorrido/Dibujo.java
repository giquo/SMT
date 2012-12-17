package Dibujar_recorrido;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Dibujo extends JPanel {

    private Image imagenMapa;
    private Image imagenEstacion;
    private String nombreRuta;
    private ArrayList<ParadaBus> ubicaciones;
    private Recorrido recorrido;


    public Dibujo() {
        this.setIgnoreRepaint(true); 
            imagenMapa = new ImageIcon("megamapa.JPG").getImage();
            imagenEstacion = new ImageIcon("estacion.png").getImage();
            this.setSize(1904, 3007);
            this.setAutoscrolls(true); 
    }

    
    public void setScale(int value) {
        setSize(1904,3007);
    }

    
    @Override
    public void paint(Graphics g) {
        g.setColor(Color.red); 
        //super.paint(g);
        
        dibujarRuta(g);
        dibujarEstaciones(g);
    }
    
    public void dibujarRuta(Graphics g) {
	    Graphics2D g2 = (Graphics2D) g;
            
		// dibujamos el mapa
		g2.drawImage(imagenMapa,0,0,this);
                g2.drawString("Ruta "+recorrido.getNombreRuta(), 30, 30);
                g2.drawString("# de paradas="+recorrido.getParadas().size(), 30, 50);
                DecimalFormat df = new DecimalFormat("#.###");
                g2.drawString("Total distancia="+df.format(recorrido.getDistanciaTotal())+" Kms", 30, 70);
        
        //System.out.println("se van a dibujar " + ubicaciones.size() + " ubicaciones");
        
        for (int i=1; i<ubicaciones.size(); i++) {
            ParadaBus u1 = ubicaciones.get(i-1);
            ParadaBus u2 = ubicaciones.get(i);
            g2.drawLine(u1.getX(), u1.getY(), u2.getX(), u2.getY());
            g2.drawLine(u1.getX()+1, u1.getY(), u2.getX()+1, u2.getY());
            g2.drawLine(u1.getX(), u1.getY()+1, u2.getX(), u2.getY()+1);
            g2.drawLine(u1.getX()-1, u1.getY(), u2.getX()-1, u2.getY());
            g2.drawLine(u1.getX(), u1.getY()-1, u2.getX(), u2.getY()-1);
            g2.drawLine(u1.getX()+2, u1.getY(), u2.getX()+2, u2.getY());
            g2.drawLine(u1.getX(), u1.getY()+2, u2.getX(), u2.getY()+2);
            g2.drawLine(u1.getX()-2, u1.getY(), u2.getX()-2, u2.getY());
            g2.drawLine(u1.getX(), u1.getY()-2, u2.getX(), u2.getY()-2);
            g2.drawString(u1.getIdEstacion(), u1.getX()-(imagenEstacion.getWidth(this)/2), u1.getY()-(imagenEstacion.getHeight(this)/2));
            g2.drawString(u2.getIdEstacion(), u2.getX()-(imagenEstacion.getWidth(this)/2), u2.getY()-(imagenEstacion.getHeight(this)/2));
        }
    }
    
    public void setUbicaciones(ArrayList<ParadaBus> ubicaciones) {
        this.ubicaciones = ubicaciones;
    }

    public void setRecorrido(Recorrido recorrido) {
        this.recorrido = recorrido;
        this.ubicaciones = recorrido.getParadas();
    }
    
    public void dibujarEstaciones(Graphics g) {
        for (int i=0; i<ubicaciones.size(); i++) {
            g.drawImage(imagenEstacion, ubicaciones.get(i).getX()-(imagenEstacion.getWidth(this)/2), ubicaciones.get(i).getY()-(imagenEstacion.getHeight(this)/2), this);
        }
    }
}
