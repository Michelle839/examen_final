package co.edu.ufps.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ClienteRequest {

    private String documento;

    private String nombre;

    @JsonProperty("tipo_documento")
    private String tipoDocumento;
}
