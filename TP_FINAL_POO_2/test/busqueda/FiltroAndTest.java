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

import muestra.EstadoDeVerificacion;
import muestra.EstadoNoVerificado;
import muestra.EstadoVerificado;
import muestra.Muestra;
import varios.TipoDeInsecto;

class FiltroAndTest {
	private Muestra muestra_Vinchuca_2019_Verificada;
	private Muestra muestra_Vinchuca_2050_NoVerificada;

	private TipoDeInsecto vinchuca;

	private LocalDate fecha2019;
	private LocalDate fecha2050;

	private List<Muestra> muestras;

	// --------------------------------------------------------------------------------

	@BeforeEach
	void setUp() {
		// Mocks & Stubs
		muestra_Vinchuca_2019_Verificada = mock(Muestra.class);
		muestra_Vinchuca_2050_NoVerificada = mock(Muestra.class);

		vinchuca = mock(TipoDeInsecto.class);

		fecha2019 = LocalDate.of(2019, 4, 20);
		fecha2050 = LocalDate.of(2050, 4, 20);

		EstadoDeVerificacion estadoVerificado = mock(EstadoVerificado.class);
		EstadoDeVerificacion estadoNoVerificado = mock(EstadoNoVerificado.class);

		when(muestra_Vinchuca_2019_Verificada.getTipoDeInsectoDetectado()).thenReturn(vinchuca);
		when(muestra_Vinchuca_2019_Verificada.getFechaDeUltimaVotacion()).thenReturn(fecha2019);
		when(muestra_Vinchuca_2019_Verificada.getEstadoDeVerificacion()).thenReturn(estadoVerificado);

		when(muestra_Vinchuca_2050_NoVerificada.getTipoDeInsectoDetectado()).thenReturn(vinchuca);
		when(muestra_Vinchuca_2050_NoVerificada.getFechaDeUltimaVotacion()).thenReturn(fecha2050);
		when(muestra_Vinchuca_2050_NoVerificada.getEstadoDeVerificacion()).thenReturn(estadoNoVerificado);

		muestras = Arrays.asList(muestra_Vinchuca_2019_Verificada, muestra_Vinchuca_2050_NoVerificada);
	}

	// --------------------------------------------------------------------------------

	@Test
	void cumple_IndicaTrueSiLaMuestraCumpleTodosLosFiltros() {
		// SUT
		Filtro filtro = new FiltroAnd(
				new FiltroTipoDeInsectoDetectado(vinchuca),
				new FiltroFechaDeUltimaVotacion(fecha2019)
				);

		// Exercise & Verify
		assertTrue(filtro.cumple(muestra_Vinchuca_2019_Verificada));
	}

	// --------------------------------------------------------------------------------

	@Test
	void cumple_IndicaFalseSiLaMuestraNoCumpleTodosLosFiltros() {
		// SUT
		Filtro filtro = new FiltroAnd(
				new FiltroTipoDeInsectoDetectado(vinchuca),
				new FiltroFechaDeUltimaVotacion(fecha2019)
				);

		// Exercise & Verify
		assertFalse(filtro.cumple(muestra_Vinchuca_2050_NoVerificada));
	}

	// --------------------------------------------------------------------------------

	@Test
	void filtrar_DescribeUnaListaConMuestrasQueCumplenTodosLosFiltros() {
		// SUT
		Filtro filtro =
				new FiltroAnd(
						new FiltroTipoDeInsectoDetectado(vinchuca),
						new FiltroFechaDeUltimaVotacion(fecha2019)
						);

		// Exercise
		List<Muestra> muestrasFiltradas = filtro.filtrar(muestras);

		// Verify
		assertEquals(1, muestrasFiltradas.size());
		assertTrue(muestrasFiltradas.contains(muestra_Vinchuca_2019_Verificada));
	}

	// --------------------------------------------------------------------------------

	@Test
	void filtrar_DescribeUnaListaVaciaSiNingunaMuestraCumpleTodosLosFiltros() {
		// Estado que no coincide con ninguna muestra
		EstadoDeVerificacion verificado = mock(EstadoVerificado.class);

		// SUT
		Filtro filtro =
				new FiltroAnd(
						new FiltroTipoDeInsectoDetectado(vinchuca),
						new FiltroEstadoDeVerificacion(verificado)
						);

		// Exercise
		List<Muestra> muestrasFiltradas = filtro.filtrar(muestras);

		// Verify
		assertTrue(muestrasFiltradas.isEmpty());
	}
}
