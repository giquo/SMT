/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DAO.exceptions.IllegalOrphanException;
import DAO.exceptions.NonexistentEntityException;
import DAO.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entidades.Bus;
import Entidades.Ruta;
import java.util.ArrayList;
import java.util.List;
import Entidades.TarjetaSeUsaEnRuta;
import Entidades.Turno;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author jdavidva
 */
public class RutaJpaController implements Serializable {

    public RutaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Ruta ruta) throws PreexistingEntityException, Exception {
        if (ruta.getBusList() == null) {
            ruta.setBusList(new ArrayList<Bus>());
        }
        if (ruta.getTarjetaSeUsaEnRutaList() == null) {
            ruta.setTarjetaSeUsaEnRutaList(new ArrayList<TarjetaSeUsaEnRuta>());
        }
        if (ruta.getTurnoList() == null) {
            ruta.setTurnoList(new ArrayList<Turno>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Bus> attachedBusList = new ArrayList<Bus>();
            for (Bus busListBusToAttach : ruta.getBusList()) {
                busListBusToAttach = em.getReference(busListBusToAttach.getClass(), busListBusToAttach.getIdBus());
                attachedBusList.add(busListBusToAttach);
            }
            ruta.setBusList(attachedBusList);
            List<TarjetaSeUsaEnRuta> attachedTarjetaSeUsaEnRutaList = new ArrayList<TarjetaSeUsaEnRuta>();
            for (TarjetaSeUsaEnRuta tarjetaSeUsaEnRutaListTarjetaSeUsaEnRutaToAttach : ruta.getTarjetaSeUsaEnRutaList()) {
                tarjetaSeUsaEnRutaListTarjetaSeUsaEnRutaToAttach = em.getReference(tarjetaSeUsaEnRutaListTarjetaSeUsaEnRutaToAttach.getClass(), tarjetaSeUsaEnRutaListTarjetaSeUsaEnRutaToAttach.getTarjetaSeUsaEnRutaPK());
                attachedTarjetaSeUsaEnRutaList.add(tarjetaSeUsaEnRutaListTarjetaSeUsaEnRutaToAttach);
            }
            ruta.setTarjetaSeUsaEnRutaList(attachedTarjetaSeUsaEnRutaList);
            List<Turno> attachedTurnoList = new ArrayList<Turno>();
            for (Turno turnoListTurnoToAttach : ruta.getTurnoList()) {
                turnoListTurnoToAttach = em.getReference(turnoListTurnoToAttach.getClass(), turnoListTurnoToAttach.getTurnoPK());
                attachedTurnoList.add(turnoListTurnoToAttach);
            }
            ruta.setTurnoList(attachedTurnoList);
            em.persist(ruta);
            for (Bus busListBus : ruta.getBusList()) {
                Ruta oldRutaAsignadaOfBusListBus = busListBus.getRutaAsignada();
                busListBus.setRutaAsignada(ruta);
                busListBus = em.merge(busListBus);
                if (oldRutaAsignadaOfBusListBus != null) {
                    oldRutaAsignadaOfBusListBus.getBusList().remove(busListBus);
                    oldRutaAsignadaOfBusListBus = em.merge(oldRutaAsignadaOfBusListBus);
                }
            }
            for (TarjetaSeUsaEnRuta tarjetaSeUsaEnRutaListTarjetaSeUsaEnRuta : ruta.getTarjetaSeUsaEnRutaList()) {
                Ruta oldRutaOfTarjetaSeUsaEnRutaListTarjetaSeUsaEnRuta = tarjetaSeUsaEnRutaListTarjetaSeUsaEnRuta.getRuta();
                tarjetaSeUsaEnRutaListTarjetaSeUsaEnRuta.setRuta(ruta);
                tarjetaSeUsaEnRutaListTarjetaSeUsaEnRuta = em.merge(tarjetaSeUsaEnRutaListTarjetaSeUsaEnRuta);
                if (oldRutaOfTarjetaSeUsaEnRutaListTarjetaSeUsaEnRuta != null) {
                    oldRutaOfTarjetaSeUsaEnRutaListTarjetaSeUsaEnRuta.getTarjetaSeUsaEnRutaList().remove(tarjetaSeUsaEnRutaListTarjetaSeUsaEnRuta);
                    oldRutaOfTarjetaSeUsaEnRutaListTarjetaSeUsaEnRuta = em.merge(oldRutaOfTarjetaSeUsaEnRutaListTarjetaSeUsaEnRuta);
                }
            }
            for (Turno turnoListTurno : ruta.getTurnoList()) {
                Ruta oldIdRutaOfTurnoListTurno = turnoListTurno.getIdRuta();
                turnoListTurno.setIdRuta(ruta);
                turnoListTurno = em.merge(turnoListTurno);
                if (oldIdRutaOfTurnoListTurno != null) {
                    oldIdRutaOfTurnoListTurno.getTurnoList().remove(turnoListTurno);
                    oldIdRutaOfTurnoListTurno = em.merge(oldIdRutaOfTurnoListTurno);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findRuta(ruta.getIdRuta()) != null) {
                throw new PreexistingEntityException("Ruta " + ruta + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Ruta ruta) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ruta persistentRuta = em.find(Ruta.class, ruta.getIdRuta());
            List<Bus> busListOld = persistentRuta.getBusList();
            List<Bus> busListNew = ruta.getBusList();
            List<TarjetaSeUsaEnRuta> tarjetaSeUsaEnRutaListOld = persistentRuta.getTarjetaSeUsaEnRutaList();
            List<TarjetaSeUsaEnRuta> tarjetaSeUsaEnRutaListNew = ruta.getTarjetaSeUsaEnRutaList();
            List<Turno> turnoListOld = persistentRuta.getTurnoList();
            List<Turno> turnoListNew = ruta.getTurnoList();
            List<String> illegalOrphanMessages = null;
            for (TarjetaSeUsaEnRuta tarjetaSeUsaEnRutaListOldTarjetaSeUsaEnRuta : tarjetaSeUsaEnRutaListOld) {
                if (!tarjetaSeUsaEnRutaListNew.contains(tarjetaSeUsaEnRutaListOldTarjetaSeUsaEnRuta)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TarjetaSeUsaEnRuta " + tarjetaSeUsaEnRutaListOldTarjetaSeUsaEnRuta + " since its ruta field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Bus> attachedBusListNew = new ArrayList<Bus>();
            for (Bus busListNewBusToAttach : busListNew) {
                busListNewBusToAttach = em.getReference(busListNewBusToAttach.getClass(), busListNewBusToAttach.getIdBus());
                attachedBusListNew.add(busListNewBusToAttach);
            }
            busListNew = attachedBusListNew;
            ruta.setBusList(busListNew);
            List<TarjetaSeUsaEnRuta> attachedTarjetaSeUsaEnRutaListNew = new ArrayList<TarjetaSeUsaEnRuta>();
            for (TarjetaSeUsaEnRuta tarjetaSeUsaEnRutaListNewTarjetaSeUsaEnRutaToAttach : tarjetaSeUsaEnRutaListNew) {
                tarjetaSeUsaEnRutaListNewTarjetaSeUsaEnRutaToAttach = em.getReference(tarjetaSeUsaEnRutaListNewTarjetaSeUsaEnRutaToAttach.getClass(), tarjetaSeUsaEnRutaListNewTarjetaSeUsaEnRutaToAttach.getTarjetaSeUsaEnRutaPK());
                attachedTarjetaSeUsaEnRutaListNew.add(tarjetaSeUsaEnRutaListNewTarjetaSeUsaEnRutaToAttach);
            }
            tarjetaSeUsaEnRutaListNew = attachedTarjetaSeUsaEnRutaListNew;
            ruta.setTarjetaSeUsaEnRutaList(tarjetaSeUsaEnRutaListNew);
            List<Turno> attachedTurnoListNew = new ArrayList<Turno>();
            for (Turno turnoListNewTurnoToAttach : turnoListNew) {
                turnoListNewTurnoToAttach = em.getReference(turnoListNewTurnoToAttach.getClass(), turnoListNewTurnoToAttach.getTurnoPK());
                attachedTurnoListNew.add(turnoListNewTurnoToAttach);
            }
            turnoListNew = attachedTurnoListNew;
            ruta.setTurnoList(turnoListNew);
            ruta = em.merge(ruta);
            for (Bus busListOldBus : busListOld) {
                if (!busListNew.contains(busListOldBus)) {
                    busListOldBus.setRutaAsignada(null);
                    busListOldBus = em.merge(busListOldBus);
                }
            }
            for (Bus busListNewBus : busListNew) {
                if (!busListOld.contains(busListNewBus)) {
                    Ruta oldRutaAsignadaOfBusListNewBus = busListNewBus.getRutaAsignada();
                    busListNewBus.setRutaAsignada(ruta);
                    busListNewBus = em.merge(busListNewBus);
                    if (oldRutaAsignadaOfBusListNewBus != null && !oldRutaAsignadaOfBusListNewBus.equals(ruta)) {
                        oldRutaAsignadaOfBusListNewBus.getBusList().remove(busListNewBus);
                        oldRutaAsignadaOfBusListNewBus = em.merge(oldRutaAsignadaOfBusListNewBus);
                    }
                }
            }
            for (TarjetaSeUsaEnRuta tarjetaSeUsaEnRutaListNewTarjetaSeUsaEnRuta : tarjetaSeUsaEnRutaListNew) {
                if (!tarjetaSeUsaEnRutaListOld.contains(tarjetaSeUsaEnRutaListNewTarjetaSeUsaEnRuta)) {
                    Ruta oldRutaOfTarjetaSeUsaEnRutaListNewTarjetaSeUsaEnRuta = tarjetaSeUsaEnRutaListNewTarjetaSeUsaEnRuta.getRuta();
                    tarjetaSeUsaEnRutaListNewTarjetaSeUsaEnRuta.setRuta(ruta);
                    tarjetaSeUsaEnRutaListNewTarjetaSeUsaEnRuta = em.merge(tarjetaSeUsaEnRutaListNewTarjetaSeUsaEnRuta);
                    if (oldRutaOfTarjetaSeUsaEnRutaListNewTarjetaSeUsaEnRuta != null && !oldRutaOfTarjetaSeUsaEnRutaListNewTarjetaSeUsaEnRuta.equals(ruta)) {
                        oldRutaOfTarjetaSeUsaEnRutaListNewTarjetaSeUsaEnRuta.getTarjetaSeUsaEnRutaList().remove(tarjetaSeUsaEnRutaListNewTarjetaSeUsaEnRuta);
                        oldRutaOfTarjetaSeUsaEnRutaListNewTarjetaSeUsaEnRuta = em.merge(oldRutaOfTarjetaSeUsaEnRutaListNewTarjetaSeUsaEnRuta);
                    }
                }
            }
            for (Turno turnoListOldTurno : turnoListOld) {
                if (!turnoListNew.contains(turnoListOldTurno)) {
                    turnoListOldTurno.setIdRuta(null);
                    turnoListOldTurno = em.merge(turnoListOldTurno);
                }
            }
            for (Turno turnoListNewTurno : turnoListNew) {
                if (!turnoListOld.contains(turnoListNewTurno)) {
                    Ruta oldIdRutaOfTurnoListNewTurno = turnoListNewTurno.getIdRuta();
                    turnoListNewTurno.setIdRuta(ruta);
                    turnoListNewTurno = em.merge(turnoListNewTurno);
                    if (oldIdRutaOfTurnoListNewTurno != null && !oldIdRutaOfTurnoListNewTurno.equals(ruta)) {
                        oldIdRutaOfTurnoListNewTurno.getTurnoList().remove(turnoListNewTurno);
                        oldIdRutaOfTurnoListNewTurno = em.merge(oldIdRutaOfTurnoListNewTurno);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = ruta.getIdRuta();
                if (findRuta(id) == null) {
                    throw new NonexistentEntityException("The ruta with id " + id + " no longer exists.");
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
            Ruta ruta;
            try {
                ruta = em.getReference(Ruta.class, id);
                ruta.getIdRuta();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ruta with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<TarjetaSeUsaEnRuta> tarjetaSeUsaEnRutaListOrphanCheck = ruta.getTarjetaSeUsaEnRutaList();
            for (TarjetaSeUsaEnRuta tarjetaSeUsaEnRutaListOrphanCheckTarjetaSeUsaEnRuta : tarjetaSeUsaEnRutaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Ruta (" + ruta + ") cannot be destroyed since the TarjetaSeUsaEnRuta " + tarjetaSeUsaEnRutaListOrphanCheckTarjetaSeUsaEnRuta + " in its tarjetaSeUsaEnRutaList field has a non-nullable ruta field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Bus> busList = ruta.getBusList();
            for (Bus busListBus : busList) {
                busListBus.setRutaAsignada(null);
                busListBus = em.merge(busListBus);
            }
            List<Turno> turnoList = ruta.getTurnoList();
            for (Turno turnoListTurno : turnoList) {
                turnoListTurno.setIdRuta(null);
                turnoListTurno = em.merge(turnoListTurno);
            }
            em.remove(ruta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Ruta> findRutaEntities() {
        return findRutaEntities(true, -1, -1);
    }

    public List<Ruta> findRutaEntities(int maxResults, int firstResult) {
        return findRutaEntities(false, maxResults, firstResult);
    }

    private List<Ruta> findRutaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Ruta.class));
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

    public Ruta findRuta(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Ruta.class, id);
        } finally {
            em.close();
        }
    }

    public int getRutaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Ruta> rt = cq.from(Ruta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
