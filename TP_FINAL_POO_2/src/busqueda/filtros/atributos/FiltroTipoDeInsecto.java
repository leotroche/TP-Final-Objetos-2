package busqueda.filtros.atributos;

import busqueda.filtros.Filtro;
import muestras.Muestra;
import muestras.TipoDeInsecto;

public class FiltroTipoDeInsecto extends Filtro {
	private TipoDeInsecto tipoDeInsecto;


	public FiltroTipoDeInsecto(TipoDeInsecto tipoDeInsecto) {
		this.setTipoDeInsecto(tipoDeInsecto);
	}

	private TipoDeInsecto getTipoDeInsecto() {
		return this.tipoDeInsecto;
	}

	private void setTipoDeInsecto(TipoDeInsecto tipoDeInsecto) {
		this.tipoDeInsecto = tipoDeInsecto;
	}

	@Override
	public boolean cumple(Muestra muestra) {
		return this.getTipoDeInsecto().equals(muestra.getTipoDeInsecto());
	}
}
