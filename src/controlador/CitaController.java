package controlador;

import modelo.*;
import util.DatabaseUtil;
import vista.Ventana;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class CitaController {
    private final Ventana ventana;
    private final DatabaseUtil database;
    private final AutenticacionController authController;

    public CitaController(Ventana ventana, AutenticacionController authController) {
        this.ventana = ventana;
        this.database = DatabaseUtil.getInstance();
        this.authController = authController;

        // Esperar un momento para que la ventana se inicialice completamente
        Timer timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                configurarEventos();
                cargarDatosIniciales();
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    private void configurarEventos() {
        // Verificar que los componentes no sean null antes de agregar listeners
        if (ventana.getBtnConfirmarCita() != null) {
            ventana.getBtnConfirmarCita().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    solicitarCita();
                }
            });
        }

        if (ventana.getBtnVolverMenu() != null) {
            ventana.getBtnVolverMenu().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ventana.mostrarMenu();
                }
            });
        }

        if (ventana.getCmbEspecialidad() != null) {
            ventana.getCmbEspecialidad().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    cargarMedicos();
                }
            });
        }

        if (ventana.getCmbFecha() != null) {
            ventana.getCmbFecha().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    cargarHorasDisponibles();
                }
            });
        }
    }

    private void cargarDatosIniciales() {
        if (ventana.getCmbEspecialidad() != null && ventana.getCmbFecha() != null) {
            cargarFechasDisponibles();
            cargarMedicos();
        }
    }

    private void cargarFechasDisponibles() {
        if (ventana.getCmbFecha() == null) return;

        ventana.getCmbFecha().removeAllItems();
        LocalDate hoy = LocalDate.now();

        for (int i = 1; i <= 30; i++) {
            LocalDate fecha = hoy.plusDays(i);
            if (fecha.getDayOfWeek().getValue() <= 5) {
                ventana.getCmbFecha().addItem(fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            }
        }
    }

    private void cargarMedicos() {
        if (ventana.getCmbMedico() == null || ventana.getCmbEspecialidad() == null) return;

        ventana.getCmbMedico().removeAllItems();
        String especialidadSeleccionada = (String) ventana.getCmbEspecialidad().getSelectedItem();

        if (especialidadSeleccionada != null) {
            for (Medico medico : database.obtenerMedicosPorEspecialidad(especialidadSeleccionada)) {
                ventana.getCmbMedico().addItem(medico.getNombreCompleto());
            }
        }
    }

    private void cargarHorasDisponibles() {
        if (ventana.getCmbHora() == null) return;

        ventana.getCmbHora().removeAllItems();

        LocalTime horaInicio = LocalTime.of(7, 0);
        LocalTime horaFin = LocalTime.of(20, 0);

        LocalTime horaActual = horaInicio;
        while (horaActual.isBefore(horaFin)) {
            ventana.getCmbHora().addItem(horaActual.toString());
            horaActual = horaActual.plusMinutes(40);
        }
    }

    private void solicitarCita() {
        if (authController.getPacienteActual() == null) {
            JOptionPane.showMessageDialog(ventana, "Debe iniciar sesión primero");
            return;
        }

        try {
            String fechaStr = (String) ventana.getCmbFecha().getSelectedItem();
            String horaStr = (String) ventana.getCmbHora().getSelectedItem();
            String medicoStr = (String) ventana.getCmbMedico().getSelectedItem();

            if (fechaStr == null || horaStr == null || medicoStr == null) {
                JOptionPane.showMessageDialog(ventana, "Por favor complete todos los campos");
                return;
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            LocalDateTime fechaHora = LocalDateTime.parse(fechaStr + " " + horaStr, formatter);

            Medico medico = database.obtenerMedicoPorNombre(medicoStr);

            if (medico != null && database.verificarDisponibilidadCita(authController.getPacienteActual(), medico, fechaHora)) {
                Cita cita = new Cita(generarIdCita(), authController.getPacienteActual(), medico, fechaHora);

                if (database.agendarCita(cita)) {
                    EmailController.enviarConfirmacionCita(cita);
                    JOptionPane.showMessageDialog(ventana,
                            "Cita agendada exitosamente. Se ha enviado un correo de confirmación.");
                    ventana.mostrarMenu();
                } else {
                    JOptionPane.showMessageDialog(ventana, "Error al agendar la cita");
                }
            } else {
                JOptionPane.showMessageDialog(ventana,
                        "No hay disponibilidad en ese horario o ya tiene una cita en la misma especialidad");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(ventana, "Error al procesar la cita: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private String generarIdCita() {
        return "CITA_" + System.currentTimeMillis();
    }
}