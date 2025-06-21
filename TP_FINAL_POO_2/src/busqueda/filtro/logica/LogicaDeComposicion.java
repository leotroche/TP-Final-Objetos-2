package busqueda.filtro.logica;

import java.util.List;

import busqueda.filtro.Filtro;
import muestra.Muestra;

public interface LogicaDeComposicion {
	public boolean evaluar(List<Filtro> filtros, Muestra muestra);
}
