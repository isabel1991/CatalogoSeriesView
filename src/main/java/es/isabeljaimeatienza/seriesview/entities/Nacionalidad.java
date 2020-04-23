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
@javax.persistence.Table(name = "NACIONALIDAD")
@javax.persistence.NamedQueries({
    @javax.persistence.NamedQuery(name = "Nacionalidad.findAll", query = "SELECT n FROM Nacionalidad n"),
    @javax.persistence.NamedQuery(name = "Nacionalidad.findById", query = "SELECT n FROM Nacionalidad n WHERE n.id = :id"),
    @javax.persistence.NamedQuery(name = "Nacionalidad.findByPais", query = "SELECT n FROM Nacionalidad n WHERE n.pais = :pais")})
public class Nacionalidad implements Serializable {

    private static final long serialVersionUID = 1L;
    @javax.persistence.Id
    @javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Column(name = "ID")
    private Integer id;
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Column(name = "PAIS")
    private String pais;
    @javax.persistence.OneToMany(cascade = javax.persistence.CascadeType.ALL, mappedBy = "pais")
    private Collection<Serie> serieCollection;

    public Nacionalidad() {
    }

    public Nacionalidad(Integer id) {
        this.id = id;
    }

    public Nacionalidad(Integer id, String pais) {
        this.id = id;
        this.pais = pais;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
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
        if (!(object instanceof Nacionalidad)) {
            return false;
        }
        Nacionalidad other = (Nacionalidad) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.isabeljaimeatienza.seriesview.entities.Nacionalidad[ id=" + id + " ]";
    }
    
}
