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
public class VentaTarjetaJpaController implements Serializable {

    public VentaTarjetaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(VentaTarjeta ventaTarjeta) throws IllegalOrphanException, PreexistingEntityException, Exception {
        List<String> illegalOrphanMessages = null;
        Tarjeta tarjetaOrphanCheck = ventaTarjeta.getTarjeta();
        if (tarjetaOrphanCheck != null) {
            VentaTarjeta oldVentaTarjetaOfTarjeta = tarjetaOrphanCheck.getVentaTarjeta();
            if (oldVentaTarjetaOfTarjeta != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Tarjeta " + tarjetaOrphanCheck + " already has an item of type VentaTarjeta whose tarjeta column cannot be null. Please make another selection for the tarjeta field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tarjeta tarjeta = ventaTarjeta.getTarjeta();
            if (tarjeta != null) {
                tarjeta = em.getReference(tarjeta.getClass(), tarjeta.getPinTarjeta());
                ventaTarjeta.setTarjeta(tarjeta);
            }
            Pasajero idPasajero = ventaTarjeta.getIdPasajero();
            if (idPasajero != null) {
                idPasajero = em.getReference(idPasajero.getClass(), idPasajero.getIdPasajero());
                ventaTarjeta.setIdPasajero(idPasajero);
            }
            Estacion idEstacion = ventaTarjeta.getIdEstacion();
            if (idEstacion != null) {
                idEstacion = em.getReference(idEstacion.getClass(), idEstacion.getIdEstacion());
                ventaTarjeta.setIdEstacion(idEstacion);
            }
            Empleado idEmpleado = ventaTarjeta.getIdEmpleado();
            if (idEmpleado != null) {
                idEmpleado = em.getReference(idEmpleado.getClass(), idEmpleado.getIdEmpleado());
                ventaTarjeta.setIdEmpleado(idEmpleado);
            }
            em.persist(ventaTarjeta);
            if (tarjeta != null) {
                tarjeta.setVentaTarjeta(ventaTarjeta);
                tarjeta = em.merge(tarjeta);
            }
            if (idPasajero != null) {
                idPasajero.getVentaTarjetaList().add(ventaTarjeta);
                idPasajero = em.merge(idPasajero);
            }
            if (idEstacion != null) {
                idEstacion.getVentaTarjetaList().add(ventaTarjeta);
                idEstacion = em.merge(idEstacion);
            }
            if (idEmpleado != null) {
                idEmpleado.getVentaTarjetaList().add(ventaTarjeta);
                idEmpleado = em.merge(idEmpleado);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findVentaTarjeta(ventaTarjeta.getPinTarjeta()) != null) {
                throw new PreexistingEntityException("VentaTarjeta " + ventaTarjeta + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(VentaTarjeta ventaTarjeta) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            VentaTarjeta persistentVentaTarjeta = em.find(VentaTarjeta.class, ventaTarjeta.getPinTarjeta());
            Tarjeta tarjetaOld = persistentVentaTarjeta.getTarjeta();
            Tarjeta tarjetaNew = ventaTarjeta.getTarjeta();
            Pasajero idPasajeroOld = persistentVentaTarjeta.getIdPasajero();
            Pasajero idPasajeroNew = ventaTarjeta.getIdPasajero();
            Estacion idEstacionOld = persistentVentaTarjeta.getIdEstacion();
            Estacion idEstacionNew = ventaTarjeta.getIdEstacion();
            Empleado idEmpleadoOld = persistentVentaTarjeta.getIdEmpleado();
            Empleado idEmpleadoNew = ventaTarjeta.getIdEmpleado();
            List<String> illegalOrphanMessages = null;
            if (tarjetaNew != null && !tarjetaNew.equals(tarjetaOld)) {
                VentaTarjeta oldVentaTarjetaOfTarjeta = tarjetaNew.getVentaTarjeta();
                if (oldVentaTarjetaOfTarjeta != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Tarjeta " + tarjetaNew + " already has an item of type VentaTarjeta whose tarjeta column cannot be null. Please make another selection for the tarjeta field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (tarjetaNew != null) {
                tarjetaNew = em.getReference(tarjetaNew.getClass(), tarjetaNew.getPinTarjeta());
                ventaTarjeta.setTarjeta(tarjetaNew);
            }
            if (idPasajeroNew != null) {
                idPasajeroNew = em.getReference(idPasajeroNew.getClass(), idPasajeroNew.getIdPasajero());
                ventaTarjeta.setIdPasajero(idPasajeroNew);
            }
            if (idEstacionNew != null) {
                idEstacionNew = em.getReference(idEstacionNew.getClass(), idEstacionNew.getIdEstacion());
                ventaTarjeta.setIdEstacion(idEstacionNew);
            }
            if (idEmpleadoNew != null) {
                idEmpleadoNew = em.getReference(idEmpleadoNew.getClass(), idEmpleadoNew.getIdEmpleado());
                ventaTarjeta.setIdEmpleado(idEmpleadoNew);
            }
            ventaTarjeta = em.merge(ventaTarjeta);
            if (tarjetaOld != null && !tarjetaOld.equals(tarjetaNew)) {
                tarjetaOld.setVentaTarjeta(null);
                tarjetaOld = em.merge(tarjetaOld);
            }
            if (tarjetaNew != null && !tarjetaNew.equals(tarjetaOld)) {
                tarjetaNew.setVentaTarjeta(ventaTarjeta);
                tarjetaNew = em.merge(tarjetaNew);
            }
            if (idPasajeroOld != null && !idPasajeroOld.equals(idPasajeroNew)) {
                idPasajeroOld.getVentaTarjetaList().remove(ventaTarjeta);
                idPasajeroOld = em.merge(idPasajeroOld);
            }
            if (idPasajeroNew != null && !idPasajeroNew.equals(idPasajeroOld)) {
                idPasajeroNew.getVentaTarjetaList().add(ventaTarjeta);
                idPasajeroNew = em.merge(idPasajeroNew);
            }
            if (idEstacionOld != null && !idEstacionOld.equals(idEstacionNew)) {
                idEstacionOld.getVentaTarjetaList().remove(ventaTarjeta);
                idEstacionOld = em.merge(idEstacionOld);
            }
            if (idEstacionNew != null && !idEstacionNew.equals(idEstacionOld)) {
                idEstacionNew.getVentaTarjetaList().add(ventaTarjeta);
                idEstacionNew = em.merge(idEstacionNew);
            }
            if (idEmpleadoOld != null && !idEmpleadoOld.equals(idEmpleadoNew)) {
                idEmpleadoOld.getVentaTarjetaList().remove(ventaTarjeta);
                idEmpleadoOld = em.merge(idEmpleadoOld);
            }
            if (idEmpleadoNew != null && !idEmpleadoNew.equals(idEmpleadoOld)) {
                idEmpleadoNew.getVentaTarjetaList().add(ventaTarjeta);
                idEmpleadoNew = em.merge(idEmpleadoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = ventaTarjeta.getPinTarjeta();
                if (findVentaTarjeta(id) == null) {
                    throw new NonexistentEntityException("The ventaTarjeta with id " + id + " no longer exists.");
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
            VentaTarjeta ventaTarjeta;
            try {
                ventaTarjeta = em.getReference(VentaTarjeta.class, id);
                ventaTarjeta.getPinTarjeta();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ventaTarjeta with id " + id + " no longer exists.", enfe);
            }
            Tarjeta tarjeta = ventaTarjeta.getTarjeta();
            if (tarjeta != null) {
                tarjeta.setVentaTarjeta(null);
                tarjeta = em.merge(tarjeta);
            }
            Pasajero idPasajero = ventaTarjeta.getIdPasajero();
            if (idPasajero != null) {
                idPasajero.getVentaTarjetaList().remove(ventaTarjeta);
                idPasajero = em.merge(idPasajero);
            }
            Estacion idEstacion = ventaTarjeta.getIdEstacion();
            if (idEstacion != null) {
                idEstacion.getVentaTarjetaList().remove(ventaTarjeta);
                idEstacion = em.merge(idEstacion);
            }
            Empleado idEmpleado = ventaTarjeta.getIdEmpleado();
            if (idEmpleado != null) {
                idEmpleado.getVentaTarjetaList().remove(ventaTarjeta);
                idEmpleado = em.merge(idEmpleado);
            }
            em.remove(ventaTarjeta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<VentaTarjeta> findVentaTarjetaEntities() {
        return findVentaTarjetaEntities(true, -1, -1);
    }

    public List<VentaTarjeta> findVentaTarjetaEntities(int maxResults, int firstResult) {
        return findVentaTarjetaEntities(false, maxResults, firstResult);
    }

    private List<VentaTarjeta> findVentaTarjetaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(VentaTarjeta.class));
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

    public VentaTarjeta findVentaTarjeta(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(VentaTarjeta.class, id);
        } finally {
            em.close();
        }
    }

    public int getVentaTarjetaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<VentaTarjeta> rt = cq.from(VentaTarjeta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
