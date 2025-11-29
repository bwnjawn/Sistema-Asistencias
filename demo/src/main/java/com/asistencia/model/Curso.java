package com.asistencia.model;

/**
 * Clase que representa la tabla 'curso' de la base de datos.
 */
public class Curso {
    private int idCurso;
    private String nombre;
    private String seccion; // Ej: "001D"

    // Constructor vacío
    public Curso() {
    }

    // Constructor completo (con ID)
    public Curso(int idCurso, String nombre, String seccion) {
        this.idCurso = idCurso;
        this.nombre = nombre;
        this.seccion = seccion;
    }

    // Constructor sin ID (para insertar nuevos)
    public Curso(String nombre, String seccion) {
        this.nombre = nombre;
        this.seccion = seccion;
    }

    // Getters y Setters
    public int getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(int idCurso) {
        this.idCurso = idCurso;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSeccion() {
        return seccion;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }
}
