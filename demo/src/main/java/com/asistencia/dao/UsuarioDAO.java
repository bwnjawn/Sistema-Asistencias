package com.asistencia.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.asistencia.model.Usuario;
import com.asistencia.util.ConexionDB;

public class UsuarioDAO {

    /**
     * Valida las credenciales de un usuario.
     * @param email El correo electrónico del usuario.
     * @param password La contraseña (en texto plano por ahora, idealmente debería ser hash).
     * @return Objeto Usuario si las credenciales son correctas, null si no lo son.
     */
    @SuppressWarnings("CallToPrintStackTrace")
    public Usuario validarLogin(String email, String password) {
        Usuario usuario = null;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        // La consulta busca por email y password_hash (asumiendo que guardamos la pass tal cual para probar)
        String sql = "SELECT * FROM usuario WHERE email = ? AND password_hash = ?";

        try {
            // 1. Obtener la conexión usando nuestro Singleton
            conn = ConexionDB.getInstance().getConnection();

            // 2. Preparar la consulta (PreparedStatement evita inyección SQL)
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setString(2, password);

            // 3. Ejecutar la consulta
            rs = stmt.executeQuery();

            // 4. Procesar el resultado
            if (rs.next()) {
                // Si entra aquí, es que encontró al usuario
                usuario = new Usuario();
                
                // Mapeamos las columnas de la BD (snake_case) a los atributos de la clase (camelCase)
                usuario.setIdUsuario(rs.getInt("id_usuario"));
                usuario.setRut(rs.getString("rut"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setApellido(rs.getString("apellido"));
                usuario.setEmail(rs.getString("email"));
                usuario.setPassword(rs.getString("password_hash"));
                usuario.setIdRol(rs.getInt("id_rol"));
            }

        } catch (SQLException e) {
            System.err.println("Error al validar login: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // 5. Cerrar recursos (ResultSet y Statement, pero NO la conexión del Singleton)
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return usuario;
    }
}
