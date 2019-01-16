package peval3Final;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;

public class Principal extends JFrame {

    private JPanel contentPane;

    /**
     * Inicio de la aplicación.
     */
    public static void main(String[] args) {
        Principal frame = new Principal();
        frame.setVisible(true);
    }

    /**
     * Creacion del constructor Principal. Hay creados dos botones que son FTP Y
     * EMAIL,depende del boton que pinches irás a una nueva ventana o otra. Una
     * vez que va hacia una nueva ventana,la ventana Principal se cierra
     */
    public Principal() {
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 236, 191);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel panel = new JPanel();
        panel.setBounds(5, 5, 205, 136);
        contentPane.add(panel);
        panel.setLayout(new GridLayout(0, 1, 0, 0));

        JButton btnNewButton = new JButton("FTP");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Principal.super.dispose();
                new Cliente();
            }
        });
        panel.add(btnNewButton);

        JButton btnNewButton_1 = new JButton("EMAIL");
        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Emails().setVisible(true);
                Principal.super.dispose();
            }
        });
        panel.add(btnNewButton_1);
    }
}
