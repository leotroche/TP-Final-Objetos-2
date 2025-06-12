package busqueda;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import muestra.Muestra;
import varios.TipoDeInsecto;

class FiltroTipoDeInsectoDetectadoTest {
	private Muestra muestraConTipoDeInsectoDetectadoVinchuca;
	private Muestra muestraConTipoDeInsectoDetectadoChinche;

	private TipoDeInsecto vinchuca;
	private TipoDeInsecto chinche;

	private List<Muestra> muestras;

	// --------------------------------------------------------------------------------

	@BeforeEach
	void setUp() throws Exception {
		// MOCK
		muestraConTipoDeInsectoDetectadoVinchuca = mock(Muestra.class);
		muestraConTipoDeInsectoDetectadoChinche = mock(Muestra.class);

		vinchuca = mock(TipoDeInsecto.class);
		chinche = mock(TipoDeInsecto.class);

		// STUB
		when(muestraConTipoDeInsectoDetectadoVinchuca.getTipoDeInsectoDetectado()).thenReturn(vinchuca);
		when(muestraConTipoDeInsectoDetectadoChinche.getTipoDeInsectoDetectado()).thenReturn(chinche);

		muestras = Arrays.asList(
				muestraConTipoDeInsectoDetectadoVinchuca,
				muestraConTipoDeInsectoDetectadoChinche
				);
	}

	// --------------------------------------------------------------------------------

	@Test
	void filtrar_RetornaUnaListaConSoloUnaMuestra_Test() {
		// SUT
		Filtro filtro = new FiltroTipoDeInsectoDetectado(vinchuca);

		// EXERCISE
		List<Muestra> muestrasFiltradas = filtro.filtrar(muestras);

		// VERIFY
		assertEquals(1, muestrasFiltradas.size());
	}

	// --------------------------------------------------------------------------------

	@Test
	void filtrar_RetornaUnaListaConSoloUnaMuestraConEstadoVerificado_Test() {
		// SUT
		Filtro filtro = new FiltroTipoDeInsectoDetectado(vinchuca);

		// EXERCISE
		List<Muestra> muestrasFiltradas = filtro.filtrar(muestras);

		// VERIFY
		assertTrue(muestrasFiltradas.contains(muestraConTipoDeInsectoDetectadoVinchuca));
	}

	// --------------------------------------------------------------------------------

	@Test
	void filtrar_RetornaUnaListaVacia_Test() {
		// MOCK
		TipoDeInsecto desconocido = mock(TipoDeInsecto.class);

		// SUT
		Filtro filtro = new FiltroTipoDeInsectoDetectado(desconocido);

		// EXERCISE
		List<Muestra> muestrasFiltradas = filtro.filtrar(muestras);

		// VERIFY
		assertEquals(0, muestrasFiltradas.size());
	}
}
