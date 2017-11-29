

<!DOCTYPE HTML>
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <link rel="stylesheet" type="text/css" href="./resources/bootstrap-3.3.7-dist/css/bootstrap.min.css"></link>
    <link rel="stylesheet" type="text/css" href="./resources/css/general.css"></link>
    <script src="http://code.jquery.com/jquery-3.2.1.min.js"></script>
   
   
    <title> Espotify </title>

</head>

<body>


    <header class="header">
        <span id="header-tit" onclick="location.reload()"><img class="img" src="./resources/images/espotify-drowlogo.png"></span>
        <h1 id="header-titl" onclick="location.reload()">Espotify </h1>

        <nav class="nav-bar">
            <ul>
                <%
                    if(request.getSession().getAttribute("UserNick")==null){
                        out.println("<li class=\"button_nav\"><a  href=\"/Lab/Login.jsp\">Ingresar</a></li>");
                    }
                    else{
                        out.println("<li class=\"button_nav\"><a  href=\"/Lab/myPerfil\">Mi perfil</a></li>");
                    } 
                    
                    //Objetos para pruebas:                    
                %>
            </ul>
        </nav>
    </header>

      <ul class="tree">
        <li>
          <input type="checkbox" checked="checked" id="c1" />
          <label class="tree_label" for="c1"> Registros</label>
          <ul>
              <li><a href="Registrar.jsp" class="tree_label"> Registrar Usuario</a></li>
              <li><a href="AgregarGenerosAlAlbum.jsp" class="tree_label"> Crear Album</a></li>
              <li><a href="AltaTema.jsp" class="tree_label"> Crear Tema</a></li>
              <li><a href="cargarBD.jsp" class="tree_label"> Cargar generos para la base de datos</a></li>
              <li>
              <input type="checkbox" checked="checked" id="c2" />
              <label for="c2" class="tree_label">Crear Lista de Reproduccion</label>
              <ul>
                <li><a href=".java" class="tree_label"> Particular</a></li>
              </ul>
          </ul>
            
            <li>
            <input type="checkbox" checked="checked" id="c3" />
            <label class="tree_label" for="c3"> Consultas</label>
            <ul>
                <li><a href="ConsultarUsuario.jsp" class="tree_label"> Consultar Usuario</a></li>
                <li><a href="BuscarLista.jsp" class="tree_label"> Consultar Lista</a></li>
                <li>
                <input type="checkbox" checked="checked" id="c4" />
                <label for="c4" class="tree_label">Consultar Album</label>
                <ul>
                    <li><a href="ConsultarAlbumGenero.jsp" class="tree_label"> Por Genero</a></li>
                    <li><a href="ConsultarAlbumArtista.jsp" class="tree_label"> Por Artista</a></li>
                </ul>
            </ul>
            
            <li>
            <input type="checkbox" checked="checked" id="c6" />
            <label class="tree_label" for="c6"> Usuarios</label>
            <ul>
                <li><a href=".java" class="tree_label"> Seguir Usuario</a></li>
                <li><a href=".java" class="tree_label"> Dejar de Seguir Usuario</a></li>
                <li>
                <input type="checkbox" checked="checked" id="c7" />
                <label for="c7" class="tree_label">Agregar a Favoritos</label>
                <ul>
                    <li><a href=".java" class="tree_label"> Tema</a></li>
                    <li><a href=".java" class="tree_label"> Lista</a></li>
                    <li><a href=".java" class="tree_label"> Album</a></li>
                </ul>           
            </ul>
    
            <li>
                    <input type="checkbox" checked="checked" id="c9" />
                    <label class="tree_label" for="c9"> Listas</label>
                    <ul>
                        <li><a href=".java" class="tree_label"> Publicar Lista</a></li>
                        <li>
                        <input type="checkbox" checked="checked" id="c10" />
                        <label for="c10" class="tree_label">Agregar Tema</label>
                        <ul>
                            <li><a href=".java" class="tree_label"> Lista Particual</a></li>
                            <li><a href=".java" class="tree_label"> Lista Por Defecto</a></li>
                        </ul>
                        </ul>
                        
            <li>
                    <input type="checkbox" checked="checked" id="c11" />
                    <label class="tree_label" for="c11"> Suscripciones</label>
                    <ul>
                        <li><a href="ContratarSuscripcion.jsp" class="tree_label"> Contratar Suscripcion</a></li>
                        <li><a href="ActualizarSuscripcion.jsp" class="tree_label"> Actualizar Suscripcion</a></li>                            
                     </ul>
              </ul>

    <footer class="footer">
        Copyright <a href="./templates/pingpongchang.html">&copy;</a> 2017
    </footer>

</body>
</html>   