package co.edu.ufps.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.ufps.entities.DetallesCompra;


@Repository
public interface DetallesCompraRepository extends JpaRepository<DetallesCompra,Integer>{
    List<DetallesCompra> findByCompraId(Integer compraId);

}


