
package Comunicacion;

import CAD.*;
import POJOS.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hugo Vélez Fernández
 */
public class ClienteC {
    
    private Socket socketCliente;
    private static final int PUERTO = 13221;
    private static final int TIMEOUT = 5000;
    
    public ClienteC(String ip) throws ExcepcionAMSound {
        try {
            //System.out.println("Estableciendo la conexion con el servidor");
            socketCliente = new Socket(ip, PUERTO);
            socketCliente.setSoTimeout(TIMEOUT);
            
        } catch (Exception ex) {
            manejadorException(ex);
        }
    }
    
    private void manejadorIOException(IOException ex) throws ExcepcionAMSound {
        ExcepcionAMSound e = new ExcepcionAMSound();
        e.setMensajeUsuario("Fallo en las comunicaciones. Consulte con el administrador");
        e.setMensajeErrorBD(ex.getMessage());
        throw e;
    }
 
    private void manejadorClassNotFoundException(ClassNotFoundException ex) throws ExcepcionAMSound {
        ExcepcionAMSound e = new ExcepcionAMSound();
        e.setMensajeUsuario("Error general en el sistema. Consulte con el administrador");
        e.setMensajeErrorBD(ex.getMessage());
        throw e;
    }
    
    private void manejadorException(Exception ex) throws ExcepcionAMSound {
        ExcepcionAMSound e = new ExcepcionAMSound();
        e.setMensajeUsuario("Error general en el sistema. Consulte con el administrador");
        e.setMensajeErrorBD(ex.getMessage());
        throw e;
    }
    
    public int insertarUsuario(Usuario usuario) throws ExcepcionAMSound {
        
        Peticion p = new Peticion();
        p.setIdPeticion(Operaciones.INSERTAR_USUARIO);
        p.setEntidad(usuario);
        Respuesta r = null;
        int cantidad = 0;
        
        try {
            ObjectOutputStream oos = new ObjectOutputStream(socketCliente.getOutputStream());
            oos.writeObject(p);
            
            ObjectInputStream ois = new ObjectInputStream(socketCliente.getInputStream());
            r = (Respuesta) ois.readObject();

            ois.close();
            oos.close();
            
            if(r.getCantidad() != null)
                cantidad = r.getCantidad();
            else if(r.getE() != null)
                throw r.getE();
            
            
        } catch (IOException ex) {
            manejadorIOException(ex);
        } catch (ClassNotFoundException ex) {
            manejadorClassNotFoundException(ex);
        }
                    
        return cantidad;
    }
    
    public int insertarRecurso(Recurso recurso) throws ExcepcionAMSound {
        
        Peticion p = new Peticion();
        p.setIdPeticion(Operaciones.INSERTAR_RECURSO);
        p.setEntidad(recurso);
        Respuesta r = null;
        int cantidad = 0;
        
        try {
            ObjectOutputStream oos = new ObjectOutputStream(socketCliente.getOutputStream());
            oos.writeObject(p);
            
            ObjectInputStream ois = new ObjectInputStream(socketCliente.getInputStream());
            r = (Respuesta) ois.readObject();

            ois.close();
            oos.close();
            
            if(r.getCantidad() != null)
                cantidad = r.getCantidad();
            else if(r.getE() != null)
                throw r.getE();
            
            
        } catch (IOException ex) {
            manejadorIOException(ex);
        } catch (ClassNotFoundException ex) {
            manejadorClassNotFoundException(ex);
        }
                    
        return cantidad;
    }
    
    public int modificarUsuario(Integer id, Usuario usuario) throws ExcepcionAMSound {
        
        ArrayList<Object> entidades = new ArrayList<>();
        entidades.add(id);
        entidades.add(usuario);
        
        Peticion p = new Peticion();
        p.setIdPeticion(Operaciones.MODIFICAR_USUARIO);
        p.setEntidad(entidades);
        Respuesta r = null;
        int cantidad = 0;
        
        try {
            ObjectOutputStream oos = new ObjectOutputStream(socketCliente.getOutputStream());
            oos.writeObject(p);
            
            ObjectInputStream ois = new ObjectInputStream(socketCliente.getInputStream());
            r = (Respuesta) ois.readObject();

            ois.close();
            oos.close();
            
            if(r.getCantidad() != null)
                cantidad = r.getCantidad();
            else if(r.getE() != null)
                throw r.getE();
            
            
        } catch (IOException ex) {
            manejadorIOException(ex);
        } catch (ClassNotFoundException ex) {
            manejadorClassNotFoundException(ex);
        }
                    
        return cantidad;
    }
    
    public int modificarPiezaMusical(Integer id, PiezaMusical pieza) throws ExcepcionAMSound {
        
        Peticion p = new Peticion();
        p.setIdPeticion(Operaciones.MODIFICAR_PIEZA_MUSICAL);
        p.setIdEntidad(id);
        p.setEntidad(pieza);
        Respuesta r = null;
        int cantidad = 0;
        
        try {
            ObjectOutputStream oos = new ObjectOutputStream(socketCliente.getOutputStream());
            oos.writeObject(p);
            
            ObjectInputStream ois = new ObjectInputStream(socketCliente.getInputStream());
            r = (Respuesta) ois.readObject();

            ois.close();
            oos.close();
            
            if(r.getCantidad() != null)
                cantidad = r.getCantidad();
            else if(r.getE() != null)
                throw r.getE();
            
            
        } catch (IOException ex) {
            manejadorIOException(ex);
        } catch (ClassNotFoundException ex) {
            manejadorClassNotFoundException(ex);
        }
                    
        return cantidad;
    }
    
    public int eliminarUsuario(Integer id) throws ExcepcionAMSound {
        
        Peticion p = new Peticion();
        p.setIdPeticion(Operaciones.ELIMINAR_USUARIO);
        p.setEntidad(id);
        Respuesta r = null;
        int cantidad = 0;
        
        try {
            ObjectOutputStream oos = new ObjectOutputStream(socketCliente.getOutputStream());
            oos.writeObject(p);
            
            ObjectInputStream ois = new ObjectInputStream(socketCliente.getInputStream());
            r = (Respuesta) ois.readObject();

            ois.close();
            oos.close();
            
            if(r.getCantidad() != null)
                cantidad = r.getCantidad();
            else if(r.getE() != null)
                throw r.getE();
            
            
        } catch (IOException ex) {
            manejadorIOException(ex);
        } catch (ClassNotFoundException ex) {
            manejadorClassNotFoundException(ex);
        }
                    
        return cantidad;
    }
    
    public int eliminarPiezaMusical(Integer id) throws ExcepcionAMSound {
        
        Peticion p = new Peticion();
        p.setIdPeticion(Operaciones.ELIMINAR_PIEZA_MUSICAL);
        p.setEntidad(id);
        Respuesta r = null;
        int cantidad = 0;
        
        try {
            ObjectOutputStream oos = new ObjectOutputStream(socketCliente.getOutputStream());
            oos.writeObject(p);
            
            ObjectInputStream ois = new ObjectInputStream(socketCliente.getInputStream());
            r = (Respuesta) ois.readObject();

            ois.close();
            oos.close();
            
            if(r.getCantidad() != null)
                cantidad = r.getCantidad();
            else if(r.getE() != null)
                throw r.getE();
            
            
        } catch (IOException ex) {
            manejadorIOException(ex);
        } catch (ClassNotFoundException ex) {
            manejadorClassNotFoundException(ex);
        }
                    
        return cantidad;
    }
    
    public int eliminarVoz(Integer id) throws ExcepcionAMSound {
        
        Peticion p = new Peticion();
        p.setIdPeticion(Operaciones.ELIMINAR_VOZ);
        p.setEntidad(id);
        Respuesta r = null;
        int cantidad = 0;
        
        try {
            ObjectOutputStream oos = new ObjectOutputStream(socketCliente.getOutputStream());
            oos.writeObject(p);
            
            ObjectInputStream ois = new ObjectInputStream(socketCliente.getInputStream());
            r = (Respuesta) ois.readObject();

            ois.close();
            oos.close();
            
            if(r.getCantidad() != null)
                cantidad = r.getCantidad();
            else if(r.getE() != null)
                throw r.getE();
            
            
        } catch (IOException ex) {
            manejadorIOException(ex);
        } catch (ClassNotFoundException ex) {
            manejadorClassNotFoundException(ex);
        }
                    
        return cantidad;
    }
    
    public int eliminarUsuarioDeVoz(Integer idUsuario, Integer idVoz) throws ExcepcionAMSound {
        
        Peticion p = new Peticion();
        p.setIdPeticion(Operaciones.ELIMINAR_USUARIO_DE_VOZ);
        p.setIdEntidad(idUsuario);
        p.setEntidad(idVoz);
        Respuesta r = null;
        int cantidad = 0;
        
        try {
            ObjectOutputStream oos = new ObjectOutputStream(socketCliente.getOutputStream());
            oos.writeObject(p);
            
            ObjectInputStream ois = new ObjectInputStream(socketCliente.getInputStream());
            r = (Respuesta) ois.readObject();

            ois.close();
            oos.close();
            
            if(r.getCantidad() != null)
                cantidad = r.getCantidad();
            else if(r.getE() != null)
                throw r.getE();
            
            
        } catch (IOException ex) {
            manejadorIOException(ex);
        } catch (ClassNotFoundException ex) {
            manejadorClassNotFoundException(ex);
        }
                    
        return cantidad;
    }
    
    public int eliminarRecurso(Integer id) throws ExcepcionAMSound {
        
        Peticion p = new Peticion();
        p.setIdPeticion(Operaciones.ELIMINAR_RECURSO);
        p.setEntidad(id);
        Respuesta r = null;
        int cantidad = 0;
        
        try {
            ObjectOutputStream oos = new ObjectOutputStream(socketCliente.getOutputStream());
            oos.writeObject(p);
            
            ObjectInputStream ois = new ObjectInputStream(socketCliente.getInputStream());
            r = (Respuesta) ois.readObject();

            ois.close();
            oos.close();
            
            if(r.getCantidad() != null)
                cantidad = r.getCantidad();
            else if(r.getE() != null)
                throw r.getE();
            
            
        } catch (IOException ex) {
            manejadorIOException(ex);
        } catch (ClassNotFoundException ex) {
            manejadorClassNotFoundException(ex);
        }
                    
        return cantidad;
    }
    
    public int eliminarAgrupacion(Integer id) throws ExcepcionAMSound {
        
        Peticion p = new Peticion();
        p.setIdPeticion(Operaciones.ELIMINAR_AGRUPACION);
        p.setEntidad(id);
        Respuesta r = null;
        int cantidad = 0;
        
        try {
            ObjectOutputStream oos = new ObjectOutputStream(socketCliente.getOutputStream());
            oos.writeObject(p);
            
            ObjectInputStream ois = new ObjectInputStream(socketCliente.getInputStream());
            r = (Respuesta) ois.readObject();

            ois.close();
            oos.close();
            
            if(r.getCantidad() != null)
                cantidad = r.getCantidad();
            else if(r.getE() != null)
                throw r.getE();
            
            
        } catch (IOException ex) {
            manejadorIOException(ex);
        } catch (ClassNotFoundException ex) {
            manejadorClassNotFoundException(ex);
        }
                    
        return cantidad;
    }
    
    public Usuario leerUsuario(Integer id) throws ExcepcionAMSound {
            
        Peticion p = new Peticion();
        p.setIdPeticion(Operaciones.LEER_USUARIO);
        p.setIdEntidad(id);
        Respuesta r = null;
        Usuario usuario = null;
        
        try {
            ObjectOutputStream oos = new ObjectOutputStream(socketCliente.getOutputStream());
            oos.writeObject(p);
            
            ObjectInputStream ois = new ObjectInputStream(socketCliente.getInputStream());
            r = (Respuesta) ois.readObject();

            ois.close();
            oos.close();
            
            if(r.getEntidad() != null)
                usuario = (Usuario) r.getEntidad();
            else if(r.getE() != null)
                throw r.getE();
            
            
        } catch (IOException ex) {
            manejadorIOException(ex);
        } catch (ClassNotFoundException ex) {
            manejadorClassNotFoundException(ex);
        }
                    
        return usuario;
    }
    
    public ArrayList<Usuario> leerUsuarios() throws ExcepcionAMSound {
            
        Peticion p = new Peticion();
        p.setIdPeticion(Operaciones.LEER_USUARIOS);
        Respuesta r = null;
        ArrayList<Usuario> listaUsuarios = null;
        
        try {
            ObjectOutputStream oos = new ObjectOutputStream(socketCliente.getOutputStream());
            oos.writeObject(p);
            
            ObjectInputStream ois = new ObjectInputStream(socketCliente.getInputStream());
            r = (Respuesta) ois.readObject();

            ois.close();
            oos.close();
            
            if(r.getEntidad() != null)
                listaUsuarios = (ArrayList<Usuario>) r.getEntidad();
            else if(r.getE() != null)
                throw r.getE();
            
            
        } catch (IOException ex) {
            manejadorIOException(ex);
        } catch (ClassNotFoundException ex) {
            manejadorClassNotFoundException(ex);
        }
                    
        return listaUsuarios;
    }
    
    public ArrayList<Usuario> leerUsuariosDeAgrupacion(Integer idAgrupacion) throws ExcepcionAMSound {
            
        Peticion p = new Peticion();
        p.setIdPeticion(Operaciones.LEER_USUARIOS_DE_AGRUPACION);
        p.setIdEntidad(idAgrupacion);
        Respuesta r = null;
        ArrayList<Usuario> listaUsuarios = null;
        
        try {
            ObjectOutputStream oos = new ObjectOutputStream(socketCliente.getOutputStream());
            oos.writeObject(p);
            
            ObjectInputStream ois = new ObjectInputStream(socketCliente.getInputStream());
            r = (Respuesta) ois.readObject();

            ois.close();
            oos.close();
            
            if(r.getEntidad() != null)
                listaUsuarios = (ArrayList<Usuario>) r.getEntidad();
            else if(r.getE() != null)
                throw r.getE();
            
            
        } catch (IOException ex) {
            manejadorIOException(ex);
        } catch (ClassNotFoundException ex) {
            manejadorClassNotFoundException(ex);
        }
                    
        return listaUsuarios;
    }
    
    public ArrayList<Voz> leerVocesDeAgrupacion(Integer idAgrupacion) throws ExcepcionAMSound {
            
        Peticion p = new Peticion();
        p.setIdPeticion(Operaciones.LEER_VOCES_DE_AGRUPACION);
        p.setIdEntidad(idAgrupacion);
        Respuesta r = null;
        ArrayList<Voz> listaVoces = null;
        
        try {
            ObjectOutputStream oos = new ObjectOutputStream(socketCliente.getOutputStream());
            oos.writeObject(p);
            
            ObjectInputStream ois = new ObjectInputStream(socketCliente.getInputStream());
            r = (Respuesta) ois.readObject();

            ois.close();
            oos.close();
            
            if(r.getEntidad() != null)
                listaVoces = (ArrayList<Voz>) r.getEntidad();
            else if(r.getE() != null)
                throw r.getE();
            
            
        } catch (IOException ex) {
            manejadorIOException(ex);
        } catch (ClassNotFoundException ex) {
            manejadorClassNotFoundException(ex);
        }
                    
        return listaVoces;
    }
    
    public ArrayList<Voz> leerVocesDeUsuario(Integer idUsuario, Integer idAgrupacion) throws ExcepcionAMSound {

        Peticion p = new Peticion();
        p.setIdPeticion(Operaciones.LEER_VOCES_DE_USUARIO);
        p.setIdEntidad(idUsuario);
        p.setEntidad(idAgrupacion);
        Respuesta r = null;
        ArrayList<Voz> listaVoces = null;

        try {
            ObjectOutputStream oos = new ObjectOutputStream(socketCliente.getOutputStream());
            oos.writeObject(p);

            ObjectInputStream ois = new ObjectInputStream(socketCliente.getInputStream());
            r = (Respuesta) ois.readObject();

            ois.close();
            oos.close();

            if(r.getEntidad() != null)
                listaVoces = (ArrayList<Voz>) r.getEntidad();
            else if(r.getE() != null)
                throw r.getE();


        } catch (IOException ex) {
            manejadorIOException(ex);
        } catch (ClassNotFoundException ex) {
            manejadorClassNotFoundException(ex);
        }

        return listaVoces;
    }
    
    public ArrayList<PiezaMusical> leerPiezasDeAgrupacion(Integer idAgrupacion) throws ExcepcionAMSound {
            
        Peticion p = new Peticion();
        p.setIdPeticion(Operaciones.LEER_PIEZAS_MUSICALES_DE_AGRUPACION);
        p.setIdEntidad(idAgrupacion);
        Respuesta r = null;
        ArrayList<PiezaMusical> listaPiezas = null;
        
        try {
            ObjectOutputStream oos = new ObjectOutputStream(socketCliente.getOutputStream());
            oos.writeObject(p);
            
            ObjectInputStream ois = new ObjectInputStream(socketCliente.getInputStream());
            r = (Respuesta) ois.readObject();

            ois.close();
            oos.close();
            
            if(r.getEntidad() != null)
                listaPiezas = (ArrayList<PiezaMusical>) r.getEntidad();
            else if(r.getE() != null)
                throw r.getE();
            
            
        } catch (IOException ex) {
            manejadorIOException(ex);
        } catch (ClassNotFoundException ex) {
            manejadorClassNotFoundException(ex);
        }
                    
        return listaPiezas;
    }
    
    public ArrayList<Agrupacion> leerAgrupacionesDeUsuario(Integer idUsuario) throws ExcepcionAMSound {

        Peticion p = new Peticion();
        p.setIdPeticion(Operaciones.LEER_AGRUPACIONES_DE_USUARIO);
        p.setIdEntidad(idUsuario);
        Respuesta r = null;
        ArrayList<Agrupacion> listaAgrupaciones = null;

        try {
            ObjectOutputStream oos = new ObjectOutputStream(socketCliente.getOutputStream());
            oos.writeObject(p);

            ObjectInputStream ois = new ObjectInputStream(socketCliente.getInputStream());
            r = (Respuesta) ois.readObject();

            ois.close();
            oos.close();

            if(r.getEntidad() != null)
                listaAgrupaciones = (ArrayList<Agrupacion>) r.getEntidad();
            else if(r.getE() != null)
                throw r.getE();


        } catch (IOException ex) {
            ex.printStackTrace();
            manejadorIOException(ex);
        } catch (ClassNotFoundException ex) {
            manejadorClassNotFoundException(ex);
        } catch (Exception ex){
            manejadorException(ex);
        }

        return listaAgrupaciones;
    }
    
    public ArrayList<Recurso> leerRecursosDePiezaMusical(Integer idPiezaMusical) throws ExcepcionAMSound {
            
        Peticion p = new Peticion();
        p.setIdPeticion(Operaciones.LEER_RECURSOS_DE_PIEZA_MUSICAL);
        p.setIdEntidad(idPiezaMusical);
        Respuesta r = null;
        ArrayList<Recurso> listaRecursos = null;
        
        try {
            ObjectOutputStream oos = new ObjectOutputStream(socketCliente.getOutputStream());
            oos.writeObject(p);
            
            ObjectInputStream ois = new ObjectInputStream(socketCliente.getInputStream());
            r = (Respuesta) ois.readObject();

            ois.close();
            oos.close();
            
            if(r.getEntidad() != null)
                listaRecursos = (ArrayList<Recurso>) r.getEntidad();
            else if(r.getE() != null)
                throw r.getE();
            
            
        } catch (IOException ex) {
            ex.printStackTrace();
            manejadorIOException(ex);
        } catch (ClassNotFoundException ex) {
            manejadorClassNotFoundException(ex);
        }
                    
        return listaRecursos;
    }
    
    public void cerrarC() throws ExcepcionAMSound{
        try {
            socketCliente.close();
        } catch (IOException ex) {
            manejadorException(ex);
        }
    }
    
    public static void main(String[] args){
        try {
            //ClienteC c = new ClienteC("192.168.1.75");
            //Usuario usuario = c.leerUsuario(1);
            //ArrayList<Agrupacion> agr = c.leerAgrupacionesDeUsuario(usuario.getId());
            
            //System.out.println(usuario);
            //System.out.println(agr);
            //c.modificarUsuario(2, new Usuario());
            //c.eliminarUsuario(2);
            //System.out.println(c.leerUsuario(1));
            
            ClienteC c = new ClienteC("192.168.1.13");
            System.out.println(c.leerUsuario(1));
            
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(ex);
        }
    }
}
