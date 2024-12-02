package co.edu.ufps.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@Entity
@Table(name = "compra")
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id; 

    @Column(name="total",precision = 10, scale = 2)
    private Double total;

    @Column(name="impuestos",precision = 5, scale = 2)
    private Double impuestos;

    @Column(name="fecha",nullable = false)
    private LocalDateTime fecha;


    @Column(name="observaciones",length = 1000)
    private String observaciones;
    
    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "tienda_id", nullable = false)
    private Tienda tienda;

    @ManyToOne
    @JoinColumn(name = "vendedor_id", nullable = false)
    private Vendedor vendedor;

    @ManyToOne
    @JoinColumn(name = "cajero_id", nullable = false)
    private Cajero cajero;

    @OneToMany(mappedBy = "compra", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<DetallesCompra> detallesCompra;

    @OneToMany(mappedBy = "compra", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Pago> pagos;
}
