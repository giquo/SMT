/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DAO.exceptions.NonexistentEntityException;
import DAO.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entidades.Tarjeta;
import Entidades.Estacion;
import Entidades.Empleado;
import Entidades.Recarga;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author jdavidva
 */
public class RecargaJpaController implements Serializable {

    public RecargaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Recarga recarga) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tarjeta pinTarjeta = recarga.getPinTarjeta();
            if (pinTarjeta != null) {
                pinTarjeta = em.getReference(pinTarjeta.getClass(), pinTarjeta.getPinTarjeta());
                recarga.setPinTarjeta(pinTarjeta);
            }
            Estacion idEstacion = recarga.getIdEstacion();
            if (idEstacion != null) {
                idEstacion = em.getReference(idEstacion.getClass(), idEstacion.getIdEstacion());
                recarga.setIdEstacion(idEstacion);
            }
            Empleado idEmpleado = recarga.getIdEmpleado();
            if (idEmpleado != null) {
                idEmpleado = em.getReference(idEmpleado.getClass(), idEmpleado.getIdEmpleado());
                recarga.setIdEmpleado(idEmpleado);
            }
            em.persist(recarga);
            if (pinTarjeta != null) {
                pinTarjeta.getRecargaList().add(recarga);
                pinTarjeta = em.merge(pinTarjeta);
            }
            if (idEstacion != null) {
                idEstacion.getRecargaList().add(recarga);
                idEstacion = em.merge(idEstacion);
            }
            if (idEmpleado != null) {
                idEmpleado.getRecargaList().add(recarga);
                idEmpleado = em.merge(idEmpleado);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findRecarga(recarga.getConsecutivo()) != null) {
                throw new PreexistingEntityException("Recarga " + recarga + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Recarga recarga) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Recarga persistentRecarga = em.find(Recarga.class, recarga.getConsecutivo());
            Tarjeta pinTarjetaOld = persistentRecarga.getPinTarjeta();
            Tarjeta pinTarjetaNew = recarga.getPinTarjeta();
            Estacion idEstacionOld = persistentRecarga.getIdEstacion();
            Estacion idEstacionNew = recarga.getIdEstacion();
            Empleado idEmpleadoOld = persistentRecarga.getIdEmpleado();
            Empleado idEmpleadoNew = recarga.getIdEmpleado();
            if (pinTarjetaNew != null) {
                pinTarjetaNew = em.getReference(pinTarjetaNew.getClass(), pinTarjetaNew.getPinTarjeta());
                recarga.setPinTarjeta(pinTarjetaNew);
            }
            if (idEstacionNew != null) {
                idEstacionNew = em.getReference(idEstacionNew.getClass(), idEstacionNew.getIdEstacion());
                recarga.setIdEstacion(idEstacionNew);
            }
            if (idEmpleadoNew != null) {
                idEmpleadoNew = em.getReference(idEmpleadoNew.getClass(), idEmpleadoNew.getIdEmpleado());
                recarga.setIdEmpleado(idEmpleadoNew);
            }
            recarga = em.merge(recarga);
            if (pinTarjetaOld != null && !pinTarjetaOld.equals(pinTarjetaNew)) {
                pinTarjetaOld.getRecargaList().remove(recarga);
                pinTarjetaOld = em.merge(pinTarjetaOld);
            }
            if (pinTarjetaNew != null && !pinTarjetaNew.equals(pinTarjetaOld)) {
                pinTarjetaNew.getRecargaList().add(recarga);
                pinTarjetaNew = em.merge(pinTarjetaNew);
            }
            if (idEstacionOld != null && !idEstacionOld.equals(idEstacionNew)) {
                idEstacionOld.getRecargaList().remove(recarga);
                idEstacionOld = em.merge(idEstacionOld);
            }
            if (idEstacionNew != null && !idEstacionNew.equals(idEstacionOld)) {
                idEstacionNew.getRecargaList().add(recarga);
                idEstacionNew = em.merge(idEstacionNew);
            }
            if (idEmpleadoOld != null && !idEmpleadoOld.equals(idEmpleadoNew)) {
                idEmpleadoOld.getRecargaList().remove(recarga);
                idEmpleadoOld = em.merge(idEmpleadoOld);
            }
            if (idEmpleadoNew != null && !idEmpleadoNew.equals(idEmpleadoOld)) {
                idEmpleadoNew.getRecargaList().add(recarga);
                idEmpleadoNew = em.merge(idEmpleadoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = recarga.getConsecutivo();
                if (findRecarga(id) == null) {
                    throw new NonexistentEntityException("The recarga with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Recarga recarga;
            try {
                recarga = em.getReference(Recarga.class, id);
                recarga.getConsecutivo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The recarga with id " + id + " no longer exists.", enfe);
            }
            Tarjeta pinTarjeta = recarga.getPinTarjeta();
            if (pinTarjeta != null) {
                pinTarjeta.getRecargaList().remove(recarga);
                pinTarjeta = em.merge(pinTarjeta);
            }
            Estacion idEstacion = recarga.getIdEstacion();
            if (idEstacion != null) {
                idEstacion.getRecargaList().remove(recarga);
                idEstacion = em.merge(idEstacion);
            }
            Empleado idEmpleado = recarga.getIdEmpleado();
            if (idEmpleado != null) {
                idEmpleado.getRecargaList().remove(recarga);
                idEmpleado = em.merge(idEmpleado);
            }
            em.remove(recarga);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Recarga> findRecargaEntities() {
        return findRecargaEntities(true, -1, -1);
    }

    public List<Recarga> findRecargaEntities(int maxResults, int firstResult) {
        return findRecargaEntities(false, maxResults, firstResult);
    }

    private List<Recarga> findRecargaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Recarga.class));
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

    public Recarga findRecarga(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Recarga.class, id);
        } finally {
            em.close();
        }
    }

    public int getRecargaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Recarga> rt = cq.from(Recarga.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
