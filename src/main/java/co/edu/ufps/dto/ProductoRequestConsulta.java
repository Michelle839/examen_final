package co.edu.ufps.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductoRequestConsulta {
	
	 private String referencia;
	    private String nombre;
	    private int cantidad;
	    private double precio;
	    private double descuento;
	    private double subtotal;

}
