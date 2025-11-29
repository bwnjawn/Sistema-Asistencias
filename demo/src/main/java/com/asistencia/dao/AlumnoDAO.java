package com.asistencia.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.asistencia.model.Alumno;
import com.asistencia.util.ConexionDB;

public class AlumnoDAO {

    private Connection getConnection() throws SQLException {
        return ConexionDB.getInstance().getConnection();
    }

    /**
     * Lista solo a los usuarios que tienen Rol de Alumno (id_rol = 3).
     */
    public List<Alumno> listAll() {
        List<Alumno> alumnos = new ArrayList<>();
        // Filtramos directamente por el rol de alumno
        String sql = "SELECT * FROM usuario WHERE id_rol = 3 ORDER BY apellido, nombre";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                alumnos.add(mapAlumno(rs));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return alumnos;
    }

    /**
     * Obtiene un alumno por su ID, asegurando que sea realmente un alumno.
     */
    public Alumno getById(int id) {
        Alumno alumno = null;
        String sql = "SELECT * FROM usuario WHERE id_usuario = ? AND id_rol = 3";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    alumno = mapAlumno(rs);
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return alumno;
    }

    /**
     * Registra un nuevo alumno. 
     * Fuerza el id_rol = 3 aunque el objeto traiga otro.
     */
    public boolean insert(Alumno alumno) {
        String sql = "INSERT INTO usuario (rut, nombre, apellido, email, password_hash, id_rol) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, alumno.getRut());
            stmt.setString(2, alumno.getNombre());
            stmt.setString(3, alumno.getApellido());
            stmt.setString(4, alumno.getEmail());
            stmt.setString(5, alumno.getPassword());
            stmt.setInt(6, 3); // Forzamos Rol 3 (Alumno)
            
            return stmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Actualiza los datos de un alumno.
     */
    public boolean update(Alumno alumno) {
        String sql = "UPDATE usuario SET rut=?, nombre=?, apellido=?, email=?, password_hash=? WHERE id_usuario=? AND id_rol=3";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, alumno.getRut());
            stmt.setString(2, alumno.getNombre());
            stmt.setString(3, alumno.getApellido());
            stmt.setString(4, alumno.getEmail());
            stmt.setString(5, alumno.getPassword());
            stmt.setInt(6, alumno.getIdUsuario());
            
            return stmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Método auxiliar para mapear el ResultSet a un objeto Alumno.
     */
    private Alumno mapAlumno(ResultSet rs) throws SQLException {
        Alumno a = new Alumno();
        a.setIdUsuario(rs.getInt("id_usuario"));
        a.setRut(rs.getString("rut"));
        a.setNombre(rs.getString("nombre"));
        a.setApellido(rs.getString("apellido"));
        a.setEmail(rs.getString("email"));
        a.setPassword(rs.getString("password_hash"));
        // El rol ya sabemos que es 3, pero lo seteamos igual
        a.setIdRol(rs.getInt("id_rol")); 
        return a;
    }
}