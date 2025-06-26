package muestras.estados;

import muestras.Muestra;
import muestras.Opinion;
import muestras.TipoDeInsecto;

public class EstadoEnProceso implements EstadoDeVerificacion {
	@Override
	public void agregarOpinion(Muestra muestra, Opinion opinion) {
		opinion.getAutor().opinarSobreMuestraEnProceso(muestra, opinion);
	}

	@Override
	public void recibioOpinionDeExperto(Muestra muestra) {
		muestra.verificarMuestraSiCorresponde();
	}

	@Override
	public TipoDeInsecto obtenerResultadoActual(Muestra muestra) {
		// Si hay una opinion de experto, devuelve el tipo de insecto de esa opinion (la última).
		// Si hay más de una opinion de experto, entonces hay empate (NINGUNA).

		return muestra.opinionesDeExpertos().size() == 1
				? muestra.obtenerUltimaOpinion().getTipoDeInsecto()
						: TipoDeInsecto.NINGUNA;
	}
}
