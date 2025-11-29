<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.asistencia.model.Curso" %>
<html>
<head>
    <title>Mis Cursos - Profesor</title>
</head>
<body>
    <h2>Mis Cursos Asignados</h2>
    <a href="dashboardProfesor.jsp">Volver al Inicio</a>
    <hr>
    
    <% if (request.getParameter("mensaje") != null) { %>
        <p style="color: green;">¡Asistencia registrada correctamente!</p>
    <% } %>

    <table border="1" cellpadding="10">
        <tr>
            <th>Código</th>
            <th>Nombre del Curso</th>
            <th>Acción</th>
        </tr>
        <% 
            List<Curso> cursos = (List<Curso>) request.getAttribute("misCursos");
            if (cursos != null && !cursos.isEmpty()) {
                for (Curso c : cursos) {
        %>
        <tr>
            <td><%= c.getCodigo() %></td>
            <td><%= c.getNombre() %></td>
            <td>
                <a href="asistencia?action=tomar&idCurso=<%= c.getIdCurso() %>">Tomar Asistencia</a>
            </td>
        </tr>
        <% 
                }
            } else {
        %>
            <tr><td colspan="3">No tienes cursos asignados.</td></tr>
        <% } %>
    </table>
</body>
</html>