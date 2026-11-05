package Clases;

import java.sql.Date;

public class Socio {

    private int     id_socio;
    private String  nombre;
    private String  dni;
    private String  telefono;
    private Date    fecha_alta;

    public Socio() {
    }

    public Socio(int id_socio, String nombre, String dni, String telefono, Date fecha_alta) {
        this.id_socio = id_socio;
        this.nombre = nombre;
        this.dni = dni;
        this.telefono = telefono;
        this.fecha_alta = fecha_alta;
    }

    public int getId_socio() {
        return id_socio;
    }

    public void setId_socio(int id_socio) {
        this.id_socio = id_socio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Date getFecha_alta() {
        return fecha_alta;
    }

    public void setFecha_alta(Date fecha_alta) {
        this.fecha_alta = fecha_alta;
    }

    @Override
    public String toString() {
        return "Socio{" + "id_socio=" + id_socio + ", nombre=" + nombre + ", dni=" + dni + ", telefono=" + telefono + ", fecha_alta=" + fecha_alta + '}';
    }

}
