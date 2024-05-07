package edu.tasklynx.tasklynxspringboot.models.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "trabajador")
@NamedQueries({
        @NamedQuery(name = "Trabajador.findByNameAndPass", 
                query = "SELECT t FROM Trabajador t WHERE t.nombre = :nombre AND t.contraseña = :contraseña")
})
public class Trabajador {
    @Id
    @NotEmpty(message = "El campo Id no puede estar vacío")
    @Size(max = 5, message = "El tamaño máximo del ID es 5")
    @Column(name = "id_trabajador", nullable = false, length = 5, unique = true)
    private String idTrabajador;

    @NotEmpty(message = "El campo DNI no puede estar vacío")
    @Size(max = 9, message = "El tamaño máximo del DNI es 9")
    @Column(name = "dni", nullable = false, length = 9)
    private String dni;

    @Column(name = "nombre", length = 100)
    @Size(max = 100, message = "El tamaño máximo del nombre es 100")
    private String nombre;

    @Column(name = "apellidos", length = 100)
    @Size(max = 100, message = "El tamaño máximo de los apellidos es 100")
    private String apellidos;

    @NotEmpty(message = "El campo especialidad no puede estar vacío")
    @Size(max = 50, message = "El tamaño máximo de la especialidad es 50")
    @Column(name = "especialidad", nullable = false, length = 50)
    private String especialidad;

    @NotEmpty(message = "El campo contraseña no puede estar vacío")
    @Size(max = 50, message = "El tamaño máximo de la contraseña es 50")
    @Column(name = "\"contraseña\"", nullable = false, length = 50)
    private String contraseña;

    @Column(name = "email", length = 150)
    @Email(message = "El email no es válido")
    @Size(max = 150, message = "El tamaño máximo del email es 150")
    private String email;

    @OneToMany(mappedBy = "idTrabajador")
    @JsonManagedReference
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