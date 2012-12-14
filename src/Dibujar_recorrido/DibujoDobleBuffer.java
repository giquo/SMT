/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Dibujar_recorrido;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.Timer;

/**
 *
 * @author Usuario
 */
public class DibujoDobleBuffer extends Canvas {
    public BufferedImage imagenMapa;
    public BufferedImage imagenEstacion;
    Graphics g;
    int numeroDePintadas;
    ArrayList<ParadaBus> ubicaciones;
    private ActionListener actionListenerList = null;
    private Timer bufferTimer = null;
    
    public DibujoDobleBuffer() {
        setBackground(Color.gray);
        setIgnoreRepaint(true);
        this.setSize(1904,3007);
    }
    
    public  void classInit(){
        imagenMapa = this.loadImage("D:/Mis imágenes/megamapa.png"); 
        imagenEstacion = this.loadImage("D:/Mis imágenes/estacion.png"); 
        this.createBufferStrategy(2);
 this.emulateBuffering();
  }
  
  public void emulateBuffering () {
    this.createBufferStrategy(2);
    final BufferStrategy bufferStrategy = this.getBufferStrategy();
    if (this.bufferTimer instanceof Timer) this.bufferTimer.stop();
    this.bufferTimer = null;
    this.bufferTimer = new Timer (1, new ActionListener () {
        public void actionPerformed(ActionEvent e) {
          canvasUpdate(bufferStrategy.getDrawGraphics());
          bufferStrategy.show();
        }
      }
    );
    this.bufferTimer.start();
  }
  
  public BufferedImage loadImage( String filename ) {
    BufferedImage img2load;
    try {
      img2load = ImageIO.read(new File(filename));
      return img2load;
    } catch (IOException e) {
        
    }
    return null;
  }
    
    @Override 
    public void paint(Graphics g) {
	}
	
	
	public void canvasUpdate (Graphics g) {
		BufferedImage bgBuffer = new BufferedImage(1904, 3007, BufferedImage.TYPE_INT_ARGB);
		bgBuffer = dibujarRuta(bgBuffer);
		g.drawImage(bgBuffer,0,0,this);
		g.dispose();
    }

    public BufferedImage dibujarRuta(BufferedImage img) {
	    
	    Graphics g = img.getGraphics();
		// dibujamos el mapa
		g.drawImage(imagenMapa,0,0,this);
        
        //System.out.println("se van a dibujar " + ubicaciones.size() + " ubicaciones");
        
        for (int i=1; i<ubicaciones.size(); i++) {
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
		return img;
    }

    public void setUbicaciones(ArrayList<ParadaBus> ubicaciones) {
        this.ubicaciones = ubicaciones;
    }
    
    /*
    public void update(Graphics g) {
	Graphics offgc;
	Image offscreen = null;
	Dimension d = size();

	// create the offscreen buffer and associated Graphics
	offscreen = createImage(d.width, d.height);
	offgc = offscreen.getGraphics();
	// clear the exposed area
	offgc.setColor(getBackground());
	offgc.fillRect(0, 0, d.width, d.height);
	offgc.setColor(getForeground());
        dibujarRuta(offgc);
	// do normal redraw
	paint(offgc);
	// transfer offscreen to window
	g.drawImage(offscreen, 0, 0, this);
    }
    */
    
}
