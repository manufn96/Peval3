package peval3Final;

import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;

public class Correo {
    /*
     * Este es el correo de pruebas creada para la peval
     * correo=pruebacorreoftp@gmail.com contraseña=pruebacorreopeval4
     */

    /**
     * Metodo enviarDatos. Su funcionalidad es la enviar los datos que recibe
     * por parámetros. Se crea una instancia de la clase Properties a la cual le
     * pasamos unos parámetros para poder establecer las propiedades de nuestro
     * email. Se crea una instancia de la clase Session a la cual le pasamos los
     * parámetros propiedades y una instancia de la clase Authenticator,la cual
     * comprueba que existe ese correo con esa contraseña y devuelve una
     * autorizacion de contraseña Se crea una instancia de la clase MimeMessage
     * que recibe por parametro la sesion actual,luego añade a quien va
     * destinado el mensaje,añade tambien el asunto y el cuerpo del mensaje y lo
     * envía.
     *
     * @param destino Es un String que contiene el correo al que se envía este
     * mensaje
     * @param asunto Es un String que contiene el asunto del mensaje
     * @param mensaje Es un String que contiene el cuerpo del mensaje
     */
    public static void enviarDatos(String destino, String asunto, String mensaje) {
        Properties propiedades = new Properties();
        propiedades.put("mail.smtp.host", "smtp.gmail.com");
        propiedades.put("mail.smtp.starttls.enable", "true");
        propiedades.put("mail.smtp.auth", "true");
        propiedades.put("mail.smtp.port", "587");
        try {
            Session sesion = Session.getInstance(propiedades, new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("pruebacorreoftp@gmail.com", "uzhlifqmaduwccwb");
                }
            });
            MimeMessage msg = new MimeMessage(sesion);
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(destino));
            msg.setSubject(asunto);
            msg.setText(mensaje);
            // send message
            Transport.send(msg);
            JOptionPane.showMessageDialog(null, "Correo enviado", "INFORMACION", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e1) {
            System.out.println(e1.getMessage());
            JOptionPane.showMessageDialog(null, "Error al intentar enviar el email", "ERROR",
                    JOptionPane.ERROR_MESSAGE);

        }
    }

    /**
     * Metodo recibirDatos. Su funcionalidad es la de recibir correos,se
     * realizar casi los mismos pasos que con el metodo enviarDatos solo que con
     * la diferencia de que no hay que crear una instancia de la clase
     * Authenticator para autentificar el correo con su contraseña y en las
     * propiedades solo debemos pasar como s los datos de host,puerto y tls para
     * pop3. Creamos un almacenamiento para el pop3 y luego se conecta ese
     * almacen con los datos del correo,la contraseña y el pop de gmail. Se crea
     * una carpeta la cual es la carpeta principal de gmail y se abre para leer.
     * A continuación se crea un Array de la clase Message que recibirá todos
     * los mensajes existentes y se devuelve ese Array.
     *
     * @return Message [] msg
     * @throws MessagingException
     */
    public static Message[] recibirDatos() throws MessagingException {
        Properties propiedades = new Properties();
        propiedades.put("mail.pop3.host", "pop.gmail.com");
        propiedades.put("mail.pop3.port", "995");
        propiedades.put("mail.pop3.starttls.enable", "true");
        Session sesion = Session.getInstance(propiedades);
        Store almacen = sesion.getStore("pop3s");
        almacen.connect("pop.gmail.com", "pruebacorreoftp@gmail.com", "uzhlifqmaduwccwb");
        Folder carpeta = almacen.getFolder("INBOX");
        carpeta.open(Folder.READ_ONLY);
        Message[] msg = carpeta.getMessages();
        return msg;

    }

}
