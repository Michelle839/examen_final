package co.edu.ufps.controller;

import co.edu.ufps.dto.CompraRequest;
import co.edu.ufps.dto.RespuestaBase;
import co.edu.ufps.services.CompraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/compra")
public class CompraController {

    @Autowired
    private CompraService compraService;

    @PostMapping("/crear")
    @ResponseStatus(HttpStatus.CREATED) 
    public RespuestaBase crearFactura(@RequestBody CompraRequest request, @RequestParam String uuidTienda) {
        return compraService.procesarCompra(uuidTienda, request);
    }
}
