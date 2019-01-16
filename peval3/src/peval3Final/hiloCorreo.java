package peval3Final;

import javax.mail.Message;
import javax.swing.JOptionPane;

public class hiloCorreo extends Thread {

    /**
     * Esta es la clase hilo,tiene un bucle infinito de manera que estará
     * siempre a la escucha. Se crea un Array de Message dado que el metodo
     * recibirDatos de la clase Correo devuelve ese mismo Array. Se crea un for
     * para poder recorrer todos los mensajes que existen y añadirlos en un
     * ArrayList de la clase objeto datosCorreo. Se crea un sleep para el hilo
     * de forma que cada cierto tiempo se duerma,básicamente es por que tenga
     * cierta similitud al correo de gmail,recibes correo cada x tiempo. Se
     * llama al ArrayList para poder limpiar y borrar todos los datos,se hace
     * esta llamada ya que al estar recibiendo todos los correos volvería a
     * añadir al ArrayList los correos antiguos y los nuevos
     *
     * @see datosCorreo
     * @see Correo
     */
    public void run() {
        while (true) {
            try {
                Message[] msg = Correo.recibirDatos();
                Emails.lblMensajes.setText("Hay un total de " + String.valueOf(msg.length) + " mensajes");
                /*
                 * HAY QUE TENER CUIDADO CON EL ENVIO DE MENSAJES,ES DECIR,SI ESCRIBES UN
                 * MENSAJE DE SALUDO A UNA PERSONA PREGUNTADOLE COMO ESTA , GOOGLE DIRECTAMENTE
                 * TE DA UNA BOTONERA CON OPCIONES Y AL SER ESO HTML NO ESTA IMPLEMENTADO EN EL
                 * PROGRAMA Y ESTARÁ A LA ESPERA DE PODER LEER HTML
                 */
                for (int i = 0, n = msg.length; i < n; i++) {
                    Message mensaje = msg[i];
                    System.out.println("---------------------------------");
                    System.out.println("Email Number " + (i + 1));
                    System.out.println("Asunto: " + mensaje.getSubject());
                    System.out.println("Origen: " + mensaje.getFrom()[0]);
                    Emails.listaDatos.add(new datosCorreo(mensaje));
                    System.out.println(Emails.listaDatos.size());
                }

            } catch (Exception e) {
                // TODO: handle exception
                JOptionPane.showMessageDialog(null,
                        "Ha habido un conflicto con el programa,si tiene un antivirus activado,desactivelo y vuelva a intentarlo",
                        "ERROR", JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }
            try {
                Thread.sleep(3000);
                Emails.listaDatos.clear();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                System.out.println(e.getMessage());
            }
        }

    }
}
