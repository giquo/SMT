/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package canvas;

import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import javax.swing.ImageIcon;

/**
 *
 * @author Usuario
 */
public class Dibujo extends Canvas {
    Image imagenMapa;
    Image imagenEstacion;
    Graphics g;
    ArrayList<ParadaBus> ubicaciones;
    public Dibujo() {
        initImage();
        ubicaciones = new ArrayList();
        this.setPreferredSize(new Dimension(1904, 3007)); 
        
        //setBackground(Color.black);
    }
    
    public void initImage(){  
    imagenMapa = new ImageIcon("D:/Mis imágenes/megamapa.png").getImage(); 
    imagenEstacion = new ImageIcon("D:/Mis imágenes/estacion.png").getImage(); 
    setImage(imagenMapa);  
  }
    void setImage(Image image){  
    this.imagenMapa = image ;  
    repaint();
  }  

    @Override
    public void paint(Graphics g) {
        super.paint(g); 
        g.drawImage(imagenMapa,0,0,this) ;
        //Image imagen = new ImageIcon("D:/Mis imágenes/mapa cali.jpg").getImage();
        //g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
        
        g.setColor(Color.RED);
        
        dibujarRuta(g);
        this.g=g;
    }

    public void dibujarRuta(Graphics g) {
        
        System.out.println("se van a dibujar " + ubicaciones.size() + " ubicaciones");
        
        for (int i=1; i<ubicaciones.size(); i++) {
            System.out.println("dibujando ubicacion " + i);
            ParadaBus u1 = ubicaciones.get(i-1);
            ParadaBus u2 = ubicaciones.get(i);
            g.drawLine(u1.getX(), u1.getY(), u2.getX(), u2.getY());
            g.drawLine(u1.getX()+1, u1.getY(), u2.getX()+1, u2.getY());
            g.drawLine(u1.getX(), u1.getY()+1, u2.getX(), u2.getY()+1);
            g.drawString(u1.getIdEstacion(), u1.getX()-(imagenEstacion.getWidth(this)/2), u1.getY()-(imagenEstacion.getHeight(this)/2));
            g.drawString(u2.getIdEstacion(), u2.getX()-(imagenEstacion.getWidth(this)/2), u2.getY()-(imagenEstacion.getHeight(this)/2));
            g.drawImage(imagenEstacion, u1.getX()-(imagenEstacion.getWidth(this)/2), u1.getY()-(imagenEstacion.getHeight(this)/2), this);
            g.drawImage(imagenEstacion, u2.getX()-(imagenEstacion.getWidth(this)/2), u2.getY()-(imagenEstacion.getHeight(this)/2), this);
        }
    }

    public void setUbicaciones(ArrayList<ParadaBus> ubicaciones) {
        this.ubicaciones = ubicaciones;
    }
    

    
}
