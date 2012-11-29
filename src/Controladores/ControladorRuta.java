/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

/**
 *
 * @author jdavidva
 */

import DAO.RutaJpaController;
import DAO.exceptions.NonexistentEntityException;
import DAO.exceptions.PreexistingEntityException;
import Entidades.*;
import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;
import javax.swing.JOptionPane;

public class ControladorRuta 
{    
    RutaJpaController daoRuta;
    
    //se requiere para elaborar consultas personalizadas
    EntityManager manager;
    
    public ControladorRuta(FabricaObjetos mi_fabrica)
    {
       manager= mi_fabrica.crear().createEntityManager();
       
       //se requiere ingresar por parametro una fabrica de objetos para manipular las entidades (crear, modificar..en fin)
       daoRuta = new RutaJpaController(mi_fabrica.getFactory());
       
    }
    
    public String consultar()
    {
                        
            Iterator i;
            //sirve para ejecutar consultas
            i = manager.createQuery("SELECT p FROM Ruta p").getResultList().subList(1, 10).iterator();
            System.out.print("id_ruta |\t tipo_ruta |\t paradas \n");
            String lista="";
            while(i.hasNext())
            {
                Ruta r = (Ruta) i.next();
                System.out.println("-----------------------------------------------------------------------------------");
                System.out.println(r.getIdRuta()+"\t"+r.getTipoRuta()+"\t"+r.getParadas()+"\t");
                lista+=r.getIdRuta()+"\t"+r.getTipoRuta()+"\t"+r.getParadas()+"\n";
            }
            return lista;
    }
    
    public void insertar(String id_ruta, String tipo_ruta, String paradas)
    {

        Ruta unRuta = new Ruta();
        unRuta.setIdRuta(id_ruta);
        unRuta.setTipoRuta(tipo_ruta);
        unRuta.setParadas(paradas);
        
        try 
        {
            daoRuta.create(unRuta);
            JOptionPane.showMessageDialog(null, "La ruta ha sido creado con exito");
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
    
    public void modificar(Ruta ruta)
    {
        try 
        {   
            daoRuta.edit(ruta);
            JOptionPane.showMessageDialog(null, "La ruta ha sido modificado con exito");
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
    
    public Ruta consultar(String idRuta){
            return daoRuta.findRuta(idRuta);
        }
        
       
    
    //CONSULTA CON UN JOIN 
    //punto numero 4 del taller
    public void consultaConJoin()
    {
        Iterator i;       
        i = manager.createQuery("SELECT un_producto FROM Ruta p JOIN p.idProducto un_producto WHERE (un_producto.idProducto = 'PR-185'); ").getResultList().subList(1, 10).iterator();          
        System.out.print("id |\t Titulo  |\t Subtitulo\n");        
        while(i.hasNext())
        {
            //Producto p = (Producto) i.next();        
            Ruta unRuta = (Ruta) i.next();
            System.out.println("-----------------------------------------------------------------------------------");            
            //System.out.println(p.getIdProducto()+"\t"+p.getNombre()+"\t"+p.getSerial());            
            System.out.println(unRuta.getIdRuta());
        }
    }
    
    public void eliminar(String idRuta){
        try 
        {   
            daoRuta.destroy(idRuta);
            JOptionPane.showMessageDialog(null, "ruta eliminada con éxito");
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
    
    public List<Ruta> consultarRutas(){
           List<Ruta> listaRuta = daoRuta.findRutaEntities();
            return listaRuta;
    }
    
}
