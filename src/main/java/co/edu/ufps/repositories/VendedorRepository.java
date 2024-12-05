package co.edu.ufps.repositories;

import co.edu.ufps.entities.Vendedor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendedorRepository extends JpaRepository<Vendedor, Integer> {

	Vendedor findByDocumento(String documento);

}
