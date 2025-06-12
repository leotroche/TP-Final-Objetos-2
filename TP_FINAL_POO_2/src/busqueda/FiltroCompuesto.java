package busqueda;

import java.util.ArrayList;
import java.util.List;

public abstract class FiltroCompuesto implements Filtro {
	private List<Filtro> filtros = new ArrayList<Filtro>();

	public FiltroCompuesto(Filtro... filtros) {
		for (Filtro filtro : filtros) {
			this.agregarFiltro(filtro);
		}
	}

	public List<Filtro> getFiltros() {
		return this.filtros;
	}

	public void agregarFiltro(Filtro filtro) {
		this.getFiltros().add(filtro);
	}
}
