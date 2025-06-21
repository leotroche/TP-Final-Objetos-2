package busqueda.filtro.atributo.fecha.comparacion;

import java.time.LocalDate;

public class ComparadorFechaAnterior implements ComparadorDeFechas {
	@Override
	public boolean comparar(LocalDate fecha1, LocalDate fecha2) {
		return fecha1.isBefore(fecha2);
	}
}
