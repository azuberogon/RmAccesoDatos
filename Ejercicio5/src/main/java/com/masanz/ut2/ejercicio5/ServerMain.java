package com.masanz.ut2.ejercicio5;

import com.masanz.ut2.ejercicio5.dao.ArticulosDao;
import com.masanz.ut2.ejercicio5.dao.ComprasDao;
import com.masanz.ut2.ejercicio5.dao.UsuariosDao;
import com.masanz.ut2.ejercicio5.database.DatabaseManager;
import com.masanz.ut2.ejercicio5.dto.Articulo;
import com.masanz.ut2.ejercicio5.dto.Compras;
import com.masanz.ut2.ejercicio5.dto.Usuario;
import freemarker.template.Configuration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.*;

public class ServerMain {

    // Declaración del logger para registrar eventos
    private static final Logger logger = LogManager.getLogger(ServerMain.class);

    // Variable para almacenar el ID del usuario logueado
    private static int loggedUserId = -1;

    // Método principal
    public static void main(String[] args) {

        // Configuración del logger
        logger.info("PODEIS EMPLEAR ESTE METODO PARA SACAR INFORMACION, COMO SI FUESE UN SOUT");
        logger.error("ESTE SIRVE PARA VOLCAR ERRORES, POR EJEMPLO, LAS EXCEPCIONES");

        // Configuración de Spark
        staticFileLocation("/public"); // Establecer la ubicación de los archivos estáticos
        port(8080); // Establecer el puerto

        // Configuración de FreeMarker
        FreeMarkerEngine freeMarker = new FreeMarkerEngine();
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_0);
        configuration.setClassForTemplateLoading(ServerMain.class, "/templates");
        freeMarker.setConfiguration(configuration);

        // Conexión a la base de datos y creación de objetos DAO
        DatabaseManager.conectar("acda", "root", "root");
        UsuariosDao usuariosDao = new UsuariosDao();
        ArticulosDao articulosDao = new ArticulosDao();
        ComprasDao comprasDao = new ComprasDao();

        // Configuración de rutas y controladores de Spark
        // Ruta para la página de inicio
        get("/", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            return new ModelAndView(model, "login.ftl");
        }, freeMarker);

        // Ruta para mostrar mensaje de error en el inicio de sesión
        get("/error", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("error", "¡Usuario o contraseña incorrectos!");
            return new ModelAndView(model, "login.ftl");
        }, freeMarker);

        // Controlador para el inicio de sesión
        post("/login", (request, response) -> {

            String username = request.queryParams("username");
            String password = request.queryParams("password");

            Usuario usuario = usuariosDao.autenticar(username, password);

            if (usuario!=null) {
                loggedUserId = usuario.getId();
                response.redirect("/home");
            } else {
                response.redirect("/error");
            }
            return null;
        });

            // Ruta para la página de inicio
        get("/home", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("userId", loggedUserId);
            return new ModelAndView(model, "home.ftl");
        }, freeMarker);

            // Ruta para mostrar los artículos del usuario logueado
        get("/articulos", (request, response) -> {
            List<Articulo> articulos = articulosDao.obtenerArticulosPropietario(loggedUserId);
            Map<String, Object> model = new HashMap<>();
            model.put("mostrarIdExtra", false);
            model.put("articulos", articulos);
            return new ModelAndView(model, "articulos.ftl");
        }, freeMarker);

            // Ruta para mostrar los artículos de un usuario específico
        get("/articulos/:id", (request, response) -> {
            int userId = Integer.parseInt(request.params(":id"));
            List<Articulo> articulos = articulosDao.obtenerArticulosPropietario(userId);
            Map<String, Object> model = new HashMap<>();
            model.put("mostrarIdExtra", true);
            model.put("articulos", articulos);
            return new ModelAndView(model, "articulos.ftl");
        }, freeMarker);

            // Ruta para mostrar la lista de usuarios
        get("/usuarios", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            List<Usuario> usuarios = usuariosDao.obtenerUsuarios();
            model.put("usuarios", usuarios);
            return new ModelAndView(model, "usuarios.ftl");
        }, freeMarker);

            // Ruta para mostrar información de un usuario específico
        get("/usuario/:id", (request, response) -> {
            int userId = Integer.parseInt(request.params(":id"));
            logger.info("BUSCANDO INFORMACION DEL USUARIO CON ID: "+userId);
            Usuario usuario = usuariosDao.obtenerUsuario(userId, null);
            Map<String, Object> model = new HashMap<>();
            model.put("usuario", usuario);
            return new ModelAndView(model, "usuario.ftl");
        }, freeMarker);

        // Ruta para realizar una compra de un artículo
        get("/comprar/:id", (request, response) -> {
            // Obtener el ID del artículo de los parámetros de la solicitud
            int articuloId = Integer.parseInt(request.params(":id"));

            // Registrar información sobre la compra en el registro
            logger.info("COMPRANDO ARTICULO ID: "+articuloId);

            // Obtener información sobre el artículo que se está comprando
            Articulo articulo = articulosDao.obtenerArticulo(articuloId);

            // Obtener el valor del artículo
            int valorArticulo = articulo.getValor();

            // Obtener información del usuario comprador actualmente logueado
            Usuario usuarioComprador = usuariosDao.obtenerUsuario(loggedUserId, null);

            // Obtener información del usuario vendedor del artículo
            Usuario usuarioVendedor = usuariosDao.obtenerUsuario(articulo.getIdPropietario(), null);

            // Verificar si el usuario comprador tiene saldo suficiente y no está comprando su propio artículo
            if(usuarioComprador.getSaldo() >= valorArticulo && usuarioComprador.getId() != usuarioVendedor.getId()) {
                // Actualizar saldos de los usuarios
                usuarioComprador.setSaldo(usuarioComprador.getSaldo() - valorArticulo);
                usuarioVendedor.setSaldo(usuarioVendedor.getSaldo() + valorArticulo);

                // Transferir la propiedad del artículo al usuario comprador
                articulo.setIdPropietario(usuarioComprador.getId());

                // Actualizar información en la base de datos
                usuarioComprador = usuariosDao.actualizarUsuario(usuarioComprador);
                usuarioVendedor = usuariosDao.actualizarUsuario(usuarioVendedor);
                articulo = articulosDao.actualizarArticulo(articulo);

                // Verificar si la operación de actualización fue exitosa
                if(usuarioComprador != null && usuarioVendedor != null && articulo != null) {
                    // Registrar la compra en la base de datos y confirmar la transacción
                    comprasDao.crearCompras(articulo.getId(), usuarioComprador.getId(), usuarioVendedor.getId(),  new Date());
                    DatabaseManager.persistir();
                } else {
                    // Deshacer la transacción si no se pudo actualizar la información
                    DatabaseManager.deshacer();
                }

                // Imprimir todas las compras realizadas en la consola
                List<Compras> compras = comprasDao.obtenerCompras();
                for (Compras compra : compras) {
                    System.out.println(compra);
                }
            }

                // Redirigir al usuario de vuelta a la página de inicio
                response.redirect("/home");

                // Retornar nulo ya que no se requiere ninguna respuesta adicional
                        return null;

        }, freeMarker);

        init();
    }
}