
package Controladores;


import DAO.BusJpaController;
import DAO.RutaJpaController;
import DAO.exceptions.NonexistentEntityException;
import DAO.exceptions.PreexistingEntityException;
import Entidades.*;
import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;
import javax.swing.JOptionPane;

public class ControladorBus 
{    
    BusJpaController daoBus;
    RutaJpaController daoRuta;
    
    //se requiere para elaborar consultas personalizadas
    EntityManager manager;
    
    public ControladorBus(FabricaObjetos mi_fabrica)
    {
       manager= mi_fabrica.crear().createEntityManager();
       
       //se requiere ingresar por parametro una fabrica de objetos para manipular las entidades (crear, modificar..en fin)
       daoBus = new BusJpaController(mi_fabrica.getFactory());
       daoRuta = new RutaJpaController(mi_fabrica.getFactory());
       
    }
    
    public String consultar()
    {
                        
            Iterator i;
            //sirve para ejecutar consultas
            i = manager.createQuery("SELECT p FROM Bus p").getResultList().subList(1, 10).iterator();
            System.out.print("id_bus |\t tipo_bus  |\t carroceria |\t motor |\t capacidad_parados |\t capacidad_sillas |\t ruta_asignada \n");
            String lista="";
            while(i.hasNext())
            {
                Bus b = (Bus) i.next();
                System.out.println("-----------------------------------------------------------------------------------");
                System.out.println(b.getIdBus()+"\t"+b.getTipoBus()+"\t"+b.getCarroceria()+"\t"+b.getCapacidadParados()+"\t"+b.getCapacidadSillas()+"\t"+b.getRutaAsignada()+"\t");
                lista+=b.getIdBus()+"\t"+b.getTipoBus()+"\t"+b.getCarroceria()+"\t"+b.getCapacidadParados()+"\t"+b.getCapacidadSillas()+"\t"+b.getRutaAsignada()+"\n";
            }
            return lista;
    }
    
    public Bus simularBus(String id_bus, String tipo_bus, String carroceria, int capacidadParados, int capacidadSentados, String rutaAsignada)
    {
        
        Ruta ruta = daoRuta.findRuta(rutaAsignada);
        

        Bus unBus = new Bus();
        unBus.setIdBus(id_bus);
        unBus.setTipoBus(tipo_bus);
        unBus.setCarroceria(carroceria);
        unBus.setCapacidadParados(capacidadParados);
        unBus.setCapacidadSillas(capacidadSentados);
        unBus.setRutaAsignada(ruta);
        
        return unBus;
    }
    
    public void insertar(String id_bus, String tipo_bus, String carroceria, int capacidadParados, int capacidadSentados, String rutaAsignada)
    {
        
        Ruta ruta = daoRuta.findRuta(rutaAsignada);
        

        Bus unBus = new Bus();
        unBus.setIdBus(id_bus);
        unBus.setTipoBus(tipo_bus);
        unBus.setCarroceria(carroceria);
        unBus.setCapacidadParados(capacidadParados);
        unBus.setCapacidadSillas(capacidadSentados);
        unBus.setRutaAsignada(ruta);
        
        try 
        {
            daoBus.create(unBus);
            JOptionPane.showMessageDialog(null, "El bus ha sido creado con exito");
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
    
    public void modificar(Bus bus)
    {
        try 
        {   
            daoBus.edit(bus);
            JOptionPane.showMessageDialog(null, "El bus ha sido modificado con exito");
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
    
    public Bus consultar(String idBus){
            return daoBus.findBus(idBus);
        }
        
       
    
    //CONSULTA CON UN JOIN 
    //punto numero 4 del taller
    public void consultaConJoin()
    {
        Iterator i;       
        i = manager.createQuery("SELECT un_producto FROM Bus p JOIN p.idProducto un_producto WHERE (un_producto.idProducto = 'PR-185'); ").getResultList().subList(1, 10).iterator();          
        System.out.print("id |\t Titulo  |\t Subtitulo\n");        
        while(i.hasNext())
        {
            //Producto p = (Producto) i.next();        
            Bus unBus = (Bus) i.next();
            System.out.println("-----------------------------------------------------------------------------------");            
            //System.out.println(p.getIdProducto()+"\t"+p.getNombre()+"\t"+p.getSerial());            
            System.out.println(unBus.getIdBus());
        }
    }
    
    public void eliminar(String idBus){
        try 
        {   
            daoBus.destroy(idBus);
            JOptionPane.showMessageDialog(null, "bus eliminado con éxito");
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
    
    public List<Bus> consultarBuses(){
           List<Bus> listaBus = daoBus.findBusEntities();
            return listaBus;
    }
    
}
