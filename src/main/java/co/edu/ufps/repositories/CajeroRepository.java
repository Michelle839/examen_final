package co.edu.ufps.repositories;

import co.edu.ufps.entities.Cajero;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CajeroRepository extends JpaRepository<Cajero, Integer> {

	Cajero findByToken(String token);
	
}
