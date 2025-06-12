package busqueda;

import java.util.List;

import muestra.Muestra;

public interface Filtro {
	public List<Muestra> filtrar(List<Muestra> muestras);
}
