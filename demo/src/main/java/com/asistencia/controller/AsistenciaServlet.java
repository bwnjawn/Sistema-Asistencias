package com.asistencia.controller;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.asistencia.dao.AlumnoDAO;
import com.asistencia.dao.AsistenciaDAO;
import com.asistencia.dao.CursoDAO;
import com.asistencia.model.Alumno;
import com.asistencia.model.Asistencia;
import com.asistencia.model.Curso;
import com.asistencia.model.Usuario;

@WebServlet("/asistencia")
public class AsistenciaServlet extends HttpServlet {

    private CursoDAO cursoDAO;
    private AlumnoDAO alumnoDAO;
    private AsistenciaDAO asistenciaDAO;

    @Override
    public void init() {
        cursoDAO = new CursoDAO();
        alumnoDAO = new AlumnoDAO();
        asistenciaDAO = new AsistenciaDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        if (action == null) action = "listarCursos"; // Default para profes

        switch (action) {
            case "tomar":
                mostrarFormulario(request, response);
                break;
            case "verHistorial": // NUEVO CASO
                mostrarHistorialAlumno(request, response);
                break;
            default:
                // Si es alumno, por defecto ver historial; si es profe, ver cursos
                HttpSession session = request.getSession(false);
                if (session != null && session.getAttribute("usuario") != null) {
                    Usuario u = (Usuario) session.getAttribute("usuario");
                    if (u.getIdRol() == 3) {
                        mostrarHistorialAlumno(request, response);
                    } else {
                        listarCursosProfesor(request, response);
                    }
                } else {
                    response.sendRedirect("login.jsp");
                }
                break;
        }
    }

    // ... otros métodos ...

    // --- NUEVO MÉTODO AUXILIAR ---
    private void mostrarHistorialAlumno(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        // Seguridad: Verificar que sea Alumno
        if (usuario == null || usuario.getIdRol() != 3) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Obtener historial desde el DAO
        List<Asistencia> historial = asistenciaDAO.listarPorAlumno(usuario.getIdUsuario());
        
        request.setAttribute("historial", historial);
        request.getRequestDispatcher("alumno-historial.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 1. Recibir datos generales
        int idCurso = Integer.parseInt(request.getParameter("idCurso"));
        String fechaStr = request.getParameter("fecha"); // Formato YYYY-MM-DD
        Date fecha = Date.valueOf(fechaStr);

        // 2. Obtener lista completa de alumnos para iterar y verificar quién vino
        // (En un sistema real, traeríamos solo los inscritos en el curso)
        List<Alumno> alumnos = alumnoDAO.listAll();

        // 3. Procesar asistencia
        for (Alumno alumno : alumnos) {
            // El checkbox envía el value solo si está marcado.
            // Buscamos el parámetro "asistencia_IDALUMNO"
            String paramName = "presente_" + alumno.getIdUsuario();
            boolean estaPresente = request.getParameter(paramName) != null;

            Asistencia asistencia = new Asistencia(idCurso, alumno.getIdUsuario(), fecha, estaPresente);
            asistenciaDAO.registrarAsistencia(asistencia);
        }

        // 4. Redirigir con mensaje de éxito
        response.sendRedirect("asistencia?mensaje=GuardadoExitoso");
    }

    private void listarCursosProfesor(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        // Seguridad básica: Verificar que sea profesor
        if (usuario == null || usuario.getIdRol() != 2) {
            response.sendRedirect("login.jsp");
            return;
        }

        List<Curso> misCursos = cursoDAO.listByProfesor(usuario.getIdUsuario());
        request.setAttribute("misCursos", misCursos);
        request.getRequestDispatcher("profesor-cursos.jsp").forward(request, response);
    }

    private void mostrarFormulario(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        int idCurso = Integer.parseInt(request.getParameter("idCurso"));
        
        // Obtenemos info del curso para el título
        Curso curso = cursoDAO.getById(idCurso);
        
        // Obtenemos todos los alumnos (Nota: Idealmente filtrar por inscritos en el futuro)
        List<Alumno> listaAlumnos = alumnoDAO.listAll();

        request.setAttribute("curso", curso);
        request.setAttribute("listaAlumnos", listaAlumnos);
        request.setAttribute("fechaHoy", LocalDate.now()); // Para poner fecha por defecto
        
        request.getRequestDispatcher("asistencia-form.jsp").forward(request, response);
    }
}