package co.edu.ufps.dto;

import lombok.Data;

@Data
public class ClienteRequest {
    private String documento;
    private String nombre;
    private String tipoDocumento;
}
