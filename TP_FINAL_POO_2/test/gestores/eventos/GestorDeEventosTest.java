package gestores.eventos;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import eventos.Evento;
import muestras.Muestra;
import zonas.ZonaDeCobertura;

class GestorDeEventosTest {
	private GestorDeEventos gestor;

	private Evento evento;
	private Observer observer;

	private Evento otroEvento;
	private Observer otroObserver;

	// --------------------------------------------------------------------------------

	@BeforeEach
	void setUp() {
		gestor = new GestorDeEventos();

		// Eventos simulados
		evento = mock(Evento.class);
		otroEvento = mock(Evento.class);

		// Observers simulados
		observer = mock(Observer.class);
		otroObserver = mock(Observer.class);
	}

	// --------------------------------------------------------------------------------

	@Test
	void retornaFalseCuandoNoHaySuscripciones() {
		assertFalse(gestor.estaSuscrito(evento, observer));
	}

	// --------------------------------------------------------------------------------

	@Test
	void unObserverSeSuscribeCorrectamenteAUnEvento() {
		gestor.suscribir(evento, observer);

		assertTrue(gestor.estaSuscrito(evento, observer));
	}

	// --------------------------------------------------------------------------------

	@Test
	void estaSuscritoRetornaFalseCuandoNoHayCoincidenciaExacta() {
		// Exercise: evento y observer incorrectos
		assertFalse(gestor.estaSuscrito(evento, observer));

		gestor.suscribir(evento, observer);

		// Exercise: evento correcto pero observer incorrecto
		assertFalse(gestor.estaSuscrito(evento, otroObserver));

		// Exercise: evento incorrecto pero observer correcto
		assertFalse(gestor.estaSuscrito(otroEvento, observer));
	}

	// --------------------------------------------------------------------------------

	@Test
	void suscribirNoAgregaLaMismaSuscripcionDosVeces() {
		gestor.suscribir(evento, observer);

		gestor.suscribir(evento, observer);

		assertTrue(gestor.estaSuscrito(evento, observer));
	}

	// --------------------------------------------------------------------------------

	@Test
	void desuscribirEliminaLaSuscripcionDelObserverAlEvento() {
		gestor.suscribir(evento, observer);

		gestor.desuscribir(evento, observer);

		assertFalse(gestor.estaSuscrito(evento, observer));
	}

	// --------------------------------------------------------------------------------

	@Test
	void desuscribirNoEliminaLaSuscripcionSiNoHayCoincidenciaExacta() {
		gestor.suscribir(evento, observer);

		// Exercise: intentar eliminar con evento correcto pero observer incorrecto
		gestor.desuscribir(evento, otroObserver);
		assertTrue(gestor.estaSuscrito(evento, observer));

		// Exercise: intentar eliminar con evento incorrecto pero observer correcto
		gestor.desuscribir(otroEvento, observer);
		assertTrue(gestor.estaSuscrito(evento, observer));

		// Exercise: evento y observer incorrectos
		gestor.desuscribir(otroEvento, otroObserver);
		assertTrue(gestor.estaSuscrito(evento, observer));
	}

	// --------------------------------------------------------------------------------

	@Test
	void notificarInvocaUpdateEnObserverSuscriptoConLosParametrosCorrectos() {
		gestor.suscribir(evento, observer);

		ZonaDeCobertura zonaDeCobertura = mock(ZonaDeCobertura.class);
		Muestra muestra = mock(Muestra.class);

		gestor.notificar(evento, zonaDeCobertura, muestra);

		verify(observer).update(evento, zonaDeCobertura, muestra);
	}

	// --------------------------------------------------------------------------------

	@Test
	void notificarNoInvocaUpdateSiElObserverNoEstaSuscripto() {
		gestor.suscribir(otroEvento, otroObserver);

		ZonaDeCobertura zonaDeCobertura = mock(ZonaDeCobertura.class);
		Muestra muestra = mock(Muestra.class);

		gestor.notificar(evento, zonaDeCobertura, muestra);

		verify(otroObserver, never()).update(evento, zonaDeCobertura, muestra);
	}

	// --------------------------------------------------------------------------------

	@Test
	void notificarNoHaceNadaSiNoHaySuscripciones() {
		ZonaDeCobertura zonaDeCobertura = mock(ZonaDeCobertura.class);
		Muestra muestra = mock(Muestra.class);

		gestor.notificar(evento, zonaDeCobertura, muestra);

		verify(otroObserver, never()).update(evento, zonaDeCobertura, muestra);
	}
}
