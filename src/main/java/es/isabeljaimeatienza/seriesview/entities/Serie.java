/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.isabeljaimeatienza.seriesview.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author cadit
 */
@Entity
@Table(name = "SERIE")
@NamedQueries({
    @NamedQuery(name = "Serie.findAll", query = "SELECT s FROM Serie s"),
    @NamedQuery(name = "Serie.findById", query = "SELECT s FROM Serie s WHERE s.id = :id"),
    @NamedQuery(name = "Serie.findByT\u00edtulo", query = "SELECT s FROM Serie s WHERE s.t\u00edtulo = :t\u00edtulo"),
    @NamedQuery(name = "Serie.findByFechaEstreno", query = "SELECT s FROM Serie s WHERE s.fechaEstreno = :fechaEstreno"),
    @NamedQuery(name = "Serie.findByCapitulos", query = "SELECT s FROM Serie s WHERE s.capitulos = :capitulos"),
    @NamedQuery(name = "Serie.findByPrecio", query = "SELECT s FROM Serie s WHERE s.precio = :precio"),
    @NamedQuery(name = "Serie.findByValoracion", query = "SELECT s FROM Serie s WHERE s.valoracion = :valoracion"),
    @NamedQuery(name = "Serie.findByVisionada", query = "SELECT s FROM Serie s WHERE s.visionada = :visionada"),
    @NamedQuery(name = "Serie.findByFoto", query = "SELECT s FROM Serie s WHERE s.foto = :foto")})
public class Serie implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "T\u00cdTULO")
    private String título;
    @Column(name = "FECHA_ESTRENO")
    @Temporal(TemporalType.DATE)
    private Date fechaEstreno;
    @Column(name = "CAPITULOS")
    private Integer capitulos;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "PRECIO")
    private BigDecimal precio;
    @Column(name = "VALORACION")
    private Character valoracion;
    @Column(name = "VISIONADA")
    private Boolean visionada;
    @Column(name = "FOTO")
    private String foto;
    @JoinColumn(name = "GENERO", referencedColumnName = "ID")
    @ManyToOne
    private Genero genero;
    @JoinColumn(name = "IDIOMA", referencedColumnName = "ID")
    @ManyToOne
    private Idioma idioma;
    @JoinColumn(name = "PAIS", referencedColumnName = "ID")
    @ManyToOne
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

    public Character getValoracion() {
        return valoracion;
    }

    public void setValoracion(Character valoracion) {
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
