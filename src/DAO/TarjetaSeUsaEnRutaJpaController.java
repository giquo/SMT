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
import Entidades.Ruta;
import Entidades.TarjetaSeUsaEnRuta;
import Entidades.TarjetaSeUsaEnRutaPK;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author jdavidva
 */
public class TarjetaSeUsaEnRutaJpaController implements Serializable {

    public TarjetaSeUsaEnRutaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TarjetaSeUsaEnRuta tarjetaSeUsaEnRuta) throws PreexistingEntityException, Exception {
        if (tarjetaSeUsaEnRuta.getTarjetaSeUsaEnRutaPK() == null) {
            tarjetaSeUsaEnRuta.setTarjetaSeUsaEnRutaPK(new TarjetaSeUsaEnRutaPK());
        }
        tarjetaSeUsaEnRuta.getTarjetaSeUsaEnRutaPK().setIdRuta(tarjetaSeUsaEnRuta.getRuta().getIdRuta());
        tarjetaSeUsaEnRuta.getTarjetaSeUsaEnRutaPK().setPinTarjeta(tarjetaSeUsaEnRuta.getTarjeta().getPinTarjeta());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tarjeta tarjeta = tarjetaSeUsaEnRuta.getTarjeta();
            if (tarjeta != null) {
                tarjeta = em.getReference(tarjeta.getClass(), tarjeta.getPinTarjeta());
                tarjetaSeUsaEnRuta.setTarjeta(tarjeta);
            }
            Ruta ruta = tarjetaSeUsaEnRuta.getRuta();
            if (ruta != null) {
                ruta = em.getReference(ruta.getClass(), ruta.getIdRuta());
                tarjetaSeUsaEnRuta.setRuta(ruta);
            }
            em.persist(tarjetaSeUsaEnRuta);
            if (tarjeta != null) {
                tarjeta.getTarjetaSeUsaEnRutaList().add(tarjetaSeUsaEnRuta);
                tarjeta = em.merge(tarjeta);
            }
            if (ruta != null) {
                ruta.getTarjetaSeUsaEnRutaList().add(tarjetaSeUsaEnRuta);
                ruta = em.merge(ruta);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTarjetaSeUsaEnRuta(tarjetaSeUsaEnRuta.getTarjetaSeUsaEnRutaPK()) != null) {
                throw new PreexistingEntityException("TarjetaSeUsaEnRuta " + tarjetaSeUsaEnRuta + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TarjetaSeUsaEnRuta tarjetaSeUsaEnRuta) throws NonexistentEntityException, Exception {
        tarjetaSeUsaEnRuta.getTarjetaSeUsaEnRutaPK().setIdRuta(tarjetaSeUsaEnRuta.getRuta().getIdRuta());
        tarjetaSeUsaEnRuta.getTarjetaSeUsaEnRutaPK().setPinTarjeta(tarjetaSeUsaEnRuta.getTarjeta().getPinTarjeta());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TarjetaSeUsaEnRuta persistentTarjetaSeUsaEnRuta = em.find(TarjetaSeUsaEnRuta.class, tarjetaSeUsaEnRuta.getTarjetaSeUsaEnRutaPK());
            Tarjeta tarjetaOld = persistentTarjetaSeUsaEnRuta.getTarjeta();
            Tarjeta tarjetaNew = tarjetaSeUsaEnRuta.getTarjeta();
            Ruta rutaOld = persistentTarjetaSeUsaEnRuta.getRuta();
            Ruta rutaNew = tarjetaSeUsaEnRuta.getRuta();
            if (tarjetaNew != null) {
                tarjetaNew = em.getReference(tarjetaNew.getClass(), tarjetaNew.getPinTarjeta());
                tarjetaSeUsaEnRuta.setTarjeta(tarjetaNew);
            }
            if (rutaNew != null) {
                rutaNew = em.getReference(rutaNew.getClass(), rutaNew.getIdRuta());
                tarjetaSeUsaEnRuta.setRuta(rutaNew);
            }
            tarjetaSeUsaEnRuta = em.merge(tarjetaSeUsaEnRuta);
            if (tarjetaOld != null && !tarjetaOld.equals(tarjetaNew)) {
                tarjetaOld.getTarjetaSeUsaEnRutaList().remove(tarjetaSeUsaEnRuta);
                tarjetaOld = em.merge(tarjetaOld);
            }
            if (tarjetaNew != null && !tarjetaNew.equals(tarjetaOld)) {
                tarjetaNew.getTarjetaSeUsaEnRutaList().add(tarjetaSeUsaEnRuta);
                tarjetaNew = em.merge(tarjetaNew);
            }
            if (rutaOld != null && !rutaOld.equals(rutaNew)) {
                rutaOld.getTarjetaSeUsaEnRutaList().remove(tarjetaSeUsaEnRuta);
                rutaOld = em.merge(rutaOld);
            }
            if (rutaNew != null && !rutaNew.equals(rutaOld)) {
                rutaNew.getTarjetaSeUsaEnRutaList().add(tarjetaSeUsaEnRuta);
                rutaNew = em.merge(rutaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                TarjetaSeUsaEnRutaPK id = tarjetaSeUsaEnRuta.getTarjetaSeUsaEnRutaPK();
                if (findTarjetaSeUsaEnRuta(id) == null) {
                    throw new NonexistentEntityException("The tarjetaSeUsaEnRuta with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(TarjetaSeUsaEnRutaPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TarjetaSeUsaEnRuta tarjetaSeUsaEnRuta;
            try {
                tarjetaSeUsaEnRuta = em.getReference(TarjetaSeUsaEnRuta.class, id);
                tarjetaSeUsaEnRuta.getTarjetaSeUsaEnRutaPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tarjetaSeUsaEnRuta with id " + id + " no longer exists.", enfe);
            }
            Tarjeta tarjeta = tarjetaSeUsaEnRuta.getTarjeta();
            if (tarjeta != null) {
                tarjeta.getTarjetaSeUsaEnRutaList().remove(tarjetaSeUsaEnRuta);
                tarjeta = em.merge(tarjeta);
            }
            Ruta ruta = tarjetaSeUsaEnRuta.getRuta();
            if (ruta != null) {
                ruta.getTarjetaSeUsaEnRutaList().remove(tarjetaSeUsaEnRuta);
                ruta = em.merge(ruta);
            }
            em.remove(tarjetaSeUsaEnRuta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TarjetaSeUsaEnRuta> findTarjetaSeUsaEnRutaEntities() {
        return findTarjetaSeUsaEnRutaEntities(true, -1, -1);
    }

    public List<TarjetaSeUsaEnRuta> findTarjetaSeUsaEnRutaEntities(int maxResults, int firstResult) {
        return findTarjetaSeUsaEnRutaEntities(false, maxResults, firstResult);
    }

    private List<TarjetaSeUsaEnRuta> findTarjetaSeUsaEnRutaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TarjetaSeUsaEnRuta.class));
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

    public TarjetaSeUsaEnRuta findTarjetaSeUsaEnRuta(TarjetaSeUsaEnRutaPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TarjetaSeUsaEnRuta.class, id);
        } finally {
            em.close();
        }
    }

    public int getTarjetaSeUsaEnRutaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TarjetaSeUsaEnRuta> rt = cq.from(TarjetaSeUsaEnRuta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
