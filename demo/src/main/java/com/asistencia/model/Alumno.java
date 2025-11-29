package com.asistencia.model;

/**
 * Clase Alumno que extiende de Usuario.
 * Hereda todos los atributos (rut, nombre, email, etc.)
 * Se puede usar para manejar lógica específica de alumnos si es necesario.
 */
public class Alumno extends Usuario {

    // Constructor Vacío
    public Alumno() {
        super();
        // Forzamos el rol de alumno (3) por defecto
        this.setIdRol(3);
    }

    // Constructor Completo (Reutiliza el del padre)
    public Alumno(int idUsuario, String rut, String nombre, String apellido, String email, String password) {
        super(idUsuario, rut, nombre, apellido, email, password, 3); // 3 es el ID de rol Alumno
    }

    // Constructor para registro (sin ID)
    public Alumno(String rut, String nombre, String apellido, String email, String password) {
        super(rut, nombre, apellido, email, password, 3);
    }
    
    // Aquí podrías agregar atributos exclusivos de Alumno si existieran en tu BD
    // Por ejemplo: private String matricula;
    
    @Override
    public String toString() {
        return "Alumno " + super.toString();
    }
}
