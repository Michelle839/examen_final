package co.edu.ufps.dto;

import lombok.Data;
import java.util.List;

@Data
public class CompraRequest {

    private double impuesto;

    private ClienteRequest cliente;

    private List<ProductoRequest> productos;

    private List<MedioPagoRequest> mediosPago;

    private VendedorRequest vendedor;

    private CajeroRequest cajero;
}
