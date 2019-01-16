/*
 * Clase que se usa para recolectar los datos del fichero a la hora de subirlo
 */
package peval3Final;

import java.io.Serializable;

public class EnviaFichero implements Serializable {
    /**
     * Contenido del fichero a subir
     */
    byte[] contenidoFichero;
    /**
     * String que almacena el nombre de un fichero
     */
    String strNombre;
    /**
     * String que almacena el directorio del fichero a subir
     */
    String strDirectorio;

    public EnviaFichero(byte[] contenidoFichero, String strNombre, String strDirectorio) {
        this.contenidoFichero = contenidoFichero;
        this.strNombre = strNombre;
        this.strDirectorio = strDirectorio;
    }

    public byte[] getContenidoFichero() {
        return contenidoFichero;
    }

    public String getNombre() {
        return strNombre;
    }

    public String getDirectorio() {
        return strDirectorio;
    }

}
