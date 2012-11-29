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
@Table(name = "tarjeta_se_usa_en_ruta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TarjetaSeUsaEnRuta.findAll", query = "SELECT t FROM TarjetaSeUsaEnRuta t"),
    @NamedQuery(name = "TarjetaSeUsaEnRuta.findByPinTarjeta", query = "SELECT t FROM TarjetaSeUsaEnRuta t WHERE t.tarjetaSeUsaEnRutaPK.pinTarjeta = :pinTarjeta"),
    @NamedQuery(name = "TarjetaSeUsaEnRuta.findByIdRuta", query = "SELECT t FROM TarjetaSeUsaEnRuta t WHERE t.tarjetaSeUsaEnRutaPK.idRuta = :idRuta"),
    @NamedQuery(name = "TarjetaSeUsaEnRuta.findByFecha", query = "SELECT t FROM TarjetaSeUsaEnRuta t WHERE t.tarjetaSeUsaEnRutaPK.fecha = :fecha")})
public class TarjetaSeUsaEnRuta implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected TarjetaSeUsaEnRutaPK tarjetaSeUsaEnRutaPK;
    @JoinColumn(name = "pin_tarjeta", referencedColumnName = "pin_tarjeta", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Tarjeta tarjeta;
    @JoinColumn(name = "id_ruta", referencedColumnName = "id_ruta", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Ruta ruta;

    public TarjetaSeUsaEnRuta() {
    }

    public TarjetaSeUsaEnRuta(TarjetaSeUsaEnRutaPK tarjetaSeUsaEnRutaPK) {
        this.tarjetaSeUsaEnRutaPK = tarjetaSeUsaEnRutaPK;
    }

    public TarjetaSeUsaEnRuta(int pinTarjeta, String idRuta, Date fecha) {
        this.tarjetaSeUsaEnRutaPK = new TarjetaSeUsaEnRutaPK(pinTarjeta, idRuta, fecha);
    }

    public TarjetaSeUsaEnRutaPK getTarjetaSeUsaEnRutaPK() {
        return tarjetaSeUsaEnRutaPK;
    }

    public void setTarjetaSeUsaEnRutaPK(TarjetaSeUsaEnRutaPK tarjetaSeUsaEnRutaPK) {
        this.tarjetaSeUsaEnRutaPK = tarjetaSeUsaEnRutaPK;
    }

    public Tarjeta getTarjeta() {
        return tarjeta;
    }

    public void setTarjeta(Tarjeta tarjeta) {
        this.tarjeta = tarjeta;
    }

    public Ruta getRuta() {
        return ruta;
    }

    public void setRuta(Ruta ruta) {
        this.ruta = ruta;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tarjetaSeUsaEnRutaPK != null ? tarjetaSeUsaEnRutaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TarjetaSeUsaEnRuta)) {
            return false;
        }
        TarjetaSeUsaEnRuta other = (TarjetaSeUsaEnRuta) object;
        if ((this.tarjetaSeUsaEnRutaPK == null && other.tarjetaSeUsaEnRutaPK != null) || (this.tarjetaSeUsaEnRutaPK != null && !this.tarjetaSeUsaEnRutaPK.equals(other.tarjetaSeUsaEnRutaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.TarjetaSeUsaEnRuta[ tarjetaSeUsaEnRutaPK=" + tarjetaSeUsaEnRutaPK + " ]";
    }
    
}
