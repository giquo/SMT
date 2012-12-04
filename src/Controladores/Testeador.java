/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import java.sql.Date;



/**
 *
 * @author jdavidva
 */
public class Testeador {
    
    FabricaObjetos fabrica;

    ControladorBus cBuses;
    ControladorRuta cRutas;
    ControladorEmpleado cEmpleados;
    ControladorTarjeta cTarjetas;
    
    public Testeador()
    {
    
        fabrica = new FabricaObjetos();
        cBuses = new ControladorBus(fabrica);
        cRutas = new ControladorRuta(fabrica);
        cEmpleados = new ControladorEmpleado(fabrica);
        cTarjetas = new ControladorTarjeta(fabrica);
    
    }
    
    public void Agregaciones()
    {
        /*
        cRutas.insertar("101", "Callejera", "1.El estadio, 2.Mi casa, 3.La casa de giquo");
        cRutas.insertar("102", "Estacion", "1.El estadio, 2.San Pascual, 3.Universidades");
        cRutas.insertar("103", "Callejera", "1.El estadio, 2.Mi casa");
        cBuses.insertar("MIO111", "Alimentador", "Mazda", "1990", 80, 30, "101");
        cBuses.insertar("MIO112", "Alimentador", "Mazda", "1999", 80, 40, "101");
        cBuses.insertar("MIO113", "Alimentador", "Mazda", "2006", 100, 50, "102");
        */
        
        //cEmpleados.consultar("01");
        
        /*
        cTarjetas.insertar(12346, "Generica", "Activo", 300000);
        cTarjetas.insertar(12347, "Generica", "Activo", 10000);
        cTarjetas.insertar(12348, "Generica", "Activo", 3000);
        */
        
        /*
        cBuses.eliminar("MIO111");
        cBuses.eliminar("MIO112");
        cBuses.eliminar("MIO113");
        cRutas.eliminar("101");
        cRutas.eliminar("102");
        cRutas.eliminar("103");
        */
        
    }
    
    public static void main(String []args)
    {
        Testeador t = new Testeador();
        t.Agregaciones();
    }
    
    
    
}
