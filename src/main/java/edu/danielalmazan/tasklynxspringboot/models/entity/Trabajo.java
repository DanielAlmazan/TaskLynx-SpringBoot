package edu.danielalmazan.tasklynxspringboot.models.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "trabajo")
public class Trabajo {
    @Id
    @Column(name = "cod_trabajo", nullable = false, length = 5)
    private String codTrabajo;

    @Column(name = "categoria", nullable = false, length = 50)
    private String categoria;

    @Column(name = "descripcion", nullable = false, length = 500)
    private String descripcion;

    @Column(name = "fec_ini", nullable = false)
    private LocalDate fecIni;

    @Column(name = "fec_fin")
    private LocalDate fecFin;

    @Column(name = "tiempo", precision = 4, scale = 1)
    private BigDecimal tiempo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_trabajador")
    private Trabajador idTrabajador;

    @Column(name = "prioridad", nullable = false, precision = 1)
    private BigDecimal prioridad;

    public String getCodTrabajo() {
        return codTrabajo;
    }

    public void setCodTrabajo(String codTrabajo) {
        this.codTrabajo = codTrabajo;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFecIni() {
        return fecIni;
    }

    public void setFecIni(LocalDate fecIni) {
        this.fecIni = fecIni;
    }

    public LocalDate getFecFin() {
        return fecFin;
    }

    public void setFecFin(LocalDate fecFin) {
        this.fecFin = fecFin;
    }

    public BigDecimal getTiempo() {
        return tiempo;
    }

    public void setTiempo(BigDecimal tiempo) {
        this.tiempo = tiempo;
    }

    public Trabajador getIdTrabajador() {
        return idTrabajador;
    }

    public void setIdTrabajador(Trabajador idTrabajador) {
        this.idTrabajador = idTrabajador;
    }

    public BigDecimal getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(BigDecimal prioridad) {
        this.prioridad = prioridad;
    }

}