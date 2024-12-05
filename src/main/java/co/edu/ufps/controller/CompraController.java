package co.edu.ufps.controller;

import co.edu.ufps.dto.CompraRequest;
import co.edu.ufps.dto.FacturaConsultaRespuesta;
import co.edu.ufps.dto.RespuestaBase;
import co.edu.ufps.dto.RespuestaBaseConsulta;
import co.edu.ufps.services.CompraService;
import co.edu.ufps.services.FacturaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/compra")
public class CompraController {

    @Autowired
    private CompraService compraService;
    @Autowired
    private FacturaService facturaService;

    @PostMapping("/crear")
    @ResponseStatus(HttpStatus.CREATED) 
    public RespuestaBase crearFactura(@RequestBody CompraRequest request, @RequestParam String uuidTienda) {
        return compraService.procesarCompra(uuidTienda, request);
    }
    
    
    @PostMapping("/consultar/{uuidTienda}")
    public ResponseEntity<?> consultarFactura(@PathVariable String uuidTienda, 
                                              @RequestBody RespuestaBaseConsulta request) {
        try {
            // Llamar al servicio para consultar la factura
            FacturaConsultaRespuesta factura = facturaService.consultarFactura(uuidTienda, request);

            // Si todo es correcto, se devuelve la factura con el status 200
            return new ResponseEntity<>(factura, HttpStatus.OK);

        } catch (RuntimeException e) {
            // Si ocurre un error (por ejemplo, tienda no encontrada o sin permisos)
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
