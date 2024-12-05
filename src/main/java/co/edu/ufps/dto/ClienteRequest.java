package co.edu.ufps.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;


@AllArgsConstructor
@Data
public class ClienteRequest {

    private String documento;

    private String nombre;

    @JsonProperty("tipo_documento")
    private String tipoDocumento;
}
