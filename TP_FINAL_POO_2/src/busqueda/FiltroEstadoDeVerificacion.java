package busqueda;

import java.util.List;

import muestra.EstadoDeVerificacion;
import muestra.Muestra;

public class FiltroEstadoDeVerificacion implements FiltroSimple {
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
	public List<Muestra> filtrar(List<Muestra> muestras) {
		return muestras.stream()
				.filter(m -> m.getEstadoDeVerificacion().equals(this.getEstadoDeVerificacion()))
				.toList();
	}
}
