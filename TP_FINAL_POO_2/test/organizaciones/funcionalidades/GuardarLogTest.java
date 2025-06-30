package organizaciones.funcionalidades;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import muestras.Muestra;
import organizaciones.Organizacion;
import zonas.ZonaDeCobertura;

class GuardarLogTest {
	private FuncionalidadExterna guardarLog;

	@BeforeEach
	void setUp() {
		guardarLog = spy(new GuardarLog());
	}

	@Test
	void nuevoEventoTest() {
		Organizacion organizacion = mock(Organizacion.class);
		ZonaDeCobertura zonaDeCobertura = mock(ZonaDeCobertura.class);
		Muestra muestra = mock(Muestra.class);

		guardarLog.nuevoEvento(organizacion, zonaDeCobertura, muestra);

		verify(guardarLog).nuevoEvento(organizacion, zonaDeCobertura, muestra);
	}
}
