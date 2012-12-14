/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Dibujar_recorrido;

import java.util.ArrayList;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author Usuario
 */
public class Ventana2 extends JFrame {
    JDesktopPane jsp;
    EjemploDobleBuffer dib;
    int i;
    public Ventana2() {
        i=0;
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
        dib = new EjemploDobleBuffer();
        
        
        jsp = new JDesktopPane();
        jsp.add(dib);
        
        
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
        dib.start();
    }
    
    public static void main(String[] args) {
        new Ventana2();
    }
}
