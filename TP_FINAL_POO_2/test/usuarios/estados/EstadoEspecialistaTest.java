package usuarios.estados;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import muestras.Muestra;
import muestras.Opinion;
import usuarios.Usuario;

class EstadoEspecialistaTest {
	private EstadoDeConocimiento estado;

	private Usuario usuario;
	private Muestra muestra;
	private Opinion opinion;

	@BeforeEach
	void setUp() {
		estado = new EstadoEspecialista();

		usuario = mock(Usuario.class);
		muestra = mock(Muestra.class);
		opinion = mock(Opinion.class);
	}

	@Test
	void opinarSobreMuestraInvocaDoAgregarOpinionYRecibioOpinionDeExperto() {
		estado.opinarSobreMuestra(usuario, muestra, opinion);

		verify(muestra).doAgregarOpinion(opinion);
		verify(muestra).recibioOpinionDeExperto();
	}

	@Test
	void opinarSobreMuestraEnProcesoInvocaDoAgregarOpinionYRecibioOpinionDeExperto() {
		estado.opinarSobreMuestraEnProceso(usuario, muestra, opinion);

		verify(muestra).doAgregarOpinion(opinion);
		verify(muestra).recibioOpinionDeExperto();
	}
}
