package Libro;

public class Libro{
    
    private String ISBN;
    private String Titulo;
    private String Autor;
    private String editorial;
    private int aniopublicacion;

    public Libro() {
    }

    public Libro(String ISBN, String Titulo, String Autor, String editorial, int aniopublicacion) {
        this.ISBN = ISBN;
        this.Titulo = Titulo;
        this.Autor = Autor;
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
        return Titulo;
    }

    public void setTitulo(String Titulo) {
        this.Titulo = Titulo;
    }

    public String getAutor() {
        return Autor;
    }

    public void setAutor(String Autor) {
        this.Autor = Autor;
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
        return "Libro{" + "ISBN=" + ISBN + ", Titulo=" + Titulo + ", Autor=" + Autor + 
                ", editorial=" + editorial + ", aniopublicacion=" + aniopublicacion + '}';
    }
    
    
    
}
