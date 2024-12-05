package co.edu.ufps.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FacturaConsultaRespuesta {

	
	private double total;
    private double impuestos;
    private ClienteRequest cliente;
    private List<ProductoRequestConsulta> productos;
    private CajeroRequestConsulta cajero;
}
