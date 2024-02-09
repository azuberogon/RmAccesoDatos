package com.masanz.ut2.ejercicio5.dto;

// Definición de la clase Articulo
public class Articulo {

    // Atributos de la clase Articulo
    private int id;             // Identificador del artículo
    private String nombre;      // Nombre del artículo
    private int valor;          // Valor del artículo
    private int idPropietario;  // Identificador del propietario del artículo

    // Constructor por defecto de la clase Articulo
    public Articulo(){
        this.id = -1;           // Asignar un valor por defecto al identificador (-1 indica no inicializado)
        this.nombre = "";       // Asignar un valor por defecto al nombre (cadena vacía)
        this.valor = -1;        // Asignar un valor por defecto al valor (-1 indica no inicializado)
        this.idPropietario = -1; // Asignar un valor por defecto al identificador del propietario (-1 indica no inicializado)
    }

    // Constructor parametrizado de la clase Articulo
    public Articulo(int id, String nombre, int valor, int idPropietario) {
        this.id = id;            // Asignar el identificador proporcionado
        this.nombre = nombre;   // Asignar el nombre proporcionado
        this.valor = valor;     // Asignar el valor proporcionado
        this.idPropietario = idPropietario; // Asignar el identificador del propietario proporcionado
    }

    // Métodos para obtener y establecer el identificador del artículo
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Métodos para obtener y establecer el nombre del artículo
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // Métodos para obtener y establecer el valor del artículo
    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    // Métodos para obtener y establecer el identificador del propietario del artículo
    public int getIdPropietario() {
        return idPropietario;
    }

    public void setIdPropietario(int idPropietario) {
        this.idPropietario = idPropietario;
    }

    // Método toString para representar el objeto Articulo como una cadena
    @Override
    public String toString() {
        return "Articulo{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", valor=" + valor +
                ", idPropietario=" + idPropietario +
                '}';
    }
}
