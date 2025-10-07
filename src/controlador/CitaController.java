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
import java.util.List;
import java.util.stream.Collectors;

public class CitaController {
    private final Ventana ventana;
    private final DatabaseUtil database;
    private final AutenticacionController authController;

    public CitaController(Ventana ventana, AutenticacionController authController) {
        this.ventana = ventana;
        this.database = DatabaseUtil.getInstance();
        this.authController = authController;

        // Configurar la referencia en authController
        authController.setCitaController(this);

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
        // Eventos existentes para solicitar cita
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

        // Nuevos eventos para reasignar y cancelar citas
        if (ventana.getBtnConfirmarReasignacion() != null) {
            ventana.getBtnConfirmarReasignacion().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    reasignarCita();
                }
            });
        }

        if (ventana.getBtnConfirmarCancelacion() != null) {
            ventana.getBtnConfirmarCancelacion().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    cancelarCita();
                }
            });
        }

        // Eventos para combobox
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

        if (ventana.getCmbNuevaFecha() != null) {
            ventana.getCmbNuevaFecha().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    cargarHorasReasignacion();
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

    // Métodos para Mis Citas
    public void cargarMisCitas() {
        if (authController.getPacienteActual() == null) return;

        DefaultListModel<String> model = ventana.getModelMisCitas();
        model.clear();

        List<Cita> citas = database.obtenerCitasPorPaciente(authController.getPacienteActual().getId());

        for (Cita cita : citas) {
            if (!"CANCELADA".equals(cita.getEstado())) {
                String infoCita = String.format("Dr. %s %s - %s | %s %s | %s",
                        cita.getMedico().getNombre(),
                        cita.getMedico().getApellido(),
                        cita.getMedico().getEspecialidad().getNombre(),
                        cita.getFechaHora().toLocalDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                        cita.getFechaHora().toLocalTime(),
                        cita.getEstado());
                model.addElement(infoCita);
            }
        }

        if (model.isEmpty()) {
            model.addElement("No tiene citas programadas");
        }
    }

    // Métodos para Reasignar Cita
    public void cargarCitasParaReasignar() {
        if (authController.getPacienteActual() == null) return;

        DefaultListModel<String> model = ventana.getModelCitasReasignar();
        model.clear();

        List<Cita> citas = database.obtenerCitasPorPaciente(authController.getPacienteActual().getId());

        for (Cita cita : citas) {
            if ("PROGRAMADA".equals(cita.getEstado()) &&
                    cita.getFechaHora().isAfter(LocalDateTime.now().plusHours(24))) {
                String infoCita = String.format("Dr. %s %s - %s | %s %s",
                        cita.getMedico().getNombre(),
                        cita.getMedico().getApellido(),
                        cita.getMedico().getEspecialidad().getNombre(),
                        cita.getFechaHora().toLocalDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                        cita.getFechaHora().toLocalTime());
                model.addElement(infoCita);
            }
        }

        if (model.isEmpty()) {
            model.addElement("No tiene citas que puedan ser reasignadas");
            ventana.getBtnConfirmarReasignacion().setEnabled(false);
        } else {
            ventana.getBtnConfirmarReasignacion().setEnabled(true);
        }
    }

    public void cargarFechasReasignacion() {
        if (ventana.getCmbNuevaFecha() == null) return;

        ventana.getCmbNuevaFecha().removeAllItems();
        LocalDate hoy = LocalDate.now();

        for (int i = 1; i <= 30; i++) {
            LocalDate fecha = hoy.plusDays(i);
            if (fecha.getDayOfWeek().getValue() <= 5) {
                ventana.getCmbNuevaFecha().addItem(fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            }
        }
    }

    private void cargarHorasReasignacion() {
        if (ventana.getCmbNuevaHora() == null) return;

        ventana.getCmbNuevaHora().removeAllItems();

        LocalTime horaInicio = LocalTime.of(7, 0);
        LocalTime horaFin = LocalTime.of(20, 0);

        LocalTime horaActual = horaInicio;
        while (horaActual.isBefore(horaFin)) {
            ventana.getCmbNuevaHora().addItem(horaActual.toString());
            horaActual = horaActual.plusMinutes(40);
        }
    }

    // Métodos para Cancelar Cita
    public void cargarCitasParaCancelar() {
        if (authController.getPacienteActual() == null) return;

        DefaultListModel<String> model = ventana.getModelCitasCancelar();
        model.clear();

        // Limpiar el campo de motivo cada vez que se carga el panel
        ventana.getTxtMotivoCancelacion().setText("");

        List<Cita> citas = database.obtenerCitasPorPaciente(authController.getPacienteActual().getId());

        for (Cita cita : citas) {
            if ("PROGRAMADA".equals(cita.getEstado())) {
                String infoCita = String.format("Dr. %s %s - %s | %s %s",
                        cita.getMedico().getNombre(),
                        cita.getMedico().getApellido(),
                        cita.getMedico().getEspecialidad().getNombre(),
                        cita.getFechaHora().toLocalDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                        cita.getFechaHora().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm"))); // Formato de hora mejorado
                model.addElement(infoCita);
            }
        }

        if (model.isEmpty()) {
            model.addElement("No tiene citas programadas para cancelar");
            ventana.getBtnConfirmarCancelacion().setEnabled(false);
            ventana.getTxtMotivoCancelacion().setEnabled(false);
        } else {
            ventana.getBtnConfirmarCancelacion().setEnabled(true);
            ventana.getTxtMotivoCancelacion().setEnabled(true);
        }
    }

    // Método para reasignar cita
    private void reasignarCita() {
        int selectedIndex = ventana.getLstCitasReasignar().getSelectedIndex();
        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(ventana, "Por favor seleccione una cita para reasignar");
            return;
        }

        String fechaStr = (String) ventana.getCmbNuevaFecha().getSelectedItem();
        String horaStr = (String) ventana.getCmbNuevaHora().getSelectedItem();

        if (fechaStr == null || horaStr == null) {
            JOptionPane.showMessageDialog(ventana, "Por favor seleccione nueva fecha y hora");
            return;
        }

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            LocalDateTime nuevaFechaHora = LocalDateTime.parse(fechaStr + " " + horaStr, formatter);

            // Obtener la cita seleccionada
            List<Cita> citas = database.obtenerCitasPorPaciente(authController.getPacienteActual().getId());
            Cita citaSeleccionada = citas.get(selectedIndex);

            // Verificar disponibilidad
            if (database.verificarDisponibilidadCita(authController.getPacienteActual(),
                    citaSeleccionada.getMedico(), nuevaFechaHora)) {

                database.reasignarCita(citaSeleccionada.getId(), nuevaFechaHora);
                JOptionPane.showMessageDialog(ventana, "Cita reasignada exitosamente");
                ventana.mostrarMenu();

            } else {
                JOptionPane.showMessageDialog(ventana, "No hay disponibilidad en el horario seleccionado");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(ventana, "Error al reasignar la cita: " + e.getMessage());
        }
    }

    // Método para cancelar cita
    private void cancelarCita() {
        int selectedIndex = ventana.getLstCitasCancelar().getSelectedIndex();
        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(ventana, "Por favor seleccione una cita para cancelar");
            return;
        }

        String motivo = ventana.getTxtMotivoCancelacion().getText().trim();
        if (motivo.isEmpty()) {
            JOptionPane.showMessageDialog(ventana, "Por favor ingrese el motivo de cancelación");
            return;
        }

        // Obtener la cita seleccionada
        List<Cita> citas = database.obtenerCitasPorPaciente(authController.getPacienteActual().getId());
        Cita citaSeleccionada = citas.get(selectedIndex);

        database.cancelarCita(citaSeleccionada.getId(), motivo);
        JOptionPane.showMessageDialog(ventana, "Cita cancelada exitosamente");
        ventana.mostrarMenu();
    }

    // Métodos existentes para solicitar cita
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