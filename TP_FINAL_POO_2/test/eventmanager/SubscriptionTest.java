package eventmanager;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SubscriptionTest {
	private Subscription subscription;

	private Event event;
	private EventListener listener;

	@BeforeEach
	void setUp() {
		// Mocks
		event = mock(Event.class);
		listener = mock(EventListener.class);

		// SUT
		subscription = new Subscription(event, listener);
	}

	@Test
	void getEvent() {
		// Exercise & Verify
		assertTrue(subscription.getEvent().equals(event));
	}

	@Test
	void getListener() {
		// Exercise & Verify
		assertTrue(subscription.getListener().equals(listener));
	}

	@Test
	void isEvent() {
		// Exercise & Verify
		assertTrue(subscription.isEvent(event));
	}

	@Test
	void isListener() {
		// Exercise & Verify
		assertTrue(subscription.isListener(listener));
	}
}
