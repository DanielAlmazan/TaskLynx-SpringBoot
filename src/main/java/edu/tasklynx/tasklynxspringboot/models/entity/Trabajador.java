package edu.tasklynx.tasklynxspringboot.models.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "trabajador")
public class Trabajador {
    @Id
    @NotEmpty(message = "no puede estar vacío")
    @Column(name = "id_trabajador", nullable = false, length = 5)
    private String idTrabajador;

    @NotEmpty(message = "no puede estar vacío")
    @Column(name = "dni", nullable = false, length = 9)
    private String dni;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "apellidos", nullable = false, length = 100)
    private String apellidos;

    @NotEmpty(message = "no puede estar vacío")
    @Column(name = "especialidad", nullable = false, length = 50)
    private String especialidad;

    @NotEmpty(message = "no puede estar vacío")
    @Column(name = "\"contraseña\"", nullable = false, length = 50)
    private String contraseña;

    @Column(name = "email", nullable = false, length = 150)
    private String email;

    @OneToMany(mappedBy = "idTrabajador")
    private Set<Trabajo> trabajos = new LinkedHashSet<>();

    public Trabajador() {
    }

    public Trabajador(
            String dni,
            String nombre,
            String apellidos,
            String email,
            String contraseña,
            String especialidad
    ) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.especialidad = especialidad;
        this.contraseña = contraseña;
        this.email = email;
    }

    public String getIdTrabajador() {
        return idTrabajador;
    }

    public void setIdTrabajador(String idTrabajador) {
        this.idTrabajador = idTrabajador;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Trabajo> getTrabajos() {
        return trabajos;
    }

    public void setTrabajos(Set<Trabajo> trabajos) {
        this.trabajos = trabajos;
    }

}