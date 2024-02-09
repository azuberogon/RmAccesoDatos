package com.masanz.ut2.ejercicio5;

import com.masanz.ut2.ejercicio5.Dto.Persona;

import java.sql.*;

public class GestorDataBase {

    /*
    private Connection conexion;
    private  String bd;
    private  String usuario;
    private  String contrasena;
*/
    private static final String URL = "jdbc:mysql://localhost:3306/test";
    private static final String USUARIO = "root";
    private static final String CONTRASENA = "root";
    public GestorDataBase() {

    }

    public boolean conectar(){
        boolean exito = false;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            this.conexion = DriverManager.getConnection( "jdbc:mysql://localhost/" + bd, usuario, contrasena ) ;
        }catch (SQLException | ClassNotFoundException e){
            throw new RuntimeException(e);
        }
        return exito;
    }

    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, CONTRASENA);
    }

    public boolean desconectar(){
        boolean exito = false;
        try {
            if(this.conexion!=null && !this.conexion.isClosed()){
                conexion.close();
                exito = true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return exito;
    }

    public Persona crearUsuario(String nombre, int edad){
        PreparedStatement pst = null;
        Persona persona = null;
        try{
            String sql = "SELECT LAST_INSERT_ID() AS ultimo_id";
            ResultSet rs = pst.executeQuery(sql);
            int ultimoID=0;
            if (rs.next()) {
                ultimoID = rs.getInt("ultimo_id");
            }
            String sql2 = "Insert into Usuarios (id, nombre, edad) values (?,?,?)";

            pst.setInt(1,ultimoID);
            pst.setString(2,nombre);
            pst.setInt(1,edad);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        return persona;
    }

    public static void main(String[] args) {
        GestorDataBase asd = new GestorDataBase("jdbc:mysql://localhost:3306/test\";")
    }


}
