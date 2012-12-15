
/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
 */
package DAO;

//~--- non-JDK imports --------------------------------------------------------

/**
 *
 * @author alexfcz
 */

//~--- JDK imports ------------------------------------------------------------

import Dibujar_recorrido.ParadaBus;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DaoParadas {
    FachadaBD fachada;

    public DaoParadas() {
        fachada = new FachadaBD();
    }

    public int ingresarCliente() {
        String sql_ingresar;
        int    numFilas = 0;

//      Cliente(String id,String nombre, String apellido, String telefono, String direccion,String fecha_nacimiento)    
        sql_ingresar = "INSERT INTO cliente VALUES ();";
        try {
            Connection conn      = fachada.conectar();
            Statement  sentencia = conn.createStatement();

            numFilas = sentencia.executeUpdate(sql_ingresar);
            conn.close();

            return numFilas;
        } catch (SQLException e) {
            System.out.println(e);
        } catch (Exception e) {
            System.out.println(e);
        }

        return -1;
    }    // fin ingresarCliente

    public int eliminarCliente(String id) {
        String sql_texto;
        int    numFilas = 0;

        sql_texto = "DELETE FROM cliente WHERE id='" + id + "');";

        try {
            Connection conn      = fachada.conectar();
            Statement  sentencia = conn.createStatement();

            numFilas = sentencia.executeUpdate(sql_texto);
            conn.close();

            return numFilas;
        } catch (SQLException e) {
            System.out.println(e);
        } catch (Exception e) {
            System.out.println(e);
        }

        return -1;
    }    // fin eliminar

    public int actualizarCliente(String id) {
        String sql_texto;
        int    numFilas = 0;

//      Cliente(String id,String nombre, String apellido, String telefono, String direccion,String fecha_nacimiento)        
        sql_texto = "UPDATE cliente SET ;";

        try {
            Connection conn      = fachada.conectar();
            Statement  sentencia = conn.createStatement();

            numFilas = sentencia.executeUpdate(sql_texto);
            conn.close();

            return numFilas;
        } catch (SQLException e) {
            System.out.println(e);
        } catch (Exception e) {
            System.out.println(e);
        }

        return -1;
    }    // fin actualizarCliente

    public ArrayList<ParadaBus> consultarParadas() {
        
        String  sql_consultar;

        sql_consultar = "SELECT * FROM ubicaciones_paradas;";

        try {
            ArrayList<ParadaBus> ubicacionesParadas;
            Connection conn = fachada.conectar();

            System.out.println("consultando en la bd ubicaciones de paradas de buses");

            Statement sentencia = conn.createStatement();
            ResultSet tabla     = sentencia.executeQuery(sql_consultar);
            ubicacionesParadas = new ArrayList();
//          Cliente(String id,String nombre, String apellido, String telefono, String direccion,String fecha_nacimiento)                      
            while (tabla.next()) {
                ParadaBus parada = new ParadaBus();
                parada.setIdEstacion(tabla.getString("nombre_parada"));
                parada.setX(tabla.getInt("posicionx"));
                parada.setY(tabla.getInt("posiciony"));
                ubicacionesParadas.add(parada);
                
            }
            conn.close();
            System.out.println("ok\n"+ubicacionesParadas.size()+" paradas encontradas");
            return ubicacionesParadas;
        } catch (SQLException e) {
            System.out.println(e);
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }  // fin consultarParadas
    
    public ParadaBus consultarParada(String nombreParada) {
        
        String  sql_consultar;

        sql_consultar = "SELECT * FROM ubicaciones_paradas WHERE nombre_parada='"+nombreParada+"';";

        try {
            Connection conn = fachada.conectar();

            System.out.println("consultando en la bd ubicaciones de la parada "+ nombreParada);

            Statement sentencia = conn.createStatement();
            ResultSet tabla     = sentencia.executeQuery(sql_consultar);
            
            ParadaBus parada = new ParadaBus();
            while (tabla.next()) {
                parada.setIdEstacion(tabla.getString("nombre_parada"));
                parada.setX(tabla.getInt("posicionx"));
                parada.setY(tabla.getInt("posiciony"));
            }
            conn.close();
            System.out.println("se han econtrado las posiciones "+parada.getX()+","+parada.getY());
            return parada;
        } catch (SQLException e) {
            System.out.println(e);
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }  // fin consultarParadas
}


//~ Formatted by Jindent --- http://www.jindent.com
