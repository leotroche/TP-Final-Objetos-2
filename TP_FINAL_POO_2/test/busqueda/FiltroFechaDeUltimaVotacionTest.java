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

class FiltroFechaDeUltimaVotacionTest {
	private Muestra muestraConFechaDeUltimaVotacion1995;
	private Muestra muestraConFechaDeUltimaVotacion2020;

	private LocalDate fecha1995;
	private LocalDate fecha2020;

	private List<Muestra> muestras;

	// --------------------------------------------------------------------------------

	@BeforeEach
	void setUp() {
		// Mocks & Stubs
		muestraConFechaDeUltimaVotacion1995 = mock(Muestra.class);
		muestraConFechaDeUltimaVotacion2020 = mock(Muestra.class);

		fecha1995 = LocalDate.of(1995, 1, 1);
		fecha2020 = LocalDate.of(2020, 1, 1);

		when(muestraConFechaDeUltimaVotacion1995.getFechaDeUltimaVotacion()).thenReturn(fecha1995);
		when(muestraConFechaDeUltimaVotacion2020.getFechaDeUltimaVotacion()).thenReturn(fecha2020);

		muestras = Arrays.asList(muestraConFechaDeUltimaVotacion1995, muestraConFechaDeUltimaVotacion2020);
	}

	// --------------------------------------------------------------------------------

	@Test
	void cumple_IndicaTrueSiLaFechaCoincide() {
		// SUT
		Filtro filtro = new FiltroFechaDeUltimaVotacion(fecha1995);

		// Exercise & Verify
		assertTrue(filtro.cumple(muestraConFechaDeUltimaVotacion1995));
	}

	// --------------------------------------------------------------------------------

	@Test
	void cumple_IndicaFalseSiLaFechaNoCoincide() {
		// SUT
		Filtro filtro = new FiltroFechaDeUltimaVotacion(fecha1995);

		// Exercise & Verify
		assertFalse(filtro.cumple(muestraConFechaDeUltimaVotacion2020));
	}

	// --------------------------------------------------------------------------------

	@Test
	void filtrar_DescribeLaListaDeMuestrasQueCumplenElFiltro() {
		// SUT
		Filtro filtro = new FiltroFechaDeUltimaVotacion(fecha1995);

		// Exercise
		List<Muestra> muestrasFiltradas = filtro.filtrar(muestras);

		// Verify
		assertEquals(1, muestrasFiltradas.size());
		assertTrue(muestrasFiltradas.contains(muestraConFechaDeUltimaVotacion1995));
	}

	// --------------------------------------------------------------------------------

	@Test
	void filtrar_DescribeUnaListaVaciaSiNingunaMuestraCumpleElFiltro() {
		// Fecha futura que no coincide con ninguna muestra
		LocalDate fecha2050 = LocalDate.of(2050, 1, 1);

		// SUT
		Filtro filtro = new FiltroFechaDeUltimaVotacion(fecha2050);

		// Exercise
		List<Muestra> muestrasFiltradas = filtro.filtrar(muestras);

		// Verify
		assertTrue(muestrasFiltradas.isEmpty());
	}
}
