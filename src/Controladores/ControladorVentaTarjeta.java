/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

/**
 *
 * @author jdavidva
 */

import DAO.VentaTarjetaJpaController;
import DAO.TarjetaJpaController;
import DAO.EmpleadoJpaController;
import DAO.EstacionJpaController;
import DAO.PasajeroJpaController;
import DAO.exceptions.NonexistentEntityException;
import DAO.exceptions.PreexistingEntityException;
import Entidades.*;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;
import javax.swing.JOptionPane;

public class ControladorVentaTarjeta 
{    
    VentaTarjetaJpaController daoVentaTarjeta;
    TarjetaJpaController daoTarjeta;
    EmpleadoJpaController daoEmpleado;
    EstacionJpaController daoEstacion;
    PasajeroJpaController daoPasajero;
    
    //se requiere para elaborar consultas personalizadas
    EntityManager manager;
    
    public ControladorVentaTarjeta(FabricaObjetos mi_fabrica)
    {
       manager= mi_fabrica.crear().createEntityManager();
       
       //se requiere ingresar por parametro una fabrica de objetos para manipular las entidades (crear, modificar..en fin)
       daoVentaTarjeta = new VentaTarjetaJpaController(mi_fabrica.getFactory());
       daoTarjeta = new TarjetaJpaController(mi_fabrica.getFactory());
       daoEmpleado = new EmpleadoJpaController(mi_fabrica.getFactory());
       daoEstacion = new EstacionJpaController(mi_fabrica.getFactory());
       daoPasajero = new PasajeroJpaController(mi_fabrica.getFactory());
       
    }
    
    public String consultar()
    {
                        
            Iterator i;
            //sirve para ejecutar consultas
            i = manager.createQuery("SELECT p FROM VentaTarjeta p").getResultList().subList(1, 10).iterator();
            System.out.print("pin_tarjeta |\t id_empleado |\t id_estacion |\t id_pasajero |\t fecha \n");
            String lista="";
            while(i.hasNext())
            {
                VentaTarjeta v = (VentaTarjeta) i.next();
                System.out.println("-----------------------------------------------------------------------------------");
                System.out.println(v.getPinTarjeta()+"\t"+v.getIdEmpleado()+"\t"+v.getIdEstacion()+"\t"+v.getIdPasajero()+"\t"+v.getFecha()+"\t");
                lista+=v.getPinTarjeta()+"\t"+v.getIdEmpleado()+"\t"+v.getIdEstacion()+"\t"+v.getIdPasajero()+"\t"+v.getFecha()+"\n";
            }
            return lista;
    }
    
    public void insertar(int id_tarjeta, String id_empleado, String id_estacion, String id_pasajero, Date fecha)
    {
        
        Tarjeta tarjeta = daoTarjeta.findTarjeta(id_tarjeta);
        Empleado empleado = daoEmpleado.findEmpleado(id_empleado);
        Estacion estacion = daoEstacion.findEstacion(id_estacion);
        Pasajero pasajero = daoPasajero.findPasajero(id_pasajero);
        
        VentaTarjeta unVentaTarjeta = new VentaTarjeta();
        unVentaTarjeta.setPinTarjeta(id_tarjeta);
        unVentaTarjeta.setFecha(fecha);
        unVentaTarjeta.setTarjeta(tarjeta);
        unVentaTarjeta.setIdEmpleado(empleado);
        unVentaTarjeta.setIdEstacion(estacion);
        unVentaTarjeta.setIdPasajero(pasajero);
        
        try 
        {
            daoVentaTarjeta.create(unVentaTarjeta);
            JOptionPane.showMessageDialog(null, "La tarjeta ha sido vendida con exito");
        }
        catch (PreexistingEntityException ex) 
        {
            JOptionPane.showMessageDialog(null, ex.getMessage());
            System.out.println(ex.getMessage());
        }
        catch (Exception ex) 
        {
            JOptionPane.showMessageDialog(null, ex.getMessage());
            System.out.println(ex.getMessage());
        }
    }
    
    public void modificar(VentaTarjeta ventaTarjeta)
    {
        try 
        {   
            daoVentaTarjeta.edit(ventaTarjeta);
            JOptionPane.showMessageDialog(null, "La tarjeta vendida ha sido modificada con exito");
        }
        catch (NonexistentEntityException ex) 
        {
            System.out.println(ex.getMessage());
        }
        catch (Exception ex) 
        {
            System.out.println(ex.getMessage());
        }
    }
    
    public VentaTarjeta consultar(int idVentaTarjeta){
            return daoVentaTarjeta.findVentaTarjeta(idVentaTarjeta);
        }
        
       
    
    //CONSULTA CON UN JOIN 
    //punto numero 4 del taller
    public void consultaConJoin()
    {
        Iterator i;       
        i = manager.createQuery("SELECT un_producto FROM VentaTarjeta p JOIN p.idProducto un_producto WHERE (un_producto.idProducto = 'PR-185'); ").getResultList().subList(1, 10).iterator();          
        System.out.print("id |\t Titulo  |\t Subtitulo\n");        
        while(i.hasNext())
        {
            //Producto p = (Producto) i.next();        
            VentaTarjeta unVentaTarjeta = (VentaTarjeta) i.next();
            System.out.println("-----------------------------------------------------------------------------------");            
            //System.out.println(p.getIdProducto()+"\t"+p.getNombre()+"\t"+p.getSerial());            
            System.out.println(unVentaTarjeta.getPinTarjeta());
        }
    }
    
    public void eliminar(int idVentaTarjeta){
        try 
        {   
            daoVentaTarjeta.destroy(idVentaTarjeta);
            JOptionPane.showMessageDialog(null, "ventaTarjeta eliminado con éxito");
        }
        catch (NonexistentEntityException ex) 
        {
            System.out.println(ex.getMessage());
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        catch (Exception ex) 
        {
            System.out.println(ex.getMessage());
        }
    }
    
    public List<VentaTarjeta> consultarVentaTarjetaes(){
           List<VentaTarjeta> listaVentaTarjeta = daoVentaTarjeta.findVentaTarjetaEntities();
            return listaVentaTarjeta;
    }
    
}
