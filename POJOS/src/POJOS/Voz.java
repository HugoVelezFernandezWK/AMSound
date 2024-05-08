
package POJOS;

import java.io.Serializable;

/**
 * Clase que almacena los datos de la voz
 * @author Hugo Vélez Fernández
 * @version 1.0
 * @since 1.0
 */
public class Voz implements Serializable {
    
    /**
     * Numero de serialización
     */
    public static final long serialVersionUID = 4L;
    
    private Integer id;
    private String nombre;
    private String texto;
    private Agrupacion deAgrupacion;
    
    /**
     * Constructor vacio | Valorres por defecto (ID = 0)
     */
    public Voz(){
        this.id = 0;
    }
    
    /**
     * Constructor parcial | Inicializa ID
     * @param id ID de voz
     */
    public Voz(Integer id){
        this.id = id;
    }

    /**
     * Constructor parcial 2 | Inicializa todas las variables salvo el ID (ID = 0)
     * @param nombre Nombre de voz
     * @param texto Texto como encabezado en el panel de voz
     * @param deAgrupacion Agrupación a la que pertenece
     */
    public Voz(String nombre, String texto, Agrupacion deAgrupacion) {
        this.id = 0;
        this.nombre = nombre;
        this.texto = texto;
        this.deAgrupacion = deAgrupacion;
    }
    
    /**
     * Constructor completo
     * @param id ID de voz
     * @param nombre Nombre de voz
     * @param texto Texto como encabezado en el panel de voz
     * @param deAgrupacion Agrupación a la que pertenece
     */
    public Voz(Integer id, String nombre, String texto, Agrupacion deAgrupacion) {
        this.id = id;
        this.nombre = nombre;
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
        return "Voz{" + "id=" + id + ", nombre=" + nombre + ", texto=" + texto + ", dePiezaMusical=" + deAgrupacion + '}';
    }
}
