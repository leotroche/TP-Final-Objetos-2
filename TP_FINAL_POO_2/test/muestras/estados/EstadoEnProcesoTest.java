package muestras.estados;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import muestras.Muestra;
import muestras.Opinion;
import muestras.TipoDeInsecto;
import usuarios.Usuario;

class EstadoEnProcesoTest {
	private EstadoDeVerificacion estado;

	private Muestra muestra;
	private Opinion opinion;
	private Usuario usuario;

	@BeforeEach
	void setUp() {
		estado = new EstadoEnProceso();

		muestra = mock(Muestra.class);
		opinion = mock(Opinion.class);
		usuario = mock(Usuario.class);

		when(opinion.getAutor()).thenReturn(usuario);
	}

	@Test
	void agregarOpinionInvocaMetodoDeUsuarioOpinarSobreMuestra() {
		estado.agregarOpinion(muestra, opinion);
		verify(usuario).opinarSobreMuestraEnProceso(muestra, opinion);
	}

	@Test
	void recibioOpinionDeExpertoInvocaMetodoVerificarMuestraSiCorresponde() {
		estado.recibioOpinionDeExperto(muestra);
		verify(muestra).verificarMuestraSiCorresponde();
	}

	@Test
	void obtenerResultadoActualDevuelveTipoDeInsectoSiHayUnaSolaOpinionDeExperto() {
		when(muestra.opinionesDeExpertos()).thenReturn(List.of(opinion));

		TipoDeInsecto tipoDeInsecto = mock(TipoDeInsecto.class);

		when(opinion.getEsOpinionDeExperto()).thenReturn(true);
		when(opinion.getTipoDeInsecto()).thenReturn(tipoDeInsecto);
		when(muestra.obtenerUltimaOpinion()).thenReturn(opinion);

		TipoDeInsecto resultado = estado.obtenerResultadoActual(muestra);

		assertEquals(tipoDeInsecto, resultado);
	}

	@Test
	void obtenerResultadoActualDevuelveTipoDeInsectoNingunaSiHayMasDeUnaOpinionDeExperto() {
		Opinion opinion2 = mock(Opinion.class);
		when(opinion2.getEsOpinionDeExperto()).thenReturn(true);
		when(opinion2.getTipoDeInsecto()).thenReturn(TipoDeInsecto.VINCHUCA_GUASAYANA);

		when(muestra.opinionesDeExpertos()).thenReturn(List.of(opinion, opinion2));

		TipoDeInsecto resultado = estado.obtenerResultadoActual(muestra);

		assertEquals(TipoDeInsecto.NINGUNA, resultado);
	}
}
