package busqueda.filtros.atributos;

import busqueda.filtros.FiltroSimple;
import muestras.Muestra;
import muestras.estados.EstadoDeVerificacion;

public class FiltroEstadoDeVerificacion extends FiltroSimple {
	private EstadoDeVerificacion estadoDeVerificacion;

	public FiltroEstadoDeVerificacion(EstadoDeVerificacion estadoDeVerificacion) {
		this.setEstadoDeVerificacion(estadoDeVerificacion);
	}

	private EstadoDeVerificacion getEstadoDeVerificacion() {
		return this.estadoDeVerificacion;
	}

	private void setEstadoDeVerificacion(EstadoDeVerificacion estadoDeVerificacion) {
		this.estadoDeVerificacion = estadoDeVerificacion;
	}

	@Override
	public boolean cumple(Muestra muestra) {
		return this.getEstadoDeVerificacion().equals(muestra.getEstadoDeVerificacion());
	}
}
