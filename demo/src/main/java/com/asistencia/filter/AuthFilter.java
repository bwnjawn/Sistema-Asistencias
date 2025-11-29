package com.asistencia.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.asistencia.model.Usuario;

/**
 * Filtro de Seguridad que protege las URLs de los dashboards.
 * Verifica: 1) Que el usuario esté logueado. 2) Que tenga el rol correcto para la página.
 */
// Aplica el filtro solo a estas URLs específicas
@WebFilter(urlPatterns = { "/dashboardAdmin.jsp", "/dashboardProfesor.jsp", "/dashboardAlumno.jsp" })
public class AuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException { }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);

        // 1. VERIFICAR AUTENTICACIÓN (¿Está logueado?)
        boolean isLoggedIn = (session != null && session.getAttribute("usuario") != null);

        if (!isLoggedIn) {
            res.sendRedirect("login.jsp?error=Debes iniciar sesion primero");
            return; // Detenemos la ejecución aquí
        }

        // 2. VERIFICAR AUTORIZACIÓN (¿Tiene el rol correcto?)
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        String reqURI = req.getRequestURI();

        // Roles: 1=Admin, 2=Profesor, 3=Alumno
        boolean esAdmin = usuario.getIdRol() == 1;
        boolean esProfe = usuario.getIdRol() == 2;
        boolean esAlumno = usuario.getIdRol() == 3;

        // Regla: Si intenta entrar a Admin y NO es Admin -> Fuera
        if (reqURI.contains("dashboardAdmin") && !esAdmin) {
            res.sendRedirect("login.jsp?error=Acceso no autorizado");
            return;
        }

        // Regla: Si intenta entrar a Profesor y NO es Profe -> Fuera
        if (reqURI.contains("dashboardProfesor") && !esProfe) {
            res.sendRedirect("login.jsp?error=Acceso no autorizado");
            return;
        }

        // Regla: Si intenta entrar a Alumno y NO es Alumno -> Fuera
        if (reqURI.contains("dashboardAlumno") && !esAlumno) {
            res.sendRedirect("login.jsp?error=Acceso no autorizado");
            return;
        }

        // 3. SI PASA TODO: Dejar pasar la petición al JSP
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() { }
}