package co.edu.ufps.controller;

@RestController
@RequestMapping("/consultar")
public class FacturaController {

    @Autowired
    private VendedorService vendedorService;

    @Autowired
    private FacturaService facturaService;

    @PostMapping("/{facturaId}")
    public ResponseEntity<FacturaResponse> consultarFactura(@PathVariable Long facturaId,
                                                            @RequestBody FacturaRequest facturaRequest) {
        // Validar token
        Vendedor vendedor = vendedorService.validarToken(facturaRequest.getToken());
        if (vendedor == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                 .body(new FacturaResponse("error", "Token no válido", null));
        }

        // Obtener la factura
        Factura factura = facturaService.obtenerFactura(facturaId, facturaRequest.getCliente(), vendedor);
        if (factura == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body(new FacturaResponse("error", "Factura no encontrada o no tiene permiso", null));
        }

        // Construir la respuesta
        FacturaResponse response = new FacturaResponse("success", "Factura encontrada", factura);
        return ResponseEntity.ok(response);
    }
}
