package co.edu.ufps.repositories;

import co.edu.ufps.entities.Vendedor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VendedorRepository extends JpaRepository<Vendedor, Integer> {

	  Optional<Vendedor> findByDocumento(String documento);
	  }
