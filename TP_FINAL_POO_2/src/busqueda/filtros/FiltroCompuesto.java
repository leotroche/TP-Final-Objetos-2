package busqueda.filtros;

import java.util.ArrayList;
import java.util.List;

import busqueda.filtros.logica.LogicaDeComposicion;
import muestras.Muestra;

public class FiltroCompuesto extends Filtro {
	private List<Filtro> filtros = new ArrayList<>();
	private LogicaDeComposicion logica;

	public FiltroCompuesto(LogicaDeComposicion logica) {
		this.setLogica(logica);
	}

	public List<Filtro> getFiltros() {
		return this.filtros;
	}

	public void agregarFiltro(Filtro filtro) {
		this.getFiltros().add(filtro);
	}

	public void eliminarFiltro(Filtro filtro) {
		this.getFiltros().remove(filtro);
	}

	public LogicaDeComposicion getLogica() {
		return this.logica;
	}

	public void setLogica(LogicaDeComposicion logica) {
		this.logica = logica;
	}

	@Override
	public boolean cumple(Muestra muestra) {
		return this.getLogica().evaluar(this.getFiltros(), muestra);
	}
}
