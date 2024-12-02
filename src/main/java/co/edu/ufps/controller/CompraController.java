// CompraController.java
package co.edu.ufps.controller;

import co.edu.ufps.dto.CompraRequest;
import co.edu.ufps.dto.RespuestaBase;
import co.edu.ufps.services.CompraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/crear")
public class CompraController {

    @Autowired
    private CompraService compraService;

    @PostMapping("/{uuidTienda}")
    public RespuestaBase crearFactura(@PathVariable String uuidTienda, @RequestBody CompraRequest request) {
        return compraService.procesarCompra(uuidTienda, request);
    }
}
