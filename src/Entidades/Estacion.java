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
@Table(name = "estacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Estacion.findAll", query = "SELECT e FROM Estacion e"),
    @NamedQuery(name = "Estacion.findByIdEstacion", query = "SELECT e FROM Estacion e WHERE e.idEstacion = :idEstacion"),
    @NamedQuery(name = "Estacion.findByNombreEstacion", query = "SELECT e FROM Estacion e WHERE e.nombreEstacion = :nombreEstacion"),
    @NamedQuery(name = "Estacion.findByTipoEstacion", query = "SELECT e FROM Estacion e WHERE e.tipoEstacion = :tipoEstacion"),
    @NamedQuery(name = "Estacion.findByDireccionEstacion", query = "SELECT e FROM Estacion e WHERE e.direccionEstacion = :direccionEstacion")})
public class Estacion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_estacion")
    private String idEstacion;
    @Column(name = "nombre_estacion")
    private String nombreEstacion;
    @Column(name = "tipo_estacion")
    private String tipoEstacion;
    @Column(name = "direccion_estacion")
    private String direccionEstacion;
    @OneToMany(mappedBy = "idEstacion")
    private List<QuejasYReclamos> quejasYReclamosList;
    @OneToMany(mappedBy = "idEstacion")
    private List<Recarga> recargaList;
    @JoinColumn(name = "empleado_encargado", referencedColumnName = "id_empleado")
    @ManyToOne
    private Empleado empleadoEncargado;
    @OneToMany(mappedBy = "idEstacion")
    private List<VentaTarjeta> ventaTarjetaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "estacion")
    private List<TarjetaSeUsaEnEstacion> tarjetaSeUsaEnEstacionList;

    public Estacion() {
    }

    public Estacion(String idEstacion) {
        this.idEstacion = idEstacion;
    }

    public String getIdEstacion() {
        return idEstacion;
    }

    public void setIdEstacion(String idEstacion) {
        this.idEstacion = idEstacion;
    }

    public String getNombreEstacion() {
        return nombreEstacion;
    }

    public void setNombreEstacion(String nombreEstacion) {
        this.nombreEstacion = nombreEstacion;
    }

    public String getTipoEstacion() {
        return tipoEstacion;
    }

    public void setTipoEstacion(String tipoEstacion) {
        this.tipoEstacion = tipoEstacion;
    }

    public String getDireccionEstacion() {
        return direccionEstacion;
    }

    public void setDireccionEstacion(String direccionEstacion) {
        this.direccionEstacion = direccionEstacion;
    }

    @XmlTransient
    public List<QuejasYReclamos> getQuejasYReclamosList() {
        return quejasYReclamosList;
    }

    public void setQuejasYReclamosList(List<QuejasYReclamos> quejasYReclamosList) {
        this.quejasYReclamosList = quejasYReclamosList;
    }

    @XmlTransient
    public List<Recarga> getRecargaList() {
        return recargaList;
    }

    public void setRecargaList(List<Recarga> recargaList) {
        this.recargaList = recargaList;
    }

    public Empleado getEmpleadoEncargado() {
        return empleadoEncargado;
    }

    public void setEmpleadoEncargado(Empleado empleadoEncargado) {
        this.empleadoEncargado = empleadoEncargado;
    }

    @XmlTransient
    public List<VentaTarjeta> getVentaTarjetaList() {
        return ventaTarjetaList;
    }

    public void setVentaTarjetaList(List<VentaTarjeta> ventaTarjetaList) {
        this.ventaTarjetaList = ventaTarjetaList;
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
        hash += (idEstacion != null ? idEstacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Estacion)) {
            return false;
        }
        Estacion other = (Estacion) object;
        if ((this.idEstacion == null && other.idEstacion != null) || (this.idEstacion != null && !this.idEstacion.equals(other.idEstacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Estacion[ idEstacion=" + idEstacion + " ]";
    }
    
}
