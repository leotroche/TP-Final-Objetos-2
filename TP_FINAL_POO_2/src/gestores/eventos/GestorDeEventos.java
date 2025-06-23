package gestores.eventos;

import java.util.ArrayList;
import java.util.List;

import eventos.Evento;
import muestra.Muestra;
import organizacion.ZonaDeCobertura;

public class GestorDeEventos {
	List<Suscripcion> suscripcion = new ArrayList<>();

	private List<Suscripcion> getSuscripciones() {
		return this.suscripcion;
	}

	public void suscribir(Evento evento, Observer observer) {
		if (!this.estaSuscrito(evento, observer)) {
			this.getSuscripciones().add(new Suscripcion(evento, observer));
		}
	}

	public void desuscribir(Evento evento, Observer observer) {
		this.getSuscripciones().removeIf(s -> s.esEvento(evento) && s.esObserver(observer));
	}

	public void notificar(Evento evento, ZonaDeCobertura zonaDeCobertura, Muestra muestra) {
		for (Suscripcion suscripcion : this.getSuscripciones()) {
			if (suscripcion.esEvento(evento)) {
				suscripcion.getObserver().update(evento, zonaDeCobertura, muestra);
			}
		}
	}

	public boolean estaSuscrito(Evento evento, Observer observer) {
		return this.getSuscripciones().stream()
				.anyMatch(s -> s.esEvento(evento) && s.esObserver(observer));
	}
}
