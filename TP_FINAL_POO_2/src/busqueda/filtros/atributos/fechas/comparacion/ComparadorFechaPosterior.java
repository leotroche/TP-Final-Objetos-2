package busqueda.filtros.atributos.fechas.comparacion;

import java.time.LocalDate;

public class ComparadorFechaPosterior implements ComparadorDeFechas {
	@Override
	public boolean comparar(LocalDate fecha1, LocalDate fecha2) {
		return fecha1.isAfter(fecha2);
	}
}
