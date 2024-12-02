package co.edu.ufps.dto;

import lombok.Data;

@Data
public class MedioPagoRequest {
    private String tipoPago;
    private String tipoTarjeta; // Puede ser null si no aplica
    private double valor;
    private Integer cuotas; // Puede ser null si no aplica
}
