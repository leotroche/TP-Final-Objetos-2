package muestras.estados;

import muestras.Muestra;
import muestras.Opinion;
import muestras.TipoDeInsecto;

public class EstadoVerificado implements EstadoDeVerificacion {
	public void agregarOpinion(Muestra muestra, Opinion opinion) {
		// No recibe opiniones de experto en este estado
	}

	@Override
	public void recibioOpinionDeExperto(Muestra muestra) {
		// No recibe opiniones de experto en este estado
	}

	public TipoDeInsecto obtenerResultadoActual(Muestra muestra) {
		return muestra.obtenerUltimaOpinion().getTipoDeInsecto();
	}
}
