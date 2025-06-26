package busqueda.filtros.atributos.fechas.comparacion;

import java.time.LocalDate;

public interface ComparadorDeFechas {
	public boolean comparar(LocalDate fecha1, LocalDate fecha2);
}
