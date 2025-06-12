package busqueda;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
	void setUp() throws Exception {
		// MOCK
		muestraConEstadoVerificado = mock(Muestra.class);
		muestraConEstadoNoVerificado = mock(Muestra.class);

		estadoVerificado = mock(EstadoVerificado.class);
		estadoNoVerificado = mock(EstadoNoVerificado.class);

		// STUB
		when(muestraConEstadoVerificado.getEstadoDeVerificacion()).thenReturn(estadoVerificado);
		when(muestraConEstadoNoVerificado.getEstadoDeVerificacion()).thenReturn(estadoNoVerificado);

		muestras = Arrays.asList(muestraConEstadoVerificado, muestraConEstadoNoVerificado);
	}

	// --------------------------------------------------------------------------------

	@Test
	void filtrar_RetornaUnaListaConSoloUnaMuestra_Test() {
		// SUT
		Filtro filtro = new FiltroEstadoDeVerificacion(estadoVerificado);

		// EXERCISE
		List<Muestra> muestrasFiltradas = filtro.filtrar(muestras);

		// VERIFY
		assertEquals(1, muestrasFiltradas.size());
	}

	// --------------------------------------------------------------------------------

	@Test
	void filtrar_RetornaUnaListaConSoloUnaMuestraConEstadoVerificado_Test() {
		// SUT
		Filtro filtro = new FiltroEstadoDeVerificacion(estadoVerificado);

		// EXERCISE
		List<Muestra> muestrasFiltradas = filtro.filtrar(muestras);

		// VERIFY
		assertTrue(muestrasFiltradas.contains(muestraConEstadoVerificado));
	}

	// --------------------------------------------------------------------------------

	@Test
	void filtrar_RetornaUnaListaVacia_Test() {
		// MOCK
		EstadoDeVerificacion estadoEnProceso = mock(EstadoEnProceso.class);

		// SUT
		Filtro filtro = new FiltroEstadoDeVerificacion(estadoEnProceso);

		// EXERCISE
		List<Muestra> muestrasFiltradas = filtro.filtrar(muestras);

		// VERIFY
		assertEquals(0, muestrasFiltradas.size());
	}
}
