package app.vista;

import app.controlador.Servicio;
import app.controlador.Usuario;
import app.modelo.Producto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;


public class catalogoProductos extends JFrame{
    private JPanel panelCatalogo;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JButton nuevoButton;
    private JButton mostrarButton;
    private JButton actualizarButton;
    private JButton eliminarButton;
    private JTable tableProductos;
    private JButton btnReporte;
    private JButton salirBtn;
    private JLabel labelUsuario;
    private Object[] columnas = {"ID", "Codigo", "Nombre", "Precio"};
    private Object[] row = new Object[4];
    private Map<Integer, Producto> mapa = null;
    private Servicio controlador = new Servicio();
    private DefaultTableModel model = null;
    private String clave;

    public void obtenerRegistrisTabla() {

        model = new DefaultTableModel() {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int filas, int columnas) {
                return false;
            }
        };
        model.setColumnIdentifiers(columnas);
        while (model.getRowCount() > 0) {
            model.removeRow(0);
        }
        mapa = controlador.seleccionarTodo();
        for (Map.Entry<Integer, Producto> entry : mapa.entrySet()) {
            row[0] = entry.getKey();
            row[1] = entry.getValue().getCodigo();
            row[2] = entry.getValue().getNombre();
            row[3] = String.format("%.2f", entry.getValue().getPrecio());
            model.addRow(row);
        }
        tableProductos.setModel(model);

        tableProductos.getColumnModel().getColumn(0).setPreferredWidth(30);
        tableProductos.getColumnModel().getColumn(1).setPreferredWidth(30);
        tableProductos.getColumnModel().getColumn(2).setPreferredWidth(180);
        tableProductos.getColumnModel().getColumn(3).setPreferredWidth(30);
    }

    private void limpiarCampos() {
    }

    public catalogoProductos() {
        setTitle("Estado");
        setSize(600, 500);
        obtenerRegistrisTabla();;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(panelCatalogo);
        setLocationRelativeTo(null);
        labelUsuario.setText("Bienvenid@: " + Usuario.getUsuario());


        tableProductos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int i = tableProductos.getSelectedRow();
                clave = model.getValueAt(i,0).toString();
                textField1.setText(model.getValueAt(i,1).toString());
                textField2.setText(model.getValueAt(i,2).toString());
                textField3.setText(model.getValueAt(i, 3).toString());
            }
        });
        tableProductos.addMouseListener(new MouseAdapter() {
        });
        mostrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                obtenerRegistrisTabla();
            }
        });


        nuevoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String codigo = textField1.getText().trim();
                    String nombre = textField2.getText().trim();
                    String precioStr = textField3.getText().trim().replace(",", ".");

                    if (codigo.isEmpty() || nombre.isEmpty() || precioStr.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios.");
                        return;
                    }

                    double precio = Double.parseDouble(precioStr);

                    Producto nuevoProducto = new Producto(codigo, nombre, precio);
                    controlador.insertar(nuevoProducto);
                    obtenerRegistrisTabla();
                    limpiarCampos();

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Precio inválido. Usa formato numérico (ej: 10.99)");
                }
            }
        });



        eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt(clave);
                controlador.eliminar(id);
                obtenerRegistrisTabla();
            }
        });
        btnReporte.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    tableProductos.print();
                }catch (Exception e2) {
                    System.out.println(e2.getMessage());
                }
            }
        });
        salirBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Login l = new Login();
                l.setVisible(true);
                setVisible(false);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new catalogoProductos().setVisible(true);
        });
    }
}
