package co.edu.ufps.dto;

import lombok.Data;

@Data
public class ProductoRequest {
    private String referencia;
    private Integer cantidad;
    private Double descuento;
}
