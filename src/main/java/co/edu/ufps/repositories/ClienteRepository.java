package co.edu.ufps.repositories;

import co.edu.ufps.entities.Cliente;
import co.edu.ufps.entities.TipoDocumento;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
	
	Cliente findByDocumentoAndTipoDocumento(String documento, TipoDocumento tipoDocumento);

}
