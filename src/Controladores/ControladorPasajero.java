/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

/**
 *
 * @author jdavidva
 */

import DAO.PasajeroJpaController;
import DAO.RutaJpaController;
import DAO.exceptions.NonexistentEntityException;
import DAO.exceptions.PreexistingEntityException;
import Entidades.*;
import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;
import javax.swing.JOptionPane;

public class ControladorPasajero 
{    
    PasajeroJpaController daoPasajero;
    RutaJpaController daoRuta;
    
    //se requiere para elaborar consultas personalizadas
    EntityManager manager;
    
    public ControladorPasajero(FabricaObjetos mi_fabrica)
    {
       manager= mi_fabrica.crear().createEntityManager();
       
       //se requiere ingresar por parametro una fabrica de objetos para manipular las entidades (crear, modificar..en fin)
       daoPasajero = new PasajeroJpaController(mi_fabrica.getFactory());
       daoRuta = new RutaJpaController(mi_fabrica.getFactory());
       
    }
    
    public String consultar()
    {
                        
            Iterator i;
            //sirve para ejecutar consultas
            i = manager.createQuery("SELECT p FROM Pasajero p").getResultList().subList(1, 10).iterator();
            System.out.print("id_pasajero |\t nombre_pasajero |\t estrato_pasajero |\t direccion_pasajero |\t telefono_pasajero |\t genero_pasajero \n");
            String lista="";
            while(i.hasNext())
            {
                Pasajero p = (Pasajero) i.next();
                System.out.println("-----------------------------------------------------------------------------------");
                System.out.println(p.getIdPasajero()+"\t"+p.getNombrePasajero()+"\t"+p.getEstratoPasajero()+"\t"+p.getDireccionPasajero()+"\t"+p.getTelefonoPasajero()+"\t"+p.getGeneroPasajero()+"\t");
                lista+= p.getIdPasajero()+"\t"+p.getNombrePasajero()+"\t"+p.getEstratoPasajero()+"\t"+p.getDireccionPasajero()+"\t"+p.getTelefonoPasajero()+"\t"+p.getGeneroPasajero()+"\n";
            }
            return lista;
    }
    
    public void insertar(String id_pasajero, String nombre_pasajero, int estrato_pasajero, String direccion_pasajero, String telefono_pasajero, String genero_pasajero)
    {

        Pasajero unPasajero = new Pasajero();
        unPasajero.setIdPasajero(id_pasajero);
        unPasajero.setNombrePasajero(nombre_pasajero);
        unPasajero.setEstratoPasajero(estrato_pasajero);
        unPasajero.setDireccionPasajero(direccion_pasajero);
        unPasajero.setTelefonoPasajero(telefono_pasajero);
        unPasajero.setGeneroPasajero(genero_pasajero);
        
        try 
        {
            daoPasajero.create(unPasajero);
            JOptionPane.showMessageDialog(null, "El pasajero ha sido creado con exito");
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
    
    public Pasajero SimularPasajero(String id_pasajero, String nombre_pasajero, int estrato_pasajero, String direccion_pasajero, String telefono_pasajero, String genero_pasajero)
    {

        Pasajero unPasajero = new Pasajero();
        unPasajero.setIdPasajero(id_pasajero);
        unPasajero.setNombrePasajero(nombre_pasajero);
        unPasajero.setEstratoPasajero(estrato_pasajero);
        unPasajero.setDireccionPasajero(direccion_pasajero);
        unPasajero.setTelefonoPasajero(telefono_pasajero);
        unPasajero.setGeneroPasajero(genero_pasajero);
        
        return unPasajero;
    }
    
    public void modificar(Pasajero pasajero)
    {
        try 
        {   
            daoPasajero.edit(pasajero);
            JOptionPane.showMessageDialog(null, "El pasajero ha sido modificado con exito");
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
    
    public Pasajero consultar(String idPasajero){
            return daoPasajero.findPasajero(idPasajero);
        }
        
       
    
    //CONSULTA CON UN JOIN 
    //punto numero 4 del taller
    public void consultaConJoin()
    {
        Iterator i;       
        i = manager.createQuery("SELECT un_pasajero FROM Pasajero p JOIN p.idPasajero un_pasajero WHERE (un_pasajero.idPasajero = 'PR-185'); ").getResultList().subList(1, 10).iterator();          
        System.out.print("id |\t Titulo  |\t Subtitulo\n");        
        while(i.hasNext())
        {
            //Producto p = (Producto) i.next();        
            Pasajero unPasajero = (Pasajero) i.next();
            System.out.println("-----------------------------------------------------------------------------------");            
            //System.out.println(p.getIdProducto()+"\t"+p.getNombre()+"\t"+p.getSerial());            
            System.out.println(unPasajero.getIdPasajero());
        }
    }
    
    public void eliminar(String idPasajero){
        try 
        {   
            daoPasajero.destroy(idPasajero);
            JOptionPane.showMessageDialog(null, "pasajero eliminado con éxito");
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
    
    public List<Pasajero> consultarPasajeros(){
           List<Pasajero> listaPasajeros = daoPasajero.findPasajeroEntities();
            return listaPasajeros;
    }
    
}