/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jdavidva
 */
@Entity
@Table(name = "turno")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Turno.findAll", query = "SELECT t FROM Turno t"),
    @NamedQuery(name = "Turno.findByIdBus", query = "SELECT t FROM Turno t WHERE t.turnoPK.idBus = :idBus"),
    @NamedQuery(name = "Turno.findByFecha", query = "SELECT t FROM Turno t WHERE t.turnoPK.fecha = :fecha"),
    @NamedQuery(name = "Turno.findByJornada", query = "SELECT t FROM Turno t WHERE t.turnoPK.jornada = :jornada")})
public class Turno implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected TurnoPK turnoPK;
    @JoinColumn(name = "id_ruta", referencedColumnName = "id_ruta")
    @ManyToOne
    private Ruta idRuta;
    @JoinColumn(name = "id_empleado", referencedColumnName = "id_empleado")
    @ManyToOne
    private Empleado idEmpleado;
    @JoinColumn(name = "id_bus", referencedColumnName = "id_bus", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Bus bus;

    public Turno() {
    }

    public Turno(TurnoPK turnoPK) {
        this.turnoPK = turnoPK;
    }

    public Turno(String idBus, Date fecha, String jornada) {
        this.turnoPK = new TurnoPK(idBus, fecha, jornada);
    }

    public TurnoPK getTurnoPK() {
        return turnoPK;
    }

    public void setTurnoPK(TurnoPK turnoPK) {
        this.turnoPK = turnoPK;
    }

    public Ruta getIdRuta() {
        return idRuta;
    }

    public void setIdRuta(Ruta idRuta) {
        this.idRuta = idRuta;
    }

    public Empleado getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Empleado idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public Bus getBus() {
        return bus;
    }

    public void setBus(Bus bus) {
        this.bus = bus;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (turnoPK != null ? turnoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Turno)) {
            return false;
        }
        Turno other = (Turno) object;
        if ((this.turnoPK == null && other.turnoPK != null) || (this.turnoPK != null && !this.turnoPK.equals(other.turnoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Turno[ turnoPK=" + turnoPK + " ]";
    }
    
}
