package web;

import java.util.ArrayList;
import java.util.List;

import eventos.Evento;
import muestras.Muestra;
import usuarios.Usuario;
import zonas.ZonaDeCobertura;

public class PaginaWeb {
	private ArrayList<Usuario> usuariosRegistrados= new ArrayList<>();
	private ArrayList<Muestra> muestrasRegistradas = new ArrayList<>();

	// ------------------------------------------------------------

	public List<Usuario> getUsuariosRegistrados() {
		return this.usuariosRegistrados;
	}

	public void agregarUsuario(Usuario usuario) {
		this.usuariosRegistrados.add(usuario);
	}

	public List<Muestra> getMuestrasRegistradas() {
		return this.muestrasRegistradas;
	}

	public void agregarMuestra(Muestra muestra) {
		this.getMuestrasRegistradas().add(muestra);
	}

	// ------------------------------------------------------------
	// Metodos de eventos
	// ------------------------------------------------------------

	public void subscribirZonaDeCobertura(Evento evento, ZonaDeCobertura zona) {
		//
	}

	public void desubscribirZonaDeCobertura(Evento evento, ZonaDeCobertura zona) {
		//
	}
	public void notificarZonaDeCobertura(Evento evento, ZonaDeCobertura zona, Muestra muestra) {
		//
	}
}

