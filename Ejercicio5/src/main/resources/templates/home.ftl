<!DOCTYPE html>
<html>
    <head>
        <title>Home</title>
        <!-- Enlace a los estilos de Bootstrap -->
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css">
        <style>
            .btn-lg {
                font-size: 24px;
                padding: 20px 40px;
                margin-bottom: 20px;
            }
        </style>
    </head>
    <body>
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-md-6 mt-5 text-center">
                <h1>Bienvenidos a la HOME de la intranet</h1>
                <a href="/articulos" class="btn btn-primary btn-lg">Mis articulos</a>
                <a href="/usuario/${userId}" class="btn btn-danger btn-lg">Detalle del usuario</a>
                <a href="/usuarios" class="btn btn-success btn-lg">Todos los usuarios</a>
            </div>
        </div>
    </div>

    <!-- Enlace a los scripts de Bootstrap -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.7.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>