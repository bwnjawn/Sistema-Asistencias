package com.asistencia.model;

public class Curso {

    private int idCurso;
    private String codigo;      // Ej: "PGY4121"
    private String nombre;      // Ej: "Programación de Aplicaciones"
    private int idProfesor;     // ID del usuario con rol profesor asignado

    // 1. Constructor Vacío
    public Curso() {
    }

    // 2. Constructor Completo
    public Curso(int idCurso, String codigo, String nombre, int idProfesor) {
        this.idCurso = idCurso;
        this.codigo = codigo;
        this.nombre = nombre;
        this.idProfesor = idProfesor;
    }

    // 3. Constructor sin ID (para crear nuevos cursos)
    public Curso(String codigo, String nombre, int idProfesor) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.idProfesor = idProfesor;
    }

    // --- Getters y Setters ---

    public int getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(int idCurso) {
        this.idCurso = idCurso;
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

    public int getIdProfesor() {
        return idProfesor;
    }

    public void setIdProfesor(int idProfesor) {
        this.idProfesor = idProfesor;
    }

    @Override
    public String toString() {
        return "Curso [id=" + idCurso + ", codigo=" + codigo + ", nombre=" + nombre + "]";
    }
}