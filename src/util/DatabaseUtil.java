package util;

import java.io.*; // <--- AGREGADO: Importante para Serialización
import modelo.*;
import java.util.*;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

public class DatabaseUtil {
    private static DatabaseUtil instance;
    private Map<String, Paciente> pacientes;
    private List<Medico> medicos;
    private List<Cita> citas;
    private List<Especialidad> especialidades;
    private static final String DATA_FILE = "app_data.ser"; // Archivo para guardar los datos

    private DatabaseUtil() {
        // 1. Inicializa datos estáticos (Médicos y Especialidades) SIEMPRE
        inicializarDataEstatica();

        // 2. Intenta cargar datos del disco. Si falla, usa la data dinámica de demo.
        if (!cargarDatos()) {
            inicializarDataDinamicaDemo();
        }
    }

    public static DatabaseUtil getInstance() {
        if (instance == null) {
            instance = new DatabaseUtil();
        }
        return instance;
    }

    // =========================================================================
    //  MÉTODOS DE PERSISTENCIA (NUEVOS)
    // =========================================================================

    /**
     * Carga las colecciones Pacientes y Citas del archivo app_data.ser.
     */
    private boolean cargarDatos() {
        File dataFile = new File(DATA_FILE);
        if (!dataFile.exists()) {
            return false;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            // Leer los objetos en el ORDEN en que fueron guardados
            pacientes = (Map<String, Paciente>) ois.readObject();
            citas = (List<Cita>) ois.readObject();

            System.out.println("DEBUG: Datos cargados exitosamente. Pacientes: " + pacientes.size() + ", Citas: " + citas.size());
            return true;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("ERROR al cargar datos. Se reiniciarán: " + e.getMessage());
            dataFile.delete();
            return false;
        }
    }

    /**
     * Guarda el estado actual de las colecciones dinámicas (Pacientes y Citas) al disco.
     */
    public void guardarDatos() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            // Escribir los objetos en el disco
            oos.writeObject(pacientes);
            oos.writeObject(citas);
            System.out.println("DEBUG: Datos guardados exitosamente.");
        } catch (IOException e) {
            System.err.println("ERROR: No se pudo guardar la persistencia: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // =========================================================================
    //  MÉTODOS DE INICIALIZACIÓN (REFATORIZADOS)
    // =========================================================================

    /**
     * Inicializa los datos estáticos (Médicos y Especialidades).
     */
    private void inicializarDataEstatica() {
        // Inicializar especialidades
        especialidades = Arrays.asList(
                new Especialidad("ESP001", "Cardiología"),
                new Especialidad("ESP002", "Dermatología"),
                new Especialidad("ESP003", "Pediatría"),
                new Especialidad("ESP004", "Ortopedia"),
                new Especialidad("ESP005", "Neurología")
        );

        // Inicializar médicos
        medicos = Arrays.asList(
                new Medico("MED001", "Carlos", "Gómez", especialidades.get(0)),
                new Medico("MED002", "Ana", "Martínez", especialidades.get(1)),
                new Medico("MED003", "Luis", "Rodríguez", especialidades.get(2)),
                new Medico("MED004", "María", "López", especialidades.get(3)),
                new Medico("MED005", "Pedro", "Hernández", especialidades.get(4))
        );
    }

    /**
     * Inicializa las colecciones dinámicas y añade data de demo.
     */
    private void inicializarDataDinamicaDemo() {
        pacientes = new HashMap<>();
        citas = new ArrayList<>();

        // Paciente de prueba para el primer inicio (ID: 111, Pass: 111)
        try {
            pacientes.put("111", new Paciente("111", "111", "Juan", "Perez", java.time.LocalDate.of(1990, 5, 15), "Demo", "555-1000"));
        } catch (Exception e) {
            // Ignorar
        }
    }

    // El método 'inicializarDatos()' original queda como stub para compatibilidad.
    private void inicializarDatos() {
        // Lógica de inicialización movida a inicializarDataEstatica() y inicializarDataDinamicaDemo()
    }


    // =========================================================================
    //  MÉTODOS DE LÓGICA DE NEGOCIO (CON LLAMADAS A guardarDatos())
    // =========================================================================

    public Paciente autenticarPaciente(String id, String password) {
        Paciente paciente = pacientes.get(id);
        if (paciente != null && paciente.getPassword().equals(password)) {
            return paciente;
        }
        return null;
    }

    public boolean registrarPaciente(Paciente paciente) {
        if (pacientes.containsKey(paciente.getId())) {
            return false;
        }
        pacientes.put(paciente.getId(), paciente);
        guardarDatos(); // <--- PERSISTENCIA: Guardar al registrar
        return true;
    }

    public List<Medico> obtenerMedicosPorEspecialidad(String especialidadNombre) {
        return medicos.stream()
                .filter(medico -> medico.getEspecialidad().getNombre().equals(especialidadNombre))
                .collect(Collectors.toList());
    }

    public Medico obtenerMedicoPorNombre(String nombreCompleto) {
        return medicos.stream()
                .filter(medico -> medico.getNombreCompleto().equals(nombreCompleto))
                .findFirst()
                .orElse(null);
    }

    public boolean verificarDisponibilidadCita(Paciente paciente, Medico medico, LocalDateTime fechaHora) {
        for (Cita cita : citas) {
            if (cita.getPaciente().getId().equals(paciente.getId()) &&
                    cita.getMedico().getEspecialidad().equals(medico.getEspecialidad()) &&
                    cita.getFechaHora().equals(fechaHora) &&
                    !cita.getEstado().equals("CANCELADA")) {
                return false;
            }
        }
        return true;
    }

    public boolean agendarCita(Cita cita) {
        citas.add(cita);
        guardarDatos(); // <--- PERSISTENCIA: Guardar al agendar
        return true;
    }

    public List<Cita> obtenerCitasPorPaciente(String pacienteId) {
        return citas.stream()
                .filter(cita -> cita.getPaciente().getId().equals(pacienteId))
                .collect(Collectors.toList());
    }

    public boolean reasignarCita(String citaId, LocalDateTime nuevaFechaHora) {
        for (Cita cita : citas) {
            if (cita.getId().equals(citaId)) {
                cita.reasignar(nuevaFechaHora);
                guardarDatos(); // <--- PERSISTENCIA: Guardar al reasignar
                return true;
            }
        }
        return false;
    }

    public boolean cancelarCita(String citaId, String motivo) {
        for (Cita cita : citas) {
            if (cita.getId().equals(citaId)) {
                cita.cancelar(motivo);
                guardarDatos(); // <--- PERSISTENCIA: Guardar al cancelar
                return true;
            }
        }
        return false;
    }

    public List<Especialidad> getEspecialidades() {
        return especialidades;
    }

    public List<Medico> getMedicos() {
        return medicos;
    }
}