package busqueda;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import muestra.EstadoDeVerificacion;
import muestra.EstadoNoVerificado;
import muestra.EstadoVerificado;
import muestra.Muestra;
import varios.TipoDeInsecto;

class FiltroAndFiltroOrTest {
	Muestra muestra_Vinchuca_2050_Verificada;
	Muestra muestra_Vinchuca_2019_NoVerificada;
	Muestra muestra_Vinchuca_1995_NoVerificada;
	Muestra muestra_Chinche_2019_Verificada;

	TipoDeInsecto vinchuca;
	TipoDeInsecto chinche;

	LocalDate fecha2050;
	LocalDate fecha2019;
	LocalDate fecha1995;

	EstadoDeVerificacion estadoVerificado;
	EstadoDeVerificacion estadoNoVerificado;

	List<Muestra> muestras;

	@BeforeEach
	void setUp() throws Exception {
		// MOCK
		muestra_Vinchuca_2050_Verificada = mock(Muestra.class);
		muestra_Vinchuca_2019_NoVerificada = mock(Muestra.class);
		muestra_Vinchuca_1995_NoVerificada = mock(Muestra.class);
		muestra_Chinche_2019_Verificada = mock(Muestra.class);

		vinchuca = mock(TipoDeInsecto.class);
		chinche = mock(TipoDeInsecto.class);

		fecha2050 = LocalDate.of(2050, 1, 1);
		fecha2019 = LocalDate.of(2019, 1, 1);
		fecha1995 = LocalDate.of(1995, 1, 1);

		estadoVerificado = mock(EstadoVerificado.class);
		estadoNoVerificado = mock(EstadoNoVerificado.class);

		// STUB
		when(muestra_Vinchuca_2050_Verificada.getTipoDeInsectoDetectado()).thenReturn(vinchuca);
		when(muestra_Vinchuca_2050_Verificada.getFechaDeUltimaVotacion()).thenReturn(fecha2050);
		when(muestra_Vinchuca_2050_Verificada.getEstadoDeVerificacion()).thenReturn(estadoVerificado);

		when(muestra_Vinchuca_2019_NoVerificada.getTipoDeInsectoDetectado()).thenReturn(vinchuca);
		when(muestra_Vinchuca_2019_NoVerificada.getFechaDeUltimaVotacion()).thenReturn(fecha2019);
		when(muestra_Vinchuca_2019_NoVerificada.getEstadoDeVerificacion()).thenReturn(estadoNoVerificado);

		when(muestra_Vinchuca_1995_NoVerificada.getTipoDeInsectoDetectado()).thenReturn(vinchuca);
		when(muestra_Vinchuca_1995_NoVerificada.getFechaDeUltimaVotacion()).thenReturn(fecha1995);
		when(muestra_Vinchuca_1995_NoVerificada.getEstadoDeVerificacion()).thenReturn(estadoNoVerificado);

		when(muestra_Chinche_2019_Verificada.getTipoDeInsectoDetectado()).thenReturn(chinche);
		when(muestra_Chinche_2019_Verificada.getFechaDeUltimaVotacion()).thenReturn(fecha2019);
		when(muestra_Chinche_2019_Verificada.getEstadoDeVerificacion()).thenReturn(estadoVerificado);

		muestras = List.of(
				muestra_Vinchuca_2050_Verificada,
				muestra_Vinchuca_2019_NoVerificada,
				muestra_Vinchuca_1995_NoVerificada,
				muestra_Chinche_2019_Verificada
				);
	}

	/*
	 --------------------------------------------------------------------------------
	 CASO 1:

	 FECHA DE ÚLTIMA VOTACIÓN = 01/01/2019

	 MUESTRAS FILTRADAS:
	 - muestra_Vinchuca_2019_NoVerificada
	 - muestra_Chinche_2019_Verificada
	 --------------------------------------------------------------------------------
	 */

	@Test
	void Caso1Test() {
		// SUT
		Filtro filtro = new FiltroFechaDeUltimaVotacion(fecha2019);

		// EXERCISE
		List<Muestra> muestrasFiltradas = filtro.filtrar(muestras);

		// VERIFY
		assertEquals(2, muestrasFiltradas.size());
	}

	/*
	 --------------------------------------------------------------------------------
	 CASO 2:

	 AND(
	   ESTADO DE VERIFICACIÓN   = VERIFICADO,
	   FECHA DE ÚLTIMA VOTACIÓN = 01/01/2019
	 )

	 MUESTRAS FILTRADAS:
	 - muestra_Chinche_2019_Verificada
	 --------------------------------------------------------------------------------
	 */

	@Test
	void caso2Test() {
		// SUT
		Filtro filtro = new FiltroAND(
				new FiltroEstadoDeVerificacion(estadoVerificado),
				new FiltroFechaDeUltimaVotacion(fecha2019)
				);

		// EXERCISE
		List<Muestra> muestrasFiltradas = filtro.filtrar(muestras);

		// VERIFY
		assertEquals(1, muestrasFiltradas.size());
	}

	/*
	 --------------------------------------------------------------------------------
	 CASO 3:

	 AND(
	   TIPO DE INSECTO DETECTADO    = VINCHUCA,
	   OR(
	     ESTADO DE VERIFICACIÓN     = VERIFICADO,
	     FECHA DE ÚLTIMA VOTACIÓN   = 01/01/2019
	   )
	 )

	 MUESTRAS FILTRADAS:
	 - muestra_Vinchuca_2050_Verificada
	 - muestra_Vinchuca_2019_NoVerificada
	 --------------------------------------------------------------------------------
	 */

	@Test
	void caso3Test() {
		// SUT
		Filtro filtro =	new FiltroAND(
				new FiltroTipoDeInsectoDetectado(vinchuca),
				new FiltroOR(
						new FiltroEstadoDeVerificacion(estadoVerificado),
						new FiltroFechaDeUltimaVotacion(fecha2019)
						)
				);

		// EXERCISE
		List<Muestra> muestrasFiltradas = filtro.filtrar(muestras);

		// VERIFY
		assertEquals(2, muestrasFiltradas.size());
	}

	/*
	 --------------------------------------------------------------------------------
	 CASO 4:

	 OR(
	   AND(
	     TIPO DE INSECTO DETECTADO = VINCHUCA,
	     FECHA DE ÚLTIMA VOTACIÓN  = 01/01/2050,
	     ESTADO DE VERIFICACIÓN    = VERIFICADO
	   ),
	   AND(
	     TIPO DE INSECTO DETECTADO = CHINCHE,
	     ESTADO DE VERIFICACIÓN    = VERIFICADO
	   )
	 )

	 MUESTRAS FILTRADAS ESPERADAS:
	 - muestra_Vinchuca_2050_Verificada  (CUMPLE LAS 3 CONDICIONES DEL PRIMER AND)
	 - muestra_Chinche_2019_Verificada   (CUMPLE LAS 2 CONDICIONES DEL SEGUNDO AND)
	 --------------------------------------------------------------------------------
	 */

	@Test
	void casoComplejo1_Test() {
		// SUT
		Filtro filtro = new FiltroOR(
				new FiltroAND(
						new FiltroTipoDeInsectoDetectado(vinchuca),
						new FiltroFechaDeUltimaVotacion(fecha2050),
						new FiltroEstadoDeVerificacion(estadoVerificado)
						),
				new FiltroAND(
						new FiltroTipoDeInsectoDetectado(chinche),
						new FiltroEstadoDeVerificacion(estadoVerificado)
						)
				);

		// EXERCISE
		List<Muestra> muestrasFiltradas = filtro.filtrar(muestras);

		// VERIFY
		assertEquals(2, muestrasFiltradas.size());
	}

	/*
	 --------------------------------------------------------------------------------
	 CASO 5:

	 OR(
	   AND(
	     TIPO DE INSECTO DETECTADO = VINCHUCA,
	     FECHA ÚLTIMA VOTACIÓN     = 1995,
	     ESTADO DE VERIFICACIÓN    = VERIFICADO
	   ),
	   AND(
	     TIPO DE INSECTO DETECTADO = CHINCHE,
	     FECHA ÚLTIMA VOTACIÓN     = 2050,
	     ESTADO DE VERIFICACIÓN    = NO VERIFICADO
	   )
	 )

	 CADA AND NO TIENE MUESTRAS QUE CUMPLAN TODAS SUS CONDICIONES,
	 POR LO QUE EL OR TAMPOCO DEVUELVE NINGUNA MUESTRA.
	 --------------------------------------------------------------------------------
	 */

	@Test
	void casoNegativo_OR_con_dos_AND() {
		// SUT
		Filtro filtro = new FiltroOR(
				new FiltroAND(
						new FiltroTipoDeInsectoDetectado(vinchuca),
						new FiltroFechaDeUltimaVotacion(fecha1995),
						new FiltroEstadoDeVerificacion(estadoVerificado)
						),
				new FiltroAND(
						new FiltroTipoDeInsectoDetectado(chinche),
						new FiltroFechaDeUltimaVotacion(fecha2050),
						new FiltroEstadoDeVerificacion(estadoNoVerificado)
						)
				);

		// EXERCISE
		List<Muestra> muestrasFiltradas = filtro.filtrar(muestras);

		// VERIFY
		assertEquals(0, muestrasFiltradas.size());
	}
}
