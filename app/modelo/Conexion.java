package app.modelo;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Conexion {
    private static final String URL = "jdbc:mysql://localhost:3306/prod";
    private static final String USER = "root";
    private static final String PASSWORD = "12345";

    public Connection conectar() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conectado a MySQL");
        } catch (SQLException e) {
            System.out.println("Error de conexión: " + e.getMessage());
        }
        return conn;
    }

    public static Map<String, String> cargarUsuarios(String rutaArchivo) {
        Map<String, String> usuarios = new HashMap<>();

        try (BufferedReader lector = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                String[] partes = linea.split(";");
                if (partes.length == 2) {
                    String usuario = partes[0];
                    String contraseña = partes[1];
                    usuarios.put(usuario, contraseña);
                    System.out.println("Usuario: " + usuario + "||" + "Contraseña: " + contraseña);
                } else {
                    System.out.println("Formato inválido: " + linea);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return usuarios;
    }
}
