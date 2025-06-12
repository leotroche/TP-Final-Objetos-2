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

class FiltroFechaDeCreacionTest {
	private Muestra muestraConFechaDeCreacion1995;
	private Muestra muestraConFechaDeCreacion2020;

	private LocalDate fecha1995;
	private LocalDate fecha2020;

	private List<Muestra> muestras;

	// --------------------------------------------------------------------------------

	@BeforeEach
	void setUp() throws Exception {
		// MOCK
		muestraConFechaDeCreacion1995 = mock(Muestra.class);
		muestraConFechaDeCreacion2020 = mock(Muestra.class);

		fecha1995 = LocalDate.of(1995, 1, 1);
		fecha2020 = LocalDate.of(2020, 1, 1);

		// STUB
		when(muestraConFechaDeCreacion1995.getFechaDeCreacion()).thenReturn(fecha1995);
		when(muestraConFechaDeCreacion2020.getFechaDeCreacion()).thenReturn(fecha2020);

		muestras = Arrays.asList(muestraConFechaDeCreacion1995, muestraConFechaDeCreacion2020);
	}

	// --------------------------------------------------------------------------------

	@Test
	void filtrar_RetornaUnaListaConSoloUnaMuestra_Test() {
		// SUT
		Filtro filtro = new FiltroFechaDeCreacion(fecha1995);

		// EXERCISE
		List<Muestra> muestrasFiltradas = filtro.filtrar(muestras);

		// VERIFY
		assertEquals(1, muestrasFiltradas.size());
	}

	// --------------------------------------------------------------------------------

	@Test
	void filtrar_RetornaUnaListaConSoloUnaMuestraConEstadoVerificado_Test() {
		// SUT
		Filtro filtro = new FiltroFechaDeCreacion(fecha1995);

		// EXERCISE
		List<Muestra> muestrasFiltradas = filtro.filtrar(muestras);

		// VERIFY
		assertTrue(muestrasFiltradas.contains(muestraConFechaDeCreacion1995));
	}

	// --------------------------------------------------------------------------------

	@Test
	void filtrar_RetornaUnaListaVacia_Test() {
		// MOCK
		LocalDate fecha2050 = LocalDate.of(2050, 1, 1);

		// SUT
		Filtro filtro = new FiltroFechaDeCreacion(fecha2050);

		// EXERCISE
		List<Muestra> muestrasFiltradas = filtro.filtrar(muestras);

		// VERIFY
		assertEquals(0, muestrasFiltradas.size());
	}
}
