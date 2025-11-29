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

// La anotación define la URL del servlet: /auth
@WebServlet("/auth")
public class AuthServlet extends HttpServlet {

    private UsuarioDAO usuarioDAO;

    @Override
    public void init() throws ServletException {
        // Inicializamos el DAO
        usuarioDAO = new UsuarioDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 1. Obtener parámetros del formulario
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // 2. Validar credenciales con el DAO
        Usuario usuario = usuarioDAO.validarLogin(email, password);

        if (usuario != null) {
            // 3. LOGIN EXITOSO: Crear sesión
            HttpSession session = request.getSession();
            session.setAttribute("usuario", usuario); // Guardamos el objeto usuario completo
            session.setAttribute("rol", usuario.getIdRol()); // Guardamos el rol para facilitar validaciones

            // Redireccionar según el rol (Más adelante harás dashboards específicos)
            // Por ahora vamos al index o a una página de bienvenida
            response.sendRedirect("index.jsp"); 
        } else {
            // 4. LOGIN FALLIDO: Volver al login con error
            request.setAttribute("error", "Credenciales incorrectas. Intente nuevamente.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}