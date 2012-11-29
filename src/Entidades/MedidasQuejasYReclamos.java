/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jdavidva
 */
@Entity
@Table(name = "medidas_quejas_y_reclamos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MedidasQuejasYReclamos.findAll", query = "SELECT m FROM MedidasQuejasYReclamos m"),
    @NamedQuery(name = "MedidasQuejasYReclamos.findByNoTicket", query = "SELECT m FROM MedidasQuejasYReclamos m WHERE m.medidasQuejasYReclamosPK.noTicket = :noTicket"),
    @NamedQuery(name = "MedidasQuejasYReclamos.findByMedidaTomada", query = "SELECT m FROM MedidasQuejasYReclamos m WHERE m.medidasQuejasYReclamosPK.medidaTomada = :medidaTomada")})
public class MedidasQuejasYReclamos implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected MedidasQuejasYReclamosPK medidasQuejasYReclamosPK;
    @JoinColumn(name = "no_ticket", referencedColumnName = "no_ticket", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private QuejasYReclamos quejasYReclamos;

    public MedidasQuejasYReclamos() {
    }

    public MedidasQuejasYReclamos(MedidasQuejasYReclamosPK medidasQuejasYReclamosPK) {
        this.medidasQuejasYReclamosPK = medidasQuejasYReclamosPK;
    }

    public MedidasQuejasYReclamos(int noTicket, String medidaTomada) {
        this.medidasQuejasYReclamosPK = new MedidasQuejasYReclamosPK(noTicket, medidaTomada);
    }

    public MedidasQuejasYReclamosPK getMedidasQuejasYReclamosPK() {
        return medidasQuejasYReclamosPK;
    }

    public void setMedidasQuejasYReclamosPK(MedidasQuejasYReclamosPK medidasQuejasYReclamosPK) {
        this.medidasQuejasYReclamosPK = medidasQuejasYReclamosPK;
    }

    public QuejasYReclamos getQuejasYReclamos() {
        return quejasYReclamos;
    }

    public void setQuejasYReclamos(QuejasYReclamos quejasYReclamos) {
        this.quejasYReclamos = quejasYReclamos;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (medidasQuejasYReclamosPK != null ? medidasQuejasYReclamosPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MedidasQuejasYReclamos)) {
            return false;
        }
        MedidasQuejasYReclamos other = (MedidasQuejasYReclamos) object;
        if ((this.medidasQuejasYReclamosPK == null && other.medidasQuejasYReclamosPK != null) || (this.medidasQuejasYReclamosPK != null && !this.medidasQuejasYReclamosPK.equals(other.medidasQuejasYReclamosPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.MedidasQuejasYReclamos[ medidasQuejasYReclamosPK=" + medidasQuejasYReclamosPK + " ]";
    }
    
}
