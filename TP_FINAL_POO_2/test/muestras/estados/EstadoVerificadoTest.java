package muestras.estados;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import muestras.Muestra;
import muestras.Opinion;
import muestras.TipoDeInsecto;

class EstadoVerificadoTest {
	private EstadoDeVerificacion estado;

	private Muestra muestra;
	private Opinion opinion;

	@BeforeEach
	void setUp() {
		estado = new EstadoVerificado();

		muestra = mock(Muestra.class);
		opinion = mock(Opinion.class);
	}

	@Test
	void agregarOpinionNoHaceNada() {
		estado.agregarOpinion(muestra, opinion);
		verifyNoInteractions(muestra);
	}

	@Test
	void recibioOpinionDeExpertoNoHaceNada() {
		estado.recibioOpinionDeExperto(muestra);
		verifyNoInteractions(muestra);
	}

	@Test
	void obtenerResultadoActualDevuelveTipoDeInsectoDeUltimaOpinion() {
		TipoDeInsecto tipoDeInsecto = mock(TipoDeInsecto.class);

		when(muestra.obtenerUltimaOpinion()).thenReturn(opinion);
		when(opinion.getTipoDeInsecto()).thenReturn(tipoDeInsecto);

		TipoDeInsecto resultado = estado.obtenerResultadoActual(muestra);

		assertEquals(tipoDeInsecto, resultado);
	}
}
