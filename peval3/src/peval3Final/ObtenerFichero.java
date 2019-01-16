/*
 * Clase que nos proporciona el contenido de un fichero para usarlo a la hora
 * de subir y descargar uno
 */
package peval3Final;

import java.io.Serializable;

public class ObtenerFichero implements Serializable {

    /**
     * Array de bytes que almacena el contenido del fichero con el que
     * trabajamos
     */
    byte[] contenidoFichero;

    public ObtenerFichero(byte[] contenidoFichero) {
        this.contenidoFichero = contenidoFichero;
    }

    public byte[] getContenidoFichero() {
        return contenidoFichero;
    }

    public void setContenidoFichero(byte[] contenidoFichero) {
        this.contenidoFichero = contenidoFichero;
    }

}
