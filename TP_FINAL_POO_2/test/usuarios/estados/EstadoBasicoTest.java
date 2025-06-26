package usuarios.estados;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import muestras.Muestra;
import muestras.Opinion;
import usuarios.Usuario;

class EstadoBasicoTest {
	private EstadoDeConocimiento estado;

	private Usuario usuario;
	private Muestra muestra;
	private Opinion opinion;

	@BeforeEach
	void setUp() {
		estado = new EstadoBasico();

		usuario = mock(Usuario.class);
		muestra = mock(Muestra.class);
		opinion = mock(Opinion.class);
	}

	@Test
	void opinarSobreMuestraInvocaDoAgregarOpinion() {
		estado.opinarSobreMuestra(usuario, muestra, opinion);
		verify(muestra).doAgregarOpinion(opinion);
	}

	@Test
	void opinarSobreMuestraEnProcesoNoHaceNada() {
		estado.opinarSobreMuestraEnProceso(usuario, muestra, opinion);
		verifyNoInteractions(muestra);
	}
}
