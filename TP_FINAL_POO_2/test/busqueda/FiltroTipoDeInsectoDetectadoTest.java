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
	void setUp() {
		// Mocks & Stubs
		muestraConTipoDeInsectoDetectadoVinchuca = mock(Muestra.class);
		muestraConTipoDeInsectoDetectadoChinche = mock(Muestra.class);

		vinchuca = mock(TipoDeInsecto.class);
		chinche = mock(TipoDeInsecto.class);

		when(muestraConTipoDeInsectoDetectadoVinchuca.getTipoDeInsectoDetectado()).thenReturn(vinchuca);
		when(muestraConTipoDeInsectoDetectadoChinche.getTipoDeInsectoDetectado()).thenReturn(chinche);

		muestras = Arrays.asList(
				muestraConTipoDeInsectoDetectadoVinchuca,
				muestraConTipoDeInsectoDetectadoChinche
				);
	}

	// --------------------------------------------------------------------------------

	@Test
	void cumple_IndicaTrueSiElTipoDeInsectoDetectadoCoincide() {
		// SUT
		Filtro filtro = new FiltroTipoDeInsectoDetectado(vinchuca);

		// Exercise & Verify
		assertTrue(filtro.cumple(muestraConTipoDeInsectoDetectadoVinchuca));
	}

	// --------------------------------------------------------------------------------

	@Test
	void cumple_IndicaFalseSiElTipoDeInsectoDetectadoNoCoincide() {
		// SUT
		Filtro filtro = new FiltroTipoDeInsectoDetectado(vinchuca);

		// Exercise & Verify
		assertFalse(filtro.cumple(muestraConTipoDeInsectoDetectadoChinche));
	}

	// --------------------------------------------------------------------------------

	@Test
	void filtrar_RetornaUnaListaConSoloUnaMuestraConEstadoVerificado_Test() {
		// SUT
		Filtro filtro = new FiltroTipoDeInsectoDetectado(vinchuca);

		// Exercise
		List<Muestra> muestrasFiltradas = filtro.filtrar(muestras);

		// Verify
		assertEquals(1, muestrasFiltradas.size());
		assertTrue(muestrasFiltradas.contains(muestraConTipoDeInsectoDetectadoVinchuca));
	}

	// --------------------------------------------------------------------------------

	@Test
	void filtrar_DescribeUnaListaVaciaSiNingunaMuestraCumpleElFiltro() {
		// Tipo de insecto que no coincide con ninguna muestra
		TipoDeInsecto desconocido = mock(TipoDeInsecto.class);

		// SUT
		Filtro filtro = new FiltroTipoDeInsectoDetectado(desconocido);

		// Exercise
		List<Muestra> muestrasFiltradas = filtro.filtrar(muestras);

		// Verify
		assertTrue(muestrasFiltradas.isEmpty());
	}
}
