/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

/**
 *
 * @author jdavidva
 */

import DAO.TarjetaSeUsaEnEstacionJpaController;
import DAO.EstacionJpaController;
import DAO.TarjetaJpaController;
import DAO.exceptions.NonexistentEntityException;
import DAO.exceptions.PreexistingEntityException;
import Entidades.*;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;
import javax.swing.JOptionPane;

public class ControladorTarjetaSeUsaEnEstacion 
{    
    TarjetaSeUsaEnEstacionJpaController daoTarjetaSeUsaEnEstacion;
    EstacionJpaController daoEstacion;
    TarjetaJpaController daoTarjeta;
    
    //se requiere para elaborar consultas personalizadas
    EntityManager manager;
    
    public ControladorTarjetaSeUsaEnEstacion(FabricaObjetos mi_fabrica)
    {
       manager= mi_fabrica.crear().createEntityManager();
       
       //se requiere ingresar por parametro una fabrica de objetos para manipular las entidades (crear, modificar..en fin)
       daoTarjetaSeUsaEnEstacion = new TarjetaSeUsaEnEstacionJpaController(mi_fabrica.getFactory());
       daoEstacion = new EstacionJpaController(mi_fabrica.getFactory());
       daoTarjeta = new TarjetaJpaController(mi_fabrica.getFactory());
       
    }
    
    public String consultar()
    {
                        
            Iterator i;
            //sirve para ejecutar consultas
            i = manager.createQuery("SELECT p FROM TarjetaSeUsaEnEstacion p").getResultList().subList(1, 10).iterator();
            System.out.print("fecha |\t id_estacion |\t pin_tarjeta \n");
            String lista="";
            while(i.hasNext())
            {
                TarjetaSeUsaEnEstacion b = (TarjetaSeUsaEnEstacion) i.next();
                System.out.println("-----------------------------------------------------------------------------------");
                System.out.println(b.getTarjetaSeUsaEnEstacionPK().getFecha()+"\t"+b.getEstacion().getIdEstacion()+"\t"+b.getTarjeta().getPinTarjeta()+"\t");
                lista+=b.getTarjetaSeUsaEnEstacionPK().getFecha()+"\t"+b.getEstacion().getIdEstacion()+"\t"+b.getTarjeta().getPinTarjeta()+"\n";
            }
            return lista;
    }
    
    public void insertar(int id_tarjeta, String id_estacion, Date fecha)
    {
        
        Estacion estacion = daoEstacion.findEstacion(id_estacion);
        Tarjeta tarjeta = daoTarjeta.findTarjeta(id_tarjeta);
        
        TarjetaSeUsaEnEstacionPK tsueePK = new TarjetaSeUsaEnEstacionPK(id_tarjeta, id_estacion, fecha);

        TarjetaSeUsaEnEstacion unTarjetaSeUsaEnEstacion = new TarjetaSeUsaEnEstacion();
        unTarjetaSeUsaEnEstacion.setEstacion(estacion);
        unTarjetaSeUsaEnEstacion.setTarjeta(tarjeta);
        unTarjetaSeUsaEnEstacion.setTarjetaSeUsaEnEstacionPK(tsueePK);
        
        try 
        {
            daoTarjetaSeUsaEnEstacion.create(unTarjetaSeUsaEnEstacion);
            JOptionPane.showMessageDialog(null, "El registro sido creado con exito");
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
    
    public void modificar(String id_estacion, int id_tarjeta, Date fecha)
    {
        try 
        {   
            Estacion estacion = daoEstacion.findEstacion(id_estacion);
        Tarjeta tarjeta = daoTarjeta.findTarjeta(id_tarjeta);
        
        TarjetaSeUsaEnEstacionPK tsueePK = new TarjetaSeUsaEnEstacionPK(id_tarjeta, id_estacion, fecha);
        TarjetaSeUsaEnEstacion unTarjetaSeUsaEnEstacion = new TarjetaSeUsaEnEstacion();
        unTarjetaSeUsaEnEstacion.setEstacion(estacion);
        unTarjetaSeUsaEnEstacion.setTarjeta(tarjeta);
        unTarjetaSeUsaEnEstacion.setTarjetaSeUsaEnEstacionPK(tsueePK);

        
            daoTarjetaSeUsaEnEstacion.edit(unTarjetaSeUsaEnEstacion);
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
    
    public TarjetaSeUsaEnEstacion consultar(int id_tarjeta, String id_estacion, Date fecha){
        TarjetaSeUsaEnEstacionPK tsueePK = new TarjetaSeUsaEnEstacionPK(id_tarjeta, id_estacion, fecha);
            return daoTarjetaSeUsaEnEstacion.findTarjetaSeUsaEnEstacion(tsueePK);
        }
        
       
    
    //CONSULTA CON UN JOIN 
    //punto numero 4 del taller
    public void consultaConJoin()
    {
        Iterator i;       
        i = manager.createQuery("SELECT un_producto FROM TarjetaSeUsaEnEstacion p JOIN p.idProducto un_producto WHERE (un_producto.idProducto = 'PR-185'); ").getResultList().subList(1, 10).iterator();          
        System.out.print("id |\t Titulo  |\t Subtitulo\n");        
        while(i.hasNext())
        {
            //Producto p = (Producto) i.next();        
            TarjetaSeUsaEnEstacion unTarjetaSeUsaEnEstacion = (TarjetaSeUsaEnEstacion) i.next();
            System.out.println("-----------------------------------------------------------------------------------");            
            //System.out.println(p.getIdProducto()+"\t"+p.getNombre()+"\t"+p.getSerial());            
            System.out.println(unTarjetaSeUsaEnEstacion.getTarjetaSeUsaEnEstacionPK().getPinTarjeta());
        }
    }
    
    public void eliminar(String id_estacion, int id_tarjeta, Date fecha){
        try 
        {   
            TarjetaSeUsaEnEstacionPK tsueePK = new TarjetaSeUsaEnEstacionPK(id_tarjeta, id_estacion, fecha);
            daoTarjetaSeUsaEnEstacion.destroy(tsueePK);
            JOptionPane.showMessageDialog(null, "registro eliminado con éxito");
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
    
    public List<TarjetaSeUsaEnEstacion> consultarTarjetaSeUsaEnEstaciones(){
           List<TarjetaSeUsaEnEstacion> listaTarjetaSeUsaEnEstacion = daoTarjetaSeUsaEnEstacion.findTarjetaSeUsaEnEstacionEntities();
            return listaTarjetaSeUsaEnEstacion;
    }
    
}

