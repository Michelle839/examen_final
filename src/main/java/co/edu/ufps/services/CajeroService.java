package co.edu.ufps.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.ufps.entities.Cajero;
import co.edu.ufps.repositories.CajeroRepository;

@Service
public class CajeroService {

	@Autowired
	CajeroRepository cajeroRepository;

	
	public List<Cajero> list() {
		return cajeroRepository.findAll();
	}
	
	public Cajero create(Cajero cajero) {
		return cajeroRepository.save(cajero);
	}

	// Obtener un cajero por ID
	public Optional<Cajero> getById(Integer id) {
		return cajeroRepository.findById(id);
	}

	// Actualizar un cajero existente
	public Optional<Cajero> update(Integer id, Cajero cajeroDetails) {
		Optional<Cajero> optionalcajero = cajeroRepository.findById(id);
		if (!optionalcajero.isPresent()) {
			return Optional.empty();
		}

		Cajero cajero = optionalcajero.get();

		return Optional.of(cajeroRepository.save(cajero));
	}

	// Eliminar un cajero por ID
	public boolean delete(Integer id) {
		if (!cajeroRepository.existsById(id)) {
			return false;
		}
		cajeroRepository.deleteById(id);
		return true;
	}
}
