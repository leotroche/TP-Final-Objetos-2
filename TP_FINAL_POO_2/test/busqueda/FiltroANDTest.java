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
import muestra.EstadoNoVerificado;
import muestra.EstadoVerificado;
import muestra.Muestra;
import varios.TipoDeInsecto;

class FiltroANDTest {
	private Muestra muestra_Vinchuca_2019_Verificada;
	private Muestra muestra_Vinchuca_2050_NoVerificada;

	private TipoDeInsecto vinchuca;

	private LocalDate fecha2019;
	private LocalDate fecha2050;

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

		EstadoDeVerificacion estadoVerificado = mock(EstadoVerificado.class);
		EstadoDeVerificacion estadoNoVerificado = mock(EstadoNoVerificado.class);

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
	void filtrar_RetornaUnaListaConSoloUnaMuestra_Test() {
		// SUT
		Filtro filtro =
				new FiltroAND(
						new FiltroTipoDeInsectoDetectado(vinchuca),
						new FiltroFechaDeUltimaVotacion(fecha2019)
						);

		// EXERCISE
		List<Muestra> muestrasFiltradas = filtro.filtrar(muestras);

		// VERIFY
		assertEquals(1, muestrasFiltradas.size());
	}

	// --------------------------------------------------------------------------------

	@Test
	void filtrar_RetornaUnaListaConSoloUnaMuestraConEstadoVerificado_Test() {
		// SUT
		Filtro filtro =
				new FiltroAND(
						new FiltroTipoDeInsectoDetectado(vinchuca),
						new FiltroFechaDeUltimaVotacion(fecha2019)
						);

		// EXERCISE
		List<Muestra> muestrasFiltradas = filtro.filtrar(muestras);

		// VERIFY
		assertTrue(muestrasFiltradas.contains(muestra_Vinchuca_2019_Verificada));
	}

	// --------------------------------------------------------------------------------

	@Test
	void filtrar_RetornaUnaListaVacia_Test() {
		// MOCK
		EstadoDeVerificacion verificado = mock(EstadoVerificado.class);

		// SUT
		Filtro filtro =
				new FiltroAND(
						new FiltroTipoDeInsectoDetectado(vinchuca),
						new FiltroEstadoDeVerificacion(verificado)
						);

		// EXERCISE
		List<Muestra> muestrasFiltradas = filtro.filtrar(muestras);

		// VERIFY
		assertEquals(0, muestrasFiltradas.size());
	}
}
