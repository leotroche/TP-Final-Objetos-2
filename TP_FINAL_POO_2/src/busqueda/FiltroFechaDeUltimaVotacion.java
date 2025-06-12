package busqueda;

import java.time.LocalDate;
import java.util.List;

import muestra.Muestra;

public class FiltroFechaDeUltimaVotacion implements FiltroSimple {
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
	public List<Muestra> filtrar(List<Muestra> muestras) {
		return muestras.stream()
				.filter(m -> m.getFechaDeUltimaVotacion().equals(this.getFechaDeUltimaVotacion()))
				.toList();
	}
}
