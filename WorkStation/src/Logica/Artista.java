package Logica;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "ARTISTA")
public class Artista extends Usuario {
    @Column(name = "BIOGRAFIA")
    private String biografia;
    @Column(name = "DIR_WEB")
    private String dir_web;    
    @OneToMany
    @JoinTable(name="ALBUMS_DE_ARTISTA", joinColumns=@JoinColumn(name="ARTISTA_NICKNAME"), inverseJoinColumns=@JoinColumn(name="ALBUMS_ID")) 
    private List<Album> albums;
    private List<Usuario> siguiendo;

    public String getBiografia() {
        return biografia;
    }

    public String getDir_web() {
        return dir_web;
    }

    public Artista(String biografia, String dir_web, String nickname, String contraseña, String mail, String nombre, String apellido, Fecha fechaDeNacimiento, String imagen) {
        super(nickname, contraseña, mail, nombre, apellido, fechaDeNacimiento, imagen);
        this.biografia = biografia;
        this.dir_web = dir_web;
        this.albums = new ArrayList();
    }
    
    public Artista(){
        this.albums = new ArrayList();
    }
    
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Artista)) {
            return false;
    }
    Artista that = (Artista) other;        
    return (this.getBiografia().equals(that.getBiografia()) && this.getDir_web().equals(that.getDir_web()) &&
            this.getNickname().equals(that.getNickname()) && this.getMail().equals(that.getMail())
            && this.getNombre().equals(that.getNombre())  && this.getApellido().equals(that.getApellido())
            && this.getFechaDeNacimiento().equals(that.getFechaDeNacimiento()) && this.getImagen().equals(that.getImagen()));
    }
    
    @Override
    public void addFollow(Usuario u2) { //Only clients can follow
       // siguiendo.add(u2);
    }    
    
    @Override
    public  void removeFollow(Usuario u){
        //siguiendo.remove(u);
    }

    public void addAlbum(Album album){
        this.albums.add(album);
    }
    
    public List getAlbumsItem() {
        Iterator it = albums.iterator();
        Album a;
        List ret=new ArrayList();
        while(it.hasNext()){
            a=(Album) it.next();
            ret.add(new Item(a, a.getNombre()));
        }
        return ret;
    }
    
    public List<Album> getAlbums(){
        return this.albums;
    }
}