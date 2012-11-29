/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

/**
 *
 * @author jdavidva
 */

import DAO.TurnoJpaController;
import DAO.RutaJpaController;
import DAO.EmpleadoJpaController;
import DAO.BusJpaController;
import DAO.exceptions.NonexistentEntityException;
import DAO.exceptions.PreexistingEntityException;
import Entidades.*;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;
import javax.swing.JOptionPane;

public class ControladorTurno 
{    
    TurnoJpaController daoTurno;
    RutaJpaController daoRuta;
    EmpleadoJpaController daoEmpleado;
    BusJpaController daoBus;

    
    //se requiere para elaborar consultas personalizadas
    EntityManager manager;
    
    public ControladorTurno(FabricaObjetos mi_fabrica)
    {
       manager= mi_fabrica.crear().createEntityManager();
       
       //se requiere ingresar por parametro una fabrica de objetos para manipular las entidades (crear, modificar..en fin)
       daoTurno = new TurnoJpaController(mi_fabrica.getFactory());
       daoRuta = new RutaJpaController(mi_fabrica.getFactory());
       daoEmpleado = new EmpleadoJpaController(mi_fabrica.getFactory());
       daoBus = new BusJpaController(mi_fabrica.getFactory());
    }
    
    public String consultar()
    {
                        
            Iterator i;
            //sirve para ejecutar consultas
            i = manager.createQuery("SELECT p FROM Turno p").getResultList().subList(1, 10).iterator();
            System.out.print("id_empleado |\t id_ruta |\t id_bus |\t fecha |\t jornada \n");
            String lista="";
            while(i.hasNext())
            {
                Turno t = (Turno) i.next();
                System.out.println("-----------------------------------------------------------------------------------");
                System.out.println(t.getIdEmpleado()+"\t"+t.getIdRuta()+"\t"+t.getBus()+"\t"+t.getTurnoPK()+"\t");
                lista+=t.getIdEmpleado()+"\t"+t.getIdRuta()+"\t"+t.getBus()+"\t"+t.getTurnoPK()+"\n";
            }
            return lista;
    }
    
    public void insertar(String id_empleado, String id_ruta, String id_bus, Date fecha, String jornada)
    {
        Empleado empleado = daoEmpleado.findEmpleado(id_empleado);
        Ruta ruta = daoRuta.findRuta(id_ruta);
        Bus bus = daoBus.findBus(id_bus);
        
        TurnoPK turnopk = new TurnoPK(id_bus, fecha, jornada);
        
        Turno unTurno = new Turno();
        unTurno.setBus(bus);
        unTurno.setIdRuta(ruta);
        unTurno.setIdEmpleado(empleado);
        unTurno.setTurnoPK(turnopk);
        
        try 
        {
            daoTurno.create(unTurno);
            JOptionPane.showMessageDialog(null, "El turno ha sido creado con exito");
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
    
    public void modificar(Turno turno)
    {
        try 
        {   
            daoTurno.edit(turno);
            JOptionPane.showMessageDialog(null, "El turno ha sido modificado con exito");
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
    
    public Turno consultar(String idbus, Date fecha, String jornada){
            TurnoPK turnopk = new TurnoPK(idbus, fecha, jornada);
            return daoTurno.findTurno(turnopk);
        }
        
       
    
    //CONSULTA CON UN JOIN 
    //punto numero 4 del taller
    public void consultaConJoin()
    {
        Iterator i;       
        i = manager.createQuery("SELECT un_producto FROM Turno p JOIN p.idProducto un_producto WHERE (un_producto.idProducto = 'PR-185'); ").getResultList().subList(1, 10).iterator();          
        System.out.print("id |\t Titulo  |\t Subtitulo\n");        
        while(i.hasNext())
        {
            //Producto p = (Producto) i.next();        
            Turno unTurno = (Turno) i.next();
            System.out.println("-----------------------------------------------------------------------------------");            
            //System.out.println(p.getIdProducto()+"\t"+p.getNombre()+"\t"+p.getSerial());            
            System.out.println(unTurno.getBus()+"\t"+unTurno.getIdEmpleado()+"\t"+unTurno.getIdRuta()+"\t");
        }
    }
    
    public void eliminar(String idbus, Date fecha, String jornada){
        try 
        {   
            TurnoPK turnopk = new TurnoPK(idbus, fecha, jornada);
            daoTurno.destroy(turnopk);
            JOptionPane.showMessageDialog(null, "turno eliminado con éxito");
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
    
    public List<Turno> consultarTurnoes(){
           List<Turno> listaTurno = daoTurno.findTurnoEntities();
            return listaTurno;
    }
    
}