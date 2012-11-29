/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author jdavidva
 */
@Entity
@Table(name = "empleado")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Empleado.findAll", query = "SELECT e FROM Empleado e"),
    @NamedQuery(name = "Empleado.findByIdEmpleado", query = "SELECT e FROM Empleado e WHERE e.idEmpleado = :idEmpleado"),
    @NamedQuery(name = "Empleado.findByNombreEmpleado", query = "SELECT e FROM Empleado e WHERE e.nombreEmpleado = :nombreEmpleado"),
    @NamedQuery(name = "Empleado.findByFechaNac", query = "SELECT e FROM Empleado e WHERE e.fechaNac = :fechaNac"),
    @NamedQuery(name = "Empleado.findByEstratoEmpleado", query = "SELECT e FROM Empleado e WHERE e.estratoEmpleado = :estratoEmpleado"),
    @NamedQuery(name = "Empleado.findByGeneroEmpleado", query = "SELECT e FROM Empleado e WHERE e.generoEmpleado = :generoEmpleado"),
    @NamedQuery(name = "Empleado.findByCargo", query = "SELECT e FROM Empleado e WHERE e.cargo = :cargo"),
    @NamedQuery(name = "Empleado.findByDireccionEmpleado", query = "SELECT e FROM Empleado e WHERE e.direccionEmpleado = :direccionEmpleado"),
    @NamedQuery(name = "Empleado.findBySalario", query = "SELECT e FROM Empleado e WHERE e.salario = :salario"),
    @NamedQuery(name = "Empleado.findByTelefonoEmpleado", query = "SELECT e FROM Empleado e WHERE e.telefonoEmpleado = :telefonoEmpleado")})
public class Empleado implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_empleado")
    private String idEmpleado;
    @Column(name = "nombre_empleado")
    private String nombreEmpleado;
    @Column(name = "fecha_nac")
    @Temporal(TemporalType.DATE)
    private Date fechaNac;
    @Column(name = "estrato_empleado")
    private Integer estratoEmpleado;
    @Column(name = "genero_empleado")
    private String generoEmpleado;
    @Column(name = "cargo")
    private String cargo;
    @Column(name = "direccion_empleado")
    private String direccionEmpleado;
    @Column(name = "salario")
    private Integer salario;
    @Column(name = "telefono_empleado")
    private String telefonoEmpleado;
    @OneToMany(mappedBy = "idEmpleado")
    private List<QuejasYReclamos> quejasYReclamosList;
    @OneToMany(mappedBy = "idEmpleado")
    private List<Recarga> recargaList;
    @OneToMany(mappedBy = "empleadoEncargado")
    private List<Estacion> estacionList;
    @OneToMany(mappedBy = "idEmpleado")
    private List<Turno> turnoList;
    @OneToMany(mappedBy = "idEmpleado")
    private List<VentaTarjeta> ventaTarjetaList;

    public Empleado() {
    }

    public Empleado(String idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(String idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getNombreEmpleado() {
        return nombreEmpleado;
    }

    public void setNombreEmpleado(String nombreEmpleado) {
        this.nombreEmpleado = nombreEmpleado;
    }

    public Date getFechaNac() {
        return fechaNac;
    }

    public void setFechaNac(Date fechaNac) {
        this.fechaNac = fechaNac;
    }

    public Integer getEstratoEmpleado() {
        return estratoEmpleado;
    }

    public void setEstratoEmpleado(Integer estratoEmpleado) {
        this.estratoEmpleado = estratoEmpleado;
    }

    public String getGeneroEmpleado() {
        return generoEmpleado;
    }

    public void setGeneroEmpleado(String generoEmpleado) {
        this.generoEmpleado = generoEmpleado;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getDireccionEmpleado() {
        return direccionEmpleado;
    }

    public void setDireccionEmpleado(String direccionEmpleado) {
        this.direccionEmpleado = direccionEmpleado;
    }

    public Integer getSalario() {
        return salario;
    }

    public void setSalario(Integer salario) {
        this.salario = salario;
    }

    public String getTelefonoEmpleado() {
        return telefonoEmpleado;
    }

    public void setTelefonoEmpleado(String telefonoEmpleado) {
        this.telefonoEmpleado = telefonoEmpleado;
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

    @XmlTransient
    public List<Estacion> getEstacionList() {
        return estacionList;
    }

    public void setEstacionList(List<Estacion> estacionList) {
        this.estacionList = estacionList;
    }

    @XmlTransient
    public List<Turno> getTurnoList() {
        return turnoList;
    }

    public void setTurnoList(List<Turno> turnoList) {
        this.turnoList = turnoList;
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
        hash += (idEmpleado != null ? idEmpleado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Empleado)) {
            return false;
        }
        Empleado other = (Empleado) object;
        if ((this.idEmpleado == null && other.idEmpleado != null) || (this.idEmpleado != null && !this.idEmpleado.equals(other.idEmpleado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Empleado[ idEmpleado=" + idEmpleado + " ]";
    }
    
}
