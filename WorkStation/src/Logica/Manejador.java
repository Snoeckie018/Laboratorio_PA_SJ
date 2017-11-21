package Logica;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import DataType.*;


public class Manejador {
    //Clase que conserva las colecciones globales del sistema
    
    private List<Usuario> usuarios;
    private List<Cliente> clientes;
    private List<Artista> artistas;
    private List<ListaDeReproduccion> Listas; // Listas por defecto
    private Genero genero=new Genero("General"); // Guarda el genero raiz
    private List<Genero> generosList;
    private List<Tema> Temas;//Guarda los temas en una lista
    private List<Album> Albums;
    private Album TemporalAlbum;
    private List<Genero> TemporalGeneros;
    private List<Suscripcion> suscripciones;
    
    //Ids para la identificadores
    private double IdTema;
    private double IdAlbum;
    private double IdList;
    
    //Para la base de datos
    EntityManagerFactory emfactory = Persistence.createEntityManagerFactory( "Lab_Pro_AplPU" );
    
    
    private static Manejador instancia=null;
    
    private Manejador(){
        usuarios =new ArrayList();
        clientes=new ArrayList();
        artistas=new ArrayList();
        Albums = new ArrayList();
        Listas=new ArrayList(); // Listas por defecto
        generosList=new ArrayList();
        Temas=new ArrayList();
        suscripciones = new ArrayList();
        IdTema=0;
        IdAlbum=0;
        IdList=0;
        
        EntityManager entitymanager = emfactory.createEntityManager( );    
        Query q = entitymanager.createQuery("SELECT a FROM Cliente a");
        Query q2 = entitymanager.createQuery("SELECT a FROM Artista a");
        Query QGeneros = entitymanager.createQuery("SELECT a FROM Genero a");
        Query q3 = entitymanager.createQuery("SELECT a from Album a");
        Query q4 = entitymanager.createQuery("SELECT a from Tema a");
        clientes = q.getResultList();
        artistas = q2.getResultList();
        generosList = QGeneros.getResultList();
        Albums = q3.getResultList();
        Temas = q4.getResultList();
        GeneralGetHijos();
    }
    
    private void GeneralGetHijos(){
        Iterator it = generosList.iterator();
        Genero g;
        while(it.hasNext()){
            g=(Genero) it.next();
            if(g.getPadre().equals(genero.getNombre())){
                genero.addHijo(g);
            }
            else{
                Genero padre = findGenero(g.getPadre());
                if(padre!=null){
                    padre.addHijo(g);
                }
            }
        }
    }

    public List<Suscripcion> getSuscripciones() {
        return suscripciones;
    }
    
     public List getAlbumsItem() {
        Iterator it = Albums.iterator();
        List ret= new ArrayList();
        Album L;
        while(it.hasNext()){
            L= (Album) it.next();
            ret.add(new Item(L,L.getNombre()));
        }
        return ret;
    }
     
     public List getListasItem() {
        Iterator it = Listas.iterator();
        List ret= new ArrayList();
        ListaDeReproduccion L;
        while(it.hasNext()){
            L= (ListaDeReproduccion) it.next();
            ret.add(new Item(L,L.getNombre()));
        }
        return ret;
    }
    
    public List getTemasItem() {
        Iterator it = Temas.iterator();
        List ret= new ArrayList();
        Tema L;
        while(it.hasNext()){
            L= (Tema) it.next();
            ret.add(new Item(L,L.getNombre()));
        }
        return ret;
    }
    
    public List<Cliente> getClientes(){
        return this.clientes;        
    }
    
    public List<Artista> getArtistas(){
        return this.artistas;        
    }
    
    public static Manejador getinstance(){
        if (instancia==null)
            instancia = new Manejador();
        return instancia;
    }
    
    public void addCliente(Cliente usu){
        clientes.add(usu);
        EntityManager entitymanager = emfactory.createEntityManager( );  
        try{
            entitymanager.getTransaction().begin();
            entitymanager.persist(usu);
            entitymanager.getTransaction().commit();
            entitymanager.close(); 
        }
        catch (Exception e) {
            e.printStackTrace();
            entitymanager.getTransaction().rollback();
        } 
    } 
    
    public void addArtista(Artista usu){
        artistas.add(usu); 
        EntityManager entitymanager = emfactory.createEntityManager( );    
        try {
            entitymanager.getTransaction().begin();
            entitymanager.persist(usu);
            entitymanager.getTransaction().commit();
            entitymanager.close(); 
        }
        catch (Exception e) {
            e.printStackTrace();
            entitymanager.getTransaction().rollback();
        } 
    }
        
    public boolean nicknameLibre(String nickname){
        if(this.obtenerCliente(nickname) == null){
            if(this.obtenerArtista(nickname)== null)
                return true;
        }
        return false;
    }  
    
    public boolean mailLibre(String mail){
        if(this.obtenerClientePorMail(mail) == null){
            if(this.obtenerArtistaPorMail(mail)== null)
                return true;
        }
        return false;
    }
    
    public boolean artistLibre(String nombre){
            if(this.obtenerArtista(nombre)== null)
                return true;
            return false;
    }  
    
   
    public Usuario obtenerClientePorMail(String mail){
        Iterator it = clientes.iterator();
        Cliente user;
        while(it.hasNext()){
            user = (Cliente)it.next();
            if(user.getMail().equals(mail))
                return user;
        }
        return null;
    }
    public Usuario obtenerArtistaPorMail(String mail){
        Iterator it = artistas.iterator();
        Artista user;
        while(it.hasNext()){
            user = (Artista)it.next();
            if(user.getMail().equals(mail))
                return user;
        }
        return null;
    }
        
    
    public Usuario obtenerUsuario(String nickname){
        Usuario ret = null;
        ret=obtenerCliente(nickname);
        if(ret==null)
            ret=obtenerArtista(nickname);
        return ret;
    }
    
    
    public Cliente obtenerCliente(String nickname){
        Iterator it = clientes.iterator();
        Cliente user;
        while(it.hasNext()){
            user = (Cliente)it.next();
            if(user.getNickname().equals(nickname))
                return user;
        }
        return null;
    }
    
    public Artista obtenerArtista(String nickname){
        Iterator it = artistas.iterator();
        Artista user;
        while(it.hasNext()){
            user = (Artista)it.next();
            if(user.getNickname().equals(nickname))
                return user;
        }
        return null;
    }
    
    
    //Funciones auxiliares
    public Genero getGenero(){
        return genero;
    }
    
    public void createTemporalAlbum(){
        this.TemporalAlbum = new Album();
    }
    
    public void deleteTemporalAlbum(){
        this.TemporalAlbum = null;
    }
    
    public Album getTemporalAlbum(){
        return this.TemporalAlbum;
    }
    
    public void setTemporalAlbum(Album album){
        this.TemporalAlbum = album;
    }
    
    public Genero getGeneroPorNombre(String nombre){
        Iterator it = generosList.iterator();
        Genero user;
        while(it.hasNext()){
            user = (Genero)it.next();
            if(user.getNombre().equals(nombre))
                return user;
        }
        return null;
    }
    
    public ListaParticular getListaPorNombre(Cliente user, String nombre){
        Iterator it = user.getListas().iterator();
        ListaDeReproduccion lista;
        while(it.hasNext()){
            lista = (ListaDeReproduccion)it.next();
            if(lista.getNombre().equals(nombre)){
                ListaParticular listaFinal = (ListaParticular) lista;
                return listaFinal;
            }
        }
        return null;
    }
    
    public List<Genero> getListGeneros(){
        return generosList;
    }

    boolean FindUser(String text) {
        Iterator it=usuarios.iterator();
        Usuario user;
        while(it.hasNext()){
            user=(Usuario)it.next();
            if(user.getNickname().equals(text)){
                return true;
            }
        }
        return false;
    }

    public void persist(Object object) {
        EntityManager entitymanager = emfactory.createEntityManager( );
        entitymanager.getTransaction().begin();
        try {
            entitymanager.persist(object);
            entitymanager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            entitymanager.getTransaction().rollback();
        } finally {
            entitymanager.close();
        }
    }

    public Genero findGenero(String text) {
        Iterator it=generosList.iterator();
        Genero g;
        while(it.hasNext()){
            g=(Genero)it.next();
            if(g.getNombre().equals(text))
                return g;
        }
        return null;
    }

    void addGeneroToList(Genero nuevoGen){
        EntityManager entitymanager = emfactory.createEntityManager( );    
        try {
            entitymanager.getTransaction().begin();
            entitymanager.persist(nuevoGen);
            entitymanager.getTransaction().commit();
            entitymanager.close(); 
        }
        catch (Exception e) {
            e.printStackTrace();
            entitymanager.getTransaction().rollback();
        } 
        generosList.add(nuevoGen);
    }
    
    void addListaParticular(Cliente client, String nombreDeLista, String imagenDeLista){
        ListaParticular lista = new ListaParticular(client, nombreDeLista, imagenDeLista);
        client.setListaParticular(lista);
    }
    
    void addListaPorDefecto(Genero genero, String nombreDeLista, String imagenDeLista){
        ListaPorDefecto lista = new ListaPorDefecto(genero, nombreDeLista, imagenDeLista);
        this.Listas.add(lista);
    }
    

    public List ItemCLiente() {
        Iterator it=clientes.iterator();
        Cliente c;
        List ret= new ArrayList();
        while(it.hasNext()){
            c=(Cliente) it.next();
            ret.add(new Item(c, c.getNickname()));
        }
        return ret;
    }
    
    
    
    public List ItemArtista() {
        Iterator it=artistas.iterator();
        Artista c;
        List ret= new ArrayList();
        while(it.hasNext()){
            c=(Artista) it.next();
            ret.add(new Item(c, c.getNickname()));
        }
        return ret;
    }

    public List ItemTemas() {
        Iterator it=Temas.iterator();
        Tema t;
        List ret= new ArrayList();
        while(it.hasNext()){
            t=(Tema)it.next();
            ret.add(new Item(t, t.getNombre()));
        }
        return ret;
    }
    
    public List ItemSuscripciones(){
        if(suscripciones!=null){
            Iterator it = suscripciones.iterator();
            Suscripcion t;
            List ret= new ArrayList();
            while(it.hasNext()){
                t=(Suscripcion)it.next();
                ret.add(new Item(t, t.getCliente().getNickname()));
            }
            return ret;    
        }
        else
            return null;
        
    }
    
    public List ItemGenero() {
        Iterator it=generosList.iterator();
        Genero t;
        List ret= new ArrayList();
        while(it.hasNext()){
            t=(Genero)it.next();
            ret.add(new Item(t, t.getNombre()));
        }
        return ret;
    }

    public void updatePersistGen(Genero gen) {
        EntityManager entitymanager = emfactory.createEntityManager( );
        entitymanager.merge(gen);
    }

    public List getListasDefectoItem() {
        Iterator it = Listas.iterator();
        List ret = new ArrayList();
        ListaDeReproduccion lista;
        while(it.hasNext()){
            lista = (ListaDeReproduccion) it.next();
            ret.add(new Item(lista, lista.getNombre()));
        }
        return ret;
    }
    
    public void configTemporalAlbum(Artista artist, String nombre, List<Genero> generos, int año, String imagePath){
        if(artist != null && generos != null){
            this.TemporalAlbum.setArtista(artist);
            this.TemporalAlbum.setNombre(nombre);
            this.TemporalAlbum.setGenero(generos);
            this.TemporalAlbum.setAnio(año);
            this.TemporalAlbum.setImg(imagePath);    
        }
        
    }
    
    public void addTemporalAlbum(){        
        EntityManager em = emfactory.createEntityManager( );
        try {
            em.getTransaction().begin();
            List<Genero> generos = this.TemporalAlbum.getGeneros();
            
            Artista artist = this.TemporalAlbum.getArtista();
            addAlbumToGeneros(generos, this.TemporalAlbum);
            artist.addAlbum(this.TemporalAlbum); 
            this.Albums.add(this.TemporalAlbum);
            
            //em.merge(artist);
            //em.merge(this.TemporalAlbum);
            
            em.getTransaction().commit(); 
            em.close();                     
            
        }
        catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        }         
        deleteTemporalAlbum();        
        
    }
    
    public void addAlbumToGeneros(List<Genero> generos, Album album){        
        EntityManager em = emfactory.createEntityManager( );
        Iterator it=generos.iterator();
        Genero g;
        while(it.hasNext()){
            g=(Genero) it.next();
            g.addAlbum(album);
            try {
            em.getTransaction().begin();
            em.merge(g);            
            em.getTransaction().commit(); 
            em.close();  
            }
            catch (Exception e) {
                e.printStackTrace();
                em.getTransaction().rollback();
            }
        }
    }
    
    public void createTemporalGenres(){
        this.TemporalGeneros = new ArrayList();
    }
    
    public void addToTemporalGenres(Genero genero){
        if(genero != null){
            this.TemporalGeneros.add(genero);
        }
        
    }
    
    public void wipeTemporalGenres(){
        this.TemporalGeneros = null;
    }
    
    public List<Genero> getTemporalGenres(){
        return this.TemporalGeneros;
    }
    
    public void addAlbum(Album album){
        this.Albums.add(album);
    }
    
    public void addTema(Tema tema){
        this.Temas.add(tema);
        EntityManager em = emfactory.createEntityManager( );
        try {
            em.getTransaction().begin();
            em.merge(tema);            
            em.getTransaction().commit(); 
            em.close();  
        }
        catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        }
    }

    public List getAllListsAsItem() {
        List ret = new ArrayList();
        Iterator itC = clientes.iterator();
        Cliente c;
        while(itC.hasNext()){
            c=(Cliente) itC.next();
            ret.addAll(c.getListasPublicas());
        }
        ret.addAll(this.getListasDefectoItem());        
        return ret;
    }    
    
    boolean ClienteNickRepetido(String nick) {
        Iterator it = clientes.iterator();
        Cliente c;
        while(it.hasNext()){
            c=(Cliente) it.next();
            if(nick.equals(c.getNickname())){
                return true;
            }
        }
        return false;
    }
    
    boolean ArtistaNickRepetido(String nick) {
        Iterator it = artistas.iterator();
        Artista a;
        while(it.hasNext()){
            a=(Artista) it.next();
            if(nick.equals(a.getNickname())){
                return true;
            }
        }
        return false;
    }
    
    boolean ClienteMailRepetido(String mail) {
        Iterator it = clientes.iterator();
        Cliente c;
        while(it.hasNext()){
            c=(Cliente) it.next();
            if(mail.equals(c.getMail())){
                return true;
            }
        }
        return false;
    }
    
    boolean ArtistaMailRepetido(String mail) {
        Iterator it = artistas.iterator();
        Artista a;
        while(it.hasNext()){
            a=(Artista) it.next();
            if(mail.equals(a.getMail())){
                return true;
            }
        }
        return false;
    }

    
    public void ActualizarUsuario(Usuario u){
        EntityManager em = emfactory.createEntityManager();        
            try{
                if(u instanceof Cliente){
                    em.getTransaction().begin();
                    em.merge((Cliente)u);
                    em.getTransaction().commit(); 
                    em.close();
                }
                else{
                    em.getTransaction().begin();
                    em.merge((Artista)u);
                    em.getTransaction().commit(); 
                    em.close();
                }
            }
            catch(Exception e){
                e.printStackTrace();
                em.getTransaction().rollback();
            }
    }

/////////////////////////////////////FUNCIONES DE SERVLET//////////////////////////////////////////////



    public DTUsuario getUserData(String identificador) {
        DTUsuario ret = null;
        Cliente c;
        Iterator it = clientes.iterator();
        while(it.hasNext()){
            c=(Cliente) it.next();
            if(c.getNickname().equals(identificador) || c.getMail().equals(identificador)){
                ret=new DTCliente(c);
                break;
            }            
        }        
        if(ret==null){
            Artista a;
            Iterator itArt = artistas.iterator();
            while(itArt.hasNext()){
                a=(Artista) itArt.next();
                if(a.getNickname().equals(identificador) || a.getMail().equals(identificador)){
                    ret=new DTArtista(a);
                    break;
                }
            }
        }        
        return ret;
    }

    public DataSession getUserSession(String identificador, String pass) {
        DataSession ret = null;
        Usuario u;
        Iterator it=clientes.iterator();
        while(it.hasNext()){
            u=(Usuario) it.next();
            if((u.getNickname().equals(identificador) || u.getMail().equals(identificador)) && (u.getContraseña().equals(pass))){
                ret=u.getSession();
                break;
            }
        }
        if(ret==null){
            Iterator itArt = artistas.iterator();
            while(itArt.hasNext()){
                u=(Usuario) itArt.next();
                if((u.getNickname().equals(identificador) || u.getMail().equals(identificador)) && (u.getContraseña().equals(pass))){
                    ret=u.getSession();
                    break;
                }
            }
        }
        return ret;
    }
    
    public void addSuscripcion(Cliente cliente, Suscripcion s){       
        EntityManager em = emfactory.createEntityManager( );
        
            
        if(cliente!=null && s!=null){
            cliente.setSuscripcion(s);
            suscripciones.add(s);        
            em.getTransaction().begin();
            em.merge(s);
            em.merge(cliente);
            em.getTransaction().commit();
            em.close();      
        }
       
        
    }

    void AgregarTemaListaWeb(String user, String lista, String artista, String album, String tema) {
        Iterator itArt = artistas.iterator();
        Artista a;
        Tema t=null;
        while(itArt.hasNext()){
            a=(Artista) itArt.next();
            if(a.getNickname().equals(artista)){
                t=a.getTema(album, tema);
                break;
            }
        }
        Iterator itC = clientes.iterator();
        Cliente c;
        while(itC.hasNext()){
            c=(Cliente) itC.next();
            if(c.getNickname().equals(user)){
                c.AgregarTemaLista(lista, t);
                break;
            }
        }
    }

    void ActualizarImagenUsuario(String UserNick, String path) {
        Iterator itC = clientes.iterator();
        Cliente c;
        while(itC.hasNext()){
            c=(Cliente) itC.next();
            if(c.getNickname().equals(UserNick)){
                c.setImagen(path);
                return;
            }
        }
        
        Iterator itA = artistas.iterator();
        Artista a;
        while(itA.hasNext()){
            a=(Artista) itA.next();
            if(a.getNickname().equals(UserNick)){
                a.setImagen(path);
                return;
            }
        }        
    }
    
    void ActualizarImagenAlbum(String AlbumNombre, String path) {
        Iterator itC = Albums.iterator();
        Album c;
        while(itC.hasNext()){
            c=(Album) itC.next();
            if(c.getNombre().equals(AlbumNombre)){
                c.setImg(path);
                return;
            }
        }
    }
    
      public List<Album> getAlbumList(){
        return this.Albums;
    }
    
    Album getAlbumByName(String albunname) {
        Iterator it = Albums.iterator();
        Album a;
        while(it.hasNext()){
            a=(Album) it.next();
            if(a.getNombre().equals(albunname)){
                return a;
            }
        }
        return null;        
    }

}
