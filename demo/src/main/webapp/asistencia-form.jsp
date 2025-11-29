<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.asistencia.model.Curso" %>
<%@ page import="com.asistencia.model.Alumno" %>
<%@ page import="java.util.List" %>
<%
    Curso curso = (Curso) request.getAttribute("curso");
    List<Alumno> alumnos = (List<Alumno>) request.getAttribute("listaAlumnos");
    Object fechaHoy = request.getAttribute("fechaHoy");
%>
<html>
<head>
    <title>Tomar Asistencia: <%= curso.getNombre() %></title>
</head>
<body>
    <h2>Tomar Asistencia - <%= curso.getNombre() %></h2>
    
    <form action="asistencia" method="post">
        <input type="hidden" name="idCurso" value="<%= curso.getIdCurso() %>">
        
        <label>Fecha:</label>
        <input type="date" name="fecha" value="<%= fechaHoy %>" required>
        <br><br>

        <table border="1" cellpadding="5">
            <tr>
                <th>Alumno</th>
                <th>RUT</th>
                <th>¿Presente?</th>
            </tr>
            <% for (Alumno a : alumnos) { %>
            <tr>
                <td><%= a.getNombre() %> <%= a.getApellido() %></td>
                <td><%= a.getRut() %></td>
                <td style="text-align: center;">
                    <input type="checkbox" name="presente_<%= a.getIdUsuario() %>" value="true">
                </td>
            </tr>
            <% } %>
        </table>
        <br>
        <button type="submit">Guardar Asistencia</button>
        <a href="asistencia">Cancelar</a>
    </form>
</body>
</html>