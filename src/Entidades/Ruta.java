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
@Table(name = "ruta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ruta.findAll", query = "SELECT r FROM Ruta r"),
    @NamedQuery(name = "Ruta.findByIdRuta", query = "SELECT r FROM Ruta r WHERE r.idRuta = :idRuta"),
    @NamedQuery(name = "Ruta.findByTipoRuta", query = "SELECT r FROM Ruta r WHERE r.tipoRuta = :tipoRuta"),
    @NamedQuery(name = "Ruta.findByParadas", query = "SELECT r FROM Ruta r WHERE r.paradas = :paradas")})
public class Ruta implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_ruta")
    private String idRuta;
    @Column(name = "tipo_ruta")
    private String tipoRuta;
    @Column(name = "paradas")
    private String paradas;
    @OneToMany(mappedBy = "rutaAsignada")
    private List<Bus> busList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ruta")
    private List<TarjetaSeUsaEnRuta> tarjetaSeUsaEnRutaList;
    @OneToMany(mappedBy = "idRuta")
    private List<Turno> turnoList;

    public Ruta() {
    }

    public Ruta(String idRuta) {
        this.idRuta = idRuta;
    }

    public String getIdRuta() {
        return idRuta;
    }

    public void setIdRuta(String idRuta) {
        this.idRuta = idRuta;
    }

    public String getTipoRuta() {
        return tipoRuta;
    }

    public void setTipoRuta(String tipoRuta) {
        this.tipoRuta = tipoRuta;
    }

    public String getParadas() {
        return paradas;
    }

    public void setParadas(String paradas) {
        this.paradas = paradas;
    }

    @XmlTransient
    public List<Bus> getBusList() {
        return busList;
    }

    public void setBusList(List<Bus> busList) {
        this.busList = busList;
    }

    @XmlTransient
    public List<TarjetaSeUsaEnRuta> getTarjetaSeUsaEnRutaList() {
        return tarjetaSeUsaEnRutaList;
    }

    public void setTarjetaSeUsaEnRutaList(List<TarjetaSeUsaEnRuta> tarjetaSeUsaEnRutaList) {
        this.tarjetaSeUsaEnRutaList = tarjetaSeUsaEnRutaList;
    }

    @XmlTransient
    public List<Turno> getTurnoList() {
        return turnoList;
    }

    public void setTurnoList(List<Turno> turnoList) {
        this.turnoList = turnoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRuta != null ? idRuta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ruta)) {
            return false;
        }
        Ruta other = (Ruta) object;
        if ((this.idRuta == null && other.idRuta != null) || (this.idRuta != null && !this.idRuta.equals(other.idRuta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Ruta[ idRuta=" + idRuta + " ]";
    }
    
}
