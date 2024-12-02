package co.edu.ufps.controller;

import co.edu.ufps.dto.CompraRequest;
import co.edu.ufps.dto.RespuestaBase;
import co.edu.ufps.services.CompraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/compra")
public class CompraController {

    @Autowired
    private CompraService compraService;

    // Endpoint para procesar la compra
    @PostMapping("/factura")
    @ResponseStatus(HttpStatus.CREATED)  // Status 201 cuando la compra es exitosa
    public RespuestaBase crearFactura(@RequestBody CompraRequest request, @RequestParam String uuidTienda) {
        // Llamar al servicio para procesar la compra
        return compraService.procesarCompra(uuidTienda, request);
    }
}
