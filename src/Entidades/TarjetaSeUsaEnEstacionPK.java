/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

/**
 *
 * @author jdavidva
 */
@Embeddable
public class TarjetaSeUsaEnEstacionPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "pin_tarjeta")
    private int pinTarjeta;
    @Basic(optional = false)
    @Column(name = "id_estacion")
    private String idEstacion;
    @Basic(optional = false)
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;

    public TarjetaSeUsaEnEstacionPK() {
    }

    public TarjetaSeUsaEnEstacionPK(int pinTarjeta, String idEstacion, Date fecha) {
        this.pinTarjeta = pinTarjeta;
        this.idEstacion = idEstacion;
        this.fecha = fecha;
    }

    public int getPinTarjeta() {
        return pinTarjeta;
    }

    public void setPinTarjeta(int pinTarjeta) {
        this.pinTarjeta = pinTarjeta;
    }

    public String getIdEstacion() {
        return idEstacion;
    }

    public void setIdEstacion(String idEstacion) {
        this.idEstacion = idEstacion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) pinTarjeta;
        hash += (idEstacion != null ? idEstacion.hashCode() : 0);
        hash += (fecha != null ? fecha.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TarjetaSeUsaEnEstacionPK)) {
            return false;
        }
        TarjetaSeUsaEnEstacionPK other = (TarjetaSeUsaEnEstacionPK) object;
        if (this.pinTarjeta != other.pinTarjeta) {
            return false;
        }
        if ((this.idEstacion == null && other.idEstacion != null) || (this.idEstacion != null && !this.idEstacion.equals(other.idEstacion))) {
            return false;
        }
        if ((this.fecha == null && other.fecha != null) || (this.fecha != null && !this.fecha.equals(other.fecha))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.TarjetaSeUsaEnEstacionPK[ pinTarjeta=" + pinTarjeta + ", idEstacion=" + idEstacion + ", fecha=" + fecha + " ]";
    }
    
}
