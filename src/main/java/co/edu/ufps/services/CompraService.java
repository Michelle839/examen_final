package co.edu.ufps.services;

import co.edu.ufps.dto.CajeroRequest;
import co.edu.ufps.dto.ClienteRequest;
import co.edu.ufps.dto.CompraRequest;
import co.edu.ufps.dto.PagoRequest;
import co.edu.ufps.dto.ProductoRequest;
import co.edu.ufps.dto.RespuestaBase;
import co.edu.ufps.dto.RespuestaFactura;
import co.edu.ufps.dto.VendedorRequest;
import co.edu.ufps.entities.Cajero;
import co.edu.ufps.entities.Cliente;
import co.edu.ufps.entities.Compra;
import co.edu.ufps.entities.DetallesCompra;
import co.edu.ufps.entities.Pago;
import co.edu.ufps.entities.Producto;
import co.edu.ufps.entities.Tienda;
import co.edu.ufps.entities.TipoDocumento;
import co.edu.ufps.entities.TipoPago;
import co.edu.ufps.entities.Vendedor;
import co.edu.ufps.repositories.CajeroRepository;
import co.edu.ufps.repositories.ClienteRepository;
import co.edu.ufps.repositories.CompraRepository;
import co.edu.ufps.repositories.DetallesCompraRepository;
import co.edu.ufps.repositories.PagoRepository;
import co.edu.ufps.repositories.ProductoRepository;
import co.edu.ufps.repositories.TiendaRepository;
import co.edu.ufps.repositories.VendedorRepository;
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
	private VendedorRepository vendedorRepository;

	@Autowired
	private TipoPagoRepository tipoPagoRepository;

	@Autowired
	private PagoRepository pagoRepository;

	public RespuestaBase procesarCompra(String uuidTienda, CompraRequest request) {

		RespuestaBase respuestaTienda = validarTiendaExistente(uuidTienda);
		if (esError(respuestaTienda))
			return respuestaTienda;

		RespuestaBase respuestaCajero = validarCajero(request.getCajero(), uuidTienda);
		if (esError(respuestaCajero))
			return respuestaCajero;

		RespuestaBase respuestaVendedor = validarVendedor(request.getVendedor());
		if (esError(respuestaVendedor))
			return respuestaVendedor;

		RespuestaBase respuestaProductos = validarProductosYCalcularTotal(request.getProductos(),
				request.getImpuesto());
		if (esError(respuestaProductos))
			return respuestaProductos;
		Double totalCompra = (Double) respuestaProductos.getData();

		RespuestaBase respuestaPago = validarPago(request.getPagos(), totalCompra);
		if (esError(respuestaPago))
			return respuestaPago;

		RespuestaBase respuestaCliente = validarCliente(request.getCliente());
		if (esError(respuestaCliente))
			return respuestaCliente;

		Tienda tienda = (Tienda) respuestaTienda.getData();
		Cajero cajero = (Cajero) respuestaCajero.getData();
		Vendedor vendedor = (Vendedor) respuestaVendedor.getData();
		Cliente cliente = (Cliente) respuestaCliente.getData();

		Compra compra = new Compra();
		compra.setCliente(cliente);
		compra.setTienda(tienda);
		compra.setFecha(LocalDateTime.now());
		compra.setImpuestos(request.getImpuesto());
		compra.setCajero(cajero);
		compra.setVendedor(vendedor);
		compra.setTotal(totalCompra);
		compra.setObservaciones("Ninguna");
		compraRepository.save(compra);

		for (ProductoRequest productoRequest : request.getProductos()) {
			Producto producto = productoRepository.findByReferencia(productoRequest.getReferencia());
			DetallesCompra detallesCompra = new DetallesCompra();
			detallesCompra.setCompra(compra);
			detallesCompra.setProducto(producto);
			detallesCompra.setCantidad(productoRequest.getCantidad());
			detallesCompra.setDescuento(productoRequest.getDescuento());
			detallesCompra.setPrecio(producto.getPrecio());

			detalleCompraRepository.save(detallesCompra);

		}

		for (PagoRequest pagoRequest : request.getPagos()) {
			TipoPago tipoPago = tipoPagoRepository.findByNombre(pagoRequest.getTipoPago());
			Pago pago = new Pago();
			pago.setTipoPago(tipoPago);
			pago.setTarjetaTipo(pagoRequest.getTipoTarjeta());
			pago.setCuotas(pagoRequest.getCuotas());
			pago.setValor(pagoRequest.getValor());
			pago.setCompra(compra);

			pagoRepository.save(pago);
		}

		return new RespuestaBase("success", "La factura se ha creado correctamente con el número: " + compra.getId(),
				new RespuestaFactura(String.valueOf(compra.getId()), String.valueOf(totalCompra),
						compra.getFecha().toLocalDate().toString()));
	}

	private boolean esError(RespuestaBase respuesta) {
		return respuesta != null && !respuesta.getStatus().equals("success");
	}

	public RespuestaBase validarTiendaExistente(String uuidTienda) {
		Tienda tienda = tiendaRepository.findByUuid(uuidTienda);
		if (tienda == null) {
			return new RespuestaBase("error", "Tienda no encontrada", null);
		}
		return new RespuestaBase("success", "Tienda validada", tienda);
	}

	public RespuestaBase validarCajero(CajeroRequest cajeroRequest, String uuidTienda) {
		if (cajeroRequest == null || cajeroRequest.getToken() == null) {
			return new RespuestaBase("error", "No hay información del cajero", null);
		}

		Cajero cajero = cajeroRepository.findByToken(cajeroRequest.getToken());
		if (cajero == null) {
			return new RespuestaBase("error", "El token no corresponde a ningún cajero en la tienda", null);
		}

		if (!cajero.getTienda().getUuid().equals(uuidTienda)) {
			return new RespuestaBase("error", "El cajero no está asignado a esta tienda", null);
		}

		return new RespuestaBase("success", "Cajero validado", cajero);
	}

	public RespuestaBase validarVendedor(VendedorRequest vendedorRequest) {
		if (vendedorRequest == null || vendedorRequest.getDocumento() == null) {
			return new RespuestaBase("error", "No hay información del vendedor", null);
		}

		Vendedor vendedor = vendedorRepository.findByDocumento(vendedorRequest.getDocumento());
		if (vendedor == null) {
			return new RespuestaBase("error", "El vendedor no existe en la tienda", null);
		}

		return new RespuestaBase("success", "Vendedor validado", vendedor);

	}

	public RespuestaBase validarProductosYCalcularTotal(List<ProductoRequest> productos, double impuesto) {
		if (productos == null || productos.isEmpty()) {
			return new RespuestaBase("error", "No hay productos asignados para esta compra", null);
		}

		double total = 0;

		for (ProductoRequest productoRequest : productos) {
			Producto producto = productoRepository.findByReferencia(productoRequest.getReferencia());

			if (producto == null) {
				return new RespuestaBase("error", "La referencia del producto " + productoRequest.getReferencia()
						+ " no existe, por favor revisar los datos", null);
			}

			if (productoRequest.getCantidad() > producto.getCantidad()) {
				return new RespuestaBase("error", "La cantidad a comprar supera el máximo del producto en tienda",
						null);
			}

			double descuento = productoRequest.getDescuento() / 100.0;
			double subtotal = producto.getPrecio() * productoRequest.getCantidad() * (1 - descuento);
			total += subtotal;
		}

		total += total * (impuesto / 100);

		return new RespuestaBase("success", "Compra procesada correctamente", total);
	}

	public RespuestaBase validarPago(List<PagoRequest> pagosRequest, double total) {
		if (pagosRequest == null || pagosRequest.isEmpty()) {
			return new RespuestaBase("error", "No hay medios de pagos asignados para esta compra", null);
		}

		for (PagoRequest pagoRequest : pagosRequest) {
			if (pagoRequest.getTipoPago() == null || pagoRequest.getTipoPago().isEmpty()) {
				return new RespuestaBase("error", "No hay medios de pagos asignados para esta compra", null);
			}

			if (tipoPagoRepository.findByNombre(pagoRequest.getTipoPago()) == null) {
				return new RespuestaBase("error", "Tipo de pago no permitido en la tienda", null);
			}
		}

		double totalPagos = pagosRequest.stream().mapToDouble(PagoRequest::getValor).sum();
		if (Double.compare(total, totalPagos) != 0) {
			return new RespuestaBase("error", "El valor de la factura no coincide con el valor total de los pagos",
					null);
		}

		return new RespuestaBase("success", "Pagos validados correctamente", null);
	}

	public RespuestaBase validarCliente(ClienteRequest clienteRequest) {
		if (clienteRequest == null || clienteRequest.getDocumento() == null
				|| clienteRequest.getDocumento().isEmpty()) {
			return new RespuestaBase("error", "No hay información del cliente", null);
		}

		TipoDocumento tipoDocumento = tipoDocumentoRepository.findByNombre(clienteRequest.getTipoDocumento());
		if (tipoDocumento == null) {
			return new RespuestaBase("error", "Tipo de documento no válido", null);
		}

		Cliente cliente = clienteRepository.findByDocumentoAndTipoDocumento(clienteRequest.getDocumento(),
				tipoDocumento);

		if (cliente == null) {
			cliente = new Cliente();
			cliente.setDocumento(clienteRequest.getDocumento());
			cliente.setTipoDocumento(tipoDocumento);
			cliente.setNombre(clienteRequest.getNombre());
			clienteRepository.save(cliente);
			return new RespuestaBase("success", "Cliente registrado y asignado a la compra", cliente);
		}
		return new RespuestaBase("success", "Cliente encontrado y asignado a la compra", cliente);
	}

}