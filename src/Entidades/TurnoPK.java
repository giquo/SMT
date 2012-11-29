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
public class TurnoPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "id_bus")
    private String idBus;
    @Basic(optional = false)
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Basic(optional = false)
    @Column(name = "jornada")
    private String jornada;

    public TurnoPK() {
    }

    public TurnoPK(String idBus, Date fecha, String jornada) {
        this.idBus = idBus;
        this.fecha = fecha;
        this.jornada = jornada;
    }

    public String getIdBus() {
        return idBus;
    }

    public void setIdBus(String idBus) {
        this.idBus = idBus;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getJornada() {
        return jornada;
    }

    public void setJornada(String jornada) {
        this.jornada = jornada;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idBus != null ? idBus.hashCode() : 0);
        hash += (fecha != null ? fecha.hashCode() : 0);
        hash += (jornada != null ? jornada.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TurnoPK)) {
            return false;
        }
        TurnoPK other = (TurnoPK) object;
        if ((this.idBus == null && other.idBus != null) || (this.idBus != null && !this.idBus.equals(other.idBus))) {
            return false;
        }
        if ((this.fecha == null && other.fecha != null) || (this.fecha != null && !this.fecha.equals(other.fecha))) {
            return false;
        }
        if ((this.jornada == null && other.jornada != null) || (this.jornada != null && !this.jornada.equals(other.jornada))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.TurnoPK[ idBus=" + idBus + ", fecha=" + fecha + ", jornada=" + jornada + " ]";
    }
    
}
