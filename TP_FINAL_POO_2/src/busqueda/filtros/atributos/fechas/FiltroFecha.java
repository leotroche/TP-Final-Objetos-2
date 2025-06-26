package busqueda.filtros.atributos.fechas;

import java.time.LocalDate;

import busqueda.filtros.FiltroSimple;
import busqueda.filtros.atributos.fechas.comparacion.ComparadorDeFechas;

public abstract class FiltroFecha extends FiltroSimple {
	private LocalDate fecha;
	private ComparadorDeFechas comparador;

	public FiltroFecha(LocalDate fecha, ComparadorDeFechas comparador) {
		this.setFecha(fecha);
		this.setComparador(comparador);
	}

	protected LocalDate getFecha() {
		return this.fecha;
	}

	private void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public ComparadorDeFechas getComparador() {
		return this.comparador;
	}

	public void setComparador(ComparadorDeFechas comparador) {
		this.comparador = comparador;
	}
}
