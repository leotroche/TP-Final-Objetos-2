package eventmanager;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EventManagerTest {
	private EventManager eventManager;

	private Event evento;
	private EventListener listener;

	private Event otroEvento;
	private EventListener otroListener;

	// --------------------------------------------------------------------------------

	@BeforeEach
	void setUp() {
		// Mocks
		evento = mock(Event.class);
		otroEvento = mock(Event.class);
		listener = mock(EventListener.class);
		otroListener = mock(EventListener.class);

		// SUT
		eventManager = new EventManager();
	}

	// --------------------------------------------------------------------------------

	@Test
	void isSubscribed_IndicaTrueSiUnListenerEstaSubscritoAUnEvento() {
		// Exercise
		eventManager.subscribe(evento, listener);

		// Verify
		assertTrue(eventManager.isSubscribed(evento, listener));
	}

	@Test
	void isSubscribed_IndicaFalseCuandoNoHayCoincidenciaConSubscripciones() {
		// Exercise: evento y listener incorrectos
		assertFalse(eventManager.isSubscribed(evento, listener));

		// Setup
		eventManager.subscribe(evento, listener);

		// Exercise: evento correcto pero listener incorrecto
		assertFalse(eventManager.isSubscribed(evento, otroListener));

		// Exercise: evento incorrecto pero listener correcto
		assertFalse(eventManager.isSubscribed(otroEvento, listener));
	}

	// --------------------------------------------------------------------------------

	@Test
	void subscribe_AgregaLaSuscripcionDeUnListenerAUnEvento() {
		// Exercise
		eventManager.subscribe(evento, listener);

		// Verify
		assertTrue(eventManager.isSubscribed(evento, listener));
	}

	@Test
	void subscribe_NoAgregaDuplicados() {
		// Setup
		eventManager.subscribe(evento, listener);

		// Exercise: intentar agregar la misma suscripción nuevamente
		eventManager.subscribe(evento, listener);

		// Verify: sigue habiendo solo una suscripción
		assertTrue(eventManager.isSubscribed(evento, listener));
	}

	// --------------------------------------------------------------------------------

	@Test
	void unsubscribe_EliminaLaSuscripcionDeUnListenerAUnEvento() {
		// Setup
		eventManager.subscribe(evento, listener);

		// Exercise
		eventManager.unsubscribe(evento, listener);

		// Verify
		assertFalse(eventManager.isSubscribed(evento, listener));
	}

	@Test
	void unsubscribe_NoEliminaSubscripcionesQueNoCoincidenTotalmente() {
		// Setup
		eventManager.subscribe(evento, listener);

		// Exercise: intentar eliminar con evento correcto pero listener incorrecto
		eventManager.unsubscribe(evento, otroListener);
		assertTrue(eventManager.isSubscribed(evento, listener));

		// Exercise: intentar eliminar con evento incorrecto pero listener correcto
		eventManager.unsubscribe(otroEvento, listener);
		assertTrue(eventManager.isSubscribed(evento, listener));

		// Exercise: evento y listener incorrectos
		eventManager.unsubscribe(otroEvento, otroListener);
		assertTrue(eventManager.isSubscribed(evento, listener));
	}

	// --------------------------------------------------------------------------------

	@Test
	void notify_InvocaElMetodoUpdateDeUnListenerSuscrito() {
		// Setup
		eventManager.subscribe(evento, listener);

		// Exercise
		eventManager.notify(evento, null, null);

		// Verify
		verify(listener).update(evento, null, null);
	}

	@Test
	void notify_NoInvocaElMetodoUpdateSiElListenerNoEstaSuscrito() {
		// Setup
		eventManager.subscribe(otroEvento, otroListener);

		// Exercise
		eventManager.notify(evento, null, null);

		// Verify
		verify(otroListener, never()).update(evento, null, null);
	}

	@Test
	void notify_NoHaceNadaSiNoHayListenersSuscritos() {
		// Exercise
		eventManager.notify(evento, null, null);

		// Verify
		verify(listener, never()).update(evento, null, null);
	}
}
