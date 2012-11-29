/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DAO.exceptions.IllegalOrphanException;
import DAO.exceptions.NonexistentEntityException;
import DAO.exceptions.PreexistingEntityException;
import Entidades.*;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author jdavidva
 */
public class EstacionJpaController implements Serializable {

    public EstacionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Estacion estacion) throws PreexistingEntityException, Exception {
        if (estacion.getQuejasYReclamosList() == null) {
            estacion.setQuejasYReclamosList(new ArrayList<QuejasYReclamos>());
        }
        if (estacion.getRecargaList() == null) {
            estacion.setRecargaList(new ArrayList<Recarga>());
        }
        if (estacion.getVentaTarjetaList() == null) {
            estacion.setVentaTarjetaList(new ArrayList<VentaTarjeta>());
        }
        if (estacion.getTarjetaSeUsaEnEstacionList() == null) {
            estacion.setTarjetaSeUsaEnEstacionList(new ArrayList<TarjetaSeUsaEnEstacion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empleado empleadoEncargado = estacion.getEmpleadoEncargado();
            if (empleadoEncargado != null) {
                empleadoEncargado = em.getReference(empleadoEncargado.getClass(), empleadoEncargado.getIdEmpleado());
                estacion.setEmpleadoEncargado(empleadoEncargado);
            }
            List<QuejasYReclamos> attachedQuejasYReclamosList = new ArrayList<QuejasYReclamos>();
            for (QuejasYReclamos quejasYReclamosListQuejasYReclamosToAttach : estacion.getQuejasYReclamosList()) {
                quejasYReclamosListQuejasYReclamosToAttach = em.getReference(quejasYReclamosListQuejasYReclamosToAttach.getClass(), quejasYReclamosListQuejasYReclamosToAttach.getNoTicket());
                attachedQuejasYReclamosList.add(quejasYReclamosListQuejasYReclamosToAttach);
            }
            estacion.setQuejasYReclamosList(attachedQuejasYReclamosList);
            List<Recarga> attachedRecargaList = new ArrayList<Recarga>();
            for (Recarga recargaListRecargaToAttach : estacion.getRecargaList()) {
                recargaListRecargaToAttach = em.getReference(recargaListRecargaToAttach.getClass(), recargaListRecargaToAttach.getConsecutivo());
                attachedRecargaList.add(recargaListRecargaToAttach);
            }
            estacion.setRecargaList(attachedRecargaList);
            List<VentaTarjeta> attachedVentaTarjetaList = new ArrayList<VentaTarjeta>();
            for (VentaTarjeta ventaTarjetaListVentaTarjetaToAttach : estacion.getVentaTarjetaList()) {
                ventaTarjetaListVentaTarjetaToAttach = em.getReference(ventaTarjetaListVentaTarjetaToAttach.getClass(), ventaTarjetaListVentaTarjetaToAttach.getPinTarjeta());
                attachedVentaTarjetaList.add(ventaTarjetaListVentaTarjetaToAttach);
            }
            estacion.setVentaTarjetaList(attachedVentaTarjetaList);
            List<TarjetaSeUsaEnEstacion> attachedTarjetaSeUsaEnEstacionList = new ArrayList<TarjetaSeUsaEnEstacion>();
            for (TarjetaSeUsaEnEstacion tarjetaSeUsaEnEstacionListTarjetaSeUsaEnEstacionToAttach : estacion.getTarjetaSeUsaEnEstacionList()) {
                tarjetaSeUsaEnEstacionListTarjetaSeUsaEnEstacionToAttach = em.getReference(tarjetaSeUsaEnEstacionListTarjetaSeUsaEnEstacionToAttach.getClass(), tarjetaSeUsaEnEstacionListTarjetaSeUsaEnEstacionToAttach.getTarjetaSeUsaEnEstacionPK());
                attachedTarjetaSeUsaEnEstacionList.add(tarjetaSeUsaEnEstacionListTarjetaSeUsaEnEstacionToAttach);
            }
            estacion.setTarjetaSeUsaEnEstacionList(attachedTarjetaSeUsaEnEstacionList);
            em.persist(estacion);
            if (empleadoEncargado != null) {
                empleadoEncargado.getEstacionList().add(estacion);
                empleadoEncargado = em.merge(empleadoEncargado);
            }
            for (QuejasYReclamos quejasYReclamosListQuejasYReclamos : estacion.getQuejasYReclamosList()) {
                Estacion oldIdEstacionOfQuejasYReclamosListQuejasYReclamos = quejasYReclamosListQuejasYReclamos.getIdEstacion();
                quejasYReclamosListQuejasYReclamos.setIdEstacion(estacion);
                quejasYReclamosListQuejasYReclamos = em.merge(quejasYReclamosListQuejasYReclamos);
                if (oldIdEstacionOfQuejasYReclamosListQuejasYReclamos != null) {
                    oldIdEstacionOfQuejasYReclamosListQuejasYReclamos.getQuejasYReclamosList().remove(quejasYReclamosListQuejasYReclamos);
                    oldIdEstacionOfQuejasYReclamosListQuejasYReclamos = em.merge(oldIdEstacionOfQuejasYReclamosListQuejasYReclamos);
                }
            }
            for (Recarga recargaListRecarga : estacion.getRecargaList()) {
                Estacion oldIdEstacionOfRecargaListRecarga = recargaListRecarga.getIdEstacion();
                recargaListRecarga.setIdEstacion(estacion);
                recargaListRecarga = em.merge(recargaListRecarga);
                if (oldIdEstacionOfRecargaListRecarga != null) {
                    oldIdEstacionOfRecargaListRecarga.getRecargaList().remove(recargaListRecarga);
                    oldIdEstacionOfRecargaListRecarga = em.merge(oldIdEstacionOfRecargaListRecarga);
                }
            }
            for (VentaTarjeta ventaTarjetaListVentaTarjeta : estacion.getVentaTarjetaList()) {
                Estacion oldIdEstacionOfVentaTarjetaListVentaTarjeta = ventaTarjetaListVentaTarjeta.getIdEstacion();
                ventaTarjetaListVentaTarjeta.setIdEstacion(estacion);
                ventaTarjetaListVentaTarjeta = em.merge(ventaTarjetaListVentaTarjeta);
                if (oldIdEstacionOfVentaTarjetaListVentaTarjeta != null) {
                    oldIdEstacionOfVentaTarjetaListVentaTarjeta.getVentaTarjetaList().remove(ventaTarjetaListVentaTarjeta);
                    oldIdEstacionOfVentaTarjetaListVentaTarjeta = em.merge(oldIdEstacionOfVentaTarjetaListVentaTarjeta);
                }
            }
            for (TarjetaSeUsaEnEstacion tarjetaSeUsaEnEstacionListTarjetaSeUsaEnEstacion : estacion.getTarjetaSeUsaEnEstacionList()) {
                Estacion oldEstacionOfTarjetaSeUsaEnEstacionListTarjetaSeUsaEnEstacion = tarjetaSeUsaEnEstacionListTarjetaSeUsaEnEstacion.getEstacion();
                tarjetaSeUsaEnEstacionListTarjetaSeUsaEnEstacion.setEstacion(estacion);
                tarjetaSeUsaEnEstacionListTarjetaSeUsaEnEstacion = em.merge(tarjetaSeUsaEnEstacionListTarjetaSeUsaEnEstacion);
                if (oldEstacionOfTarjetaSeUsaEnEstacionListTarjetaSeUsaEnEstacion != null) {
                    oldEstacionOfTarjetaSeUsaEnEstacionListTarjetaSeUsaEnEstacion.getTarjetaSeUsaEnEstacionList().remove(tarjetaSeUsaEnEstacionListTarjetaSeUsaEnEstacion);
                    oldEstacionOfTarjetaSeUsaEnEstacionListTarjetaSeUsaEnEstacion = em.merge(oldEstacionOfTarjetaSeUsaEnEstacionListTarjetaSeUsaEnEstacion);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEstacion(estacion.getIdEstacion()) != null) {
                throw new PreexistingEntityException("Estacion " + estacion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Estacion estacion) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Estacion persistentEstacion = em.find(Estacion.class, estacion.getIdEstacion());
            Empleado empleadoEncargadoOld = persistentEstacion.getEmpleadoEncargado();
            Empleado empleadoEncargadoNew = estacion.getEmpleadoEncargado();
            List<QuejasYReclamos> quejasYReclamosListOld = persistentEstacion.getQuejasYReclamosList();
            List<QuejasYReclamos> quejasYReclamosListNew = estacion.getQuejasYReclamosList();
            List<Recarga> recargaListOld = persistentEstacion.getRecargaList();
            List<Recarga> recargaListNew = estacion.getRecargaList();
            List<VentaTarjeta> ventaTarjetaListOld = persistentEstacion.getVentaTarjetaList();
            List<VentaTarjeta> ventaTarjetaListNew = estacion.getVentaTarjetaList();
            List<TarjetaSeUsaEnEstacion> tarjetaSeUsaEnEstacionListOld = persistentEstacion.getTarjetaSeUsaEnEstacionList();
            List<TarjetaSeUsaEnEstacion> tarjetaSeUsaEnEstacionListNew = estacion.getTarjetaSeUsaEnEstacionList();
            List<String> illegalOrphanMessages = null;
            for (TarjetaSeUsaEnEstacion tarjetaSeUsaEnEstacionListOldTarjetaSeUsaEnEstacion : tarjetaSeUsaEnEstacionListOld) {
                if (!tarjetaSeUsaEnEstacionListNew.contains(tarjetaSeUsaEnEstacionListOldTarjetaSeUsaEnEstacion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TarjetaSeUsaEnEstacion " + tarjetaSeUsaEnEstacionListOldTarjetaSeUsaEnEstacion + " since its estacion field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (empleadoEncargadoNew != null) {
                empleadoEncargadoNew = em.getReference(empleadoEncargadoNew.getClass(), empleadoEncargadoNew.getIdEmpleado());
                estacion.setEmpleadoEncargado(empleadoEncargadoNew);
            }
            List<QuejasYReclamos> attachedQuejasYReclamosListNew = new ArrayList<QuejasYReclamos>();
            for (QuejasYReclamos quejasYReclamosListNewQuejasYReclamosToAttach : quejasYReclamosListNew) {
                quejasYReclamosListNewQuejasYReclamosToAttach = em.getReference(quejasYReclamosListNewQuejasYReclamosToAttach.getClass(), quejasYReclamosListNewQuejasYReclamosToAttach.getNoTicket());
                attachedQuejasYReclamosListNew.add(quejasYReclamosListNewQuejasYReclamosToAttach);
            }
            quejasYReclamosListNew = attachedQuejasYReclamosListNew;
            estacion.setQuejasYReclamosList(quejasYReclamosListNew);
            List<Recarga> attachedRecargaListNew = new ArrayList<Recarga>();
            for (Recarga recargaListNewRecargaToAttach : recargaListNew) {
                recargaListNewRecargaToAttach = em.getReference(recargaListNewRecargaToAttach.getClass(), recargaListNewRecargaToAttach.getConsecutivo());
                attachedRecargaListNew.add(recargaListNewRecargaToAttach);
            }
            recargaListNew = attachedRecargaListNew;
            estacion.setRecargaList(recargaListNew);
            List<VentaTarjeta> attachedVentaTarjetaListNew = new ArrayList<VentaTarjeta>();
            for (VentaTarjeta ventaTarjetaListNewVentaTarjetaToAttach : ventaTarjetaListNew) {
                ventaTarjetaListNewVentaTarjetaToAttach = em.getReference(ventaTarjetaListNewVentaTarjetaToAttach.getClass(), ventaTarjetaListNewVentaTarjetaToAttach.getPinTarjeta());
                attachedVentaTarjetaListNew.add(ventaTarjetaListNewVentaTarjetaToAttach);
            }
            ventaTarjetaListNew = attachedVentaTarjetaListNew;
            estacion.setVentaTarjetaList(ventaTarjetaListNew);
            List<TarjetaSeUsaEnEstacion> attachedTarjetaSeUsaEnEstacionListNew = new ArrayList<TarjetaSeUsaEnEstacion>();
            for (TarjetaSeUsaEnEstacion tarjetaSeUsaEnEstacionListNewTarjetaSeUsaEnEstacionToAttach : tarjetaSeUsaEnEstacionListNew) {
                tarjetaSeUsaEnEstacionListNewTarjetaSeUsaEnEstacionToAttach = em.getReference(tarjetaSeUsaEnEstacionListNewTarjetaSeUsaEnEstacionToAttach.getClass(), tarjetaSeUsaEnEstacionListNewTarjetaSeUsaEnEstacionToAttach.getTarjetaSeUsaEnEstacionPK());
                attachedTarjetaSeUsaEnEstacionListNew.add(tarjetaSeUsaEnEstacionListNewTarjetaSeUsaEnEstacionToAttach);
            }
            tarjetaSeUsaEnEstacionListNew = attachedTarjetaSeUsaEnEstacionListNew;
            estacion.setTarjetaSeUsaEnEstacionList(tarjetaSeUsaEnEstacionListNew);
            estacion = em.merge(estacion);
            if (empleadoEncargadoOld != null && !empleadoEncargadoOld.equals(empleadoEncargadoNew)) {
                empleadoEncargadoOld.getEstacionList().remove(estacion);
                empleadoEncargadoOld = em.merge(empleadoEncargadoOld);
            }
            if (empleadoEncargadoNew != null && !empleadoEncargadoNew.equals(empleadoEncargadoOld)) {
                empleadoEncargadoNew.getEstacionList().add(estacion);
                empleadoEncargadoNew = em.merge(empleadoEncargadoNew);
            }
            for (QuejasYReclamos quejasYReclamosListOldQuejasYReclamos : quejasYReclamosListOld) {
                if (!quejasYReclamosListNew.contains(quejasYReclamosListOldQuejasYReclamos)) {
                    quejasYReclamosListOldQuejasYReclamos.setIdEstacion(null);
                    quejasYReclamosListOldQuejasYReclamos = em.merge(quejasYReclamosListOldQuejasYReclamos);
                }
            }
            for (QuejasYReclamos quejasYReclamosListNewQuejasYReclamos : quejasYReclamosListNew) {
                if (!quejasYReclamosListOld.contains(quejasYReclamosListNewQuejasYReclamos)) {
                    Estacion oldIdEstacionOfQuejasYReclamosListNewQuejasYReclamos = quejasYReclamosListNewQuejasYReclamos.getIdEstacion();
                    quejasYReclamosListNewQuejasYReclamos.setIdEstacion(estacion);
                    quejasYReclamosListNewQuejasYReclamos = em.merge(quejasYReclamosListNewQuejasYReclamos);
                    if (oldIdEstacionOfQuejasYReclamosListNewQuejasYReclamos != null && !oldIdEstacionOfQuejasYReclamosListNewQuejasYReclamos.equals(estacion)) {
                        oldIdEstacionOfQuejasYReclamosListNewQuejasYReclamos.getQuejasYReclamosList().remove(quejasYReclamosListNewQuejasYReclamos);
                        oldIdEstacionOfQuejasYReclamosListNewQuejasYReclamos = em.merge(oldIdEstacionOfQuejasYReclamosListNewQuejasYReclamos);
                    }
                }
            }
            for (Recarga recargaListOldRecarga : recargaListOld) {
                if (!recargaListNew.contains(recargaListOldRecarga)) {
                    recargaListOldRecarga.setIdEstacion(null);
                    recargaListOldRecarga = em.merge(recargaListOldRecarga);
                }
            }
            for (Recarga recargaListNewRecarga : recargaListNew) {
                if (!recargaListOld.contains(recargaListNewRecarga)) {
                    Estacion oldIdEstacionOfRecargaListNewRecarga = recargaListNewRecarga.getIdEstacion();
                    recargaListNewRecarga.setIdEstacion(estacion);
                    recargaListNewRecarga = em.merge(recargaListNewRecarga);
                    if (oldIdEstacionOfRecargaListNewRecarga != null && !oldIdEstacionOfRecargaListNewRecarga.equals(estacion)) {
                        oldIdEstacionOfRecargaListNewRecarga.getRecargaList().remove(recargaListNewRecarga);
                        oldIdEstacionOfRecargaListNewRecarga = em.merge(oldIdEstacionOfRecargaListNewRecarga);
                    }
                }
            }
            for (VentaTarjeta ventaTarjetaListOldVentaTarjeta : ventaTarjetaListOld) {
                if (!ventaTarjetaListNew.contains(ventaTarjetaListOldVentaTarjeta)) {
                    ventaTarjetaListOldVentaTarjeta.setIdEstacion(null);
                    ventaTarjetaListOldVentaTarjeta = em.merge(ventaTarjetaListOldVentaTarjeta);
                }
            }
            for (VentaTarjeta ventaTarjetaListNewVentaTarjeta : ventaTarjetaListNew) {
                if (!ventaTarjetaListOld.contains(ventaTarjetaListNewVentaTarjeta)) {
                    Estacion oldIdEstacionOfVentaTarjetaListNewVentaTarjeta = ventaTarjetaListNewVentaTarjeta.getIdEstacion();
                    ventaTarjetaListNewVentaTarjeta.setIdEstacion(estacion);
                    ventaTarjetaListNewVentaTarjeta = em.merge(ventaTarjetaListNewVentaTarjeta);
                    if (oldIdEstacionOfVentaTarjetaListNewVentaTarjeta != null && !oldIdEstacionOfVentaTarjetaListNewVentaTarjeta.equals(estacion)) {
                        oldIdEstacionOfVentaTarjetaListNewVentaTarjeta.getVentaTarjetaList().remove(ventaTarjetaListNewVentaTarjeta);
                        oldIdEstacionOfVentaTarjetaListNewVentaTarjeta = em.merge(oldIdEstacionOfVentaTarjetaListNewVentaTarjeta);
                    }
                }
            }
            for (TarjetaSeUsaEnEstacion tarjetaSeUsaEnEstacionListNewTarjetaSeUsaEnEstacion : tarjetaSeUsaEnEstacionListNew) {
                if (!tarjetaSeUsaEnEstacionListOld.contains(tarjetaSeUsaEnEstacionListNewTarjetaSeUsaEnEstacion)) {
                    Estacion oldEstacionOfTarjetaSeUsaEnEstacionListNewTarjetaSeUsaEnEstacion = tarjetaSeUsaEnEstacionListNewTarjetaSeUsaEnEstacion.getEstacion();
                    tarjetaSeUsaEnEstacionListNewTarjetaSeUsaEnEstacion.setEstacion(estacion);
                    tarjetaSeUsaEnEstacionListNewTarjetaSeUsaEnEstacion = em.merge(tarjetaSeUsaEnEstacionListNewTarjetaSeUsaEnEstacion);
                    if (oldEstacionOfTarjetaSeUsaEnEstacionListNewTarjetaSeUsaEnEstacion != null && !oldEstacionOfTarjetaSeUsaEnEstacionListNewTarjetaSeUsaEnEstacion.equals(estacion)) {
                        oldEstacionOfTarjetaSeUsaEnEstacionListNewTarjetaSeUsaEnEstacion.getTarjetaSeUsaEnEstacionList().remove(tarjetaSeUsaEnEstacionListNewTarjetaSeUsaEnEstacion);
                        oldEstacionOfTarjetaSeUsaEnEstacionListNewTarjetaSeUsaEnEstacion = em.merge(oldEstacionOfTarjetaSeUsaEnEstacionListNewTarjetaSeUsaEnEstacion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = estacion.getIdEstacion();
                if (findEstacion(id) == null) {
                    throw new NonexistentEntityException("The estacion with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Estacion estacion;
            try {
                estacion = em.getReference(Estacion.class, id);
                estacion.getIdEstacion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The estacion with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<TarjetaSeUsaEnEstacion> tarjetaSeUsaEnEstacionListOrphanCheck = estacion.getTarjetaSeUsaEnEstacionList();
            for (TarjetaSeUsaEnEstacion tarjetaSeUsaEnEstacionListOrphanCheckTarjetaSeUsaEnEstacion : tarjetaSeUsaEnEstacionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Estacion (" + estacion + ") cannot be destroyed since the TarjetaSeUsaEnEstacion " + tarjetaSeUsaEnEstacionListOrphanCheckTarjetaSeUsaEnEstacion + " in its tarjetaSeUsaEnEstacionList field has a non-nullable estacion field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Empleado empleadoEncargado = estacion.getEmpleadoEncargado();
            if (empleadoEncargado != null) {
                empleadoEncargado.getEstacionList().remove(estacion);
                empleadoEncargado = em.merge(empleadoEncargado);
            }
            List<QuejasYReclamos> quejasYReclamosList = estacion.getQuejasYReclamosList();
            for (QuejasYReclamos quejasYReclamosListQuejasYReclamos : quejasYReclamosList) {
                quejasYReclamosListQuejasYReclamos.setIdEstacion(null);
                quejasYReclamosListQuejasYReclamos = em.merge(quejasYReclamosListQuejasYReclamos);
            }
            List<Recarga> recargaList = estacion.getRecargaList();
            for (Recarga recargaListRecarga : recargaList) {
                recargaListRecarga.setIdEstacion(null);
                recargaListRecarga = em.merge(recargaListRecarga);
            }
            List<VentaTarjeta> ventaTarjetaList = estacion.getVentaTarjetaList();
            for (VentaTarjeta ventaTarjetaListVentaTarjeta : ventaTarjetaList) {
                ventaTarjetaListVentaTarjeta.setIdEstacion(null);
                ventaTarjetaListVentaTarjeta = em.merge(ventaTarjetaListVentaTarjeta);
            }
            em.remove(estacion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Estacion> findEstacionEntities() {
        return findEstacionEntities(true, -1, -1);
    }

    public List<Estacion> findEstacionEntities(int maxResults, int firstResult) {
        return findEstacionEntities(false, maxResults, firstResult);
    }

    private List<Estacion> findEstacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Estacion.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Estacion findEstacion(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Estacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getEstacionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Estacion> rt = cq.from(Estacion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
