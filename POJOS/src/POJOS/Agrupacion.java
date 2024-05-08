
package POJOS;

import java.io.Serializable;

/**
 * Clase que almacena los datos de la Agrupación musical
 * @author Hugo Vélez Fernández
 * @version 1.0
 * @since 1.0
 */
public class Agrupacion implements Serializable {
    
    /**
     * Numero de serialización
     */
    public static final long serialVersionUID = 1L;
    
    private Integer id;
    private String nombre;
    private String texto;
    private String contacto;
    private String foto;
    private Usuario director;
    
    /**
     * Constructor vacio | Valorres por defecto (ID = 0)
     */
    public Agrupacion(){
        this.id = 0;
    }
    
    /**
     * Constructor parcial | Inicializa ID
     * @param id ID de Agrupación
     */
    public Agrupacion(Integer id){
        this.id = id;
    }

    /**
     * Constructor parcial 2 | Inicializa todas las variables salvo el ID (ID = 0)
     * @param nombre Nombre de la agrupación
     * @param texto Texto del encabezado del panel de agrupación
     * @param contacto Contacto de la agrupación
     * @param foto Imagen de agrupación
     * @param director Usuario director de la Agrupación
     */
    public Agrupacion(String nombre, String texto, String contacto, String foto, Usuario director) {
        this.id = 0;
        this.nombre = nombre;
        this.texto = texto;
        this.contacto = contacto;
        this.foto = foto;
        this.director = director;
    }
    
    /**
     * Constructor completo
     * @param id ID de Agrupación
     * @param nombre Nombre de la agrupación
     * @param texto Texto del encabezado del panel de agrupación
     * @param contacto Contacto de la agrupación
     * @param foto Imagen de agrupación
     * @param director Usuario director de la Agrupación
     */
    public Agrupacion(Integer id, String nombre, String texto, String contacto, String foto, Usuario director) {
        this.id = id;
        this.nombre = nombre;
        this.texto = texto;
        this.contacto = contacto;
        this.foto = foto;
        this.director = director;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public Usuario getDirector() {
        return director;
    }

    public void setDirector(Usuario director) {
        this.director = director;
    }

    @Override
    public String toString() {
        return "Agrupacion{" + "id=" + id + ", nombre=" + nombre + ", texto=" + texto + ", contacto=" + contacto + ", foto=" + foto + ", director=" + director + '}';
    }
}
