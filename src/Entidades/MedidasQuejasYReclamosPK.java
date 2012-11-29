/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author jdavidva
 */
@Embeddable
public class MedidasQuejasYReclamosPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "no_ticket")
    private int noTicket;
    @Basic(optional = false)
    @Column(name = "medida_tomada")
    private String medidaTomada;

    public MedidasQuejasYReclamosPK() {
    }

    public MedidasQuejasYReclamosPK(int noTicket, String medidaTomada) {
        this.noTicket = noTicket;
        this.medidaTomada = medidaTomada;
    }

    public int getNoTicket() {
        return noTicket;
    }

    public void setNoTicket(int noTicket) {
        this.noTicket = noTicket;
    }

    public String getMedidaTomada() {
        return medidaTomada;
    }

    public void setMedidaTomada(String medidaTomada) {
        this.medidaTomada = medidaTomada;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) noTicket;
        hash += (medidaTomada != null ? medidaTomada.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MedidasQuejasYReclamosPK)) {
            return false;
        }
        MedidasQuejasYReclamosPK other = (MedidasQuejasYReclamosPK) object;
        if (this.noTicket != other.noTicket) {
            return false;
        }
        if ((this.medidaTomada == null && other.medidaTomada != null) || (this.medidaTomada != null && !this.medidaTomada.equals(other.medidaTomada))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.MedidasQuejasYReclamosPK[ noTicket=" + noTicket + ", medidaTomada=" + medidaTomada + " ]";
    }
    
}
