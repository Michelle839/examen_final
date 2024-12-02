package co.edu.ufps.repositories;

import co.edu.ufps.entities.TipoDocumento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TipoDocumentoRepository extends JpaRepository<TipoDocumento, Integer> {
    Optional<TipoDocumento> findByNombre(String nombre);
}
