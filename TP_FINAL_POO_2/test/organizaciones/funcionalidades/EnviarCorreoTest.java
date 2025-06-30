package organizaciones.funcionalidades;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import muestras.Muestra;
import organizaciones.Organizacion;
import zonas.ZonaDeCobertura;

class EnviarCorreoTest {
	private FuncionalidadExterna enviarCorreo;

	@BeforeEach
	void setUp() {
		enviarCorreo = spy(new EnviarCorreo());
	}

	@Test
	void nuevoEventoTest() {
		Organizacion organizacion = mock(Organizacion.class);
		ZonaDeCobertura zonaDeCobertura = mock(ZonaDeCobertura.class);
		Muestra muestra = mock(Muestra.class);

		enviarCorreo.nuevoEvento(organizacion, zonaDeCobertura, muestra);

		verify(enviarCorreo).nuevoEvento(organizacion, zonaDeCobertura, muestra);
	}
}
