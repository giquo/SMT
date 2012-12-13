/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package canvas;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.Border;

/**
 *
 * @author Usuario
 */
public class Ventana extends JFrame {
    JScrollPane jsp;
    Dibujo dib;
    public Ventana() {
        //setSize(1904, 3007);
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        
        ArrayList<ParadaBus> ubicaciones = new ArrayList();
        
        for (int i=0; i<5; i++) {
            int randomX = (int) ((Math.random()*600)+50);
            int randomY = (int) ((Math.random()*400)+50);
            ParadaBus ubicacion = new ParadaBus("Estacion" + (i+1), randomX, randomY );
            ubicaciones.add(ubicacion);
        }
        
        JPanel jp2 = new JPanel();
        dib = new Dibujo();
        
        
        dib.setUbicaciones(ubicaciones);
        
        jsp = new JScrollPane(dib);
        
        
        
        dib.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentMoved(java.awt.event.ComponentEvent evt) {
                dibComponentMoved(evt);
            }
        });
        
        
        //jsp.setPreferredSize(new Dimension(1904, 3007));
        
        add(jsp);

        //add(jp);
        setVisible(true);
    }
    
  
    private void dibComponentMoved(java.awt.event.ComponentEvent evt) {
        this.paintAll(dib.getGraphics()); 
        jsp.updateUI();
    }
    
}
