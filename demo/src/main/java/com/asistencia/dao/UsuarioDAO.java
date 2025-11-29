package com.asistencia.dao;

import com.asistencia.model.Usuario;
import com.asistencia.util.ConexionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    private Connection getConnection() throws SQLException {
        // Obtiene la conexión del Singleton
        return ConexionDB.getInstance().getConnection();
    }

    // 1. VALIDAR LOGIN (CON DEBUGGING DETALLADO)
    public Usuario validarLogin(String email, String password) {
        Usuario usuario = null;
        
        // NOTA: Comparamos password_hash directamente. 
        // Si en la BD la clave no es texto plano, esto fallará.
        String sql = "SELECT * FROM usuario WHERE email = ? AND password_hash = ?";
        
        // --- DEBUG INICIAL (Imprime lo que llega del formulario) ---
        System.out.println("========================================");
        System.out.println("[DEBUG] UsuarioDAO: Iniciando validación de login...");
        System.out.println("[DEBUG] Email recibido del formulario: '" + email + "'");
        System.out.println("[DEBUG] Password recibida del formulario: '" + password + "'");
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, email);
            stmt.setString(2, password);
            
            System.out.println("[DEBUG] Ejecutando consulta SQL en Supabase...");
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // ¡ÉXITO!
                    System.out.println("[DEBUG] >>> ¡USUARIO ENCONTRADO! <<<");
                    usuario = mapUsuario(rs);
                    System.out.println("[DEBUG] ID: " + usuario.getIdUsuario());
                    System.out.println("[DEBUG] Nombre: " + usuario.getNombre());
                    System.out.println("[DEBUG] Rol: " + usuario.getIdRol());
                } else {
                    // ¡FALLO!
                    System.out.println("[DEBUG] >>> FALLO: No se encontró ningún usuario. <<<");
                    System.out.println("[DEBUG] Posibles causas:");
                    System.out.println("[DEBUG] 1. El email no existe en la tabla 'usuario'.");
                    System.out.println("[DEBUG] 2. La contraseña en la columna 'password_hash' no es EXACTAMENTE '" + password + "'.");
                    System.out.println("[DEBUG]    (Revisa si tiene espacios o si está encriptada).");
                }
            }
        } catch (SQLException e) {
            System.err.println("[ERROR] Error SQL crítico al validar login: " + e.getMessage());
            e.printStackTrace();
        }
        System.out.println("========================================");
        return usuario;
    }

    // 2. LISTAR TODOS LOS USUARIOS
    public List<Usuario> listAll() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuario ORDER BY apellido, nombre";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                usuarios.add(mapUsuario(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuarios;
    }

    // 3. INSERTAR USUARIO
    public boolean insert(Usuario u) {
        String sql = "INSERT INTO usuario (rut, nombre, apellido, email, password_hash, id_rol) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, u.getRut());
            stmt.setString(2, u.getNombre());
            stmt.setString(3, u.getApellido());
            stmt.setString(4, u.getEmail());
            stmt.setString(5, u.getPassword()); 
            stmt.setInt(6, u.getIdRol());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 4. ACTUALIZAR USUARIO
    public boolean update(Usuario u) {
        String sql = "UPDATE usuario SET rut=?, nombre=?, apellido=?, email=?, password_hash=?, id_rol=? WHERE id_usuario=?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, u.getRut());
            stmt.setString(2, u.getNombre());
            stmt.setString(3, u.getApellido());
            stmt.setString(4, u.getEmail());
            stmt.setString(5, u.getPassword());
            stmt.setInt(6, u.getIdRol());
            stmt.setInt(7, u.getIdUsuario());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 5. ELIMINAR USUARIO
    public boolean delete(int id) {
        String sql = "DELETE FROM usuario WHERE id_usuario=?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 6. OBTENER POR ID
    public Usuario getById(int id) {
        Usuario u = null;
        String sql = "SELECT * FROM usuario WHERE id_usuario=?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    u = mapUsuario(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return u;
    }

    // Helper para mapear ResultSet a Objeto
    private Usuario mapUsuario(ResultSet rs) throws SQLException {
        Usuario u = new Usuario();
        u.setIdUsuario(rs.getInt("id_usuario"));
        u.setRut(rs.getString("rut"));
        u.setNombre(rs.getString("nombre"));
        u.setApellido(rs.getString("apellido"));
        u.setEmail(rs.getString("email"));
        u.setPassword(rs.getString("password_hash"));
        u.setIdRol(rs.getInt("id_rol"));
        return u;
    }
}