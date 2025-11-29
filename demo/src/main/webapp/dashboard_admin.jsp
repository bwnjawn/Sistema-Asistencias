<%@ page import="com.asistencia.model.Usuario" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    // --- LÓGICA DE CONTROL DE SESIÓN (SEGURIDAD) ---
    Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
    // Verificamos si es null (no logueado) o si su ROL no es 1 (Admin)
    if (usuario == null || usuario.getIdRol() != 1) {
        response.sendRedirect("login.jsp");
        return; // Detiene la carga de la página
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Panel Administrador</title>
</head>
<body>
    <h1>Bienvenido Administrador: <%= usuario.getNombre() %></h1>
    <p>Aquí podrás gestionar Cursos, Usuarios y Reportes.</p>
    
    <a href="auth">Cerrar Sesión</a>
</body>
</html>
B. dashboard_profesor.jsp
Java

<%@ page import="com.asistencia.model.Usuario" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
    // Rol 2 = Profesor
    if (usuario == null || usuario.getIdRol() != 2) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Panel Profesor</title>
</head>
<body>
    <h1>Hola Profesor, <%= usuario.getNombre() %></h1>
    <p>Tus cursos asignados aparecerán aquí.</p>
    <button>Tomar Asistencia</button>
    <br><br>
    <a href="auth">Cerrar Sesión</a>
</body>
</html>