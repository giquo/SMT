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
public class QuejasYReclamosJpaController implements Serializable {

    public QuejasYReclamosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(QuejasYReclamos quejasYReclamos) throws PreexistingEntityException, Exception {
        if (quejasYReclamos.getMedidasQuejasYReclamosList() == null) {
            quejasYReclamos.setMedidasQuejasYReclamosList(new ArrayList<MedidasQuejasYReclamos>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pasajero idPasajero = quejasYReclamos.getIdPasajero();
            if (idPasajero != null) {
                idPasajero = em.getReference(idPasajero.getClass(), idPasajero.getIdPasajero());
                quejasYReclamos.setIdPasajero(idPasajero);
            }
            Estacion idEstacion = quejasYReclamos.getIdEstacion();
            if (idEstacion != null) {
                idEstacion = em.getReference(idEstacion.getClass(), idEstacion.getIdEstacion());
                quejasYReclamos.setIdEstacion(idEstacion);
            }
            Empleado idEmpleado = quejasYReclamos.getIdEmpleado();
            if (idEmpleado != null) {
                idEmpleado = em.getReference(idEmpleado.getClass(), idEmpleado.getIdEmpleado());
                quejasYReclamos.setIdEmpleado(idEmpleado);
            }
            List<MedidasQuejasYReclamos> attachedMedidasQuejasYReclamosList = new ArrayList<MedidasQuejasYReclamos>();
            for (MedidasQuejasYReclamos medidasQuejasYReclamosListMedidasQuejasYReclamosToAttach : quejasYReclamos.getMedidasQuejasYReclamosList()) {
                medidasQuejasYReclamosListMedidasQuejasYReclamosToAttach = em.getReference(medidasQuejasYReclamosListMedidasQuejasYReclamosToAttach.getClass(), medidasQuejasYReclamosListMedidasQuejasYReclamosToAttach.getMedidasQuejasYReclamosPK());
                attachedMedidasQuejasYReclamosList.add(medidasQuejasYReclamosListMedidasQuejasYReclamosToAttach);
            }
            quejasYReclamos.setMedidasQuejasYReclamosList(attachedMedidasQuejasYReclamosList);
            em.persist(quejasYReclamos);
            if (idPasajero != null) {
                idPasajero.getQuejasYReclamosList().add(quejasYReclamos);
                idPasajero = em.merge(idPasajero);
            }
            if (idEstacion != null) {
                idEstacion.getQuejasYReclamosList().add(quejasYReclamos);
                idEstacion = em.merge(idEstacion);
            }
            if (idEmpleado != null) {
                idEmpleado.getQuejasYReclamosList().add(quejasYReclamos);
                idEmpleado = em.merge(idEmpleado);
            }
            for (MedidasQuejasYReclamos medidasQuejasYReclamosListMedidasQuejasYReclamos : quejasYReclamos.getMedidasQuejasYReclamosList()) {
                QuejasYReclamos oldQuejasYReclamosOfMedidasQuejasYReclamosListMedidasQuejasYReclamos = medidasQuejasYReclamosListMedidasQuejasYReclamos.getQuejasYReclamos();
                medidasQuejasYReclamosListMedidasQuejasYReclamos.setQuejasYReclamos(quejasYReclamos);
                medidasQuejasYReclamosListMedidasQuejasYReclamos = em.merge(medidasQuejasYReclamosListMedidasQuejasYReclamos);
                if (oldQuejasYReclamosOfMedidasQuejasYReclamosListMedidasQuejasYReclamos != null) {
                    oldQuejasYReclamosOfMedidasQuejasYReclamosListMedidasQuejasYReclamos.getMedidasQuejasYReclamosList().remove(medidasQuejasYReclamosListMedidasQuejasYReclamos);
                    oldQuejasYReclamosOfMedidasQuejasYReclamosListMedidasQuejasYReclamos = em.merge(oldQuejasYReclamosOfMedidasQuejasYReclamosListMedidasQuejasYReclamos);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findQuejasYReclamos(quejasYReclamos.getNoTicket()) != null) {
                throw new PreexistingEntityException("QuejasYReclamos " + quejasYReclamos + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(QuejasYReclamos quejasYReclamos) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            QuejasYReclamos persistentQuejasYReclamos = em.find(QuejasYReclamos.class, quejasYReclamos.getNoTicket());
            Pasajero idPasajeroOld = persistentQuejasYReclamos.getIdPasajero();
            Pasajero idPasajeroNew = quejasYReclamos.getIdPasajero();
            Estacion idEstacionOld = persistentQuejasYReclamos.getIdEstacion();
            Estacion idEstacionNew = quejasYReclamos.getIdEstacion();
            Empleado idEmpleadoOld = persistentQuejasYReclamos.getIdEmpleado();
            Empleado idEmpleadoNew = quejasYReclamos.getIdEmpleado();
            List<MedidasQuejasYReclamos> medidasQuejasYReclamosListOld = persistentQuejasYReclamos.getMedidasQuejasYReclamosList();
            List<MedidasQuejasYReclamos> medidasQuejasYReclamosListNew = quejasYReclamos.getMedidasQuejasYReclamosList();
            List<String> illegalOrphanMessages = null;
            for (MedidasQuejasYReclamos medidasQuejasYReclamosListOldMedidasQuejasYReclamos : medidasQuejasYReclamosListOld) {
                if (!medidasQuejasYReclamosListNew.contains(medidasQuejasYReclamosListOldMedidasQuejasYReclamos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain MedidasQuejasYReclamos " + medidasQuejasYReclamosListOldMedidasQuejasYReclamos + " since its quejasYReclamos field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idPasajeroNew != null) {
                idPasajeroNew = em.getReference(idPasajeroNew.getClass(), idPasajeroNew.getIdPasajero());
                quejasYReclamos.setIdPasajero(idPasajeroNew);
            }
            if (idEstacionNew != null) {
                idEstacionNew = em.getReference(idEstacionNew.getClass(), idEstacionNew.getIdEstacion());
                quejasYReclamos.setIdEstacion(idEstacionNew);
            }
            if (idEmpleadoNew != null) {
                idEmpleadoNew = em.getReference(idEmpleadoNew.getClass(), idEmpleadoNew.getIdEmpleado());
                quejasYReclamos.setIdEmpleado(idEmpleadoNew);
            }
            List<MedidasQuejasYReclamos> attachedMedidasQuejasYReclamosListNew = new ArrayList<MedidasQuejasYReclamos>();
            for (MedidasQuejasYReclamos medidasQuejasYReclamosListNewMedidasQuejasYReclamosToAttach : medidasQuejasYReclamosListNew) {
                medidasQuejasYReclamosListNewMedidasQuejasYReclamosToAttach = em.getReference(medidasQuejasYReclamosListNewMedidasQuejasYReclamosToAttach.getClass(), medidasQuejasYReclamosListNewMedidasQuejasYReclamosToAttach.getMedidasQuejasYReclamosPK());
                attachedMedidasQuejasYReclamosListNew.add(medidasQuejasYReclamosListNewMedidasQuejasYReclamosToAttach);
            }
            medidasQuejasYReclamosListNew = attachedMedidasQuejasYReclamosListNew;
            quejasYReclamos.setMedidasQuejasYReclamosList(medidasQuejasYReclamosListNew);
            quejasYReclamos = em.merge(quejasYReclamos);
            if (idPasajeroOld != null && !idPasajeroOld.equals(idPasajeroNew)) {
                idPasajeroOld.getQuejasYReclamosList().remove(quejasYReclamos);
                idPasajeroOld = em.merge(idPasajeroOld);
            }
            if (idPasajeroNew != null && !idPasajeroNew.equals(idPasajeroOld)) {
                idPasajeroNew.getQuejasYReclamosList().add(quejasYReclamos);
                idPasajeroNew = em.merge(idPasajeroNew);
            }
            if (idEstacionOld != null && !idEstacionOld.equals(idEstacionNew)) {
                idEstacionOld.getQuejasYReclamosList().remove(quejasYReclamos);
                idEstacionOld = em.merge(idEstacionOld);
            }
            if (idEstacionNew != null && !idEstacionNew.equals(idEstacionOld)) {
                idEstacionNew.getQuejasYReclamosList().add(quejasYReclamos);
                idEstacionNew = em.merge(idEstacionNew);
            }
            if (idEmpleadoOld != null && !idEmpleadoOld.equals(idEmpleadoNew)) {
                idEmpleadoOld.getQuejasYReclamosList().remove(quejasYReclamos);
                idEmpleadoOld = em.merge(idEmpleadoOld);
            }
            if (idEmpleadoNew != null && !idEmpleadoNew.equals(idEmpleadoOld)) {
                idEmpleadoNew.getQuejasYReclamosList().add(quejasYReclamos);
                idEmpleadoNew = em.merge(idEmpleadoNew);
            }
            for (MedidasQuejasYReclamos medidasQuejasYReclamosListNewMedidasQuejasYReclamos : medidasQuejasYReclamosListNew) {
                if (!medidasQuejasYReclamosListOld.contains(medidasQuejasYReclamosListNewMedidasQuejasYReclamos)) {
                    QuejasYReclamos oldQuejasYReclamosOfMedidasQuejasYReclamosListNewMedidasQuejasYReclamos = medidasQuejasYReclamosListNewMedidasQuejasYReclamos.getQuejasYReclamos();
                    medidasQuejasYReclamosListNewMedidasQuejasYReclamos.setQuejasYReclamos(quejasYReclamos);
                    medidasQuejasYReclamosListNewMedidasQuejasYReclamos = em.merge(medidasQuejasYReclamosListNewMedidasQuejasYReclamos);
                    if (oldQuejasYReclamosOfMedidasQuejasYReclamosListNewMedidasQuejasYReclamos != null && !oldQuejasYReclamosOfMedidasQuejasYReclamosListNewMedidasQuejasYReclamos.equals(quejasYReclamos)) {
                        oldQuejasYReclamosOfMedidasQuejasYReclamosListNewMedidasQuejasYReclamos.getMedidasQuejasYReclamosList().remove(medidasQuejasYReclamosListNewMedidasQuejasYReclamos);
                        oldQuejasYReclamosOfMedidasQuejasYReclamosListNewMedidasQuejasYReclamos = em.merge(oldQuejasYReclamosOfMedidasQuejasYReclamosListNewMedidasQuejasYReclamos);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = quejasYReclamos.getNoTicket();
                if (findQuejasYReclamos(id) == null) {
                    throw new NonexistentEntityException("The quejasYReclamos with id " + id + " no longer exists.");
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
            QuejasYReclamos quejasYReclamos;
            try {
                quejasYReclamos = em.getReference(QuejasYReclamos.class, id);
                quejasYReclamos.getNoTicket();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The quejasYReclamos with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<MedidasQuejasYReclamos> medidasQuejasYReclamosListOrphanCheck = quejasYReclamos.getMedidasQuejasYReclamosList();
            for (MedidasQuejasYReclamos medidasQuejasYReclamosListOrphanCheckMedidasQuejasYReclamos : medidasQuejasYReclamosListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This QuejasYReclamos (" + quejasYReclamos + ") cannot be destroyed since the MedidasQuejasYReclamos " + medidasQuejasYReclamosListOrphanCheckMedidasQuejasYReclamos + " in its medidasQuejasYReclamosList field has a non-nullable quejasYReclamos field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Pasajero idPasajero = quejasYReclamos.getIdPasajero();
            if (idPasajero != null) {
                idPasajero.getQuejasYReclamosList().remove(quejasYReclamos);
                idPasajero = em.merge(idPasajero);
            }
            Estacion idEstacion = quejasYReclamos.getIdEstacion();
            if (idEstacion != null) {
                idEstacion.getQuejasYReclamosList().remove(quejasYReclamos);
                idEstacion = em.merge(idEstacion);
            }
            Empleado idEmpleado = quejasYReclamos.getIdEmpleado();
            if (idEmpleado != null) {
                idEmpleado.getQuejasYReclamosList().remove(quejasYReclamos);
                idEmpleado = em.merge(idEmpleado);
            }
            em.remove(quejasYReclamos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<QuejasYReclamos> findQuejasYReclamosEntities() {
        return findQuejasYReclamosEntities(true, -1, -1);
    }

    public List<QuejasYReclamos> findQuejasYReclamosEntities(int maxResults, int firstResult) {
        return findQuejasYReclamosEntities(false, maxResults, firstResult);
    }

    private List<QuejasYReclamos> findQuejasYReclamosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(QuejasYReclamos.class));
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

    public QuejasYReclamos findQuejasYReclamos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(QuejasYReclamos.class, id);
        } finally {
            em.close();
        }
    }

    public int getQuejasYReclamosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<QuejasYReclamos> rt = cq.from(QuejasYReclamos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
