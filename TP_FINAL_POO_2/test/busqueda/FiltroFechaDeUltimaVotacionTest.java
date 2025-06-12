package busqueda;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
	void setUp() throws Exception {
		// MOCK
		muestraConFechaDeUltimaVotacion1995 = mock(Muestra.class);
		muestraConFechaDeUltimaVotacion2020 = mock(Muestra.class);

		fecha1995 = LocalDate.of(1995, 1, 1);
		fecha2020 = LocalDate.of(2020, 1, 1);

		// STUB
		when(muestraConFechaDeUltimaVotacion1995.getFechaDeUltimaVotacion()).thenReturn(fecha1995);
		when(muestraConFechaDeUltimaVotacion2020.getFechaDeUltimaVotacion()).thenReturn(fecha2020);

		muestras = Arrays.asList(muestraConFechaDeUltimaVotacion1995, muestraConFechaDeUltimaVotacion2020);
	}

	// --------------------------------------------------------------------------------

	@Test
	void filtrar_RetornaUnaListaConSoloUnaMuestra_Test() {
		// SUT
		Filtro filtro = new FiltroFechaDeUltimaVotacion(fecha1995);

		// EXERCISE
		List<Muestra> muestrasFiltradas = filtro.filtrar(muestras);

		// VERIFY
		assertEquals(1, muestrasFiltradas.size());
	}

	// --------------------------------------------------------------------------------

	@Test
	void filtrar_RetornaUnaListaConSoloUnaMuestraConEstadoVerificado_Test() {
		// SUT
		Filtro filtro = new FiltroFechaDeUltimaVotacion(fecha1995);

		// EXERCISE
		List<Muestra> muestrasFiltradas = filtro.filtrar(muestras);

		// VERIFY
		assertTrue(muestrasFiltradas.contains(muestraConFechaDeUltimaVotacion1995));
	}

	// --------------------------------------------------------------------------------

	@Test
	void filtrar_RetornaUnaListaVacia_Test() {
		// MOCK
		LocalDate fecha2050 = LocalDate.of(2050, 1, 1);

		// SUT
		Filtro filtro = new FiltroFechaDeUltimaVotacion(fecha2050);

		// EXERCISE
		List<Muestra> muestrasFiltradas = filtro.filtrar(muestras);

		// VERIFY
		assertEquals(0, muestrasFiltradas.size());
	}
}
