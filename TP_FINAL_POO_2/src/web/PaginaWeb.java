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
	private List<ZonaDeCobertura> zonasDeCobertura = new ArrayList<>();

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

	public void subscribirZonaDeCobertura(ZonaDeCobertura zona) {
		if (!this.zonasDeCobertura.contains(zona)) {
			this.zonasDeCobertura.add(zona);
		}
	}

	public void desubscribirZonaDeCobertura(Evento evento,ZonaDeCobertura zona) {
		//
	}
	public void notificarZonaDeCobertura(ZonaDeCobertura zona, Muestra muestra) {
		//
	}
}

