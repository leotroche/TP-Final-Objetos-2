package gestores.funcionalidades;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import eventos.Evento;
import muestra.Muestra;
import organizacion.Organizacion;
import organizacion.ZonaDeCobertura;

class RegistroDeFuncionalidadTest {
	private RegistroDeFuncionalidad registro;

	Evento evento;
	FuncionalidadExterna funcionalidad;

	@BeforeEach
	void setUp() {
		evento = mock(Evento.class);
		funcionalidad = mock(FuncionalidadExterna.class);

		registro = new RegistroDeFuncionalidad(evento, funcionalidad);
	}

	@Test
	void retornaTrueCuandoElEventoCoincide() {
		assertTrue(registro.correspondeA(evento));
	}

	@Test
	void ejecutaLaFuncionalidadExternaConLosParametrosCorrectos() {
		Organizacion organizacion = mock(Organizacion.class);
		ZonaDeCobertura zonaDeCobertura = mock(ZonaDeCobertura.class);
		Muestra muestra = mock(Muestra.class);

		registro.ejecutar(organizacion, zonaDeCobertura, muestra);

		verify(funcionalidad).nuevoEvento(organizacion, zonaDeCobertura, muestra);
	}

	@Test
	void noEjecutaFuncionalidadSiEsNull() {
		RegistroDeFuncionalidad registro = new RegistroDeFuncionalidad(evento, null);

		Organizacion organizacion = mock(Organizacion.class);
		ZonaDeCobertura zonaDeCobertura = mock(ZonaDeCobertura.class);
		Muestra muestra = mock(Muestra.class);

		registro.ejecutar(organizacion, zonaDeCobertura, muestra);
		verify(funcionalidad, never()).nuevoEvento(organizacion, zonaDeCobertura, muestra);
	}
}
