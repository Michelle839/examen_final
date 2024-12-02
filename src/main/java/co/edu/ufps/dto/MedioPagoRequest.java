package co.edu.ufps.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MedioPagoRequest {

    @JsonProperty("tipo_pago")
    private String tipoPago;

    @JsonProperty("tipo_tarjeta")
    private String tipoTarjeta;

    private double valor;

    private int cuotas;
}
