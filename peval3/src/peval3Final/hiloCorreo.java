package peval3;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.mail.Message;
import javax.swing.JOptionPane;

public class hiloCorreo extends Thread {

	/**
	 * Esta es la clase hilo,tiene un bucle infinito de manera que estará siempre a
	 * la escucha. Se crea un Array de Message dado que el metodo recibirDatos de la
	 * clase Correo devuelve ese mismo Array. Se crea un for para poder recorrer
	 * todos los mensajes que existen y añadirlos en un ArrayList de la clase objeto
	 * datosCorreo. Se crea un sleep para el hilo de forma que cada cierto tiempo se
	 * duerma,básicamente es por que tenga cierta similitud al correo de
	 * gmail,recibes correo cada x tiempo. Se llama al ArrayList para poder limpiar
	 * y borrar todos los datos,se hace esta llamada ya que al estar recibiendo
	 * todos los correos volvería a añadir al ArrayList los correos antiguos y los
	 * nuevos
	 * 
	 * @see datosCorreo
	 * @see Correo
	 */
	public void run() {

		while (true) {
			try {
				Message[] msg = Correo.recibirDatos();
				Emails.lblMensajes.setText("Hay un total de " + String.valueOf(msg.length) + " mensajes");
				
				for (int i = 0, n = msg.length; i < n; i++) {
					Message mensaje = msg[i];
					Emails.listaDatos.add(new datosCorreo(mensaje));
				}
				mostrarMensajes();
			} catch (Exception e) {
				// TODO: handle exception
				JOptionPane.showMessageDialog(null,
						"Ha habido un conflicto con el programa,si tiene un antivirus activado,desactivelo y vuelva a intentarlo",
						"ERROR", JOptionPane.ERROR_MESSAGE);
				System.exit(0);
			}
			try {
				Thread.sleep(50000);
				Emails.listaDatos.clear();
				Emails.contador = 0;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage());
			}
		}
	}

	/**
	 * Metodo mostrarMensajes que su única funcionalidad es la de mostrar los
	 * mensajes existentes que hay en el correo. Se crea los eventos de los botones
	 * para poder recorrer los mensajes en orden de llegada. Se utiliza el try y
	 * catch por si se dá el caso de que el usuario intentar leer mensajes que no
	 * existe,es decir,si hay 5 mensajes y empieza viendo el primer mensaje y quiere
	 * ver el mensaje anterior,como es negativo controlamos ese error mostrándole
	 * que es un error y cerrar el programa.
	 * 
	 * @see Emails
	 */
	private void mostrarMensajes() {
		try {
			Emails.txt_origen.setText(Emails.listaDatos.get(Emails.contador).getOrigen());
			Emails.txt_Asunto.setText(Emails.listaDatos.get(Emails.contador).getAsunto());
			Emails.txt_mensaje.setText(Emails.listaDatos.get(Emails.contador).getMensaje());
			Emails.btnAtras.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						Emails.contador--;
						Emails.txt_origen.setText(Emails.listaDatos.get(Emails.contador).getOrigen());
						Emails.txt_Asunto.setText(Emails.listaDatos.get(Emails.contador).getAsunto());
						Emails.txt_mensaje.setText(Emails.listaDatos.get(Emails.contador).getMensaje());
					} catch (Exception e1) {}
				}
			});
			Emails.btnAdelante.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						Emails.contador++;
						Emails.txt_origen.setText(Emails.listaDatos.get(Emails.contador).getOrigen());
						Emails.txt_Asunto.setText(Emails.listaDatos.get(Emails.contador).getAsunto());
						Emails.txt_mensaje.setText(Emails.listaDatos.get(Emails.contador).getMensaje());
					} catch (Exception e1) {}

				}
			});
		} catch (Exception e) {}

	}
}
