
package Comunicacion;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Enumeration;

/**
 * Servidor de comunicaciones para la aplicación AMSound
 * @author Hugo Vélez Fernández
 */
public class ServidorC extends Thread {
    
    private ServerSocket servidor;
    private Socket conexion;
    public static final int PUERTO = 13221;
    
    /**
     * Constructor vacio | Valores por defecto
     */
    public ServidorC(){}
    
    @Override
    public void run(){
        
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface ni = interfaces.nextElement();
                Enumeration<InetAddress> inetAddresses = ni.getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    InetAddress ia = inetAddresses.nextElement();
                    if (!ia.isLoopbackAddress() && ia.isSiteLocalAddress()) {
                        System.out.println("IP privada: " + ia.getHostAddress());
                    }
                }
            }
//            
            servidor = new ServerSocket(PUERTO);
            while(true){
                conexion = servidor.accept();
                ManejadorPeticion mp = new ManejadorPeticion(conexion);
                mp.start();
            }
            
        } catch (Exception ex) {
            System.err.println("Error en el servidor de comunicaciones\n"+ex.toString());
        }
    }
    
    public static void main(String[] args){
        ServidorC servidor = new ServidorC();
        servidor.start();
    }
}
