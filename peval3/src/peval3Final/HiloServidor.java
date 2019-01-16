/*
 * Clase que se usará como tubería para realizar operaciones que necesiten datos
 * del servidor y del cliente
 */
package peval3Final;

import java.io.*;
import java.net.*;

public class HiloServidor extends Thread {

    /**
     * Socket
     */
    Socket socket;
    /**
     * Salida
     */
    ObjectOutputStream outObjeto;
    /**
     * Entrada
     */
    ObjectInputStream inObjeto;
    /**
     * Objeto estructuraFich
     */
    EstructuraFicheros estructuraFich;

    public HiloServidor(Socket socket, EstructuraFicheros estructuraFich) {
        this.socket = socket;
        this.estructuraFich = estructuraFich;
        try {
            outObjeto = new ObjectOutputStream(socket.getOutputStream());
            inObjeto = new ObjectInputStream(socket.getInputStream());
        } catch (IOException ex) {
            System.out.println("Error de socket");
        }
    }
    /**
     * Método Run. Dependiendo de la petición que realice el cliente el objeto
     * tendrá una estructura u otra
     */
    public void run() {
        try {
            outObjeto.writeObject(estructuraFich);
            while (true) {
                Object peticion = inObjeto.readObject();
                if (peticion instanceof PedirFichero) {
                    PedirFichero fichero = (PedirFichero) peticion;
                    EnviarFichero(fichero);
                }
                if (peticion instanceof EnviaFichero) {
                    EnviaFichero fich = (EnviaFichero) peticion;
                    File directorio = new File(fich.getDirectorio());
                    File fichero = new File(directorio, fich.getNombre());
                    FileOutputStream outStream = new FileOutputStream(fichero);
                    outStream.write(fich.getContenidoFichero());
                    outStream.close();
                    EstructuraFicheros eFicheros = new EstructuraFicheros(fich.getDirectorio());
                    outObjeto.writeObject(eFicheros);
                }
            }
        } catch (IOException e) {
            //Cliente cierra conexion
            try {
                inObjeto.close();
                outObjeto.close();
                socket.close();
                System.out.println("Cerrando cliente");
            } catch (IOException ex) {
                System.out.println("Error al cerrar el cliente");
            }
        } catch (ClassNotFoundException ee) {
            System.out.println("No se ha encontrado la clase");
        }
    }
    /**
     * Método EnviarFichero. Este método copiará la información del 
     * fichero que hemos seleccionado, cogerá la dirección del servidor y
     * subirá un fichero identico a este.
     */
    private void EnviarFichero(PedirFichero fich) {
        File fichero = new File(fich.getStrNombreFich());
        FileInputStream fInStream = null;
        try {
            fInStream = new FileInputStream(fichero);
            long lgBytes = fichero.length();
            byte[] buffer = new byte[(int) lgBytes];
            int aux, aux2 = 0;
            while ((aux = fInStream.read()) != -1) {
                buffer[aux2] = (byte) aux;
                aux2++;
            }
            fInStream.close();
            Object object = new ObtenerFichero(buffer);
            outObjeto.writeObject(object);
        } catch (FileNotFoundException io) {
            System.out.println("No se ha encontrado el fichero");
        } catch (IOException e) {
            System.out.println("Error al enviar el fichero");
        }
    }
}
