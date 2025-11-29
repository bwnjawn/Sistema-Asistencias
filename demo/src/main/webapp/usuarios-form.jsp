<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.asistencia.model.Usuario" %>
<%@ page import="java.util.List" %>
<%
    Usuario usuario = (Usuario) request.getAttribute("usuario");
    List<String> errores = (List<String>) request.getAttribute("errores");
%>
<html>
<head>
    <title><%= (usuario != null && usuario.getIdUsuario() > 0) ? "Editar Usuario" : "Nuevo Usuario" %></title>
    <style>
        .error-box { background-color: #f8d7da; color: #721c24; padding: 10px; border: 1px solid #f5c6cb; margin-bottom: 15px; border-radius: 5px; }
        .form-group { margin-bottom: 10px; }
        label { font-weight: bold; display: block; }
        input[type="text"], input[type="email"], select { width: 100%; padding: 8px; box-sizing: border-box; }
        button { padding: 10px 20px; background-color: #28a745; color: white; border: none; cursor: pointer; }
        .error-msg { color: red; font-size: 0.8em; display: none; }
    </style>
</head>
<body>
    <div style="width: 50%; margin: 0 auto;">
        <h2><%= (usuario != null && usuario.getIdUsuario() > 0) ? "Editar Usuario" : "Nuevo Usuario" %></h2>
        
        <%-- 1. ZONA DE ERRORES DEL SERVIDOR --%>
        <% if (errores != null && !errores.isEmpty()) { %>
            <div class="error-box">
                <strong>Por favor corrija los siguientes errores:</strong>
                <ul>
                    <% for (String error : errores) { %>
                        <li><%= error %></li>
                    <% } %>
                </ul>
            </div>
        <% } %>

        <form action="usuarios" method="post" onsubmit="return validarCliente()">
            
            <% if (usuario != null && usuario.getIdUsuario() > 0) { %>
                <input type="hidden" name="idUsuario" value="<%= usuario.getIdUsuario() %>">
            <% } %>

            <div class="form-group">
                <label>RUT (Sin puntos y con guion):</label>
                <input type="text" id="rut" name="rut" placeholder="12345678-9" 
                       value="<%= (usuario!=null)?usuario.getRut():"" %>">
                <span id="err-rut" class="error-msg">RUT inválido (Formato ej: 11222333-K)</span>
            </div>

            <div class="form-group">
                <label>Nombre:</label>
                <input type="text" name="nombre" value="<%= (usuario!=null)?usuario.getNombre():"" %>" required>
            </div>

            <div class="form-group">
                <label>Apellido:</label>
                <input type="text" name="apellido" value="<%= (usuario!=null)?usuario.getApellido():"" %>" required>
            </div>

            <div class="form-group">
                <label>Email:</label>
                <input type="email" name="email" value="<%= (usuario!=null)?usuario.getEmail():"" %>" required>
            </div>
            
            <div class="form-group">
                <label>Contraseña:</label>
                <input type="text" id="password" name="password" value="<%= (usuario!=null)?usuario.getPassword():"" %>" required>
                <span id="err-pass" class="error-msg">La contraseña es muy corta</span>
            </div>

            <div class="form-group">
                <label>Rol:</label>
                <select name="idRol">
                    <option value="3" <%= (usuario!=null && usuario.getIdRol()==3)?"selected":"" %>>Alumno</option>
                    <option value="2" <%= (usuario!=null && usuario.getIdRol()==2)?"selected":"" %>>Profesor</option>
                    <option value="1" <%= (usuario!=null && usuario.getIdRol()==1)?"selected":"" %>>Administrador</option>
                </select>
            </div>

            <button type="submit">Guardar</button>
            <a href="usuarios" style="margin-left: 10px;">Cancelar</a>
        </form>
    </div>

    <%-- 2. VALIDACIÓN CLIENTE (JAVASCRIPT) --%>
    <script>
        function validarCliente() {
            let esValido = true;
            
            // Validar RUT (Formato simple con Regex)
            const rut = document.getElementById("rut").value;
            const rutRegex = /^[0-9]+-[0-9kK]{1}$/;
            const errRut = document.getElementById("err-rut");
            
            if (!rutRegex.test(rut)) {
                errRut.style.display = "block";
                esValido = false;
            } else {
                errRut.style.display = "none";
            }

            // Validar Password (Longitud)
            const pass = document.getElementById("password").value;
            const errPass = document.getElementById("err-pass");
            
            if (pass.length < 4) {
                errPass.style.display = "block";
                errPass.textContent = "La contraseña debe tener al menos 4 caracteres.";
                esValido = false;
            } else {
                errPass.style.display = "none";
            }

            return esValido; // Si devuelve false, el formulario NO se envía
        }
    </script>
</body>
</html>