/**
 * Clase que define la estructura del directorio donde trabajaremos, con sus
 * directorios, ficheros y rutas correspondiente.
 */
package peval3Final;

import java.io.*;

public class EstructuraFicheros implements Serializable {

    /**
     * String que almacena el nombre del directorio
     */
    String strNombreDir;
    /**
     * String que almacena la ruta del directorio
     */
    String strRutaDir;
    /**
     * Boolean que nos indicará si es un directorio o no
     */
    boolean blDir;
    /**
     * Entero que almacena el total de ficheros y directorios del servidor
     */
    int intNumFich;
    /**
     * Array que almacenará los datos de los ficheros
     */
    EstructuraFicheros[] listaFich;

    /**
     * Comprueba si lo que hemos seleccionado es un directorio y guarda los
     * datos que contiene en un array
     */
    public EstructuraFicheros(String strNombreDir) {
        File file = new File(strNombreDir);
        this.strNombreDir = file.getName();
        this.strRutaDir = file.getPath();
        this.blDir = file.isDirectory();
        this.listaFich = getListaFiles();
        if (file.isDirectory()) {
            File[] ficheros = file.listFiles();
            this.intNumFich = 0;
            if (!(ficheros == null)) {
                this.intNumFich = ficheros.length;
            }
        }
    }

    public EstructuraFicheros(String strNombreDir, String strRutaDir, boolean blDir, int intNumFich) {
        this.strNombreDir = strNombreDir;
        this.strRutaDir = strRutaDir;
        this.blDir = blDir;
        this.intNumFich = intNumFich;
    }

    public String getStrNombreDir() {
        String strNombre_dir = strNombreDir;
        if (blDir) {
            int aux = strRutaDir.lastIndexOf(File.separator);
            strNombre_dir = strRutaDir.substring(aux + 1, strRutaDir.length());
        }
        return strNombre_dir;
    }

    public String getStrRutaDir() {
        return strRutaDir;
    }

    public boolean isBlDir() {
        return blDir;
    }

    public int getIntNumFich() {
        return intNumFich;
    }

    public EstructuraFicheros[] getListaFich() {
        return listaFich;
    }

    /**
     * Metodo que le añadirá (DIR) a los directorios para diferenciarlos mejor
     * de los ficheros
     */
    public String toString() {
        String strNombre = this.strNombreDir;
        if (this.blDir) {
            strNombre = " (DIR) " + strNombreDir;
        }
        return strNombre;
    }

    /**
     * Metodo que almacena los datos del servidor en un array
     */
    public EstructuraFicheros[] getListaFiles() {
        EstructuraFicheros[] lista = null;
        String strDirectorioAux = this.strRutaDir;
        File file = new File(strDirectorioAux);
        File[] ficheros = file.listFiles();
        int intTotalFich = ficheros.length;
        if (intTotalFich > 0) {
            lista = new EstructuraFicheros[intTotalFich];
            for (int i = 0; i < ficheros.length; i++) {
                EstructuraFicheros eFicheros;
                String strNombreAux = ficheros[i].getName();
                String strRutaAux = ficheros[i].getPath();
                boolean blDirAux = ficheros[i].isDirectory();
                int num = 0;
                if (blDirAux) {
                    File[] fic = ficheros[i].listFiles();
                    if (!(fic == null)) {
                        num = fic.length;
                    }
                }
                eFicheros = new EstructuraFicheros(strNombreAux, strRutaAux, blDirAux, num);
                lista[i] = eFicheros;
            }
        }
        return lista;
    }
}
