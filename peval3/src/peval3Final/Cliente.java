/*
 * Clase para iniciar el cliente
 */
package peval3Final;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class Cliente extends JFrame implements Runnable {

    private static final long serialVersionUID = 1L;
    /**
     * Componentes swing
     */
    static JLabel lblConectado = new JLabel();
    static JLabel lblRaiz = new JLabel();

    private static JLabel lblFichSelec = new JLabel();
    private static JLabel lblNumFich = new JLabel();
    /**
     * Botón que subirá un fichero al servidor
     */
    JButton botonCargar = new JButton("Subir fichero");
    /**
     * Botón que descargará un fichero del servidor
     */
    JButton botonDescargar = new JButton("Descargar fichero");
    /**
     * Botón con el cual se desconecta el cliente
     */
    JButton botonSalir = new JButton("Salir");
    /**
     * Lista que mostrará los directorios y ficheros del servidor donde
     * trabajamos
     */
    static JList listaDirec = new JList();

    static Socket socket;
    /**
     * Objeto tipo estructuraFichero que nos ayudará a saber qué fichero hemos
     * seleccionado
     */
    EstructuraFicheros nodo = null;
    /**
     * Objeto tipo estructuraFichero que nos ayudará a recoger los directorios y
     * ficheros del lugar de trabajo
     */
    EstructuraFicheros raiz;
    ObjectInputStream inObjeto;
    ObjectOutputStream outObjeto;
    /**
     * String que mostrará el directorio donde trabaja el servidor
     */
    static String direcSelec = "";
    /**
     * String que mostrará el fichero o directorio seleccionado por el cliente
     */
    static String ficheroSelec = "";
    /**
     * Ruta del fichero
     */
    static String rutaFichero = "";

    public Cliente() {
        /**
         * Entero que indica el puerto por donde el cliente se conectará al
         * servidor
         */
        int intPuerto = 5000;
        try {
            Socket s = new Socket("localhost", intPuerto);
            Cliente hiloCliente = new Cliente(s);
            hiloCliente.setBounds(0, 0, 540, 500);
            hiloCliente.setVisible(true);
            new Thread(hiloCliente).start();
        } catch (IOException ex) {
            System.out.println("Error al conectar con localhost o el puerto, compruebe que el servidor está encendido");
        }
    }

    /**
     * Constructor de la clase cliente, aquí inicializaremos variables y
     * crearemos una ventana
     */
    public Cliente(Socket s) {
        super("Cliente");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 453, 94);
        setResizable(false);
        socket = s;
        try {
            outObjeto = new ObjectOutputStream(socket.getOutputStream());
            inObjeto = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            System.out.println("Error en el socket del cliente");
            System.exit(0);
        }
        listaDirec.addListSelectionListener(new ListSelectionListener() {
            @Override
            /**
             * Al pulsar un elemento de la lista lo muestra en un label
             */
            public void valueChanged(ListSelectionEvent lse) {
                if (lse.getValueIsAdjusting()) {
                    ficheroSelec = "";
                    rutaFichero = "";
                    nodo = (EstructuraFicheros) listaDirec.getSelectedValue();
                    if (nodo.isBlDir()) {
                        lblFichSelec.setText("-------");
                    } else {
                        ficheroSelec = nodo.getStrNombreDir();
                        rutaFichero = nodo.getStrRutaDir();
                        lblFichSelec.setText("Fichero seleccionado: " + ficheroSelec);
                    }
                }
            }
        });
        botonSalir.addActionListener(new ActionListener() {
            @Override
            /**
             * Al pulsar el cliente sale de la aplicación
             */
            public void actionPerformed(ActionEvent e) {
                try {
                    socket.close();
                    System.exit(0);
                } catch (IOException ex) {
                    System.out.println("Error al salir");
                }
            }
        });
        botonCargar.addActionListener(new ActionListener() {
            @Override
            /**
             * Al pulsar el cliente sube un fichero al servidor. Un buffer lee
             * el fichero y lo copia.
             */
            public void actionPerformed(ActionEvent e) {
                JFileChooser fChooser;
                File file;
                fChooser = new JFileChooser();
                fChooser.setDialogTitle("Selecciona el fichero para subir al servidor");
                int intValor = fChooser.showDialog(fChooser, "Cargar");
                if (intValor == JFileChooser.APPROVE_OPTION) {
                    file = fChooser.getSelectedFile();
                    String strArchivo = file.getAbsolutePath();
                    String strNombreArch = file.getName();
                    BufferedInputStream inBuff;
                    try {
                        inBuff = new BufferedInputStream(new FileInputStream(strArchivo));
                        long lgBytes = file.length();
                        byte[] buffer = new byte[(int) lgBytes];
                        int aux, aux2 = 0;
                        while ((aux = inBuff.read()) != -1) {
                            buffer[aux2] = (byte) aux;
                            aux2++;
                        }
                        inBuff.close();
                        Object object = new EnviaFichero(buffer, strNombreArch, direcSelec);
                        outObjeto.writeObject(object);
                        JOptionPane.showMessageDialog(null, "Fichero cargado");
                        nodo = (EstructuraFicheros) inObjeto.readObject();
                        EstructuraFicheros[] lista = nodo.getListaFich();
                        direcSelec = nodo.getStrRutaDir();
                        llenarLista(lista, nodo.getIntNumFich());
                        lblNumFich.setText("Numero de ficheros: " + lista.length);
                    } catch (FileNotFoundException fl) {
                        System.out.println("No se ha encontradon el fichero");
                    } catch (IOException exc) {
                        System.out.println("Error en el buffer");
                    } catch (ClassNotFoundException cnf) {
                        System.out.println("Clase no encontrada");
                    }
                }
            }
        });
        botonDescargar.addActionListener(new ActionListener() {
            @Override
            /**
             * Descarga el fichero seleccionado en el directorio donde se
             * encuentra el programa.
             */
            public void actionPerformed(ActionEvent e) {
                if (rutaFichero.equals("")) {
                    return;
                }
                PedirFichero pedirFichero = new PedirFichero(rutaFichero);
                try {
                    outObjeto.writeObject(pedirFichero);
                    FileOutputStream fOutStream = new FileOutputStream(ficheroSelec);
                    Object object = inObjeto.readObject();
                    if (object instanceof ObtenerFichero) {
                        ObtenerFichero fich = (ObtenerFichero) object;
                        fOutStream.write(fich.getContenidoFichero());
                        fOutStream.close();
                        JOptionPane.showMessageDialog(null, "Fichero descargado");
                    }
                } catch (IOException ex) {
                    System.out.println("Error en la descarga");
                } catch (ClassNotFoundException cnf) {
                    System.out.println("Clase no encontrada");
                }
            }
        });
        /**
         * Creación de la ventana.
         */
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setBorder(new LineBorder(Color.GRAY));
        setContentPane(panelPrincipal);
        panelPrincipal.setLayout(null);

        JPanel panelLogin = new JPanel();
        panelLogin.setBackground(new Color(245, 245, 220));
        panelLogin.setBorder(new LineBorder(new Color(128, 128, 128), 2, true));
        panelLogin.setBounds(10, 11, 427, 43);
        panelPrincipal.add(panelLogin);
        panelLogin.setLayout(null);

        lblConectado.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblConectado.setBounds(10, 11, 150, 14);
        panelLogin.add(lblConectado);

        lblRaiz.setFont(new Font("Segoe UI Historic", Font.PLAIN, 14));
        lblRaiz.setBounds(160, 11, 180, 14);
        panelLogin.add(lblRaiz);

        botonSalir.setFont(new Font("Tahoma", Font.BOLD, 11));
        botonSalir.setBounds(345, 9, 81, 23);
        panelLogin.add(botonSalir);

        botonCargar.setBounds(10, 288, 120, 23);
        panelPrincipal.add(botonCargar);

        lblFichSelec.setBounds(10, 322, 212, 14);
        panelPrincipal.add(lblFichSelec);

        lblNumFich.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblNumFich.setBounds(30, 65, 160, 14);
        panelPrincipal.add(lblNumFich);

        JScrollPane scroll1 = new JScrollPane();
        scroll1.setBounds(10, 82, 212, 197);
        panelPrincipal.add(scroll1);
        scroll1.setViewportView(listaDirec);

        botonDescargar.setBounds(135, 288, 180, 23);
        panelPrincipal.add(botonDescargar);

        setVisible(true);

    }

    @Override
    /**
     * Metodo Run. Le da valores a variables anteriormente mencionadas que se
     * mostrarán en la ventana
     */
    public void run() {
        try {
            lblConectado.setText("Conectando con el servidor");
            raiz = (EstructuraFicheros) inObjeto.readObject();
            EstructuraFicheros[] nodos = raiz.getListaFich();
            direcSelec = raiz.getStrRutaDir();
            llenarLista(nodos, raiz.getIntNumFich());
            lblRaiz.setText("Raiz: " + direcSelec);
            lblConectado.setText("Conectado al servidor");
            lblNumFich.setText("Numero de ficheros: " + raiz.getIntNumFich());
        } catch (IOException e) {
            System.out.println("Error en el run");
        } catch (ClassNotFoundException cnf) {
            System.out.println("No se ha podido leer el objeto");
        }
    }

    /**
     * Metodo llenarLista. Llena la lista con los directorios y ficheros del
     * servidor con el cual trabajamos
     */
    private void llenarLista(EstructuraFicheros[] ficheros, int intNumFich) {
        if (intNumFich == 0) {
            return;
        }
        DefaultListModel modeloLista = new DefaultListModel();
        Font fuente = new Font("TimesRoman", Font.PLAIN, 14);
        listaDirec.setFont(fuente);
        listaDirec.removeAll();
        for (int i = 0; i < ficheros.length; i++) {
            modeloLista.addElement(ficheros[i]);
            try {
                listaDirec.setModel(modeloLista);
            } catch (NullPointerException e) {
                System.out.println("Error al llenar la lista");
            }
        }
    }

}
