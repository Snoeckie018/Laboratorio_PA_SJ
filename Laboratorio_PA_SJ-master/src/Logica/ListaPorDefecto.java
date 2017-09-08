/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import java.awt.image.BufferedImage;

/**
 *
 * @author Casca
 */
public class ListaPorDefecto extends ListaDeReproduccion {
    
    private Genero genero;

    public ListaPorDefecto(Genero genero, String nombre, BufferedImage imagen) {
        super(nombre, imagen);
        this.genero = genero;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }
    
    
}
