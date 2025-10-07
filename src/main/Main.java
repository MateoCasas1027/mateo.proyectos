package main;

import controlador.AutenticacionController;
import controlador.CitaController;
import util.DatabaseUtil; // <--- AGREGADO
import vista.Ventana;
import javax.swing.*;
import java.awt.event.WindowAdapter; // <--- AGREGADO
import java.awt.event.WindowEvent; // <--- AGREGADO

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Ventana ventana = new Ventana();
            ventana.setVisible(true);

            // ----------------------------------------------------
            // BLOQUE AGREGADO: Guardar datos al cerrar la ventana
            ventana.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    DatabaseUtil.getInstance().guardarDatos();
                }
            });
            // ----------------------------------------------------

            Timer timer = new Timer(100, e -> {
                AutenticacionController authController = new AutenticacionController(ventana);
                CitaController citaController = new CitaController(ventana, authController);
            });
            timer.setRepeats(false);
            timer.start();
        });
    }
}