package co.edu.ufps.repositories;

import co.edu.ufps.entities.TipoPago;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TipoPagoRepository extends JpaRepository<TipoPago, Integer> {
	TipoPago findByNombre(String nombre);
}
