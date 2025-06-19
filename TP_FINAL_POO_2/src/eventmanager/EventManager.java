package eventmanager;

import java.util.ArrayList;
import java.util.List;

public class EventManager {
	List<Subscription> subscriptions = new ArrayList<>();

	private List<Subscription> getSubscriptions() {
		return this.subscriptions;
	}

	public void subscribe(Event event, EventListener listener) {
		if (!this.isSubscribed(event, listener)) {
			this.getSubscriptions().add(new Subscription(event, listener));
		}
	}

	public void unsubscribe(Event event, EventListener listener) {
		this.getSubscriptions().removeIf(s -> s.isEvent(event) && s.isListener(listener));
	}

	public void notify(Event event, Object context, Object extraData) {
		for (Subscription s : this.getSubscriptions()) {
			if (s.isEvent(event)) {
				s.getListener().update(event, context, extraData);
			}
		}
	}

	public boolean isSubscribed(Event event, EventListener listener) {
		return this.getSubscriptions().stream()
				.anyMatch(s -> s.isEvent(event) && s.isListener(listener));
	}
}
