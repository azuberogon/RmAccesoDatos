package com.masanz.ut2.ejercicio5.dao;

import com.masanz.ut2.ejercicio5.database.DatabaseManager;
import com.masanz.ut2.ejercicio5.dto.Articulo;
import java.util.ArrayList;
import java.util.List;

// Esta clase representa un Data Access Object (DAO) para la entidad "Articulo"
public class ArticulosDao {

    // Constructor por defecto
    public ArticulosDao(){
    }

    // Método para crear un nuevo artículo utilizando un objeto Articulo
    public Articulo crearArticulo(Articulo articulo){
        return crearArticulo(articulo.getId(), articulo.getNombre(), articulo.getValor(), articulo.getIdPropietario());
    }

    // Método privado para crear un nuevo artículo utilizando los parámetros
    public Articulo crearArticulo(int id, String nombre, int valor, int idPropietario){
        // Se crea un objeto Articulo con los parámetros dados
        Articulo articulo = new Articulo(id, nombre, valor, idPropietario);
        // Se prepara la sentencia SQL para la inserción en la base de datos
        String sql = "INSERT INTO articulos (id, nombre, valor, id_propietario) VALUES (?, ?, ?, ?)";
        // Se especifican los parámetros para la sentencia SQL
        Object[] params = {id, nombre, valor, idPropietario};
        // Se ejecuta la sentencia SQL para insertar el nuevo artículo en la base de datos
        int registrosIncluidos = DatabaseManager.ejecutarUpdateSQL(sql, params);
        // Si se insertó al menos un registro, se devuelve el artículo creado; de lo contrario, se devuelve null
        if(registrosIncluidos>0){
            return articulo;
        }
        return null;
    }

    // Método para borrar un artículo existente de la base de datos
    public boolean borrarArticulo(Articulo articulo){
        boolean borradoExitoso = false;
        // Se prepara la sentencia SQL para eliminar el artículo de la base de datos
        String sql = "DELETE FROM articulos WHERE id = ?";
        // Se especifican los parámetros para la sentencia SQL
        Object[] params = { articulo.getId() };
        // Se ejecuta la sentencia SQL para eliminar el artículo de la base de datos
        int registrosEliminados = DatabaseManager.ejecutarUpdateSQL(sql, params);
        // Si se eliminó al menos un registro, se establece borradoExitoso como true
        if (registrosEliminados > 0) {
            borradoExitoso = true;
        }
        return borradoExitoso;
    }

    // Método para actualizar un artículo existente en la base de datos
    public Articulo actualizarArticulo(Articulo articulo){
        return actualizarArticulo(articulo.getId(), articulo.getNombre(), articulo.getValor(), articulo.getIdPropietario());
    }

    // Método privado para actualizar un artículo utilizando los parámetros
    private Articulo actualizarArticulo(int id, String nombre, int valor, int idPropietario) {
        // Se prepara la sentencia SQL para actualizar el artículo en la base de datos
        String sql = "UPDATE articulos SET nombre = ?, valor = ?, id_propietario = ? WHERE id = ?";
        // Se especifican los parámetros para la sentencia SQL
        Object[] params = {nombre, valor, idPropietario, id};
        // Se ejecuta la sentencia SQL para actualizar el artículo en la base de datos
        int registrosActualizados = DatabaseManager.ejecutarUpdateSQL(sql, params);
        // Si se actualizó al menos un registro, se devuelve un nuevo objeto Articulo con los datos actualizados; de lo contrario, se devuelve null
        if (registrosActualizados > 0) {
            Articulo articulo = new Articulo(id, nombre, valor, idPropietario);
            return articulo;
        }
        return null;
    }

    // Método para obtener todos los artículos de la base de datos
    public List<Articulo> obtenerArticulos(){
        // Se prepara la sentencia SQL para obtener todos los artículos de la base de datos
        String sql = "SELECT * FROM articulos";
        // No se requieren parámetros para esta consulta
        Object[] params = null;
        // Se ejecuta la sentencia SQL y se obtiene el resultado
        Object[][] resultado = DatabaseManager.ejecutarSelectSQL(sql, params);
        // Se procesa el resultado y se devuelve una lista de objetos Articulo
        List<Articulo> articulos = procesarResultado(resultado);
        return articulos;
    }

    // Método para obtener un artículo por su ID
    public Articulo obtenerArticulo(int articuloId){
        // Se prepara la sentencia SQL para obtener el artículo por su ID
        String sql = "SELECT * FROM articulos WHERE id = ?";
        // Se especifican los parámetros para la sentencia SQL
        Object[] params = {articuloId};
        // Se ejecuta la sentencia SQL y se obtiene el resultado
        Object[][] resultado = DatabaseManager.ejecutarSelectSQL(sql, params);
        // Se procesa el resultado y se devuelve el artículo encontrado
        List<Articulo> articulos = procesarResultado(resultado);
        if(articulos!=null && articulos.size()>0){
            return articulos.get(0);
        }
        return null;
    }

    // Método para obtener todos los artículos pertenecientes a un propietario específico
    public List<Articulo> obtenerArticulosPropietario(int idPropietario){
        // Se prepara la sentencia SQL para obtener los artículos pertenecientes al propietario especificado
        String sql = "SELECT * FROM articulos WHERE id_propietario = ?";
        // Se especifican los parámetros para la sentencia SQL
        Object[] params = {idPropietario};
        // Se ejecuta la sentencia SQL y se obtiene el resultado
        Object[][] resultado = DatabaseManager.ejecutarSelectSQL(sql, params);
        // Se procesa el resultado y se devuelve una lista de objetos Articulo
        List<Articulo> articulos = procesarResultado(resultado);
        return articulos;
    }

    // Método privado para procesar el resultado de las consultas SQL y convertirlo en objetos Articulo
    private List<Articulo> procesarResultado(Object[][] resultado){
        List<Articulo> articulos = null;
        // Se verifica si el resultado es nulo
        if (resultado != null) {
            articulos = new ArrayList<>();
            // Se recorre cada fila del resultado
            for (Object[] fila : resultado) {
                // Se crea un nuevo objeto Articulo y se asignan los valores de la fila
                Articulo articulo = new Articulo();
                articulo.setId((Integer) fila[0]);
                articulo.setNombre((String) fila[1]);
                articulo.setValor((Integer) fila[2]);
                articulo.setIdPropietario((Integer) fila[3]);

                // Se añade el artículo a la lista
                articulos.add(articulo);
            }
        } else {
            // Si el resultado es nulo, se imprime un mensaje
            System.out.println("El resultado es nulo.");
        }
        return articulos;
    }

    // Método para obtener el último artículo insertado en la base de datos
    public Articulo obtenerUltimoArticulo(){
        // Se prepara la sentencia SQL para obtener el último artículo insertado
        String sql = "SELECT * FROM articulos ORDER BY id DESC LIMIT 1";
        // No se requieren parámetros para esta consulta
        Object[] params = null;
        // Se ejecuta la sentencia SQL y se obtiene el resultado
        Object[][] resultado = DatabaseManager.ejecutarSelectSQL(sql, params);
        // Se procesa el resultado y se devuelve el último artículo insertado
        List<Articulo> articulos = procesarResultado(resultado);
        if(articulos!=null && articulos.size()>0){
            return articulos.get(0);
        }
        return null;
    }
}
