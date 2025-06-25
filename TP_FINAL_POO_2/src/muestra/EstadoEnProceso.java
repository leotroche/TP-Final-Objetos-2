package muestra;

import java.util.stream.Stream;

import varios.Opinion;
import varios.TipoDeInsecto;

public class EstadoEnProceso implements EstadoDeVerificacion {

	private boolean existeEmpate = false;           // La primera vez que se instancia EstadoEnProceso, solo tiene la opinion de un experto, por lo tanto no hay coincidencia en tipo de insecto
	
	/*
	 * Agrega una opinion a la muestra solo si es experto, si lo es, verifica si hay algun otro experto que coincide con su tipo de insecto
	 */
	    
	@Override
	public void agregarOpinion(Muestra muestra, Opinion opinion) {
		opinion.getAutor().opinarSobreMuestraEnProceso(muestra, opinion);
	}


	@Override
	public void recibioOpinionDeExperto(Muestra muestra) {
		existeEmpate = true;
		muestra.verificarMuestraSiCorresponde();
	}
	
	/*
	 *  Devuelve el tipo de insecto, si hay empate, el tipo de insecto es ninguno, si no hay, devuelve el tipo de insecto de la ultima opinion.
	 *  No habra empate cuando un solo experto haya opinado, si hay mas de uno, hay empate
	 */
	
	@Override
	public TipoDeInsecto obtenerResultadoActual(Muestra muestra) {
		return existeEmpate ? TipoDeInsecto.NINGUNA : muestra.obtenerUltimaOpinion().getTipoDeInsecto();
	}


	
	

}
