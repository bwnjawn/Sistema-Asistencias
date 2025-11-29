<%@ page import="java.util.List" %>
<%@ page import="com.asistencia.model.Curso" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    if (session.getAttribute("usuarioLogueado") == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Gestión de Cursos - Validada</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        th, td { border: 1px solid #ddd; padding: 10px; text-align: left; }
        th { background-color: #007bff; color: white; }
        .container { max-width: 800px; margin: 0 auto; }
        .form-box { background: #f9f9f9; padding: 20px; border-radius: 8px; border: 1px solid #ddd; }
        .btn { background: #28a745; color: white; padding: 10px 15px; border: none; cursor: pointer; border-radius: 4px; }
        .btn:hover { background-color: #218838; }
        
        /* Estilos para Mensajes */
        .alert { padding: 10px; margin-bottom: 15px; border-radius: 4px; }
        .alert-danger { background-color: #f8d7da; color: #721c24; border: 1px solid #f5c6cb; }
        .alert-success { background-color: #d4edda; color: #155724; border: 1px solid #c3e6cb; }
        .error-text { color: red; font-size: 0.9em; display: none; }
    </style>

    <script>
        function validarFormulario(event) {
            // Obtenemos los valores
            var nombre = document.getElementById("nombre").value;
            var seccion = document.getElementById("seccion").value;
            var errorDiv = document.getElementById("js-error");
            
            // Limpiamos mensajes previos
            errorDiv.style.display = "none";
            errorDiv.innerText = "";

            // Lógica de validación
            if (nombre.trim() === "" || seccion.trim() === "") {
                mostrarError(event, "Por favor, completa todos los campos (JS).");
                return;
            }
            if (nombre.length < 3) {
                mostrarError(event, "El nombre es muy corto (mínimo 3 letras).");
                return;
            }
            
            // Si todo está bien, el formulario se envía.
        }

        function mostrarError(event, mensaje) {
            event.preventDefault(); // DETIENE el envío del formulario
            var errorDiv = document.getElementById("js-error");
            errorDiv.innerText = mensaje;
            errorDiv.style.display = "block";
        }
    </script>
</head>
<body>
    <div class="container">
        <h1>Gestión de Cursos</h1>

        <% 
            String error = (String) request.getAttribute("mensajeError");
            if (error != null) { 
        %>
            <div class="alert alert-danger"><%= error %></div>
        <% } %>

        <% 
            if (request.getParameter("exito") != null) { 
        %>
            <div class="alert alert-success">¡Curso guardado correctamente!</div>
        <% } %>

        <div id="js-error" class="alert alert-danger" style="display:none;"></div>

        <div class="form-box">
            <h3>Nuevo Curso</h3>
            <form action="cursos" method="post" onsubmit="validarFormulario(event)">
                <div style="margin-bottom: 10px;">
                    <label>Nombre:</label><br>
                    <input type="text" id="nombre" name="nombre" placeholder="Ej: Bases de Datos" style="width: 100%; padding: 8px;">
                </div>
                
                <div style="margin-bottom: 10px;">
                    <label>Sección:</label><br>
                    <input type="text" id="seccion" name="seccion" placeholder="Ej: 005D" style="width: 100%; padding: 8px;">
                </div>
                
                <button type="submit" class="btn">Guardar Curso</button>
            </form>
        </div>

        <hr>

        <h3>Listado de Cursos</h3>
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Nombre</th>
                    <th>Sección</th>
                    <th>Acción</th>
                </tr>
            </thead>
            <tbody>
                <% 
                    List<Curso> lista = (List<Curso>) request.getAttribute("listaCursos");
                    if (lista != null && !lista.isEmpty()) {
                        for (Curso c : lista) {
                %>
                <tr>
                    <td><%= c.getIdCurso() %></td>
                    <td><%= c.getNombre() %></td>
                    <td><%= c.getSeccion() %></td>
                    <td>
                        <a href="cursos?action=eliminar&id=<%= c.getIdCurso() %>" 
                           onclick="return confirm('¿Eliminar este curso?');"
                           style="color: red; font-weight: bold; text-decoration: none;">
                           Eliminar
                        </a>
                    </td>
                </tr>
                <% 
                        }
                    } else {
                %>
                    <tr><td colspan="4">No hay cursos registrados.</td></tr>
                <% } %>
            </tbody>
        </table>
        
        <br>
        <a href="dashboard_admin.jsp">Volver al Dashboard</a>
    </div>
</body>
</html>