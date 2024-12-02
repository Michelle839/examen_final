package co.edu.ufps.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@Entity
@Table(name = "tipo_pago")
public class TipoPago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="nombre",nullable = false, length = 200)
    private String nombre;

    @OneToMany(mappedBy = "tipoPago", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Pago> pagos;
}
