package busqueda.filtros.atributos.fechas;

import java.time.LocalDate;

import busqueda.filtros.atributos.fechas.comparacion.ComparadorDeFechas;
import muestras.Muestra;

public class FiltroFechaDeCreacion extends FiltroFecha {
	public FiltroFechaDeCreacion(LocalDate fechaDeCreacion, ComparadorDeFechas comparador) {
		super(fechaDeCreacion, comparador);
	}

	@Override
	public boolean cumple(Muestra muestra) {
		return this.getComparador().comparar(muestra.getFechaDeCreacion(), this.getFecha());
	}
}
