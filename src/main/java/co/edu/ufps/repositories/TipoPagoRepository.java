package co.edu.ufps.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.ufps.entities.TipoPago;
import co.edu.ufps.entities.Compra;


@Repository
public interface TipoPagoRepository extends JpaRepository<TipoPago,Integer>{
}


