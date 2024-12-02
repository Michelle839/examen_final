package co.edu.ufps.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.ufps.entities.Cliente;
import co.edu.ufps.repositories.ClienteRepository;

@Service
public class ClienteService {

	@Autowired
	ClienteRepository clienteRepository;

	
	public List<Cliente> list() {
		return clienteRepository.findAll();
	}
	
	public Cliente create(Cliente cliente) {
		return clienteRepository.save(cliente);
	}

	// Obtener un cliente por ID
	public Optional<Cliente> getById(Integer id) {
		return clienteRepository.findById(id);
	}

	// Actualizar un cliente existente
	public Optional<Cliente> update(Integer id, Cliente clienteDetails) {
		Optional<Cliente> optionalcliente = clienteRepository.findById(id);
		if (!optionalcliente.isPresent()) {
			return Optional.empty();
		}

		Cliente cliente = optionalcliente.get();

		return Optional.of(clienteRepository.save(cliente));
	}

	// Eliminar un cliente por ID
	public boolean delete(Integer id) {
		if (!clienteRepository.existsById(id)) {
			return false;
		}
		clienteRepository.deleteById(id);
		return true;
	}
}
