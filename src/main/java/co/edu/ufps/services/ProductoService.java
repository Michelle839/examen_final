package co.edu.ufps.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.ufps.entities.Producto ;
import co.edu.ufps.repositories.ProductoRepository;

@Service
public class ProductoService {

	@Autowired
	ProductoRepository productoRepository;

	
	public List<Producto > list() {
		return productoRepository.findAll();
	}
	
	public Producto  create(Producto  producto) {
		return productoRepository.save(producto);
	}

	// Obtener un producto por ID
	public Optional<Producto > getById(Integer id) {
		return productoRepository.findById(id);
	}

	// Actualizar un producto existente
	public Optional<Producto > update(Integer id, Producto  productoDetails) {
		Optional<Producto > optionalproducto = productoRepository.findById(id);
		if (!optionalproducto.isPresent()) {
			return Optional.empty();
		}

		Producto  producto = optionalproducto.get();

		return Optional.of(productoRepository.save(producto));
	}

	// Eliminar un producto por ID
	public boolean delete(Integer id) {
		if (!productoRepository.existsById(id)) {
			return false;
		}
		productoRepository.deleteById(id);
		return true;
	}
}
