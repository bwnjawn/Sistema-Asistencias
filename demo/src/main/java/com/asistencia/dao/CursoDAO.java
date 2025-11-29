package com.asistencia.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.asistencia.model.Curso;
import com.asistencia.util.ConexionDB;

public class CursoDAO {

    private Connection getConnection() throws SQLException {
        return ConexionDB.getInstance().getConnection();
    }

    // 1. LISTAR TODOS
    public List<Curso> listAll() {
        List<Curso> cursos = new ArrayList<>();
        String sql = "SELECT id_curso, codigo, nombre, id_profesor FROM cursos ORDER BY nombre"; 
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                Curso curso = new Curso();
                curso.setIdCurso(rs.getInt("id_curso"));
                curso.setCodigo(rs.getString("codigo"));
                curso.setNombre(rs.getString("nombre"));
                curso.setIdProfesor(rs.getInt("id_profesor"));
                cursos.add(curso);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cursos;
    }

    // 2. INSERTAR NUEVO CURSO
    public boolean insert(Curso curso) {
        String sql = "INSERT INTO cursos (codigo, nombre, id_profesor) VALUES (?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, curso.getCodigo());
            pstmt.setString(2, curso.getNombre());
            pstmt.setInt(3, curso.getIdProfesor());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 3. ACTUALIZAR CURSO EXISTENTE
    public boolean update(Curso curso) {
        String sql = "UPDATE cursos SET codigo=?, nombre=?, id_profesor=? WHERE id_curso=?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, curso.getCodigo());
            pstmt.setString(2, curso.getNombre());
            pstmt.setInt(3, curso.getIdProfesor());
            pstmt.setInt(4, curso.getIdCurso());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 4. ELIMINAR CURSO
    public boolean delete(int idCurso) {
        String sql = "DELETE FROM cursos WHERE id_curso = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idCurso);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 5. OBTENER UN CURSO POR ID (Para editar)
    public Curso getById(int id) {
        String sql = "SELECT * FROM cursos WHERE id_curso = ?";
        Curso curso = null;
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                curso = new Curso();
                curso.setIdCurso(rs.getInt("id_curso"));
                curso.setCodigo(rs.getString("codigo"));
                curso.setNombre(rs.getString("nombre"));
                curso.setIdProfesor(rs.getInt("id_profesor"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return curso;
    }
}