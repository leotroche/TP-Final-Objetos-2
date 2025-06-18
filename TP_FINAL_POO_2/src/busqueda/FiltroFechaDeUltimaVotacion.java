package busqueda;

import java.time.LocalDate;

import muestra.Muestra;

public class FiltroFechaDeUltimaVotacion extends FiltroSimple {
	private LocalDate fechaDeUltimaVotacion;

	public FiltroFechaDeUltimaVotacion(LocalDate fechaDeUltimaVotacion) {
		this.setFechaDeUltimaVotacion(fechaDeUltimaVotacion);
	}

	private LocalDate getFechaDeUltimaVotacion() {
		return this.fechaDeUltimaVotacion;
	}

	private void setFechaDeUltimaVotacion(LocalDate fechaDeUltimaVotacion) {
		this.fechaDeUltimaVotacion = fechaDeUltimaVotacion;
	}

	@Override
	public boolean cumple(Muestra muestra) {
		return this.getFechaDeUltimaVotacion().equals(muestra.getFechaDeUltimaVotacion());
	}
}
