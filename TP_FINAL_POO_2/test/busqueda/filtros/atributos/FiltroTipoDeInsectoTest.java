package busqueda.filtros.atributos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import busqueda.filtros.Filtro;
import busqueda.filtros.atributos.FiltroTipoDeInsecto;
import muestras.Muestra;
import muestras.TipoDeInsecto;

class FiltroTipoDeInsectoTest {
	private TipoDeInsecto vinchuca;

	private Muestra muestra;
	private List<Muestra> muestras;

	@BeforeEach
	void setUp() {
		// Muestras simuladas
		muestra = mock(Muestra.class);
		muestras = List.of(
				mock(Muestra.class),
				mock(Muestra.class),
				muestra,
				mock(Muestra.class),
				mock(Muestra.class)
				);

		// Tipos de insecto simulados
		vinchuca = mock(TipoDeInsecto.class);
	}

	// --------------------------------------------------------------------------------

	@Test
	void retornaTrueSiElTipoDeInsectoCoincide() {
		Filtro filtro = new FiltroTipoDeInsecto(vinchuca);

		when(muestra.getTipoDeInsecto()).thenReturn(vinchuca);

		assertTrue(filtro.cumple(muestra));
	}

	// --------------------------------------------------------------------------------

	@Test
	void retornaFalseSiElTipoDeInsectoNoCoincide() {
		Filtro filtro = new FiltroTipoDeInsecto(vinchuca);

		assertFalse(filtro.cumple(muestra));
	}

	// --------------------------------------------------------------------------------

	@Test
	void filtraMuestrasQueCoincidenConElTipoDeInsecto() {
		Filtro filtro = new FiltroTipoDeInsecto(vinchuca);

		// Simulamos que la primera muestra cumple el filtro
		when(muestra.getTipoDeInsecto()).thenReturn(vinchuca);

		List<Muestra> muestrasFiltradas = filtro.filtrar(muestras);

		assertEquals(1, muestrasFiltradas.size());
		assertTrue(muestrasFiltradas.contains(muestra));
	}

	// --------------------------------------------------------------------------------

	@Test
	void retornaListaVaciaSiNingunaMuestraCoincide() {
		Filtro filtro = new FiltroTipoDeInsecto(vinchuca);

		List<Muestra> muestrasFiltradas = filtro.filtrar(muestras);

		assertTrue(muestrasFiltradas.isEmpty());
	}
}
