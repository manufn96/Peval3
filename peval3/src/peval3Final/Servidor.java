/**
 * Clase para iniciar el servidor FTP
 *
 * Cuando iniciemos el servidor nos obligará a elegir un directorio donde
 * trabajar
 *
 */
package peval3Final;

import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public class Servidor {

    /**
     * Entero que indica el puerto a usar
     */
    static int intPuerto = 5000;
    /**
     * Objeto de la clase EstructuraFicheros
     */
    static public EstructuraFicheros estructuraFich;
    /**
     * Socket del servidor
     */
    static ServerSocket servidor;

    public static void main(String[] args) {
        /**
         * String el cual indicará el nombre del directorio a usar como servidor
         */
        String strDirectorio = "";
        /**
         * File chooser para indicar el directorio donde trabajar
         */
        JFileChooser jfcElegirDirectorio = new JFileChooser();
        jfcElegirDirectorio.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        jfcElegirDirectorio.setDialogTitle("Selecciona el directorio deseado");
        int intValor = jfcElegirDirectorio.showDialog(jfcElegirDirectorio, "Seleccionar");
        if (intValor == JFileChooser.APPROVE_OPTION) {
            File file = jfcElegirDirectorio.getSelectedFile();
            strDirectorio = file.getAbsolutePath();
        }
        if (strDirectorio.equals("")) {
            System.out.println("Debe seleccionar un directorio");
            System.exit(1);
        }
        try {
            servidor = new ServerSocket(intPuerto);
            System.out.println("Servidor iniciado en puerto " + intPuerto);
        } catch (IOException ex) {
            System.out.println("Error con el puerto");
        }
        while (true) {
            try {
                Socket cliente = servidor.accept();
                System.out.println("Bienvenido");
                estructuraFich = new EstructuraFicheros(strDirectorio);
                HiloServidor hilo = new HiloServidor(cliente, estructuraFich);
                hilo.start();
            } catch (IOException e) {
                System.out.println("Error al aceptar el cliente");
                System.exit(0);
            }
        }
    }

}
