package busqueda.filtro.atributo.fecha;

import java.time.LocalDate;

import busqueda.filtro.atributo.fecha.comparacion.ComparadorDeFechas;
import muestra.Muestra;

public class FiltroFechaDeUltimaVotacion extends FiltroFecha {
	public FiltroFechaDeUltimaVotacion(LocalDate fechaDeUltimaVotacion, ComparadorDeFechas comparador) {
		super(fechaDeUltimaVotacion, comparador);
	}

	@Override
	public boolean cumple(Muestra muestra) {
		return this.getComparador().comparar(muestra.getFechaDeUltimaVotacion(), this.getFecha());
	}
}
