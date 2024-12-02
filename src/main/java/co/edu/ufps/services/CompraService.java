package co.edu.ufps.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.ufps.entities.Compra;
import co.edu.ufps.repositories.CompraRepository;

@Service
public class CompraService {

	@Autowired
	CompraRepository compraRepository;

	
	public List<Compra> list() {
		return compraRepository.findAll();
	}
	
	public Compra create(Compra compra) {
		return compraRepository.save(compra);
	}

	// Obtener un compra por ID
	public Optional<Compra> getById(Integer id) {
		return compraRepository.findById(id);
	}

	// Actualizar un compra existente
	public Optional<Compra> update(Integer id, Compra compraDetails) {
		Optional<Compra> optionalcompra = compraRepository.findById(id);
		if (!optionalcompra.isPresent()) {
			return Optional.empty();
		}

		Compra compra = optionalcompra.get();

		// Actualiza otros campos según sea necesario
		compra.setNombre(compraDetails.getNombre());

		return Optional.of(compraRepository.save(compra));
	}

	// Eliminar un compra por ID
	public boolean delete(Integer id) {
		if (!compraRepository.existsById(id)) {
			return false;
		}
		compraRepository.deleteById(id);
		return true;
	}
}
