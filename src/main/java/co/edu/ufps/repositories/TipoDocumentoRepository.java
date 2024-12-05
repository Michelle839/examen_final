package co.edu.ufps.repositories;

import co.edu.ufps.entities.TipoDocumento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoDocumentoRepository extends JpaRepository<TipoDocumento, Integer> {
	
	TipoDocumento findByNombre(String nombre);
}
