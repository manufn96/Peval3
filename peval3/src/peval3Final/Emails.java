package peval3Final;

import javax.swing.JFrame;

import javax.swing.JPanel;

import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;
import java.awt.GridLayout;

import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.mail.Message;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.DefaultListModel;
import javax.swing.ListSelectionModel;

public class Emails extends JFrame {

    private JPanel contentPane;
    private JTextField txtDestino;
    private JTextField txtAsunto;
    private JTextArea textArea;
    private JTextField textField;
    private JTextField textField_1;
    private JList list;
    public static ArrayList<datosCorreo> listaDatos = new ArrayList<>();
    public static JLabel lblMensajes;

    /**
     * Creación del constructor Emails. Se crea un hilo para la escucha
     * permanente de emails,es decir,para estar recibiendo emails siempre. Se
     * crea un boton para volver a la ventana Principal por si el usuario desea
     * terminar con el email e irse al ftp,cuando se cierra la ventana Emails se
     * para el hilo para que deje de escuchar y se limpia un ArrayList de una
     * clase objeto la cual va guardando los mensajes que va recibiendo el hilo.
     * Se crea un modelo de lista por defecto el cual recorremos con los datos
     * del ArrayList del objeto que contiene los mensajes para poder tener en
     * una lista los datos. Se crea un sleep del hilo principal para que haya
     * datos del ArrayList,esto es debido a que en el hilo que esta a la escucha
     * recibe un Array de Messages pero el metodo que se encarga tarda un par de
     * segundos en recibir todos los mensajes,una vez pasa los segundos
     * indicados del sleep nos aseugramos que el ArrayList contenga datos y
     * pueda ser mostrado en la lista. Se crea un botón Enviar para el envío de
     * Emails.Para ello llamamos a un metodo estático de otra clase llamada
     * Correo.
     *
     * @see Correo
     *
     */
    public Emails() {
        setTitle("EMAIL");
        hiloCorreo thread = new hiloCorreo();
        thread.start();
        getContentPane().setLayout(new GridLayout(1, 0, 0, 0));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 790, 513);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new GridLayout(0, 1, 0, 0));

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        contentPane.add(tabbedPane);

        JPanel panel = new JPanel();
        tabbedPane.addTab("RECIBO EMAILS", null, panel, null);
        panel.setLayout(null);

        JLabel lblPrncipal = new JLabel("CORREOS");
        lblPrncipal.setBounds(85, 22, 82, 14);
        panel.add(lblPrncipal);

        lblMensajes = new JLabel("");
        lblMensajes.setBounds(465, 11, 190, 14);
        panel.add(lblMensajes);

        JLabel lblDe = new JLabel("De:");
        lblDe.setBounds(342, 49, 27, 14);
        panel.add(lblDe);

        textField = new JTextField();
        textField.setBounds(398, 46, 351, 20);
        panel.add(textField);
        textField.setColumns(10);

        JLabel lblAsunto_1 = new JLabel("Asunto:");
        lblAsunto_1.setBounds(342, 97, 46, 14);
        panel.add(lblAsunto_1);

        textField_1 = new JTextField();
        textField_1.setBounds(398, 94, 351, 20);
        panel.add(textField_1);
        textField_1.setColumns(10);

        JLabel lblMensaje_1 = new JLabel("Mensaje:");
        lblMensaje_1.setBounds(342, 162, 46, 14);
        panel.add(lblMensaje_1);

        JScrollPane scrollPane_1 = new JScrollPane();
        scrollPane_1.setBounds(398, 162, 351, 263);
        panel.add(scrollPane_1);

        JTextArea textArea_1 = new JTextArea();
        scrollPane_1.setViewportView(textArea_1);

        JButton btnVolverAPrincipal = new JButton("VOLVER A PRINCIPAL");
        btnVolverAPrincipal.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                new Principal().setVisible(true);
                thread.stop();
                listaDatos.clear();
                Emails.super.dispose();
            }
        });
        btnVolverAPrincipal.setBounds(30, 308, 190, 61);
        panel.add(btnVolverAPrincipal);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        // necesario para poder meter datos en la lista
        DefaultListModel listmodel = new DefaultListModel<>();
        for (int i = 0; i < listaDatos.size(); i++) {
            listmodel.add(i, listaDatos.get(i).getAsunto());
        }
        JScrollPane scrollpanel = new JScrollPane();
        scrollpanel.setSize(210, 219);
        scrollpanel.setLocation(30, 47);
        list = new JList();
        list.setModel(listmodel);
		// NO HE PODIDO TRADUCIR EL UTF8 DE LOS EMAILS DE ORIGEN,POR LO TANTO APARECE
        // MUCHAS PALABRAS QUE SON CARACRTERES ESPESCIALES PERO LUEGO SI APACERE EL
        // CORREO
        // AQUI PETA,SI DESCOMENTAS EL MOUSE LISTENER COMPLETO ,AL PRINCIPIO PETA,SALTA
        // EXCEPCION,PERO EN LA CONSOLA AL CABO DE VARIOS SEGUNDOS TE MUESTRA TODOS LOS
        // MENSAJES
//		MouseListener mouseListener = new MouseAdapter() {
//		    public void mouseClicked(MouseEvent e) {
//		        if (e.getClickCount() == 1) {
//		           JOptionPane.showMessageDialog(null, "hola desde evento click", "PRUEBA", JOptionPane.INFORMATION_MESSAGE);
//		         }
//		    }
//		};
//		list.addMouseListener(mouseListener);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        scrollpanel.setViewportView(list);
        list.setBounds(30, 48, 210, 194);
        panel.add(scrollpanel);

        JPanel panel_1 = new JPanel();
        tabbedPane.addTab("ENVIAR EMAILS", null, panel_1, null);
        panel_1.setLayout(null);

        JLabel lblDestino = new JLabel("Destino:");
        lblDestino.setBounds(10, 11, 46, 14);
        panel_1.add(lblDestino);

        txtDestino = new JTextField();
        txtDestino.setBounds(66, 8, 683, 20);
        panel_1.add(txtDestino);
        txtDestino.setColumns(10);

        JLabel lblAsunto = new JLabel("Asunto:");
        lblAsunto.setBounds(10, 48, 46, 14);
        panel_1.add(lblAsunto);

        txtAsunto = new JTextField();
        txtAsunto.setBounds(66, 45, 683, 20);
        panel_1.add(txtAsunto);
        txtAsunto.setColumns(10);

        JLabel lblMensaje = new JLabel("Mensaje:");
        lblMensaje.setBounds(10, 94, 92, 14);
        panel_1.add(lblMensaje);

        JButton btnEnviar = new JButton("ENVIAR");
        btnEnviar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String Destino = txtDestino.getText().toString();
                String Asunto = txtAsunto.getText().toString();
                String Mensaje = textArea.getText().toString();
                if (Destino.equalsIgnoreCase("") || Asunto.equalsIgnoreCase("") || Mensaje.equalsIgnoreCase("")) {
                    JOptionPane.showMessageDialog(null, "Se ha detectado campos vacíos al intentar enviar un email",
                            "ERROR", JOptionPane.ERROR_MESSAGE);
                    txtDestino.setText("");
                    txtAsunto.setText("");
                    textArea.setText("");
                } else {
                    Correo.enviarDatos(Destino, Asunto, Mensaje);
                }

            }
        });

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 119, 749, 256);
        panel_1.add(scrollPane);

        textArea = new JTextArea();
        scrollPane.setViewportView(textArea);
        btnEnviar.setBounds(660, 386, 89, 50);
        panel_1.add(btnEnviar);

    }
}
