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

class FiltroAndFiltroOrTest {
	Muestra muestra_Vinchuca_2050_Verificado;
	Muestra muestra_Vinchuca_2019_NoVerificado;
	Muestra muestra_Vinchuca_1995_NoVerificado;
	Muestra muestra_Chinche_2019_Verificado;

	TipoDeInsecto vinchuca;
	TipoDeInsecto chinche;

	LocalDate fecha2050;
	LocalDate fecha2019;
	LocalDate fecha1995;

	EstadoDeVerificacion estadoVerificado;
	EstadoDeVerificacion estadoNoVerificado;

	List<Muestra> muestras;

	@BeforeEach
	void setUp() {
		// Mocks & Stubs
		muestra_Vinchuca_2050_Verificado = mock(Muestra.class);
		muestra_Vinchuca_2019_NoVerificado = mock(Muestra.class);
		muestra_Vinchuca_1995_NoVerificado = mock(Muestra.class);
		muestra_Chinche_2019_Verificado = mock(Muestra.class);

		vinchuca = mock(TipoDeInsecto.class);
		chinche = mock(TipoDeInsecto.class);

		fecha2050 = LocalDate.of(2050, 1, 1);
		fecha2019 = LocalDate.of(2019, 1, 1);
		fecha1995 = LocalDate.of(1995, 1, 1);

		estadoVerificado = mock(EstadoVerificado.class);
		estadoNoVerificado = mock(EstadoNoVerificado.class);

		when(muestra_Vinchuca_2050_Verificado.getTipoDeInsectoDetectado()).thenReturn(vinchuca);
		when(muestra_Vinchuca_2050_Verificado.getFechaDeUltimaVotacion()).thenReturn(fecha2050);
		when(muestra_Vinchuca_2050_Verificado.getEstadoDeVerificacion()).thenReturn(estadoVerificado);

		when(muestra_Vinchuca_2019_NoVerificado.getTipoDeInsectoDetectado()).thenReturn(vinchuca);
		when(muestra_Vinchuca_2019_NoVerificado.getFechaDeUltimaVotacion()).thenReturn(fecha2019);
		when(muestra_Vinchuca_2019_NoVerificado.getEstadoDeVerificacion()).thenReturn(estadoNoVerificado);

		when(muestra_Vinchuca_1995_NoVerificado.getTipoDeInsectoDetectado()).thenReturn(vinchuca);
		when(muestra_Vinchuca_1995_NoVerificado.getFechaDeUltimaVotacion()).thenReturn(fecha1995);
		when(muestra_Vinchuca_1995_NoVerificado.getEstadoDeVerificacion()).thenReturn(estadoNoVerificado);

		when(muestra_Chinche_2019_Verificado.getTipoDeInsectoDetectado()).thenReturn(chinche);
		when(muestra_Chinche_2019_Verificado.getFechaDeUltimaVotacion()).thenReturn(fecha2019);
		when(muestra_Chinche_2019_Verificado.getEstadoDeVerificacion()).thenReturn(estadoVerificado);

		muestras = Arrays.asList(
				muestra_Vinchuca_2050_Verificado,
				muestra_Vinchuca_2019_NoVerificado,
				muestra_Vinchuca_1995_NoVerificado,
				muestra_Chinche_2019_Verificado
				);
	}

	/*
	 --------------------------------------------------------------------------------
	 Escenario 1:

	 Fecha de última votación = 2019

	 Muestras filtradas esperadas:
	 - muestra_Vinchuca_2019_NoVerificado
	 - muestra_Chinche_2019_Verificado
	 --------------------------------------------------------------------------------
	 */

	@Test
	void escenario1() {
		// SUT
		Filtro filtro = new FiltroFechaDeUltimaVotacion(fecha2019);

		// Exercise
		List<Muestra> muestrasFiltradas = filtro.filtrar(muestras);

		// Verify
		assertEquals(2, muestrasFiltradas.size());
		assertTrue(muestrasFiltradas.contains(muestra_Vinchuca_2019_NoVerificado));
		assertTrue(muestrasFiltradas.contains(muestra_Chinche_2019_Verificado));
	}

	/*
	 --------------------------------------------------------------------------------
	 Escenario 2:

	 AND(Verificado, 2019)

	 Muestras filtradas esperadas:
	 - muestra_Chinche_2019_Verificado
	 --------------------------------------------------------------------------------
	 */

	@Test
	void escenarioDos() {
		// SUT
		Filtro filtro = new FiltroAnd(
				new FiltroEstadoDeVerificacion(estadoVerificado),
				new FiltroFechaDeUltimaVotacion(fecha2019)
				);

		// Exercise
		List<Muestra> muestrasFiltradas = filtro.filtrar(muestras);

		// Verify
		assertEquals(1, muestrasFiltradas.size());
		assertTrue(muestrasFiltradas.contains(muestra_Chinche_2019_Verificado));
	}

	/*
	 --------------------------------------------------------------------------------
	 Escenario 3:

	 AND(
	   Vinchuca,
	   OR(Verificado, 2019)
	 )

	 Muestras filtradas esperadas:
	 - muestra_Vinchuca_2050_Verificado
	 - muestra_Vinchuca_2019_NoVerificado
	 --------------------------------------------------------------------------------
	 */

	@Test
	void escenarioTres() {
		// SUT
		Filtro filtro =	new FiltroAnd(
				new FiltroTipoDeInsectoDetectado(vinchuca),
				new FiltroOr(
						new FiltroEstadoDeVerificacion(estadoVerificado),
						new FiltroFechaDeUltimaVotacion(fecha2019)
						)
				);

		// Exercise
		List<Muestra> muestrasFiltradas = filtro.filtrar(muestras);

		// Verify
		assertEquals(2, muestrasFiltradas.size());
		assertTrue(muestrasFiltradas.contains(muestra_Vinchuca_2050_Verificado));
		assertTrue(muestrasFiltradas.contains(muestra_Vinchuca_2019_NoVerificado));
	}

	/*
	 --------------------------------------------------------------------------------
	 Escenario 4:

	 OR(
	   AND(Vinchuca, 2050, Verificado),
	   AND(Chinche, Verificado)
	 )

	 Muestras filtradas esperadas:
	 - muestra_Vinchuca_2050_Verificado  (Cumple las 3 condiciones del primer AND)
 	 - muestra_Chinche_2019_Verificado   (Cumple las 2 condiciones del segundo AND)
	 --------------------------------------------------------------------------------
	 */

	@Test
	void escenario4() {
		// SUT
		Filtro filtro = new FiltroOr(
				new FiltroAnd(
						new FiltroTipoDeInsectoDetectado(vinchuca),
						new FiltroFechaDeUltimaVotacion(fecha2050),
						new FiltroEstadoDeVerificacion(estadoVerificado)
						),
				new FiltroAnd(
						new FiltroTipoDeInsectoDetectado(chinche),
						new FiltroEstadoDeVerificacion(estadoVerificado)
						)
				);

		// Exercise
		List<Muestra> muestrasFiltradas = filtro.filtrar(muestras);

		// Verify
		assertEquals(2, muestrasFiltradas.size());
		assertTrue(muestrasFiltradas.contains(muestra_Vinchuca_2050_Verificado));
		assertTrue(muestrasFiltradas.contains(muestra_Chinche_2019_Verificado));
	}

	/*
	 --------------------------------------------------------------------------------
	 Escenario 5:

	 OR(
	   AND(Vinchuca, 1995, Verificado),
	   AND(Chinche, 2050, No Verificado)
	 )

	 Ninguna muestra cumple todas las condiciones de los AND,
	 por lo tanto, el OR no devuelve ninguna muestra.
	 --------------------------------------------------------------------------------
	 */

	@Test
	void escenario5() {
		// SUT
		Filtro filtro = new FiltroOr(
				new FiltroAnd(
						new FiltroTipoDeInsectoDetectado(vinchuca),
						new FiltroFechaDeUltimaVotacion(fecha1995),
						new FiltroEstadoDeVerificacion(estadoVerificado)
						),
				new FiltroAnd(
						new FiltroTipoDeInsectoDetectado(chinche),
						new FiltroFechaDeUltimaVotacion(fecha2050),
						new FiltroEstadoDeVerificacion(estadoNoVerificado)
						)
				);

		// Exercise
		List<Muestra> muestrasFiltradas = filtro.filtrar(muestras);

		// Verify
		assertTrue(muestrasFiltradas.isEmpty());
	}
}
