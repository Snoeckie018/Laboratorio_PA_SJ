<%-- 
    Document   : ConsultarAlbum
    Created on : 06-nov-2017, 8:55:11
    Author     : Casca
--%>

<%@page import="pkgWS.PublicadorService"%>
<%@page import="pkgWS.ArrayList"%>
<%@page import="pkgWS.Publicador"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
       //Fabrica fabrica = Fabrica.getInstance();
       //IControlador ICU = fabrica.getIControlador();
       PublicadorService service = new pkgWS.PublicadorService();
       Publicador ICU = service.getPublicadorPort();
       ArrayList generos = ICU.getGenerosInString(); 
    %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="http://code.jquery.com/jquery-3.2.1.min.js"></script>
        <script src="js/ajax-cargarAlbumPorGenero.js" type="text/javascript"></script>
        <title>Consultar Album</title>
    </head>
    <body>
        <form action="/Lab/ConsultarAlbumGenero" method="POST">
            <h1 id="generost" name="generost">Generos: </h1>
            <input type="hidden" name="hiddenTemp1" id="hiddenTemp1" value="<%=generos%>">
            <select id="dropdown1" name="dropdown1">
                <script type="text/javascript" >                    
                    function myFunction(){
                        var generos = document.getElementById("hiddenTemp1").value;
                        var generos2 = generos.match(/\w+/g);
                        var arrayLength = generos2.length;
                        var x = document.getElementById("dropdown1"); 
                        var test2 = [];
                        for (var i = 0; i < arrayLength; i++) {
                            test2[i] = document.createElement("option");
                            test2[i].text = generos2[i];
                            x.add(test2[i]);
                        }
                    }
                    window.onload = myFunction; 
                </script>
            </select>
            <h1>Albums: </h1>
            <select id="dropdown2" name="dropdown2">         
            </select>
            <input type="submit" value="Consultar Album" />
        </form>            
    </body>
</html>
