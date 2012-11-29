/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import DAO.QuejasYReclamosJpaController;
import DAO.EstacionJpaController;
import DAO.EmpleadoJpaController;
import DAO.PasajeroJpaController;
import DAO.exceptions.NonexistentEntityException;
import DAO.exceptions.PreexistingEntityException;
import Entidades.*;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;
import javax.swing.JOptionPane;

/**
 *
 * @author jdavidva
 */
public class ControladorQuejasYReclamos 
{    
    QuejasYReclamosJpaController daoQuejasYReclamos;
    EstacionJpaController daoEstacion;
    EmpleadoJpaController daoEmpleado;
    PasajeroJpaController daoPasajero;
    
    //se requiere para elaborar consultas personalizadas
    EntityManager manager;
    
    public ControladorQuejasYReclamos(FabricaObjetos mi_fabrica)
    {
       manager= mi_fabrica.crear().createEntityManager();
       
       //se requiere ingresar por parametro una fabrica de objetos para manipular las entidades (crear, modificar..en fin)
       daoQuejasYReclamos = new QuejasYReclamosJpaController(mi_fabrica.getFactory());
       daoEstacion = new EstacionJpaController(mi_fabrica.getFactory());
       daoEmpleado = new EmpleadoJpaController(mi_fabrica.getFactory());
       daoPasajero = new PasajeroJpaController(mi_fabrica.getFactory());
       
    }
    
    public String consultar()
    {
                        
            Iterator i;
            //sirve para ejecutar consultas
            i = manager.createQuery("SELECT p FROM QuejasYReclamos p").getResultList().subList(1, 10).iterator();
            System.out.print(" id_pasajero |\t id_estacion |\t id_empleado |\t no_ticket |\t estado |\t descripcion |\t fecha \n");
            String lista="";
            while(i.hasNext())
            {
                QuejasYReclamos b = (QuejasYReclamos) i.next();
                System.out.println("-----------------------------------------------------------------------------------");
                System.out.println(b.getIdPasajero()+"\t"+b.getIdEstacion()+"\t"+b.getIdEmpleado()+"\t"+b.getNoTicket()+"\t"+b.getEstado()+"\t"+b.getDescripcion()+"\t"+b.getFecha()+"\t");
                lista+=b.getIdPasajero()+"\t"+b.getIdEstacion()+"\t"+b.getIdEmpleado()+"\t"+b.getNoTicket()+"\t"+b.getEstado()+"\t"+b.getDescripcion()+"\t"+b.getFecha()+"\n";
            }
            return lista;
    }
    
    public void insertar(String id_pasajero, String id_estacion, String id_empleado, int no_ticket, String estado, String descripcion, Date fecha)
    {
        Pasajero pasajero = daoPasajero.findPasajero(id_pasajero);
        Estacion estacion = daoEstacion.findEstacion(id_estacion);
        Empleado empleado = daoEmpleado.findEmpleado(id_empleado);
        

        QuejasYReclamos unQuejasYReclamos = new QuejasYReclamos();
        unQuejasYReclamos.setIdEmpleado(empleado);
        unQuejasYReclamos.setIdEstacion(estacion);
        unQuejasYReclamos.setIdPasajero(pasajero);
        unQuejasYReclamos.setNoTicket(no_ticket);
        unQuejasYReclamos.setEstado(estado);
        unQuejasYReclamos.setDescripcion(descripcion);
        unQuejasYReclamos.setFecha(fecha);

        try 
        {
            daoQuejasYReclamos.create(unQuejasYReclamos);
            JOptionPane.showMessageDialog(null, "La queja o reclamo ha sido creado con exito");
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
    
    public void modificar(QuejasYReclamos quejasYReclamos)
    {
        try 
        {   
            daoQuejasYReclamos.edit(quejasYReclamos);
            JOptionPane.showMessageDialog(null, "La queja o reclamo ha sido modificado con exito");
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
    
    public QuejasYReclamos consultar(int idQuejasYReclamos){
            return daoQuejasYReclamos.findQuejasYReclamos(idQuejasYReclamos);
        }
        
       
    
    //CONSULTA CON UN JOIN 
    //punto numero 4 del taller
    public void consultaConJoin()
    {
        Iterator i;       
        i = manager.createQuery("SELECT un_producto FROM QuejasYReclamos p JOIN p.idProducto un_producto WHERE (un_producto.idProducto = 'PR-185'); ").getResultList().subList(1, 10).iterator();          
        System.out.print("id |\t Titulo  |\t Subtitulo\n");        
        while(i.hasNext())
        {
            //Producto p = (Producto) i.next();        
            QuejasYReclamos unQuejasYReclamos = (QuejasYReclamos) i.next();
            System.out.println("-----------------------------------------------------------------------------------");            
            //System.out.println(p.getIdProducto()+"\t"+p.getNombre()+"\t"+p.getSerial());            
            System.out.println(unQuejasYReclamos.getNoTicket());
        }
    }
    
    public void eliminar(int idQuejasYReclamos){
        try 
        {   
            daoQuejasYReclamos.destroy(idQuejasYReclamos);
            JOptionPane.showMessageDialog(null, "La queja o reclamo ha sido eliminado con éxito");
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
    
    public List<QuejasYReclamos> consultarQuejasYReclamos(){
           List<QuejasYReclamos> listaQuejasYReclamos = daoQuejasYReclamos.findQuejasYReclamosEntities();
            return listaQuejasYReclamos;
    }
    
}