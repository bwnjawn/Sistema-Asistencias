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

    // doGet: Para listar, mostrar formulario de edición o borrar
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "new":
                showNewForm(request, response);
                break;
            case "edit":
                showEditForm(request, response);
                break;
            case "delete":
                deleteCurso(request, response);
                break;
            default:
                listCursos(request, response);
                break;
        }
    }

    // doPost: Para insertar o actualizar (cuando se envía el formulario)
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String idStr = request.getParameter("idCurso");
        String codigo = request.getParameter("codigo");
        String nombre = request.getParameter("nombre");
        // Por simplificación, ponemos idProfesor = 1 (Admin) o lo recibimos del form
        int idProfesor = Integer.parseInt(request.getParameter("idProfesor")); 

        Curso curso = new Curso(codigo, nombre, idProfesor);

        if (idStr == null || idStr.isEmpty()) {
            // INSERTAR
            cursoDAO.insert(curso);
        } else {
            // ACTUALIZAR
            curso.setIdCurso(Integer.parseInt(idStr));
            cursoDAO.update(curso);
        }
        response.sendRedirect("cursos"); // Volver a la lista
    }

    // --- Métodos Auxiliares ---

    private void listCursos(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        List<Curso> listCursos = cursoDAO.listAll();
        request.setAttribute("listaCursos", listCursos);
        request.getRequestDispatcher("cursos.jsp").forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.getRequestDispatcher("cursos-form.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Curso existingCurso = cursoDAO.getById(id);
        request.setAttribute("curso", existingCurso);
        request.getRequestDispatcher("cursos-form.jsp").forward(request, response);
    }

    private void deleteCurso(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        cursoDAO.delete(id);
        response.sendRedirect("cursos");
    }
}