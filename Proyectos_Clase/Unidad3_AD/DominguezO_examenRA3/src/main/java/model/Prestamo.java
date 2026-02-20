package model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "prestamos", schema = "biblioteca_db", indexes = {
        @Index(name = "idUsuario", columnList = "idUsuario"),
        @Index(name = "isbn", columnList = "isbn")
})
public class Prestamo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPrestamo", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idUsuario", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "isbn", nullable = false)
    private Libro libro;

    @Column(name = "fechaPrestamo", nullable = false)
    private LocalDate fechaPrestamo;

    @Column(name = "fechaDevolucion")
    private LocalDate fechaDevolucion;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    public LocalDate getFechaPrestamo() {
        return fechaPrestamo;
    }

    public void setFechaPrestamo(LocalDate fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    public LocalDate getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(LocalDate fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    @Override
    public String toString() {
        return "idPrestamo=" + id +
                " usuario=" + usuario.getEmail() +
                " libro=" + libro +
                " fechaPrestamo=" + fechaPrestamo +
                " devolucion=" + fechaDevolucion +
                '}';
    }
}