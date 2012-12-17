
/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
 */
package DAO;

//~--- JDK imports ------------------------------------------------------------

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JOptionPane;

/**
 *
 * @author jdavidva
 * Clase copipasteada tal cual :D
 */
public class FachadaBD {
    Connection conexion;
    Statement  instruccion;
    ResultSet  tabla;
    String     url, usuario, password;

    FachadaBD() {
        
        /*
        url      = "jdbc:postgresql://pgsql:5432/oscaraca";
        usuario  = "oscaraca";
        password = "oscaraca";
        */

        
        url="jdbc:postgresql://pgsql:5432/oscaraca";
        usuario="oscaraca";
        password="pass";
    }

    public Connection conectar() {
        try {

            // Se carga el driver
            Class.forName("org.postgresql.Driver");

            // System.out.println( "Driver Cargado" );
        } catch (Exception e) {
            System.out.println("No se pudo cargar el driver.");
        }

        try {

            // Crear el objeto de conexion a la base de datos
            conexion = DriverManager.getConnection(url, usuario, password);
            System.out.println("Conexion Abierta");

            return conexion;

            // Crear objeto Statement para realizar queries a la base de datos
        } catch (Exception e) {
            System.out.println("No se pudo abrir la bd.");
            System.out.println(e.getMessage());
            return null;
        }
    }    // end connectar

    public void cerrarConexion(Connection c) {
        try {
            c.close();
        } catch (Exception e) {
            System.out.println("No se pudo cerrar.");
        }
    }
}


