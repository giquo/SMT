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
import Entidades.TarjetaSeUsaEnEstacion;
import Entidades.TarjetaSeUsaEnEstacionPK;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author jdavidva
 */
public class TarjetaSeUsaEnEstacionJpaController implements Serializable {

    public TarjetaSeUsaEnEstacionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TarjetaSeUsaEnEstacion tarjetaSeUsaEnEstacion) throws PreexistingEntityException, Exception {
        if (tarjetaSeUsaEnEstacion.getTarjetaSeUsaEnEstacionPK() == null) {
            tarjetaSeUsaEnEstacion.setTarjetaSeUsaEnEstacionPK(new TarjetaSeUsaEnEstacionPK());
        }
        tarjetaSeUsaEnEstacion.getTarjetaSeUsaEnEstacionPK().setPinTarjeta(tarjetaSeUsaEnEstacion.getTarjeta().getPinTarjeta());
        tarjetaSeUsaEnEstacion.getTarjetaSeUsaEnEstacionPK().setIdEstacion(tarjetaSeUsaEnEstacion.getEstacion().getIdEstacion());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tarjeta tarjeta = tarjetaSeUsaEnEstacion.getTarjeta();
            if (tarjeta != null) {
                tarjeta = em.getReference(tarjeta.getClass(), tarjeta.getPinTarjeta());
                tarjetaSeUsaEnEstacion.setTarjeta(tarjeta);
            }
            Estacion estacion = tarjetaSeUsaEnEstacion.getEstacion();
            if (estacion != null) {
                estacion = em.getReference(estacion.getClass(), estacion.getIdEstacion());
                tarjetaSeUsaEnEstacion.setEstacion(estacion);
            }
            em.persist(tarjetaSeUsaEnEstacion);
            if (tarjeta != null) {
                tarjeta.getTarjetaSeUsaEnEstacionList().add(tarjetaSeUsaEnEstacion);
                tarjeta = em.merge(tarjeta);
            }
            if (estacion != null) {
                estacion.getTarjetaSeUsaEnEstacionList().add(tarjetaSeUsaEnEstacion);
                estacion = em.merge(estacion);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTarjetaSeUsaEnEstacion(tarjetaSeUsaEnEstacion.getTarjetaSeUsaEnEstacionPK()) != null) {
                throw new PreexistingEntityException("TarjetaSeUsaEnEstacion " + tarjetaSeUsaEnEstacion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TarjetaSeUsaEnEstacion tarjetaSeUsaEnEstacion) throws NonexistentEntityException, Exception {
        tarjetaSeUsaEnEstacion.getTarjetaSeUsaEnEstacionPK().setPinTarjeta(tarjetaSeUsaEnEstacion.getTarjeta().getPinTarjeta());
        tarjetaSeUsaEnEstacion.getTarjetaSeUsaEnEstacionPK().setIdEstacion(tarjetaSeUsaEnEstacion.getEstacion().getIdEstacion());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TarjetaSeUsaEnEstacion persistentTarjetaSeUsaEnEstacion = em.find(TarjetaSeUsaEnEstacion.class, tarjetaSeUsaEnEstacion.getTarjetaSeUsaEnEstacionPK());
            Tarjeta tarjetaOld = persistentTarjetaSeUsaEnEstacion.getTarjeta();
            Tarjeta tarjetaNew = tarjetaSeUsaEnEstacion.getTarjeta();
            Estacion estacionOld = persistentTarjetaSeUsaEnEstacion.getEstacion();
            Estacion estacionNew = tarjetaSeUsaEnEstacion.getEstacion();
            if (tarjetaNew != null) {
                tarjetaNew = em.getReference(tarjetaNew.getClass(), tarjetaNew.getPinTarjeta());
                tarjetaSeUsaEnEstacion.setTarjeta(tarjetaNew);
            }
            if (estacionNew != null) {
                estacionNew = em.getReference(estacionNew.getClass(), estacionNew.getIdEstacion());
                tarjetaSeUsaEnEstacion.setEstacion(estacionNew);
            }
            tarjetaSeUsaEnEstacion = em.merge(tarjetaSeUsaEnEstacion);
            if (tarjetaOld != null && !tarjetaOld.equals(tarjetaNew)) {
                tarjetaOld.getTarjetaSeUsaEnEstacionList().remove(tarjetaSeUsaEnEstacion);
                tarjetaOld = em.merge(tarjetaOld);
            }
            if (tarjetaNew != null && !tarjetaNew.equals(tarjetaOld)) {
                tarjetaNew.getTarjetaSeUsaEnEstacionList().add(tarjetaSeUsaEnEstacion);
                tarjetaNew = em.merge(tarjetaNew);
            }
            if (estacionOld != null && !estacionOld.equals(estacionNew)) {
                estacionOld.getTarjetaSeUsaEnEstacionList().remove(tarjetaSeUsaEnEstacion);
                estacionOld = em.merge(estacionOld);
            }
            if (estacionNew != null && !estacionNew.equals(estacionOld)) {
                estacionNew.getTarjetaSeUsaEnEstacionList().add(tarjetaSeUsaEnEstacion);
                estacionNew = em.merge(estacionNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                TarjetaSeUsaEnEstacionPK id = tarjetaSeUsaEnEstacion.getTarjetaSeUsaEnEstacionPK();
                if (findTarjetaSeUsaEnEstacion(id) == null) {
                    throw new NonexistentEntityException("The tarjetaSeUsaEnEstacion with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(TarjetaSeUsaEnEstacionPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TarjetaSeUsaEnEstacion tarjetaSeUsaEnEstacion;
            try {
                tarjetaSeUsaEnEstacion = em.getReference(TarjetaSeUsaEnEstacion.class, id);
                tarjetaSeUsaEnEstacion.getTarjetaSeUsaEnEstacionPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tarjetaSeUsaEnEstacion with id " + id + " no longer exists.", enfe);
            }
            Tarjeta tarjeta = tarjetaSeUsaEnEstacion.getTarjeta();
            if (tarjeta != null) {
                tarjeta.getTarjetaSeUsaEnEstacionList().remove(tarjetaSeUsaEnEstacion);
                tarjeta = em.merge(tarjeta);
            }
            Estacion estacion = tarjetaSeUsaEnEstacion.getEstacion();
            if (estacion != null) {
                estacion.getTarjetaSeUsaEnEstacionList().remove(tarjetaSeUsaEnEstacion);
                estacion = em.merge(estacion);
            }
            em.remove(tarjetaSeUsaEnEstacion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TarjetaSeUsaEnEstacion> findTarjetaSeUsaEnEstacionEntities() {
        return findTarjetaSeUsaEnEstacionEntities(true, -1, -1);
    }

    public List<TarjetaSeUsaEnEstacion> findTarjetaSeUsaEnEstacionEntities(int maxResults, int firstResult) {
        return findTarjetaSeUsaEnEstacionEntities(false, maxResults, firstResult);
    }

    private List<TarjetaSeUsaEnEstacion> findTarjetaSeUsaEnEstacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TarjetaSeUsaEnEstacion.class));
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

    public TarjetaSeUsaEnEstacion findTarjetaSeUsaEnEstacion(TarjetaSeUsaEnEstacionPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TarjetaSeUsaEnEstacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getTarjetaSeUsaEnEstacionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TarjetaSeUsaEnEstacion> rt = cq.from(TarjetaSeUsaEnEstacion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
