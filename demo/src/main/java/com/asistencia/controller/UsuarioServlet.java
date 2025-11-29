package com.asistencia.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.asistencia.dao.UsuarioDAO;
import com.asistencia.model.Usuario;

@WebServlet("/usuarios")
public class UsuarioServlet extends HttpServlet {

    private UsuarioDAO usuarioDAO;

    @Override
    public void init() {
        usuarioDAO = new UsuarioDAO();
    }

    // doGet: Maneja la navegación (Listar, Formulario Nuevo, Formulario Editar, Eliminar)
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "new":
                showNewForm(request, response);
                break;
            case "edit":
                showEditForm(request, response);
                break;
            case "delete":
                deleteUsuario(request, response);
                break;
            default:
                listUsuarios(request, response);
                break;
        }
    }

    // doPost: Maneja el envío del formulario con VALIDACIONES
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 1. Recibir Parámetros
        String idStr = request.getParameter("idUsuario");
        String rut = request.getParameter("rut");
        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String idRolStr = request.getParameter("idRol");

        // 2. Preparar objeto para mantener los datos en caso de error
        int idRol = (idRolStr != null) ? Integer.parseInt(idRolStr) : 3; // Default Alumno
        Usuario usuario = new Usuario(rut, nombre, apellido, email, password, idRol);
        
        // Si viene un ID, lo seteamos (significa que es una edición)
        if (idStr != null && !idStr.isEmpty()) {
            usuario.setIdUsuario(Integer.parseInt(idStr));
        }

        // 3. --- VALIDACIONES ROBUSTAS (SERVER-SIDE) ---
        List<String> errores = new ArrayList<>();

        // A. Validar campos obligatorios vacíos
        if (rut == null || rut.trim().isEmpty()) errores.add("El RUT es obligatorio.");
        if (nombre == null || nombre.trim().isEmpty()) errores.add("El Nombre es obligatorio.");
        if (apellido == null || apellido.trim().isEmpty()) errores.add("El Apellido es obligatorio.");
        
        // B. Validar formato de Email (Regex simple)
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        if (email == null || !Pattern.matches(emailRegex, email)) {
            errores.add("El formato del correo electrónico no es válido.");
        }

        // C. Validar Contraseña (mínimo 4 caracteres)
        if (password == null || password.length() < 4) {
            errores.add("La contraseña debe tener al menos 4 caracteres.");
        }

        // 4. Decisión: ¿Hay errores?
        if (!errores.isEmpty()) {
            // SI HAY ERRORES: Volvemos al formulario mostrando las alertas
            request.setAttribute("errores", errores);
            // Devolvemos el objeto usuario para que el formulario se rellene con lo que el usuario ya escribió
            request.setAttribute("usuario", usuario); 
            request.getRequestDispatcher("usuarios-form.jsp").forward(request, response);
            return; // Importante: Detenemos la ejecución aquí para no guardar
        }

        // 5. SI NO HAY ERRORES: Procedemos a Guardar en Base de Datos
        if (idStr == null || idStr.isEmpty()) {
            usuarioDAO.insert(usuario);
        } else {
            usuarioDAO.update(usuario);
        }
        
        // Redirigir al listado principal
        response.sendRedirect("usuarios");
    }

    // --- Métodos Auxiliares para doGet ---

    private void listUsuarios(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        List<Usuario> listUsuarios = usuarioDAO.listAll();
        request.setAttribute("listaUsuarios", listUsuarios);
        request.getRequestDispatcher("usuarios.jsp").forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.getRequestDispatcher("usuarios-form.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            Usuario existingUsuario = usuarioDAO.getById(id);
            request.setAttribute("usuario", existingUsuario);
            request.getRequestDispatcher("usuarios-form.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.sendRedirect("usuarios");
        }
    }

    private void deleteUsuario(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            usuarioDAO.delete(id);
        } catch (NumberFormatException e) {
            // Ignorar error de ID inválido al borrar
        }
        response.sendRedirect("usuarios");
    }
}