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
public class TarjetaJpaController implements Serializable {

    public TarjetaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Tarjeta tarjeta) throws PreexistingEntityException, Exception {
        if (tarjeta.getRecargaList() == null) {
            tarjeta.setRecargaList(new ArrayList<Recarga>());
        }
        if (tarjeta.getTarjetaSeUsaEnRutaList() == null) {
            tarjeta.setTarjetaSeUsaEnRutaList(new ArrayList<TarjetaSeUsaEnRuta>());
        }
        if (tarjeta.getTarjetaSeUsaEnEstacionList() == null) {
            tarjeta.setTarjetaSeUsaEnEstacionList(new ArrayList<TarjetaSeUsaEnEstacion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            VentaTarjeta ventaTarjeta = tarjeta.getVentaTarjeta();
            if (ventaTarjeta != null) {
                ventaTarjeta = em.getReference(ventaTarjeta.getClass(), ventaTarjeta.getPinTarjeta());
                tarjeta.setVentaTarjeta(ventaTarjeta);
            }
            List<Recarga> attachedRecargaList = new ArrayList<Recarga>();
            for (Recarga recargaListRecargaToAttach : tarjeta.getRecargaList()) {
                recargaListRecargaToAttach = em.getReference(recargaListRecargaToAttach.getClass(), recargaListRecargaToAttach.getConsecutivo());
                attachedRecargaList.add(recargaListRecargaToAttach);
            }
            tarjeta.setRecargaList(attachedRecargaList);
            List<TarjetaSeUsaEnRuta> attachedTarjetaSeUsaEnRutaList = new ArrayList<TarjetaSeUsaEnRuta>();
            for (TarjetaSeUsaEnRuta tarjetaSeUsaEnRutaListTarjetaSeUsaEnRutaToAttach : tarjeta.getTarjetaSeUsaEnRutaList()) {
                tarjetaSeUsaEnRutaListTarjetaSeUsaEnRutaToAttach = em.getReference(tarjetaSeUsaEnRutaListTarjetaSeUsaEnRutaToAttach.getClass(), tarjetaSeUsaEnRutaListTarjetaSeUsaEnRutaToAttach.getTarjetaSeUsaEnRutaPK());
                attachedTarjetaSeUsaEnRutaList.add(tarjetaSeUsaEnRutaListTarjetaSeUsaEnRutaToAttach);
            }
            tarjeta.setTarjetaSeUsaEnRutaList(attachedTarjetaSeUsaEnRutaList);
            List<TarjetaSeUsaEnEstacion> attachedTarjetaSeUsaEnEstacionList = new ArrayList<TarjetaSeUsaEnEstacion>();
            for (TarjetaSeUsaEnEstacion tarjetaSeUsaEnEstacionListTarjetaSeUsaEnEstacionToAttach : tarjeta.getTarjetaSeUsaEnEstacionList()) {
                tarjetaSeUsaEnEstacionListTarjetaSeUsaEnEstacionToAttach = em.getReference(tarjetaSeUsaEnEstacionListTarjetaSeUsaEnEstacionToAttach.getClass(), tarjetaSeUsaEnEstacionListTarjetaSeUsaEnEstacionToAttach.getTarjetaSeUsaEnEstacionPK());
                attachedTarjetaSeUsaEnEstacionList.add(tarjetaSeUsaEnEstacionListTarjetaSeUsaEnEstacionToAttach);
            }
            tarjeta.setTarjetaSeUsaEnEstacionList(attachedTarjetaSeUsaEnEstacionList);
            em.persist(tarjeta);
            if (ventaTarjeta != null) {
                Tarjeta oldTarjetaOfVentaTarjeta = ventaTarjeta.getTarjeta();
                if (oldTarjetaOfVentaTarjeta != null) {
                    oldTarjetaOfVentaTarjeta.setVentaTarjeta(null);
                    oldTarjetaOfVentaTarjeta = em.merge(oldTarjetaOfVentaTarjeta);
                }
                ventaTarjeta.setTarjeta(tarjeta);
                ventaTarjeta = em.merge(ventaTarjeta);
            }
            for (Recarga recargaListRecarga : tarjeta.getRecargaList()) {
                Tarjeta oldPinTarjetaOfRecargaListRecarga = recargaListRecarga.getPinTarjeta();
                recargaListRecarga.setPinTarjeta(tarjeta);
                recargaListRecarga = em.merge(recargaListRecarga);
                if (oldPinTarjetaOfRecargaListRecarga != null) {
                    oldPinTarjetaOfRecargaListRecarga.getRecargaList().remove(recargaListRecarga);
                    oldPinTarjetaOfRecargaListRecarga = em.merge(oldPinTarjetaOfRecargaListRecarga);
                }
            }
            for (TarjetaSeUsaEnRuta tarjetaSeUsaEnRutaListTarjetaSeUsaEnRuta : tarjeta.getTarjetaSeUsaEnRutaList()) {
                Tarjeta oldTarjetaOfTarjetaSeUsaEnRutaListTarjetaSeUsaEnRuta = tarjetaSeUsaEnRutaListTarjetaSeUsaEnRuta.getTarjeta();
                tarjetaSeUsaEnRutaListTarjetaSeUsaEnRuta.setTarjeta(tarjeta);
                tarjetaSeUsaEnRutaListTarjetaSeUsaEnRuta = em.merge(tarjetaSeUsaEnRutaListTarjetaSeUsaEnRuta);
                if (oldTarjetaOfTarjetaSeUsaEnRutaListTarjetaSeUsaEnRuta != null) {
                    oldTarjetaOfTarjetaSeUsaEnRutaListTarjetaSeUsaEnRuta.getTarjetaSeUsaEnRutaList().remove(tarjetaSeUsaEnRutaListTarjetaSeUsaEnRuta);
                    oldTarjetaOfTarjetaSeUsaEnRutaListTarjetaSeUsaEnRuta = em.merge(oldTarjetaOfTarjetaSeUsaEnRutaListTarjetaSeUsaEnRuta);
                }
            }
            for (TarjetaSeUsaEnEstacion tarjetaSeUsaEnEstacionListTarjetaSeUsaEnEstacion : tarjeta.getTarjetaSeUsaEnEstacionList()) {
                Tarjeta oldTarjetaOfTarjetaSeUsaEnEstacionListTarjetaSeUsaEnEstacion = tarjetaSeUsaEnEstacionListTarjetaSeUsaEnEstacion.getTarjeta();
                tarjetaSeUsaEnEstacionListTarjetaSeUsaEnEstacion.setTarjeta(tarjeta);
                tarjetaSeUsaEnEstacionListTarjetaSeUsaEnEstacion = em.merge(tarjetaSeUsaEnEstacionListTarjetaSeUsaEnEstacion);
                if (oldTarjetaOfTarjetaSeUsaEnEstacionListTarjetaSeUsaEnEstacion != null) {
                    oldTarjetaOfTarjetaSeUsaEnEstacionListTarjetaSeUsaEnEstacion.getTarjetaSeUsaEnEstacionList().remove(tarjetaSeUsaEnEstacionListTarjetaSeUsaEnEstacion);
                    oldTarjetaOfTarjetaSeUsaEnEstacionListTarjetaSeUsaEnEstacion = em.merge(oldTarjetaOfTarjetaSeUsaEnEstacionListTarjetaSeUsaEnEstacion);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTarjeta(tarjeta.getPinTarjeta()) != null) {
                throw new PreexistingEntityException("Tarjeta " + tarjeta + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Tarjeta tarjeta) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tarjeta persistentTarjeta = em.find(Tarjeta.class, tarjeta.getPinTarjeta());
            VentaTarjeta ventaTarjetaOld = persistentTarjeta.getVentaTarjeta();
            VentaTarjeta ventaTarjetaNew = tarjeta.getVentaTarjeta();
            List<Recarga> recargaListOld = persistentTarjeta.getRecargaList();
            List<Recarga> recargaListNew = tarjeta.getRecargaList();
            List<TarjetaSeUsaEnRuta> tarjetaSeUsaEnRutaListOld = persistentTarjeta.getTarjetaSeUsaEnRutaList();
            List<TarjetaSeUsaEnRuta> tarjetaSeUsaEnRutaListNew = tarjeta.getTarjetaSeUsaEnRutaList();
            List<TarjetaSeUsaEnEstacion> tarjetaSeUsaEnEstacionListOld = persistentTarjeta.getTarjetaSeUsaEnEstacionList();
            List<TarjetaSeUsaEnEstacion> tarjetaSeUsaEnEstacionListNew = tarjeta.getTarjetaSeUsaEnEstacionList();
            List<String> illegalOrphanMessages = null;
            if (ventaTarjetaOld != null && !ventaTarjetaOld.equals(ventaTarjetaNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain VentaTarjeta " + ventaTarjetaOld + " since its tarjeta field is not nullable.");
            }
            for (TarjetaSeUsaEnRuta tarjetaSeUsaEnRutaListOldTarjetaSeUsaEnRuta : tarjetaSeUsaEnRutaListOld) {
                if (!tarjetaSeUsaEnRutaListNew.contains(tarjetaSeUsaEnRutaListOldTarjetaSeUsaEnRuta)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TarjetaSeUsaEnRuta " + tarjetaSeUsaEnRutaListOldTarjetaSeUsaEnRuta + " since its tarjeta field is not nullable.");
                }
            }
            for (TarjetaSeUsaEnEstacion tarjetaSeUsaEnEstacionListOldTarjetaSeUsaEnEstacion : tarjetaSeUsaEnEstacionListOld) {
                if (!tarjetaSeUsaEnEstacionListNew.contains(tarjetaSeUsaEnEstacionListOldTarjetaSeUsaEnEstacion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TarjetaSeUsaEnEstacion " + tarjetaSeUsaEnEstacionListOldTarjetaSeUsaEnEstacion + " since its tarjeta field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (ventaTarjetaNew != null) {
                ventaTarjetaNew = em.getReference(ventaTarjetaNew.getClass(), ventaTarjetaNew.getPinTarjeta());
                tarjeta.setVentaTarjeta(ventaTarjetaNew);
            }
            List<Recarga> attachedRecargaListNew = new ArrayList<Recarga>();
            for (Recarga recargaListNewRecargaToAttach : recargaListNew) {
                recargaListNewRecargaToAttach = em.getReference(recargaListNewRecargaToAttach.getClass(), recargaListNewRecargaToAttach.getConsecutivo());
                attachedRecargaListNew.add(recargaListNewRecargaToAttach);
            }
            recargaListNew = attachedRecargaListNew;
            tarjeta.setRecargaList(recargaListNew);
            List<TarjetaSeUsaEnRuta> attachedTarjetaSeUsaEnRutaListNew = new ArrayList<TarjetaSeUsaEnRuta>();
            for (TarjetaSeUsaEnRuta tarjetaSeUsaEnRutaListNewTarjetaSeUsaEnRutaToAttach : tarjetaSeUsaEnRutaListNew) {
                tarjetaSeUsaEnRutaListNewTarjetaSeUsaEnRutaToAttach = em.getReference(tarjetaSeUsaEnRutaListNewTarjetaSeUsaEnRutaToAttach.getClass(), tarjetaSeUsaEnRutaListNewTarjetaSeUsaEnRutaToAttach.getTarjetaSeUsaEnRutaPK());
                attachedTarjetaSeUsaEnRutaListNew.add(tarjetaSeUsaEnRutaListNewTarjetaSeUsaEnRutaToAttach);
            }
            tarjetaSeUsaEnRutaListNew = attachedTarjetaSeUsaEnRutaListNew;
            tarjeta.setTarjetaSeUsaEnRutaList(tarjetaSeUsaEnRutaListNew);
            List<TarjetaSeUsaEnEstacion> attachedTarjetaSeUsaEnEstacionListNew = new ArrayList<TarjetaSeUsaEnEstacion>();
            for (TarjetaSeUsaEnEstacion tarjetaSeUsaEnEstacionListNewTarjetaSeUsaEnEstacionToAttach : tarjetaSeUsaEnEstacionListNew) {
                tarjetaSeUsaEnEstacionListNewTarjetaSeUsaEnEstacionToAttach = em.getReference(tarjetaSeUsaEnEstacionListNewTarjetaSeUsaEnEstacionToAttach.getClass(), tarjetaSeUsaEnEstacionListNewTarjetaSeUsaEnEstacionToAttach.getTarjetaSeUsaEnEstacionPK());
                attachedTarjetaSeUsaEnEstacionListNew.add(tarjetaSeUsaEnEstacionListNewTarjetaSeUsaEnEstacionToAttach);
            }
            tarjetaSeUsaEnEstacionListNew = attachedTarjetaSeUsaEnEstacionListNew;
            tarjeta.setTarjetaSeUsaEnEstacionList(tarjetaSeUsaEnEstacionListNew);
            tarjeta = em.merge(tarjeta);
            if (ventaTarjetaNew != null && !ventaTarjetaNew.equals(ventaTarjetaOld)) {
                Tarjeta oldTarjetaOfVentaTarjeta = ventaTarjetaNew.getTarjeta();
                if (oldTarjetaOfVentaTarjeta != null) {
                    oldTarjetaOfVentaTarjeta.setVentaTarjeta(null);
                    oldTarjetaOfVentaTarjeta = em.merge(oldTarjetaOfVentaTarjeta);
                }
                ventaTarjetaNew.setTarjeta(tarjeta);
                ventaTarjetaNew = em.merge(ventaTarjetaNew);
            }
            for (Recarga recargaListOldRecarga : recargaListOld) {
                if (!recargaListNew.contains(recargaListOldRecarga)) {
                    recargaListOldRecarga.setPinTarjeta(null);
                    recargaListOldRecarga = em.merge(recargaListOldRecarga);
                }
            }
            for (Recarga recargaListNewRecarga : recargaListNew) {
                if (!recargaListOld.contains(recargaListNewRecarga)) {
                    Tarjeta oldPinTarjetaOfRecargaListNewRecarga = recargaListNewRecarga.getPinTarjeta();
                    recargaListNewRecarga.setPinTarjeta(tarjeta);
                    recargaListNewRecarga = em.merge(recargaListNewRecarga);
                    if (oldPinTarjetaOfRecargaListNewRecarga != null && !oldPinTarjetaOfRecargaListNewRecarga.equals(tarjeta)) {
                        oldPinTarjetaOfRecargaListNewRecarga.getRecargaList().remove(recargaListNewRecarga);
                        oldPinTarjetaOfRecargaListNewRecarga = em.merge(oldPinTarjetaOfRecargaListNewRecarga);
                    }
                }
            }
            for (TarjetaSeUsaEnRuta tarjetaSeUsaEnRutaListNewTarjetaSeUsaEnRuta : tarjetaSeUsaEnRutaListNew) {
                if (!tarjetaSeUsaEnRutaListOld.contains(tarjetaSeUsaEnRutaListNewTarjetaSeUsaEnRuta)) {
                    Tarjeta oldTarjetaOfTarjetaSeUsaEnRutaListNewTarjetaSeUsaEnRuta = tarjetaSeUsaEnRutaListNewTarjetaSeUsaEnRuta.getTarjeta();
                    tarjetaSeUsaEnRutaListNewTarjetaSeUsaEnRuta.setTarjeta(tarjeta);
                    tarjetaSeUsaEnRutaListNewTarjetaSeUsaEnRuta = em.merge(tarjetaSeUsaEnRutaListNewTarjetaSeUsaEnRuta);
                    if (oldTarjetaOfTarjetaSeUsaEnRutaListNewTarjetaSeUsaEnRuta != null && !oldTarjetaOfTarjetaSeUsaEnRutaListNewTarjetaSeUsaEnRuta.equals(tarjeta)) {
                        oldTarjetaOfTarjetaSeUsaEnRutaListNewTarjetaSeUsaEnRuta.getTarjetaSeUsaEnRutaList().remove(tarjetaSeUsaEnRutaListNewTarjetaSeUsaEnRuta);
                        oldTarjetaOfTarjetaSeUsaEnRutaListNewTarjetaSeUsaEnRuta = em.merge(oldTarjetaOfTarjetaSeUsaEnRutaListNewTarjetaSeUsaEnRuta);
                    }
                }
            }
            for (TarjetaSeUsaEnEstacion tarjetaSeUsaEnEstacionListNewTarjetaSeUsaEnEstacion : tarjetaSeUsaEnEstacionListNew) {
                if (!tarjetaSeUsaEnEstacionListOld.contains(tarjetaSeUsaEnEstacionListNewTarjetaSeUsaEnEstacion)) {
                    Tarjeta oldTarjetaOfTarjetaSeUsaEnEstacionListNewTarjetaSeUsaEnEstacion = tarjetaSeUsaEnEstacionListNewTarjetaSeUsaEnEstacion.getTarjeta();
                    tarjetaSeUsaEnEstacionListNewTarjetaSeUsaEnEstacion.setTarjeta(tarjeta);
                    tarjetaSeUsaEnEstacionListNewTarjetaSeUsaEnEstacion = em.merge(tarjetaSeUsaEnEstacionListNewTarjetaSeUsaEnEstacion);
                    if (oldTarjetaOfTarjetaSeUsaEnEstacionListNewTarjetaSeUsaEnEstacion != null && !oldTarjetaOfTarjetaSeUsaEnEstacionListNewTarjetaSeUsaEnEstacion.equals(tarjeta)) {
                        oldTarjetaOfTarjetaSeUsaEnEstacionListNewTarjetaSeUsaEnEstacion.getTarjetaSeUsaEnEstacionList().remove(tarjetaSeUsaEnEstacionListNewTarjetaSeUsaEnEstacion);
                        oldTarjetaOfTarjetaSeUsaEnEstacionListNewTarjetaSeUsaEnEstacion = em.merge(oldTarjetaOfTarjetaSeUsaEnEstacionListNewTarjetaSeUsaEnEstacion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tarjeta.getPinTarjeta();
                if (findTarjeta(id) == null) {
                    throw new NonexistentEntityException("The tarjeta with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tarjeta tarjeta;
            try {
                tarjeta = em.getReference(Tarjeta.class, id);
                tarjeta.getPinTarjeta();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tarjeta with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            VentaTarjeta ventaTarjetaOrphanCheck = tarjeta.getVentaTarjeta();
            if (ventaTarjetaOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Tarjeta (" + tarjeta + ") cannot be destroyed since the VentaTarjeta " + ventaTarjetaOrphanCheck + " in its ventaTarjeta field has a non-nullable tarjeta field.");
            }
            List<TarjetaSeUsaEnRuta> tarjetaSeUsaEnRutaListOrphanCheck = tarjeta.getTarjetaSeUsaEnRutaList();
            for (TarjetaSeUsaEnRuta tarjetaSeUsaEnRutaListOrphanCheckTarjetaSeUsaEnRuta : tarjetaSeUsaEnRutaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Tarjeta (" + tarjeta + ") cannot be destroyed since the TarjetaSeUsaEnRuta " + tarjetaSeUsaEnRutaListOrphanCheckTarjetaSeUsaEnRuta + " in its tarjetaSeUsaEnRutaList field has a non-nullable tarjeta field.");
            }
            List<TarjetaSeUsaEnEstacion> tarjetaSeUsaEnEstacionListOrphanCheck = tarjeta.getTarjetaSeUsaEnEstacionList();
            for (TarjetaSeUsaEnEstacion tarjetaSeUsaEnEstacionListOrphanCheckTarjetaSeUsaEnEstacion : tarjetaSeUsaEnEstacionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Tarjeta (" + tarjeta + ") cannot be destroyed since the TarjetaSeUsaEnEstacion " + tarjetaSeUsaEnEstacionListOrphanCheckTarjetaSeUsaEnEstacion + " in its tarjetaSeUsaEnEstacionList field has a non-nullable tarjeta field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Recarga> recargaList = tarjeta.getRecargaList();
            for (Recarga recargaListRecarga : recargaList) {
                recargaListRecarga.setPinTarjeta(null);
                recargaListRecarga = em.merge(recargaListRecarga);
            }
            em.remove(tarjeta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Tarjeta> findTarjetaEntities() {
        return findTarjetaEntities(true, -1, -1);
    }

    public List<Tarjeta> findTarjetaEntities(int maxResults, int firstResult) {
        return findTarjetaEntities(false, maxResults, firstResult);
    }

    private List<Tarjeta> findTarjetaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tarjeta.class));
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

    public Tarjeta findTarjeta(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tarjeta.class, id);
        } finally {
            em.close();
        }
    }

    public int getTarjetaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tarjeta> rt = cq.from(Tarjeta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
