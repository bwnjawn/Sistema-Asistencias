package com.asistencia.model;

/**
 * Clase Modelo que representa la tabla 'usuario' de la base de datos.
 * Sigue el patrón Java Bean (atributos privados, constructores, getters/setters).
 */
public class Usuario {

    // Atributos (coinciden con las columnas de la tabla 'usuario')
    private int idUsuario;
    private String rut;
    private String nombre;
    private String apellido;
    private String email;
    private String password; // Representa el 'password_hash' de la DB
    private int idRol;       // 1=Admin, 2=Profesor, 3=Alumno

    // 1. Constructor Vacío (Obligatorio para frameworks y JSP)
    public Usuario() {
    }

    // 2. Constructor Completo (Para crear objetos fácilmente desde la DB)
    public Usuario(int idUsuario, String rut, String nombre, String apellido, String email, String password, int idRol) {
        this.idUsuario = idUsuario;
        this.rut = rut;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.password = password;
        this.idRol = idRol;
    }

    // 3. Constructor sin ID (Para registrar nuevos usuarios, ya que la DB genera el ID)
    public Usuario(String rut, String nombre, String apellido, String email, String password, int idRol) {
        this.rut = rut;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.password = password;
        this.idRol = idRol;
    }

    // --- Getters y Setters (Para acceder y modificar los datos) ---

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    // Método toString() opcional para imprimir el objeto en consola y debuggear
    @Override
    public String toString() {
        return "Usuario [id=" + idUsuario + ", nombre=" + nombre + " " + apellido + ", rol=" + idRol + "]";
    }
}