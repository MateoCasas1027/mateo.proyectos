package modelo;

import java.io.Serializable;

public class Especialidad implements Serializable {
    private String id;
    private String nombre;

    public Especialidad(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    // Getters
    public String getId() { return id; }
    public String getNombre() { return nombre; }
}