package com.masanz.ut2.ejercicio5.Dao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TestUsuarioDao {
    // Definición de las credenciales de conexión a la base de datos
    private static final String URL = "jdbc:mysql://localhost:3306/nombre_base_de_datos";
    private static final String USUARIO = "usuario";
    private static final String CONTRASENA = "contraseña";

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
    public static void main(String[] args) {
        try {
            crearTablaUsuarios();
            // Puedes llamar a otros métodos aquí para probarlos
        } catch (SQLException e) {
            manejarError(e);
        }
    }
}

