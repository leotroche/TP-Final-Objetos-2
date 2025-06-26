package web;

import java.util.ArrayList;
import java.util.List;

import eventos.Evento;
import gestores.eventos.GestorDeEventos;
import gestores.eventos.Observer;
import muestras.Muestra;
import usuarios.Usuario;
import zonas.ZonaDeCobertura;

public class PaginaWeb {
	private GestorDeEventos gestorDeEventos;
	private ArrayList<Usuario> usuariosRegistrados= new ArrayList<>();
	private ArrayList<Muestra> muestrasRegistradas = new ArrayList<>();
	
	public PaginaWeb(GestorDeEventos gestorDeEventos) {
		this.setGestorDeEventos(gestorDeEventos);
	}
	
	// ------------------------------------------------------------
	
	private GestorDeEventos getGestorDeEventos() {
		return this.gestorDeEventos;
	}
	
	private void setGestorDeEventos(GestorDeEventos gestorDeEventos) {
		this.gestorDeEventos = gestorDeEventos;
	}	
	
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
		this.getGestorDeEventos().suscribir(evento, (Observer) zona);
	}

	public void desubscribirZonaDeCobertura(Evento evento, ZonaDeCobertura zona) {
		this.getGestorDeEventos().desuscribir(evento, zona);
	}
	public void notificarZonaDeCobertura(Evento evento, ZonaDeCobertura zona, Muestra muestra) {
		this.getGestorDeEventos().notificar(evento, zona, muestra);
	}

}
