package busqueda.filtros.logica;

import java.util.List;

import busqueda.filtros.Filtro;
import muestras.Muestra;

public class LogicaOr implements LogicaDeComposicion {
	@Override
	public boolean evaluar(List<Filtro> filtros, Muestra muestra) {
		return filtros.stream().anyMatch(f -> f.cumple(muestra));
	}
}
