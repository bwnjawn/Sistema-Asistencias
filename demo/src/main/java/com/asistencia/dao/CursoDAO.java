package com.asistencia.dao;

import com.asistencia.model.Curso;
import com.asistencia.util.ConexionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CursoDAO {

    private Connection getConnection() throws SQLException {
        // Usa la instancia Singleton de tu conexión
        return ConexionDB.getInstance().getConnection();
    }

    /**
     * Recupera una lista de todos los cursos de la base de datos.
     * (Método básico de listado requerido por el Paso 4)
     */
    public List<Curso> listAll() {
        List<Curso> cursos = new ArrayList<>();
        // Ajusta el SELECT a los nombres reales de tus columnas
        String SQL = "SELECT id_curso, codigo, nombre, id_profesor FROM cursos ORDER BY nombre"; 
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL);
             ResultSet rs = pstmt.executeQuery()) {
            
            // Itera sobre el ResultSet y mapea cada fila a un objeto Curso
            while (rs.next()) {
                Curso curso = new Curso();
                curso.setIdCurso(rs.getInt("id_curso"));
                curso.setCodigo(rs.getString("codigo"));
                curso.setNombre(rs.getString("nombre"));
                curso.setIdProfesor(rs.getInt("id_profesor"));
                
                cursos.add(curso);
            }
            
        } catch (SQLException e) {
            System.err.println("Error al listar todos los cursos: " + e.getMessage());
            e.printStackTrace();
        }
        return cursos;
    }
}