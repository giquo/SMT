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
@Table(name = "venta_tarjeta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VentaTarjeta.findAll", query = "SELECT v FROM VentaTarjeta v"),
    @NamedQuery(name = "VentaTarjeta.findByPinTarjeta", query = "SELECT v FROM VentaTarjeta v WHERE v.pinTarjeta = :pinTarjeta"),
    @NamedQuery(name = "VentaTarjeta.findByFecha", query = "SELECT v FROM VentaTarjeta v WHERE v.fecha = :fecha")})
public class VentaTarjeta implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "pin_tarjeta")
    private Integer pinTarjeta;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @JoinColumn(name = "pin_tarjeta", referencedColumnName = "pin_tarjeta", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Tarjeta tarjeta;
    @JoinColumn(name = "id_pasajero", referencedColumnName = "id_pasajero")
    @ManyToOne
    private Pasajero idPasajero;
    @JoinColumn(name = "id_estacion", referencedColumnName = "id_estacion")
    @ManyToOne
    private Estacion idEstacion;
    @JoinColumn(name = "id_empleado", referencedColumnName = "id_empleado")
    @ManyToOne
    private Empleado idEmpleado;

    public VentaTarjeta() {
    }

    public VentaTarjeta(Integer pinTarjeta) {
        this.pinTarjeta = pinTarjeta;
    }

    public Integer getPinTarjeta() {
        return pinTarjeta;
    }

    public void setPinTarjeta(Integer pinTarjeta) {
        this.pinTarjeta = pinTarjeta;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Tarjeta getTarjeta() {
        return tarjeta;
    }

    public void setTarjeta(Tarjeta tarjeta) {
        this.tarjeta = tarjeta;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pinTarjeta != null ? pinTarjeta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VentaTarjeta)) {
            return false;
        }
        VentaTarjeta other = (VentaTarjeta) object;
        if ((this.pinTarjeta == null && other.pinTarjeta != null) || (this.pinTarjeta != null && !this.pinTarjeta.equals(other.pinTarjeta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.VentaTarjeta[ pinTarjeta=" + pinTarjeta + " ]";
    }
    
}
