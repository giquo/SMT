/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

/**
 *
 * @author jdavidva
 */

import DAO.RecargaJpaController;
import DAO.TarjetaJpaController;
import DAO.EstacionJpaController;
import DAO.EmpleadoJpaController;
import DAO.exceptions.NonexistentEntityException;
import DAO.exceptions.PreexistingEntityException;
import Entidades.*;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;
import javax.swing.JOptionPane;

public class ControladorRecarga 
{    
    RecargaJpaController daoRecarga;
    TarjetaJpaController daoTarjeta;
    EstacionJpaController daoEstacion;
    EmpleadoJpaController daoEmpleado;
    
    //se requiere para elaborar consultas personalizadas
    EntityManager manager;
    
    public ControladorRecarga(FabricaObjetos mi_fabrica)
    {
       manager= mi_fabrica.crear().createEntityManager();
       
       //se requiere ingresar por parametro una fabrica de objetos para manipular las entidades (crear, modificar..en fin)
       daoRecarga = new RecargaJpaController(mi_fabrica.getFactory());
       daoTarjeta = new TarjetaJpaController(mi_fabrica.getFactory());
       daoEstacion = new EstacionJpaController(mi_fabrica.getFactory());
       daoEmpleado = new EmpleadoJpaController(mi_fabrica.getFactory());
       
    }
    
    public String consultar()
    {
                        
            Iterator i;
            //sirve para ejecutar consultas
            i = manager.createQuery("SELECT p FROM Recarga p").getResultList().subList(1, 10).iterator();
            System.out.print(" consecutivo |\t pin_tarjeta |\t id_estacion |\t id_empleado |\t fecha |\t valor \n");
            String lista="";
            while(i.hasNext())
            {
                Recarga r = (Recarga) i.next();
                System.out.println("-----------------------------------------------------------------------------------");
                System.out.println(r.getConsecutivo()+"\t"+r.getPinTarjeta()+"\t"+r.getIdEstacion()+"\t"+r.getIdEmpleado()+"\t"+r.getFecha()+"\t"+r.getValor()+"\t");
                lista+= r.getConsecutivo()+"\t"+r.getPinTarjeta()+"\t"+r.getIdEstacion()+"\t"+r.getIdEmpleado()+"\t"+r.getFecha()+"\t"+r.getValor()+"\n";
            }
            return lista;
    }
    
    public void insertar(int consecutivo, int pin_tarjeta, String id_estacion, String id_empleado, Date fecha, int valor)
    {
       
        Tarjeta tarjeta = daoTarjeta.findTarjeta(pin_tarjeta);
        Estacion estacion = daoEstacion.findEstacion(id_estacion);
        Empleado empleado = daoEmpleado.findEmpleado(id_empleado);

        Recarga unRecarga = new Recarga();
        unRecarga.setConsecutivo(consecutivo);
        unRecarga.setPinTarjeta(tarjeta);
        unRecarga.setIdEstacion(estacion);
        unRecarga.setIdEmpleado(empleado);
        unRecarga.setFecha(fecha);
        unRecarga.setValor(valor);
        
        try 
        {
            daoRecarga.create(unRecarga);
            JOptionPane.showMessageDialog(null, "El recarga ha sido creado con exito");
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
    
    public void modificar(Recarga recarga)
    {
        try 
        {   
            daoRecarga.edit(recarga);
            JOptionPane.showMessageDialog(null, "El recarga ha sido modificado con exito");
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
    
    public Recarga consultar(int consecutivo){
            return daoRecarga.findRecarga(consecutivo);
        }
        
       
    
    //CONSULTA CON UN JOIN 
    //punto numero 4 del taller
    public void consultaConJoin()
    {
        Iterator i;       
        i = manager.createQuery("SELECT un_producto FROM Recarga p JOIN p.idProducto un_producto WHERE (un_producto.idProducto = 'PR-185'); ").getResultList().subList(1, 10).iterator();          
        System.out.print("id |\t Titulo  |\t Subtitulo\n");        
        while(i.hasNext())
        {
            //Producto p = (Producto) i.next();        
            Recarga unRecarga = (Recarga) i.next();
            System.out.println("-----------------------------------------------------------------------------------");            
            //System.out.println(p.getIdProducto()+"\t"+p.getNombre()+"\t"+p.getSerial());            
            System.out.println(unRecarga.getConsecutivo());
        }
    }
    
    public void eliminar(int consecutivo){
        try 
        {   
            daoRecarga.destroy(consecutivo);
            JOptionPane.showMessageDialog(null, "recarga eliminada con éxito");
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
    
    public List<Recarga> consultarRecargas(){
           List<Recarga> listaRecarga = daoRecarga.findRecargaEntities();
            return listaRecarga;
    }
    
}
