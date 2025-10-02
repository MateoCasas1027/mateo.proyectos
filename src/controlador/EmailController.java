package controlador;

import modelo.Cita;
import javax.swing.*;

public class EmailController {

    public static void enviarConfirmacionCita(Cita cita) {
        // En un sistema real, aquí se integraría con un servicio de email
        // Por ahora simulamos el envío

        String mensaje = String.format(
                "Estimado %s %s,\n\n" +
                        "Su cita ha sido confirmada:\n" +
                        "Médico: %s\n" +
                        "Especialidad: %s\n" +
                        "Fecha: %s\n" +
                        "Hora: %s\n\n" +
                        "Por favor llegue 15 minutos antes de su cita.\n\n" +
                        "Saludos cordiales,\nSistema de Citas Médicas",
                cita.getPaciente().getNombre(),
                cita.getPaciente().getApellido(),
                cita.getMedico().getNombreCompleto(),
                cita.getMedico().getEspecialidad().getNombre(),
                cita.getFechaHora().toLocalDate().toString(),
                cita.getFechaHora().toLocalTime().toString()
        );

        // Simulación de envío de email
        System.out.println("EMAIL ENVIADO:");
        System.out.println(mensaje);
        System.out.println("----------------------------------------");

        // En un sistema real, usaríamos:
        // JavaMail API o Spring Email
    }
}