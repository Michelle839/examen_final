package co.edu.ufps.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.ufps.entities.DetallesCompra;
import co.edu.ufps.repositories.DetallesCompraRepository;

@Service
public class DetallesCompraService {

	@Autowired
	DetallesCompraRepository detallesCompraRepository;

	
	public List<DetallesCompra> list() {
		return detallesCompraRepository.findAll();
	}
	
	public DetallesCompra create(DetallesCompra detallesCompra) {
		return detallesCompraRepository.save(detallesCompra);
	}

	// Obtener un detallesCompra por ID
	public Optional<DetallesCompra> getById(Integer id) {
		return detallesCompraRepository.findById(id);
	}

	// Actualizar un detallesCompra existente
	public Optional<DetallesCompra> update(Integer id, DetallesCompra detallesCompraDetails) {
		Optional<DetallesCompra> optionaldetallesCompra = detallesCompraRepository.findById(id);
		if (!optionaldetallesCompra.isPresent()) {
			return Optional.empty();
		}

		DetallesCompra detallesCompra = optionaldetallesCompra.get();

		return Optional.of(detallesCompraRepository.save(detallesCompra));
	}

	// Eliminar un detallesCompra por ID
	public boolean delete(Integer id) {
		if (!detallesCompraRepository.existsById(id)) {
			return false;
		}
		detallesCompraRepository.deleteById(id);
		return true;
	}
}
