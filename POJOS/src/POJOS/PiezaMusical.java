
package POJOS;

import java.io.Serializable;

/**
 * Clase que almacena los datos de la pieza musical
 * @author Hugo Vélez Fernández
 * @version 1.0
 * @since 1.0
 */
public class PiezaMusical implements Serializable {
    
    /**
     * Numero de serialización
     */
    public static final long serialVersionUID = 3L;
    
    private Integer id;
    private String nombre;
    private String autor;
    private String texto;
    private Agrupacion deAgrupacion;
    
    /**
     * Constructor vacio | Valorres por defecto (ID = 0)
     */
    public PiezaMusical(){
        this.id = 0;
    }
    
    /**
     * Constructor parcial | Inicializa ID
     * @param id ID de pieza musical
     */
    public PiezaMusical(Integer id){
        this.id = id;
    }

    /**
     * Constructor parcial 2 | Inicializa todas las variables salvo el ID (ID = 0)
     * @param nombre Nombre de pieza musical
     * @param autor Autor de pieza musical
     * @param texto Texto de encabezado
     * @param deAgrupacion Agrupacion a la que pertenecen
     */
    public PiezaMusical(String nombre, String autor, String texto, Agrupacion deAgrupacion) {
        this.id = 0;
        this.nombre = nombre;
        this.autor = autor;
        this.texto = texto;
        this.deAgrupacion = deAgrupacion;
    }
    
    /**
     * Constructor completo
     * @param id ID de pieza musical
     * @param nombre Nombre de pieza musical
     * @param autor Autor de pieza musical
     * @param texto Texto de encabezado
     * @param deAgrupacion Agrupacion a la que pertenecen
     */
    public PiezaMusical(Integer id, String nombre, String autor, String texto, Agrupacion deAgrupacion) {
        this.id = id;
        this.nombre = nombre;
        this.autor = autor;
        this.texto = texto;
        this.deAgrupacion = deAgrupacion;
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

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Agrupacion getDeAgrupacion() {
        return deAgrupacion;
    }

    public void setDeAgrupacion(Agrupacion deAgrupacion) {
        this.deAgrupacion = deAgrupacion;
    }

    @Override
    public String toString() {
        return "PiezaMusical{" + "id=" + id + ", nombre=" + nombre + ", autor=" + autor + ", texto=" + texto + ", deAgrupacion=" + deAgrupacion + '}';
    }
}
