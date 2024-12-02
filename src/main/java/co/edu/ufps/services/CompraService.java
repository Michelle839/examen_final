package co.edu.ufps.services;

import co.edu.ufps.dto.CompraRequest;
import co.edu.ufps.entities.*;
import co.edu.ufps.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CompraService {

    @Autowired
    private TiendaRepository tiendaRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private ProductoRepository productoRepository;
    @Autowired
    private TipoDocumentoRepository tipoDocumentoRepository;
    @Autowired
    private VendedorRepository vendedorRepository;
    @Autowired
    private CajeroRepository cajeroRepository;
    @Autowired
    private CompraRepository compraRepository;
    @Autowired
    private DetallesCompraRepository detallesCompraRepository;
    @Autowired
    private TipoPagoRepository tipoPagoRepository;
    @Autowired
    private PagoRepository pagoRepository;

    @Transactional
    public void procesarCompra(String uuid, CompraRequest request) {
        // Validar existencia de la tienda
        Tienda tienda = tiendaRepository.findByUuid(uuid)
                .orElseThrow(() -> new RuntimeException("Tienda no encontrada con UUID: " + uuid));

    
        // Verificar vendedor y cajero
        Vendedor vendedor = vendedorRepository.findByDocumento(request.getVendedor().getDocumento())
                .orElseThrow(() -> new RuntimeException("Vendedor no encontrado con documento: " + request.getVendedor().getDocumento()));

        Cajero cajero = cajeroRepository.findByToken(request.getCajero().getToken())
                .orElseThrow(() -> new RuntimeException("Cajero no encontrado con token: " + request.getCajero().getToken()));

        // Registrar la compra
        Compra compra = new Compra();
        compra.setCliente(cliente);
        compra.setTienda(tienda);
        compra.setVendedor(vendedor);
        compra.setCajero(cajero);
        compra.setImpuestos(request.getImpuesto());
        compra.setTotal(0.0); // Se calculará con los productos
        compraRepository.save(compra);

        // Procesar productos y registrar detalles de compra
        double totalCompra = 0.0;
        for (ProductoRequest productoRequest : request.getProductos()) {
            Producto producto = productoRepository.findByReferencia(productoRequest.getReferencia())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado: " + productoRequest.getReferencia()));

            double precioConDescuento = producto.getPrecio() * (1 - productoRequest.getDescuento() / 100);
            totalCompra += precioConDescuento * productoRequest.getCantidad();

            DetallesCompra detalle = new DetallesCompra();
            detalle.setCompra(compra);
            detalle.setProducto(producto);
            detalle.setCantidad(productoRequest.getCantidad());
            detalle.setPrecio(producto.getPrecio());
            detalle.setDescuento(productoRequest.getDescuento());
            detallesCompraRepository.save(detalle);
        }
        compra.setTotal(totalCompra + (totalCompra * (request.getImpuesto() / 100)));
        compraRepository.save(compra);

        // Procesar pagos
        for (MedioPagoRequest medioPagoRequest : request.getMediosPago()) {
            TipoPago tipoPago = tipoPagoRepository.findByNombre(medioPagoRequest.getTipoPago())
                    .orElseGet(() -> crearTipoPago(medioPagoRequest.getTipoPago()));

            Pago pago = new Pago();
            pago.setCompra(compra);
            pago.setTipoPago(tipoPago);
            pago.setValor(medioPagoRequest.getValor());
            pago.setTarjetaTipo(medioPagoRequest.getTipoTarjeta());
            pago.setCuotas(medioPagoRequest.getCuotas());
            pagoRepository.save(pago);
        }
    }

    private Cliente crearCliente(ClienteRequest clienteRequest) {
        TipoDocumento tipoDocumento = tipoDocumentoRepository.findByNombre(clienteRequest.getTipoDocumento())
                .orElseThrow(() -> new RuntimeException("Tipo de documento no encontrado: " + clienteRequest.getTipoDocumento()));

        Cliente cliente = new Cliente();
        cliente.setNombre(clienteRequest.getNombre());
        cliente.setDocumento(clienteRequest.getDocumento());
        cliente.setTipoDocumento(tipoDocumento);
        return clienteRepository.save(cliente);
    }

    private TipoPago crearTipoPago(String nombre) {
        TipoPago tipoPago = new TipoPago();
        tipoPago.setNombre(nombre);
        return tipoPagoRepository.save(tipoPago);
    }
}
