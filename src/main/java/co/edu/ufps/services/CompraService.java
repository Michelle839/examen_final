package co.edu.ufps.services;

import co.edu.ufps.dto.CompraRequest;
import co.edu.ufps.dto.MedioPagoRequest;
import co.edu.ufps.dto.ProductoRequest;
import co.edu.ufps.dto.RespuestaBase;
import co.edu.ufps.dto.RespuestaFactura;
import co.edu.ufps.entities.Cajero;
import co.edu.ufps.entities.Cliente;
import co.edu.ufps.entities.Compra;
import co.edu.ufps.entities.DetallesCompra;
import co.edu.ufps.entities.Pago;
import co.edu.ufps.entities.Producto;
import co.edu.ufps.entities.Tienda;
import co.edu.ufps.entities.TipoDocumento;
import co.edu.ufps.entities.TipoPago;
import co.edu.ufps.repositories.CajeroRepository;
import co.edu.ufps.repositories.ClienteRepository;
import co.edu.ufps.repositories.CompraRepository;
import co.edu.ufps.repositories.DetallesCompraRepository;
import co.edu.ufps.repositories.PagoRepository;
import co.edu.ufps.repositories.ProductoRepository;
import co.edu.ufps.repositories.TiendaRepository;
import co.edu.ufps.repositories.TipoDocumentoRepository;
import co.edu.ufps.repositories.TipoPagoRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
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
    private CompraRepository compraRepository;

    @Autowired
    private DetallesCompraRepository detalleCompraRepository;
    
    @Autowired
    private TipoDocumentoRepository tipoDocumentoRepository;

    @Autowired
    private CajeroRepository cajeroRepository;
    
    
    @Autowired
    private TipoPagoRepository tipoPagoRepository;

    @Autowired
    private PagoRepository pagoRepository;

    public RespuestaBase procesarCompra(String uuidTienda, CompraRequest request) {
        
        Tienda tienda = tiendaRepository.findByUuid(uuidTienda);
        if (tienda == null) {
            return new RespuestaBase("error", "Tienda no encontrada", null);
        }
        
        Optional<TipoDocumento> tipoDocumentoOptional = tipoDocumentoRepository
            .findByNombre(request.getCliente().getTipoDocumento());

        if (!tipoDocumentoOptional.isPresent()) {
            return new RespuestaBase("error", "Tipo de documento no encontrado", null);
        }

        TipoDocumento tipoDocumento = tipoDocumentoOptional.get();

        // Verificar si el cliente ya está registrado
        Cliente cliente = clienteRepository
            .findByDocumentoAndTipoDocumento(request.getCliente().getDocumento(), tipoDocumento);

        if (cliente == null) {
            // Si el cliente no existe, registrarlo
            cliente = new Cliente();
            cliente.setDocumento(request.getCliente().getDocumento());
            cliente.setNombre(request.getCliente().getNombre());
            cliente.setTipoDocumento(tipoDocumento);
            clienteRepository.save(cliente);
        }
        Cajero cajero = cajeroRepository.findByToken(request.getCajero().getToken()).orElse(null);
        if (cajero == null) {
            return new RespuestaBase("error", "Cajero no encontrado", null);
        }

        // Crear la compra
        Compra compra = new Compra();
        compra.setCliente(cliente);
        compra.setTienda(tienda);
        compra.setFecha(LocalDateTime.now());  // Usar la fecha y hora actual
        compra.setImpuestos(request.getImpuesto());

        // Calcular el total de la compra
        double total = calcularTotal(request.getProductos(), request.getImpuesto());
        compra.setTotal(total);

        compraRepository.save(compra);  // Guardar la compra

        // Crear los detalles de la compra
        for (ProductoRequest productoRequest : request.getProductos()) {
            Producto producto = productoRepository.findByReferencia(productoRequest.getReferencia());

            if (producto != null) {
                DetallesCompra detallesCompra = new DetallesCompra();
                detallesCompra.setCompra(compra);
                detallesCompra.setProducto(producto);
                detallesCompra.setCantidad(productoRequest.getCantidad());
                detallesCompra.setDescuento(productoRequest.getDescuento());
                detallesCompra.setPrecio(producto.getPrecio());

                detalleCompraRepository.save(detallesCompra);
            }
        }

        for (MedioPagoRequest medioPagoRequest : request.getMediosPago()) {
            // Comprobar si el tipo de pago está presente en la base de datos
            Optional<TipoPago> tipoPagoOptional = tipoPagoRepository.findByNombre(medioPagoRequest.getTipoPago());

            if (!tipoPagoOptional.isPresent()) {
                // Si no se encuentra el tipo de pago, retornar error
                return new RespuestaBase("error", "Tipo de pago no encontrado", null);
            }

            TipoPago tipoPago = tipoPagoOptional.get();  // Obtener el tipo de pago encontrado

            // Crear el objeto Pago y asignarle los valores
            Pago pago = new Pago();
            pago.setTipoPago(tipoPago);  // Asignar el tipo de pago encontrado
            pago.setTarjetaTipo(medioPagoRequest.getTipoTarjeta());
            pago.setCuotas(medioPagoRequest.getCuotas());
            pago.setValor(medioPagoRequest.getValor());
            pagoRepository.save(pago);
        }

        RespuestaFactura respuestaFactura = new RespuestaFactura(
        	    String.valueOf(compra.getId()),  // El número de factura
        	    total,                           // El total
        	    compra.getFecha().toString()     // La fecha de la compra
        	);



        // Devolver la respuesta con estado success
        return new RespuestaBase("success", "La factura se ha creado correctamente con el número: " + compra.getId(), respuestaFactura);
    }

    // Método para calcular el total de la compra
    private double calcularTotal(List<ProductoRequest> productos, double impuesto) {
        double total = 0;
        for (ProductoRequest productoRequest : productos) {
            Producto producto = productoRepository.findByReferencia(productoRequest.getReferencia());
            if (producto != null) {
                double precio = producto.getPrecio();
                double subtotal = precio * productoRequest.getCantidad();
                double descuento = subtotal * (productoRequest.getDescuento() / 100.0);
                total += (subtotal - descuento);
            }
        }
        total += total * (impuesto / 100);  // Aplicar el impuesto
        return total;
    }
}
