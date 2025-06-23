package busqueda.filtro.atributo;

import busqueda.filtro.FiltroSimple;
import muestra.EstadoDeVerificacion;
import muestra.Muestra;

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
