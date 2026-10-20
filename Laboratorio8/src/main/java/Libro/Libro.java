package Libro;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class Libro{
    
    @Id
    @Column(name = "ISBN")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String ISBN;
    
    private String titulo;
    private String autor;
    private String editorial;
    private int aniopublicacion;

    public Libro() {
    }

    public Libro(String ISBN, String titulo, String autor, String editorial, int aniopublicacion) {
        this.ISBN = ISBN;
        this.titulo = titulo;
        this.autor = autor;
        this.editorial = editorial;
        this.aniopublicacion = aniopublicacion;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public int getAniopublicacion() {
        return aniopublicacion;
    }

    public void setAniopublicacion(int aniopublicacion) {
        this.aniopublicacion = aniopublicacion;
    }

    @Override
    public String toString() {
        return "Libro{" + "ISBN=" + ISBN + ", titulo=" + titulo + ", autor=" + autor + ", editorial=" + editorial + ", aniopublicacion=" + aniopublicacion + '}';
    }

    
    
    
}
