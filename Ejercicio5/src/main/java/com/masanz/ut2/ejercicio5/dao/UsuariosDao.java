package com.masanz.ut2.ejercicio5.dao;

import com.masanz.ut2.ejercicio5.database.DatabaseManager;
import com.masanz.ut2.ejercicio5.dto.Usuario;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

// Esta clase representa un Data Access Object (DAO) para la entidad "Usuario"
public class UsuariosDao {

    // Constructor por defecto
    public UsuariosDao(){

    }

    // Método para crear un nuevo usuario utilizando un objeto Usuario
    public Usuario crearUsuario(Usuario usuario){
        return crearUsuario(usuario.getFullName(), usuario.getUser(), usuario.getEmail(), usuario.getPassword(), usuario.getSaldo());
    }

    // Método privado para crear un nuevo usuario utilizando los parámetros
    public Usuario crearUsuario(String fullName, String user, String email, String password, int saldo){
        // Se crea un objeto Usuario con los parámetros dados
        Usuario usuario = new Usuario(fullName, user, email, password, saldo);
        // Se prepara la sentencia SQL para la inserción en la base de datos
        String sql = "INSERT INTO usuarios (full_name, user, email, password, creation_date, modification_date) VALUES (?, ?, ?, ?, ?, ?)";
        // Se especifican los parámetros para la sentencia SQL
        Object[] params = {fullName, user, email, password, usuario.getCreationDate(), usuario.getModificationDate()};
        // Se ejecuta la sentencia SQL para insertar el nuevo usuario en la base de datos
        int registrosIncluidos = DatabaseManager.ejecutarUpdateSQL(sql, params);
        // Si se insertó al menos un registro, se devuelve el usuario creado; de lo contrario, se devuelve null
        if(registrosIncluidos>0){
            return usuario;
        }
        return null;
    }

    // Método para borrar un usuario existente de la base de datos
    public boolean borrarUsuario(Usuario usuario){
        boolean borradoExitoso = false;
        // Se prepara la sentencia SQL para eliminar el usuario de la base de datos
        String sql = "DELETE FROM usuarios WHERE user LIKE ?";
        // Se especifican los parámetros para la sentencia SQL
        Object[] params = { usuario.getUser() };
        // Se ejecuta la sentencia SQL para eliminar el usuario de la base de datos
        int registrosEliminados = DatabaseManager.ejecutarUpdateSQL(sql, params);
        // Si se eliminó al menos un registro, se establece borradoExitoso como true
        if (registrosEliminados > 0) {
            borradoExitoso = true;
        }
        return borradoExitoso;
    }

    // Método para actualizar un usuario existente en la base de datos
    public Usuario actualizarUsuario(Usuario usuario){
        // Se prepara la sentencia SQL para actualizar el usuario en la base de datos
        String sql = "UPDATE usuarios SET full_name = ?, email = ?, password = ? , modification_date = ? , saldo = ? WHERE user = ?";
        // Se especifican los parámetros para la sentencia SQL
        Object[] params = {usuario.getFullName(), usuario.getEmail(), usuario.getPassword(), new java.util.Date(), usuario.getSaldo(), usuario.getUser()};
        // Se ejecuta la sentencia SQL para actualizar el usuario en la base de datos
        int registrosActualizados = DatabaseManager.ejecutarUpdateSQL(sql, params);
        // Si se actualizó al menos un registro, se devuelve el usuario actualizado; de lo contrario, se devuelve null
        if (registrosActualizados > 0) {
            return usuario;
        }
        return null;
    }

    // Método para obtener un usuario por su ID o nombre de usuario
    public Usuario obtenerUsuario(int userId, String user){
        // Se prepara la sentencia SQL para obtener el usuario por su ID o nombre de usuario
        String sql = "SELECT * FROM usuarios WHERE id = ? OR user = ? LIMIT 1";
        // Se especifican los parámetros para la sentencia SQL
        Object[] params = {userId, user};
        // Se ejecuta la sentencia SQL y se obtiene el resultado
        Object[][] resultado = DatabaseManager.ejecutarSelectSQL(sql, params);
        // Se procesa el resultado y se devuelve el usuario encontrado
        List<Usuario> usuarios = procesarResultado(resultado);
        if(usuarios!=null && usuarios.size()==1){
            return usuarios.get(0);
        }
        return null;
    }

    // Método para obtener todos los usuarios de la base de datos
    public List<Usuario> obtenerUsuarios(){
        // Se prepara la sentencia SQL para obtener todos los usuarios de la base de datos
        String sql = "SELECT * FROM usuarios";
        // No se requieren parámetros para esta consulta
        Object[] params = null;
        // Se ejecuta la sentencia SQL y se obtiene el resultado
        Object[][] resultado = DatabaseManager.ejecutarSelectSQL(sql, params);
        // Se procesa el resultado y se devuelve una lista de objetos Usuario
        List<Usuario> usuarios = procesarResultado(resultado);
        return usuarios;
    }

    // Método para obtener el último usuario insertado en la base de datos
    public Usuario obtenerUltimoUsuario(){
        // Se prepara la sentencia SQL para obtener el último usuario insertado
        String sql = "SELECT * FROM usuarios ORDER BY id DESC LIMIT 1";
        // No se requieren parámetros para esta consulta
        Object[] params = null;
        // Se ejecuta la sentencia SQL y se obtiene el resultado
        Object[][] resultado = DatabaseManager.ejecutarSelectSQL(sql, params);
        // Se procesa el resultado y se devuelve el último usuario insertado
        List<Usuario> usuarios = procesarResultado(resultado);
        if(usuarios!=null && usuarios.size()>0){
            return usuarios.get(0);
        }
        return null;
    }

    // Método para procesar el resultado de las consultas SQL y

    private List<Usuario> procesarResultado(Object[][] resultado){
        // Este método procesa un resultado obtenido de la base de datos y lo convierte en una lista de objetos Usuario
        List<Usuario> usuarios = null; // Inicializa una lista de usuarios como nula
        if (resultado != null) { // Comprueba si el resultado no es nulo
            usuarios = new ArrayList<>(); // Inicializa una nueva lista de usuarios
            for (Object[] fila : resultado) { // Recorre cada fila del resultado
                Usuario usuario = new Usuario(); // Crea un nuevo objeto Usuario
                // Asigna los valores de la fila al objeto Usuario
                usuario.setId((Integer) fila[0]);
                usuario.setFullName((String) fila[1]);
                usuario.setUser((String) fila[2]);
                usuario.setEmail((String) fila[3]);
                usuario.setPassword((String) fila[4]);
                usuario.setCreationDate((Date) fila[5]);
                usuario.setModificationDate((Date) fila[6]);
                usuario.setSaldo((Integer) fila[7]);
                usuarios.add(usuario); // Agrega el usuario a la lista de usuarios
            }
        } else {
            System.out.println("El resultado es nulo."); // Si el resultado es nulo, imprime un mensaje
        }
        return usuarios; // Devuelve la lista de usuarios (puede ser nula si no hay resultados)
    }

    public Usuario autenticar(String user, String password){
        // Este método intenta autenticar a un usuario dado su nombre de usuario y contraseña
        String sql = "SELECT * FROM usuarios WHERE user = ? AND password = ?"; // Consulta SQL para autenticar al usuario
        Object[] params = {user, password}; // Parámetros para la consulta SQL
        Object[][] resultado = DatabaseManager.ejecutarSelectSQL(sql, params); // Ejecuta la consulta SQL
        List<Usuario> usuarios = procesarResultado(resultado); // Procesa el resultado obtenido
        if (usuarios!=null && usuarios.size()>0) { // Si se encontraron usuarios válidos
            return usuarios.get(0); // Devuelve el primer usuario encontrado
        }
        return null; // Devuelve nulo si no se encontraron usuarios válidos
    }

    public boolean borrarUsuariosAusentes(int cantidad){
        // Este método intenta borrar un número específico de usuarios menos activos
        boolean borradoExitoso = false; // Inicializa la variable de éxito de borrado como falsa
        String sql = "DELETE FROM usuarios ORDER BY modification_date ASC LIMIT ?"; // Consulta SQL para borrar usuarios menos activos
        Object[] params = {cantidad}; // Parámetros para la consulta SQL
        int usuariosEliminados = DatabaseManager.ejecutarUpdateSQL(sql, params); // Ejecuta la consulta SQL para borrar usuarios
        if (usuariosEliminados > 0) { // Si se eliminaron usuarios
            borradoExitoso = true; // Establece el éxito de borrado como verdadero
        }
        return borradoExitoso; // Devuelve el estado de éxito de borrado
    }

}
