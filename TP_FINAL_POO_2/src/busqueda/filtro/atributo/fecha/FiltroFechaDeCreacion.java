package busqueda.filtro.atributo.fecha;

import java.time.LocalDate;

import busqueda.filtro.atributo.fecha.comparacion.ComparadorDeFechas;
import muestra.Muestra;

public class FiltroFechaDeCreacion extends FiltroFecha {
	public FiltroFechaDeCreacion(LocalDate fechaDeCreacion, ComparadorDeFechas comparador) {
		super(fechaDeCreacion, comparador);
	}

	@Override
	public boolean cumple(Muestra muestra) {
		return this.getComparador().comparar(muestra.getFechaDeCreacion(), this.getFecha());
	}
}
