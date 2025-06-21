package eventmanager;

public interface EventListener {
	public void update(Event event, Object context, Object extraData);
}
