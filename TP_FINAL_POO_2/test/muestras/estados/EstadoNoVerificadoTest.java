package muestras.estados;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import muestras.Muestra;
import muestras.Opinion;
import usuarios.Usuario;

class EstadoNoVerificadoTest {
	private EstadoDeVerificacion estado;

	private Muestra muestra;
	private Opinion opinion;
	private Usuario usuario;

	@BeforeEach
	void setUp() {
		estado = new EstadoNoVerificado();

		muestra = mock(Muestra.class);
		opinion = mock(Opinion.class);
		usuario = mock(Usuario.class);

		when(opinion.getAutor()).thenReturn(usuario);
	}

	@Test
	void agregarOpinionInvocaMetodoDeUsuarioOpinarSobreMuestra() {
		estado.agregarOpinion(muestra, opinion);
		verify(usuario).opinarSobreMuestra(muestra, opinion);
	}

	@Test
	void recibioOpinionDeExpertoCambiaEstadoAMuestraEnProceso() {
		estado.recibioOpinionDeExperto(muestra);
		verify(muestra).setEstadoDeVerificacion(any());
	}

	@Test
	void obtenerResultadoActualDevuelveTipoMasFrecuenteEnOpiniones() {
		estado.obtenerResultadoActual(muestra);
		verify(muestra).tipoMasFrecuenteEnOpiniones();
	}
}
