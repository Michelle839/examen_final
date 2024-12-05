package co.edu.ufps.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RespuestaFactura {
    private String numero;
    private String total;
    private String fecha;
       
}
