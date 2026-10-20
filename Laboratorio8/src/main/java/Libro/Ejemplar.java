package Libro;


public class Ejemplar {
    
    private String ISBN;
    
    private int nro_ejemplar;
    private Estado estado;

    public Ejemplar() {
    }

    public Ejemplar(String ISBN, int nro_ejemplar, Estado estado) {
        this.ISBN = ISBN;
        this.nro_ejemplar = nro_ejemplar;
        this.estado = estado;
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

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Ejemplar{" + "ISBN=" + ISBN + ", nro_ejemplar=" + nro_ejemplar + ", estado=" + estado + '}';
    }
    
    
}
