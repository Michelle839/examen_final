package co.edu.ufps.controller;

import co.edu.ufps.dto.CompraRequest;
import co.edu.ufps.services.CompraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/compra")
public class CompraController {

    @Autowired
    private CompraService compraService;

    @PostMapping("/{uuid}")
    public ResponseEntity<?> registrarCompra(@PathVariable String uuid, @RequestBody CompraRequest request) {
        try {
            compraService.procesarCompra(uuid, request);
            return ResponseEntity.ok("Compra registrada exitosamente.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
