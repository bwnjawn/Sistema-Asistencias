<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.asistencia.model.Usuario" %>

<html>
<head>
    <title>Gestión de Usuarios</title>
</head>
<body>
    <h2>Listado de Usuarios</h2>
    <a href="usuarios?action=new">Agregar Nuevo Usuario</a>
    <a href="dashboardAdmin.jsp">Volver al Dashboard</a>
    <br><br>
    
    <table border="1" cellpadding="5">
        <tr>
            <th>ID</th>
            <th>RUT</th>
            <th>Nombre Completo</th>
            <th>Email</th>
            <th>Rol</th>
            <th>Acciones</th>
        </tr>
        <% 
           List<Usuario> lista = (List<Usuario>) request.getAttribute("listaUsuarios");
           if (lista != null) {
               for (Usuario u : lista) {
                   String rolNombre = (u.getIdRol() == 1) ? "Admin" : (u.getIdRol() == 2) ? "Profesor" : "Alumno";
        %>
        <tr>
            <td><%= u.getIdUsuario() %></td>
            <td><%= u.getRut() %></td>
            <td><%= u.getNombre() %> <%= u.getApellido() %></td>
            <td><%= u.getEmail() %></td>
            <td><%= rolNombre %> (<%= u.getIdRol() %>)</td>
            <td>
                <a href="usuarios?action=edit&id=<%= u.getIdUsuario() %>">Editar</a> |
                <a href="usuarios?action=delete&id=<%= u.getIdUsuario() %>" onclick="return confirm('¿Eliminar usuario?')">Eliminar</a>
            </td>
        </tr>
        <% 
               }
           } 
        %>
    </table>
</body>
</html>