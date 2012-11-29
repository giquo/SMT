/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DAO.exceptions.NonexistentEntityException;
import DAO.exceptions.PreexistingEntityException;
import Entidades.Pasajero;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entidades.QuejasYReclamos;
import java.util.ArrayList;
import java.util.List;
import Entidades.VentaTarjeta;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author jdavidva
 */
public class PasajeroJpaController implements Serializable {

    public PasajeroJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Pasajero pasajero) throws PreexistingEntityException, Exception {
        if (pasajero.getQuejasYReclamosList() == null) {
            pasajero.setQuejasYReclamosList(new ArrayList<QuejasYReclamos>());
        }
        if (pasajero.getVentaTarjetaList() == null) {
            pasajero.setVentaTarjetaList(new ArrayList<VentaTarjeta>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<QuejasYReclamos> attachedQuejasYReclamosList = new ArrayList<QuejasYReclamos>();
            for (QuejasYReclamos quejasYReclamosListQuejasYReclamosToAttach : pasajero.getQuejasYReclamosList()) {
                quejasYReclamosListQuejasYReclamosToAttach = em.getReference(quejasYReclamosListQuejasYReclamosToAttach.getClass(), quejasYReclamosListQuejasYReclamosToAttach.getNoTicket());
                attachedQuejasYReclamosList.add(quejasYReclamosListQuejasYReclamosToAttach);
            }
            pasajero.setQuejasYReclamosList(attachedQuejasYReclamosList);
            List<VentaTarjeta> attachedVentaTarjetaList = new ArrayList<VentaTarjeta>();
            for (VentaTarjeta ventaTarjetaListVentaTarjetaToAttach : pasajero.getVentaTarjetaList()) {
                ventaTarjetaListVentaTarjetaToAttach = em.getReference(ventaTarjetaListVentaTarjetaToAttach.getClass(), ventaTarjetaListVentaTarjetaToAttach.getPinTarjeta());
                attachedVentaTarjetaList.add(ventaTarjetaListVentaTarjetaToAttach);
            }
            pasajero.setVentaTarjetaList(attachedVentaTarjetaList);
            em.persist(pasajero);
            for (QuejasYReclamos quejasYReclamosListQuejasYReclamos : pasajero.getQuejasYReclamosList()) {
                Pasajero oldIdPasajeroOfQuejasYReclamosListQuejasYReclamos = quejasYReclamosListQuejasYReclamos.getIdPasajero();
                quejasYReclamosListQuejasYReclamos.setIdPasajero(pasajero);
                quejasYReclamosListQuejasYReclamos = em.merge(quejasYReclamosListQuejasYReclamos);
                if (oldIdPasajeroOfQuejasYReclamosListQuejasYReclamos != null) {
                    oldIdPasajeroOfQuejasYReclamosListQuejasYReclamos.getQuejasYReclamosList().remove(quejasYReclamosListQuejasYReclamos);
                    oldIdPasajeroOfQuejasYReclamosListQuejasYReclamos = em.merge(oldIdPasajeroOfQuejasYReclamosListQuejasYReclamos);
                }
            }
            for (VentaTarjeta ventaTarjetaListVentaTarjeta : pasajero.getVentaTarjetaList()) {
                Pasajero oldIdPasajeroOfVentaTarjetaListVentaTarjeta = ventaTarjetaListVentaTarjeta.getIdPasajero();
                ventaTarjetaListVentaTarjeta.setIdPasajero(pasajero);
                ventaTarjetaListVentaTarjeta = em.merge(ventaTarjetaListVentaTarjeta);
                if (oldIdPasajeroOfVentaTarjetaListVentaTarjeta != null) {
                    oldIdPasajeroOfVentaTarjetaListVentaTarjeta.getVentaTarjetaList().remove(ventaTarjetaListVentaTarjeta);
                    oldIdPasajeroOfVentaTarjetaListVentaTarjeta = em.merge(oldIdPasajeroOfVentaTarjetaListVentaTarjeta);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPasajero(pasajero.getIdPasajero()) != null) {
                throw new PreexistingEntityException("Pasajero " + pasajero + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Pasajero pasajero) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pasajero persistentPasajero = em.find(Pasajero.class, pasajero.getIdPasajero());
            List<QuejasYReclamos> quejasYReclamosListOld = persistentPasajero.getQuejasYReclamosList();
            List<QuejasYReclamos> quejasYReclamosListNew = pasajero.getQuejasYReclamosList();
            List<VentaTarjeta> ventaTarjetaListOld = persistentPasajero.getVentaTarjetaList();
            List<VentaTarjeta> ventaTarjetaListNew = pasajero.getVentaTarjetaList();
            List<QuejasYReclamos> attachedQuejasYReclamosListNew = new ArrayList<QuejasYReclamos>();
            for (QuejasYReclamos quejasYReclamosListNewQuejasYReclamosToAttach : quejasYReclamosListNew) {
                quejasYReclamosListNewQuejasYReclamosToAttach = em.getReference(quejasYReclamosListNewQuejasYReclamosToAttach.getClass(), quejasYReclamosListNewQuejasYReclamosToAttach.getNoTicket());
                attachedQuejasYReclamosListNew.add(quejasYReclamosListNewQuejasYReclamosToAttach);
            }
            quejasYReclamosListNew = attachedQuejasYReclamosListNew;
            pasajero.setQuejasYReclamosList(quejasYReclamosListNew);
            List<VentaTarjeta> attachedVentaTarjetaListNew = new ArrayList<VentaTarjeta>();
            for (VentaTarjeta ventaTarjetaListNewVentaTarjetaToAttach : ventaTarjetaListNew) {
                ventaTarjetaListNewVentaTarjetaToAttach = em.getReference(ventaTarjetaListNewVentaTarjetaToAttach.getClass(), ventaTarjetaListNewVentaTarjetaToAttach.getPinTarjeta());
                attachedVentaTarjetaListNew.add(ventaTarjetaListNewVentaTarjetaToAttach);
            }
            ventaTarjetaListNew = attachedVentaTarjetaListNew;
            pasajero.setVentaTarjetaList(ventaTarjetaListNew);
            pasajero = em.merge(pasajero);
            for (QuejasYReclamos quejasYReclamosListOldQuejasYReclamos : quejasYReclamosListOld) {
                if (!quejasYReclamosListNew.contains(quejasYReclamosListOldQuejasYReclamos)) {
                    quejasYReclamosListOldQuejasYReclamos.setIdPasajero(null);
                    quejasYReclamosListOldQuejasYReclamos = em.merge(quejasYReclamosListOldQuejasYReclamos);
                }
            }
            for (QuejasYReclamos quejasYReclamosListNewQuejasYReclamos : quejasYReclamosListNew) {
                if (!quejasYReclamosListOld.contains(quejasYReclamosListNewQuejasYReclamos)) {
                    Pasajero oldIdPasajeroOfQuejasYReclamosListNewQuejasYReclamos = quejasYReclamosListNewQuejasYReclamos.getIdPasajero();
                    quejasYReclamosListNewQuejasYReclamos.setIdPasajero(pasajero);
                    quejasYReclamosListNewQuejasYReclamos = em.merge(quejasYReclamosListNewQuejasYReclamos);
                    if (oldIdPasajeroOfQuejasYReclamosListNewQuejasYReclamos != null && !oldIdPasajeroOfQuejasYReclamosListNewQuejasYReclamos.equals(pasajero)) {
                        oldIdPasajeroOfQuejasYReclamosListNewQuejasYReclamos.getQuejasYReclamosList().remove(quejasYReclamosListNewQuejasYReclamos);
                        oldIdPasajeroOfQuejasYReclamosListNewQuejasYReclamos = em.merge(oldIdPasajeroOfQuejasYReclamosListNewQuejasYReclamos);
                    }
                }
            }
            for (VentaTarjeta ventaTarjetaListOldVentaTarjeta : ventaTarjetaListOld) {
                if (!ventaTarjetaListNew.contains(ventaTarjetaListOldVentaTarjeta)) {
                    ventaTarjetaListOldVentaTarjeta.setIdPasajero(null);
                    ventaTarjetaListOldVentaTarjeta = em.merge(ventaTarjetaListOldVentaTarjeta);
                }
            }
            for (VentaTarjeta ventaTarjetaListNewVentaTarjeta : ventaTarjetaListNew) {
                if (!ventaTarjetaListOld.contains(ventaTarjetaListNewVentaTarjeta)) {
                    Pasajero oldIdPasajeroOfVentaTarjetaListNewVentaTarjeta = ventaTarjetaListNewVentaTarjeta.getIdPasajero();
                    ventaTarjetaListNewVentaTarjeta.setIdPasajero(pasajero);
                    ventaTarjetaListNewVentaTarjeta = em.merge(ventaTarjetaListNewVentaTarjeta);
                    if (oldIdPasajeroOfVentaTarjetaListNewVentaTarjeta != null && !oldIdPasajeroOfVentaTarjetaListNewVentaTarjeta.equals(pasajero)) {
                        oldIdPasajeroOfVentaTarjetaListNewVentaTarjeta.getVentaTarjetaList().remove(ventaTarjetaListNewVentaTarjeta);
                        oldIdPasajeroOfVentaTarjetaListNewVentaTarjeta = em.merge(oldIdPasajeroOfVentaTarjetaListNewVentaTarjeta);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = pasajero.getIdPasajero();
                if (findPasajero(id) == null) {
                    throw new NonexistentEntityException("The pasajero with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pasajero pasajero;
            try {
                pasajero = em.getReference(Pasajero.class, id);
                pasajero.getIdPasajero();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pasajero with id " + id + " no longer exists.", enfe);
            }
            List<QuejasYReclamos> quejasYReclamosList = pasajero.getQuejasYReclamosList();
            for (QuejasYReclamos quejasYReclamosListQuejasYReclamos : quejasYReclamosList) {
                quejasYReclamosListQuejasYReclamos.setIdPasajero(null);
                quejasYReclamosListQuejasYReclamos = em.merge(quejasYReclamosListQuejasYReclamos);
            }
            List<VentaTarjeta> ventaTarjetaList = pasajero.getVentaTarjetaList();
            for (VentaTarjeta ventaTarjetaListVentaTarjeta : ventaTarjetaList) {
                ventaTarjetaListVentaTarjeta.setIdPasajero(null);
                ventaTarjetaListVentaTarjeta = em.merge(ventaTarjetaListVentaTarjeta);
            }
            em.remove(pasajero);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Pasajero> findPasajeroEntities() {
        return findPasajeroEntities(true, -1, -1);
    }

    public List<Pasajero> findPasajeroEntities(int maxResults, int firstResult) {
        return findPasajeroEntities(false, maxResults, firstResult);
    }

    private List<Pasajero> findPasajeroEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pasajero.class));
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

    public Pasajero findPasajero(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pasajero.class, id);
        } finally {
            em.close();
        }
    }

    public int getPasajeroCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Pasajero> rt = cq.from(Pasajero.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
