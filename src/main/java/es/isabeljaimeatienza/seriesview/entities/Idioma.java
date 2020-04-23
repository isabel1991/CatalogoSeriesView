/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.isabeljaimeatienza.seriesview.entities;

import java.io.Serializable;
import java.util.Collection;

/**
 *
 * @author cadit
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "IDIOMA")
@javax.persistence.NamedQueries({
    @javax.persistence.NamedQuery(name = "Idioma.findAll", query = "SELECT i FROM Idioma i"),
    @javax.persistence.NamedQuery(name = "Idioma.findById", query = "SELECT i FROM Idioma i WHERE i.id = :id"),
    @javax.persistence.NamedQuery(name = "Idioma.findByCodigo", query = "SELECT i FROM Idioma i WHERE i.codigo = :codigo"),
    @javax.persistence.NamedQuery(name = "Idioma.findByNombre", query = "SELECT i FROM Idioma i WHERE i.nombre = :nombre")})
public class Idioma implements Serializable {

    private static final long serialVersionUID = 1L;
    @javax.persistence.Id
    @javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Column(name = "ID")
    private Integer id;
    @javax.persistence.Column(name = "CODIGO")
    private String codigo;
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Column(name = "NOMBRE")
    private String nombre;
    @javax.persistence.OneToMany(cascade = javax.persistence.CascadeType.ALL, mappedBy = "idioma")
    private Collection<Serie> serieCollection;

    public Idioma() {
    }

    public Idioma(Integer id) {
        this.id = id;
    }

    public Idioma(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Collection<Serie> getSerieCollection() {
        return serieCollection;
    }

    public void setSerieCollection(Collection<Serie> serieCollection) {
        this.serieCollection = serieCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Idioma)) {
            return false;
        }
        Idioma other = (Idioma) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.isabeljaimeatienza.seriesview.entities.Idioma[ id=" + id + " ]";
    }
    
}
