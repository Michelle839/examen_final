package co.edu.ufps.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@Entity
@Table(name = "cajero")
public class Cajero {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="nombre",nullable = false, length = 200)
    private String nombre;

    @Column(name="documento",length = 20)
    private String documento;

    @Column(name="email",length = 50)
    private String email;

    @Column(name="token",length = 100)
    private String token;

    @ManyToOne
    @JoinColumn(name = "tienda_id", nullable = false)
    private Tienda tienda;


    @OneToMany(mappedBy = "cajero", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Compra> compras;
}
