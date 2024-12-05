package co.edu.ufps.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.edu.ufps.dto.CajeroRequestConsulta;
import co.edu.ufps.dto.ClienteRequest;
import co.edu.ufps.dto.FacturaConsultaRespuesta;
import co.edu.ufps.dto.ProductoRequestConsulta;
import co.edu.ufps.dto.RespuestaBaseConsulta;
import co.edu.ufps.entities.Cajero;
import co.edu.ufps.entities.Cliente;
import co.edu.ufps.entities.Compra;
import co.edu.ufps.entities.Tienda;
import co.edu.ufps.entities.DetallesCompra;
import co.edu.ufps.entities.Producto;
import co.edu.ufps.repositories.CajeroRepository;
import co.edu.ufps.repositories.CompraRepository;
import co.edu.ufps.repositories.DetallesCompraRepository;
import co.edu.ufps.repositories.TiendaRepository;

@Service
public class FacturaService {

	@Autowired
	private TiendaRepository tiendaRepository;
	
	@Autowired
	private CajeroRepository cajeroRepository;

	@Autowired
	private CompraRepository compraRepository;

	@Autowired
	private DetallesCompraRepository detallesCompraRepository;

	public FacturaConsultaRespuesta consultarFactura(String uuidTienda, RespuestaBaseConsulta request) {

		Tienda tienda = tiendaRepository.findByUuid(uuidTienda);
		if (tienda == null) {
			throw new RuntimeException("Tienda no encontrada");
		}

		Cajero cajero = cajeroRepository.findByToken(request.getToken());
		if (cajero == null) {
			throw new RuntimeException("Cajero no encontrado");
		}

		if (request.getCliente() == null) {
			throw new RuntimeException("Cliente no encontrado");
		}

		Optional<Compra> numFactura = compraRepository.findById(request.getFactura());
		if (!numFactura.isPresent()) {
			throw new RuntimeException("Factura no encontrada");
		}

		if (!tienePermisoParaConsultarFactura(request.getToken(), uuidTienda))
			throw new RuntimeException("El cajero no tiene permiso para consultar esta factura");

		Compra compra = numFactura.get();

		Cliente cliente = compra.getCliente();

		if (!cliente.getDocumento().equals(request.getCliente())) {
			throw new RuntimeException("El cliente no coincide con el asociado a la factura");
		}

		List<DetallesCompra> detalles = detallesCompraRepository.findByCompraId(request.getFactura());

		List<ProductoRequestConsulta> productos = detalles.stream().map(detalle -> {
			Producto producto = detalle.getProducto();
			return new ProductoRequestConsulta(producto.getReferencia(), producto.getNombre(), detalle.getCantidad(),
					detalle.getPrecio(), detalle.getDescuento(),
					detalle.getCantidad() * detalle.getPrecio() - detalle.getDescuento());
		}).collect(Collectors.toList());

		return new FacturaConsultaRespuesta(compra.getTotal(), compra.getImpuestos(),
				new ClienteRequest(cliente.getDocumento(), cliente.getNombre(), cliente.getTipoDocumento().getNombre()),
				productos, new CajeroRequestConsulta(cajero.getDocumento(), cajero.getNombre()));
	}

	private boolean tienePermisoParaConsultarFactura(String token, String uuidTienda) {

		Cajero cajero = cajeroRepository.findByToken(token);
		if (cajero == null) {
			return false;
		}
		return cajero.getTienda().getUuid().equals(uuidTienda);
	}

}