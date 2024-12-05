package co.edu.ufps.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PagoRequest {

    @JsonProperty("tipo_pago")
    private String tipoPago;

    @JsonProperty("tipo_tarjeta")
    private String tipoTarjeta;
    
    private Integer cuotas;

    private Double valor;

   
}
