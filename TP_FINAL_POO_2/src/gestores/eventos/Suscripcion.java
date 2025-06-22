package gestores.eventos;

import eventos.Evento;

public class Suscripcion {
	private Evento evento;
	private Observer observer;

	public Suscripcion(Evento evento, Observer observer) {
		this.setEvento(evento);
		this.setObserver(observer);
	}

	public Evento getEvento() {
		return this.evento;
	}

	public Observer getObserver() {
		return this.observer;
	}

	private void setEvento(Evento evento) {
		this.evento = evento;
	}

	private void setObserver(Observer observer) {
		this.observer = observer;
	}

	public boolean esEvento(Evento evento) {
		return this.getEvento().equals(evento);
	}

	public boolean esObserver(Observer observer) {
		return this.getObserver().equals(observer);
	}
}
