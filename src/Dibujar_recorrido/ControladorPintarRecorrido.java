/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Dibujar_recorrido;

import Controladores.ControladorRuta;
import Controladores.FabricaObjetos;
import DAO.DaoParadas;
import DAO.RutaJpaController;
import Entidades.Ruta;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.swing.JComboBox;

/**
 *
 * @author Usuario
 */
public class ControladorPintarRecorrido {
    DaoParadas dp;
    EntityManager manager;
    RutaJpaController daoRuta;
    
    public ControladorPintarRecorrido(FabricaObjetos mi_fabrica) {
        EntityManagerFactory factory = mi_fabrica.getFactory();
        dp = new DaoParadas();
        daoRuta = new RutaJpaController(mi_fabrica.getFactory());
    }
    
    public ArrayList<ParadaBus> ConsultarParadas() {
        return dp.consultarParadas();
    }
    
    public void cargarParadasEnCombo(JComboBox combo) {
        ArrayList<ParadaBus> todasLasParadas = dp.consultarParadas();
        
        String [] arregloParadas = new String[todasLasParadas.size()];
        
        for (int i=0; i<arregloParadas.length; i++) {
            arregloParadas[i] = todasLasParadas.get(i).getIdEstacion();
        }
        
        javax.swing.DefaultComboBoxModel model = new javax.swing.DefaultComboBoxModel(arregloParadas);
        combo.setModel(model);
    }
    
    public ArrayList<Ruta> buscarRutasRecorrido(String origen, String destino){
        List<Ruta> listaDeRutas = daoRuta.findRutaEntities();
        ArrayList<Ruta> rutasQueCumplenRecorrido = new ArrayList();
        
        System.out.println("se va buscar rutas q cumplan con el origen y destino del recorrido");
        for (Ruta ruta : listaDeRutas) {
            String paradas = ruta.getParadas();
            if(paradas.contains(origen) && paradas.contains(destino)) {
                System.out.println("se ha encontrado la ruta "+ruta.getIdRuta()+" q cumple con el recorrido deseado");
                rutasQueCumplenRecorrido.add(ruta);
            }
        }
        return rutasQueCumplenRecorrido;
    }
    
    public ArrayList<Recorrido> trazarRecorrido(ArrayList<Ruta> rutasRecorrido, String origen, String destino) {
        ArrayList<Recorrido> recorridos = new ArrayList();
        for (Ruta ruta : rutasRecorrido) {
            String paradasRuta = ruta.getParadas();
            String [] arregloParadas = paradasRuta.split(",");
            
            Recorrido recorrido = new Recorrido();
            recorrido.setNombreRuta(ruta.getIdRuta());
            recorrido.setOrigen(origen);
            recorrido.setDestino(destino);
            ArrayList<ParadaBus> paradasRecorrido = new ArrayList();
            
            int indiceOrigenEnArreglo = indexOfParada(origen, arregloParadas);
            int indiceDestinoEnArreglo = indexOfParada(destino, arregloParadas);
            System.out.println("indices de paradas en arreglo encontrados: origen="+indiceOrigenEnArreglo+" destino="+indiceDestinoEnArreglo);
            if (indiceOrigenEnArreglo<indiceDestinoEnArreglo) {
                for (int i=indiceOrigenEnArreglo; i<=indiceDestinoEnArreglo; i++) {
                    ParadaBus paradaConsultada = dp.consultarParada(arregloParadas[i]);
                    System.out.println("agregando parada " +paradaConsultada.getIdEstacion()+" en ubicacion: "+paradaConsultada.getX()+","+paradaConsultada.getY()+" al arreglo del recorrido de la ruta "+ recorrido.getNombreRuta());
                    paradasRecorrido.add(paradaConsultada);
                }
            }
            else if(indiceOrigenEnArreglo>indiceDestinoEnArreglo){
                for (int i=indiceDestinoEnArreglo; i<=indiceOrigenEnArreglo; i++) {
                    ParadaBus paradaConsultada = dp.consultarParada(arregloParadas[i]);
                    System.out.println("agregando parada " +paradaConsultada.getIdEstacion()+" en ubicacion: "+paradaConsultada.getX()+","+paradaConsultada.getY()+" al arreglo del recorrido de la ruta "+ recorrido.getNombreRuta());
                    paradasRecorrido.add(paradaConsultada);
                }
            }
            recorrido.setParadas(paradasRecorrido);
            recorrido.calcularDistanciaTotal();
            recorridos.add(recorrido);
        }
        return recorridos;
    }
    
    public Recorrido encontrarRecorridoMasCorto(ArrayList<Recorrido> recorridos) {
        int indiceRecorridoMasCorto=0;
        if(recorridos.size()>1){
            System.out.println("Comparando "+recorridos.size()+" recorridos");
            for (int i=1; i<recorridos.size(); i++) {
                if(recorridos.get(i).getDistanciaTotal()<recorridos.get(indiceRecorridoMasCorto).getDistanciaTotal())indiceRecorridoMasCorto=i ;
            }
            System.out.println("Recorrido mas corto "+recorridos.get(indiceRecorridoMasCorto).getNombreRuta());
        }
        return recorridos.get(indiceRecorridoMasCorto);
    }
    
    public int indexOfParada(String parada, String [] paradas) {
        for (int i=0; i<paradas.length; i++) {
            if(parada.equals(paradas[i])) return i;
        }
        return -1;
    }
    
    public static void main(String [] args) {
        
        
        ControladorPintarRecorrido cpr = new ControladorPintarRecorrido(new FabricaObjetos());
        System.out.println("Antes de buscar rutas recorrido");
        ArrayList<Ruta> rutasEncontradas = cpr.buscarRutasRecorrido("puente de los mil dias", "autopista con 45");
        System.out.println(rutasEncontradas.size()+" encontradas que cumplen el recorrido");
        ArrayList<Recorrido> recorridos = cpr.trazarRecorrido(rutasEncontradas, "puente de los mil dias", "autopista con 45");
        
        System.out.println("se han encontrado "+recorridos.size()+" recorridos para la consulta");
        
        for (Recorrido recorrido : recorridos) {
            System.out.println(recorrido.getNombreRuta()+" Desde "+recorrido.getOrigen()+" hasta "+recorrido.getDestino());
            System.out.println("con las siguientes paradas en orden");
            for (ParadaBus parada : recorrido.getParadas()) {
                System.out.println(parada.getIdEstacion()+" ubicada en "+parada.getX()+","+parada.getY());
            }
        }
        
        Dibujo dib = new Dibujo();
        dib.setRecorrido(recorridos.get(0));
        dib.setPreferredSize(new Dimension(1904, 3007));
        new VentanaRecorrido(dib).setVisible(true); 
    }
}
