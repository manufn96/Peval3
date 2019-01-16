/**
 * Clase que se utiliza para obtener el nombre del fichero seleccionado como
 * objeto
 */
package peval3Final;

import java.io.Serializable;

public class PedirFichero implements Serializable {

    /**
     * String que almacena el nombre de un fichero
     */
    String strNombreFich;

    public PedirFichero(String strNombreFich) {
        this.strNombreFich = strNombreFich;
    }

    public String getStrNombreFich() {
        return strNombreFich;
    }

    public void setStrNombreFich(String strNombreFich) {
        this.strNombreFich = strNombreFich;
    }
}
