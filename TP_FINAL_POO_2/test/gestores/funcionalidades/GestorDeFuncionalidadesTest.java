package gestores.funcionalidades;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import eventos.Evento;
import muestras.Muestra;
import organizaciones.Organizacion;
import zonas.ZonaDeCobertura;

class GestorDeFuncionalidadesTest {
	private GestorDeFuncionalidades gestor;

	private Evento evento;
	private FuncionalidadExterna funcionalidad;

	@BeforeEach
	void setUp() {
		evento = mock(Evento.class);
		funcionalidad = mock(FuncionalidadExterna.class);

		gestor = new GestorDeFuncionalidades();
	}

	@Test
	void noHayFuncionalidadParaEventoEnGestorVacio() {
		assertFalse(gestor.hayFuncionalidadPara(evento));
	}

	@Test
	void agregarFuncionalidadAsociaCorrectamenteAlEvento() {
		gestor.cambiarPara(evento, funcionalidad);

		assertTrue(gestor.hayFuncionalidadPara(evento));
	}

	@Test
	void ejecutarFuncionalidadInvocaMetodoNuevoEventoConLosParametrosCorrectos() {
		Organizacion organizacion = mock(Organizacion.class);
		ZonaDeCobertura zonaDeCobertura = mock(ZonaDeCobertura.class);
		Muestra muestra = mock(Muestra.class);

		gestor.cambiarPara(evento, funcionalidad);
		gestor.ejecutarPara(evento, organizacion, zonaDeCobertura, muestra);

		verify(funcionalidad).nuevoEvento(organizacion, zonaDeCobertura, muestra);
	}

	@Test
	void cambiarFuncionalidadActualizaCorrectamenteYSeEjecutaLaNueva() {
		gestor.cambiarPara(evento, funcionalidad); // Agrega la funcionalidad inicial

		FuncionalidadExterna otraFuncionalidad = mock(FuncionalidadExterna.class);

		gestor.cambiarPara(evento, otraFuncionalidad); // Cambia a otra funcionalidad

		gestor.ejecutarPara(evento, mock(Organizacion.class), mock(ZonaDeCobertura.class), mock(Muestra.class));

		assertTrue(gestor.hayFuncionalidadPara(evento));
		verify(otraFuncionalidad).nuevoEvento(any(), any(), any());
	}

	@Test
	void ejecutarParaNoEjecutaSiEventoNoCorresponde() {
		Evento otroEvento = mock(Evento.class);

		gestor.cambiarPara(evento, funcionalidad);

		gestor.ejecutarPara(otroEvento, mock(Organizacion.class), mock(ZonaDeCobertura.class), mock(Muestra.class));

		verify(funcionalidad, never()).nuevoEvento(any(), any(), any());
	}
}
