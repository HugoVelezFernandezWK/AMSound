
package POJOS;

import POJOS.ExcepcionAMSound;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Hugo Vélez Fernández
 */
public class Respuesta implements Serializable {
    
    public static final long serialVersionUID = 8L;
    
    private Integer idOperacion;
    private Integer cantidad;
    private Object entidad;
    private ArrayList<Object> listaEntidades;
    private ExcepcionAMSound e;

    public Respuesta() {
    }

    public Respuesta(Integer idOperacion, Integer cantidad, Object entidad, ArrayList<Object> listaEntidades, ExcepcionAMSound e) {
        this.idOperacion = idOperacion;
        this.cantidad = cantidad;
        this.entidad = entidad;
        this.listaEntidades = listaEntidades;
        this.e = e;
    }

    public Integer getIdOperacion() {
        return idOperacion;
    }

    public void setIdOperacion(Integer idOperacion) {
        this.idOperacion = idOperacion;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Object getEntidad() {
        return entidad;
    }

    public void setEntidad(Object entidad) {
        this.entidad = entidad;
    }

    public ArrayList<Object> getListaEntidades() {
        return listaEntidades;
    }

    public void setListaEntidades(ArrayList<Object> listaEntidades) {
        this.listaEntidades = listaEntidades;
    }

    public ExcepcionAMSound getE() {
        return e;
    }

    public void setE(ExcepcionAMSound e) {
        this.e = e;
    }

    @Override
    public String toString() {
        return "Respuesta{" + "idOperacion=" + idOperacion + ", cantidad=" + cantidad + ", entidad=" + entidad + ", listaEntidades=" + listaEntidades + ", e=" + e + '}';
    }
    
}
