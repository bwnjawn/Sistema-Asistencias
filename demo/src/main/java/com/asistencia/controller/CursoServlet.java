package com.asistencia.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.asistencia.dao.CursoDAO;
import com.asistencia.model.Curso;

@WebServlet("/cursos")
public class CursoServlet extends HttpServlet {
    
    private CursoDAO cursoDAO;

    @Override
    public void init() {
        cursoDAO = new CursoDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        if (action == null) action = "listar";

        switch (action) {
            case "eliminar" -> {
                int id = Integer.parseInt(request.getParameter("id"));
                cursoDAO.eliminarCurso(id);
                response.sendRedirect("cursos");
            }
            default -> listarYMostrar(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 1. Recibir datos
        String nombre = request.getParameter("nombre");
        String seccion = request.getParameter("seccion");
        
        // --- PASO 10: VALIDACIÓN SERVER-SIDE ROBUSTA ---
        String mensajeError = null;

        // Validación 1: Campos vacíos o espacios en blanco
        if (nombre == null || nombre.trim().isEmpty() || seccion == null || seccion.trim().isEmpty()) {
            mensajeError = "Error: Todos los campos son obligatorios.";
        } 
        // Validación 2: Longitud mínima (Ej: Nombre al menos 3 letras)
        else if (nombre.length() < 3) {
            mensajeError = "Error: El nombre del curso es muy corto.";
        }
        // Validación 3: Formato de Sección (Ej: Debe tener al menos 3 caracteres)
        else if (seccion.length() < 3) {
            mensajeError = "Error: La sección debe tener al menos 3 caracteres (Ej: 001D).";
        }

        // Si hay error, NO guardamos y devolvemos a la página
        if (mensajeError != null) {
            request.setAttribute("mensajeError", mensajeError);
            listarYMostrar(request, response); // Recargamos la tabla para que no desaparezca
            return;
        }

        // --- SI PASA LAS VALIDACIONES, GUARDAMOS ---
        Curso nuevoCurso = new Curso(nombre, seccion);
        cursoDAO.insertarCurso(nuevoCurso);
        
        // Éxito: Redirigimos para limpiar el formulario (evita reenvío al refrescar)
        response.sendRedirect("cursos?exito=true");
    }

    // Método auxiliar para evitar repetir código de listar
    private void listarYMostrar(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        List<Curso> listaCursos = cursoDAO.listarCursos();
        request.setAttribute("listaCursos", listaCursos);
        request.getRequestDispatcher("cursos.jsp").forward(request, response);
    }
}