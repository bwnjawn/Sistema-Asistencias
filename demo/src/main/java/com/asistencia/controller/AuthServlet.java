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

@WebServlet(name = "AuthServlet", urlPatterns = {"/auth"})
public class AuthServlet extends HttpServlet {

    private UsuarioDAO usuarioDAO;

    @Override
    public void init() throws ServletException {
        usuarioDAO = new UsuarioDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        Usuario usuario = usuarioDAO.validarLogin(email, password);
        
        if (usuario != null) {
            // 1. Crear la sesión
            HttpSession session = request.getSession();
            session.setAttribute("usuarioLogueado", usuario);
            
            // 2. Lógica de Redirección según ROL (Paso 6)
            // Asumimos: 1=Admin, 2=Profesor, 3=Alumno (según tu modelo Usuario)
            int rol = usuario.getIdRol();
            
            switch (rol) {
                case 1 -> response.sendRedirect("dashboard_admin.jsp");
                case 2 -> response.sendRedirect("dashboard_profesor.jsp");
                case 3 -> response.sendRedirect("dashboard_alumno.jsp");
                default -> response.sendRedirect("index.jsp"); // Por seguridad
            }
        } else {
            request.setAttribute("mensajeError", "Credenciales incorrectas");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        response.sendRedirect("login.jsp");
    }
}