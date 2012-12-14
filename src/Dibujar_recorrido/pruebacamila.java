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
public class pruebacamila {
    public static void main(String args[]) {
        ArrayList<Integer> arreglo1 = new ArrayList();
        ArrayList<Integer> arreglo2 = new ArrayList();
        arreglo1.add(1);
        arreglo1.add(2);
        arreglo1.add(3);
        arreglo1.add(5);
        arreglo1.add(7);
        arreglo1.add(11);
        
        for (int i=0; i<arreglo1.size(); i++) {
            Integer entero = arreglo1.get(i);
            arreglo2.add(entero);
        }
        
        arreglo2.remove(2);
        
        System.out.println("arreglo1:");
        for (int i=0; i<arreglo1.size(); i++) {
            System.out.println(arreglo1.get(i));
        }
        
        System.out.println("arreglo2:");
        for (int i=0; i<arreglo2.size(); i++) {
            System.out.println(arreglo2.get(i));
        }
    }
    
}
