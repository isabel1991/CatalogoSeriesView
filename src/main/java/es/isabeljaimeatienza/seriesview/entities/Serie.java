/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.isabeljaimeatienza.seriesview.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author cadit
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "SERIE")
@javax.persistence.NamedQueries({
    @javax.persistence.NamedQuery(name = "Serie.findAll", query = "SELECT s FROM Serie s"),
    @javax.persistence.NamedQuery(name = "Serie.findById", query = "SELECT s FROM Serie s WHERE s.id = :id"),
    @javax.persistence.NamedQuery(name = "Serie.findByT\u00edtulo", query = "SELECT s FROM Serie s WHERE s.t\u00edtulo = :t\u00edtulo"),
    @javax.persistence.NamedQuery(name = "Serie.findByFechaEstreno", query = "SELECT s FROM Serie s WHERE s.fechaEstreno = :fechaEstreno"),
    @javax.persistence.NamedQuery(name = "Serie.findByCapitulos", query = "SELECT s FROM Serie s WHERE s.capitulos = :capitulos"),
    @javax.persistence.NamedQuery(name = "Serie.findByPrecio", query = "SELECT s FROM Serie s WHERE s.precio = :precio"),
    @javax.persistence.NamedQuery(name = "Serie.findByValoracion", query = "SELECT s FROM Serie s WHERE s.valoracion = :valoracion"),
    @javax.persistence.NamedQuery(name = "Serie.findByVisionada", query = "SELECT s FROM Serie s WHERE s.visionada = :visionada"),
    @javax.persistence.NamedQuery(name = "Serie.findByFoto", query = "SELECT s FROM Serie s WHERE s.foto = :foto")})
public class Serie implements Serializable {

    private static final long serialVersionUID = 1L;
    @javax.persistence.Id
    @javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Column(name = "ID")
    private Integer id;
    @javax.persistence.Basic(optional = false)
    @javax.persistence.Column(name = "T\u00cdTULO")
    private String título;
    @javax.persistence.Column(name = "FECHA_ESTRENO")
    @javax.persistence.Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaEstreno;
    @javax.persistence.Column(name = "CAPITULOS")
    private Integer capitulos;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @javax.persistence.Column(name = "PRECIO")
    private BigDecimal precio;
    @javax.persistence.Column(name = "VALORACION")
    private Boolean valoracion;
    @javax.persistence.Column(name = "VISIONADA")
    private Boolean visionada;
    @javax.persistence.Column(name = "FOTO")
    private String foto;
    @javax.persistence.JoinColumn(name = "GENERO", referencedColumnName = "ID")
    @javax.persistence.ManyToOne(optional = false)
    private Genero genero;
    @javax.persistence.JoinColumn(name = "IDIOMA", referencedColumnName = "ID")
    @javax.persistence.ManyToOne(optional = false)
    private Idioma idioma;
    @javax.persistence.JoinColumn(name = "PAIS", referencedColumnName = "ID")
    @javax.persistence.ManyToOne(optional = false)
    private Nacionalidad pais;

    public Serie() {
    }

    public Serie(Integer id) {
        this.id = id;
    }

    public Serie(Integer id, String título) {
        this.id = id;
        this.título = título;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTítulo() {
        return título;
    }

    public void setTítulo(String título) {
        this.título = título;
    }

    public Date getFechaEstreno() {
        return fechaEstreno;
    }

    public void setFechaEstreno(Date fechaEstreno) {
        this.fechaEstreno = fechaEstreno;
    }

    public Integer getCapitulos() {
        return capitulos;
    }

    public void setCapitulos(Integer capitulos) {
        this.capitulos = capitulos;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public Boolean getValoracion() {
        return valoracion;
    }

    public void setValoracion(Boolean valoracion) {
        this.valoracion = valoracion;
    }

    public Boolean getVisionada() {
        return visionada;
    }

    public void setVisionada(Boolean visionada) {
        this.visionada = visionada;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public Idioma getIdioma() {
        return idioma;
    }

    public void setIdioma(Idioma idioma) {
        this.idioma = idioma;
    }

    public Nacionalidad getPais() {
        return pais;
    }

    public void setPais(Nacionalidad pais) {
        this.pais = pais;
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
        if (!(object instanceof Serie)) {
            return false;
        }
        Serie other = (Serie) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "es.isabeljaimeatienza.seriesview.entities.Serie[ id=" + id + " ]";
    }
    
}
