package busqueda.filtros.logica;

import java.util.List;

import busqueda.filtros.Filtro;
import muestras.Muestra;

public interface LogicaDeComposicion {
	public boolean evaluar(List<Filtro> filtros, Muestra muestra);
}
