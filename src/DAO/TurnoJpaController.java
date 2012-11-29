/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DAO.exceptions.NonexistentEntityException;
import DAO.exceptions.PreexistingEntityException;
import Entidades.*;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author jdavidva
 */
public class TurnoJpaController implements Serializable {

    public TurnoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Turno turno) throws PreexistingEntityException, Exception {
        if (turno.getTurnoPK() == null) {
            turno.setTurnoPK(new TurnoPK());
        }
        turno.getTurnoPK().setIdBus(turno.getBus().getIdBus());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ruta idRuta = turno.getIdRuta();
            if (idRuta != null) {
                idRuta = em.getReference(idRuta.getClass(), idRuta.getIdRuta());
                turno.setIdRuta(idRuta);
            }
            Empleado idEmpleado = turno.getIdEmpleado();
            if (idEmpleado != null) {
                idEmpleado = em.getReference(idEmpleado.getClass(), idEmpleado.getIdEmpleado());
                turno.setIdEmpleado(idEmpleado);
            }
            Bus bus = turno.getBus();
            if (bus != null) {
                bus = em.getReference(bus.getClass(), bus.getIdBus());
                turno.setBus(bus);
            }
            em.persist(turno);
            if (idRuta != null) {
                idRuta.getTurnoList().add(turno);
                idRuta = em.merge(idRuta);
            }
            if (idEmpleado != null) {
                idEmpleado.getTurnoList().add(turno);
                idEmpleado = em.merge(idEmpleado);
            }
            if (bus != null) {
                bus.getTurnoList().add(turno);
                bus = em.merge(bus);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTurno(turno.getTurnoPK()) != null) {
                throw new PreexistingEntityException("Turno " + turno + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Turno turno) throws NonexistentEntityException, Exception {
        turno.getTurnoPK().setIdBus(turno.getBus().getIdBus());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Turno persistentTurno = em.find(Turno.class, turno.getTurnoPK());
            Ruta idRutaOld = persistentTurno.getIdRuta();
            Ruta idRutaNew = turno.getIdRuta();
            Empleado idEmpleadoOld = persistentTurno.getIdEmpleado();
            Empleado idEmpleadoNew = turno.getIdEmpleado();
            Bus busOld = persistentTurno.getBus();
            Bus busNew = turno.getBus();
            if (idRutaNew != null) {
                idRutaNew = em.getReference(idRutaNew.getClass(), idRutaNew.getIdRuta());
                turno.setIdRuta(idRutaNew);
            }
            if (idEmpleadoNew != null) {
                idEmpleadoNew = em.getReference(idEmpleadoNew.getClass(), idEmpleadoNew.getIdEmpleado());
                turno.setIdEmpleado(idEmpleadoNew);
            }
            if (busNew != null) {
                busNew = em.getReference(busNew.getClass(), busNew.getIdBus());
                turno.setBus(busNew);
            }
            turno = em.merge(turno);
            if (idRutaOld != null && !idRutaOld.equals(idRutaNew)) {
                idRutaOld.getTurnoList().remove(turno);
                idRutaOld = em.merge(idRutaOld);
            }
            if (idRutaNew != null && !idRutaNew.equals(idRutaOld)) {
                idRutaNew.getTurnoList().add(turno);
                idRutaNew = em.merge(idRutaNew);
            }
            if (idEmpleadoOld != null && !idEmpleadoOld.equals(idEmpleadoNew)) {
                idEmpleadoOld.getTurnoList().remove(turno);
                idEmpleadoOld = em.merge(idEmpleadoOld);
            }
            if (idEmpleadoNew != null && !idEmpleadoNew.equals(idEmpleadoOld)) {
                idEmpleadoNew.getTurnoList().add(turno);
                idEmpleadoNew = em.merge(idEmpleadoNew);
            }
            if (busOld != null && !busOld.equals(busNew)) {
                busOld.getTurnoList().remove(turno);
                busOld = em.merge(busOld);
            }
            if (busNew != null && !busNew.equals(busOld)) {
                busNew.getTurnoList().add(turno);
                busNew = em.merge(busNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                TurnoPK id = turno.getTurnoPK();
                if (findTurno(id) == null) {
                    throw new NonexistentEntityException("The turno with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(TurnoPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Turno turno;
            try {
                turno = em.getReference(Turno.class, id);
                turno.getTurnoPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The turno with id " + id + " no longer exists.", enfe);
            }
            Ruta idRuta = turno.getIdRuta();
            if (idRuta != null) {
                idRuta.getTurnoList().remove(turno);
                idRuta = em.merge(idRuta);
            }
            Empleado idEmpleado = turno.getIdEmpleado();
            if (idEmpleado != null) {
                idEmpleado.getTurnoList().remove(turno);
                idEmpleado = em.merge(idEmpleado);
            }
            Bus bus = turno.getBus();
            if (bus != null) {
                bus.getTurnoList().remove(turno);
                bus = em.merge(bus);
            }
            em.remove(turno);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Turno> findTurnoEntities() {
        return findTurnoEntities(true, -1, -1);
    }

    public List<Turno> findTurnoEntities(int maxResults, int firstResult) {
        return findTurnoEntities(false, maxResults, firstResult);
    }

    private List<Turno> findTurnoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Turno.class));
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

    public Turno findTurno(TurnoPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Turno.class, id);
        } finally {
            em.close();
        }
    }

    public int getTurnoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Turno> rt = cq.from(Turno.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
