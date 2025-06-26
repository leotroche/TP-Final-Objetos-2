package busqueda.filtros.logica;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import busqueda.filtros.Filtro;
import busqueda.filtros.logica.LogicaDeComposicion;
import busqueda.filtros.logica.LogicaOr;
import muestras.Muestra;

class LogicaOrTest {
	private LogicaDeComposicion logica;

	private Muestra muestra;

	private Filtro filtro_2025;
	private Filtro filtro_chinche;

	@BeforeEach
	void setUp() {
		logica = new LogicaOr();

		// Muestras simuladas
		muestra = mock(Muestra.class);

		// Filtros simulados
		filtro_2025 = mock(Filtro.class);
		filtro_chinche = mock(Filtro.class);
	}

	// ------------------------------------------------------------

	@Test
	void retornaTrueSiTodosLosFiltrosSeCumplen() {
		List<Filtro> filtros = List.of(filtro_chinche, filtro_2025);

		when(filtro_chinche.cumple(muestra)).thenReturn(true);
		when(filtro_2025.cumple(muestra)).thenReturn(true);

		assertTrue(logica.evaluar(filtros, muestra));
	}

	// ------------------------------------------------------------

	@Test
	void retornaTrueSiAlMenosUnFiltroSeCumple() {
		List<Filtro> filtros = List.of(filtro_chinche, filtro_2025);

		when(filtro_chinche.cumple(muestra)).thenReturn(true);
		when(filtro_2025.cumple(muestra)).thenReturn(false);

		assertTrue(logica.evaluar(filtros, muestra));
	}

	// ------------------------------------------------------------

	@Test
	void retornaFalseSiNoHayFiltros() {
		List<Filtro> filtros = List.of();

		assertFalse(logica.evaluar(filtros, muestra));
	}

	// ------------------------------------------------------------

	@Test
	void retornaFalseSiNingunFiltroSeCumple() {
		List<Filtro> filtros = List.of(filtro_chinche, filtro_2025);

		when(filtro_chinche.cumple(muestra)).thenReturn(false);
		when(filtro_2025.cumple(muestra)).thenReturn(false);

		assertFalse(logica.evaluar(filtros, muestra));
	}
}
