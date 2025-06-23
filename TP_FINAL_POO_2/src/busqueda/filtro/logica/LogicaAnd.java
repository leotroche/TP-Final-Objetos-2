package busqueda.filtro.logica;

import java.util.List;

import busqueda.filtro.Filtro;
import muestra.Muestra;

public class LogicaAnd implements LogicaDeComposicion {
	@Override
	public boolean evaluar(List<Filtro> filtros, Muestra muestra) {
		return filtros.stream().allMatch(f -> f.cumple(muestra));
	}
}
