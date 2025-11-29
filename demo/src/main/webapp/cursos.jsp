<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.List" %>
<%@ page import="com.asistencia.model.Curso" %>

<html>
<head>
    <title>Gestión de Cursos</title>
</head>
<body>
    <h2>Listado de Cursos</h2>
    <a href="cursos?action=new">Agregar Nuevo Curso</a>
    <a href="dashboardAdmin.jsp">Volver al Dashboard</a>
    <br><br>
    
    <table border="1" cellpadding="5">
        <tr>
            <th>ID</th>
            <th>Código</th>
            <th>Nombre</th>
            <th>ID Profesor</th>
            <th>Acciones</th>
        </tr>
        
        <% 
           List<Curso> lista = (List<Curso>) request.getAttribute("listaCursos");
           if (lista != null) {
               for (Curso c : lista) {
        %>
        <tr>
            <td><%= c.getIdCurso() %></td>
            <td><%= c.getCodigo() %></td>
            <td><%= c.getNombre() %></td>
            <td><%= c.getIdProfesor() %></td>
            <td>
                <a href="cursos?action=edit&id=<%= c.getIdCurso() %>">Editar</a> |
                <a href="cursos?action=delete&id=<%= c.getIdCurso() %>" onclick="return confirm('¿Seguro?')">Eliminar</a>
            </td>
        </tr>
        <% 
               }
           } else {
        %>
           <tr><td colspan="5">No hay cursos cargados.</td></tr>
        <% } %>
    </table>
</body>
</html>