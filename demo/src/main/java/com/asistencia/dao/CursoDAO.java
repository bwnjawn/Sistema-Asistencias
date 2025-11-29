package com.asistencia.dao;

// IMPORTACIONES NECESARIAS
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.asistencia.model.Curso;
import com.asistencia.util.ConexionDB;

public class CursoDAO {

    // Método para INSERTAR un curso
    public void insertarCurso(Curso curso) {
        String sql = "INSERT INTO curso (nombre, seccion) VALUES (?, ?)";
        // Try-with-resources cierra la conexión automáticamente
        try (Connection conn = ConexionDB.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, curso.getNombre());
            stmt.setString(2, curso.getSeccion());
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para LISTAR todos los cursos
    public List<Curso> listarCursos() {
        List<Curso> cursos = new ArrayList<>(); // Usamos ArrayList
        String sql = "SELECT * FROM curso ORDER BY id_curso ASC";
        
        try (Connection conn = ConexionDB.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Curso c = new Curso();
                c.setIdCurso(rs.getInt("id_curso")); // Asegúrate que tu columna en DB se llame 'id_curso'
                c.setNombre(rs.getString("nombre"));
                c.setSeccion(rs.getString("seccion"));
                cursos.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cursos;
    }

    // Método para ELIMINAR un curso
    public void eliminarCurso(int id) {
        String sql = "DELETE FROM curso WHERE id_curso = ?";
        try (Connection conn = ConexionDB.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}