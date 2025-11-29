<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Portal Docente</title>
</head>
<body>
    <h1>Profesor: ${sessionScope.usuario.nombre} ${sessionScope.usuario.apellido}</h1>
    <hr>
    <ul>
        <li><a href="#">Mis Cursos</a></li>
        <li><a href="#">Tomar Asistencia</a></li>
        <li><a href="logout" style="color: red;">Cerrar Sesión</a></li>
    </ul>
</body>
</html>