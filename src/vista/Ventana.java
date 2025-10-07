package vista;

import javax.swing.*;
import java.awt.*;

public class Ventana extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;

    // Paneles
    private JPanel loginPanel;
    private JPanel registroPanel;
    private JPanel menuPanel;
    private JPanel solicitarCitaPanel;
    private JPanel misCitasPanel;
    private JPanel reasignarCitaPanel;
    private JPanel cancelarCitaPanel;

    // Componentes Login
    private JTextField txtIdLogin;
    private JPasswordField txtPasswordLogin;
    private JButton btnLogin;
    private JButton btnRegistrarse;

    // Componentes Registro
    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtIdRegistro;
    private JPasswordField txtPasswordRegistro;
    private JTextField txtFechaNacimiento;
    private JTextField txtBarrio;
    private JTextField txtTelefono;
    private JButton btnCrearCuenta;
    private JButton btnVolverLogin;

    // Componentes Menu
    private JButton btnSolicitarCita;
    private JButton btnMisCitas;
    private JButton btnReasignarCita;
    private JButton btnCancelarCita;
    private JButton btnCerrarSesion;

    // Componentes Solicitar Cita
    private JComboBox<String> cmbEspecialidad;
    private JComboBox<String> cmbMedico;
    private JComboBox<String> cmbFecha;
    private JComboBox<String> cmbHora;
    private JButton btnConfirmarCita;
    private JButton btnVolverMenu;

    // Componentes Mis Citas
    private JList<String> lstMisCitas;
    private DefaultListModel<String> modelMisCitas;
    private JButton btnVolverMenuMisCitas;

    // Componentes Reasignar Cita
    private JList<String> lstCitasReasignar;
    private DefaultListModel<String> modelCitasReasignar;
    private JComboBox<String> cmbNuevaFecha;
    private JComboBox<String> cmbNuevaHora;
    private JButton btnConfirmarReasignacion;
    private JButton btnVolverMenuReasignar;

    // Componentes Cancelar Cita
    private JList<String> lstCitasCancelar;
    private DefaultListModel<String> modelCitasCancelar;
    private JTextArea txtMotivoCancelacion;
    private JButton btnConfirmarCancelacion;
    private JButton btnVolverMenuCancelar;

    public Ventana() {
        inicializarComponentes();
        configurarVentana();
    }

    private void inicializarComponentes() {
        setTitle("Sistema de Citas Médicas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        crearLoginPanel();
        crearRegistroPanel();
        crearMenuPanel();
        crearSolicitarCitaPanel();
        crearMisCitasPanel();
        crearReasignarCitaPanel();
        crearCancelarCitaPanel();

        mainPanel.add(loginPanel, "LOGIN");
        mainPanel.add(registroPanel, "REGISTRO");
        mainPanel.add(menuPanel, "MENU");
        mainPanel.add(solicitarCitaPanel, "SOLICITAR_CITA");
        mainPanel.add(misCitasPanel, "MIS_CITAS");
        mainPanel.add(reasignarCitaPanel, "REASIGNAR_CITA");
        mainPanel.add(cancelarCitaPanel, "CANCELAR_CITA");

        add(mainPanel);
    }

    private void configurarVentana() {
        setResizable(false);
    }

    private void crearLoginPanel() {
        loginPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel lblTitulo = new JLabel("INICIAR SESIÓN");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));

        JLabel lblId = new JLabel("Número de ID:");
        txtIdLogin = new JTextField(15);

        JLabel lblPassword = new JLabel("Contraseña:");
        txtPasswordLogin = new JPasswordField(15);

        btnLogin = new JButton("Iniciar Sesión");
        btnRegistrarse = new JButton("Crear Cuenta");

        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        loginPanel.add(lblTitulo, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = 1;
        loginPanel.add(lblId, gbc);

        gbc.gridx = 1;
        loginPanel.add(txtIdLogin, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        loginPanel.add(lblPassword, gbc);

        gbc.gridx = 1;
        loginPanel.add(txtPasswordLogin, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        JPanel botonesPanel = new JPanel();
        botonesPanel.add(btnLogin);
        botonesPanel.add(btnRegistrarse);
        loginPanel.add(botonesPanel, gbc);
    }

    private void crearRegistroPanel() {
        registroPanel = new JPanel(new GridLayout(8, 2, 10, 10));
        registroPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        registroPanel.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        registroPanel.add(txtNombre);

        registroPanel.add(new JLabel("Apellido:"));
        txtApellido = new JTextField();
        registroPanel.add(txtApellido);

        registroPanel.add(new JLabel("Número de ID:"));
        txtIdRegistro = new JTextField();
        registroPanel.add(txtIdRegistro);

        registroPanel.add(new JLabel("Contraseña:"));
        txtPasswordRegistro = new JPasswordField();
        registroPanel.add(txtPasswordRegistro);

        registroPanel.add(new JLabel("Fecha Nacimiento (dd/mm/aaaa):"));
        txtFechaNacimiento = new JTextField();
        registroPanel.add(txtFechaNacimiento);

        registroPanel.add(new JLabel("Barrio:"));
        txtBarrio = new JTextField();
        registroPanel.add(txtBarrio);

        registroPanel.add(new JLabel("Teléfono:"));
        txtTelefono = new JTextField();
        registroPanel.add(txtTelefono);

        btnCrearCuenta = new JButton("Crear Cuenta");
        btnVolverLogin = new JButton("Volver al Login");

        registroPanel.add(btnCrearCuenta);
        registroPanel.add(btnVolverLogin);
    }

    private void crearMenuPanel() {
        menuPanel = new JPanel(new GridLayout(5, 1, 10, 10));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        btnSolicitarCita = new JButton("Solicitar Cita");
        btnMisCitas = new JButton("Mis Citas");
        btnReasignarCita = new JButton("Reasignar Cita");
        btnCancelarCita = new JButton("Cancelar Cita");
        btnCerrarSesion = new JButton("Cerrar Sesión");

        Font buttonFont = new Font("Arial", Font.BOLD, 16);
        btnSolicitarCita.setFont(buttonFont);
        btnMisCitas.setFont(buttonFont);
        btnReasignarCita.setFont(buttonFont);
        btnCancelarCita.setFont(buttonFont);
        btnCerrarSesion.setFont(buttonFont);

        menuPanel.add(btnSolicitarCita);
        menuPanel.add(btnMisCitas);
        menuPanel.add(btnReasignarCita);
        menuPanel.add(btnCancelarCita);
        menuPanel.add(btnCerrarSesion);
    }

    private void crearSolicitarCitaPanel() {
        solicitarCitaPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblTitulo = new JLabel("SOLICITAR CITA MÉDICA");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));

        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        solicitarCitaPanel.add(lblTitulo, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = 1;
        solicitarCitaPanel.add(new JLabel("Especialidad:"), gbc);

        gbc.gridx = 1;
        cmbEspecialidad = new JComboBox<>(new String[]{
                "Cardiología", "Dermatología", "Pediatría", "Ortopedia", "Neurología"
        });
        solicitarCitaPanel.add(cmbEspecialidad, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        solicitarCitaPanel.add(new JLabel("Médico:"), gbc);

        gbc.gridx = 1;
        cmbMedico = new JComboBox<>();
        solicitarCitaPanel.add(cmbMedico, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        solicitarCitaPanel.add(new JLabel("Fecha:"), gbc);

        gbc.gridx = 1;
        cmbFecha = new JComboBox<>();
        solicitarCitaPanel.add(cmbFecha, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        solicitarCitaPanel.add(new JLabel("Hora:"), gbc);

        gbc.gridx = 1;
        cmbHora = new JComboBox<>();
        solicitarCitaPanel.add(cmbHora, gbc);

        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        JPanel botonesPanel = new JPanel();
        btnConfirmarCita = new JButton("Confirmar Cita");
        btnVolverMenu = new JButton("Volver al Menú");
        botonesPanel.add(btnConfirmarCita);
        botonesPanel.add(btnVolverMenu);
        solicitarCitaPanel.add(botonesPanel, gbc);
    }

    private void crearMisCitasPanel() {
        misCitasPanel = new JPanel(new BorderLayout());
        misCitasPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblTitulo = new JLabel("MIS CITAS PROGRAMADAS");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);

        modelMisCitas = new DefaultListModel<>();
        lstMisCitas = new JList<>(modelMisCitas);
        JScrollPane scrollPane = new JScrollPane(lstMisCitas);

        btnVolverMenuMisCitas = new JButton("Volver al Menú");

        misCitasPanel.add(lblTitulo, BorderLayout.NORTH);
        misCitasPanel.add(scrollPane, BorderLayout.CENTER);
        misCitasPanel.add(btnVolverMenuMisCitas, BorderLayout.SOUTH);
    }

    private void crearReasignarCitaPanel() {
        reasignarCitaPanel = new JPanel(new BorderLayout());
        reasignarCitaPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblTitulo = new JLabel("REASIGNAR CITA");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);

        // Panel superior con lista de citas
        JPanel panelSuperior = new JPanel(new BorderLayout());
        modelCitasReasignar = new DefaultListModel<>();
        lstCitasReasignar = new JList<>(modelCitasReasignar);
        JScrollPane scrollPane = new JScrollPane(lstCitasReasignar);
        panelSuperior.add(new JLabel("Seleccione la cita a reasignar:"), BorderLayout.NORTH);
        panelSuperior.add(scrollPane, BorderLayout.CENTER);

        // Panel central con nueva fecha/hora
        JPanel panelCentral = new JPanel(new GridLayout(2, 2, 10, 10));
        panelCentral.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        panelCentral.add(new JLabel("Nueva Fecha:"));
        cmbNuevaFecha = new JComboBox<>();
        panelCentral.add(cmbNuevaFecha);

        panelCentral.add(new JLabel("Nueva Hora:"));
        cmbNuevaHora = new JComboBox<>();
        panelCentral.add(cmbNuevaHora);

        // Panel inferior con botones
        JPanel panelInferior = new JPanel();
        btnConfirmarReasignacion = new JButton("Confirmar Reasignación");
        btnVolverMenuReasignar = new JButton("Volver al Menú");
        panelInferior.add(btnConfirmarReasignacion);
        panelInferior.add(btnVolverMenuReasignar);

        reasignarCitaPanel.add(lblTitulo, BorderLayout.NORTH);
        reasignarCitaPanel.add(panelSuperior, BorderLayout.CENTER);
        reasignarCitaPanel.add(panelCentral, BorderLayout.SOUTH);
        reasignarCitaPanel.add(panelInferior, BorderLayout.PAGE_END);
    }

    private void crearCancelarCitaPanel() {
        cancelarCitaPanel = new JPanel(new BorderLayout(10, 10));
        cancelarCitaPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblTitulo = new JLabel("CANCELAR CITA");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);

        // Panel superior con lista de citas
        JPanel panelSuperior = new JPanel(new BorderLayout(10, 10));
        modelCitasCancelar = new DefaultListModel<>();
        lstCitasCancelar = new JList<>(modelCitasCancelar);
        JScrollPane scrollPane = new JScrollPane(lstCitasCancelar);
        scrollPane.setPreferredSize(new Dimension(700, 150));
        panelSuperior.add(new JLabel("Seleccione la cita a cancelar:"), BorderLayout.NORTH);
        panelSuperior.add(scrollPane, BorderLayout.CENTER);

        // Panel central con motivo - MÁS VISIBLE
        JPanel panelCentral = new JPanel(new BorderLayout(10, 10));
        panelCentral.setBorder(BorderFactory.createTitledBorder("Motivo de Cancelación"));

        JLabel lblMotivo = new JLabel("Por favor ingrese el motivo de cancelación:");
        lblMotivo.setFont(new Font("Arial", Font.BOLD, 12));

        txtMotivoCancelacion = new JTextArea(5, 50); // ✅ Más grande y visible
        txtMotivoCancelacion.setLineWrap(true);
        txtMotivoCancelacion.setWrapStyleWord(true);
        txtMotivoCancelacion.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        JScrollPane motivoScrollPane = new JScrollPane(txtMotivoCancelacion);
        motivoScrollPane.setPreferredSize(new Dimension(700, 100));

        panelCentral.add(lblMotivo, BorderLayout.NORTH);
        panelCentral.add(motivoScrollPane, BorderLayout.CENTER);

        // Panel inferior con botones
        JPanel panelInferior = new JPanel(new FlowLayout());
        btnConfirmarCancelacion = new JButton("Confirmar Cancelación");
        btnVolverMenuCancelar = new JButton("Volver al Menú");

        // Estilos para los botones
        btnConfirmarCancelacion.setBackground(new Color(220, 80, 80));
        btnConfirmarCancelacion.setForeground(Color.WHITE);
        btnConfirmarCancelacion.setFont(new Font("Arial", Font.BOLD, 14));

        panelInferior.add(btnConfirmarCancelacion);
        panelInferior.add(btnVolverMenuCancelar);

        // Organizar en el panel principal
        JPanel contenidoPanel = new JPanel(new BorderLayout(15, 15));
        contenidoPanel.add(panelSuperior, BorderLayout.NORTH);
        contenidoPanel.add(panelCentral, BorderLayout.CENTER);
        contenidoPanel.add(panelInferior, BorderLayout.SOUTH);

        cancelarCitaPanel.add(lblTitulo, BorderLayout.NORTH);
        cancelarCitaPanel.add(contenidoPanel, BorderLayout.CENTER);
    }

    // Métodos para cambiar entre paneles
    public void mostrarLogin() {
        cardLayout.show(mainPanel, "LOGIN");
    }

    public void mostrarRegistro() {
        cardLayout.show(mainPanel, "REGISTRO");
    }

    public void mostrarMenu() {
        cardLayout.show(mainPanel, "MENU");
    }

    public void mostrarSolicitarCita() {
        cardLayout.show(mainPanel, "SOLICITAR_CITA");
    }

    public void mostrarMisCitas() {
        cardLayout.show(mainPanel, "MIS_CITAS");
    }

    public void mostrarReasignarCita() {
        cardLayout.show(mainPanel, "REASIGNAR_CITA");
    }

    public void mostrarCancelarCita() {
        cardLayout.show(mainPanel, "CANCELAR_CITA");
    }

    // Getters para los componentes
    public JTextField getTxtIdLogin() { return txtIdLogin; }
    public JPasswordField getTxtPasswordLogin() { return txtPasswordLogin; }
    public JButton getBtnLogin() { return btnLogin; }
    public JButton getBtnRegistrarse() { return btnRegistrarse; }
    public JTextField getTxtNombre() { return txtNombre; }
    public JTextField getTxtApellido() { return txtApellido; }
    public JTextField getTxtIdRegistro() { return txtIdRegistro; }
    public JPasswordField getTxtPasswordRegistro() { return txtPasswordRegistro; }
    public JTextField getTxtFechaNacimiento() { return txtFechaNacimiento; }
    public JTextField getTxtBarrio() { return txtBarrio; }
    public JTextField getTxtTelefono() { return txtTelefono; }
    public JButton getBtnCrearCuenta() { return btnCrearCuenta; }
    public JButton getBtnVolverLogin() { return btnVolverLogin; }
    public JButton getBtnSolicitarCita() { return btnSolicitarCita; }
    public JButton getBtnMisCitas() { return btnMisCitas; }
    public JButton getBtnReasignarCita() { return btnReasignarCita; }
    public JButton getBtnCancelarCita() { return btnCancelarCita; }
    public JButton getBtnCerrarSesion() { return btnCerrarSesion; }
    public JComboBox<String> getCmbEspecialidad() { return cmbEspecialidad; }
    public JComboBox<String> getCmbMedico() { return cmbMedico; }
    public JComboBox<String> getCmbFecha() { return cmbFecha; }
    public JComboBox<String> getCmbHora() { return cmbHora; }
    public JButton getBtnConfirmarCita() { return btnConfirmarCita; }
    public JButton getBtnVolverMenu() { return btnVolverMenu; }

    // Nuevos getters
    public JList<String> getLstMisCitas() { return lstMisCitas; }
    public DefaultListModel<String> getModelMisCitas() { return modelMisCitas; }
    public JButton getBtnVolverMenuMisCitas() { return btnVolverMenuMisCitas; }

    public JList<String> getLstCitasReasignar() { return lstCitasReasignar; }
    public DefaultListModel<String> getModelCitasReasignar() { return modelCitasReasignar; }
    public JComboBox<String> getCmbNuevaFecha() { return cmbNuevaFecha; }
    public JComboBox<String> getCmbNuevaHora() { return cmbNuevaHora; }
    public JButton getBtnConfirmarReasignacion() { return btnConfirmarReasignacion; }
    public JButton getBtnVolverMenuReasignar() { return btnVolverMenuReasignar; }

    public JList<String> getLstCitasCancelar() { return lstCitasCancelar; }
    public DefaultListModel<String> getModelCitasCancelar() { return modelCitasCancelar; }
    public JTextArea getTxtMotivoCancelacion() { return txtMotivoCancelacion; }
    public JButton getBtnConfirmarCancelacion() { return btnConfirmarCancelacion; }
    public JButton getBtnVolverMenuCancelar() { return btnVolverMenuCancelar; }
}