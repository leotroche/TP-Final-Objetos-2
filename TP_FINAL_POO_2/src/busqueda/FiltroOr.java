package busqueda;

import muestra.Muestra;

public class FiltroOr extends FiltroCompuesto {
	FiltroOr(Filtro... filtros) {
		super(filtros);
	}

	@Override
	public boolean cumple(Muestra muestra) {
		return this.getFiltros().stream()
				.anyMatch(f -> f.cumple(muestra));
	}
}
