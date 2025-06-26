package web;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import eventos.Evento;
import gestores.eventos.GestorDeEventos;
import muestras.Muestra;
import usuarios.Usuario;
import zonas.ZonaDeCobertura;

class PaginaWebTest {
	private PaginaWeb web;

	private GestorDeEventos gestorDeEventos;
	private Usuario usuario;
	private Muestra muestra;


	@BeforeEach
	void setUp() {
		gestorDeEventos = mock(GestorDeEventos.class);
		muestra = mock(Muestra.class);
		usuario = mock(Usuario.class);

		web = new PaginaWeb(gestorDeEventos);
	}

	@Test
	void unaPaginaWebIncialmenteNoTieneUsuarios() {
		assertTrue(web.getUsuariosRegistrados().isEmpty());
	}

	@Test
	void unaPaginaWebIncialmenteNoTieneMuestras() {
		assertTrue(web.getMuestrasRegistradas().isEmpty());
	}

	@Test
	void agregarUsuarioAgregaUnUsuarioAlaPaginaWeb() {
		web.agregarUsuario(usuario);
		assertEquals(1, web.getUsuariosRegistrados().size());
	}

	@Test
	void agregarMuestraAgregaUnaMuestraAlaPaginaWeb() {
		web.agregarMuestra(muestra);
		assertEquals(1, web.getMuestrasRegistradas().size());
	}

	@Test
	void agregarMuestraDelegaLaNotificacionAlGestorDeEventos() {
		web.agregarMuestra(muestra);
		verify(gestorDeEventos).notificar(Evento.MUESTRA_CARGADA, null, muestra);
	}

	@Test
	void subscribirZonaDeCoberturaDelegaLaSuscripcionAlGestorDeEventos() {
		ZonaDeCobertura zona = mock(ZonaDeCobertura.class);
		Evento evento = mock(Evento.class);

		web.subscribirZonaDeCobertura(evento, zona);

		verify(gestorDeEventos).suscribir(evento, zona);
	}

	@Test
	void desubscribirZonaDeCoberturaDelegaLaDesuscripcionAlGestorDeEventos() {
		ZonaDeCobertura zona = mock(ZonaDeCobertura.class);
		Evento evento = mock(Evento.class);

		web.desubscribirZonaDeCobertura(evento, zona);

		verify(gestorDeEventos).desuscribir(evento, zona);
	}

	@Test
	void notificarZonaDeCoberturaDelegaLaNotificacionAlGestorDeEventos() {
		ZonaDeCobertura zona = mock(ZonaDeCobertura.class);
		Evento evento = mock(Evento.class);

		web.notificarZonaDeCobertura(evento, zona, muestra);

		verify(gestorDeEventos).notificar(evento, zona, muestra);
	}
}

