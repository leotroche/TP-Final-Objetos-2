package muestra;

import varios.Opinion;
import varios.TipoDeInsecto;

public class EstadoVerificado implements EstadoDeVerificacion {

	public void agregarOpinion(Muestra muestra, Opinion opinion) {
		
		// No agrega opinion al estar en estado verificado
	}
	 
	@Override
	public void recibioOpinionDeExperto(Muestra muestra) {
		// No recibe opiniones de experto en este estado
	}
	
	
	/*
	 * Devuelve el tipo de insecto de la ultima opinion agregada antes de cambiar al estado verificado
	 */
	
	public TipoDeInsecto obtenerResultadoActual(Muestra muestra) {
		return muestra.obtenerUltimaOpinion().getTipoDeInsecto();
	}

	
}
 
