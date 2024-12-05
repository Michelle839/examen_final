package co.edu.ufps.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
public class CompraRequest {

    private double impuesto;

    @JsonProperty("cliente")
    private ClienteRequest cliente;

    @JsonProperty("productos")
    private List<ProductoRequest> productos;
    
    @JsonProperty("medios_pago")
    private List<PagoRequest> pagos;

    @JsonProperty("vendedor")
    private VendedorRequest vendedor;

    @JsonProperty("cajero")
    private CajeroRequest cajero;
}

