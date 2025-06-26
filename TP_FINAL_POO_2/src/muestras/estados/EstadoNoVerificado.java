package muestras.estados;

import muestras.Muestra;
import muestras.Opinion;
import muestras.TipoDeInsecto;

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
		return muestra.tipoMasFrecuenteEnOpiniones();
	}
}
