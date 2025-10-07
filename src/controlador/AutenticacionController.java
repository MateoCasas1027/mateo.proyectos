package controlador;

import modelo.Paciente;
import util.DatabaseUtil;
import vista.Ventana;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AutenticacionController {
    private final Ventana ventana;
    private final DatabaseUtil database;
    private Paciente pacienteActual;
    private CitaController citaController;

    public AutenticacionController(Ventana ventana) {
        this.ventana = ventana;
        this.database = DatabaseUtil.getInstance();
        configurarEventos();
    }

    public void setCitaController(CitaController citaController) {
        this.citaController = citaController;
    }

    private void configurarEventos() {
        // Evento Login
        ventana.getBtnLogin().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });

        // Evento Registro
        ventana.getBtnRegistrarse().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ventana.mostrarRegistro();
            }
        });

        ventana.getBtnCrearCuenta().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarPaciente();
            }
        });

        ventana.getBtnVolverLogin().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ventana.mostrarLogin();
            }
        });

        // Eventos del Menú
        ventana.getBtnSolicitarCita().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ventana.mostrarSolicitarCita();
            }
        });

        ventana.getBtnMisCitas().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (pacienteActual != null) {
                    ventana.mostrarMisCitas();
                    if (citaController != null) {
                        citaController.cargarMisCitas();
                    }
                } else {
                    JOptionPane.showMessageDialog(ventana, "Debe iniciar sesión primero");
                }
            }
        });

        ventana.getBtnReasignarCita().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (pacienteActual != null) {
                    ventana.mostrarReasignarCita();
                    if (citaController != null) {
                        citaController.cargarCitasParaReasignar();
                        citaController.cargarFechasReasignacion();
                    }
                } else {
                    JOptionPane.showMessageDialog(ventana, "Debe iniciar sesión primero");
                }
            }
        });

        ventana.getBtnCancelarCita().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (pacienteActual != null) {
                    ventana.mostrarCancelarCita();  // ✅ Esto debe llamar al panel correcto
                    if (citaController != null) {
                        citaController.cargarCitasParaCancelar();
                    }
                } else {
                    JOptionPane.showMessageDialog(ventana, "Debe iniciar sesión primero");
                }
            }
        });

        ventana.getBtnCerrarSesion().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cerrarSesion();
            }
        });

        // Eventos para volver al menú desde los nuevos paneles
        ventana.getBtnVolverMenuMisCitas().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ventana.mostrarMenu();
            }
        });

        ventana.getBtnVolverMenuReasignar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ventana.mostrarMenu();
            }
        });

        ventana.getBtnVolverMenuCancelar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ventana.mostrarMenu();
            }
        });
    }

    private void login() {
        String id = ventana.getTxtIdLogin().getText();
        String password = new String(ventana.getTxtPasswordLogin().getPassword());

        if (id.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(ventana, "Por favor complete todos los campos");
            return;
        }

        Paciente paciente = database.autenticarPaciente(id, password);
        if (paciente != null) {
            pacienteActual = paciente;
            JOptionPane.showMessageDialog(ventana, "Bienvenido " + paciente.getNombre());
            ventana.mostrarMenu();
            limpiarCamposLogin();
        } else {
            JOptionPane.showMessageDialog(ventana, "ID o contraseña incorrectos");
        }
    }

    private void registrarPaciente() {
        try {
            String nombre = ventana.getTxtNombre().getText();
            String apellido = ventana.getTxtApellido().getText();
            String id = ventana.getTxtIdRegistro().getText();
            String password = new String(ventana.getTxtPasswordRegistro().getPassword());
            String fechaNacStr = ventana.getTxtFechaNacimiento().getText();
            String barrio = ventana.getTxtBarrio().getText();
            String telefono = ventana.getTxtTelefono().getText();

            if (nombre.isEmpty() || apellido.isEmpty() || id.isEmpty() || password.isEmpty() ||
                    fechaNacStr.isEmpty() || barrio.isEmpty() || telefono.isEmpty()) {
                JOptionPane.showMessageDialog(ventana, "Por favor complete todos los campos");
                return;
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate fechaNacimiento = LocalDate.parse(fechaNacStr, formatter);

            Paciente nuevoPaciente = new Paciente(id, password, nombre, apellido,
                    fechaNacimiento, barrio, telefono);

            if (database.registrarPaciente(nuevoPaciente)) {
                JOptionPane.showMessageDialog(ventana, "Cuenta creada exitosamente");
                ventana.mostrarLogin();
                limpiarCamposRegistro();
            } else {
                JOptionPane.showMessageDialog(ventana, "Error: El ID ya existe");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(ventana, "Error en el formato de fecha. Use dd/mm/aaaa");
        }
    }

    private void cerrarSesion() {
        pacienteActual = null;
        ventana.mostrarLogin();
    }

    private void limpiarCamposLogin() {
        ventana.getTxtIdLogin().setText("");
        ventana.getTxtPasswordLogin().setText("");
    }

    private void limpiarCamposRegistro() {
        ventana.getTxtNombre().setText("");
        ventana.getTxtApellido().setText("");
        ventana.getTxtIdRegistro().setText("");
        ventana.getTxtPasswordRegistro().setText("");
        ventana.getTxtFechaNacimiento().setText("");
        ventana.getTxtBarrio().setText("");
        ventana.getTxtTelefono().setText("");
    }

    public Paciente getPacienteActual() {
        return pacienteActual;
    }
}