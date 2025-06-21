package eventmanager;

public class Subscription {
	private Event event;
	private EventListener listener;

	public Subscription(Event event, EventListener listener) {
		this.setEvent(event);
		this.setListener(listener);
	}

	public Event getEvent() {
		return this.event;
	}

	public EventListener getListener() {
		return this.listener;
	}

	private void setEvent(Event event) {
		this.event = event;
	}

	private void setListener(EventListener listener) {
		this.listener = listener;
	}

	public boolean isEvent(Event event) {
		return this.getEvent().equals(event);
	}

	public boolean isListener(EventListener listener) {
		return this.getListener().equals(listener);
	}
}
