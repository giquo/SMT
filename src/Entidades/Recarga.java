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
@Table(name = "recarga")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Recarga.findAll", query = "SELECT r FROM Recarga r"),
    @NamedQuery(name = "Recarga.findByConsecutivo", query = "SELECT r FROM Recarga r WHERE r.consecutivo = :consecutivo"),
    @NamedQuery(name = "Recarga.findByFecha", query = "SELECT r FROM Recarga r WHERE r.fecha = :fecha"),
    @NamedQuery(name = "Recarga.findByValor", query = "SELECT r FROM Recarga r WHERE r.valor = :valor")})
public class Recarga implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "consecutivo")
    private Integer consecutivo;
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Column(name = "valor")
    private Integer valor;
    @JoinColumn(name = "pin_tarjeta", referencedColumnName = "pin_tarjeta")
    @ManyToOne
    private Tarjeta pinTarjeta;
    @JoinColumn(name = "id_estacion", referencedColumnName = "id_estacion")
    @ManyToOne
    private Estacion idEstacion;
    @JoinColumn(name = "id_empleado", referencedColumnName = "id_empleado")
    @ManyToOne
    private Empleado idEmpleado;

    public Recarga() {
    }

    public Recarga(Integer consecutivo) {
        this.consecutivo = consecutivo;
    }

    public Integer getConsecutivo() {
        return consecutivo;
    }

    public void setConsecutivo(Integer consecutivo) {
        this.consecutivo = consecutivo;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Integer getValor() {
        return valor;
    }

    public void setValor(Integer valor) {
        this.valor = valor;
    }

    public Tarjeta getPinTarjeta() {
        return pinTarjeta;
    }

    public void setPinTarjeta(Tarjeta pinTarjeta) {
        this.pinTarjeta = pinTarjeta;
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
        hash += (consecutivo != null ? consecutivo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Recarga)) {
            return false;
        }
        Recarga other = (Recarga) object;
        if ((this.consecutivo == null && other.consecutivo != null) || (this.consecutivo != null && !this.consecutivo.equals(other.consecutivo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Recarga[ consecutivo=" + consecutivo + " ]";
    }
    
}
