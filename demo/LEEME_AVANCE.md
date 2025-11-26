Estado del Proyecto - Sistema de Asistencia
    Última Actualización: [26/11/2025]
    Estatus: ✅ Paso 3.1 Completado (Capa de Conexión)

🛠 Qué se ha realizado hasta ahora:

1. Estructura del Proyecto:

    Se inicializó el proyecto con Maven (arquetipo webapp).

    Se creó la estructura de carpetas estándar MVC en src/main/java/com/asistencia/....

2. Base de Datos (Supabase):

    Base de datos PostgreSQL creada y configurada.

    Tablas creadas (Usuario, Curso, Asistencia, etc.) mediante script SQL.

3. Backend (Java):

    Dependencias: Se agregó el driver postgresql al archivo pom.xml.

    Conexión: Se implementó la clase ConexionDB.java en el paquete com.asistencia.util.

        Usa el patrón Singleton para gestionar eficientemente la conexión.

        Ya contiene las credenciales del Host de Supabase.

🚀 Siguientes Pasos (Para el equipo):

1. Configuración Local:

    Hacer git pull para bajar estos cambios.

I   MPORTANTE: En ConexionDB.java, deben ingresar la contraseña de la base de datos en la variable PASSWORD (Pedir clave por interno).

2. Próxima Tarea (Paso 4):

    Crear la clase Modelo Usuario.java en com.asistencia.model.

    Crear la clase UsuarioDAO.java en com.asistencia.dao para validar el login.

Nota: Si tienen problemas con Maven, recuerden actualizar el proyecto o verificar su JAVA_HOME.