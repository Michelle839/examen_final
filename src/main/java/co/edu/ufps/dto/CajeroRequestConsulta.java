package co.edu.ufps.dto;

import lombok.AllArgsConstructor;
import lombok.Data;


@AllArgsConstructor
@Data
public class CajeroRequestConsulta {
    private String documento;
    private String nombre;
}
