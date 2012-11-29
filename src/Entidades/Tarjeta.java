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
@Table(name = "tarjeta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tarjeta.findAll", query = "SELECT t FROM Tarjeta t"),
    @NamedQuery(name = "Tarjeta.findByPinTarjeta", query = "SELECT t FROM Tarjeta t WHERE t.pinTarjeta = :pinTarjeta"),
    @NamedQuery(name = "Tarjeta.findByTipoTarjeta", query = "SELECT t FROM Tarjeta t WHERE t.tipoTarjeta = :tipoTarjeta"),
    @NamedQuery(name = "Tarjeta.findByEstadoTarjeta", query = "SELECT t FROM Tarjeta t WHERE t.estadoTarjeta = :estadoTarjeta"),
    @NamedQuery(name = "Tarjeta.findBySaldo", query = "SELECT t FROM Tarjeta t WHERE t.saldo = :saldo")})
public class Tarjeta implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "pin_tarjeta")
    private Integer pinTarjeta;
    @Column(name = "tipo_tarjeta")
    private String tipoTarjeta;
    @Column(name = "estado_tarjeta")
    private String estadoTarjeta;
    @Column(name = "saldo")
    private Integer saldo;
    @OneToMany(mappedBy = "pinTarjeta")
    private List<Recarga> recargaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tarjeta")
    private List<TarjetaSeUsaEnRuta> tarjetaSeUsaEnRutaList;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "tarjeta")
    private VentaTarjeta ventaTarjeta;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tarjeta")
    private List<TarjetaSeUsaEnEstacion> tarjetaSeUsaEnEstacionList;

    public Tarjeta() {
    }

    public Tarjeta(Integer pinTarjeta) {
        this.pinTarjeta = pinTarjeta;
    }

    public Integer getPinTarjeta() {
        return pinTarjeta;
    }

    public void setPinTarjeta(Integer pinTarjeta) {
        this.pinTarjeta = pinTarjeta;
    }

    public String getTipoTarjeta() {
        return tipoTarjeta;
    }

    public void setTipoTarjeta(String tipoTarjeta) {
        this.tipoTarjeta = tipoTarjeta;
    }

    public String getEstadoTarjeta() {
        return estadoTarjeta;
    }

    public void setEstadoTarjeta(String estadoTarjeta) {
        this.estadoTarjeta = estadoTarjeta;
    }

    public Integer getSaldo() {
        return saldo;
    }

    public void setSaldo(Integer saldo) {
        this.saldo = saldo;
    }

    @XmlTransient
    public List<Recarga> getRecargaList() {
        return recargaList;
    }

    public void setRecargaList(List<Recarga> recargaList) {
        this.recargaList = recargaList;
    }

    @XmlTransient
    public List<TarjetaSeUsaEnRuta> getTarjetaSeUsaEnRutaList() {
        return tarjetaSeUsaEnRutaList;
    }

    public void setTarjetaSeUsaEnRutaList(List<TarjetaSeUsaEnRuta> tarjetaSeUsaEnRutaList) {
        this.tarjetaSeUsaEnRutaList = tarjetaSeUsaEnRutaList;
    }

    public VentaTarjeta getVentaTarjeta() {
        return ventaTarjeta;
    }

    public void setVentaTarjeta(VentaTarjeta ventaTarjeta) {
        this.ventaTarjeta = ventaTarjeta;
    }

    @XmlTransient
    public List<TarjetaSeUsaEnEstacion> getTarjetaSeUsaEnEstacionList() {
        return tarjetaSeUsaEnEstacionList;
    }

    public void setTarjetaSeUsaEnEstacionList(List<TarjetaSeUsaEnEstacion> tarjetaSeUsaEnEstacionList) {
        this.tarjetaSeUsaEnEstacionList = tarjetaSeUsaEnEstacionList;
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
        if (!(object instanceof Tarjeta)) {
            return false;
        }
        Tarjeta other = (Tarjeta) object;
        if ((this.pinTarjeta == null && other.pinTarjeta != null) || (this.pinTarjeta != null && !this.pinTarjeta.equals(other.pinTarjeta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Tarjeta[ pinTarjeta=" + pinTarjeta + " ]";
    }
    
}
