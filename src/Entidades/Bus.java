/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author jdavidva
 */
@Entity
@Table(name = "bus")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Bus.findAll", query = "SELECT b FROM Bus b"),
    @NamedQuery(name = "Bus.findByIdBus", query = "SELECT b FROM Bus b WHERE b.idBus = :idBus"),
    @NamedQuery(name = "Bus.findByTipoBus", query = "SELECT b FROM Bus b WHERE b.tipoBus = :tipoBus"),
    @NamedQuery(name = "Bus.findByCarroceria", query = "SELECT b FROM Bus b WHERE b.carroceria = :carroceria"),
    @NamedQuery(name = "Bus.findByMotor", query = "SELECT b FROM Bus b WHERE b.motor = :motor"),
    @NamedQuery(name = "Bus.findByCapacidadParados", query = "SELECT b FROM Bus b WHERE b.capacidadParados = :capacidadParados"),
    @NamedQuery(name = "Bus.findByCapacidadSillas", query = "SELECT b FROM Bus b WHERE b.capacidadSillas = :capacidadSillas")})
public class Bus implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_bus")
    private String idBus;
    @Column(name = "tipo_bus")
    private String tipoBus;
    @Column(name = "carroceria")
    private String carroceria;
    @Column(name = "motor")
    private String motor;
    @Column(name = "capacidad_parados")
    private Integer capacidadParados;
    @Column(name = "capacidad_sillas")
    private Integer capacidadSillas;
    @JoinColumn(name = "ruta_asignada", referencedColumnName = "id_ruta")
    @ManyToOne
    private Ruta rutaAsignada;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bus")
    private List<Turno> turnoList;

    public Bus() {
    }

    public Bus(String idBus) {
        this.idBus = idBus;
    }

    public String getIdBus() {
        return idBus;
    }

    public void setIdBus(String idBus) {
        this.idBus = idBus;
    }

    public String getTipoBus() {
        return tipoBus;
    }

    public void setTipoBus(String tipoBus) {
        this.tipoBus = tipoBus;
    }

    public String getCarroceria() {
        return carroceria;
    }

    public void setCarroceria(String carroceria) {
        this.carroceria = carroceria;
    }

    public String getMotor() {
        return motor;
    }

    public void setMotor(String motor) {
        this.motor = motor;
    }

    public Integer getCapacidadParados() {
        return capacidadParados;
    }

    public void setCapacidadParados(Integer capacidadParados) {
        this.capacidadParados = capacidadParados;
    }

    public Integer getCapacidadSillas() {
        return capacidadSillas;
    }

    public void setCapacidadSillas(Integer capacidadSillas) {
        this.capacidadSillas = capacidadSillas;
    }

    public Ruta getRutaAsignada() {
        return rutaAsignada;
    }

    public void setRutaAsignada(Ruta rutaAsignada) {
        this.rutaAsignada = rutaAsignada;
    }

    @XmlTransient
    public List<Turno> getTurnoList() {
        return turnoList;
    }

    public void setTurnoList(List<Turno> turnoList) {
        this.turnoList = turnoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idBus != null ? idBus.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Bus)) {
            return false;
        }
        Bus other = (Bus) object;
        if ((this.idBus == null && other.idBus != null) || (this.idBus != null && !this.idBus.equals(other.idBus))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Bus[ idBus=" + idBus + " ]";
    }
    
}
