/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

/**
 *
 * @author jdavidva
 */


import DAO.EmpleadoJpaController;
import DAO.RutaJpaController;
import DAO.exceptions.NonexistentEntityException;
import DAO.exceptions.PreexistingEntityException;
import Entidades.*;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;
import javax.swing.JOptionPane;

public class ControladorEmpleado
{    
    EmpleadoJpaController daoEmpleado;
    RutaJpaController daoRuta;
    
    //se requiere para elaborar consultas personalizadas
    EntityManager manager;
    
    public ControladorEmpleado(FabricaObjetos mi_fabrica)
    {
       manager= mi_fabrica.crear().createEntityManager();
       
       //se requiere ingresar por parametro una fabrica de objetos para manipular las entidades (crear, modificar..en fin)
       daoEmpleado = new EmpleadoJpaController(mi_fabrica.getFactory());
       //daoRuta = new RutaJpaController(mi_fabrica.getFactory());
       
    }
    
    public String consultar()
    {
            Iterator i;
            //sirve para ejecutar consultas
            i = manager.createQuery("SELECT p FROM Empleado p").getResultList().subList(1, 10).iterator();
            System.out.print(" id_empleado |\t nombre_empleado |\t fecha_nac |\t estrato_empleado |\t genero_empleado |\t cargo |\t direccion_empleado |\t salario |\t telefono_empleado\n");
            String lista="";
            while(i.hasNext())
            {
                Empleado e = (Empleado) i.next();
                System.out.println("-----------------------------------------------------------------------------------");
                System.out.println(e.getIdEmpleado()+"\t"+e.getNombreEmpleado()+"\t"+e.getFechaNac()+"\t"+e.getEstratoEmpleado()+"\t"+e.getGeneroEmpleado()+"\t"+e.getCargo()+"\t"+e.getDireccionEmpleado()+"\t"+e.getSalario()+"\t"+e.getTelefonoEmpleado()+"\t");
                
                lista+= e.getIdEmpleado()+"\t"+e.getNombreEmpleado()+"\t"+e.getFechaNac()+"\t"+e.getEstratoEmpleado()+"\t"+e.getGeneroEmpleado()+"\t"+e.getCargo()+"\t"+e.getDireccionEmpleado()+"\t"+e.getSalario()+"\t"+e.getTelefonoEmpleado()+"\n";
            }
            return lista;
    }
    
    public void insertar(String id_empleado, String nombre_empleado, Date fecha_nac, int estrato_empleado, String genero_empleado, String cargo, String direccion_empleado, int salario, String telefono_empleado)
    {
 
        Empleado unEmpleado = new Empleado();
        unEmpleado.setIdEmpleado(id_empleado);
        unEmpleado.setNombreEmpleado(nombre_empleado);
        unEmpleado.setFechaNac(fecha_nac);
        unEmpleado.setEstratoEmpleado(estrato_empleado);
        unEmpleado.setGeneroEmpleado(genero_empleado);
        unEmpleado.setCargo(cargo);
        unEmpleado.setDireccionEmpleado(direccion_empleado);
        unEmpleado.setSalario(salario);
        unEmpleado.setTelefonoEmpleado(telefono_empleado);
        
        
        try 
        {
            daoEmpleado.create(unEmpleado);
            JOptionPane.showMessageDialog(null, "El producto ha sido creado con exito");
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
    
    public void modificar(Empleado unEmpleado)
    {
        try 
        {   
            daoEmpleado.edit(unEmpleado);
            JOptionPane.showMessageDialog(null, "El producto ha sido modificado con exito");
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
    
    public Empleado consultar(String idEmpleado){
            return daoEmpleado.findEmpleado(idEmpleado);
        }
        
       
    
    //CONSULTA CON UN JOIN 
    //punto numero 4 del taller
    public void consultaConJoin()
    {
        Iterator i;       
        i = manager.createQuery("SELECT un_empleado FROM Empleado p JOIN p.idEmpleado un_empleado WHERE (un_empleado.idEmpleado = 'PR-185'); ").getResultList().subList(1, 10).iterator();          
        System.out.print("id |\t Titulo  |\t Subtitulo\n");        
        while(i.hasNext())
        {
            //Producto p = (Producto) i.next();        
            Empleado unEmpleado = (Empleado) i.next();
            System.out.println("-----------------------------------------------------------------------------------");            
            //System.out.println(p.getIdProducto()+"\t"+p.getNombre()+"\t"+p.getSerial());            
            System.out.println(unEmpleado.getIdEmpleado());
        }
    }
    
    public void eliminar(String idEmpleado){
        try 
        {   
            daoEmpleado.destroy(idEmpleado);
            JOptionPane.showMessageDialog(null, "Empleado eliminado con éxito");
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
    
    public List<Empleado> consultarEmpleados(){
           List<Empleado> listaEmpleados = daoEmpleado.findEmpleadoEntities();
            return listaEmpleados;
    }
    
}
