package main;

import controlador.AutenticacionController;
import controlador.CitaController;
import vista.Ventana;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Ventana ventana = new Ventana();
            ventana.setVisible(true); // Primero mostrar la ventana

            // Pequeño delay para asegurar inicialización
            Timer timer = new Timer(100, e -> {
                AutenticacionController authController = new AutenticacionController(ventana);
                CitaController citaController = new CitaController(ventana, authController);
            });
            timer.setRepeats(false);
            timer.start();
        });
    }
}