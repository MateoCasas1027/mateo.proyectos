package util;

import modelo.*;
import java.util.*;

public class DatabaseUtil {
    private static DatabaseUtil instance;
    private Map<String, Paciente> pacientes;
    private List<Medico> medicos;
    private List<Cita> citas;
    private List<Especialidad> especialidades;

    private DatabaseUtil() {
        inicializarDatos();
    }

    public static DatabaseUtil getInstance() {
        if (instance == null) {
            instance = new DatabaseUtil();
        }
        return instance;
    }

    private void inicializarDatos() {
        pacientes = new HashMap<>();
        citas = new ArrayList<>();

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
        return true;
    }

    public List<Medico> obtenerMedicosPorEspecialidad(String especialidadNombre) {
        List<Medico> resultado = new ArrayList<>();
        for (Medico medico : medicos) {
            if (medico.getEspecialidad().getNombre().equals(especialidadNombre)) {
                resultado.add(medico);
            }
        }
        return resultado;
    }

    public Medico obtenerMedicoPorNombre(String nombreCompleto) {
        for (Medico medico : medicos) {
            if (medico.getNombreCompleto().equals(nombreCompleto)) {
                return medico;
            }
        }
        return null;
    }

    public boolean verificarDisponibilidadCita(Paciente paciente, Medico medico, java.time.LocalDateTime fechaHora) {
        // Verificar que el paciente no tenga otra cita en la misma especialidad a la misma hora
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
        return true;
    }
}