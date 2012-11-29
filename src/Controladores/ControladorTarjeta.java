/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

/**
 *
 * @author jdavidva
 */

import DAO.TarjetaJpaController;
import DAO.exceptions.NonexistentEntityException;
import DAO.exceptions.PreexistingEntityException;
import Entidades.*;
import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;
import javax.swing.JOptionPane;

public class ControladorTarjeta 
{    
    TarjetaJpaController daoTarjeta;
    
    //se requiere para elaborar consultas personalizadas
    EntityManager manager;
    
    public ControladorTarjeta(FabricaObjetos mi_fabrica)
    {
       manager= mi_fabrica.crear().createEntityManager();
       
       //se requiere ingresar por parametro una fabrica de objetos para manipular las entidades (crear, modificar..en fin)
       daoTarjeta = new TarjetaJpaController(mi_fabrica.getFactory());
       
    }
    
    public String consultar()
    {
                        
            Iterator i;
            //sirve para ejecutar consultas
            i = manager.createQuery("SELECT p FROM Tarjeta p").getResultList().subList(1, 10).iterator();
            System.out.print("pin_tarjeta |\t tipo_tarjeta |\t estado_tarjeta |\t saldo \n");
            String lista="";
            while(i.hasNext())
            {
                Tarjeta t = (Tarjeta) i.next();
                System.out.println("-----------------------------------------------------------------------------------");
                System.out.println(t.getPinTarjeta()+"\t"+t.getTipoTarjeta()+"\t"+t.getEstadoTarjeta()+"\t"+t.getSaldo()+"\t");
                lista+=t.getPinTarjeta()+"\t"+t.getTipoTarjeta()+"\t"+t.getEstadoTarjeta()+"\t"+t.getSaldo()+"\n";
            }
            return lista;
    }
    
    public void insertar(int pin_tarjeta, String tipo_tarjeta, String estado_tarjeta, int saldo)
    {
       
        Tarjeta unTarjeta = new Tarjeta();
        unTarjeta.setPinTarjeta(pin_tarjeta);
        unTarjeta.setTipoTarjeta(tipo_tarjeta);
        unTarjeta.setEstadoTarjeta(estado_tarjeta);
        unTarjeta.setSaldo(saldo);
        
        try 
        {
            daoTarjeta.create(unTarjeta);
            JOptionPane.showMessageDialog(null, "La tarjeta ha sido creado con exito");
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
    
    public void modificar(Tarjeta tarjeta)
    {
        try 
        {   
            daoTarjeta.edit(tarjeta);
            JOptionPane.showMessageDialog(null, "La tarjeta ha sido modificado con exito");
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
    
    public Tarjeta consultar(int idTarjeta){
            return daoTarjeta.findTarjeta(idTarjeta);
        }
        
       
    
    //CONSULTA CON UN JOIN 
    //punto numero 4 del taller
    public void consultaConJoin()
    {
        Iterator i;       
        i = manager.createQuery("SELECT un_producto FROM Tarjeta p JOIN p.idProducto un_producto WHERE (un_producto.idProducto = 'PR-185'); ").getResultList().subList(1, 10).iterator();          
        System.out.print("id |\t Titulo  |\t Subtitulo\n");        
        while(i.hasNext())
        {
            //Producto p = (Producto) i.next();        
            Tarjeta unTarjeta = (Tarjeta) i.next();
            System.out.println("-----------------------------------------------------------------------------------");            
            //System.out.println(p.getIdProducto()+"\t"+p.getNombre()+"\t"+p.getSerial());            
            System.out.println(unTarjeta.getPinTarjeta());
        }
    }
    
    public void eliminar(int idTarjeta){
        try 
        {   
            daoTarjeta.destroy(idTarjeta);
            JOptionPane.showMessageDialog(null, "tarjeta eliminado con éxito");
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
    
    public List<Tarjeta> consultarTarjetaes(){
           List<Tarjeta> listaTarjeta = daoTarjeta.findTarjetaEntities();
            return listaTarjeta;
    }
    
}
