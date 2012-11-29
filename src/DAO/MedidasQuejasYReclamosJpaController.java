/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DAO.exceptions.NonexistentEntityException;
import DAO.exceptions.PreexistingEntityException;
import Entidades.MedidasQuejasYReclamos;
import Entidades.MedidasQuejasYReclamosPK;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entidades.QuejasYReclamos;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author jdavidva
 */
public class MedidasQuejasYReclamosJpaController implements Serializable {

    public MedidasQuejasYReclamosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(MedidasQuejasYReclamos medidasQuejasYReclamos) throws PreexistingEntityException, Exception {
        if (medidasQuejasYReclamos.getMedidasQuejasYReclamosPK() == null) {
            medidasQuejasYReclamos.setMedidasQuejasYReclamosPK(new MedidasQuejasYReclamosPK());
        }
        medidasQuejasYReclamos.getMedidasQuejasYReclamosPK().setNoTicket(medidasQuejasYReclamos.getQuejasYReclamos().getNoTicket());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            QuejasYReclamos quejasYReclamos = medidasQuejasYReclamos.getQuejasYReclamos();
            if (quejasYReclamos != null) {
                quejasYReclamos = em.getReference(quejasYReclamos.getClass(), quejasYReclamos.getNoTicket());
                medidasQuejasYReclamos.setQuejasYReclamos(quejasYReclamos);
            }
            em.persist(medidasQuejasYReclamos);
            if (quejasYReclamos != null) {
                quejasYReclamos.getMedidasQuejasYReclamosList().add(medidasQuejasYReclamos);
                quejasYReclamos = em.merge(quejasYReclamos);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findMedidasQuejasYReclamos(medidasQuejasYReclamos.getMedidasQuejasYReclamosPK()) != null) {
                throw new PreexistingEntityException("MedidasQuejasYReclamos " + medidasQuejasYReclamos + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(MedidasQuejasYReclamos medidasQuejasYReclamos) throws NonexistentEntityException, Exception {
        medidasQuejasYReclamos.getMedidasQuejasYReclamosPK().setNoTicket(medidasQuejasYReclamos.getQuejasYReclamos().getNoTicket());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            MedidasQuejasYReclamos persistentMedidasQuejasYReclamos = em.find(MedidasQuejasYReclamos.class, medidasQuejasYReclamos.getMedidasQuejasYReclamosPK());
            QuejasYReclamos quejasYReclamosOld = persistentMedidasQuejasYReclamos.getQuejasYReclamos();
            QuejasYReclamos quejasYReclamosNew = medidasQuejasYReclamos.getQuejasYReclamos();
            if (quejasYReclamosNew != null) {
                quejasYReclamosNew = em.getReference(quejasYReclamosNew.getClass(), quejasYReclamosNew.getNoTicket());
                medidasQuejasYReclamos.setQuejasYReclamos(quejasYReclamosNew);
            }
            medidasQuejasYReclamos = em.merge(medidasQuejasYReclamos);
            if (quejasYReclamosOld != null && !quejasYReclamosOld.equals(quejasYReclamosNew)) {
                quejasYReclamosOld.getMedidasQuejasYReclamosList().remove(medidasQuejasYReclamos);
                quejasYReclamosOld = em.merge(quejasYReclamosOld);
            }
            if (quejasYReclamosNew != null && !quejasYReclamosNew.equals(quejasYReclamosOld)) {
                quejasYReclamosNew.getMedidasQuejasYReclamosList().add(medidasQuejasYReclamos);
                quejasYReclamosNew = em.merge(quejasYReclamosNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                MedidasQuejasYReclamosPK id = medidasQuejasYReclamos.getMedidasQuejasYReclamosPK();
                if (findMedidasQuejasYReclamos(id) == null) {
                    throw new NonexistentEntityException("The medidasQuejasYReclamos with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(MedidasQuejasYReclamosPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            MedidasQuejasYReclamos medidasQuejasYReclamos;
            try {
                medidasQuejasYReclamos = em.getReference(MedidasQuejasYReclamos.class, id);
                medidasQuejasYReclamos.getMedidasQuejasYReclamosPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The medidasQuejasYReclamos with id " + id + " no longer exists.", enfe);
            }
            QuejasYReclamos quejasYReclamos = medidasQuejasYReclamos.getQuejasYReclamos();
            if (quejasYReclamos != null) {
                quejasYReclamos.getMedidasQuejasYReclamosList().remove(medidasQuejasYReclamos);
                quejasYReclamos = em.merge(quejasYReclamos);
            }
            em.remove(medidasQuejasYReclamos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<MedidasQuejasYReclamos> findMedidasQuejasYReclamosEntities() {
        return findMedidasQuejasYReclamosEntities(true, -1, -1);
    }

    public List<MedidasQuejasYReclamos> findMedidasQuejasYReclamosEntities(int maxResults, int firstResult) {
        return findMedidasQuejasYReclamosEntities(false, maxResults, firstResult);
    }

    private List<MedidasQuejasYReclamos> findMedidasQuejasYReclamosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(MedidasQuejasYReclamos.class));
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

    public MedidasQuejasYReclamos findMedidasQuejasYReclamos(MedidasQuejasYReclamosPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(MedidasQuejasYReclamos.class, id);
        } finally {
            em.close();
        }
    }

    public int getMedidasQuejasYReclamosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<MedidasQuejasYReclamos> rt = cq.from(MedidasQuejasYReclamos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
