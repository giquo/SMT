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
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author jdavidva
 */
public class EmpleadoJpaController implements Serializable {

    public EmpleadoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Empleado empleado) throws PreexistingEntityException, Exception {
        if (empleado.getQuejasYReclamosList() == null) {
            empleado.setQuejasYReclamosList(new ArrayList<QuejasYReclamos>());
        }
        if (empleado.getRecargaList() == null) {
            empleado.setRecargaList(new ArrayList<Recarga>());
        }
        if (empleado.getEstacionList() == null) {
            empleado.setEstacionList(new ArrayList<Estacion>());
        }
        if (empleado.getTurnoList() == null) {
            empleado.setTurnoList(new ArrayList<Turno>());
        }
        if (empleado.getVentaTarjetaList() == null) {
            empleado.setVentaTarjetaList(new ArrayList<VentaTarjeta>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<QuejasYReclamos> attachedQuejasYReclamosList = new ArrayList<QuejasYReclamos>();
            for (QuejasYReclamos quejasYReclamosListQuejasYReclamosToAttach : empleado.getQuejasYReclamosList()) {
                quejasYReclamosListQuejasYReclamosToAttach = em.getReference(quejasYReclamosListQuejasYReclamosToAttach.getClass(), quejasYReclamosListQuejasYReclamosToAttach.getNoTicket());
                attachedQuejasYReclamosList.add(quejasYReclamosListQuejasYReclamosToAttach);
            }
            empleado.setQuejasYReclamosList(attachedQuejasYReclamosList);
            List<Recarga> attachedRecargaList = new ArrayList<Recarga>();
            for (Recarga recargaListRecargaToAttach : empleado.getRecargaList()) {
                recargaListRecargaToAttach = em.getReference(recargaListRecargaToAttach.getClass(), recargaListRecargaToAttach.getConsecutivo());
                attachedRecargaList.add(recargaListRecargaToAttach);
            }
            empleado.setRecargaList(attachedRecargaList);
            List<Estacion> attachedEstacionList = new ArrayList<Estacion>();
            for (Estacion estacionListEstacionToAttach : empleado.getEstacionList()) {
                estacionListEstacionToAttach = em.getReference(estacionListEstacionToAttach.getClass(), estacionListEstacionToAttach.getIdEstacion());
                attachedEstacionList.add(estacionListEstacionToAttach);
            }
            empleado.setEstacionList(attachedEstacionList);
            List<Turno> attachedTurnoList = new ArrayList<Turno>();
            for (Turno turnoListTurnoToAttach : empleado.getTurnoList()) {
                turnoListTurnoToAttach = em.getReference(turnoListTurnoToAttach.getClass(), turnoListTurnoToAttach.getTurnoPK());
                attachedTurnoList.add(turnoListTurnoToAttach);
            }
            empleado.setTurnoList(attachedTurnoList);
            List<VentaTarjeta> attachedVentaTarjetaList = new ArrayList<VentaTarjeta>();
            for (VentaTarjeta ventaTarjetaListVentaTarjetaToAttach : empleado.getVentaTarjetaList()) {
                ventaTarjetaListVentaTarjetaToAttach = em.getReference(ventaTarjetaListVentaTarjetaToAttach.getClass(), ventaTarjetaListVentaTarjetaToAttach.getPinTarjeta());
                attachedVentaTarjetaList.add(ventaTarjetaListVentaTarjetaToAttach);
            }
            empleado.setVentaTarjetaList(attachedVentaTarjetaList);
            em.persist(empleado);
            for (QuejasYReclamos quejasYReclamosListQuejasYReclamos : empleado.getQuejasYReclamosList()) {
                Empleado oldIdEmpleadoOfQuejasYReclamosListQuejasYReclamos = quejasYReclamosListQuejasYReclamos.getIdEmpleado();
                quejasYReclamosListQuejasYReclamos.setIdEmpleado(empleado);
                quejasYReclamosListQuejasYReclamos = em.merge(quejasYReclamosListQuejasYReclamos);
                if (oldIdEmpleadoOfQuejasYReclamosListQuejasYReclamos != null) {
                    oldIdEmpleadoOfQuejasYReclamosListQuejasYReclamos.getQuejasYReclamosList().remove(quejasYReclamosListQuejasYReclamos);
                    oldIdEmpleadoOfQuejasYReclamosListQuejasYReclamos = em.merge(oldIdEmpleadoOfQuejasYReclamosListQuejasYReclamos);
                }
            }
            for (Recarga recargaListRecarga : empleado.getRecargaList()) {
                Empleado oldIdEmpleadoOfRecargaListRecarga = recargaListRecarga.getIdEmpleado();
                recargaListRecarga.setIdEmpleado(empleado);
                recargaListRecarga = em.merge(recargaListRecarga);
                if (oldIdEmpleadoOfRecargaListRecarga != null) {
                    oldIdEmpleadoOfRecargaListRecarga.getRecargaList().remove(recargaListRecarga);
                    oldIdEmpleadoOfRecargaListRecarga = em.merge(oldIdEmpleadoOfRecargaListRecarga);
                }
            }
            for (Estacion estacionListEstacion : empleado.getEstacionList()) {
                Empleado oldEmpleadoEncargadoOfEstacionListEstacion = estacionListEstacion.getEmpleadoEncargado();
                estacionListEstacion.setEmpleadoEncargado(empleado);
                estacionListEstacion = em.merge(estacionListEstacion);
                if (oldEmpleadoEncargadoOfEstacionListEstacion != null) {
                    oldEmpleadoEncargadoOfEstacionListEstacion.getEstacionList().remove(estacionListEstacion);
                    oldEmpleadoEncargadoOfEstacionListEstacion = em.merge(oldEmpleadoEncargadoOfEstacionListEstacion);
                }
            }
            for (Turno turnoListTurno : empleado.getTurnoList()) {
                Empleado oldIdEmpleadoOfTurnoListTurno = turnoListTurno.getIdEmpleado();
                turnoListTurno.setIdEmpleado(empleado);
                turnoListTurno = em.merge(turnoListTurno);
                if (oldIdEmpleadoOfTurnoListTurno != null) {
                    oldIdEmpleadoOfTurnoListTurno.getTurnoList().remove(turnoListTurno);
                    oldIdEmpleadoOfTurnoListTurno = em.merge(oldIdEmpleadoOfTurnoListTurno);
                }
            }
            for (VentaTarjeta ventaTarjetaListVentaTarjeta : empleado.getVentaTarjetaList()) {
                Empleado oldIdEmpleadoOfVentaTarjetaListVentaTarjeta = ventaTarjetaListVentaTarjeta.getIdEmpleado();
                ventaTarjetaListVentaTarjeta.setIdEmpleado(empleado);
                ventaTarjetaListVentaTarjeta = em.merge(ventaTarjetaListVentaTarjeta);
                if (oldIdEmpleadoOfVentaTarjetaListVentaTarjeta != null) {
                    oldIdEmpleadoOfVentaTarjetaListVentaTarjeta.getVentaTarjetaList().remove(ventaTarjetaListVentaTarjeta);
                    oldIdEmpleadoOfVentaTarjetaListVentaTarjeta = em.merge(oldIdEmpleadoOfVentaTarjetaListVentaTarjeta);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEmpleado(empleado.getIdEmpleado()) != null) {
                throw new PreexistingEntityException("Empleado " + empleado + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Empleado empleado) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empleado persistentEmpleado = em.find(Empleado.class, empleado.getIdEmpleado());
            List<QuejasYReclamos> quejasYReclamosListOld = persistentEmpleado.getQuejasYReclamosList();
            List<QuejasYReclamos> quejasYReclamosListNew = empleado.getQuejasYReclamosList();
            List<Recarga> recargaListOld = persistentEmpleado.getRecargaList();
            List<Recarga> recargaListNew = empleado.getRecargaList();
            List<Estacion> estacionListOld = persistentEmpleado.getEstacionList();
            List<Estacion> estacionListNew = empleado.getEstacionList();
            List<Turno> turnoListOld = persistentEmpleado.getTurnoList();
            List<Turno> turnoListNew = empleado.getTurnoList();
            List<VentaTarjeta> ventaTarjetaListOld = persistentEmpleado.getVentaTarjetaList();
            List<VentaTarjeta> ventaTarjetaListNew = empleado.getVentaTarjetaList();
            List<QuejasYReclamos> attachedQuejasYReclamosListNew = new ArrayList<QuejasYReclamos>();
            for (QuejasYReclamos quejasYReclamosListNewQuejasYReclamosToAttach : quejasYReclamosListNew) {
                quejasYReclamosListNewQuejasYReclamosToAttach = em.getReference(quejasYReclamosListNewQuejasYReclamosToAttach.getClass(), quejasYReclamosListNewQuejasYReclamosToAttach.getNoTicket());
                attachedQuejasYReclamosListNew.add(quejasYReclamosListNewQuejasYReclamosToAttach);
            }
            quejasYReclamosListNew = attachedQuejasYReclamosListNew;
            empleado.setQuejasYReclamosList(quejasYReclamosListNew);
            List<Recarga> attachedRecargaListNew = new ArrayList<Recarga>();
            for (Recarga recargaListNewRecargaToAttach : recargaListNew) {
                recargaListNewRecargaToAttach = em.getReference(recargaListNewRecargaToAttach.getClass(), recargaListNewRecargaToAttach.getConsecutivo());
                attachedRecargaListNew.add(recargaListNewRecargaToAttach);
            }
            recargaListNew = attachedRecargaListNew;
            empleado.setRecargaList(recargaListNew);
            List<Estacion> attachedEstacionListNew = new ArrayList<Estacion>();
            for (Estacion estacionListNewEstacionToAttach : estacionListNew) {
                estacionListNewEstacionToAttach = em.getReference(estacionListNewEstacionToAttach.getClass(), estacionListNewEstacionToAttach.getIdEstacion());
                attachedEstacionListNew.add(estacionListNewEstacionToAttach);
            }
            estacionListNew = attachedEstacionListNew;
            empleado.setEstacionList(estacionListNew);
            List<Turno> attachedTurnoListNew = new ArrayList<Turno>();
            for (Turno turnoListNewTurnoToAttach : turnoListNew) {
                turnoListNewTurnoToAttach = em.getReference(turnoListNewTurnoToAttach.getClass(), turnoListNewTurnoToAttach.getTurnoPK());
                attachedTurnoListNew.add(turnoListNewTurnoToAttach);
            }
            turnoListNew = attachedTurnoListNew;
            empleado.setTurnoList(turnoListNew);
            List<VentaTarjeta> attachedVentaTarjetaListNew = new ArrayList<VentaTarjeta>();
            for (VentaTarjeta ventaTarjetaListNewVentaTarjetaToAttach : ventaTarjetaListNew) {
                ventaTarjetaListNewVentaTarjetaToAttach = em.getReference(ventaTarjetaListNewVentaTarjetaToAttach.getClass(), ventaTarjetaListNewVentaTarjetaToAttach.getPinTarjeta());
                attachedVentaTarjetaListNew.add(ventaTarjetaListNewVentaTarjetaToAttach);
            }
            ventaTarjetaListNew = attachedVentaTarjetaListNew;
            empleado.setVentaTarjetaList(ventaTarjetaListNew);
            empleado = em.merge(empleado);
            for (QuejasYReclamos quejasYReclamosListOldQuejasYReclamos : quejasYReclamosListOld) {
                if (!quejasYReclamosListNew.contains(quejasYReclamosListOldQuejasYReclamos)) {
                    quejasYReclamosListOldQuejasYReclamos.setIdEmpleado(null);
                    quejasYReclamosListOldQuejasYReclamos = em.merge(quejasYReclamosListOldQuejasYReclamos);
                }
            }
            for (QuejasYReclamos quejasYReclamosListNewQuejasYReclamos : quejasYReclamosListNew) {
                if (!quejasYReclamosListOld.contains(quejasYReclamosListNewQuejasYReclamos)) {
                    Empleado oldIdEmpleadoOfQuejasYReclamosListNewQuejasYReclamos = quejasYReclamosListNewQuejasYReclamos.getIdEmpleado();
                    quejasYReclamosListNewQuejasYReclamos.setIdEmpleado(empleado);
                    quejasYReclamosListNewQuejasYReclamos = em.merge(quejasYReclamosListNewQuejasYReclamos);
                    if (oldIdEmpleadoOfQuejasYReclamosListNewQuejasYReclamos != null && !oldIdEmpleadoOfQuejasYReclamosListNewQuejasYReclamos.equals(empleado)) {
                        oldIdEmpleadoOfQuejasYReclamosListNewQuejasYReclamos.getQuejasYReclamosList().remove(quejasYReclamosListNewQuejasYReclamos);
                        oldIdEmpleadoOfQuejasYReclamosListNewQuejasYReclamos = em.merge(oldIdEmpleadoOfQuejasYReclamosListNewQuejasYReclamos);
                    }
                }
            }
            for (Recarga recargaListOldRecarga : recargaListOld) {
                if (!recargaListNew.contains(recargaListOldRecarga)) {
                    recargaListOldRecarga.setIdEmpleado(null);
                    recargaListOldRecarga = em.merge(recargaListOldRecarga);
                }
            }
            for (Recarga recargaListNewRecarga : recargaListNew) {
                if (!recargaListOld.contains(recargaListNewRecarga)) {
                    Empleado oldIdEmpleadoOfRecargaListNewRecarga = recargaListNewRecarga.getIdEmpleado();
                    recargaListNewRecarga.setIdEmpleado(empleado);
                    recargaListNewRecarga = em.merge(recargaListNewRecarga);
                    if (oldIdEmpleadoOfRecargaListNewRecarga != null && !oldIdEmpleadoOfRecargaListNewRecarga.equals(empleado)) {
                        oldIdEmpleadoOfRecargaListNewRecarga.getRecargaList().remove(recargaListNewRecarga);
                        oldIdEmpleadoOfRecargaListNewRecarga = em.merge(oldIdEmpleadoOfRecargaListNewRecarga);
                    }
                }
            }
            for (Estacion estacionListOldEstacion : estacionListOld) {
                if (!estacionListNew.contains(estacionListOldEstacion)) {
                    estacionListOldEstacion.setEmpleadoEncargado(null);
                    estacionListOldEstacion = em.merge(estacionListOldEstacion);
                }
            }
            for (Estacion estacionListNewEstacion : estacionListNew) {
                if (!estacionListOld.contains(estacionListNewEstacion)) {
                    Empleado oldEmpleadoEncargadoOfEstacionListNewEstacion = estacionListNewEstacion.getEmpleadoEncargado();
                    estacionListNewEstacion.setEmpleadoEncargado(empleado);
                    estacionListNewEstacion = em.merge(estacionListNewEstacion);
                    if (oldEmpleadoEncargadoOfEstacionListNewEstacion != null && !oldEmpleadoEncargadoOfEstacionListNewEstacion.equals(empleado)) {
                        oldEmpleadoEncargadoOfEstacionListNewEstacion.getEstacionList().remove(estacionListNewEstacion);
                        oldEmpleadoEncargadoOfEstacionListNewEstacion = em.merge(oldEmpleadoEncargadoOfEstacionListNewEstacion);
                    }
                }
            }
            for (Turno turnoListOldTurno : turnoListOld) {
                if (!turnoListNew.contains(turnoListOldTurno)) {
                    turnoListOldTurno.setIdEmpleado(null);
                    turnoListOldTurno = em.merge(turnoListOldTurno);
                }
            }
            for (Turno turnoListNewTurno : turnoListNew) {
                if (!turnoListOld.contains(turnoListNewTurno)) {
                    Empleado oldIdEmpleadoOfTurnoListNewTurno = turnoListNewTurno.getIdEmpleado();
                    turnoListNewTurno.setIdEmpleado(empleado);
                    turnoListNewTurno = em.merge(turnoListNewTurno);
                    if (oldIdEmpleadoOfTurnoListNewTurno != null && !oldIdEmpleadoOfTurnoListNewTurno.equals(empleado)) {
                        oldIdEmpleadoOfTurnoListNewTurno.getTurnoList().remove(turnoListNewTurno);
                        oldIdEmpleadoOfTurnoListNewTurno = em.merge(oldIdEmpleadoOfTurnoListNewTurno);
                    }
                }
            }
            for (VentaTarjeta ventaTarjetaListOldVentaTarjeta : ventaTarjetaListOld) {
                if (!ventaTarjetaListNew.contains(ventaTarjetaListOldVentaTarjeta)) {
                    ventaTarjetaListOldVentaTarjeta.setIdEmpleado(null);
                    ventaTarjetaListOldVentaTarjeta = em.merge(ventaTarjetaListOldVentaTarjeta);
                }
            }
            for (VentaTarjeta ventaTarjetaListNewVentaTarjeta : ventaTarjetaListNew) {
                if (!ventaTarjetaListOld.contains(ventaTarjetaListNewVentaTarjeta)) {
                    Empleado oldIdEmpleadoOfVentaTarjetaListNewVentaTarjeta = ventaTarjetaListNewVentaTarjeta.getIdEmpleado();
                    ventaTarjetaListNewVentaTarjeta.setIdEmpleado(empleado);
                    ventaTarjetaListNewVentaTarjeta = em.merge(ventaTarjetaListNewVentaTarjeta);
                    if (oldIdEmpleadoOfVentaTarjetaListNewVentaTarjeta != null && !oldIdEmpleadoOfVentaTarjetaListNewVentaTarjeta.equals(empleado)) {
                        oldIdEmpleadoOfVentaTarjetaListNewVentaTarjeta.getVentaTarjetaList().remove(ventaTarjetaListNewVentaTarjeta);
                        oldIdEmpleadoOfVentaTarjetaListNewVentaTarjeta = em.merge(oldIdEmpleadoOfVentaTarjetaListNewVentaTarjeta);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = empleado.getIdEmpleado();
                if (findEmpleado(id) == null) {
                    throw new NonexistentEntityException("The empleado with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empleado empleado;
            try {
                empleado = em.getReference(Empleado.class, id);
                empleado.getIdEmpleado();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The empleado with id " + id + " no longer exists.", enfe);
            }
            List<QuejasYReclamos> quejasYReclamosList = empleado.getQuejasYReclamosList();
            for (QuejasYReclamos quejasYReclamosListQuejasYReclamos : quejasYReclamosList) {
                quejasYReclamosListQuejasYReclamos.setIdEmpleado(null);
                quejasYReclamosListQuejasYReclamos = em.merge(quejasYReclamosListQuejasYReclamos);
            }
            List<Recarga> recargaList = empleado.getRecargaList();
            for (Recarga recargaListRecarga : recargaList) {
                recargaListRecarga.setIdEmpleado(null);
                recargaListRecarga = em.merge(recargaListRecarga);
            }
            List<Estacion> estacionList = empleado.getEstacionList();
            for (Estacion estacionListEstacion : estacionList) {
                estacionListEstacion.setEmpleadoEncargado(null);
                estacionListEstacion = em.merge(estacionListEstacion);
            }
            List<Turno> turnoList = empleado.getTurnoList();
            for (Turno turnoListTurno : turnoList) {
                turnoListTurno.setIdEmpleado(null);
                turnoListTurno = em.merge(turnoListTurno);
            }
            List<VentaTarjeta> ventaTarjetaList = empleado.getVentaTarjetaList();
            for (VentaTarjeta ventaTarjetaListVentaTarjeta : ventaTarjetaList) {
                ventaTarjetaListVentaTarjeta.setIdEmpleado(null);
                ventaTarjetaListVentaTarjeta = em.merge(ventaTarjetaListVentaTarjeta);
            }
            em.remove(empleado);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Empleado> findEmpleadoEntities() {
        return findEmpleadoEntities(true, -1, -1);
    }

    public List<Empleado> findEmpleadoEntities(int maxResults, int firstResult) {
        return findEmpleadoEntities(false, maxResults, firstResult);
    }

    private List<Empleado> findEmpleadoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Empleado.class));
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

    public Empleado findEmpleado(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Empleado.class, id);
        } finally {
            em.close();
        }
    }

    public int getEmpleadoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Empleado> rt = cq.from(Empleado.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
