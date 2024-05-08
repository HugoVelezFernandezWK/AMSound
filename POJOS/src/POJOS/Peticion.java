
package POJOS;

import java.io.Serializable;

/**
 *
 * @author Hugo Vélez Fernández
 */
public class Peticion implements Serializable {
    
    public static final long serialVersionUID = 7L;
    
    private Integer idPeticion;
    private Integer idEntidad;
    private Object entidad;

    public Peticion() {
    }

    public Peticion(Integer idPeticion, Integer idEntidad, Object entidad) {
        this.idPeticion = idPeticion;
        this.idEntidad = idEntidad;
        this.entidad = entidad;
    }

    public Integer getIdPeticion() {
        return idPeticion;
    }

    public void setIdPeticion(Integer idPeticion) {
        this.idPeticion = idPeticion;
    }

    public Integer getIdEntidad() {
        return idEntidad;
    }

    public void setIdEntidad(Integer idEntidad) {
        this.idEntidad = idEntidad;
    }

    public Object getEntidad() {
        return entidad;
    }

    public void setEntidad(Object entidad) {
        this.entidad = entidad;
    }

    @Override
    public String toString() {
        return "Peticion{" + "idPeticion=" + idPeticion + ", idEntidad=" + idEntidad + ", entidad=" + entidad + '}';
    }
}
