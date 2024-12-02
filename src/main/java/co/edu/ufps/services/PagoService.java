package co.edu.ufps.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.ufps.entities.Pago;
import co.edu.ufps.repositories.PagoRepository;

@Service
public class PagoService {

	@Autowired
	PagoRepository pagoRepository;

	
	public List<Pago> list() {
		return pagoRepository.findAll();
	}
	
	public Pago create(Pago pago) {
		return pagoRepository.save(pago);
	}

	// Obtener un pago por ID
	public Optional<Pago> getById(Integer id) {
		return pagoRepository.findById(id);
	}

	// Actualizar un pago existente
	public Optional<Pago> update(Integer id, Pago pagoDetails) {
		Optional<Pago> optionalpago = pagoRepository.findById(id);
		if (!optionalpago.isPresent()) {
			return Optional.empty();
		}

		Pago pago = optionalpago.get();

		return Optional.of(pagoRepository.save(pago));
	}

	// Eliminar un pago por ID
	public boolean delete(Integer id) {
		if (!pagoRepository.existsById(id)) {
			return false;
		}
		pagoRepository.deleteById(id);
		return true;
	}
}
