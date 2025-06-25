package muestra;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.List;

import varios.Opinion;
import varios.TipoDeInsecto;

public class EstadoNoVerificado implements EstadoDeVerificacion {
	  
	@Override 
	public void agregarOpinion(Muestra muestra, Opinion opinion) {
		opinion.getAutor().opinarSobreMuestra(muestra, opinion);
	}
	
	
	@Override
	public void recibioOpinionDeExperto(Muestra muestra) {
		muestra.setEstadoDeVerificacion(new EstadoEnProceso());
	}
	
	
	@Override
	public TipoDeInsecto obtenerResultadoActual(Muestra muestra) {
		
		/*
		 * Utilizo un Map siendo un TipoDeInsecto la clave (correspone al tipo de insecto de la muestra) y como valor
		 * la cantidad de veces que se repite esa tipo
		 */
		
		Map<TipoDeInsecto, Long> votacionesDeCadaTipoDeInsecto = muestra.getOpiniones().stream().collect(Collectors.groupingBy(Opinion::getTipoDeInsecto, Collectors.counting()));
		
		
		/*
		 * Obtengo todos los valores del map y averiguo cual es el maximo
		 * Creo una lista para almacenar todos los tipos de insectos que tengan ese valor
		 */
		
		Long maximaCantidadVotaciones = Collections.max(votacionesDeCadaTipoDeInsecto.values());
		List<TipoDeInsecto> tiposConMasVotaciones = new ArrayList<TipoDeInsecto>();
		
		
		/*
		 * Recorro el Map con los valores de las claves y agrego a la lista solo los que coincidan con el 
		 * maximo conseguido
		 */
		
		for (TipoDeInsecto tipo : votacionesDeCadaTipoDeInsecto.keySet()) {
			if (votacionesDeCadaTipoDeInsecto.get(tipo) == maximaCantidadVotaciones) {
				tiposConMasVotaciones.add(tipo);
			}
		}
		
		
		/*
		 * Si la lista tiene mas de un elemento, hay empate, por lo tanto el tipo de insecto es indefinido (NINGUNA)
		 * Si tiene un solo, el tipo de insecto es el unico que contiene la lista
		 */
		
		return (tiposConMasVotaciones.size() > 1) ? TipoDeInsecto.NINGUNA : tiposConMasVotaciones.get(0);
		
	}
	
	
	

	
	
}
