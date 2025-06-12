package busqueda;

import java.util.ArrayList;
import java.util.List;

import muestra.Muestra;

public class FiltroAND extends FiltroCompuesto {
	public FiltroAND(Filtro... filtros) {
		super(filtros);
	}

	@Override
	public List<Muestra> filtrar(List<Muestra> muestras) {
		List<Muestra> resultado = new ArrayList<Muestra>(muestras);

		for (Filtro filtro : this.getFiltros()) {
			// NOTA: .retainAll() conserva solo las muestras que cumplen todos los filtros.
			resultado.retainAll(filtro.filtrar(muestras));
		}

		return resultado;
	}
}
