/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

/**
 *
 * @author jdavidva
 */

import DAO.EstacionJpaController;
import DAO.EmpleadoJpaController;
import DAO.exceptions.NonexistentEntityException;
import DAO.exceptions.PreexistingEntityException;
import Entidades.*;
import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;
import javax.swing.JOptionPane;

public class ControladorEstacion 
{    
    EstacionJpaController daoEstacion;
    EmpleadoJpaController daoEmpleado;
    
    //se requiere para elaborar consultas personalizadas
    EntityManager manager;
    
    public ControladorEstacion(FabricaObjetos mi_fabrica)
    {
       manager= mi_fabrica.crear().createEntityManager();
       
       //se requiere ingresar por parametro una fabrica de objetos para manipular las entidades (crear, modificar..en fin)
       daoEstacion = new EstacionJpaController(mi_fabrica.getFactory());
       daoEmpleado = new EmpleadoJpaController(mi_fabrica.getFactory());
       
    }
    
    public String consultar()
    {
                        
            Iterator i;
            //sirve para ejecutar consultas
            i = manager.createQuery("SELECT p FROM Estacion p").getResultList().subList(1, 10).iterator();
            System.out.print("id_estacion |\t nombre_estacion |\t tipo_estacion |\t direccion_estacion |\t empleado_encargado\n");
            String lista="";
            while(i.hasNext())
            {
                Estacion e = (Estacion) i.next();
                System.out.println("-----------------------------------------------------------------------------------");
                System.out.println(e.getIdEstacion()+"\t"+e.getNombreEstacion()+"\t"+e.getTipoEstacion()+"\t"+e.getDireccionEstacion()+"\t"+e.getEmpleadoEncargado()+"\t");
                lista+= e.getIdEstacion()+"\t"+e.getNombreEstacion()+"\t"+e.getTipoEstacion()+"\t"+e.getDireccionEstacion()+"\t"+e.getEmpleadoEncargado()+"\n";
            }
            return lista;
    }
    
    public void insertar(String id_estacion, String nombre_estacion, String tipo_estacion, String direccion_estacion, String empleado_encargado)
    {
        Empleado unEmpleado = daoEmpleado.findEmpleado(empleado_encargado);
 
        Estacion unaEstacion = new Estacion();
        unaEstacion.setIdEstacion(id_estacion);
        unaEstacion.setNombreEstacion(nombre_estacion);
        unaEstacion.setTipoEstacion(tipo_estacion);
        unaEstacion.setEmpleadoEncargado(unEmpleado);
        unaEstacion.setDireccionEstacion(direccion_estacion);
        
        try 
        {
            daoEstacion.create(unaEstacion);
            JOptionPane.showMessageDialog(null, "La estacion ha sido creada con exito");
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
    
    public void modificar(Estacion estacion)
    {
        try 
        {   
            daoEstacion.edit(estacion);
            JOptionPane.showMessageDialog(null, "La estacion ha sido modificada con exito");
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
    
    public Estacion consultar(String idEstacion){
            return daoEstacion.findEstacion(idEstacion);
        }
        
       
    
    //CONSULTA CON UN JOIN 
    //punto numero 4 del taller
    public void consultaConJoin()
    {
        Iterator i;       
        i = manager.createQuery("SELECT un_estacion FROM Estacion p JOIN p.idEstacion un_estacion WHERE (un_estacion.idEstacion = 'PR-185'); ").getResultList().subList(1, 10).iterator();          
        System.out.print("id |\t Titulo  |\t Subtitulo\n");        
        while(i.hasNext())
        {
            //Producto p = (Producto) i.next();        
            Estacion unEstacion = (Estacion) i.next();
            System.out.println("-----------------------------------------------------------------------------------");            
            //System.out.println(p.getIdProducto()+"\t"+p.getNombre()+"\t"+p.getSerial());            
            System.out.println(unEstacion.getIdEstacion());
        }
    }
    
    public void eliminar(String idEstacion){
        try 
        {   
            daoEstacion.destroy(idEstacion);
            JOptionPane.showMessageDialog(null, "estacion eliminado con éxito");
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
    
    public List<Estacion> consultarEstaciones(){
           List<Estacion> listaEstaciones = daoEstacion.findEstacionEntities();
            return listaEstaciones;
    }
    
}