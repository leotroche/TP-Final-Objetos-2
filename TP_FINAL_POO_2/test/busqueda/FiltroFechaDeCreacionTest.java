package busqueda;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import muestra.Muestra;

class FiltroFechaDeCreacionTest {
	private Muestra muestraConFechaDeCreacion1995;
	private Muestra muestraConFechaDeCreacion2020;

	private LocalDate fecha1995;
	private LocalDate fecha2020;

	private List<Muestra> muestras;

	// --------------------------------------------------------------------------------

	@BeforeEach
	void setUp() {
		// Mocks & Stubs
		muestraConFechaDeCreacion1995 = mock(Muestra.class);
		muestraConFechaDeCreacion2020 = mock(Muestra.class);

		fecha1995 = LocalDate.of(1995, 1, 1);
		fecha2020 = LocalDate.of(2020, 1, 1);

		when(muestraConFechaDeCreacion1995.getFechaDeCreacion()).thenReturn(fecha1995);
		when(muestraConFechaDeCreacion2020.getFechaDeCreacion()).thenReturn(fecha2020);

		muestras = Arrays.asList(muestraConFechaDeCreacion1995, muestraConFechaDeCreacion2020);
	}

	// --------------------------------------------------------------------------------

	@Test
	void cumple_IndicaTrueSiLaFechaCoincide() {
		// SUT
		Filtro filtro = new FiltroFechaDeCreacion(fecha1995);

		// Exercise & Verify
		assertTrue(filtro.cumple(muestraConFechaDeCreacion1995));
	}

	// --------------------------------------------------------------------------------

	@Test
	void cumple_IndicaFalseSiLaFechaNoCoincide() {
		// SUT
		Filtro filtro = new FiltroFechaDeCreacion(fecha1995);

		// Exercise & Verify
		assertFalse(filtro.cumple(muestraConFechaDeCreacion2020));
	}

	// --------------------------------------------------------------------------------

	@Test
	void filtrar_DescribeLaListaDeMuestrasQueCumplenElFiltro() {
		// SUT
		Filtro filtro = new FiltroFechaDeCreacion(fecha1995);

		// Exercise
		List<Muestra> muestrasFiltradas = filtro.filtrar(muestras);

		// Verify
		assertEquals(1, muestrasFiltradas.size());
		assertTrue(muestrasFiltradas.contains(muestraConFechaDeCreacion1995));
	}

	// --------------------------------------------------------------------------------

	@Test
	void filtrar_DescribeUnaListaVaciaSiNingunaMuestraCumpleElFiltro() {
		// Fecha futura que no coincide con ninguna muestra
		LocalDate fecha2050 = LocalDate.of(2050, 1, 1);

		// SUT
		Filtro filtro = new FiltroFechaDeCreacion(fecha2050);

		// Exercise
		List<Muestra> muestrasFiltradas = filtro.filtrar(muestras);

		// Verify
		assertTrue(muestrasFiltradas.isEmpty());
	}
}
