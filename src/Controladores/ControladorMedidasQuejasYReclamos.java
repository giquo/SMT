/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import DAO.MedidasQuejasYReclamosJpaController;
import DAO.QuejasYReclamosJpaController;
import DAO.QuejasYReclamosJpaController;
import DAO.exceptions.NonexistentEntityException;
import DAO.exceptions.PreexistingEntityException;
import Entidades.*;
import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;
import javax.swing.JOptionPane;

/**
 *
 * @author jdavidva
 */
public class ControladorMedidasQuejasYReclamos 
{    
    MedidasQuejasYReclamosJpaController daoMedidasQuejasYReclamos;
    QuejasYReclamosJpaController daoQuejasYReclamos;
    
    
    //se requiere para elaborar consultas personalizadas
    EntityManager manager;
    
    public ControladorMedidasQuejasYReclamos(FabricaObjetos mi_fabrica)
    {
       manager= mi_fabrica.crear().createEntityManager();
       
       //se requiere ingresar por parametro una fabrica de objetos para manipular las entidades (crear, modificar..en fin)
       daoMedidasQuejasYReclamos = new MedidasQuejasYReclamosJpaController(mi_fabrica.getFactory());
       daoQuejasYReclamos = new QuejasYReclamosJpaController(mi_fabrica.getFactory());
       
    }
    
    public String consultar()
    {
                        
            Iterator i;
            //sirve para ejecutar consultas
            i = manager.createQuery("SELECT p FROM MedidasQuejasYReclamos p").getResultList().subList(1, 10).iterator();
            System.out.print("no_ticket |\t medida_tomada\n");
            String lista="";
            while(i.hasNext())
            {
                MedidasQuejasYReclamos m = (MedidasQuejasYReclamos) i.next();
                System.out.println("-----------------------------------------------------------------------------------");
                System.out.println(m.getMedidasQuejasYReclamosPK().getNoTicket()+"\t"+m.getMedidasQuejasYReclamosPK().getMedidaTomada()+"\t"+m.getQuejasYReclamos()+"\t");
                lista+=m.getMedidasQuejasYReclamosPK()+"\t"+m.getQuejasYReclamos()+"\n";
            }
            return lista;
    }
    
    public void insertar(int no_ticket, String medida_tomada)
    {
        
        QuejasYReclamos qyr = daoQuejasYReclamos.findQuejasYReclamos(no_ticket);
        
        MedidasQuejasYReclamosPK mQYRpk = new MedidasQuejasYReclamosPK(no_ticket, medida_tomada);
        
        MedidasQuejasYReclamos unMQR = new MedidasQuejasYReclamos();
        unMQR.setQuejasYReclamos(qyr);
        unMQR.setMedidasQuejasYReclamosPK(mQYRpk);

        
        try 
        {
            daoMedidasQuejasYReclamos.create(unMQR);
            JOptionPane.showMessageDialog(null, "La MQR ha sido creado con exito");
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
    
    public void modificar(int no_ticket, String medida_tomada)
    {
        try 
        {   
            QuejasYReclamos qyr = daoQuejasYReclamos.findQuejasYReclamos(no_ticket);
            MedidasQuejasYReclamosPK mQYRpk = new MedidasQuejasYReclamosPK(no_ticket, medida_tomada);
        
            MedidasQuejasYReclamos unMQR = new MedidasQuejasYReclamos();
            unMQR.setQuejasYReclamos(qyr);
            unMQR.setMedidasQuejasYReclamosPK(mQYRpk);
            daoMedidasQuejasYReclamos.edit(unMQR);
            JOptionPane.showMessageDialog(null, "la MQR ha sido modificado con exito");
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
    
    public MedidasQuejasYReclamos consultar(int no_ticket, String medida_tomada){
        
            MedidasQuejasYReclamosPK mQYRpk = new MedidasQuejasYReclamosPK(no_ticket, medida_tomada);
            return daoMedidasQuejasYReclamos.findMedidasQuejasYReclamos(mQYRpk);
        }
        
       
    
    //CONSULTA CON UN JOIN 
    //punto numero 4 del taller
    public void consultaConJoin()
    {
        Iterator i;       
        i = manager.createQuery("SELECT un_producto FROM MedidasQuejasYReclamos p JOIN p.idProducto un_producto WHERE (un_producto.idProducto = 'PR-185'); ").getResultList().subList(1, 10).iterator();          
        System.out.print("id |\t Titulo  |\t Subtitulo\n");        
        while(i.hasNext())
        {
            //Producto p = (Producto) i.next();        
            MedidasQuejasYReclamos unMedidasQuejasYReclamos = (MedidasQuejasYReclamos) i.next();
            System.out.println("-----------------------------------------------------------------------------------");            
            //System.out.println(p.getIdProducto()+"\t"+p.getNombre()+"\t"+p.getSerial());            
            System.out.println(unMedidasQuejasYReclamos.getMedidasQuejasYReclamosPK().getNoTicket()+"\t"+unMedidasQuejasYReclamos.getMedidasQuejasYReclamosPK().getMedidaTomada());
        }
    }
    
    public void eliminar(int no_ticket, String medida_tomada){
        try 
        {   
            MedidasQuejasYReclamosPK mQYRpk = new MedidasQuejasYReclamosPK(no_ticket, medida_tomada); 
            daoMedidasQuejasYReclamos.destroy(mQYRpk);
            JOptionPane.showMessageDialog(null, "medida eliminada con éxito");
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
    
    public List<MedidasQuejasYReclamos> consultarProductos(){
           List<MedidasQuejasYReclamos> listaProveedores = daoMedidasQuejasYReclamos.findMedidasQuejasYReclamosEntities();
            return listaProveedores;
    }
    
}