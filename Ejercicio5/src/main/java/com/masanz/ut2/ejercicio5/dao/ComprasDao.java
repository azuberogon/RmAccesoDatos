package com.masanz.ut2.ejercicio5.dao;

import com.masanz.ut2.ejercicio5.database.DatabaseManager;
import com.masanz.ut2.ejercicio5.dto.Compras;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// Esta clase representa un Data Access Object (DAO) para la entidad "Compras"
public class ComprasDao {

    // Constructor por defecto
    public ComprasDao(){

    }

    // Método para crear una nueva compra utilizando un objeto Compras
    public Compras crearCompras(Compras compras){
        return crearCompras(compras.getIdArticulo(), compras.getIdComprador(), compras.getIdVendedor(), compras.getFechaComprar());
    }

    // Método privado para crear una nueva compra utilizando los parámetros
    public Compras crearCompras(int idArticulo, int idComprador, int idVendedor, Date fechaComprar){
        // Se crea un objeto Compras con los parámetros dados
        Compras compras = new Compras(idArticulo, idComprador, idVendedor);
        // Se prepara la sentencia SQL para la inserción en la base de datos
        String sql = "INSERT INTO compras (id_objeto, id_comprador, id_vendedor,  fecha_compra) VALUES (?, ?, ?, ?)";
        // Se especifican los parámetros para la sentencia SQL
        Object[] params = {idArticulo, idComprador, idVendedor, fechaComprar};
        // Se ejecuta la sentencia SQL para insertar la nueva compra en la base de datos
        int registrosIncluidos = DatabaseManager.ejecutarUpdateSQL(sql, params);
        // Si se insertó al menos un registro, se devuelve la compra creada; de lo contrario, se devuelve null
        if(registrosIncluidos>0){
            return compras;
        }
        return null;
    }

    // Método para obtener todas las compras de la base de datos
    public List<Compras> obtenerCompras(){
        // Se prepara la sentencia SQL para obtener todas las compras de la base de datos
        String sql = "SELECT * FROM compras";
        // No se requieren parámetros para esta consulta
        Object[] params = null;
        // Se ejecuta la sentencia SQL y se obtiene el resultado
        Object[][] resultado = DatabaseManager.ejecutarSelectSQL(sql, params);
        // Se procesa el resultado y se devuelve una lista de objetos Compras
        List<Compras> compras = procesarResultado(resultado);
        return compras;
    }

    // Método privado para procesar el resultado de las consultas SQL y convertirlo en objetos Compras
    private List<Compras> procesarResultado(Object[][] resultado){
        List<Compras> compras = null;
        // Se verifica si el resultado es nulo
        if (resultado != null) {
            compras = new ArrayList<>();
            // Se recorre cada fila del resultado
            for (Object[] fila : resultado) {
                // Se crea un nuevo objeto Compras y se asignan los valores de la fila
                Compras compra = new Compras();
                compra.setId((Integer) fila[0]);
                compra.setIdArticulo((Integer) fila[1]);
                compra.setIdComprador((Integer) fila[2]);
                compra.setIdVendedor((Integer) fila[3]);
                compra.setFechaComprar((Date) fila[4]);
                // Se añade la compra a la lista
                compras.add(compra);
            }
        } else {
            // Si el resultado es nulo, se imprime un mensaje
            System.out.println("El resultado es nulo.");
        }
        return compras;
    }
}
