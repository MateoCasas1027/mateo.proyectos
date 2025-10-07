package modelo;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Cita implements Serializable {
    private String id;
    private Paciente paciente;
    private Medico medico;
    private LocalDateTime fechaHora;
    private String estado; // PROGRAMADA, CONFIRMADA, CANCELADA, COMPLETADA
    private String motivoCancelacion;

    public Cita(String id, Paciente paciente, Medico medico, LocalDateTime fechaHora) {
        this.id = id;
        this.paciente = paciente;
        this.medico = medico;
        this.fechaHora = fechaHora;
        this.estado = "PROGRAMADA";
    }

    // Getters y Setters
    public String getId() { return id; }
    public Paciente getPaciente() { return paciente; }
    public Medico getMedico() { return medico; }
    public LocalDateTime getFechaHora() { return fechaHora; }
    public String getEstado() { return estado; }
    public String getMotivoCancelacion() { return motivoCancelacion; }

    public void cancelar(String motivo) {
        this.estado = "CANCELADA";
        this.motivoCancelacion = motivo;
    }

    public void reasignar(LocalDateTime nuevaFechaHora) {
        this.fechaHora = nuevaFechaHora;
        this.estado = "REASIGNADA";
    }
}