package busqueda.filtro.logica;

import java.util.List;

import busqueda.filtro.Filtro;
import muestra.Muestra;

public class LogicaOr implements LogicaDeComposicion {
	@Override
	public boolean evaluar(List<Filtro> filtros, Muestra muestra) {
		return filtros.stream().anyMatch(f -> f.cumple(muestra));
	}
}
