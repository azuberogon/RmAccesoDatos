<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Información de Usuario</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

<div class="container mt-5">
    <h2>Información de Usuario</h2>

    <div class="card">
        <div class="card-body">
            <h5 class="card-title">Nombre Completo</h5>
            <p class="card-text">${usuario.fullName}</p>
        </div>
    </div>

    <div class="card mt-3">
        <div class="card-body">
            <h5 class="card-title">Usuario</h5>
            <p class="card-text">${usuario.user}</p>
        </div>
    </div>

    <div class="card mt-3">
        <div class="card-body">
            <h5 class="card-title">Correo Electrónico</h5>
            <p class="card-text">${usuario.email}</p>
        </div>
    </div>

    <div class="card mt-3">
        <div class="card-body">
            <h5 class="card-title">Saldo</h5>
            <p class="card-text">${usuario.saldo}</p>
        </div>
    </div>
    <!-- ESTE ENLACE IGUAL ES INUTIL -->
    <a href="/articulos" class="btn btn-success btn-lg  mt-3">Articulos del usuario</a>

    <#if usuario.creationDate?has_content>
        <div class="card mt-3">
            <div class="card-body">
                <h5 class="card-title">Fecha de Creación</h5>
                <p class="card-text">${usuario.creationDate}</p>
            </div>
        </div>
    </#if>

    <#if usuario.modificationDate?has_content>
        <div class="card mt-3">
            <div class="card-body">
                <h5 class="card-title">Fecha de Modificación</h5>
                <p class="card-text">${usuario.modificationDate}</p>
            </div>
        </div>
    </#if>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>