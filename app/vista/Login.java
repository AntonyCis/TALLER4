package app.vista;

import app.controlador.Usuario;
import app.modelo.Conexion;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

public class Login extends JFrame{
    private JPanel panelLogin;
    private JTextField txtNombre;
    private JButton btnAcceso;
    private JButton btnLimpiar;
    private JPasswordField txtPassword;
    private JComboBox comboBox1;
    private String rutaArchivo = "C:\\Users\\POO\\Documents\\Proyectos\\CRUD\\src\\app\\modelo\\users.txt";


    public Login(){
        setTitle("LOGIN");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(panelLogin);
        setLocationRelativeTo(null);

        btnAcceso.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = txtNombre.getText();
                String password = new String(txtPassword.getPassword());

                if (nombre.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor llene todos los campos");
                } else {
                    Map<String, String> usuarios = Conexion.cargarUsuarios(rutaArchivo);

                    if (usuarios.containsKey(nombre) && usuarios.get(nombre).equals(password)) {
                        Usuario.setUsuario(nombre);
                        catalogoProductos c = new catalogoProductos();
                        c.setVisible(true);
                        setVisible(false);
                    } else {
                        JOptionPane.showMessageDialog(null, "Credenciales incorrectas");
                    }
                }
            }
        });

        btnLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtNombre.setText("");
                txtPassword.setText("");
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Login l = new Login();
            l.setVisible(true);
        });
    }
}
