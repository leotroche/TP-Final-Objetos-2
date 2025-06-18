package busqueda;

import muestra.Muestra;

public class FiltroAnd extends FiltroCompuesto {
	public FiltroAnd(Filtro... filtros) {
		super(filtros);
	}

	@Override
	public boolean cumple(Muestra muestra) {
		return this.getFiltros().stream()
				.allMatch(f -> f.cumple(muestra));
	}
}
