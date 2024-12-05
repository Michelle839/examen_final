package co.edu.ufps.repositories;

import co.edu.ufps.entities.Producto;
import co.edu.ufps.entities.Tienda;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TiendaRepository extends JpaRepository<Tienda, Integer> {
	Tienda findByUuid(String uuid);
}
