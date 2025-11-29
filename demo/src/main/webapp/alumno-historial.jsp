<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.asistencia.model.Asistencia" %>
<%@ page import="java.text.SimpleDateFormat" %>
<html>
<head>
    <title>Mi Historial de Asistencia</title>
    <style>
        .presente { color: green; font-weight: bold; }
        .ausente { color: red; font-weight: bold; }
        table { border-collapse: collapse; width: 80%; margin: 20px auto; }
        th, td { border: 1px solid #ddd; padding: 8px; text-align: center; }
        th { background-color: #f2f2f2; }
    </style>
</head>
<body>
    <div style="text-align: center;">
        <h2>Mi Historial de Asistencia</h2>
        <a href="dashboardAlumno.jsp">Volver al Inicio</a>
    </div>

    <% 
        List<Asistencia> lista = (List<Asistencia>) request.getAttribute("historial");
        
        // Contadores simples para estadísticas rápidas
        int totalPresente = 0;
        int totalAusente = 0;
    %>

    <table>
        <thead>
            <tr>
                <th>Fecha</th>
                <th>Curso</th>
                <th>Estado</th>
            </tr>
        </thead>
        <tbody>
            <% 
            if (lista != null && !lista.isEmpty()) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                for (Asistencia a : lista) {
                    if(a.isPresente()) totalPresente++; else totalAusente++;
            %>
            <tr>
                <td><%= sdf.format(a.getFecha()) %></td>
                <td><%= a.getNombreCurso() %></td>
                <td>
                    <% if (a.isPresente()) { %>
                        <span class="presente">PRESENTE</span>
                    <% } else { %>
                        <span class="ausente">AUSENTE</span>
                    <% } %>
                </td>
            </tr>
            <% 
                }
            } else { 
            %>
            <tr>
                <td colspan="3">No hay registros de asistencia disponibles.</td>
            </tr>
            <% } %>
        </tbody>
    </table>

    <div style="text-align: center; margin-top: 20px;">
        <p>Resumen: <strong><%= totalPresente %></strong> Asistencias | <strong><%= totalAusente %></strong> Inasistencias</p>
    </div>
</body>
</html>