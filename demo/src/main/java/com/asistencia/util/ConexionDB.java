package com.asistencia.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase de Conexión a DB (Singleton)
 * Corrección: Fabrica conexiones nuevas en lugar de reutilizar una sola instancia insegura.
 */
public class ConexionDB {

    // --- CREDENCIALES DE SUPABASE ---
    private static final String JDBC_URL = "jdbc:postgresql://aws-1-us-east-1.pooler.supabase.com:5432/postgres?sslmode=require";
    private static final String USER = "postgres.bwfnosoulhjketfappxx"; ; 
    private static final String PASSWORD = "cortemagnate123!"; 

    // Instancia única de la propia CLASE (ConexionDB), no de la Connection java.sql
    private static ConexionDB instance;

    /**
     * Constructor privado.
     * Aquí registramos el driver una sola vez por seguridad.
     */
    private ConexionDB() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("Error: Driver de PostgreSQL no encontrado.");
            e.printStackTrace();
        }
    }

    /**
     * Singleton para obtener la instancia de la utilidad.
     */
    public static ConexionDB getInstance() {
        if (instance == null) {
            synchronized (ConexionDB.class) {
                if (instance == null) {
                    instance = new ConexionDB();
                }
            }
        }
        return instance;
    }

    /**
     * CORRECCIÓN IMPORTANTE:
     * Este método ahora crea y devuelve una NUEVA conexión cada vez.
     * El DAO que llame a este método es responsable de cerrarla (lo cual ya hacen tus DAOs).
     */
    public Connection getConnection() throws SQLException {
        // System.out.println("Abriendo nueva conexión a Supabase..."); 
        // Comentamos el print para no ensuciar la consola, pero puedes activarlo para depurar.
        return DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
    }
    
    // Eliminamos el método closeConnection() de aquí, ya que cada DAO cierra su propia conexión individualmente.
}