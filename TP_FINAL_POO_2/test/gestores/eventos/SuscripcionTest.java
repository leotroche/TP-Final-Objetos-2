package gestores.eventos;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import eventos.Evento;

class SuscripcionTest {
	private Suscripcion suscripcion;

	private Evento evento;
	private Observer observer;

	@BeforeEach
	void setUp() {
		evento = mock(Evento.class);
		observer = mock(Observer.class);

		suscripcion = new Suscripcion(evento, observer);
	}

	@Test
	void retornaElEventoCorrectamente() {
		assertTrue(suscripcion.getEvento().equals(evento));
	}

	@Test
	void retornaElObserverCorrectamente() {
		assertTrue(suscripcion.getObserver().equals(observer));
	}

	@Test
	void retornaTrueCuandoElEventoCoincide() {
		assertTrue(suscripcion.esEvento(evento));
	}

	@Test
	void retornaTrueCuandoElObserverCoincide() {
		assertTrue(suscripcion.esObserver(observer));
	}
}
