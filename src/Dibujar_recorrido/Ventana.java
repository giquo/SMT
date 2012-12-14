/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Dibujar_recorrido;


import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.util.ArrayList;
import javax.swing.*;

/**
 *
 * @author Usuario
 */
public class Ventana extends JFrame {
    JScrollPane jsp;
    Dibujo dib;
    int i;
    public Ventana() {
        
        i=0;
        //setSize(1904, 3007);
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        
        
        ArrayList<ParadaBus> ubicaciones = new ArrayList();
        
        for (int i=0; i<5; i++) {
            int randomX = (int) ((Math.random()*600)+50);
            int randomY = (int) ((Math.random()*400)+50);
            ParadaBus ubicacion = new ParadaBus("Estacion" + (i+1), randomX, randomY );
            ubicaciones.add(ubicacion);
        }
        
        dib = new Dibujo();
        //dib.setLayout(new GridLayout(0,1)); 
        
        
        dib.setUbicaciones(ubicaciones);
        
        
        jsp = new JScrollPane();
        dib.setPreferredSize(new Dimension(1904, 3007));
        jsp.getViewport().add(dib);
        //jsp.setPreferredSize(new Dimension(1, 600));
        //jsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        //jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        
        dib.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentMoved(java.awt.event.ComponentEvent evt) {
                dibComponentMoved(evt);
            }
        });
        
        
        //jsp.setPreferredSize(new Dimension(1904, 3007));
        
        add(jsp);

        //add(jp);
        setVisible(true);
        //this.pack();
        System.out.println("tamaño jpanel: " + dib.getWidth()+"x"+dib.getHeight() );
    }
    
  
    private void dibComponentMoved(java.awt.event.ComponentEvent evt) {
        
    }
}
