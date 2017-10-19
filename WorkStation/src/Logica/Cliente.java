package Logica;



import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "CLIENTE")
public class Cliente extends Usuario{
    private List<Usuario> siguiendo;
    private List<Tema> temasFav;
    private List<Album> albumsFav;
    private List<ListaDeReproduccion> listasFav;
    private List<ListaDeReproduccion> Listas; //Listas propias creadas por el usuario

    
    
    public Cliente(String nickname, String contraseña, String mail,
                    String nombre, String apellido, Fecha fechaDeNacimiento,
                    String imagen) {
        super(nickname, contraseña, mail, nombre, apellido, fechaDeNacimiento, imagen);
        this.siguiendo = new ArrayList();
        this.temasFav = new ArrayList();
        this.albumsFav = new ArrayList();
        this.listasFav = new ArrayList();
        this.Listas = new ArrayList();
    }

    public Cliente() {
    }   
    
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Cliente)) {
            return false;
    }
    Cliente that = (Cliente) other;
    return (this.getNickname().equals(that.getNickname()) && this.getMail().equals(that.getMail())
            && this.getNombre().equals(that.getNombre())  && this.getApellido().equals(that.getApellido())
            && this.getFechaDeNacimiento().equals(that.getFechaDeNacimiento()) && this.getImagen().equals(that.getImagen()));
}
    
    public List<ListaDeReproduccion> getListas(){
        return this.Listas;
    }
    
    @Override
    public void addFollow(Usuario u2) {
        siguiendo.add(u2);
    }
    
    @Override
    public  void removeFollow(Usuario u){
        siguiendo.remove(u);
    }
    
    void setListaParticular(ListaDeReproduccion lista){
        this.Listas.add(lista);
    }
    
  
    public List getListsItem() {
        Iterator it = Listas.iterator();
        List ret= new ArrayList();
        ListaDeReproduccion L;
        while(it.hasNext()){
            L= (ListaDeReproduccion) it.next();
            ret.add(new Item(L,L.getNombre()));
        }
        return ret;
    }
    
    public List getTemasFavItem() {
        Iterator it = temasFav.iterator();
        List ret= new ArrayList();
        Tema T;
        while(it.hasNext()){
            T= (Tema)it.next();
            ret.add(new Item(T,T.getNombre()));
        }
        return ret;
    }
    
    public void quitarTemaFav(Tema t) {
        temasFav.remove(t);
    }

    public void quitarAlbumFav(Album a) {
        albumsFav.remove(a);
    }

    public List getListsFavItem() {
        Iterator it = listasFav.iterator();
        List ret= new ArrayList();
        ListaDeReproduccion L;
        while(it.hasNext()){
            L= (ListaDeReproduccion)it.next();
            ret.add(new Item(L,L.getNombre()));
        }
        return ret;
    }

    public void quitarListFav(ListaDeReproduccion lista) {
        listasFav.remove(lista);
    }

    public List getAlbumsFavItem() {
        Iterator it = albumsFav.iterator();
        Album a;
        List ret =new ArrayList();
        while(it.hasNext()){
            a= (Album) it.next();
            ret.add(new Item(a,a.getNombre()));
        }
        return ret;
    }
    
    void guardarListFav(ListaDeReproduccion lista) {
        listasFav.add(lista);
    }

    void guardarTemaFav(Tema t) {
        temasFav.add(t);
    }

    void guardarAlbumFav(Album a) {
        albumsFav.add(a);
    }
    
    public List ItemSiguiendo() {
        Iterator it=siguiendo.iterator();
        Usuario c;
        List ret= new ArrayList();
        while(it.hasNext()){
            c=(Usuario) it.next();
            ret.add(new Item(c, c.getNickname()));
        }
        return ret;
    }

    Collection getListasPublicas() {
        Iterator it= Listas.iterator();
        //Item L;
        ListaParticular L;
        List ret = new ArrayList();
        while(it.hasNext()){
            L=(ListaParticular) it.next();
            if(!L.getPrivacidad()){
                ret.add(new Item(L, L.getNombre()));
            }
        }
       return ret;
    }        
}