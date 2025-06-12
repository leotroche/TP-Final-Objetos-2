package busqueda;

import java.time.LocalDate;
import java.util.List;

import muestra.Muestra;

public class FiltroFechaDeCreacion implements FiltroSimple {
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
	public List<Muestra> filtrar(List<Muestra> muestras) {
		return muestras.stream()
				.filter(m -> m.getFechaDeCreacion().equals(this.getFechaDeCreacion()))
				.toList();
	}
}
