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

import muestra.EstadoDeVerificacion;
import muestra.EstadoEnProceso;
import muestra.EstadoNoVerificado;
import muestra.EstadoVerificado;
import muestra.Muestra;
import varios.TipoDeInsecto;

class FiltroORTest {
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
	void setUp() throws Exception {
		// MOCK
		muestra_Vinchuca_2019_Verificada = mock(Muestra.class);
		muestra_Vinchuca_2050_NoVerificada = mock(Muestra.class);

		vinchuca = mock(TipoDeInsecto.class);

		fecha2019 = LocalDate.of(2019, 4, 20);
		fecha2050 = LocalDate.of(2050, 4, 20);

		estadoVerificado = mock(EstadoVerificado.class);
		estadoNoVerificado = mock(EstadoNoVerificado.class);

		// STUB
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
	void filtrar_RetornaUnaListaConDosMuestras_Test() {
		// SUT
		Filtro filtro =
				new FiltroOR(
						new FiltroEstadoDeVerificacion(estadoVerificado),
						new FiltroFechaDeUltimaVotacion(fecha2050)
						);

		// EXERCISE
		List<Muestra> muestrasFiltradas = filtro.filtrar(muestras);

		// VERIFY
		assertEquals(2, muestrasFiltradas.size());
	}

	// --------------------------------------------------------------------------------

	@Test
	void filtrar_RetornaUnaListaConDosMuestras_UnaVerificada_OtraConFechaUltimaVotacion2050_Test() {
		// SUT
		Filtro filtro =
				new FiltroOR(
						new FiltroEstadoDeVerificacion(estadoVerificado),
						new FiltroFechaDeUltimaVotacion(fecha2050)
						);

		// EXERCISE
		List<Muestra> muestrasFiltradas = filtro.filtrar(muestras);

		// VERIFY
		assertTrue(muestrasFiltradas.contains(muestra_Vinchuca_2019_Verificada));
		assertTrue(muestrasFiltradas.contains(muestra_Vinchuca_2050_NoVerificada));
	}

	// --------------------------------------------------------------------------------

	@Test
	void filtrar_RetornaUnaListaVacia_Test() {
		// MOCK
		EstadoDeVerificacion estadoEnProceso = mock(EstadoEnProceso.class);
		LocalDate fechaUltimaVotacion2099 = LocalDate.of(2099, 10, 1);


		// SUT
		Filtro filtro =
				new FiltroOR(
						new FiltroEstadoDeVerificacion(estadoEnProceso),
						new FiltroFechaDeUltimaVotacion(fechaUltimaVotacion2099)
						);

		// EXERCISE
		List<Muestra> muestrasFiltradas = filtro.filtrar(muestras);

		// VERIFY
		assertEquals(0, muestrasFiltradas.size());
	}
}
