package com.masanz.ut2.ejercicio5;

import com.masanz.ut2.ejercicio5.Dto.Persona;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class GestorDataBase {
    private Connection conexion;
    private  String bd;
    private  String usuario;
    private  String contrasena;

    public GestorDataBase(Connection conexion, String bd, String usuario, String contrasena) {
        this.conexion = conexion;
        this.bd = bd;
        this.usuario = usuario;
        this.contrasena = contrasena;
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

    public Persona crearUsuari(int id, String nombre, int edad){
        PreparedStatement pst = null;
        Persona persona = null;
        try{
            String sql = "Insert into Usuarios (id, nombre, edad) values (?,?,?)";

        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        return persona;
    }
crut


}
