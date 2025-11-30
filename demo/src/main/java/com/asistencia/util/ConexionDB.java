package com.asistencia.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase de Conexión a DB (Singleton)
 * Implementa la conexión a PostgreSQL (Supabase).
 * El patrón Singleton asegura una única instancia de conexión para optimizar recursos.
 */
public class ConexionDB {

    // --- CREDENCIALES DE SUPABASE ---
    
    // 1. URL JDBC con tu Host Real de Supabase
    private static final String URL = "jdbc:postgresql://aws-1-us-east-1.pooler.supabase.com:5432/postgres?sslmode=require";

    // 2. USUARIO:
    //    - Debe ser el nombre completo del usuario del pooler (según tu imagen).
    private static final String USER = "postgres.bwfnosoulhjketfappxx"; 

    // 3. CONTRASEÑA:
    //    - La contraseña que definiste al crear el proyecto.
    private static final String PASS = "cortemagnate123!";

    // Instancia única de la clase (Singleton)
    private static ConexionDB instance;
    
    // Objeto Connection reutilizable
    private Connection connection;

    /**
     * Constructor privado para evitar instanciación externa.
     */
    private ConexionDB() {
        // JDBC 4.0+ carga el driver automáticamente.
    }

    /**
     * Método estático para obtener la única instancia de la clase.
     * Thread-safe (seguro para hilos).
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
     * Obtiene una conexión activa a la base de datos.
     * Si no existe o está cerrada, abre una nueva.
     */
    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            System.out.println("Conectando a Supabase...");
            try {
                connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
                System.out.println("¡Conexión establecida con éxito!");
            } catch (SQLException e) {
                System.err.println("Error crítico al conectar a BD: " + e.getMessage());
                throw e; 
            }
        }
        return connection;
    }

    /**
     * Cierra la conexión activa.
     */
    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Conexión cerrada.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}