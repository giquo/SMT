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
@Table(name = "pasajero")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pasajero.findAll", query = "SELECT p FROM Pasajero p"),
    @NamedQuery(name = "Pasajero.findByIdPasajero", query = "SELECT p FROM Pasajero p WHERE p.idPasajero = :idPasajero"),
    @NamedQuery(name = "Pasajero.findByNombrePasajero", query = "SELECT p FROM Pasajero p WHERE p.nombrePasajero = :nombrePasajero"),
    @NamedQuery(name = "Pasajero.findByEstratoPasajero", query = "SELECT p FROM Pasajero p WHERE p.estratoPasajero = :estratoPasajero"),
    @NamedQuery(name = "Pasajero.findByDireccionPasajero", query = "SELECT p FROM Pasajero p WHERE p.direccionPasajero = :direccionPasajero"),
    @NamedQuery(name = "Pasajero.findByTelefonoPasajero", query = "SELECT p FROM Pasajero p WHERE p.telefonoPasajero = :telefonoPasajero"),
    @NamedQuery(name = "Pasajero.findByGeneroPasajero", query = "SELECT p FROM Pasajero p WHERE p.generoPasajero = :generoPasajero")})
public class Pasajero implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_pasajero")
    private String idPasajero;
    @Column(name = "nombre_pasajero")
    private String nombrePasajero;
    @Column(name = "estrato_pasajero")
    private Integer estratoPasajero;
    @Column(name = "direccion_pasajero")
    private String direccionPasajero;
    @Column(name = "telefono_pasajero")
    private String telefonoPasajero;
    @Column(name = "genero_pasajero")
    private String generoPasajero;
    @OneToMany(mappedBy = "idPasajero")
    private List<QuejasYReclamos> quejasYReclamosList;
    @OneToMany(mappedBy = "idPasajero")
    private List<VentaTarjeta> ventaTarjetaList;

    public Pasajero() {
    }

    public Pasajero(String idPasajero) {
        this.idPasajero = idPasajero;
    }

    public String getIdPasajero() {
        return idPasajero;
    }

    public void setIdPasajero(String idPasajero) {
        this.idPasajero = idPasajero;
    }

    public String getNombrePasajero() {
        return nombrePasajero;
    }

    public void setNombrePasajero(String nombrePasajero) {
        this.nombrePasajero = nombrePasajero;
    }

    public Integer getEstratoPasajero() {
        return estratoPasajero;
    }

    public void setEstratoPasajero(Integer estratoPasajero) {
        this.estratoPasajero = estratoPasajero;
    }

    public String getDireccionPasajero() {
        return direccionPasajero;
    }

    public void setDireccionPasajero(String direccionPasajero) {
        this.direccionPasajero = direccionPasajero;
    }

    public String getTelefonoPasajero() {
        return telefonoPasajero;
    }

    public void setTelefonoPasajero(String telefonoPasajero) {
        this.telefonoPasajero = telefonoPasajero;
    }

    public String getGeneroPasajero() {
        return generoPasajero;
    }

    public void setGeneroPasajero(String generoPasajero) {
        this.generoPasajero = generoPasajero;
    }

    @XmlTransient
    public List<QuejasYReclamos> getQuejasYReclamosList() {
        return quejasYReclamosList;
    }

    public void setQuejasYReclamosList(List<QuejasYReclamos> quejasYReclamosList) {
        this.quejasYReclamosList = quejasYReclamosList;
    }

    @XmlTransient
    public List<VentaTarjeta> getVentaTarjetaList() {
        return ventaTarjetaList;
    }

    public void setVentaTarjetaList(List<VentaTarjeta> ventaTarjetaList) {
        this.ventaTarjetaList = ventaTarjetaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPasajero != null ? idPasajero.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pasajero)) {
            return false;
        }
        Pasajero other = (Pasajero) object;
        if ((this.idPasajero == null && other.idPasajero != null) || (this.idPasajero != null && !this.idPasajero.equals(other.idPasajero))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Pasajero[ idPasajero=" + idPasajero + " ]";
    }
    
}
