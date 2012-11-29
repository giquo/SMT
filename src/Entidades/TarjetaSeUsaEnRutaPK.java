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
public class TarjetaSeUsaEnRutaPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "pin_tarjeta")
    private int pinTarjeta;
    @Basic(optional = false)
    @Column(name = "id_ruta")
    private String idRuta;
    @Basic(optional = false)
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;

    public TarjetaSeUsaEnRutaPK() {
    }

    public TarjetaSeUsaEnRutaPK(int pinTarjeta, String idRuta, Date fecha) {
        this.pinTarjeta = pinTarjeta;
        this.idRuta = idRuta;
        this.fecha = fecha;
    }

    public int getPinTarjeta() {
        return pinTarjeta;
    }

    public void setPinTarjeta(int pinTarjeta) {
        this.pinTarjeta = pinTarjeta;
    }

    public String getIdRuta() {
        return idRuta;
    }

    public void setIdRuta(String idRuta) {
        this.idRuta = idRuta;
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
        hash += (idRuta != null ? idRuta.hashCode() : 0);
        hash += (fecha != null ? fecha.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TarjetaSeUsaEnRutaPK)) {
            return false;
        }
        TarjetaSeUsaEnRutaPK other = (TarjetaSeUsaEnRutaPK) object;
        if (this.pinTarjeta != other.pinTarjeta) {
            return false;
        }
        if ((this.idRuta == null && other.idRuta != null) || (this.idRuta != null && !this.idRuta.equals(other.idRuta))) {
            return false;
        }
        if ((this.fecha == null && other.fecha != null) || (this.fecha != null && !this.fecha.equals(other.fecha))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.TarjetaSeUsaEnRutaPK[ pinTarjeta=" + pinTarjeta + ", idRuta=" + idRuta + ", fecha=" + fecha + " ]";
    }
    
}
