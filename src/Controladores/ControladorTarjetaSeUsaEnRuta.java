/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

/**
 *
 * @author jdavidva
 */


import DAO.TarjetaSeUsaEnRutaJpaController;
import DAO.RutaJpaController;
import DAO.TarjetaJpaController;
import DAO.exceptions.NonexistentEntityException;
import DAO.exceptions.PreexistingEntityException;
import Entidades.*;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;
import javax.swing.JOptionPane;

public class ControladorTarjetaSeUsaEnRuta
{    
    TarjetaSeUsaEnRutaJpaController daoTarjetaSeUsaEnRuta;
    RutaJpaController daoRuta;
    TarjetaJpaController daoTarjeta;
    //se requiere para elaborar consultas personalizadas
    EntityManager manager;
    
    public ControladorTarjetaSeUsaEnRuta(FabricaObjetos mi_fabrica)
    {
       manager= mi_fabrica.crear().createEntityManager();
       
       //se requiere ingresar por parametro una fabrica de objetos para manipular las entidades (crear, modificar..en fin)
       daoTarjetaSeUsaEnRuta = new TarjetaSeUsaEnRutaJpaController(mi_fabrica.getFactory());
       daoRuta = new RutaJpaController(mi_fabrica.getFactory());
       daoTarjeta = new TarjetaJpaController(mi_fabrica.getFactory());
    }
    
    public String consultar()
    {
                        
            Iterator i;
            //sirve para ejecutar consultas
            i = manager.createQuery("SELECT p FROM TarjetaSeUsaEnRuta p").getResultList().subList(1, 10).iterator();
            System.out.print("fecha |\t id_estacion |\t pin_tarjeta \n");
            String lista="";
            while(i.hasNext())
            {
                TarjetaSeUsaEnRuta b = (TarjetaSeUsaEnRuta) i.next();
                System.out.println("-----------------------------------------------------------------------------------");
                System.out.println(b.getTarjetaSeUsaEnRutaPK().getFecha()+"\t"+b.getRuta()+"\t"+b.getTarjeta()+"\t");
                lista+=b.getTarjetaSeUsaEnRutaPK().getFecha()+"\t"+b.getRuta()+"\t"+b.getTarjeta()+"\n";
            }
            return lista;
    }
    
    public void insertar(String id_ruta, int id_tarjeta, Date fecha)
    {
        
        Ruta ruta = daoRuta.findRuta(id_ruta);
        Tarjeta tarjeta = daoTarjeta.findTarjeta(id_tarjeta);
        
        TarjetaSeUsaEnRutaPK tPK = new TarjetaSeUsaEnRutaPK(id_tarjeta, id_ruta, fecha);
        

        TarjetaSeUsaEnRuta unTarjetaSeUsaEnRuta = new TarjetaSeUsaEnRuta();
        unTarjetaSeUsaEnRuta.setRuta(ruta);
        unTarjetaSeUsaEnRuta.setTarjeta(tarjeta);
        unTarjetaSeUsaEnRuta.setTarjetaSeUsaEnRutaPK(tPK);
        
        try 
        {
            daoTarjetaSeUsaEnRuta.create(unTarjetaSeUsaEnRuta);
            JOptionPane.showMessageDialog(null, "El registro ha sido creado con exito");
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
    
    public void modificar(String id_ruta, int id_tarjeta, Date fecha)
    {
        try 
        {   
            Ruta ruta = daoRuta.findRuta(id_ruta);
            Tarjeta tarjeta = daoTarjeta.findTarjeta(id_tarjeta);

            TarjetaSeUsaEnRutaPK tPK = new TarjetaSeUsaEnRutaPK(id_tarjeta, id_ruta, fecha);

            TarjetaSeUsaEnRuta unTarjetaSeUsaEnRuta = new TarjetaSeUsaEnRuta();
            unTarjetaSeUsaEnRuta.setRuta(ruta);
            unTarjetaSeUsaEnRuta.setTarjeta(tarjeta);
            unTarjetaSeUsaEnRuta.setTarjetaSeUsaEnRutaPK(tPK);

            daoTarjetaSeUsaEnRuta.edit(unTarjetaSeUsaEnRuta);
            JOptionPane.showMessageDialog(null, "El registro ha sido modificado con exito");
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
    
    public TarjetaSeUsaEnRuta consultar(String id_ruta, int id_tarjeta, Date fecha){
            TarjetaSeUsaEnRutaPK tPK = new TarjetaSeUsaEnRutaPK(id_tarjeta, id_ruta, fecha);    
            return daoTarjetaSeUsaEnRuta.findTarjetaSeUsaEnRuta(tPK);
        }
        
       
    
    //CONSULTA CON UN JOIN 
    //punto numero 4 del taller
    public void consultaConJoin()
    {
        Iterator i;       
        i = manager.createQuery("SELECT un_producto FROM TarjetaSeUsaEnRuta p JOIN p.idProducto un_producto WHERE (un_producto.idProducto = 'PR-185'); ").getResultList().subList(1, 10).iterator();          
        System.out.print("id |\t Titulo  |\t Subtitulo\n");        
        while(i.hasNext())
        {
            //Producto p = (Producto) i.next();        
            TarjetaSeUsaEnRuta unTarjetaSeUsaEnRuta = (TarjetaSeUsaEnRuta) i.next();
            System.out.println("-----------------------------------------------------------------------------------");            
            //System.out.println(p.getIdProducto()+"\t"+p.getNombre()+"\t"+p.getSerial());            
            System.out.println(unTarjetaSeUsaEnRuta.getTarjetaSeUsaEnRutaPK().toString());
        }
    }
    
    public void eliminar(String id_ruta, int id_tarjeta, Date fecha){
        try 
        {   
            TarjetaSeUsaEnRutaPK tPK = new TarjetaSeUsaEnRutaPK(id_tarjeta, id_ruta, fecha);  
            daoTarjetaSeUsaEnRuta.destroy(tPK);
            JOptionPane.showMessageDialog(null, "Registro eliminado con éxito");
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
    
    public List<TarjetaSeUsaEnRuta> consultarTarjetaSeUsaEnRutaes(){
           List<TarjetaSeUsaEnRuta> listaTarjetaSeUsaEnRuta = daoTarjetaSeUsaEnRuta.findTarjetaSeUsaEnRutaEntities();
            return listaTarjetaSeUsaEnRuta;
    }
    
}