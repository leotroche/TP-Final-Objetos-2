package busqueda;

import muestra.Muestra;
import varios.TipoDeInsecto;

public class FiltroTipoDeInsectoDetectado extends Filtro {
	private TipoDeInsecto tipoDeInsectoDetectado;


	public FiltroTipoDeInsectoDetectado(TipoDeInsecto tipoDeInsectoDetectado) {
		this.setTipoDeInsectoDetectado(tipoDeInsectoDetectado);
	}

	private TipoDeInsecto getTipoDeInsectoDetectado() {
		return this.tipoDeInsectoDetectado;
	}

	private void setTipoDeInsectoDetectado(TipoDeInsecto tipoDeInsectoDetectado) {
		this.tipoDeInsectoDetectado = tipoDeInsectoDetectado;
	}

	@Override
	public boolean cumple(Muestra muestra) {
		return this.getTipoDeInsectoDetectado().equals(muestra.getTipoDeInsectoDetectado());
	}
}
