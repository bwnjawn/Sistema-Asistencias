<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login - Sistema Asistencia</title>
    <style>
        .error { color: red; }
        .container { margin-top: 50px; text-align: center; }
    </style>
</head>
<body>
    <div class="container">
        <h2>Iniciar Sesión</h2>
        
        <% 
           String mensaje = (String) request.getAttribute("mensajeError");
           if (mensaje != null) { 
        %>
            <p class="error"><%= mensaje %></p>
        <% } %>

        <form action="auth" method="post">
            <div>
                <label for="email">Correo Electrónico:</label><br>
                <input type="email" id="email" name="email" required>
            </div>
            <br>
            <div>
                <label for="password">Contraseña:</label><br>
                <input type="password" id="password" name="password" required>
            </div>
            <br>
            <button type="submit">Ingresar</button>
        </form>
    </div>
</body>
</html>