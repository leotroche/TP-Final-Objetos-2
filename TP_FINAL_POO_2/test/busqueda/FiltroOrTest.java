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
import muestra.EstadoEnProceso;
import muestra.EstadoNoVerificado;
import muestra.EstadoVerificado;
import muestra.Muestra;
import varios.TipoDeInsecto;

class FiltroOrTest {
	private Muestra muestra_Vinchuca_2019_Verificada;
	private Muestra muestra_Vinchuca_2050_NoVerificada;

	private TipoDeInsecto vinchuca;

	private LocalDate fecha2019;
	private LocalDate fecha2050;

	private EstadoDeVerificacion estadoVerificado;
	private EstadoDeVerificacion estadoNoVerificado;

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

		estadoVerificado = mock(EstadoVerificado.class);
		estadoNoVerificado = mock(EstadoNoVerificado.class);

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
	void cumple_IndicaTrueSiLaMuestraCumpleAlgunoDeLosFiltros() {
		// SUT
		Filtro filtro = new FiltroOr(
				new FiltroEstadoDeVerificacion(estadoVerificado),
				new FiltroFechaDeUltimaVotacion(fecha2050)
				);

		// Exercise & Verify
		assertTrue(filtro.cumple(muestra_Vinchuca_2019_Verificada));
		assertTrue(filtro.cumple(muestra_Vinchuca_2050_NoVerificada));
	}

	// --------------------------------------------------------------------------------

	@Test
	void cumple_IndicaFalseSiLaMuestraNoCumpleNingunoDeLosFiltros() {
		// Estado y fecha que no coinciden con ninguna muestra
		EstadoDeVerificacion estadoEnProceso = mock(EstadoEnProceso.class);
		LocalDate fechaUltimaVotacion2099 = LocalDate.of(2099, 10, 1);

		// SUT
		Filtro filtro = new FiltroOr(
				new FiltroEstadoDeVerificacion(estadoEnProceso),
				new FiltroFechaDeUltimaVotacion(fechaUltimaVotacion2099)
				);

		// Exercise & Verify
		assertFalse(filtro.cumple(muestra_Vinchuca_2019_Verificada));
		assertFalse(filtro.cumple(muestra_Vinchuca_2050_NoVerificada));
	}

	// --------------------------------------------------------------------------------

	@Test
	void filtrar_DescribeUnaListaConMuestrasQueCumplenAlgunoDeLosFiltros() {
		// SUT
		Filtro filtro =
				new FiltroOr(
						new FiltroEstadoDeVerificacion(estadoVerificado),
						new FiltroFechaDeUltimaVotacion(fecha2050)
						);

		// Exercise
		List<Muestra> muestrasFiltradas = filtro.filtrar(muestras);

		// Verify
		assertEquals(2, muestrasFiltradas.size());
		assertTrue(muestrasFiltradas.contains(muestra_Vinchuca_2019_Verificada));
		assertTrue(muestrasFiltradas.contains(muestra_Vinchuca_2050_NoVerificada));
	}

	// --------------------------------------------------------------------------------

	@Test
	void filtrar_DescribeUnaListaVaciaSiNingunaMuestraCumpleAlgunoDeLosFiltros() {
		// Estado y fecha que no coinciden con ninguna muestra
		EstadoDeVerificacion estadoEnProceso = mock(EstadoEnProceso.class);
		LocalDate fechaUltimaVotacion2099 = LocalDate.of(2099, 10, 1);

		// SUT
		Filtro filtro =
				new FiltroOr(
						new FiltroEstadoDeVerificacion(estadoEnProceso),
						new FiltroFechaDeUltimaVotacion(fechaUltimaVotacion2099)
						);

		// Exercise
		List<Muestra> muestrasFiltradas = filtro.filtrar(muestras);

		// Verify
		assertTrue(muestrasFiltradas.isEmpty());
	}
}
