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

        mainPanel.add(loginPanel, "LOGIN");
        mainPanel.add(registroPanel, "REGISTRO");
        mainPanel.add(menuPanel, "MENU");
        mainPanel.add(solicitarCitaPanel, "SOLICITAR_CITA");

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
}