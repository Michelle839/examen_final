package co.edu.ufps.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.ufps.entities.Cajero;
import co.edu.ufps.repositories.CajeroRepository;
import co.edu.ufps.repositories.TiendaRepository;

@Service
public class CajeroService {

//	 @Autowired
//	    private CajeroRepository cajeroRepository;
//
//	    @Autowired
//	    private TiendaRepository tiendaRepository;
//
//	    public boolean tienePermisoParaConsultarFactura(String token, Long tiendaId) {
//	        // Buscar al cajero usando el token
//	        Cajero cajero = cajeroRepository.findByToken(token);
//
//	        // Verificar si se encuentra el cajero
//	        if (cajero == null) {
//	            return false; // El cajero no existe o el token es inválido
//	        }
//
//	        // Verificar si el cajero está asociado con la tienda indicada
//	        return cajero.getTiendaId().equals(tiendaId); // Suponiendo que 'TiendaId' esté asociado con el cajero
//	    }
}
