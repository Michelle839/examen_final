package co.edu.ufps.repositories;

import co.edu.ufps.entities.Compra;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CompraRepository extends JpaRepository<Compra, Integer> {
	Optional<Compra> findById(Integer id);

	Compra findByIdAndClienteId(Integer id, Integer clienteId);

}
