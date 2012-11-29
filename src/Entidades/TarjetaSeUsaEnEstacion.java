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
@Table(name = "tarjeta_se_usa_en_estacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TarjetaSeUsaEnEstacion.findAll", query = "SELECT t FROM TarjetaSeUsaEnEstacion t"),
    @NamedQuery(name = "TarjetaSeUsaEnEstacion.findByPinTarjeta", query = "SELECT t FROM TarjetaSeUsaEnEstacion t WHERE t.tarjetaSeUsaEnEstacionPK.pinTarjeta = :pinTarjeta"),
    @NamedQuery(name = "TarjetaSeUsaEnEstacion.findByIdEstacion", query = "SELECT t FROM TarjetaSeUsaEnEstacion t WHERE t.tarjetaSeUsaEnEstacionPK.idEstacion = :idEstacion"),
    @NamedQuery(name = "TarjetaSeUsaEnEstacion.findByFecha", query = "SELECT t FROM TarjetaSeUsaEnEstacion t WHERE t.tarjetaSeUsaEnEstacionPK.fecha = :fecha")})
public class TarjetaSeUsaEnEstacion implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected TarjetaSeUsaEnEstacionPK tarjetaSeUsaEnEstacionPK;
    @JoinColumn(name = "pin_tarjeta", referencedColumnName = "pin_tarjeta", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Tarjeta tarjeta;
    @JoinColumn(name = "id_estacion", referencedColumnName = "id_estacion", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Estacion estacion;

    public TarjetaSeUsaEnEstacion() {
    }

    public TarjetaSeUsaEnEstacion(TarjetaSeUsaEnEstacionPK tarjetaSeUsaEnEstacionPK) {
        this.tarjetaSeUsaEnEstacionPK = tarjetaSeUsaEnEstacionPK;
    }

    public TarjetaSeUsaEnEstacion(int pinTarjeta, String idEstacion, Date fecha) {
        this.tarjetaSeUsaEnEstacionPK = new TarjetaSeUsaEnEstacionPK(pinTarjeta, idEstacion, fecha);
    }

    public TarjetaSeUsaEnEstacionPK getTarjetaSeUsaEnEstacionPK() {
        return tarjetaSeUsaEnEstacionPK;
    }

    public void setTarjetaSeUsaEnEstacionPK(TarjetaSeUsaEnEstacionPK tarjetaSeUsaEnEstacionPK) {
        this.tarjetaSeUsaEnEstacionPK = tarjetaSeUsaEnEstacionPK;
    }

    public Tarjeta getTarjeta() {
        return tarjeta;
    }

    public void setTarjeta(Tarjeta tarjeta) {
        this.tarjeta = tarjeta;
    }

    public Estacion getEstacion() {
        return estacion;
    }

    public void setEstacion(Estacion estacion) {
        this.estacion = estacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tarjetaSeUsaEnEstacionPK != null ? tarjetaSeUsaEnEstacionPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TarjetaSeUsaEnEstacion)) {
            return false;
        }
        TarjetaSeUsaEnEstacion other = (TarjetaSeUsaEnEstacion) object;
        if ((this.tarjetaSeUsaEnEstacionPK == null && other.tarjetaSeUsaEnEstacionPK != null) || (this.tarjetaSeUsaEnEstacionPK != null && !this.tarjetaSeUsaEnEstacionPK.equals(other.tarjetaSeUsaEnEstacionPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.TarjetaSeUsaEnEstacion[ tarjetaSeUsaEnEstacionPK=" + tarjetaSeUsaEnEstacionPK + " ]";
    }
    
}
