package co.edu.ufps.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RespuestaBaseConsulta {
	
	private String token;
    private String cliente;
    private Integer factura;

}
