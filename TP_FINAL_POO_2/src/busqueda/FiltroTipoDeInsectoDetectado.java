package busqueda;

import java.util.List;

import muestra.Muestra;
import varios.TipoDeInsecto;

public class FiltroTipoDeInsectoDetectado implements Filtro {
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
	public List<Muestra> filtrar(List<Muestra> muestras) {
		return muestras.stream()
				.filter(m -> m.getTipoDeInsectoDetectado().equals(this.getTipoDeInsectoDetectado()))
				.toList();
	}
}
