package busqueda;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import muestra.Muestra;

public class FiltroOR extends FiltroCompuesto {
	FiltroOR(Filtro... filtros) {
		super(filtros);
	}

	@Override
	public List<Muestra> filtrar(List<Muestra> muestras) {
		Set<Muestra> resultado = new HashSet<Muestra>();

		for (Filtro filtro : this.getFiltros()) {
			// NOTA: .addAll() conserva todas las muestras que cumplen al menos uno de los filtros.
			resultado.addAll(filtro.filtrar(muestras));
		}

		return new ArrayList<Muestra>(resultado);
	}
}

