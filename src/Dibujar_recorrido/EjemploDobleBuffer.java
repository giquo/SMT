/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Dibujar_recorrido;

/**
 *
 * @author Usuario
 */
import java.awt.*;
import java.util.ArrayList;
import javax.swing.ImageIcon;

public class EjemploDobleBuffer extends Canvas {
        Image dobleBuffer;
        Image imagenEstacion;
        Image imagenMapa;
        Graphics miCG;
        ArrayList<ParadaBus> ubicaciones;

    public EjemploDobleBuffer() {
    // Inicializa el doble buffer


        dobleBuffer = createImage( 800,600 );
        miCG = dobleBuffer.getGraphics();

        // Construye un área gráfica de trabajo
        miCG .setColor( Color.white );
        miCG.fillRect( 0,0,300,300 );
        resize( 1904,3007 );
    }
    
    public void initImage(){  
        imagenMapa = new ImageIcon("D:/Mis imágenes/megamapa.png").getImage(); 
        imagenEstacion = new ImageIcon("D:/Mis imágenes/estacion.png").getImage(); 
        setImage(imagenMapa);  
    }
    
    private void setImage(Image image){  
        this.imagenMapa = image ;  
        repaint();
    }

    //Podemos utilizar ##miCG## para dibujar cualquier cosa. Las imágenes se trazarán en doble buffer. Cuando el dibujo esté terminado, se puede presentar el doble buffer en pantalla:

        public void paint( Graphics g ) {
        // Sólo se tiene que presentar la imagen del buffer
        g.drawImage( dobleBuffer,0,0,this );
    }



    //==== //Utilización de Contextos Gráficos// ====

    //Una vez definido un contexto gráfico, podemos usarlo en cualquier parte de nuestro programa. Por ejemplo, podemos repartir la responsabilidad para dibujar sobre varias funciones:

    public void titulo() {
        // Obtiene la fuente de texto actual y la guardamos
        Font f = miCG.getFont();

        // Seleccionamos otra fuente para el título
        //miCG.setFont( new Font( "TimesRoman".Font.BOLD,36 ) );
        miCG.drawString( "Ejemplo de Espiral",15,50 );
        miCG.drawString( "Círculos",15,90 );
        // Recuperamos la fuente original
        miCG.setFont( f );
    }

    public void espiral() {
        int x,y;

        // Dibujamos circulos en los lados horizontales
        y = 100;
        for( x=100; x <= 200; x+=10 )
        {
            miCG.drawOval( x,y,20,20 );
            miCG.drawOval( x,y+100,20,20 );
        }
        // Ahora en los verticales
        x = 100;
        for( y=100; y <= 200; y+=10 )
        {
            miCG.drawOval( x,y,20,20 );
            miCG.drawOval( x+100,y,20,20 );
        }
    }

    public void start() {
        // Hace el dibujo off-line
        titulo();
        espiral();
        //dibujarRuta();
        // Ahora muestra la imagen de golpe
        repaint();
    }
    
    public void dibujarRuta() {
        
        System.out.println("se van a dibujar " + ubicaciones.size() + " ubicaciones");
        
        for (int i=1; i<ubicaciones.size(); i++) {
            ParadaBus u1 = ubicaciones.get(i-1);
            ParadaBus u2 = ubicaciones.get(i);
            miCG.drawLine(u1.getX(), u1.getY(), u2.getX(), u2.getY());
            miCG.drawLine(u1.getX()+1, u1.getY(), u2.getX()+1, u2.getY());
            miCG.drawLine(u1.getX(), u1.getY()+1, u2.getX(), u2.getY()+1);
            miCG.drawString(u1.getIdEstacion(), u1.getX()-(imagenEstacion.getWidth(this)/2), u1.getY()-(imagenEstacion.getHeight(this)/2));
            miCG.drawString(u2.getIdEstacion(), u2.getX()-(imagenEstacion.getWidth(this)/2), u2.getY()-(imagenEstacion.getHeight(this)/2));
            miCG.drawImage(imagenEstacion, u1.getX()-(imagenEstacion.getWidth(this)/2), u1.getY()-(imagenEstacion.getHeight(this)/2), this);
            miCG.drawImage(imagenEstacion, u2.getX()-(imagenEstacion.getWidth(this)/2), u2.getY()-(imagenEstacion.getHeight(this)/2), this);
        }
    }
    
    public void setUbicaciones(ArrayList<ParadaBus> ubicaciones) {
        this.ubicaciones = ubicaciones;
    }
}
