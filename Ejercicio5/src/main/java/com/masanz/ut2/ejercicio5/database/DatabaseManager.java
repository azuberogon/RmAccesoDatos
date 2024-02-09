package com.masanz.ut2.ejercicio5.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {

    private static Connection conexion;

    // Método para establecer la conexión con la base de datos
    public static boolean conectar(String bd, String usuario, String contrasena){
        boolean exito = false;
        try {
            // Cargar el controlador JDBC de MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Establecer la conexión con la base de datos utilizando los parámetros proporcionados
            conexion = DriverManager.getConnection( "jdbc:mysql://localhost/" + bd, usuario, contrasena ) ;
            // Desactivar el autocommit para permitir transacciones manuales
            conexion.setAutoCommit(false);
            exito = true;
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error en la conexión con la BBDD. "+e);
        }
        return exito;
    }

    // Método para ejecutar una consulta SQL de selección y obtener los resultados
    public static Object[][] ejecutarSelectSQL(String sql, Object[] params) {
        Object[][] resultado = null;
        try {
            if(conexion!=null && !conexion.isClosed()) {
                // Preparar la consulta SQL
                PreparedStatement pst = conexion.prepareStatement(sql);
                // Establecer los parámetros en la consulta
                establecerParametros(pst, params);
                // Ejecutar la consulta y obtener el resultado
                ResultSet rs = pst.executeQuery();
                // Convertir el resultado a un formato bidimensional de objetos
                resultado = convertirResultSets(rs);
                rs.close();
                pst.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultado;
    }

    // Método para ejecutar una consulta SQL de actualización (INSERT, UPDATE, DELETE)
    public static int ejecutarUpdateSQL(String sql, Object[] params) {
        int registrosAfectados = -1;
        try {
            if(conexion!=null && !conexion.isClosed()) {
                // Preparar la consulta SQL
                PreparedStatement pst = conexion.prepareStatement(sql);
                // Establecer los parámetros en la consulta
                establecerParametros(pst, params);
                // Ejecutar la consulta de actualización y obtener el número de registros afectados
                registrosAfectados = pst.executeUpdate();
                pst.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return registrosAfectados;
    }

    // Método privado para convertir un ResultSet a un array bidimensional de objetos
    private static Object[][] convertirResultSets(ResultSet rs) throws SQLException {
        if (rs == null) {
            return null;
        }
        List<Object[]> filas = new ArrayList<>();
        int columnCount = rs.getMetaData().getColumnCount();
        while (rs.next()) {
            Object[] fila = new Object[columnCount];
            for (int i = 0; i < columnCount; i++) {
                fila[i] = rs.getObject(i + 1);
            }
            filas.add(fila);
        }
        return filas.toArray(new Object[0][0]);
    }

    // Método privado para establecer los parámetros en un PreparedStatement
    private static void establecerParametros(PreparedStatement pst, Object[] params) throws SQLException {
        if (params != null) {
            for (int i = 0; i < params.length; i++) {
                if (params[i] != null) {
                    // Establecer los diferentes tipos de datos en la consulta preparada
                    if (params[i] instanceof String) {
                        pst.setString(i + 1, (String) params[i]);
                    } else if (params[i] instanceof Integer) {
                        pst.setInt(i + 1, (Integer) params[i]);
                    } else if (params[i] instanceof Double) {
                        pst.setDouble(i + 1, (Double) params[i]);
                    } else if (params[i] instanceof Float) {
                        pst.setFloat(i + 1, (Float) params[i]);
                    } else if (params[i] instanceof java.util.Date) {
                        Date sqlDate = new Date(((java.util.Date) params[i]).getTime());
                        pst.setDate(i + 1, sqlDate);
                    } else {
                        pst.setObject(i + 1, params[i]);
                    }
                } else {
                    // Si el parámetro es nulo, establecerlo como NULL en la consulta preparada
                    pst.setNull(i + 1, Types.NULL);
                }
            }
        }
    }

    // Método para confirmar las transacciones realizadas
    public static void persistir(){
        try {
            if(conexion!=null && !conexion.isClosed()){
                // Confirmar las transacciones
                conexion.commit();
            }
        } catch (SQLException e) {
            System.out.println();
        }
    }

    // Método para deshacer las transacciones realizadas
    public static void deshacer(){
        try {
            if(conexion!=null && !conexion.isClosed()){
                // Deshacer las transacciones
                conexion.rollback();
            }
        } catch (SQLException e) {
            System.out.println();
        }
    }

    // Método para cerrar la conexión con la base de datos
    public static boolean desconectar(){
        boolean exito = false;
        try {
            if(conexion!=null && !conexion.isClosed()){
                // Cerrar la conexión
                conexion.close();
                exito = true;
            }
        } catch (SQLException e) {
            System.out.println("Error desconectando con la BBDD. "+e);
        }
        return exito;
    }

}
