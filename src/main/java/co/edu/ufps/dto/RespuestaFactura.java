package co.edu.ufps.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RespuestaFactura {
    private String numero;
    private double total;
    private String fecha;
    
    
    
}
