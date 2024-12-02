package co.edu.ufps.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@Entity
@Table(name = "producto")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="nombre",nullable = false, length = 200)
    private String nombre;

    @Column(name="descripcion",length = 1000)
    private String descripcion;

    @Column(name="precio",nullable = false, precision = 10, scale = 2)
    private Double precio;

    @Column(name="cantidad",nullable = false, columnDefinition = "int default 0")
    private Integer cantidad;

    @Column(name="referencia",length = 20)
    private String referencia;
    
    @ManyToOne
    @JoinColumn(name = "tipo_producto_id", nullable = false)
    private TipoProducto tipoProducto;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<DetallesCompra> detallesCompra;
}
