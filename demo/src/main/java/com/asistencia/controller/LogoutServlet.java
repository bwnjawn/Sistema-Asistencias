package com.asistencia.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 1. Obtener la sesión actual si existe (false = no crear una nueva si no existe)
        HttpSession session = request.getSession(false);
        
        if (session != null) {
            // 2. Destruir la sesión (borra todos los atributos guardados)
            session.invalidate();
        }
        
        // 3. Redirigir al Login con un mensaje (opcional)
        response.sendRedirect("login.jsp?msg=SesionCerrada");
    }
}
