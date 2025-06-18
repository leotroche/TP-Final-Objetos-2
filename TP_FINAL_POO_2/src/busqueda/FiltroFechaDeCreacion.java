package busqueda;

import java.time.LocalDate;

import muestra.Muestra;

public class FiltroFechaDeCreacion extends FiltroSimple {
	private LocalDate fechaDeCreacion;

	public FiltroFechaDeCreacion(LocalDate fechaDeCreacion) {
		this.setFechaDeCreacion(fechaDeCreacion);
	}

	private LocalDate getFechaDeCreacion() {
		return this.fechaDeCreacion;
	}

	private void setFechaDeCreacion(LocalDate fechaDeCreacion) {
		this.fechaDeCreacion = fechaDeCreacion;
	}

	@Override
	public boolean cumple(Muestra muestra) {
		return this.getFechaDeCreacion().equals(muestra.getFechaDeCreacion());
	}
}
