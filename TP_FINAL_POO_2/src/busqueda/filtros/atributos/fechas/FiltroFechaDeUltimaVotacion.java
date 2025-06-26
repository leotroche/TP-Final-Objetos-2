package busqueda.filtros.atributos.fechas;

import java.time.LocalDate;

import busqueda.filtros.atributos.fechas.comparacion.ComparadorDeFechas;
import muestras.Muestra;

public class FiltroFechaDeUltimaVotacion extends FiltroFecha {
	public FiltroFechaDeUltimaVotacion(LocalDate fechaDeUltimaVotacion, ComparadorDeFechas comparador) {
		super(fechaDeUltimaVotacion, comparador);
	}

	@Override
	public boolean cumple(Muestra muestra) {
		return this.getComparador().comparar(muestra.getFechaDeUltimaVotacion(), this.getFecha());
	}
}
