/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DAO.exceptions.IllegalOrphanException;
import DAO.exceptions.NonexistentEntityException;
import DAO.exceptions.PreexistingEntityException;
import Entidades.Bus;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entidades.Ruta;
import Entidades.Turno;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author jdavidva
 */
public class BusJpaController implements Serializable {

    public BusJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Bus bus) throws PreexistingEntityException, Exception {
        if (bus.getTurnoList() == null) {
            bus.setTurnoList(new ArrayList<Turno>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ruta rutaAsignada = bus.getRutaAsignada();
            if (rutaAsignada != null) {
                rutaAsignada = em.getReference(rutaAsignada.getClass(), rutaAsignada.getIdRuta());
                bus.setRutaAsignada(rutaAsignada);
            }
            List<Turno> attachedTurnoList = new ArrayList<Turno>();
            for (Turno turnoListTurnoToAttach : bus.getTurnoList()) {
                turnoListTurnoToAttach = em.getReference(turnoListTurnoToAttach.getClass(), turnoListTurnoToAttach.getTurnoPK());
                attachedTurnoList.add(turnoListTurnoToAttach);
            }
            bus.setTurnoList(attachedTurnoList);
            em.persist(bus);
            if (rutaAsignada != null) {
                rutaAsignada.getBusList().add(bus);
                rutaAsignada = em.merge(rutaAsignada);
            }
            for (Turno turnoListTurno : bus.getTurnoList()) {
                Bus oldBusOfTurnoListTurno = turnoListTurno.getBus();
                turnoListTurno.setBus(bus);
                turnoListTurno = em.merge(turnoListTurno);
                if (oldBusOfTurnoListTurno != null) {
                    oldBusOfTurnoListTurno.getTurnoList().remove(turnoListTurno);
                    oldBusOfTurnoListTurno = em.merge(oldBusOfTurnoListTurno);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findBus(bus.getIdBus()) != null) {
                throw new PreexistingEntityException("Bus " + bus + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Bus bus) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Bus persistentBus = em.find(Bus.class, bus.getIdBus());
            Ruta rutaAsignadaOld = persistentBus.getRutaAsignada();
            Ruta rutaAsignadaNew = bus.getRutaAsignada();
            List<Turno> turnoListOld = persistentBus.getTurnoList();
            List<Turno> turnoListNew = bus.getTurnoList();
            List<String> illegalOrphanMessages = null;
            for (Turno turnoListOldTurno : turnoListOld) {
                if (!turnoListNew.contains(turnoListOldTurno)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Turno " + turnoListOldTurno + " since its bus field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (rutaAsignadaNew != null) {
                rutaAsignadaNew = em.getReference(rutaAsignadaNew.getClass(), rutaAsignadaNew.getIdRuta());
                bus.setRutaAsignada(rutaAsignadaNew);
            }
            List<Turno> attachedTurnoListNew = new ArrayList<Turno>();
            for (Turno turnoListNewTurnoToAttach : turnoListNew) {
                turnoListNewTurnoToAttach = em.getReference(turnoListNewTurnoToAttach.getClass(), turnoListNewTurnoToAttach.getTurnoPK());
                attachedTurnoListNew.add(turnoListNewTurnoToAttach);
            }
            turnoListNew = attachedTurnoListNew;
            bus.setTurnoList(turnoListNew);
            bus = em.merge(bus);
            if (rutaAsignadaOld != null && !rutaAsignadaOld.equals(rutaAsignadaNew)) {
                rutaAsignadaOld.getBusList().remove(bus);
                rutaAsignadaOld = em.merge(rutaAsignadaOld);
            }
            if (rutaAsignadaNew != null && !rutaAsignadaNew.equals(rutaAsignadaOld)) {
                rutaAsignadaNew.getBusList().add(bus);
                rutaAsignadaNew = em.merge(rutaAsignadaNew);
            }
            for (Turno turnoListNewTurno : turnoListNew) {
                if (!turnoListOld.contains(turnoListNewTurno)) {
                    Bus oldBusOfTurnoListNewTurno = turnoListNewTurno.getBus();
                    turnoListNewTurno.setBus(bus);
                    turnoListNewTurno = em.merge(turnoListNewTurno);
                    if (oldBusOfTurnoListNewTurno != null && !oldBusOfTurnoListNewTurno.equals(bus)) {
                        oldBusOfTurnoListNewTurno.getTurnoList().remove(turnoListNewTurno);
                        oldBusOfTurnoListNewTurno = em.merge(oldBusOfTurnoListNewTurno);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = bus.getIdBus();
                if (findBus(id) == null) {
                    throw new NonexistentEntityException("The bus with id " + id + " no longer exists.");
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
            Bus bus;
            try {
                bus = em.getReference(Bus.class, id);
                bus.getIdBus();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The bus with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Turno> turnoListOrphanCheck = bus.getTurnoList();
            for (Turno turnoListOrphanCheckTurno : turnoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Bus (" + bus + ") cannot be destroyed since the Turno " + turnoListOrphanCheckTurno + " in its turnoList field has a non-nullable bus field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Ruta rutaAsignada = bus.getRutaAsignada();
            if (rutaAsignada != null) {
                rutaAsignada.getBusList().remove(bus);
                rutaAsignada = em.merge(rutaAsignada);
            }
            em.remove(bus);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Bus> findBusEntities() {
        return findBusEntities(true, -1, -1);
    }

    public List<Bus> findBusEntities(int maxResults, int firstResult) {
        return findBusEntities(false, maxResults, firstResult);
    }

    private List<Bus> findBusEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Bus.class));
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

    public Bus findBus(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Bus.class, id);
        } finally {
            em.close();
        }
    }

    public int getBusCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Bus> rt = cq.from(Bus.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
