package modelo;

public class Medico {
    private String id;
    private String nombre;
    private String apellido;
    private Especialidad especialidad;

    public Medico(String id, String nombre, String apellido, Especialidad especialidad) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.especialidad = especialidad;
    }

    // Getters
    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
    public Especialidad getEspecialidad() { return especialidad; }

    public String getNombreCompleto() {
        return "Dr. " + nombre + " " + apellido + " - " + especialidad.getNombre();
    }
}