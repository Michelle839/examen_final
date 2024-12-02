package co.edu.ufps.repositories;

import co.edu.ufps.entities.Cajero;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CajeroRepository extends JpaRepository<Cajero, Integer> {
    Optional<Cajero> findByToken(String token);
}
