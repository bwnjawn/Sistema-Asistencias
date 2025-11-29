package com.asistencia.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.asistencia.dao.UsuarioDAO;
import com.asistencia.model.Usuario;

/**
 * Servlet encargado de la autenticación de usuarios.
 * Recibe email/pass, valida contra la BD y gestiona la sesión.
 */
@WebServlet("/auth")
public class AuthServlet extends HttpServlet {

    private UsuarioDAO usuarioDAO;

    @Override
    public void init() throws ServletException {
        // Inicializamos el DAO una sola vez cuando arranca el servlet
        usuarioDAO = new UsuarioDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 1. Obtener parámetros del formulario de login
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // 2. Validar credenciales usando el DAO
        // Retorna el objeto Usuario con todos sus datos si es correcto, o null si falla
        Usuario usuario = usuarioDAO.validarLogin(email, password);

        if (usuario != null) {
            // --- 3. LOGIN EXITOSO ---
            
            // Crear una nueva sesión (o obtener la actual)
            HttpSession session = request.getSession();
            
            // Guardamos datos importantes en la sesión
            session.setAttribute("usuario", usuario);       // El objeto completo para usarlo en las vistas
            session.setAttribute("rol", usuario.getIdRol()); // El ID de rol para validaciones rápidas

            // --- 4. REDIRECCIÓN INTELIGENTE POR ROL ---
            // 1=Admin, 2=Profesor, 3=Alumno (según tu DB)
            switch (usuario.getIdRol()) {
                case 1:
                    // Redirige al panel del Administrador
                    response.sendRedirect("dashboardAdmin.jsp");
                    break;
                case 2:
                    // Redirige al panel del Profesor
                    response.sendRedirect("dashboardProfesor.jsp");
                    break;
                case 3:
                    // Redirige al panel del Alumno
                    response.sendRedirect("dashboardAlumno.jsp");
                    break;
                default:
                    // Si el rol no coincide con ninguno, mandamos a una página por defecto o error
                    response.sendRedirect("index.jsp");
                    break;
            }

        } else {
            // --- 5. LOGIN FALLIDO ---
            
            // Guardamos un mensaje de error para mostrarlo en el JSP
            request.setAttribute("error", "Credenciales incorrectas. Por favor, intente nuevamente.");
            
            // Devolvemos la petición al login.jsp manteniendo los datos (forward)
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}