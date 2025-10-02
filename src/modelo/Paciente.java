package modelo;

import java.time.LocalDate;

public class Paciente {
    private String id;
    private String password;
    private String nombre;
    private String apellido;
    private LocalDate fechaNacimiento;
    private String barrio;
    private String telefono;

    public Paciente(String id, String password, String nombre, String apellido,
                    LocalDate fechaNacimiento, String barrio, String telefono) {
        this.id = id;
        this.password = password;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.barrio = barrio;
        this.telefono = telefono;
    }

    // Getters y Setters
    public String getId() { return id; }
    public String getPassword() { return password; }
    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public String getBarrio() { return barrio; }
    public String getTelefono() { return telefono; }
}