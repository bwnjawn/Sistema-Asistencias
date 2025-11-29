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