<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Panel de Administración</title>
</head>
<body>
    <%-- Recuperamos el nombre del usuario desde la sesión --%>
    <h1>Bienvenido Admin: ${sessionScope.usuario.nombre} ${sessionScope.usuario.apellido}</h1>
    <hr>
    
    <nav>
        <h3>Menú</h3>
        <ul>
            <li><a href="#">Gestionar Cursos (Pendiente)</a></li>
            <li><a href="#">Gestionar Usuarios (Pendiente)</a></li>
            <li><a href="logout" style="color: red;">Cerrar Sesión</a></li>
        </ul>
    </nav>

    <div class="contenido">
        <p>Este es el panel exclusivo para administradores.</p>
    </div>
</body>
</html>