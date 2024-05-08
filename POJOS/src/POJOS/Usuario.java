
package POJOS;

import java.io.Serializable;

/**
 * Clase que almacena los datos del usuario
 * @author Hugo Vélez Fernández
 * @version 1.0
 * @since 1.0
 */
public class Usuario implements Serializable {
    
    /**
     * Numero de serialización
     */
    public static final long serialVersionUID = 2L;
    
    private Integer id;
    private String nombre;
    private String mail;
    private String telefono;
    private String contrasena;
    private String foto;
    
    /**
     * Constructor vacio | Valorres por defecto (ID = 0)
     */
    public Usuario(){
        this.id = 0;
    }
    
    /**
     * Constructor parcial | Inicializa ID
     * @param id ID de usuario
     */
    public Usuario(Integer id){
        this.id = id;
    }
    
    /**
     * Constructor parcial 2 | Inicializa todas las variables salvo el ID (ID = 0)
     * @param nombre Nombre del usuario
     * @param mail Correo electronico del usuario
     * @param telefono Telefono del usuario
     * @param contrasena Contraseña del usuario
     * @param foto Foto de perfil del usuario
     */
    public Usuario(String nombre, String mail, String telefono, String contrasena, String foto) {
        this.id = 0;
        this.nombre = nombre;
        this.mail = mail;
        this.telefono = telefono;
        this.contrasena = contrasena;
        this.foto = foto;
    }

    /**
     * Constructor completo
     * @param id id ID de usuario
     * @param nombre Nombre del usuario
     * @param mail Correo electronico del usuario
     * @param telefono Telefono del usuario
     * @param contrasena Contraseña del usuario
     * @param foto Foto de perfil del usuario
     */
    public Usuario(Integer id, String nombre, String mail, String telefono, String contrasena, String foto) {
        this.id = id;
        this.nombre = nombre;
        this.mail = mail;
        this.telefono = telefono;
        this.contrasena = contrasena;
        this.foto = foto;
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

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    @Override
    public String toString() {
        return "Usuario{" + "id=" + id + ", nombre=" + nombre + ", mail=" + mail + ", telefono=" + telefono + ", contrasena=" + contrasena + ", foto=" + foto + '}';
    }
    
}
