
package POJOS;

import java.io.Serializable;

/**
 *
 * @author Hugo Vélez Fernández
 */
public class ExcepcionAMSound extends Exception implements Serializable {
    
    public static final long serialVersionUID = 6L;
    
    // Atributos
    private String mensajeUsuario;
    private String mensajeErrorBD;
    private String sentenciaSQL;
    private Integer codigoErrorBD;
    
    /**
     * Constructor vacio | Valores a null
     */
    public ExcepcionAMSound(){}

    /**
     * Constructor completo | Valores definidos por parametro
     * 
     * @param mensajeUsuario
     * @param mensajeErrorBD
     * @param sentenciaSQL
     * @param codigoErrorBD 
     */
    public ExcepcionAMSound(String mensajeUsuario, String mensajeErrorBD, String sentenciaSQL, Integer codigoErrorBD) {
        this.mensajeUsuario = mensajeUsuario;
        this.mensajeErrorBD = mensajeErrorBD;
        this.sentenciaSQL = sentenciaSQL;
        this.codigoErrorBD = codigoErrorBD;
    }

    /**
     * Devuelve el mensaje de error para el usuario
     * @return 
     */
    public String getMensajeUsuario() {
        return mensajeUsuario;
    }

    /**
     * Modifica el mensaje de error para el usuario
     * @param mensajeUsuario 
     */
    public void setMensajeUsuario(String mensajeUsuario) {
        this.mensajeUsuario = mensajeUsuario;
    }

    /**
     * Devuelve el mensaje de error de la base de datos
     * @return 
     */
    public String getMensajeErrorBD() {
        return mensajeErrorBD;
    }

    /**
     * Modifica el mensaje de error de la base de datos
     * @param mensajeErrorBD 
     */
    public void setMensajeErrorBD(String mensajeErrorBD) {
        this.mensajeErrorBD = mensajeErrorBD;
    }

    /**
     * Devuelve la sentencia SQL introducida
     * @return 
     */
    public String getSentenciaSQL() {
        return sentenciaSQL;
    }

    /**
     * Modifica la sentencia SQL introducida
     * @param sentenciaSQL 
     */
    public void setSentenciaSQL(String sentenciaSQL) {
        this.sentenciaSQL = sentenciaSQL;
    }

    /**
     * Devuelve el codigo de error de la base de datos
     * @return 
     */
    public Integer getCodigoErrorBD() {
        return codigoErrorBD;
    }

    /**
     * Modifica el codigo de error de la base de datos
     * @param codigoErrorBD 
     */
    public void setCodigoErrorBD(Integer codigoErrorBD) {
        this.codigoErrorBD = codigoErrorBD;
    }

    /**
     * Muestra el valor de todos los atributos de la clase
     * @return String con los valores
     */
    @Override
    public String toString() {
        return "ExcepcionSmartLock{" + "mensajeUsuario=" + mensajeUsuario + ", mensajeErrorBD=" + mensajeErrorBD + ", sentenciaSQL=" + sentenciaSQL + ", codigoErrorBD=" + codigoErrorBD + '}';
    }
    
}
