<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.asistencia.model.Curso" %>
<%
    // Recuperamos el curso si estamos en modo edición
    Curso curso = (Curso) request.getAttribute("curso");
%>
<html>
<head>
    <title><%= (curso != null) ? "Editar Curso" : "Nuevo Curso" %></title>
</head>
<body>
    <h2><%= (curso != null) ? "Editar Curso" : "Nuevo Curso" %></h2>
    
    <form action="cursos" method="post">
        
        <% if (curso != null) { %>
            <input type="hidden" name="idCurso" value="<%= curso.getIdCurso() %>">
        <% } %>

        <label>Código:</label><br>
        <input type="text" name="codigo" value="<%= (curso!=null)?curso.getCodigo():"" %>" required><br><br>

        <label>Nombre del Curso:</label><br>
        <input type="text" name="nombre" value="<%= (curso!=null)?curso.getNombre():"" %>" required><br><br>
        
        <label>ID Profesor Asignado:</label><br>
        <input type="number" name="idProfesor" value="<%= (curso!=null)?curso.getIdProfesor():"" %>" required><br><br>

        <button type="submit">Guardar</button>
        <a href="cursos">Cancelar</a>
    </form>
</body>
</html>