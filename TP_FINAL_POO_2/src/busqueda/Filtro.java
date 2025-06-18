package busqueda;

import java.util.List;

import muestra.Muestra;

public abstract class Filtro {
	public abstract boolean cumple(Muestra muestra);

	public final List<Muestra> filtrar(List<Muestra> muestras) {
		return muestras.stream()
				.filter(m -> this.cumple(m))
				.toList();
	}
}
