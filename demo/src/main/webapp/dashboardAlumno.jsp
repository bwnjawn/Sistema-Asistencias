<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Portal del Estudiante</title>
</head>
<body>
    <h1>Alumno: ${sessionScope.usuario.nombre} ${sessionScope.usuario.apellido}</h1>
    <hr>
    <ul>
        <li><a href="#">Ver Mis Asistencias</a></li>
        <li><a href="logout" style="color: red;">Cerrar Sesión</a></li>
    </ul>
</body>
</html>
