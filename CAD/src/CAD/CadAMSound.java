package CAD;


import POJOS.*;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Hugo Vélez Fernández
 */
public class CadAMSound {

    // Atributos
    private Connection conn;
    private String servidor, usuario, contrasenha;
    
    /**
     * Constructor parcial | Cambia la dirección IPv4 del servidor donde se encuentra la base de datos
     * @param ip Direccion IP
     * @throws ExcepcionAMSound Se produce cuando el driver de conexion falla
     */
    public CadAMSound(String ip) throws ExcepcionAMSound{
        
        this.servidor = "jdbc:oracle:thin:@"+ip+":1521:free";
        this.usuario = "amsound";
        this.contrasenha = "kk";
        
        try {
            
            Class.forName("oracle.jdbc.driver.OracleDriver"); // Cargar driver
            
        } catch (ClassNotFoundException ex) {
            
            ExcepcionAMSound e = new ExcepcionAMSound();
            e.setMensajeErrorBD(ex.getMessage());
            e.setMensajeUsuario("Error general del sistema. Consulte con el administrador");
            throw e;
            
        }
    }
    
    /**
     * Constructor completo | IPv4, usuuario y contrasena introducidas por parametro
     * @param ip Direccion IP
     * @param usuario Usuario de la BD
     * @param contrasenha Contrasena de la BD
     * @throws ExcepcionAMSound Se produce cuando el driver de conexion falla
     */
    public CadAMSound (String ip, String usuario, String contrasenha) throws ExcepcionAMSound{
        
        this.servidor = "jdbc:oracle:thin:@"+ip+":1521:free";
        this.usuario = usuario;
        this.contrasenha = contrasenha;
        
        try {
            
            Class.forName("oracle.jdbc.driver.OracleDriver"); // Cargar driver
            
        } catch (ClassNotFoundException ex) {
            
            ExcepcionAMSound e = new ExcepcionAMSound();
            e.setMensajeErrorBD(ex.getMessage());
            e.setMensajeUsuario("Error general del sistema. Consulte con el administrador");
            throw e;
            
        }
    }
    
    /**
     * Permite establecer la conexion con la base de datos
     * @throws ExcepcionAMSound Se produce cuando la cadena de conexion falla
     */
    private void conectar() throws ExcepcionAMSound{
        try {
            
            conn = DriverManager.getConnection(servidor, usuario, contrasenha); // Conexion BD
            
        } catch (SQLException ex) {
            
            ExcepcionAMSound e = new ExcepcionAMSound();
            e.setMensajeErrorBD(ex.getMessage());
            e.setMensajeUsuario("Error general del sistema. Consulte con el administrador");
            throw e;
            
        }
    }
    
    ////////////////////////////////////////    
    ////////////////////////////////////////    
    ////////////////////////////////////////    
    ////////////////////////////////////////    
    ////////////////////////////////////////    
    ////////////////////////////////////////    
    
    /**
     * Inserta un nuevo usuario a la base de datos
     * 
     * @param usuario Usuario a insertar
     * @return Devielve la cantidad de registros afectados
     * @throws ExcepcionAMSound Se puede producir por no introducir todos los datos necesarios y por violar una FK, una CHECK constraint o un campo UNIQE
     */
    public int insertarUsuario(Usuario usuario) throws ExcepcionAMSound {
        
        int registrosAfectados = 0;
        String sql = "insert into USUARIO values (SEQ_USUARIO.NEXTVAL, ?,?,?,?,?)";
        
        try{
            
            conectar();
            PreparedStatement sentPrep = conn.prepareStatement(sql); // Crear sentencia preparada
            
            // Introducir datos
            sentPrep.setString(1, usuario.getNombre());
            sentPrep.setString(2, usuario.getMail());
            sentPrep.setString(3, usuario.getTelefono());
            sentPrep.setString(4, usuario.getContrasena());
            sentPrep.setString(5, usuario.getFoto());

            registrosAfectados = sentPrep.executeUpdate(); // Ejecutar la sentencia preparada
            sentPrep.close(); // Cerrar sentencia
            conn.close();// Cerrar la conexion con la base de datos
            
        } catch (SQLException ex) {

            ExcepcionAMSound e = new ExcepcionAMSound();
            e.setCodigoErrorBD(ex.getErrorCode());
            e.setMensajeErrorBD(ex.getMessage());
            e.setSentenciaSQL(sql);
            
            switch(ex.getErrorCode()){
                // Update NULL
                case 1400: e.setMensajeUsuario("Todos los campos son obligatorios"); break;
                // Update Violacion de Check Constraint
                case 2290: e.setMensajeUsuario("Introduzca un mail valido"); break;
                // UNIQUE
                case 1: e.setMensajeUsuario("El telefono o mail ya se han registrado"); break;
                // Registro demasiado grande
                case 12899: e.setMensajeUsuario("Se ha sobrepasado la cantidad de caracteres permitidos. Posible error: Telefono"); break;
                // Otros errores
                default: e.setMensajeUsuario("Error general del sistema. Consulte con el administrador"); break;
            }
            
            throw e;
            
        } catch (Exception ex){
            
            ExcepcionAMSound e = new ExcepcionAMSound();
            e.setMensajeErrorBD(ex.getMessage());
            e.setMensajeUsuario("Error general del sistema. Consulte con el administrador");
            throw e;
        }
        
        return registrosAfectados;
    }
    
    /**
     * Inserta una nueva agrupacion a la base de datos
     * 
     * @param agrupacion Datos de la agrupacion
     * @return Registros afectados
     * @throws ExcepcionAMSound Se puede producir por no introducir todos los datos necesarios y por violar una FK o un campo UNIQE
     */
    public int insertarAgrupacion(Agrupacion agrupacion) throws ExcepcionAMSound{
        
        int registrosAfectados = 0;
        String sql = "insert into AGRUPACION values (SEQ_AGRUPACION.NEXTVAL, ?,?,?,?,?)";
        
        try{
            
            conectar();
            PreparedStatement sentPrep = conn.prepareStatement(sql); // Crear sentencia preparada
            
            // Introducir datos
            sentPrep.setString(1, agrupacion.getNombre());
            sentPrep.setString(2, agrupacion.getContacto());
            sentPrep.setString(3, agrupacion.getTexto());
            sentPrep.setString(4, agrupacion.getFoto());
            sentPrep.setObject(5, agrupacion.getDirector().getId(), java.sql.Types.INTEGER);

            registrosAfectados = sentPrep.executeUpdate(); // Ejecutar la sentencia preparada
            sentPrep.close(); // Cerrar sentencia
            conn.close();// Cerrar la conexion con la base de datos
            
        } catch (SQLException ex) {

            ExcepcionAMSound e = new ExcepcionAMSound();
            e.setCodigoErrorBD(ex.getErrorCode());
            e.setMensajeErrorBD(ex.getMessage());
            e.setSentenciaSQL(sql);
            
            switch(ex.getErrorCode()){
                // Update NULL
                case 1400: e.setMensajeUsuario("El nombre y contacto son obligatorios"); break;
                // UNIQUE
                case 1: e.setMensajeUsuario("Esta agrupación ya existe"); break;
                // Registro demasiado grande
                case 12899: e.setMensajeUsuario("Se ha sobrepasado la cantidad de caracteres permitidos"); break;
                // Otros errores
                default: e.setMensajeUsuario("Error general del sistema. Consulte con el administrador"); break;
            }
            
            throw e;
            
        } catch (Exception ex){
            
            ExcepcionAMSound e = new ExcepcionAMSound();
            e.setMensajeErrorBD(ex.getMessage());
            e.setMensajeUsuario("Error general del sistema. Consulte con el administrador");
            throw e;
        }
        
        return registrosAfectados;
    }
    
    /**
     * Inserta una nueva pieza musical a la base de datos
     * 
     * @param piezaMusical Datos de pieza musical
     * @return Registros afectados
     * @throws ExcepcionAMSound Se puede producir por no introducir todos los datos necesarios
     */
    public int insertarPiezaMusical(PiezaMusical piezaMusical) throws ExcepcionAMSound{
        
        int registrosAfectados = 0;
        String sql = "insert into PIEZA_MUSICAL values (SEQ_PIEZA_MUSICAL.NEXTVAL, ?,?,?,?)";
        
        try{
            
            conectar();
            PreparedStatement sentPrep = conn.prepareStatement(sql); // Crear sentencia preparada
            
            // Introducir datos
            sentPrep.setString(1, piezaMusical.getNombre());
            sentPrep.setString(2, piezaMusical.getAutor());
            sentPrep.setString(3, piezaMusical.getTexto());
            sentPrep.setObject(4, piezaMusical.getDeAgrupacion().getId(), java.sql.Types.INTEGER);

            registrosAfectados = sentPrep.executeUpdate(); // Ejecutar la sentencia preparada
            sentPrep.close(); // Cerrar sentencia
            conn.close();// Cerrar la conexion con la base de datos
            
        } catch (SQLException ex) {

            ExcepcionAMSound e = new ExcepcionAMSound();
            e.setCodigoErrorBD(ex.getErrorCode());
            e.setMensajeErrorBD(ex.getMessage());
            e.setSentenciaSQL(sql);
            
            switch(ex.getErrorCode()){
                // Update NULL
                case 1400: e.setMensajeUsuario("El nombre y autor son obligatorios"); break;
                // Registro demasiado grande
                case 12899: e.setMensajeUsuario("Se ha sobrepasado la cantidad de caracteres permitidos"); break;
                // Otros errores
                default: e.setMensajeUsuario("Error general del sistema. Consulte con el administrador"); break;
            }
            
            throw e;
            
        } catch (Exception ex){
            
            ExcepcionAMSound e = new ExcepcionAMSound();
            e.setMensajeErrorBD(ex.getMessage());
            e.setMensajeUsuario("Error general del sistema. Consulte con el administrador");
            throw e;
        }
        
        return registrosAfectados;
    }
    
    /**
     * Inserta una nueva voz a la base de datos
     * 
     * @param voz Datos de voz
     * @return Registros afectados
     * @throws ExcepcionAMSound Se puede producir por no introducir todos los datos necesarios
     */
    public int insertarVoz(Voz voz) throws ExcepcionAMSound{
        
        int registrosAfectados = 0;
        String sql = "insert into VOZ values (SEQ_VOZ.NEXTVAL, ?,?,?)";
        
        try{
            
            conectar();
            PreparedStatement sentPrep = conn.prepareStatement(sql); // Crear sentencia preparada
            
            // Introducir datos
            sentPrep.setString(1, voz.getNombre());
            sentPrep.setString(2, voz.getTexto());
            sentPrep.setObject(3, voz.getDeAgrupacion().getId(), java.sql.Types.INTEGER);

            registrosAfectados = sentPrep.executeUpdate(); // Ejecutar la sentencia preparada
            sentPrep.close(); // Cerrar sentencia
            conn.close();// Cerrar la conexion con la base de datos
            
        } catch (SQLException ex) {

            ExcepcionAMSound e = new ExcepcionAMSound();
            e.setCodigoErrorBD(ex.getErrorCode());
            e.setMensajeErrorBD(ex.getMessage());
            e.setSentenciaSQL(sql);
            
            switch(ex.getErrorCode()){
                // Update NULL
                case 1400: e.setMensajeUsuario("El nombre es obligatorio"); break;
                // Registro demasiado grande
                case 12899: e.setMensajeUsuario("Se ha sobrepasado la cantidad de caracteres permitidos"); break;
                // Otros errores
                default: e.setMensajeUsuario("Error general del sistema. Consulte con el administrador"); break;
            }
            
            throw e;
            
        } catch (Exception ex){
            
            ExcepcionAMSound e = new ExcepcionAMSound();
            e.setMensajeErrorBD(ex.getMessage());
            e.setMensajeUsuario("Error general del sistema. Consulte con el administrador");
            throw e;
        }
        
        return registrosAfectados;
    }
    
    /**
     * Inserta un nuevo recurso a la base de datos
     * 
     * @param recurso Datos del recurso
     * @return Registros afectados
     * @throws ExcepcionAMSound Se puede producir por no introducir todos los datos necesarios
     */
    public int insertarRecurso(Recurso recurso) throws ExcepcionAMSound{
        
        int registrosAfectados = 0;
        String sql = "insert into RECURSO values (SEQ_VOZ.NEXTVAL, ?,?,?,?,?)";
        
        try{
            
            conectar();
            PreparedStatement sentPrep = conn.prepareStatement(sql); // Crear sentencia preparada
            
            // Introducir datos
            sentPrep.setString(1, recurso.getTitulo());
            sentPrep.setString(2, recurso.getRuta());
            sentPrep.setString(3, recurso.getTexto());
            sentPrep.setObject(4, recurso.getDeVoz().getId(), java.sql.Types.INTEGER);
            sentPrep.setObject(5, recurso.getDePiezaMusical().getId(), java.sql.Types.INTEGER);

            registrosAfectados = sentPrep.executeUpdate(); // Ejecutar la sentencia preparada
            sentPrep.close(); // Cerrar sentencia
            conn.close();// Cerrar la conexion con la base de datos
            
        } catch (SQLException ex) {

            ExcepcionAMSound e = new ExcepcionAMSound();
            e.setCodigoErrorBD(ex.getErrorCode());
            e.setMensajeErrorBD(ex.getMessage());
            e.setSentenciaSQL(sql);
            
            switch(ex.getErrorCode()){
                // Update NULL
                case 1400: e.setMensajeUsuario("El nombre es obligatorio"); break;
                // Registro demasiado grande
                case 12899: e.setMensajeUsuario("Se ha sobrepasado la cantidad de caracteres permitidos"); break;
                // Otros errores
                default: e.setMensajeUsuario("Error general del sistema. Consulte con el administrador"); break;
            }
            
            throw e;
            
        } catch (Exception ex){
            
            ExcepcionAMSound e = new ExcepcionAMSound();
            e.setMensajeErrorBD(ex.getMessage());
            e.setMensajeUsuario("Error general del sistema. Consulte con el administrador");
            throw e;
        }
        
        return registrosAfectados;
    }
    
    /**
     * Modifica el usuario indicada con los datos introducidos por parametro
     * 
     * @param id ID del usuario que se actualiza
     * @param usuario Usuario con los nuevos datos
     * @return Registros afectados 
     * @throws ExcepcionAMSound Se puede producir por no introducir todos los datos necesarios y por violar una FK, una CHECK constraint o un campo UNIQE
     */
    public int modificarUsuario(Integer id, Usuario usuario) throws ExcepcionAMSound {
         
        int registrosAfectados = 0;
        String sql = "update USUARIO set NOMBRE=?, MAIL=?, TELEFONO=?, CONTRASENA=?, FOTO=? where ID=?";
        
        try{
            
            conectar();
            PreparedStatement sentPrep = conn.prepareStatement(sql); // Crear sentencia preparada
            
            // Introducir datos
            sentPrep.setString(1, usuario.getNombre());
            sentPrep.setString(2, usuario.getMail());
            sentPrep.setString(3, usuario.getTelefono());
            sentPrep.setString(4, usuario.getContrasena());
            sentPrep.setString(5, usuario.getFoto());
            sentPrep.setObject(6, id,java.sql.Types.INTEGER); // Condicion WHERE

            registrosAfectados = sentPrep.executeUpdate(); // Ejecutar la sentencia preparada
            sentPrep.close(); // Cerrar sentencia
            conn.close();// Cerrar la conexion con la base de datos
            
        } catch (SQLException ex) {

            ExcepcionAMSound e = new ExcepcionAMSound();
            e.setCodigoErrorBD(ex.getErrorCode());
            e.setMensajeErrorBD(ex.getMessage());
            e.setSentenciaSQL(sql);
            
            switch(ex.getErrorCode()){
                // Update NULL
                case 1407: e.setMensajeUsuario("Todos los campos son obligatorios"); break;
                // Update UNIQE
                case 1: e.setMensajeUsuario("Ya existe un usuario con el mismo mail o telefono"); break;
                // Update Violacion de Check Constraint
                case 2290: e.setMensajeUsuario("Se debe introducir un correo electronico valido"); break;
                // Registro demasiado grande
                case 12899: e.setMensajeUsuario("Se ha sobrepasado la cantidad de caracteres permitidos. Posible error: Telefono"); break;
                // Otros errores
                default: e.setMensajeUsuario("Error general del sistema. Consulte con el administrador"); break;
            }
            
            throw e;
            
        } catch(Exception ex){
            
            ExcepcionAMSound e = new ExcepcionAMSound();
            e.setMensajeErrorBD(ex.getMessage());
            e.setMensajeUsuario("Error general del sistema. Consulte con el administrador");
            throw e;
        }
        
        return registrosAfectados;
    }
    
    /**
     * Modifica una agrupacion
     * 
     * @param id ID de agrupacion a modificar
     * @param agrupacion Nuevos datos de agrupacion
     * @return Registros afectados
     * @throws ExcepcionAMSound Se puede producir por no introducir todos los datos necesarios y por violar una FK o un campo UNIQE
     */
    public int modificarAgrupacion(Integer id, Agrupacion agrupacion) throws ExcepcionAMSound{
        
        int registrosAfectados = 0;
        String sql = "update AGRUPACION set NOMBRE=?, CONTACTO=?, TEXTO=?, FOTO=?, DIRECTOR_DE_AGRUPACION=? where ID=?";
        
        try{
            
            conectar();
            PreparedStatement sentPrep = conn.prepareStatement(sql); // Crear sentencia preparada
            
            // Introducir datos
            sentPrep.setString(1, agrupacion.getNombre());
            sentPrep.setString(2, agrupacion.getContacto());
            sentPrep.setString(3, agrupacion.getTexto());
            sentPrep.setString(4, agrupacion.getFoto());
            sentPrep.setObject(5, agrupacion.getDirector().getId(), java.sql.Types.INTEGER);
            sentPrep.setObject(6, id,java.sql.Types.INTEGER); // Condicion WHERE

            registrosAfectados = sentPrep.executeUpdate(); // Ejecutar la sentencia preparada
            sentPrep.close(); // Cerrar sentencia
            conn.close();// Cerrar la conexion con la base de datos
            
        } catch (SQLException ex) {

            ExcepcionAMSound e = new ExcepcionAMSound();
            e.setCodigoErrorBD(ex.getErrorCode());
            e.setMensajeErrorBD(ex.getMessage());
            e.setSentenciaSQL(sql);
            
            switch(ex.getErrorCode()){
                // Update NULL
                case 1400: e.setMensajeUsuario("El nombre y contacto son obligatorios"); break;
                // UNIQUE
                case 1: e.setMensajeUsuario("Esta agrupación ya existe"); break;
                // Registro demasiado grande
                case 12899: e.setMensajeUsuario("Se ha sobrepasado la cantidad de caracteres permitidos"); break;
                // Otros errores
                default: e.setMensajeUsuario("Error general del sistema. Consulte con el administrador"); break;
            }
            
            throw e;
            
        } catch(Exception ex){
            
            ExcepcionAMSound e = new ExcepcionAMSound();
            e.setMensajeErrorBD(ex.getMessage());
            e.setMensajeUsuario("Error general del sistema. Consulte con el administrador");
            throw e;
        }
        
        return registrosAfectados;
    }
    
    /**
     * Modifica una pieza musical
     * 
     * @param id ID de pieza musical a modificar
     * @param piezaMusical Nuevos datos de pieza musical
     * @return Registros afectados
     * @throws ExcepcionAMSound Se puede producir por no introducir todos los datos necesarios
     */
    public int modificarPiezaMusical(Integer id, PiezaMusical piezaMusical) throws ExcepcionAMSound{
        
        int registrosAfectados = 0;
        String sql = "update PIEZA_MUSICAL set NOMBRE=?, AUTOR=?, TEXTO=?, PIEZA_DE_AGRUPACION=? where ID=?";
        
        try{
            
            conectar();
            PreparedStatement sentPrep = conn.prepareStatement(sql); // Crear sentencia preparada
            
            // Introducir datos
            sentPrep.setString(1, piezaMusical.getNombre());
            sentPrep.setString(2, piezaMusical.getAutor());
            sentPrep.setString(3, piezaMusical.getTexto());
            sentPrep.setObject(4, piezaMusical.getDeAgrupacion().getId(), java.sql.Types.INTEGER);
            sentPrep.setObject(5, id,java.sql.Types.INTEGER); // Condicion WHERE

            registrosAfectados = sentPrep.executeUpdate(); // Ejecutar la sentencia preparada
            sentPrep.close(); // Cerrar sentencia
            conn.close();// Cerrar la conexion con la base de datos
            
        } catch (SQLException ex) {

            ExcepcionAMSound e = new ExcepcionAMSound();
            e.setCodigoErrorBD(ex.getErrorCode());
            e.setMensajeErrorBD(ex.getMessage());
            e.setSentenciaSQL(sql);
            
            switch(ex.getErrorCode()){
                // Update NULL
                case 1400: e.setMensajeUsuario("El nombre y autor son obligatorios"); break;
                // Registro demasiado grande
                case 12899: e.setMensajeUsuario("Se ha sobrepasado la cantidad de caracteres permitidos"); break;
                // Otros errores
                default: e.setMensajeUsuario("Error general del sistema. Consulte con el administrador"); break;
            }
            
            throw e;
            
        } catch(Exception ex){
            
            ExcepcionAMSound e = new ExcepcionAMSound();
            e.setMensajeErrorBD(ex.getMessage());
            e.setMensajeUsuario("Error general del sistema. Consulte con el administrador");
            throw e;
        }
        
        return registrosAfectados;
    }
    
    /**
     * Modifica una voz
     * 
     * @param id ID de voz a modificar
     * @param voz Nuevos datos de voz
     * @return Registros afectados
     * @throws ExcepcionAMSound Se puede producir por no introducir todos los datos necesarios
     */
    public int modificarVoz(Integer id, Voz voz) throws ExcepcionAMSound{
        
        int registrosAfectados = 0;
        String sql = "update VOZ set NOMBRE=?, TEXTO=?, VOZ_DE_AGRUPACION=? where ID=?";
        
        try{
            
            conectar();
            PreparedStatement sentPrep = conn.prepareStatement(sql); // Crear sentencia preparada
            
            // Introducir datos
            sentPrep.setString(1, voz.getNombre());
            sentPrep.setString(2, voz.getTexto());
            sentPrep.setObject(3, voz.getDeAgrupacion().getId(), java.sql.Types.INTEGER);
            sentPrep.setObject(4, id,java.sql.Types.INTEGER); // Condicion WHERE

            registrosAfectados = sentPrep.executeUpdate(); // Ejecutar la sentencia preparada
            sentPrep.close(); // Cerrar sentencia
            conn.close();// Cerrar la conexion con la base de datos
            
        } catch (SQLException ex) {

            ExcepcionAMSound e = new ExcepcionAMSound();
            e.setCodigoErrorBD(ex.getErrorCode());
            e.setMensajeErrorBD(ex.getMessage());
            e.setSentenciaSQL(sql);
            
            switch(ex.getErrorCode()){
                // Update NULL
                case 1400: e.setMensajeUsuario("El nombre y autor son obligatorios"); break;
                // Registro demasiado grande
                case 12899: e.setMensajeUsuario("Se ha sobrepasado la cantidad de caracteres permitidos"); break;
                // Otros errores
                default: e.setMensajeUsuario("Error general del sistema. Consulte con el administrador"); break;
            }
            
            throw e;
            
        } catch(Exception ex){
            
            ExcepcionAMSound e = new ExcepcionAMSound();
            e.setMensajeErrorBD(ex.getMessage());
            e.setMensajeUsuario("Error general del sistema. Consulte con el administrador");
            throw e;
        }
        
        return registrosAfectados;
    }
    
    /**
     * Modifica un recurso
     * 
     * @param id ID de recurso a modificar
     * @param recurso Nuevos datos de recurso
     * @return Registros afectados
     * @throws ExcepcionAMSound Se puede producir por no introducir todos los datos necesarios
     */
    public int modificarRecurso(Integer id, Recurso recurso) throws ExcepcionAMSound{
        
        int registrosAfectados = 0;
        String sql = "update RECURSO set TITULO=?, RUTA_ARCHIVO=?, TEXTO=?, VOZ_DE_RECURSO=?, PIEZA_DE_RECURSO=? where ID=?";
        
        try{
            
            conectar();
            PreparedStatement sentPrep = conn.prepareStatement(sql); // Crear sentencia preparada
            
            // Introducir datos
            sentPrep.setString(1, recurso.getTitulo());
            sentPrep.setString(2, recurso.getRuta());
            sentPrep.setString(3, recurso.getTexto());
            sentPrep.setObject(4, recurso.getDeVoz().getId(), java.sql.Types.INTEGER);
            sentPrep.setObject(5, recurso.getDePiezaMusical().getId(), java.sql.Types.INTEGER);
            sentPrep.setObject(6, id,java.sql.Types.INTEGER); // Condicion WHERE

            registrosAfectados = sentPrep.executeUpdate(); // Ejecutar la sentencia preparada
            sentPrep.close(); // Cerrar sentencia
            conn.close();// Cerrar la conexion con la base de datos
            
        } catch (SQLException ex) {

            ExcepcionAMSound e = new ExcepcionAMSound();
            e.setCodigoErrorBD(ex.getErrorCode());
            e.setMensajeErrorBD(ex.getMessage());
            e.setSentenciaSQL(sql);
            
            switch(ex.getErrorCode()){
                // Update NULL
                case 1400: e.setMensajeUsuario("El nombre y autor son obligatorios"); break;
                // Registro demasiado grande
                case 12899: e.setMensajeUsuario("Se ha sobrepasado la cantidad de caracteres permitidos"); break;
                // Otros errores
                default: e.setMensajeUsuario("Error general del sistema. Consulte con el administrador"); break;
            }
            
            throw e;
            
        } catch(Exception ex){
            
            ExcepcionAMSound e = new ExcepcionAMSound();
            e.setMensajeErrorBD(ex.getMessage());
            e.setMensajeUsuario("Error general del sistema. Consulte con el administrador");
            throw e;
        }
        
        return registrosAfectados;
    }
    
    /**
     * Elimina el usuario
     * 
     * @param id ID del usuario a eliminar
     * @return Registros afectados
     * @throws ExcepcionAMSound Se produce cuando sucede un error inesperado
     */
    public int eliminarUsuario(Integer id) throws ExcepcionAMSound {
        
        int registrosAfectados = 0;
        // Sentencia SQL
        String agrupacion = "select ID from AGRUPACION where DIRECTOR_DE_AGRUPACION = " + id;
        
        // Agrupacion
        String sql1 = "delete RECURSO where PIEZA_DE_RECURSO in (select ID from PIEZA_MUSICAL where PIEZA_DE_AGRUPACION in ( " + agrupacion + "))";
        String sql2 = "delete PIEZA_MUSICAL where PIEZA_DE_AGRUPACION in (" + agrupacion + ")";
        String sql3 = "delete COMPONENTE_VOZ where VOZ in (select ID from VOZ where VOZ_DE_AGRUPACION in ( " + agrupacion + "))";
        String sql4 = "delete VOZ where VOZ_DE_AGRUPACION in ( " + agrupacion + ")";
        String sql5 = "delete from AGRUPACION where ID in ( " + agrupacion + ")";
        
        // Componente
        String sql6 = "delete COMPONENTE_VOZ where USUARIO_COMPONENTE = " + id;
        
        String sql7 = "delete from USUARIO where ID=" + id;
         
        try {
            
            conectar();
            Statement sent = conn.createStatement(); // Crear statement
            
            registrosAfectados += sent.executeUpdate(sql1); // Ejecutar sentencia SQL
            registrosAfectados += sent.executeUpdate(sql2);
            registrosAfectados += sent.executeUpdate(sql3);
            registrosAfectados += sent.executeUpdate(sql4);
            registrosAfectados += sent.executeUpdate(sql5);
            registrosAfectados += sent.executeUpdate(sql6);
            registrosAfectados += sent.executeUpdate(sql7);
            
            sent.close(); // Cerrar la sentencia
            conn.close(); // Cerrar la conexion con la base de datos
            
        }  catch (SQLException ex) {
            
            ExcepcionAMSound e = new ExcepcionAMSound();
            e.setCodigoErrorBD(ex.getErrorCode());
            e.setMensajeErrorBD(ex.getMessage());
            e.setMensajeUsuario("Error general del sistema. Consulte con el administrador");
            e.setSentenciaSQL(sql1+sql2+sql3+sql4+sql5+sql6+sql7);
            
            throw e;
            
        } catch(Exception ex){
            
            ExcepcionAMSound e = new ExcepcionAMSound();
            e.setMensajeErrorBD(ex.getMessage());
            e.setMensajeUsuario("Error general del sistema. Consulte con el administrador");
            throw e;
        }
         
        return registrosAfectados;
    }
    
    /**
     * Elimina la agrupacion
     * 
     * @param id ID de la agrupacion a eliminar
     * @return Registros afectados
     * @throws ExcepcionAMSound Se produce cuando sucede un error inesperado
     */
    public int eliminarAgrupacion(Integer id) throws ExcepcionAMSound{
        
        
        int registrosAfectados = 0;
        // Sentencia SQL
        String sql = "delete RECURSO where PIEZA_DE_RECURSO in (select ID from PIEZA_MUSICAL where PIEZA_DE_AGRUPACION = " + id + ")";
        String sql2 = "delete PIEZA_MUSICAL where PIEZA_DE_AGRUPACION = " + id;
        String sql3 = "delete COMPONENTE_VOZ where VOZ in (select ID from VOZ where VOZ_DE_AGRUPACION = " + id + ")";
        String sql4 = "delete VOZ where VOZ_DE_AGRUPACION = " + id;
        String sql5 = "delete from AGRUPACION where ID=" + id;
         
        try {
            
            conectar();
            Statement sent = conn.createStatement(); // Crear statement
            
            registrosAfectados += sent.executeUpdate(sql); // Ejecutar sentencia SQL
            registrosAfectados += sent.executeUpdate(sql2);
            registrosAfectados += sent.executeUpdate(sql3);
            registrosAfectados += sent.executeUpdate(sql4);
            registrosAfectados += sent.executeUpdate(sql5);
             
            sent.close(); // Cerrar la sentencia
            conn.close(); // Cerrar la conexion con la base de datos
            
        }  catch (SQLException ex) {
            
            ExcepcionAMSound e = new ExcepcionAMSound();
            e.setCodigoErrorBD(ex.getErrorCode());
            e.setMensajeErrorBD(ex.getMessage());
            e.setMensajeUsuario("Error general del sistema. Consulte con el administrador");
            e.setSentenciaSQL(sql+sql2+sql3+sql4+sql5);
            
            throw e;
            
        } catch(Exception ex){
            
            ExcepcionAMSound e = new ExcepcionAMSound();
            e.setMensajeErrorBD(ex.getMessage());
            e.setMensajeUsuario("Error general del sistema. Consulte con el administrador");
            throw e;
        }
         
        return registrosAfectados;
    }
    
    /**
     * Elimina la pieza musical y sus dependencias
     * 
     * @param id ID de la pieza musical a eliminar
     * @return Registros afectados
     * @throws ExcepcionAMSound Se produce cuando sucede un error inesperado
     */
    public int eliminarPiezaMusical(Integer id) throws ExcepcionAMSound{
        
        
        int registrosAfectados = 0;
        // Sentencia SQL
        String sql = "delete from RECURSO where PIEZA_DE_RECURSO=" + id;
        String sql2 = "delete from PIEZA_MUSICAL where ID=" + id;
         
        try {
            
            conectar();
            Statement sent = conn.createStatement(); // Crear statement
            
            registrosAfectados += sent.executeUpdate(sql); // Ejecutar sentencia SQL
            registrosAfectados += sent.executeUpdate(sql2);
            
            sent.close(); // Cerrar la sentencia
            conn.close(); // Cerrar la conexion con la base de datos
            
        }  catch (SQLException ex) {
            
            ExcepcionAMSound e = new ExcepcionAMSound();
            e.setCodigoErrorBD(ex.getErrorCode());
            e.setMensajeErrorBD(ex.getMessage());
            e.setMensajeUsuario("Error general del sistema. Consulte con el administrador");
            e.setSentenciaSQL(sql+sql2);
            
            throw e;
            
        } catch(Exception ex){
            
            ExcepcionAMSound e = new ExcepcionAMSound();
            e.setMensajeErrorBD(ex.getMessage());
            e.setMensajeUsuario("Error general del sistema. Consulte con el administrador");
            throw e;
        }
         
        return registrosAfectados;
    }
    
    /**
     * Elimina la voz y sus dependencias
     * 
     * @param id ID de voz a eliminar
     * @return Registros afectados
     * @throws ExcepcionAMSound Se produce cuando sucede un error inesperado
     */
    public int eliminarVoz(Integer id) throws ExcepcionAMSound{
        
        
        int registrosAfectados = 0;
        // Sentencia SQL
        String sql = "delete from RECURSO where VOZ_DE_RECURSO=" + id;
        String sql2 = "delete from COMPONENTE_VOZ where VOZ=" + id;
        String sql3 = "delete from VOZ where ID=" + id;
         
        try {
            
            conectar();
            Statement sent = conn.createStatement(); // Crear statement
            
            registrosAfectados += sent.executeUpdate(sql); // Ejecutar sentencia SQL
            registrosAfectados += sent.executeUpdate(sql2);
            registrosAfectados += sent.executeUpdate(sql3);
            
            sent.close(); // Cerrar la sentencia
            conn.close(); // Cerrar la conexion con la base de datos
            
        }  catch (SQLException ex) {
            
            ExcepcionAMSound e = new ExcepcionAMSound();
            e.setCodigoErrorBD(ex.getErrorCode());
            e.setMensajeErrorBD(ex.getMessage());
            e.setMensajeUsuario("Error general del sistema. Consulte con el administrador");
            e.setSentenciaSQL(sql+sql2+sql3);
            
            throw e;
            
        } catch(Exception ex){
            
            ExcepcionAMSound e = new ExcepcionAMSound();
            e.setMensajeErrorBD(ex.getMessage());
            e.setMensajeUsuario("Error general del sistema. Consulte con el administrador");
            throw e;
        }
         
        return registrosAfectados;
    }
    
    /**
     * Elimina el recurso
     * 
     * @param id ID del recurso a eliminar
     * @return Registros afectados
     * @throws ExcepcionAMSound Se produce cuando sucede un error inesperado
     */
    public int eliminarRecurso(Integer id) throws ExcepcionAMSound{
        
        int registrosAfectados = 0;
        // Sentencia SQL
        String sql = "delete from RECURSO where ID=" + id;
         
        try {
            
            conectar();
            Statement sent = conn.createStatement(); // Crear statement
            
            registrosAfectados = sent.executeUpdate(sql); // Ejecutar sentencia SQL
            sent.close(); // Cerrar la sentencia
            conn.close(); // Cerrar la conexion con la base de datos
            
        }  catch (SQLException ex) {
            
            ExcepcionAMSound e = new ExcepcionAMSound();
            e.setCodigoErrorBD(ex.getErrorCode());
            e.setMensajeErrorBD(ex.getMessage());
            e.setMensajeUsuario("Error general del sistema. Consulte con el administrador");
            e.setSentenciaSQL(sql);
            
            throw e;
            
        } catch(Exception ex){
            
            ExcepcionAMSound e = new ExcepcionAMSound();
            e.setMensajeErrorBD(ex.getMessage());
            e.setMensajeUsuario("Error general del sistema. Consulte con el administrador");
            throw e;
        }
         
        return registrosAfectados;
    }
    
    /**
     * Lee la informacion de un usuario en concreto
     * 
     * @param id ID del usuario a leer
     * @return Un objeto Usuario con la informacion 
     * @throws ExcepcionAMSound Se produce cuando sucede un error inesperado
     */
    public Usuario leerUsuario(Integer id) throws ExcepcionAMSound{
        
        Usuario datosUsuario = new Usuario();
        String sql = "select * from USUARIO where ID = ?";
        
        try {
            conectar();
            PreparedStatement sentPrep = conn.prepareStatement(sql);
            sentPrep.setObject(1, id,java.sql.Types.INTEGER); // Condicion WHERE
            ResultSet resSet = sentPrep.executeQuery(); // Ejecuta QUERY
            
            // Guarda los datos en un objeto
            while (resSet.next()) {
                datosUsuario.setId(((BigDecimal) resSet.getObject("ID")).intValue());
                datosUsuario.setNombre(resSet.getString("NOMBRE"));
                datosUsuario.setMail(resSet.getString("MAIL"));
                datosUsuario.setTelefono(resSet.getString("TELEFONO"));
                datosUsuario.setContrasena(resSet.getString("CONTRASENA"));
                datosUsuario.setFoto(resSet.getString("FOTO"));
            }
            
            // Cierra todos los objetos
            resSet.close();
            sentPrep.close();
            conn.close();
            
        } catch (Exception ex) {
            
            ExcepcionAMSound e = new ExcepcionAMSound();
            e.setMensajeErrorBD(ex.getMessage());
            e.setMensajeUsuario("Error general del sistema. Consulte con el administrador");
            throw e;   
        }
        
        return datosUsuario;
    }
    
    /**
     * Lee la información de una agrupación
     * 
     * @param id ID de agrupación
     * @return Agrupacion
     * @throws ExcepcionAMSound Se puede producir por un error inesperado
     */
    public Agrupacion leerAgrupacion(Integer id) throws ExcepcionAMSound{
        
        Agrupacion datosAgrupacion = new Agrupacion();
        Usuario datosDirector = new Usuario();
                
        // Sentencia SQL
        String sql = "SELECT " +
             "    A.ID AS ID_AGRUPACION, " +
             "    A.NOMBRE AS NOMBRE_AGRUPACION, " +
             "    A.CONTACTO AS CONTACTO_AGRUPACION, " +
             "    A.TEXTO AS TEXTO_AGRUPACION, " +
             "    A.FOTO AS FOTO_AGRUPACION, " +
             "    U.ID AS ID_USUARIO, " +
             "    U.NOMBRE AS NOMBRE_USUARIO, " +
             "    U.MAIL AS EMAIL_USUARIO, " +
             "    U.TELEFONO AS TELEFONO_USUARIO, " +
             "    U.CONTRASENA AS CONTRASENA_USUARIO, " +
             "    U.FOTO AS FOTO_USUARIO " +
             "FROM " +
             "    AGRUPACION A " +
             "JOIN " +
             "    USUARIO U ON A.DIRECTOR_DE_AGRUPACION = U.ID" +
             "WHERE A.ID = " + id;
        
        try {
            
            conectar();
            Statement sent = conn.createStatement(); // Crear statement
            ResultSet resSet = sent.executeQuery(sql); // Ejecuta QUERY
            
            // Guarda los datos en un objeto
            while (resSet.next()) {
                
                datosAgrupacion.setId(((BigDecimal) resSet.getObject("ID_AGRUPACION")).intValue());
                datosAgrupacion.setNombre(resSet.getString("NOMBRE_AGRUPACION"));
                datosAgrupacion.setContacto(resSet.getString("CONTACTO_AGRUPACION"));
                datosAgrupacion.setTexto(resSet.getString("TEXTO_AGRUPACION"));
                datosAgrupacion.setFoto(resSet.getString("FOTO_AGRUPACION"));
                
                    datosDirector.setId(((BigDecimal) resSet.getObject("ID_USUARIO")).intValue());
                    datosDirector.setNombre(resSet.getString("NOMBRE_USUARIO"));
                    datosDirector.setMail(resSet.getString("EMAIL_USUARIO"));
                    datosDirector.setTelefono(resSet.getString("TELEFONO_USUARIO"));
                    datosDirector.setContrasena(resSet.getString("CONTRASENA_USUARIO"));
                    datosDirector.setFoto(resSet.getString("FOTO_USUARIO"));
                
                datosAgrupacion.setDirector(datosDirector);
            }
            
            // Cierra todos los objetos
            resSet.close();
            sent.close();
            conn.close();
            
        } catch (Exception ex) {
            
            ExcepcionAMSound e = new ExcepcionAMSound();
            e.setMensajeErrorBD(ex.getMessage());
            e.setMensajeUsuario("Error general del sistema. Consulte con el administrador");
            throw e;
        }
        
        return datosAgrupacion;
    }
    
    /**
     * Lee la información de una pieza musical
     * 
     * @param id ID de pieza musical
     * @return Pieza musical
     * @throws ExcepcionAMSound Se puede producir por un error inesperado
     */
    public PiezaMusical leerPiezaMusical(Integer id) throws ExcepcionAMSound{
        
        PiezaMusical datosPiezaMusical = new PiezaMusical();
        Agrupacion datosAgrupacion = new Agrupacion();
        Usuario datosDirector = new Usuario();
        
        String sql = "SELECT " +
               "    PM.ID AS ID_PIEZA_MUSICAL, " +
               "    PM.NOMBRE AS NOMBRE_PIEZA, " +
               "    PM.AUTOR AS AUTOR_PIEZA, " +
               "    PM.TEXTO AS TEXTO_PIEZA, " +
               "    A.ID AS ID_AGRUPACION, " +
               "    A.NOMBRE AS NOMBRE_AGRUPACION, " +
               "    A.CONTACTO AS CONTACTO_AGRUPACION, " +
               "    A.TEXTO AS TEXTO_AGRUPACION, " +
               "    A.FOTO AS FOTO_AGRUPACION, " +
               "    U.ID AS ID_USUARIO, " +
               "    U.NOMBRE AS NOMBRE_USUARIO, " +
               "    U.MAIL AS MAIL_USUARIO, " +
               "    U.TELEFONO AS TELEFONO_USUARIO, " +
               "    U.CONTRASENA AS CONTRASENA_USUARIO, " +
               "    U.FOTO AS FOTO_USUARIO " +
               "FROM " +
               "    PIEZA_MUSICAL PM " +
               "JOIN " +
               "    AGRUPACION A ON PM.PIEZA_DE_AGRUPACION = A.ID " +
               "JOIN " +
               "    USUARIO U ON A.DIRECTOR_DE_AGRUPACION = U.ID " +
               "WHERE PM.ID = " + id;
        
        try {
            
            conectar();
            Statement sent = conn.createStatement(); // Crear statement
            ResultSet resSet = sent.executeQuery(sql); // Ejecuta QUERY
            
            // Guarda los datos en un objeto
            while (resSet.next()) {
                
                datosPiezaMusical.setId(((BigDecimal) resSet.getObject("ID_PIEZA_MUSICAL")).intValue());
                datosPiezaMusical.setNombre(resSet.getString("NOMBRE_PIEZA"));
                datosPiezaMusical.setAutor(resSet.getString("AUTOR_PIEZA"));
                datosPiezaMusical.setTexto(resSet.getString("TEXTO_PIEZA"));

                    datosAgrupacion.setId(((BigDecimal) resSet.getObject("ID_AGRUPACION")).intValue());
                    datosAgrupacion.setNombre(resSet.getString("NOMBRE_AGRUPACION"));
                    datosAgrupacion.setContacto(resSet.getString("CONTACTO_AGRUPACION"));
                    datosAgrupacion.setTexto(resSet.getString("TEXTO_AGRUPACION"));
                    datosAgrupacion.setFoto(resSet.getString("FOTO_AGRUPACION"));

                        datosDirector.setId(((BigDecimal) resSet.getObject("ID_AGRUPACION")).intValue());
                        datosDirector.setNombre(resSet.getString("NOMBRE_USUARIO"));
                        datosDirector.setMail(resSet.getString("MAIL_USUARIO"));
                        datosDirector.setTelefono(resSet.getString("TELEFONO_USUARIO"));
                        datosDirector.setContrasena(resSet.getString("CONTRASENA_USUARIO"));
                        datosDirector.setFoto(resSet.getString("FOTO_USUARIO"));

                    datosAgrupacion.setDirector(datosDirector);
                datosPiezaMusical.setDeAgrupacion(datosAgrupacion);
            }
            
            // Cierra todos los objetos
            resSet.close();
            sent.close();
            conn.close();
            
        } catch (Exception ex) {
            
            ExcepcionAMSound e = new ExcepcionAMSound();
            e.setMensajeErrorBD(ex.getMessage());
            e.setMensajeUsuario("Error general del sistema. Consulte con el administrador");
            throw e;
        }
        
        return datosPiezaMusical;
    }
    
    /**
     * Lee la información de una voz
     * 
     * @param id ID de voz
     * @return Voz
     * @throws ExcepcionAMSound Se puede producir por un error inesperado
     */
    public Voz leerVoz(Integer id) throws ExcepcionAMSound{
        
        Voz datosVoces = new Voz();
        Agrupacion datosAgrupacion = new Agrupacion();
        Usuario datosDirector = new Usuario();
        
        String sql = "SELECT " +
               "    V.ID AS ID_VOZ, " +
               "    V.NOMBRE AS NOMBRE_VOZ, " +
               "    V.TEXTO AS TEXTO_VOZ, " +
               "    A.ID AS ID_AGRUPACION, " +
               "    A.NOMBRE AS NOMBRE_AGRUPACION, " +
               "    A.CONTACTO AS CONTACTO_AGRUPACION, " +
               "    A.TEXTO AS TEXTO_AGRUPACION, " +
               "    A.FOTO AS FOTO_AGRUPACION, " +
               "    U.ID AS ID_USUARIO, " +
               "    U.NOMBRE AS NOMBRE_USUARIO, " +
               "    U.MAIL AS MAIL_USUARIO, " +
               "    U.TELEFONO AS TELEFONO_USUARIO, " +
               "    U.CONTRASENA AS CONTRASENA_USUARIO, " +
               "    U.FOTO AS FOTO_USUARIO " +
               "FROM " +
               "    VOZ V " +
               "JOIN " +
               "    AGRUPACION A ON V.VOZ_DE_AGRUPACION = A.ID " +
               "JOIN " +
               "    USUARIO U ON A.DIRECTOR_DE_AGRUPACION = U.ID " +
               "WHERE V.ID = " + id;
        
        try {
            
            conectar();
            Statement sent = conn.createStatement(); // Crear statement
            ResultSet resSet = sent.executeQuery(sql); // Ejecuta QUERY
            
            // Guarda los datos en un objeto
            while (resSet.next()) {
                
                datosVoces.setId(((BigDecimal) resSet.getObject("ID_VOZ")).intValue());
                datosVoces.setNombre(resSet.getString("NOMBRE_VOZ"));
                datosVoces.setTexto(resSet.getString("TEXTO_VOZ"));

                    datosAgrupacion.setId(((BigDecimal) resSet.getObject("ID_AGRUPACION")).intValue());
                    datosAgrupacion.setNombre(resSet.getString("NOMBRE_AGRUPACION"));
                    datosAgrupacion.setContacto(resSet.getString("CONTACTO_AGRUPACION"));
                    datosAgrupacion.setTexto(resSet.getString("TEXTO_AGRUPACION"));
                    datosAgrupacion.setFoto(resSet.getString("FOTO_AGRUPACION"));

                        datosDirector.setId(((BigDecimal) resSet.getObject("ID_AGRUPACION")).intValue());
                        datosDirector.setNombre(resSet.getString("NOMBRE_USUARIO"));
                        datosDirector.setMail(resSet.getString("MAIL_USUARIO"));
                        datosDirector.setTelefono(resSet.getString("TELEFONO_USUARIO"));
                        datosDirector.setContrasena(resSet.getString("CONTRASENA_USUARIO"));
                        datosDirector.setFoto(resSet.getString("FOTO_USUARIO"));

                    datosAgrupacion.setDirector(datosDirector);
                datosVoces.setDeAgrupacion(datosAgrupacion);
            }
            
            // Cierra todos los objetos
            resSet.close();
            sent.close();
            conn.close();
            
        } catch (Exception ex) {
           
            ExcepcionAMSound e = new ExcepcionAMSound();
            e.setMensajeErrorBD(ex.getMessage());
            e.setMensajeUsuario("Error general del sistema. Consulte con el administrador");
            throw e;
        }
        
        return datosVoces;
    }
    
    /**
     * Lee la información de un recurso
     * 
     * @param id ID de recurso
     * @return Recurso
     * @throws ExcepcionAMSound Se puede producir por un error inesperado
     */
    public Recurso leerRecurso(Integer id, boolean leerFKs) throws ExcepcionAMSound{
        
        Recurso datosRecurso = new Recurso();
        
        String sql = "SELECT " +
                      "    ID, " +
                      "    TITULO, " +
                      "    RUTA_ARCHIVO, " +
                      "    TEXTO, " +
                      "    VOZ_DE_RECURSO, " +
                      "    PIEZA_DE_RECURSO " +
                      "FROM " +
                      "    RECURSO";
        
        try {
            
            conectar();
            Statement sent = conn.createStatement(); // Crear statement
            ResultSet resSet = sent.executeQuery(sql); // Ejecuta QUERY
            
            // Guarda los datos en un objeto
            while (resSet.next()) {
                
                datosRecurso.setId(((BigDecimal) resSet.getObject("ID")).intValue());
                datosRecurso.setTitulo(resSet.getString("TITULO"));
                datosRecurso.setRuta(resSet.getString("RUTA_ARCHIVO"));
                datosRecurso.setTexto(resSet.getString("TEXTO"));
                
                Integer idVoz = ((BigDecimal) resSet.getObject("VOZ_DE_RECURSO")).intValue();
                Integer idPieza = ((BigDecimal) resSet.getObject("PIEZA_DE_RECURSO")).intValue();
                
                if(leerFKs){
                    datosRecurso.setDeVoz(leerVoz(idVoz));
                    datosRecurso.setDePiezaMusical(leerPiezaMusical(idPieza));
                }else{
                    datosRecurso.setDeVoz(new Voz(idVoz));
                    datosRecurso.setDePiezaMusical(new PiezaMusical(idPieza));
                }
            }
            
            // Cierra todos los objetos
            resSet.close();
            sent.close();
            conn.close();
            
        } catch (Exception ex) {
            
            ex.printStackTrace();
            ExcepcionAMSound e = new ExcepcionAMSound();
            e.setMensajeErrorBD(ex.getMessage());
            e.setMensajeUsuario("Error general del sistema. Consulte con el administrador");
            throw e;
        }
        
        return datosRecurso;
    }
    
    /**
     * Lee la informacion de un usuario en concreto
     * 
     * @param mail Mail del usuario a leer
     * @return Un objeto Usuario con la informacion 
     * @throws ExcepcionAMSound Se produce cuando sucede un error inesperado
     */
    public Usuario leerUsuario(String mail) throws ExcepcionAMSound{
        
        Usuario datosUsuario = new Usuario();
        String sql = "select * from USUARIO where MAIL = ?";
        
        try {
            conectar();
            PreparedStatement sentPrep = conn.prepareStatement(sql);
            sentPrep.setString(1, mail); // Condicion WHERE
            ResultSet resSet = sentPrep.executeQuery(); // Ejecuta QUERY
            
            // Guarda los datos en un objeto
            while (resSet.next()) {
                datosUsuario.setId(((BigDecimal) resSet.getObject("ID")).intValue());
                datosUsuario.setNombre(resSet.getString("NOMBRE"));
                datosUsuario.setMail(resSet.getString("MAIL"));
                datosUsuario.setTelefono(resSet.getString("TELEFONO"));
                datosUsuario.setContrasena(resSet.getString("CONTRASENA"));
                datosUsuario.setFoto(resSet.getString("FOTO"));
            }
            
            // Cierra todos los objetos
            resSet.close();
            sentPrep.close();
            conn.close();
            
        } catch (Exception ex) {
            
            ExcepcionAMSound e = new ExcepcionAMSound();
            e.setMensajeErrorBD(ex.getMessage());
            e.setMensajeUsuario("Error general del sistema. Consulte con el administrador");
            throw e;
            
        }
        
        return datosUsuario;
    }
    
    // Buscar %%
    public Agrupacion leerAgrupacion(String nombre){
        return null;
    }
    
    // Buscar %%
    public PiezaMusical leerPiezaMusical(String nombre){
        return null;
    }
    
    /**
     * Lee la informacion de todos los usuarios de la tabla
     * 
     * @return ArrayList de Usuario con la informacion de todos los uaurios de la tabla
     * @throws ExcepcionAMSound Se produce cuando sucede un error inesperado
     */
    public ArrayList<Usuario> leerUsuarios() throws ExcepcionAMSound {
        
        ArrayList<Usuario> usuarios = new ArrayList<>();
        
        // Sentencia SQL
        String sql = "select * from USUARIO";
        
        try {
            
            conectar();
            Statement sent = conn.createStatement(); // Crear statement
            ResultSet resSet = sent.executeQuery(sql); // Ejecuta QUERY
            
            // Guarda los datos en un objeto
            while (resSet.next()) {
                
                Usuario datosUsuario = new Usuario();
                
                datosUsuario.setId(((BigDecimal) resSet.getObject("ID")).intValue());
                datosUsuario.setNombre(resSet.getString("NOMBRE"));
                datosUsuario.setMail(resSet.getString("MAIL"));
                datosUsuario.setTelefono(resSet.getString("TELEFONO"));
                datosUsuario.setContrasena(resSet.getString("CONTRASENA"));
                datosUsuario.setFoto(resSet.getString("FOTO"));
                
                usuarios.add(datosUsuario);
            }
            
            // Cierra todos los objetos
            resSet.close();
            sent.close();
            conn.close();
            
        } catch (Exception ex) {
            
            ExcepcionAMSound e = new ExcepcionAMSound();
            e.setMensajeErrorBD(ex.getMessage());
            e.setMensajeUsuario("Error general del sistema. Consulte con el administrador");
            throw e;
        }
        
        return usuarios;
    }
    
    /**
     * Lee todos los usuarios de una voz
     * 
     * @param idVoz ID de voz
     * @return Lista de usuarios
     * @throws ExcepcionAMSound Se produce cuando sucede un error inesperado
     */
    public ArrayList<Usuario> leerUsuariosDeVoz(Integer idVoz) throws ExcepcionAMSound{
        
        ArrayList<Usuario> usuarios = new ArrayList<>();
        
        // Sentencia SQL
        String sql = "SELECT " +
               "    U.ID AS ID_USUARIO, " +
               "    U.NOMBRE AS NOMBRE_USUARIO, " +
               "    U.MAIL AS MAIL_USUARIO, " +
               "    U.TELEFONO AS TELEFONO_USUARIO, " +
               "    U.CONTRASENA AS CONTRASENA_USUARIO, " +
               "    U.FOTO AS FOTO_USUARIO " +
               "FROM " +
               "    USUARIO U " +
               "JOIN " +
               "    COMPONENTE_VOZ CV ON U.ID = CV.USUARIO_COMPONENTE " +
               "WHERE CV.VOZ = " + idVoz;
        
        try {
            
            conectar();
            Statement sent = conn.createStatement(); // Crear statement
            ResultSet resSet = sent.executeQuery(sql); // Ejecuta QUERY
            
            // Guarda los datos en un objeto
            while (resSet.next()) {
                
                Usuario datosUsuario = new Usuario();
                
                datosUsuario.setId(((BigDecimal) resSet.getObject("ID_USUARIO")).intValue());
                datosUsuario.setNombre(resSet.getString("NOMBRE_USUARIO"));
                datosUsuario.setMail(resSet.getString("MAIL_USUARIO"));
                datosUsuario.setTelefono(resSet.getString("TELEFONO_USUARIO"));
                datosUsuario.setContrasena(resSet.getString("CONTRASENA_USUARIO"));
                datosUsuario.setFoto(resSet.getString("FOTO_USUARIO"));
                
                usuarios.add(datosUsuario);
            }
            
            // Cierra todos los objetos
            resSet.close();
            sent.close();
            conn.close();
            
        } catch (Exception ex) {
            
            ExcepcionAMSound e = new ExcepcionAMSound();
            e.setMensajeErrorBD(ex.getMessage());
            e.setMensajeUsuario("Error general del sistema. Consulte con el administrador");
            throw e;
        }
        
        return usuarios;
    }
    
    public ArrayList<Usuario> leerUsuariosDeAgrupacion(Integer idAgrupacion) throws ExcepcionAMSound{
        
        ArrayList<Usuario> usuarios = new ArrayList<>();
        
        // Sentencia SQL
        String sql = "SELECT " +
           "    U.ID AS ID_USUARIO, " +
           "    U.NOMBRE AS NOMBRE_USUARIO, " +
           "    U.MAIL AS MAIL_USUARIO, " +
           "    U.TELEFONO AS TELEFONO_USUARIO, " +
           "    U.CONTRASENA AS CONTRASENA_USUARIO, " +
           "    U.FOTO AS FOTO_USUARIO " +
           "FROM " +
           "    USUARIO U " +
           "JOIN " +
           "    COMPONENTE_VOZ CV ON U.ID = CV.USUARIO_COMPONENTE " +
           "JOIN " +
           "    VOZ V ON CV.VOZ = V.ID " +
           "JOIN " +
           "    AGRUPACION A ON V.VOZ_DE_AGRUPACION = A.ID " +
           " WHERE A.ID = " + idAgrupacion;
        
        try {
            
            conectar();
            Statement sent = conn.createStatement(); // Crear statement
            ResultSet resSet = sent.executeQuery(sql); // Ejecuta QUERY
            
            // Guarda los datos en un objeto
            while (resSet.next()) {
                
                Usuario datosUsuario = new Usuario();
                
                datosUsuario.setId(((BigDecimal) resSet.getObject("ID_USUARIO")).intValue());
                datosUsuario.setNombre(resSet.getString("NOMBRE_USUARIO"));
                datosUsuario.setMail(resSet.getString("MAIL_USUARIO"));
                datosUsuario.setTelefono(resSet.getString("TELEFONO_USUARIO"));
                datosUsuario.setContrasena(resSet.getString("CONTRASENA_USUARIO"));
                datosUsuario.setFoto(resSet.getString("FOTO_USUARIO"));
                
                usuarios.add(datosUsuario);
            }
            
            // Cierra todos los objetos
            resSet.close();
            sent.close();
            conn.close();
            
        } catch (Exception ex) {
            
            ExcepcionAMSound e = new ExcepcionAMSound();
            e.setMensajeErrorBD(ex.getMessage());
            e.setMensajeUsuario("Error general del sistema. Consulte con el administrador");
            throw e;
        }
        
        return usuarios;
    }
    
    /**
     * Lee todas las agrupaciones
     * 
     * @return Lista de agrupaciones
     * @throws ExcepcionAMSound Se produce cuando sucede un error inesperado
     */
    public ArrayList<Agrupacion> leerAgrupaciones() throws ExcepcionAMSound{
        
        ArrayList<Agrupacion> agrupaciones = new ArrayList<>();
        
        // Sentencia SQL
        String sql = "SELECT " +
             "    A.ID AS ID_AGRUPACION, " +
             "    A.NOMBRE AS NOMBRE_AGRUPACION, " +
             "    A.CONTACTO AS CONTACTO_AGRUPACION, " +
             "    A.TEXTO AS TEXTO_AGRUPACION, " +
             "    A.FOTO AS FOTO_AGRUPACION, " +
             "    U.ID AS ID_USUARIO, " +
             "    U.NOMBRE AS NOMBRE_USUARIO, " +
             "    U.MAIL AS EMAIL_USUARIO, " +
             "    U.TELEFONO AS TELEFONO_USUARIO, " +
             "    U.CONTRASENA AS CONTRASENA_USUARIO, " +
             "    U.FOTO AS FOTO_USUARIO " +
             "FROM " +
             "    AGRUPACION A " +
             "JOIN " +
             "    USUARIO U ON A.DIRECTOR_DE_AGRUPACION = U.ID";
        
        try {
            
            conectar();
            Statement sent = conn.createStatement(); // Crear statement
            ResultSet resSet = sent.executeQuery(sql); // Ejecuta QUERY
            
            // Guarda los datos en un objeto
            while (resSet.next()) {
                
                Agrupacion datosAgrupacion = new Agrupacion();
                Usuario datosDirector = new Usuario();
                
                datosAgrupacion.setId(((BigDecimal) resSet.getObject("ID_AGRUPACION")).intValue());
                datosAgrupacion.setNombre(resSet.getString("NOMBRE_AGRUPACION"));
                datosAgrupacion.setContacto(resSet.getString("CONTACTO_AGRUPACION"));
                datosAgrupacion.setTexto(resSet.getString("TEXTO_AGRUPACION"));
                datosAgrupacion.setFoto(resSet.getString("FOTO_AGRUPACION"));
                
                    datosDirector.setId(((BigDecimal) resSet.getObject("ID_USUARIO")).intValue());
                    datosDirector.setNombre(resSet.getString("NOMBRE_USUARIO"));
                    datosDirector.setMail(resSet.getString("EMAIL_USUARIO"));
                    datosDirector.setTelefono(resSet.getString("TELEFONO_USUARIO"));
                    datosDirector.setContrasena(resSet.getString("CONTRASENA_USUARIO"));
                    datosDirector.setFoto(resSet.getString("FOTO_USUARIO"));
                
                datosAgrupacion.setDirector(datosDirector);
                
                agrupaciones.add(datosAgrupacion);
            }
            
            // Cierra todos los objetos
            resSet.close();
            sent.close();
            conn.close();
            
        } catch (Exception ex) {
            
            ExcepcionAMSound e = new ExcepcionAMSound();
            e.setMensajeErrorBD(ex.getMessage());
            e.setMensajeUsuario("Error general del sistema. Consulte con el administrador");
            throw e;
        }
        
        return agrupaciones;
    }
    
    // Buscar %%
    public ArrayList<Agrupacion> leerAgrupaciones(String nombre){
        return null;
    }
    
    public ArrayList<Agrupacion> leerAgrupacionesDeUsuario(Integer idUsuario) throws ExcepcionAMSound{
        
        ArrayList<Agrupacion> agrupaciones = new ArrayList<>();
        String sql = """
                     SELECT DISTINCT 
                        A.ID AS ID_AGRUPACION,
                        A.NOMBRE AS NOMBRE_AGRUPACION,
                        A.CONTACTO AS CONTACTO_AGRUPACION,
                        A.TEXTO AS TEXTO_AGRUPACION,
                        A.FOTO AS FOTO_AGRUPACION,
                        U.ID AS ID_USUARIO,
                        U.NOMBRE AS NOMBRE_USUARIO,
                        U.MAIL AS EMAIL_USUARIO,
                        U.TELEFONO AS TELEFONO_USUARIO,
                        U.CONTRASENA AS CONTRASENA_USUARIO,
                        U.FOTO AS FOTO_USUARIO
                     FROM AGRUPACION A
                     JOIN USUARIO U ON A.DIRECTOR_DE_AGRUPACION = U.ID
                     WHERE A.DIRECTOR_DE_AGRUPACION = """ + idUsuario + """ 
                     OR A.ID IN (
                         SELECT VOZ_DE_AGRUPACION
                         FROM VOZ
                         WHERE ID IN (
                             SELECT VOZ
                             FROM COMPONENTE_VOZ
                             WHERE USUARIO_COMPONENTE = """ + idUsuario + """
                         )
                     )
                     """;
        
        try {
            
            conectar();
            Statement sent = conn.createStatement(); // Crear statement
            ResultSet resSet = sent.executeQuery(sql); // Ejecuta QUERY
            
            // Guarda los datos en un objeto
            while (resSet.next()) {
                
                Agrupacion datosAgrupacion = new Agrupacion();
                Usuario datosDirector = new Usuario();
                
                datosAgrupacion.setId(((BigDecimal) resSet.getObject("ID_AGRUPACION")).intValue());
                datosAgrupacion.setNombre(resSet.getString("NOMBRE_AGRUPACION"));
                datosAgrupacion.setContacto(resSet.getString("CONTACTO_AGRUPACION"));
                datosAgrupacion.setTexto(resSet.getString("TEXTO_AGRUPACION"));
                datosAgrupacion.setFoto(resSet.getString("FOTO_AGRUPACION"));
                
                    datosDirector.setId(((BigDecimal) resSet.getObject("ID_USUARIO")).intValue());
                    datosDirector.setNombre(resSet.getString("NOMBRE_USUARIO"));
                    datosDirector.setMail(resSet.getString("EMAIL_USUARIO"));
                    datosDirector.setTelefono(resSet.getString("TELEFONO_USUARIO"));
                    datosDirector.setContrasena(resSet.getString("CONTRASENA_USUARIO"));
                    datosDirector.setFoto(resSet.getString("FOTO_USUARIO"));
                
                datosAgrupacion.setDirector(datosDirector);
                
                agrupaciones.add(datosAgrupacion);
            }
            
            // Cierra todos los objetos
            resSet.close();
            sent.close();
            conn.close();
            
        } catch (Exception ex) {
            
            ExcepcionAMSound e = new ExcepcionAMSound();
            e.setMensajeErrorBD(ex.getMessage());
            e.setMensajeUsuario("Error general del sistema. Consulte con el administrador");
            throw e;
        }
        
        return agrupaciones;
    }
    
    /**
     * Lee todas las piezas musicales
     * 
     * @return Lista de piezas musicales
     * @throws ExcepcionAMSound Se produce cuando sucede un error inesperado
     */
    public ArrayList<PiezaMusical> leerPiezasMusicales() throws ExcepcionAMSound{
        
        ArrayList<PiezaMusical> piezasMusicales = new ArrayList<>();
        
        String sql = "SELECT " +
               "    PM.ID AS ID_PIEZA_MUSICAL, " +
               "    PM.NOMBRE AS NOMBRE_PIEZA, " +
               "    PM.AUTOR AS AUTOR_PIEZA, " +
               "    PM.TEXTO AS TEXTO_PIEZA, " +
               "    A.ID AS ID_AGRUPACION, " +
               "    A.NOMBRE AS NOMBRE_AGRUPACION, " +
               "    A.CONTACTO AS CONTACTO_AGRUPACION, " +
               "    A.TEXTO AS TEXTO_AGRUPACION, " +
               "    A.FOTO AS FOTO_AGRUPACION, " +
               "    U.ID AS ID_USUARIO, " +
               "    U.NOMBRE AS NOMBRE_USUARIO, " +
               "    U.MAIL AS MAIL_USUARIO, " +
               "    U.TELEFONO AS TELEFONO_USUARIO, " +
               "    U.CONTRASENA AS CONTRASENA_USUARIO, " +
               "    U.FOTO AS FOTO_USUARIO " +
               "FROM " +
               "    PIEZA_MUSICAL PM " +
               "JOIN " +
               "    AGRUPACION A ON PM.PIEZA_DE_AGRUPACION = A.ID " +
               "JOIN " +
               "    USUARIO U ON A.DIRECTOR_DE_AGRUPACION = U.ID";
        
        try {
            
            conectar();
            Statement sent = conn.createStatement(); // Crear statement
            ResultSet resSet = sent.executeQuery(sql); // Ejecuta QUERY
            
            // Guarda los datos en un objeto
            while (resSet.next()) {
                
                PiezaMusical datosPiezaMusical = new PiezaMusical();
                Agrupacion datosAgrupacion = new Agrupacion();
                Usuario datosDirector = new Usuario();
                
                datosPiezaMusical.setId(((BigDecimal) resSet.getObject("ID_PIEZA_MUSICAL")).intValue());
                datosPiezaMusical.setNombre(resSet.getString("NOMBRE_PIEZA"));
                datosPiezaMusical.setAutor(resSet.getString("AUTOR_PIEZA"));
                datosPiezaMusical.setTexto(resSet.getString("TEXTO_PIEZA"));

                    datosAgrupacion.setId(((BigDecimal) resSet.getObject("ID_AGRUPACION")).intValue());
                    datosAgrupacion.setNombre(resSet.getString("NOMBRE_AGRUPACION"));
                    datosAgrupacion.setContacto(resSet.getString("CONTACTO_AGRUPACION"));
                    datosAgrupacion.setTexto(resSet.getString("TEXTO_AGRUPACION"));
                    datosAgrupacion.setFoto(resSet.getString("FOTO_AGRUPACION"));

                        datosDirector.setId(((BigDecimal) resSet.getObject("ID_AGRUPACION")).intValue());
                        datosDirector.setNombre(resSet.getString("NOMBRE_USUARIO"));
                        datosDirector.setMail(resSet.getString("MAIL_USUARIO"));
                        datosDirector.setTelefono(resSet.getString("TELEFONO_USUARIO"));
                        datosDirector.setContrasena(resSet.getString("CONTRASENA_USUARIO"));
                        datosDirector.setFoto(resSet.getString("FOTO_USUARIO"));

                    datosAgrupacion.setDirector(datosDirector);
                datosPiezaMusical.setDeAgrupacion(datosAgrupacion);
                piezasMusicales.add(datosPiezaMusical);
            }
            
            // Cierra todos los objetos
            resSet.close();
            sent.close();
            conn.close();
            
        } catch (Exception ex) {
            
            ExcepcionAMSound e = new ExcepcionAMSound();
            e.setMensajeErrorBD(ex.getMessage());
            e.setMensajeUsuario("Error general del sistema. Consulte con el administrador");
            throw e;
        }
        
        return piezasMusicales;
    }
    
    // Buscar %%
    public ArrayList<PiezaMusical> leerPiezasMusicales(String nombre){
        return null;
    }
    
    public ArrayList<PiezaMusical> leerPiezasMusicalesDeAgrupacion(Integer idAgrupacion) throws ExcepcionAMSound{
        ArrayList<PiezaMusical> piezasMusicales = new ArrayList<>();
        
        String sql = "SELECT " +
               "    PM.ID AS ID_PIEZA_MUSICAL, " +
               "    PM.NOMBRE AS NOMBRE_PIEZA, " +
               "    PM.AUTOR AS AUTOR_PIEZA, " +
               "    PM.TEXTO AS TEXTO_PIEZA, " +
               "    A.ID AS ID_AGRUPACION, " +
               "    A.NOMBRE AS NOMBRE_AGRUPACION, " +
               "    A.CONTACTO AS CONTACTO_AGRUPACION, " +
               "    A.TEXTO AS TEXTO_AGRUPACION, " +
               "    A.FOTO AS FOTO_AGRUPACION, " +
               "    U.ID AS ID_USUARIO, " +
               "    U.NOMBRE AS NOMBRE_USUARIO, " +
               "    U.MAIL AS MAIL_USUARIO, " +
               "    U.TELEFONO AS TELEFONO_USUARIO, " +
               "    U.CONTRASENA AS CONTRASENA_USUARIO, " +
               "    U.FOTO AS FOTO_USUARIO " +
               "FROM " +
               "    PIEZA_MUSICAL PM " +
               "JOIN " +
               "    AGRUPACION A ON PM.PIEZA_DE_AGRUPACION = A.ID " +
               "JOIN " +
               "    USUARIO U ON A.DIRECTOR_DE_AGRUPACION = U.ID " +
               "WHERE PM.PIEZA_DE_AGRUPACION = " + idAgrupacion;
        
        try {
            
            conectar();
            Statement sent = conn.createStatement(); // Crear statement
            ResultSet resSet = sent.executeQuery(sql); // Ejecuta QUERY
            
            // Guarda los datos en un objeto
            while (resSet.next()) {
                
                PiezaMusical datosPiezaMusical = new PiezaMusical();
                Agrupacion datosAgrupacion = new Agrupacion();
                Usuario datosDirector = new Usuario();
                
                datosPiezaMusical.setId(((BigDecimal) resSet.getObject("ID_PIEZA_MUSICAL")).intValue());
                datosPiezaMusical.setNombre(resSet.getString("NOMBRE_PIEZA"));
                datosPiezaMusical.setAutor(resSet.getString("AUTOR_PIEZA"));
                datosPiezaMusical.setTexto(resSet.getString("TEXTO_PIEZA"));

                    datosAgrupacion.setId(((BigDecimal) resSet.getObject("ID_AGRUPACION")).intValue());
                    datosAgrupacion.setNombre(resSet.getString("NOMBRE_AGRUPACION"));
                    datosAgrupacion.setContacto(resSet.getString("CONTACTO_AGRUPACION"));
                    datosAgrupacion.setTexto(resSet.getString("TEXTO_AGRUPACION"));
                    datosAgrupacion.setFoto(resSet.getString("FOTO_AGRUPACION"));

                        datosDirector.setId(((BigDecimal) resSet.getObject("ID_AGRUPACION")).intValue());
                        datosDirector.setNombre(resSet.getString("NOMBRE_USUARIO"));
                        datosDirector.setMail(resSet.getString("MAIL_USUARIO"));
                        datosDirector.setTelefono(resSet.getString("TELEFONO_USUARIO"));
                        datosDirector.setContrasena(resSet.getString("CONTRASENA_USUARIO"));
                        datosDirector.setFoto(resSet.getString("FOTO_USUARIO"));

                    datosAgrupacion.setDirector(datosDirector);
                datosPiezaMusical.setDeAgrupacion(datosAgrupacion);
                piezasMusicales.add(datosPiezaMusical);
            }
            
            // Cierra todos los objetos
            resSet.close();
            sent.close();
            conn.close();
            
        } catch (Exception ex) {
            
            ExcepcionAMSound e = new ExcepcionAMSound();
            e.setMensajeErrorBD(ex.getMessage());
            e.setMensajeUsuario("Error general del sistema. Consulte con el administrador");
            throw e;
        }
        
        return piezasMusicales;
    }
    
    /**
     * Lee todas las voces
     * 
     * @return Lista de voces
     * @throws ExcepcionAMSound Se produce cuando sucede un error inesperado
     */
    public ArrayList<Voz> leerVoces() throws ExcepcionAMSound{
        
        ArrayList<Voz> voces = new ArrayList<>();
        
        String sql = "SELECT " +
               "    V.ID AS ID_VOZ, " +
               "    V.NOMBRE AS NOMBRE_VOZ, " +
               "    V.TEXTO AS TEXTO_VOZ, " +
               "    A.ID AS ID_AGRUPACION, " +
               "    A.NOMBRE AS NOMBRE_AGRUPACION, " +
               "    A.CONTACTO AS CONTACTO_AGRUPACION, " +
               "    A.TEXTO AS TEXTO_AGRUPACION, " +
               "    A.FOTO AS FOTO_AGRUPACION, " +
               "    U.ID AS ID_USUARIO, " +
               "    U.NOMBRE AS NOMBRE_USUARIO, " +
               "    U.MAIL AS MAIL_USUARIO, " +
               "    U.TELEFONO AS TELEFONO_USUARIO, " +
               "    U.CONTRASENA AS CONTRASENA_USUARIO, " +
               "    U.FOTO AS FOTO_USUARIO " +
               "FROM " +
               "    VOZ V " +
               "JOIN " +
               "    AGRUPACION A ON V.VOZ_DE_AGRUPACION = A.ID " +
               "JOIN " +
               "    USUARIO U ON A.DIRECTOR_DE_AGRUPACION = U.ID";
        
        try {
            
            conectar();
            Statement sent = conn.createStatement(); // Crear statement
            ResultSet resSet = sent.executeQuery(sql); // Ejecuta QUERY
            
            // Guarda los datos en un objeto
            while (resSet.next()) {
                
                Voz datosVoces = new Voz();
                Agrupacion datosAgrupacion = new Agrupacion();
                Usuario datosDirector = new Usuario();
                
                datosVoces.setId(((BigDecimal) resSet.getObject("ID_VOZ")).intValue());
                datosVoces.setNombre(resSet.getString("NOMBRE_VOZ"));
                datosVoces.setTexto(resSet.getString("TEXTO_VOZ"));

                    datosAgrupacion.setId(((BigDecimal) resSet.getObject("ID_AGRUPACION")).intValue());
                    datosAgrupacion.setNombre(resSet.getString("NOMBRE_AGRUPACION"));
                    datosAgrupacion.setContacto(resSet.getString("CONTACTO_AGRUPACION"));
                    datosAgrupacion.setTexto(resSet.getString("TEXTO_AGRUPACION"));
                    datosAgrupacion.setFoto(resSet.getString("FOTO_AGRUPACION"));

                        datosDirector.setId(((BigDecimal) resSet.getObject("ID_AGRUPACION")).intValue());
                        datosDirector.setNombre(resSet.getString("NOMBRE_USUARIO"));
                        datosDirector.setMail(resSet.getString("MAIL_USUARIO"));
                        datosDirector.setTelefono(resSet.getString("TELEFONO_USUARIO"));
                        datosDirector.setContrasena(resSet.getString("CONTRASENA_USUARIO"));
                        datosDirector.setFoto(resSet.getString("FOTO_USUARIO"));

                    datosAgrupacion.setDirector(datosDirector);
                datosVoces.setDeAgrupacion(datosAgrupacion);
                voces.add(datosVoces);
            }
            
            // Cierra todos los objetos
            resSet.close();
            sent.close();
            conn.close();
            
        } catch (Exception ex) {
           
            ExcepcionAMSound e = new ExcepcionAMSound();
            e.setMensajeErrorBD(ex.getMessage());
            e.setMensajeUsuario("Error general del sistema. Consulte con el administrador");
            throw e;
        }
        
        return voces;
    }
    
    /**
     * Lee todas las voces de una agrupacion
     * 
     * @param idAgrupacion ID de agrupacion
     * @return Lista de voces
     * @throws ExcepcionAMSound Se produce cuando sucede un error inesperado
     */
    public ArrayList<Voz> leerVocesDeAgrupacion(Integer idAgrupacion) throws ExcepcionAMSound{
        
        ArrayList<Voz> voces = new ArrayList<>();
        
        String sql = "SELECT " +
               "    V.ID AS ID_VOZ, " +
               "    V.NOMBRE AS NOMBRE_VOZ, " +
               "    V.TEXTO AS TEXTO_VOZ, " +
               "    A.ID AS ID_AGRUPACION, " +
               "    A.NOMBRE AS NOMBRE_AGRUPACION, " +
               "    A.CONTACTO AS CONTACTO_AGRUPACION, " +
               "    A.TEXTO AS TEXTO_AGRUPACION, " +
               "    A.FOTO AS FOTO_AGRUPACION, " +
               "    U.ID AS ID_USUARIO, " +
               "    U.NOMBRE AS NOMBRE_USUARIO, " +
               "    U.MAIL AS MAIL_USUARIO, " +
               "    U.TELEFONO AS TELEFONO_USUARIO, " +
               "    U.CONTRASENA AS CONTRASENA_USUARIO, " +
               "    U.FOTO AS FOTO_USUARIO " +
               "FROM " +
               "    VOZ V " +
               "JOIN " +
               "    AGRUPACION A ON V.VOZ_DE_AGRUPACION = A.ID " +
               "JOIN " +
               "    USUARIO U ON A.DIRECTOR_DE_AGRUPACION = U.ID " +
               "WHERE V.VOZ_DE_AGRUPACION = " +idAgrupacion;
        
        try {
            
            conectar();
            Statement sent = conn.createStatement(); // Crear statement
            ResultSet resSet = sent.executeQuery(sql); // Ejecuta QUERY
            
            // Guarda los datos en un objeto
            while (resSet.next()) {
                
                Voz datosVoces = new Voz();
                Agrupacion datosAgrupacion = new Agrupacion();
                Usuario datosDirector = new Usuario();
                
                datosVoces.setId(((BigDecimal) resSet.getObject("ID_VOZ")).intValue());
                datosVoces.setNombre(resSet.getString("NOMBRE_VOZ"));
                datosVoces.setTexto(resSet.getString("TEXTO_VOZ"));

                    datosAgrupacion.setId(((BigDecimal) resSet.getObject("ID_AGRUPACION")).intValue());
                    datosAgrupacion.setNombre(resSet.getString("NOMBRE_AGRUPACION"));
                    datosAgrupacion.setContacto(resSet.getString("CONTACTO_AGRUPACION"));
                    datosAgrupacion.setTexto(resSet.getString("TEXTO_AGRUPACION"));
                    datosAgrupacion.setFoto(resSet.getString("FOTO_AGRUPACION"));

                        datosDirector.setId(((BigDecimal) resSet.getObject("ID_AGRUPACION")).intValue());
                        datosDirector.setNombre(resSet.getString("NOMBRE_USUARIO"));
                        datosDirector.setMail(resSet.getString("MAIL_USUARIO"));
                        datosDirector.setTelefono(resSet.getString("TELEFONO_USUARIO"));
                        datosDirector.setContrasena(resSet.getString("CONTRASENA_USUARIO"));
                        datosDirector.setFoto(resSet.getString("FOTO_USUARIO"));

                    datosAgrupacion.setDirector(datosDirector);
                datosVoces.setDeAgrupacion(datosAgrupacion);
                voces.add(datosVoces);
            }
            
            // Cierra todos los objetos
            resSet.close();
            sent.close();
            conn.close();
            
        } catch (Exception ex) {
           
            ExcepcionAMSound e = new ExcepcionAMSound();
            e.setMensajeErrorBD(ex.getMessage());
            e.setMensajeUsuario("Error general del sistema. Consulte con el administrador");
            throw e;
        }
        
        return voces;
    }
    
    public ArrayList<Voz> leerVocesDeUsuario(Integer idUsuario, Integer idAgrupacion) throws ExcepcionAMSound{
        
        ArrayList<Voz> voces = new ArrayList<>();
        
        String sql = "SELECT " +
                    "    V.ID AS ID_VOZ, " +
                    "    V.NOMBRE AS NOMBRE_VOZ, " +
                    "    V.TEXTO AS TEXTO_VOZ, " +
                    "    A.ID AS ID_AGRUPACION, " +
                    "    A.NOMBRE AS NOMBRE_AGRUPACION, " +
                    "    A.CONTACTO AS CONTACTO_AGRUPACION, " +
                    "    A.TEXTO AS TEXTO_AGRUPACION, " +
                    "    A.FOTO AS FOTO_AGRUPACION, " +
                    "    U.ID AS ID_USUARIO, " +
                    "    U.NOMBRE AS NOMBRE_USUARIO, " +
                    "    U.MAIL AS MAIL_USUARIO, " +
                    "    U.TELEFONO AS TELEFONO_USUARIO, " +
                    "    U.CONTRASENA AS CONTRASENA_USUARIO, " +
                    "    U.FOTO AS FOTO_USUARIO " +
                    """
                     FROM USUARIO U
                     JOIN COMPONENTE_VOZ CV ON U.ID = CV.USUARIO_COMPONENTE
                     JOIN VOZ V ON CV.VOZ = V.ID
                     JOIN AGRUPACION A ON V.VOZ_DE_AGRUPACION = A.ID
                     WHERE U.ID = 
                    """;
                sql += idUsuario + " AND A.ID = " + idAgrupacion;
        
        try {
            
            conectar();
            Statement sent = conn.createStatement(); // Crear statement
            ResultSet resSet = sent.executeQuery(sql); // Ejecuta QUERY
            
            // Guarda los datos en un objeto
            while (resSet.next()) {
                
                Voz datosVoces = new Voz();
                Agrupacion datosAgrupacion = new Agrupacion();
                Usuario datosDirector = new Usuario();
                
                datosVoces.setId(((BigDecimal) resSet.getObject("ID_VOZ")).intValue());
                datosVoces.setNombre(resSet.getString("NOMBRE_VOZ"));
                datosVoces.setTexto(resSet.getString("TEXTO_VOZ"));

                    datosAgrupacion.setId(((BigDecimal) resSet.getObject("ID_AGRUPACION")).intValue());
                    datosAgrupacion.setNombre(resSet.getString("NOMBRE_AGRUPACION"));
                    datosAgrupacion.setContacto(resSet.getString("CONTACTO_AGRUPACION"));
                    datosAgrupacion.setTexto(resSet.getString("TEXTO_AGRUPACION"));
                    datosAgrupacion.setFoto(resSet.getString("FOTO_AGRUPACION"));

                        datosDirector.setId(((BigDecimal) resSet.getObject("ID_AGRUPACION")).intValue());
                        datosDirector.setNombre(resSet.getString("NOMBRE_USUARIO"));
                        datosDirector.setMail(resSet.getString("MAIL_USUARIO"));
                        datosDirector.setTelefono(resSet.getString("TELEFONO_USUARIO"));
                        datosDirector.setContrasena(resSet.getString("CONTRASENA_USUARIO"));
                        datosDirector.setFoto(resSet.getString("FOTO_USUARIO"));

                    datosAgrupacion.setDirector(datosDirector);
                datosVoces.setDeAgrupacion(datosAgrupacion);
                voces.add(datosVoces);
            }
            
            // Cierra todos los objetos
            resSet.close();
            sent.close();
            conn.close();
            
        } catch (Exception ex) {
           
            ExcepcionAMSound e = new ExcepcionAMSound();
            e.setMensajeErrorBD(ex.getMessage());
            e.setMensajeUsuario("Error general del sistema. Consulte con el administrador");
            throw e;
        }
        
        return voces;
    }
    
    /**
     * Le todos los recursos
     * 
     * @param leerFKs Permite elegir si leer las claves foraneas
     * @return Lista de recursos
     * @throws ExcepcionAMSound Se produce cuando sucede un error inesperado
     */
    public ArrayList<Recurso> leerRecursos(boolean leerFKs) throws ExcepcionAMSound{
        
        ArrayList<Recurso> recursos = new ArrayList<>();
        
        String sql = "SELECT " +
                      "    ID, " +
                      "    TITULO, " +
                      "    RUTA_ARCHIVO, " +
                      "    TEXTO, " +
                      "    VOZ_DE_RECURSO, " +
                      "    PIEZA_DE_RECURSO " +
                      "FROM " +
                      "    RECURSO";
        
        try {
            
            conectar();
            Statement sent = conn.createStatement(); // Crear statement
            ResultSet resSet = sent.executeQuery(sql); // Ejecuta QUERY
            
            // Guarda los datos en un objeto
            while (resSet.next()) {
                
                Recurso datosRecurso = new Recurso();
                
                datosRecurso.setId(((BigDecimal) resSet.getObject("ID")).intValue());
                datosRecurso.setTitulo(resSet.getString("TITULO"));
                datosRecurso.setRuta(resSet.getString("RUTA_ARCHIVO"));
                datosRecurso.setTexto(resSet.getString("TEXTO"));
                
                Integer idVoz = ((BigDecimal) resSet.getObject("VOZ_DE_RECURSO")).intValue();
                Integer idPieza = ((BigDecimal) resSet.getObject("PIEZA_DE_RECURSO")).intValue();
                
                if(leerFKs){
                    datosRecurso.setDeVoz(leerVoz(idVoz));
                    datosRecurso.setDePiezaMusical(leerPiezaMusical(idPieza));
                }else{
                    datosRecurso.setDeVoz(new Voz(idVoz));
                    datosRecurso.setDePiezaMusical(new PiezaMusical(idPieza));
                }
                
                recursos.add(datosRecurso);
            }
            
            // Cierra todos los objetos
            resSet.close();
            sent.close();
            conn.close();
            
        } catch (Exception ex) {
            
            ExcepcionAMSound e = new ExcepcionAMSound();
            e.setMensajeErrorBD(ex.getMessage());
            e.setMensajeUsuario("Error general del sistema. Consulte con el administrador");
            throw e;
        }
        
        return recursos;
    }
    
    /**
     * Lee todos los registros de una misma voz
     * 
     * @param pIdVoz ID de voz
     * @param leerFKs Permite elegir si leer las claves foraneas
     * @return Lista de recursos
     * @throws ExcepcionAMSound Se produce cuando sucede un error inesperado
     */
    public ArrayList<Recurso> leerRecursosDeVoz(Integer pIdVoz, boolean leerFKs) throws ExcepcionAMSound{
        
        ArrayList<Recurso> recursos = new ArrayList<>();
        
        String sql = "SELECT " +
                    "    R.ID AS ID_RECURSO, " +
                    "    R.TITULO AS TITULO_RECURSO, " +
                    "    R.RUTA_ARCHIVO AS RUTA_ARCHIVO_RECURSO, " +
                    "    R.TEXTO AS TEXTO_RECURSO, " +
                    "    R.VOZ_DE_RECURSO AS VOZ, " +
                    "    R.PIEZA_DE_RECURSO AS PIEZA " +
                    "FROM " +
                    "    RECURSO R " +
                    "JOIN " +
                    "    VOZ V ON R.VOZ_DE_RECURSO = V.ID " +
                    "WHERE " +
                    "    V.ID = "+ pIdVoz;
        
        try {
            
            conectar();
            Statement sent = conn.createStatement(); // Crear statement
            ResultSet resSet = sent.executeQuery(sql); // Ejecuta QUERY
            
            // Guarda los datos en un objeto
            while (resSet.next()) {
                
                Recurso datosRecurso = new Recurso();
                
                datosRecurso.setId(((BigDecimal) resSet.getObject("ID_RECURSO")).intValue());
                datosRecurso.setTitulo(resSet.getString("TITULO_RECURSO"));
                datosRecurso.setRuta(resSet.getString("RUTA_ARCHIVO_RECURSO"));
                datosRecurso.setTexto(resSet.getString("TEXTO_RECURSO"));
                
                Integer idVoz = ((BigDecimal) resSet.getObject("VOZ")).intValue();
                Integer idPieza = ((BigDecimal) resSet.getObject("PIEZA")).intValue();
                
                if(leerFKs){
                    datosRecurso.setDeVoz(leerVoz(idVoz));
                    datosRecurso.setDePiezaMusical(leerPiezaMusical(idPieza));
                }else{
                    datosRecurso.setDeVoz(new Voz(idVoz));
                    datosRecurso.setDePiezaMusical(new PiezaMusical(idPieza));
                }
                
                recursos.add(datosRecurso);
            }
            
            // Cierra todos los objetos
            resSet.close();
            sent.close();
            conn.close();
            
        } catch (Exception ex) {
            
            ExcepcionAMSound e = new ExcepcionAMSound();
            e.setMensajeErrorBD(ex.getMessage());
            e.setMensajeUsuario("Error general del sistema. Consulte con el administrador");
            throw e;
        }
        
        return recursos;
    }
    
    public ArrayList<Recurso> leerRecursosDePiezaMusical(Integer pIdPieza, boolean leerFKs) throws ExcepcionAMSound{
        
        ArrayList<Recurso> recursos = new ArrayList<>();
        
        String sql = "SELECT " +
                    "    R.ID AS ID_RECURSO, " +
                    "    R.TITULO AS TITULO_RECURSO, " +
                    "    R.RUTA_ARCHIVO AS RUTA_ARCHIVO_RECURSO, " +
                    "    R.TEXTO AS TEXTO_RECURSO, " +
                    "    R.VOZ_DE_RECURSO AS VOZ, " +
                    "    R.PIEZA_DE_RECURSO AS PIEZA " +
                    "FROM " +
                    "    RECURSO R " +
                    "JOIN " +
                    "    VOZ V ON R.VOZ_DE_RECURSO = V.ID " +
                    "WHERE " +
                    "    R.PIEZA_DE_RECURSO = " + pIdPieza;
        
        try {
            
            conectar();
            Statement sent = conn.createStatement(); // Crear statement
            ResultSet resSet = sent.executeQuery(sql); // Ejecuta QUERY
            
            // Guarda los datos en un objeto
            while (resSet.next()) {
                
                Recurso datosRecurso = new Recurso();
                
                datosRecurso.setId(((BigDecimal) resSet.getObject("ID_RECURSO")).intValue());
                datosRecurso.setTitulo(resSet.getString("TITULO_RECURSO"));
                datosRecurso.setRuta(resSet.getString("RUTA_ARCHIVO_RECURSO"));
                datosRecurso.setTexto(resSet.getString("TEXTO_RECURSO"));
                
                Integer idVoz = ((BigDecimal) resSet.getObject("VOZ")).intValue();
                Integer idPieza = ((BigDecimal) resSet.getObject("PIEZA")).intValue();
                
                if(leerFKs){
                    datosRecurso.setDeVoz(leerVoz(idVoz));
                    datosRecurso.setDePiezaMusical(leerPiezaMusical(idPieza));
                }else{
                    datosRecurso.setDeVoz(new Voz(idVoz));
                    datosRecurso.setDePiezaMusical(new PiezaMusical(idPieza));
                }
                
                recursos.add(datosRecurso);
            }
            
            // Cierra todos los objetos
            resSet.close();
            sent.close();
            conn.close();
            
        } catch (Exception ex) {
            
            ExcepcionAMSound e = new ExcepcionAMSound();
            e.setMensajeErrorBD(ex.getMessage());
            e.setMensajeUsuario("Error general del sistema. Consulte con el administrador");
            throw e;
        }
        
        return recursos;
    }
    
    /**
     * Añade un usuario a una voz
     * @param idUsuario ID de usuario
     * @param idVoz ID de voz
     * @return Registros afectados
     * @throws ExcepcionAMSound Se produce cuando sucede un error inesperado
     */
    public int agregarUsuarioAVoz(Integer idUsuario, Integer idVoz) throws ExcepcionAMSound{
        
        int registrosAfectados = 0;
        String sql = "insert into COMPONENTE_VOZ values (SEQ_COMPONENTE_VOZ.NEXTVAL, ?,?)";
        
        try{
            
            conectar();
            PreparedStatement sentPrep = conn.prepareStatement(sql); // Crear sentencia preparada
            
            // Introducir datos
            sentPrep.setObject(1, idUsuario, java.sql.Types.INTEGER);
            sentPrep.setObject(2, idVoz, java.sql.Types.INTEGER);

            registrosAfectados = sentPrep.executeUpdate(); // Ejecutar la sentencia preparada
            sentPrep.close(); // Cerrar sentencia
            conn.close();// Cerrar la conexion con la base de datos
            
        } catch (SQLException ex) {

            ExcepcionAMSound e = new ExcepcionAMSound();
            e.setCodigoErrorBD(ex.getErrorCode());
            e.setMensajeErrorBD(ex.getMessage());
            e.setSentenciaSQL(sql);
            
            switch(ex.getErrorCode()){
                // Update NULL
                case 1400: e.setMensajeUsuario("El nombre y contacto son obligatorios"); break;
                // UNIQUE
                case 1: e.setMensajeUsuario("El usuario ya está registrado en dicha voz"); break;
                // Registro demasiado grande
                case 12899: e.setMensajeUsuario("Se ha sobrepasado la cantidad de caracteres permitidos"); break;
                // Otros errores
                default: e.setMensajeUsuario("Error general del sistema. Consulte con el administrador"); break;
            }
            
            throw e;
            
        } catch (Exception ex){
            
            ExcepcionAMSound e = new ExcepcionAMSound();
            e.setMensajeErrorBD(ex.getMessage());
            e.setMensajeUsuario("Error general del sistema. Consulte con el administrador");
            throw e;
        }
        
        return registrosAfectados;
    }
    
    /**
     * Alimina un usuario de la voz
     * @param idUsuario ID de usuario
     * @param idVoz ID de voz
     * @return Registros afectados
     * @throws ExcepcionAMSound Se produce cuando sucede un error inesperado
     */
    public int eliminarUsuarioDeVoz(Integer idUsuario, Integer idVoz) throws ExcepcionAMSound{
        
        int registrosAfectados = 0;
        // Sentencia SQL
        String sql = "delete from COMPONENTE_VOZ where USUARIO_COMPONENTE=" + idUsuario + " AND VOZ=" + idVoz;
         
        try {
            
            conectar();
            Statement sent = conn.createStatement(); // Crear statement
            
            registrosAfectados = sent.executeUpdate(sql); // Ejecutar sentencia SQL
            sent.close(); // Cerrar la sentencia
            conn.close(); // Cerrar la conexion con la base de datos
            
        }  catch (SQLException ex) {
            
            ExcepcionAMSound e = new ExcepcionAMSound();
            e.setCodigoErrorBD(ex.getErrorCode());
            e.setMensajeErrorBD(ex.getMessage());
            e.setMensajeUsuario("Error general del sistema. Consulte con el administrador");
            e.setSentenciaSQL(sql);
            
            throw e;
            
        } catch(Exception ex){
            
            ExcepcionAMSound e = new ExcepcionAMSound();
            e.setMensajeErrorBD(ex.getMessage());
            e.setMensajeUsuario("Error general del sistema. Consulte con el administrador");
            throw e;
        }
         
        return registrosAfectados;
    }
    
    
    public static void main(String args[]){
        try {
            CadAMSound c = new CadAMSound("localhost", "AMSound", "Tutuyoxwk.12");
            System.out.println(c.leerAgrupacionesDeUsuario(2));
        } catch (ExcepcionAMSound ex) {
            System.out.println(ex);
        }
    }
}
