package Clases;

import java.sql.Date;

public class Prestamo {

    private int     id_prestamo;
    private int     id_socio;
    private String  ISBN;
    private int     nro_ejemplar;
    private Date    fecha_prestamo;
    private Date    fecha_prevista;
    private Date    fecha_real;

    public Prestamo() {
    }

    public Prestamo(int id_prestamo, int id_socio, String ISBN, int nro_ejemplar, Date fecha_prestamo, Date fecha_prevista, Date fecha_real) {
        this.id_prestamo = id_prestamo;
        this.id_socio = id_socio;
        this.ISBN = ISBN;
        this.nro_ejemplar = nro_ejemplar;
        this.fecha_prestamo = fecha_prestamo;
        this.fecha_prevista = fecha_prevista;
        this.fecha_real = fecha_real;
    }

    public int getId_prestamo() {
        return id_prestamo;
    }

    public void setId_prestamo(int id_prestamo) {
        this.id_prestamo = id_prestamo;
    }

    public int getId_socio() {
        return id_socio;
    }

    public void setId_socio(int id_socio) {
        this.id_socio = id_socio;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public int getNro_ejemplar() {
        return nro_ejemplar;
    }

    public void setNro_ejemplar(int nro_ejemplar) {
        this.nro_ejemplar = nro_ejemplar;
    }

    public Date getFecha_prestamo() {
        return fecha_prestamo;
    }

    public void setFecha_prestamo(Date fecha_prestamo) {
        this.fecha_prestamo = fecha_prestamo;
    }

    public Date getFecha_prevista() {
        return fecha_prevista;
    }

    public void setFecha_prevista(Date fecha_prevista) {
        this.fecha_prevista = fecha_prevista;
    }

    public Date getFecha_real() {
        return fecha_real;
    }

    public void setFecha_real(Date fecha_real) {
        this.fecha_real = fecha_real;
    }

    @Override
    public String toString() {
        return "Prestamo{" + "id_prestamo=" + id_prestamo + ", id_socio=" + id_socio + ", ISBN=" + ISBN + ", nro_ejemplar=" + nro_ejemplar + ", fecha_prestamo=" + fecha_prestamo + ", fecha_prevista=" + fecha_prevista + ", fecha_real=" + fecha_real + '}';
    }
}
