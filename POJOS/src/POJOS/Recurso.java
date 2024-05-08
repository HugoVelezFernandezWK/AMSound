
package POJOS;

import java.io.Serializable;

/**
 * Clase que almacena los datos del recurso
 * @author Hugo Vélez Fernández
 * @version 1.0
 * @since 1.0
 */
public class Recurso implements Serializable {
    
    /**
     * Numero de serialización
     */
    public static final long serialVersionUID = 5L;
    
    private Integer id;
    private String titulo;
    private String ruta;
    private String texto;
    private Voz deVoz;
    private PiezaMusical dePiezaMusical;
    
    /**
     * Constructor vacio | Valorres por defecto (ID = 0)
     */
    public Recurso(){
        this.id = 0;
    }
    
    /**
     * Constructor parcial | Inicializa ID
     * @param id ID de recurso
     */
    public Recurso(Integer id){
        this.id = id;
    }

    /**
     * Constructor parcial 2 | Inicializa todas las variables salvo el ID (ID = 0)
     * @param titulo Titulo del recurso
     * @param ruta Ruta del archivo
     * @param texto Texto esplicativo
     * @param deVoz Voz a la que pertenece
     * @param dePiezaMusical Pieza musical a la que pertenece
     */
    public Recurso(String titulo, String ruta, String texto, Voz deVoz, PiezaMusical dePiezaMusical) {
        this.id = 0;
        this.titulo = titulo;
        this.ruta = ruta;
        this.texto = texto;
        this.deVoz = deVoz;
        this.dePiezaMusical = dePiezaMusical;
    }
    
    /**
     * Constructor completo
     * @param id ID de recurso
     * @param titulo Titulo del recurso
     * @param ruta Ruta del archivo
     * @param texto Texto esplicativo
     * @param deVoz Voz a la que pertenece
     * @param dePiezaMusical Pieza musical a la que pertenece
     */
    public Recurso(Integer id, String titulo, String ruta, String texto, Voz deVoz, PiezaMusical dePiezaMusical) {
        this.id = id;
        this.titulo = titulo;
        this.ruta = ruta;
        this.texto = texto;
        this.deVoz = deVoz;
        this.dePiezaMusical = dePiezaMusical;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Voz getDeVoz() {
        return deVoz;
    }

    public void setDeVoz(Voz deVoz) {
        this.deVoz = deVoz;
    }

    public PiezaMusical getDePiezaMusical() {
        return dePiezaMusical;
    }

    public void setDePiezaMusical(PiezaMusical dePiezaMusical) {
        this.dePiezaMusical = dePiezaMusical;
    }

    @Override
    public String toString() {
        return "Recurso{" + "id=" + id + ", titulo=" + titulo + ", ruta=" + ruta + ", texto=" + texto + ", deVoz=" + deVoz + ", dePiezaMusical=" + dePiezaMusical + '}';
    }
}
