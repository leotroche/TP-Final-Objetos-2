package busqueda;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import muestra.EstadoDeVerificacion;
import muestra.EstadoEnProceso;
import muestra.EstadoNoVerificado;
import muestra.EstadoVerificado;
import muestra.Muestra;

class FiltroEstadoVerificacionTest {
	private Muestra muestraConEstadoVerificado;
	private Muestra muestraConEstadoNoVerificado;

	private EstadoDeVerificacion estadoVerificado;
	private EstadoDeVerificacion estadoNoVerificado;

	private List<Muestra> muestras;

	// --------------------------------------------------------------------------------

	@BeforeEach
	void setUp() {
		// Mocks & Stubs
		muestraConEstadoVerificado = mock(Muestra.class);
		muestraConEstadoNoVerificado = mock(Muestra.class);

		estadoVerificado = mock(EstadoVerificado.class);
		estadoNoVerificado = mock(EstadoNoVerificado.class);

		when(muestraConEstadoVerificado.getEstadoDeVerificacion()).thenReturn(estadoVerificado);
		when(muestraConEstadoNoVerificado.getEstadoDeVerificacion()).thenReturn(estadoNoVerificado);

		muestras = Arrays.asList(muestraConEstadoVerificado, muestraConEstadoNoVerificado);
	}

	// --------------------------------------------------------------------------------

	@Test
	void cumple_IndicaTrueSiElEstadoCoincide() {
		// SUT
		Filtro filtro = new FiltroEstadoDeVerificacion(estadoVerificado);

		// Exercise & Verify
		assertTrue(filtro.cumple(muestraConEstadoVerificado));
	}

	// --------------------------------------------------------------------------------

	@Test
	void cumple_IndicaFalseSiElEstadoNoCoincide() {
		// SUT
		Filtro filtro = new FiltroEstadoDeVerificacion(estadoVerificado);

		// Exercise & Verify
		assertFalse(filtro.cumple(muestraConEstadoNoVerificado));
	}

	// --------------------------------------------------------------------------------

	@Test
	void filtrar_DescribeLaListaDeMuestrasQueCumplenElFiltro() {
		// SUT
		Filtro filtro = new FiltroEstadoDeVerificacion(estadoVerificado);

		// Exercise
		List<Muestra> muestrasFiltradas = filtro.filtrar(muestras);

		// Verify
		assertEquals(1, muestrasFiltradas.size());
		assertTrue(muestrasFiltradas.contains(muestraConEstadoVerificado));
	}

	// --------------------------------------------------------------------------------

	@Test
	void filtrar_DescribeUnaListaVaciaSiNingunaMuestraCumpleElFiltro() {
		// Estado que no coincide con ninguna muestra
		EstadoDeVerificacion estadoEnProceso = mock(EstadoEnProceso.class);

		// SUT
		Filtro filtro = new FiltroEstadoDeVerificacion(estadoEnProceso);

		// Exercise
		List<Muestra> muestrasFiltradas = filtro.filtrar(muestras);

		// Verify
		assertTrue(muestrasFiltradas.isEmpty());
	}
}
