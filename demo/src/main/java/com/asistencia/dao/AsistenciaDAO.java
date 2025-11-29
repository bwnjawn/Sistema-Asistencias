package com.asistencia.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.asistencia.model.Asistencia;
import com.asistencia.util.ConexionDB;

public class AsistenciaDAO {

    private Connection getConnection() throws SQLException {
        return ConexionDB.getInstance().getConnection();
    }

    /**
     * Registra la asistencia de un alumno para una fecha y curso específico.
     */
    public boolean registrarAsistencia(Asistencia asistencia) {
        // SQL asume que tienes una tabla 'asistencia' con estas columnas
        String sql = "INSERT INTO asistencia (id_curso, id_alumno, fecha, presente) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, asistencia.getIdCurso());
            stmt.setInt(2, asistencia.getIdAlumno());
            stmt.setDate(3, asistencia.getFecha());
            stmt.setBoolean(4, asistencia.isPresente());
            
            return stmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Error al registrar asistencia: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Obtiene el historial completo de asistencia de un alumno.
     * Incluye el nombre del curso mediante un JOIN.
     */
    public List<Asistencia> listarPorAlumno(int idAlumno) {
        List<Asistencia> lista = new ArrayList<>();
        
        // Hacemos JOIN con la tabla 'cursos' para obtener el nombre del curso en una sola consulta
        String sql = "SELECT a.id_asistencia, a.id_curso, a.id_alumno, a.fecha, a.presente, c.nombre AS nombre_curso " +
                     "FROM asistencia a " +
                     "INNER JOIN cursos c ON a.id_curso = c.id_curso " +
                     "WHERE a.id_alumno = ? " +
                     "ORDER BY a.fecha DESC, c.nombre ASC";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, idAlumno);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Asistencia asis = new Asistencia();
                asis.setIdAsistencia(rs.getInt("id_asistencia"));
                asis.setIdCurso(rs.getInt("id_curso"));
                asis.setIdAlumno(rs.getInt("id_alumno"));
                asis.setFecha(rs.getDate("fecha"));
                asis.setPresente(rs.getBoolean("presente"));
                
                // Llenamos el campo extra que agregamos al modelo
                asis.setNombreCurso(rs.getString("nombre_curso"));
                
                lista.add(asis);
            }
            
        } catch (SQLException e) {
            System.err.println("Error al listar historial: " + e.getMessage());
            e.printStackTrace();
        }
        return lista;
    }
}