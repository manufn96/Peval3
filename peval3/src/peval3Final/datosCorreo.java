package peval3;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;

public class datosCorreo {
	private String Asunto, Origen, Mensaje;

	/**
	 * Constructor de la clase datosCorreo Recibe como par�metro un Message al
	 * cual,le indicamos que el asunto y el origen recibir�n los datos de mensaje2.
	 * Se un contenido para mensaje2,este contenido se para como par�metro al m�todo
	 * obtenerTexto.Se realiza este m�todo debido a que los mensajes que se envian
	 * pueden ser y que normalmente en la mayoria de casos lo son,de tipo
	 * MimeMultipart,de forma que de manera normal no se puede leer el texto.
	 * 
	 * @param mensaje2 Recibe un Message
	 * @throws MessagingException
	 * @throws IOException
	 */
	public datosCorreo(Message mensaje2) throws MessagingException, IOException {
		setAsunto(mensaje2.getSubject().toString());
		setOrigen(mensaje2.getFrom()[0].toString());
		Part parteTexto = mensaje2;
		obtenertexto(parteTexto);
	}

	/**
	 * M�todo obtenerTexto. Este m�todo compara si el texto es plano o de tipo
	 * multipart,si es de tipo texto plano,llama a setMensaje para pasarle como
	 * par�metro el contenido del texto del par�metro del m�todo pero si es
	 * multipart se crea una instancia de la clase Multipart y se castea el
	 * contenido del par�metro del m�todo. Se crea una variable integer para contar
	 * todo el contenido y se crea un for para poder recorrerlo,haciendo una llamada
	 * recursiva al m�todo pasandole como par�metro la parte de texto del conteo que
	 * lleve el for.De esta forma iremos desglosando el Multipart del mensaje y ya
	 * si sera texto que se pueda leer y comprender.
	 * 
	 * @param parteTexto Recibe un Part como par�metro
	 * @throws MessagingException
	 * @throws IOException
	 */
	private void obtenertexto(Part parteTexto) throws MessagingException, IOException {
		if (parteTexto.isMimeType("text/plain")) {
			setMensaje((String) parteTexto.getContent());
		} else if (parteTexto.isMimeType("multipart/*")) {
			Multipart mp = (Multipart) parteTexto.getContent();
			int count = mp.getCount();
			for (int i = 0; i < count; i++) {
				obtenertexto(mp.getBodyPart(i));
			}
		}
	}

	public String getAsunto() {
		return Asunto;
	}

	public void setAsunto(String asunto) {
		Asunto = asunto;
	}

	public String getOrigen() {
		return Origen;
	}

	public void setOrigen(String origen) {
		Origen = origen;
	}

	public String getMensaje() {
		return Mensaje;
	}

	public void setMensaje(String mensaje) {
		Mensaje = mensaje;
	}

}
