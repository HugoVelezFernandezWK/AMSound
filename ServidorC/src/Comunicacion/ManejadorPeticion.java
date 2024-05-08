
package Comunicacion;

import CAD.CadAMSound;
import CAD.Operaciones;
import POJOS.ExcepcionAMSound;
import POJOS.Peticion;
import POJOS.PiezaMusical;
import POJOS.Recurso;
import POJOS.Respuesta;
import POJOS.Usuario;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Hugo Vélez Fernández
 */
public class ManejadorPeticion extends Thread {
    
    private Socket clienteConectado;
    private static final String HOST = "127.0.0.1";
    private static final String USUARIO_BD = "amsound";
    private static final String CONTRASENA_BD = "Tutuyoxwk.12";
    
    public ManejadorPeticion(Socket clienteConectado) {
        this.clienteConectado = clienteConectado;
    }
    
    @Override
    public void run(){
        ObjectInputStream ois = null;
        
        try {          
            ois = new ObjectInputStream(clienteConectado.getInputStream());
            Peticion p = (Peticion) ois.readObject();
            
            System.out.print( new Date() + " - ");
            System.out.print(clienteConectado.getInetAddress().getHostName() + " - " + clienteConectado.getInetAddress().getHostAddress() + " : ");
            System.out.println(p);
            
            // Todas las posibilidades de petición
            switch(p.getIdPeticion()){
                case Operaciones.INSERTAR_USUARIO:  insertarUsuario(p); break;
                case Operaciones.MODIFICAR_USUARIO: modificarUsuario(p); break;
                case Operaciones.ELIMINAR_USUARIO: eliminarUsuario(p); break;
                case Operaciones.LEER_USUARIO: leerUsuario(p); break;
                case Operaciones.LEER_USUARIOS: leerUsuarios(p); break;
                
                case Operaciones.INSERTAR_RECURSO: insertarRecurso(p); break;
                case Operaciones.ELIMINAR_RECURSO: eliminarRecurso(p); break;
                
                case Operaciones.MODIFICAR_PIEZA_MUSICAL: modificarPiezaMusical(p); break;
                case Operaciones.ELIMINAR_PIEZA_MUSICAL: eliminarPiezaMusical(p); break;
                
                case Operaciones.LEER_VOCES_DE_USUARIO: leerVocesDeUsuario(p); break;
                case Operaciones.ELIMINAR_VOZ: eliminarVoz(p); break;
                case Operaciones.ELIMINAR_USUARIO_DE_VOZ: eliminarUsuarioDeVoz(p); break;
                
                case Operaciones.ELIMINAR_AGRUPACION: eliminarAgrupacion(p); break;
                
                case Operaciones.LEER_VOCES_DE_AGRUPACION: leerVocesDeAgrupacion(p); break;
                case Operaciones.LEER_PIEZAS_MUSICALES_DE_AGRUPACION: leerPiezasDeAgrupacion(p); break;
                case Operaciones.LEER_USUARIOS_DE_AGRUPACION: leerUsuariosDeAgrupacion(p); break;
                
                case Operaciones.LEER_AGRUPACIONES_DE_USUARIO: leerAgrupacionesDeusuario(p); break;
                
                case Operaciones.LEER_RECURSOS_DE_PIEZA_MUSICAL: leerRecursosDePiezaMusical(p); break;
            }
            
            clienteConectado.close();
            
        } catch (IOException ex) {
            manejadorIOExceptionsOIS(ex, ois);
        } catch (ClassNotFoundException ex) {
            manejadorClassNotFoundException(ex);
        }
    }

    //////////////////////////////    
    //////////////////////////////    
    //////////////////////////////    
    //////////////////////////////    
    //////////////////////////////    
    
    private void insertarUsuario(Peticion p){
        
        ObjectOutputStream oos = null;
        
        try {
            Usuario u = (Usuario) p.getEntidad();
            
            CadAMSound cad = new CadAMSound(HOST, USUARIO_BD, CONTRASENA_BD);
            cad.insertarUsuario(u);
            
            Respuesta r = new Respuesta();
            r.setIdOperacion(p.getIdPeticion());
            r.setCantidad(1);
            
            oos = new ObjectOutputStream(clienteConectado.getOutputStream());
            
            oos.writeObject(r);
            oos.close();

        } catch (IOException ex) {
            manejadorIOExceptionOOS(ex, oos);
        } catch (ExcepcionAMSound ex) {
            manejadorExcepcionLineas(ex);            
        }
    }
    
    private void insertarRecurso(Peticion p){
        
        ObjectOutputStream oos = null;
        
        try {
            Recurso recurso = (Recurso) p.getEntidad();
            
            CadAMSound cad = new CadAMSound(HOST, USUARIO_BD, CONTRASENA_BD);
            cad.insertarRecurso(recurso);
            
            Respuesta r = new Respuesta();
            r.setIdOperacion(p.getIdPeticion());
            r.setCantidad(1);
            
            oos = new ObjectOutputStream(clienteConectado.getOutputStream());
            
            oos.writeObject(r);
            oos.close();

        } catch (IOException ex) {
            manejadorIOExceptionOOS(ex, oos);
        } catch (ExcepcionAMSound ex) {
            manejadorExcepcionLineas(ex);            
        }
    }
    
    private void modificarUsuario(Peticion p){
        
        ObjectOutputStream oos = null;
        
        try {
            ArrayList<Object> entidades = (ArrayList<Object>) p.getEntidad();
            Integer id = (Integer) entidades.get(0);
            Usuario u = (Usuario) entidades.get(1);
            
            CadAMSound cad = new CadAMSound(HOST, USUARIO_BD, CONTRASENA_BD);
            cad.modificarUsuario(id, u);
            
            Respuesta r = new Respuesta();
            r.setIdOperacion(p.getIdPeticion());
            r.setCantidad(1);
            
            oos = new ObjectOutputStream(clienteConectado.getOutputStream());
            
            oos.writeObject(r);
            oos.close();

        } catch (IOException ex) {
            manejadorIOExceptionOOS(ex, oos);
        } catch (ExcepcionAMSound ex) {
            manejadorExcepcionLineas(ex);            
        }
    }
    
     private void modificarPiezaMusical(Peticion p){
        
        ObjectOutputStream oos = null;
        
        try {
            CadAMSound cad = new CadAMSound(HOST, USUARIO_BD, CONTRASENA_BD);
            cad.modificarPiezaMusical(p.getIdEntidad(), (PiezaMusical) p.getEntidad());
            
            Respuesta r = new Respuesta();
            r.setIdOperacion(p.getIdPeticion());
            r.setCantidad(1);
            
            oos = new ObjectOutputStream(clienteConectado.getOutputStream());
            
            oos.writeObject(r);
            oos.close();

        } catch (IOException ex) {
            manejadorIOExceptionOOS(ex, oos);
        } catch (ExcepcionAMSound ex) {
            manejadorExcepcionLineas(ex);            
        }
    }
    
    private void eliminarUsuario(Peticion p){
        
        ObjectOutputStream oos = null;
        
        try {
            Integer id = (Integer) p.getEntidad();
            
            CadAMSound cad = new CadAMSound(HOST, USUARIO_BD, CONTRASENA_BD);
            cad.eliminarUsuario(id);
            
            Respuesta r = new Respuesta();
            r.setIdOperacion(p.getIdPeticion());
            r.setCantidad(1);
            
            oos = new ObjectOutputStream(clienteConectado.getOutputStream());
            
            oos.writeObject(r);
            oos.close();

        } catch (IOException ex) {
            manejadorIOExceptionOOS(ex, oos);
        } catch (ExcepcionAMSound ex) {
            manejadorExcepcionLineas(ex);            
        }
    }
    
    private void eliminarPiezaMusical(Peticion p){
        
        ObjectOutputStream oos = null;
        
        try {
            Integer id = (Integer) p.getEntidad();
            
            CadAMSound cad = new CadAMSound(HOST, USUARIO_BD, CONTRASENA_BD);
            cad.eliminarPiezaMusical(id);
            
            Respuesta r = new Respuesta();
            r.setIdOperacion(p.getIdPeticion());
            r.setCantidad(1);
            
            oos = new ObjectOutputStream(clienteConectado.getOutputStream());
            
            oos.writeObject(r);
            oos.close();

        } catch (IOException ex) {
            manejadorIOExceptionOOS(ex, oos);
        } catch (ExcepcionAMSound ex) {
            manejadorExcepcionLineas(ex);            
        }
    }
    
    private void eliminarVoz(Peticion p){
        
        ObjectOutputStream oos = null;
        
        try {
            Integer id = (Integer) p.getEntidad();
            
            CadAMSound cad = new CadAMSound(HOST, USUARIO_BD, CONTRASENA_BD);
            cad.eliminarVoz(id);
            
            Respuesta r = new Respuesta();
            r.setIdOperacion(p.getIdPeticion());
            r.setCantidad(1);
            
            oos = new ObjectOutputStream(clienteConectado.getOutputStream());
            
            oos.writeObject(r);
            oos.close();

        } catch (IOException ex) {
            manejadorIOExceptionOOS(ex, oos);
        } catch (ExcepcionAMSound ex) {
            manejadorExcepcionLineas(ex);            
        }
    }
    
    private void eliminarUsuarioDeVoz(Peticion p){
        
        ObjectOutputStream oos = null;
        
        try {
            CadAMSound cad = new CadAMSound(HOST, USUARIO_BD, CONTRASENA_BD);
            cad.eliminarUsuarioDeVoz(p.getIdEntidad(), (Integer) p.getEntidad());
            
            Respuesta r = new Respuesta();
            r.setIdOperacion(p.getIdPeticion());
            r.setCantidad(1);
            
            oos = new ObjectOutputStream(clienteConectado.getOutputStream());
            
            oos.writeObject(r);
            oos.close();

        } catch (IOException ex) {
            manejadorIOExceptionOOS(ex, oos);
        } catch (ExcepcionAMSound ex) {
            manejadorExcepcionLineas(ex);            
        }
    }
    
    private void eliminarAgrupacion(Peticion p){
        
        ObjectOutputStream oos = null;
        
        try {
            Integer id = (Integer) p.getEntidad();
            
            CadAMSound cad = new CadAMSound(HOST, USUARIO_BD, CONTRASENA_BD);
            cad.eliminarAgrupacion(id);
            
            Respuesta r = new Respuesta();
            r.setIdOperacion(p.getIdPeticion());
            r.setCantidad(1);
            
            oos = new ObjectOutputStream(clienteConectado.getOutputStream());
            
            oos.writeObject(r);
            oos.close();

        } catch (IOException ex) {
            manejadorIOExceptionOOS(ex, oos);
        } catch (ExcepcionAMSound ex) {
            manejadorExcepcionLineas(ex);            
        }
    }
    
    private void eliminarRecurso(Peticion p){
        
        ObjectOutputStream oos = null;
        
        try {
            Integer id = (Integer) p.getEntidad();
            
            CadAMSound cad = new CadAMSound(HOST, USUARIO_BD, CONTRASENA_BD);
            cad.eliminarRecurso(id);
            
            Respuesta r = new Respuesta();
            r.setIdOperacion(p.getIdPeticion());
            r.setCantidad(1);
            
            oos = new ObjectOutputStream(clienteConectado.getOutputStream());
            
            oos.writeObject(r);
            oos.close();

        } catch (IOException ex) {
            manejadorIOExceptionOOS(ex, oos);
        } catch (ExcepcionAMSound ex) {
            manejadorExcepcionLineas(ex);            
        }
    }
    
    private void leerUsuario(Peticion p){
        
        try {
            CadAMSound cad = new CadAMSound(HOST, USUARIO_BD, CONTRASENA_BD);
            Integer id = p.getIdEntidad();
            
            Respuesta r = new Respuesta();
            r.setIdOperacion(p.getIdPeticion());
            r.setEntidad(cad.leerUsuario(id));
            
            ObjectOutputStream oos = new ObjectOutputStream(clienteConectado.getOutputStream());
            oos.writeObject(r);
            oos.close();
            
        } catch (IOException ex) {
            System.out.println("Error en leerUsuario");
        } catch (ExcepcionAMSound ex) {
            manejadorExcepcionLineas(ex);
        }
    }
    
    private void leerUsuarios(Peticion p){
        
        try {
            CadAMSound cad = new CadAMSound(HOST, USUARIO_BD, CONTRASENA_BD);
            
            Respuesta r = new Respuesta();
            r.setIdOperacion(p.getIdPeticion());
            r.setEntidad(cad.leerUsuarios());
            
            ObjectOutputStream oos = new ObjectOutputStream(clienteConectado.getOutputStream());
            oos.writeObject(r);
            oos.close();
            
        } catch (IOException ex) {
            System.out.println("Error en leerUsuarios");
        } catch (ExcepcionAMSound ex) {
            manejadorExcepcionLineas(ex);
        }
    }
    
    private void leerUsuariosDeAgrupacion(Peticion p){
        
        try {
            CadAMSound cad = new CadAMSound(HOST, USUARIO_BD, CONTRASENA_BD);
            
            Respuesta r = new Respuesta();
            r.setIdOperacion(p.getIdPeticion());
            r.setEntidad(cad.leerUsuariosDeAgrupacion(p.getIdEntidad()));
            
            ObjectOutputStream oos = new ObjectOutputStream(clienteConectado.getOutputStream());
            oos.writeObject(r);
            oos.close();
            
        } catch (IOException ex) {
            System.out.println("Error en leerUsuarios");
        } catch (ExcepcionAMSound ex) {
            manejadorExcepcionLineas(ex);
        }
    }
    
    private void leerVocesDeAgrupacion(Peticion p){
        
        try {
            CadAMSound cad = new CadAMSound(HOST, USUARIO_BD, CONTRASENA_BD);
            
            Respuesta r = new Respuesta();
            r.setIdOperacion(p.getIdPeticion());
            r.setEntidad(cad.leerVocesDeAgrupacion(p.getIdEntidad()));
            
            ObjectOutputStream oos = new ObjectOutputStream(clienteConectado.getOutputStream());
            oos.writeObject(r);
            oos.close();
            
        } catch (IOException ex) {
            System.out.println("Error en leerUsuarios");
        } catch (ExcepcionAMSound ex) {
            manejadorExcepcionLineas(ex);
        }
    }
    
    private void leerVocesDeUsuario(Peticion p){
        
        try {
            CadAMSound cad = new CadAMSound(HOST, USUARIO_BD, CONTRASENA_BD);
            
            Respuesta r = new Respuesta();
            r.setIdOperacion(p.getIdPeticion());
            r.setEntidad(cad.leerVocesDeUsuario(p.getIdEntidad(), (Integer) p.getEntidad()));
            
            ObjectOutputStream oos = new ObjectOutputStream(clienteConectado.getOutputStream());
            oos.writeObject(r);
            oos.close();
            
        } catch (IOException ex) {
            System.out.println("Error en leerUsuarios");
        } catch (ExcepcionAMSound ex) {
            manejadorExcepcionLineas(ex);
        }
    }
    
    private void leerPiezasDeAgrupacion(Peticion p){
        
        try {
            CadAMSound cad = new CadAMSound(HOST, USUARIO_BD, CONTRASENA_BD);
            
            Respuesta r = new Respuesta();
            r.setIdOperacion(p.getIdPeticion());
            r.setEntidad(cad.leerPiezasMusicalesDeAgrupacion(p.getIdEntidad()));
            
            ObjectOutputStream oos = new ObjectOutputStream(clienteConectado.getOutputStream());
            oos.writeObject(r);
            oos.close();
            
        } catch (IOException ex) {
            System.out.println("Error en leerUsuarios");
        } catch (ExcepcionAMSound ex) {
            manejadorExcepcionLineas(ex);
        }
    }
    
    private void leerAgrupacionesDeusuario(Peticion p){
        
        Integer idUsuario = (Integer) p.getIdEntidad();
        try {
            CadAMSound cad = new CadAMSound(HOST, USUARIO_BD, CONTRASENA_BD);
            
            Respuesta r = new Respuesta();
            r.setIdOperacion(p.getIdPeticion());
            r.setEntidad(cad.leerAgrupacionesDeUsuario(idUsuario));
            
            ObjectOutputStream oos = new ObjectOutputStream(clienteConectado.getOutputStream());
            oos.writeObject(r);
            oos.close();
            
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ExcepcionAMSound ex) {
            manejadorExcepcionLineas(ex);
        }
    }
    
    private void leerRecursosDePiezaMusical(Peticion p){
        
        try {
            CadAMSound cad = new CadAMSound(HOST, USUARIO_BD, CONTRASENA_BD);
            
            Respuesta r = new Respuesta();
            r.setIdOperacion(p.getIdPeticion());
            r.setEntidad(cad.leerRecursosDePiezaMusical(p.getIdEntidad(), true));
            
            ObjectOutputStream oos = new ObjectOutputStream(clienteConectado.getOutputStream());
            oos.writeObject(r);
            oos.close();
            
        } catch (IOException ex) {
            System.out.println("Error en leerUsuarios");
        } catch (ExcepcionAMSound ex) {
            manejadorExcepcionLineas(ex);
        }
    }
    
    //////////////////////////////    
    //////////////////////////////    
    //////////////////////////////    
    //////////////////////////////    
    //////////////////////////////    
    //////////////////////////////    
    //////////////////////////////    
    //////////////////////////////    
    
    public static void gestionarDialogo(Socket clienteConectado) {
        try {
            System.out.println("Servidor.Consola - El servidor recibe un objeto Coche del cliente");
            ObjectInputStream ois = new ObjectInputStream(clienteConectado.getInputStream());
            String nombre = (String) ois.readObject();
            System.out.println("Nombre recibido del Cliente: " + nombre);
            
            System.out.println("El servidor responde al cliente" + nombre);
            ObjectOutputStream oos = new ObjectOutputStream(clienteConectado.getOutputStream());
            oos.writeObject("Hola Don" + nombre);
            
            Thread.sleep(5000);

                ObjectOutputStream oosas = new ObjectOutputStream(clienteConectado.getOutputStream());
                oosas.writeObject("Adios Don" + nombre);
            
            ois.close();
            oos.close();
            oosas.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } catch (ClassNotFoundException ex) {
        } catch (InterruptedException ex) {
        }
    }
    
    private void manejadorIOExceptionsOIS(IOException ex, ObjectInputStream ois)
    {
        System.out.println(ex);
//        if(ois != null) 
//        {
//            ExcepcionLineas e = new ExcepcionLineas();
//            e.setMensajeUsuario("Error en la comunicación. Consulte con el administrador");
//            e.setMensajeErrorBd(ex.getMessage());
//            Respuesta r = new Respuesta();
//            r.setE(e);
//            try 
//            {
//                ObjectOutputStream oos = new ObjectOutputStream(clienteConectado.getOutputStream());
//                oos.writeObject(r);
//                oos.close();
//            } 
//            catch(IOException ex1) 
//            {
//                System.out.println(ex1);
//            }
//        }


    }
    
    private void manejadorIOExceptionOOS(IOException ex, ObjectOutputStream oos)
    {
        System.out.println(ex);
//        if(oos != null) 
//        {
//            ExcepcionLineas e = new ExcepcionLineas();
//            e.setMensajeUsuario("Error en la comunicación. Consulte con el administrador");
//            e.setMensajeErrorBd(ex.getMessage());
//            Respuesta r = new Respuesta();
//            r.setE(e);
//            try 
//            {
//                ObjectOutputStream os = new ObjectOutputStream(clienteConectado.getOutputStream());
//                os.writeObject(r);
//                os.close();
//            } 
//            catch(IOException ex1) 
//            {
//                System.out.println(ex1);
//            }
//        }
    }
    
    private void manejadorClassNotFoundException(ClassNotFoundException ex){
        System.out.println(ex);
        
//        ExcepcionLineas e = new ExcepcionLineas();
//        e.setMensajeUsuario("Error en la comunicacion. Consulte con el administrador");
//        e.setMensajeErrorBd(ex.getMessage());
//        Respuesta r = new Respuesta();
//        r.setE(e);
//        ObjectOutputStream oos = null;
//        try{
//            oos = new ObjectOutputStream(clienteConectado.getOutputStream());
//            oos.writeObject(r);
//            oos.close();
//        } catch (IOException ex2) {
//            manejadorIOExceptionOOS(ex2, oos);
//        }
        
    }
    
    private void manejadorExcepcionLineas(ExcepcionAMSound e){
        System.out.println(e);
        Respuesta r = new Respuesta();
        r.setE(e);
        ObjectOutputStream oos = null;
        e.printStackTrace();
        try{
            oos = new ObjectOutputStream(clienteConectado.getOutputStream());
            oos.writeObject(r);
            oos.close();
            clienteConectado.close();
        } catch (IOException ex2) {
            manejadorIOExceptionOOS(ex2, oos);
        }
    }
}
