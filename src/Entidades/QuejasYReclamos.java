/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author jdavidva
 */
@Entity
@Table(name = "quejas_y_reclamos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "QuejasYReclamos.findAll", query = "SELECT q FROM QuejasYReclamos q"),
    @NamedQuery(name = "QuejasYReclamos.findByNoTicket", query = "SELECT q FROM QuejasYReclamos q WHERE q.noTicket = :noTicket"),
    @NamedQuery(name = "QuejasYReclamos.findByEstado", query = "SELECT q FROM QuejasYReclamos q WHERE q.estado = :estado"),
    @NamedQuery(name = "QuejasYReclamos.findByDescripcion", query = "SELECT q FROM QuejasYReclamos q WHERE q.descripcion = :descripcion"),
    @NamedQuery(name = "QuejasYReclamos.findByFecha", query = "SELECT q FROM QuejasYReclamos q WHERE q.fecha = :fecha")})
public class QuejasYReclamos implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "no_ticket")
    private Integer noTicket;
    @Column(name = "estado")
    private String estado;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @JoinColumn(name = "id_pasajero", referencedColumnName = "id_pasajero")
    @ManyToOne
    private Pasajero idPasajero;
    @JoinColumn(name = "id_estacion", referencedColumnName = "id_estacion")
    @ManyToOne
    private Estacion idEstacion;
    @JoinColumn(name = "id_empleado", referencedColumnName = "id_empleado")
    @ManyToOne
    private Empleado idEmpleado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "quejasYReclamos")
    private List<MedidasQuejasYReclamos> medidasQuejasYReclamosList;

    public QuejasYReclamos() {
    }

    public QuejasYReclamos(Integer noTicket) {
        this.noTicket = noTicket;
    }

    public Integer getNoTicket() {
        return noTicket;
    }

    public void setNoTicket(Integer noTicket) {
        this.noTicket = noTicket;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Pasajero getIdPasajero() {
        return idPasajero;
    }

    public void setIdPasajero(Pasajero idPasajero) {
        this.idPasajero = idPasajero;
    }

    public Estacion getIdEstacion() {
        return idEstacion;
    }

    public void setIdEstacion(Estacion idEstacion) {
        this.idEstacion = idEstacion;
    }

    public Empleado getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Empleado idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    @XmlTransient
    public List<MedidasQuejasYReclamos> getMedidasQuejasYReclamosList() {
        return medidasQuejasYReclamosList;
    }

    public void setMedidasQuejasYReclamosList(List<MedidasQuejasYReclamos> medidasQuejasYReclamosList) {
        this.medidasQuejasYReclamosList = medidasQuejasYReclamosList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (noTicket != null ? noTicket.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof QuejasYReclamos)) {
            return false;
        }
        QuejasYReclamos other = (QuejasYReclamos) object;
        if ((this.noTicket == null && other.noTicket != null) || (this.noTicket != null && !this.noTicket.equals(other.noTicket))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.QuejasYReclamos[ noTicket=" + noTicket + " ]";
    }
    
}
