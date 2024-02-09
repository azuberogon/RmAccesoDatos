package com.masanz.ut2.ejercicio5.Dao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TestUsuarioDao {
    // Definición de las credenciales de conexión a la base de datos
    private static final String URL = "jdbc:mysql://localhost:3306/test";
    private static final String USUARIO = "root";
    private static final String CONTRASENA = "root";

    // Método para conectar a la base de datos
    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, CONTRASENA);
    }

    // Método para desconectar de la base de datos
    public static void desconectar(Connection conexion) throws SQLException {
        if (conexion != null && !conexion.isClosed()) {
            conexion.close();
        }
    }

    // Método para crear la tabla de usuarios
    public static void crearTablaUsuarios() throws SQLException {
        Connection conexion = null;
        Statement stmt = null;
        try {
            conexion = conectar();
            stmt = conexion.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS usuarios ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY,"
                    + "full_name VARCHAR(45),"
                    + "user VARCHAR(45) NOT NULL UNIQUE,"
                    + "email VARCHAR(45),"
                    + "password VARCHAR(45),"
                    + "creation_date DATE,"
                    + "modification_date DATE)";
            stmt.executeUpdate(sql);
            System.out.println("Tabla usuarios creada exitosamente.");
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            desconectar(conexion);
        }
    }

    // Método para borrar la tabla de usuarios
    public static void borrarTablaUsuarios() throws SQLException {
        Connection conexion = null;
        Statement stmt = null;
        try {
            conexion = conectar();
            stmt = conexion.createStatement();
            String sql = "DROP TABLE IF EXISTS usuarios";
            stmt.executeUpdate(sql);
            System.out.println("Tabla usuarios eliminada exitosamente.");
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            desconectar(conexion);
        }
    }

    // Método para borrar un usuario por su ID
    public static void borrarUsuario(int id) throws SQLException {
        Connection conexion = null;
        PreparedStatement pstmt = null;
        try {
            conexion = conectar();
            String sql = "DELETE FROM usuarios WHERE id = ?";
            pstmt = conexion.prepareStatement(sql);
            pstmt.setInt(1, id);
            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("Usuario con ID " + id + " borrado exitosamente.");
            } else {
                System.out.println("No se encontró ningún usuario con ID " + id + ".");
            }
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
            desconectar(conexion);
        }
    }

    // Método para modificar el nombre de un usuario por su ID
    public static void modificarNombreUsuario(int id, String nuevoNombre) throws SQLException {
        Connection conexion = null;
        PreparedStatement pstmt = null;
        try {
            conexion = conectar();
            String sql = "UPDATE usuarios SET full_name = ? WHERE id = ?";
            pstmt = conexion.prepareStatement(sql);
            pstmt.setString(1, nuevoNombre);
            pstmt.setInt(2, id);
            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0) {
                System.out.println("Nombre del usuario con ID " + id + " modificado exitosamente.");
            } else {
                System.out.println("No se encontró ningún usuario con ID " + id + ".");
            }
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
            desconectar(conexion);
        }
    }

    // Método para reasignar un usuario (no estoy seguro a qué te refieres con "reasignación", así que lo dejo vacío por ahora)
    // Si puedes proporcionar más detalles sobre qué significa "reasignar", puedo ayudarte a implementarlo.

    // Método para manejar errores
    public static void manejarError(Exception e) {
        System.out.println("Se ha producido un error: " + e.getMessage());
        e.printStackTrace();
    }

    // Ejemplo de uso
    /*public static void main(String[] args) {
        try {
            crearTablaUsuarios();
            crearUsuario("feo","muyfeo","feoDEcojones","123");
           /* PreparedStatement pstmt = conectar().prepareStatement("SELECT LAST_INSERT_ID()");
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                 int idUsuario = rs.getInt(1);borrarUsuario(idUsuario);
            }

            // Puedes llamar a otros métodos aquí para probarlos
        } catch (SQLException e) {
            manejarError(e);
        }
    }*/
    public static void main(String[] args) {
        try {
            crearTablaUsuarios();
            /*int idUsuarioNuevo = crearUsuarioYDevolverId("Juan Pérez", "juanperez", "juan@example.com", "password123");
            if (idUsuarioNuevo != -1) {
                borrarUsuario(idUsuarioNuevo);
            }*/
        } catch (SQLException e) {
            manejarError(e);
        }
    }

    public static void crearUsuario(String fullName, String username, String email, String password) throws SQLException {
        Connection conexion = null;
        PreparedStatement pstmt = null;
        try {
            conexion = conectar();
            String sql = "INSERT INTO usuarios (full_name, user, email, password, creation_date) VALUES (?, ?, ?, ?, ?)";
            pstmt = conexion.prepareStatement(sql);
            pstmt.setString(1, fullName);
            pstmt.setString(2, username);
            pstmt.setString(3, email);
            pstmt.setString(4, password);
            //pstmt.setDate(5, new java.sql.Date(new Date().getTime())); // Fecha actual como fecha de creación
            pstmt.executeUpdate();
            System.out.println("Nuevo usuario creado exitosamente.");
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
            desconectar(conexion);
        }
    }

    /*
     Date fechaActual = new Date();

        Crear un objeto Date con una fecha específica (año, mes, día)

        Date fechaEjemplo = new Date(121, 0, 1); // 1 de enero de 2021 (año - 1900, mes - 1, día)


        //DELETE FROM usuarios WHERE id = ?;
        //UPDATE usuarios SET full_name = ? WHERE id = ?;
         insertarNuevoUsuario("Juan Perez", "juanperez", "juan@example.com", "password123", new Date());
        insertarNuevoUsuario("María López", "marialopez", "maria@example.com", "password456", new Date());
    /*
    * Modo de apuntes:
        Creación de tabla de usuarios: Para crear la tabla de usuarios en la base de datos, utilizamos la sentencia CREATE TABLE IF NOT EXISTS seguida de la definición de las columnas y sus tipos de datos. En este caso, también hemos especificado que el campo id sea de tipo INT AUTO_INCREMENT para que se incremente automáticamente con cada nuevo usuario.

        Inserción de nuevo usuario: Para insertar un nuevo usuario en la tabla, utilizamos la sentencia INSERT INTO seguida del nombre de la tabla y los valores que queremos insertar. Utilizamos PreparedStatement para evitar la inyección SQL y asegurarnos de que los valores se inserten correctamente.

        Borrado de usuario por su ID: Para borrar un usuario de la tabla, utilizamos la sentencia DELETE FROM seguida del nombre de la tabla y una condición que especifica qué usuario queremos borrar, en este caso, por su ID.

        Modificación de nombre de usuario por su ID: Para modificar el nombre de un usuario en la tabla, utilizamos la sentencia UPDATE seguida del nombre de la tabla y la columna que queremos modificar, junto con el nuevo valor. Es importante especificar también la condición para indicar qué usuario queremos modificar, en este caso, por su ID.

        Reasignación de usuario (hipotética): Esta consulta debería ser adaptada según el significado de "reasignar" en tu contexto específico. Dependiendo de lo que signifique "reasignar", la consulta podría implicar actualizar algún campo específico del usuario o realizar algún tipo de operación más compleja.
    *
    * */

}

