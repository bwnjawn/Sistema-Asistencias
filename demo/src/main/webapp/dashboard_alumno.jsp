<%@ page import="com.asistencia.model.Usuario" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
    // Rol 3 = Alumno
    if (usuario == null || usuario.getIdRol() != 3) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Portal Alumno</title>
</head>
<body>
    <h1>Bienvenido, <%= usuario.getNombre() %></h1>
    <p>Revisa tu porcentaje de asistencia aquí.</p>
    
    <a href="auth">Cerrar Sesión</a>
</body>
</html>