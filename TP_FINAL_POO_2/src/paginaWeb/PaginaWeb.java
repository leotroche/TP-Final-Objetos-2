package paginaWeb;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.stream.Collectors;

import eventos.Evento;
import gestores.eventos.GestorDeEventos;
import gestores.eventos.Observer;
import muestra.Muestra;
import usuario.Usuario;
import zona.cobertura.ZonaDeCobertura;

public class PaginaWeb {
	private ArrayList<Usuario> usuarios= new ArrayList<>();
	private ArrayList<Muestra> muestras = new ArrayList<>();
	private GestorDeEventos gestorDeEventos;;
	
	public PaginaWeb(GestorDeEventos gestorDeEventos) {
		this.setGestorDeEventos(gestorDeEventos);
	}
	private void setGestorDeEventos(GestorDeEventos gestorDeEventos) {
		this.gestorDeEventos = gestorDeEventos;
	}
	public ArrayList<Usuario> getUsuarios() {
		return this.usuarios;
	}
	public void setUsuarios(ArrayList<Usuario> listaUsuarios) {
		this.usuarios = listaUsuarios;
	}
	public void agregarUsuario(Usuario usuario) {
		this.usuarios.add(usuario);
	}
	public ArrayList<Muestra> getMuestras() {
		return this.muestras;
	}
	public void setMuestras(ArrayList<Muestra> muestras) {
		this.muestras = muestras;
	}
	public void subscribirZonaDeCobertura(Evento evento, ZonaDeCobertura zona) {
		this.getGestorDeEventos().suscribir(evento, (Observer) zona);
	}
	private GestorDeEventos getGestorDeEventos() {
		return gestorDeEventos;
	}
	public void desubscribirZonaDeCobertura(Evento evento, ZonaDeCobertura zona) {
		this.getGestorDeEventos().desuscribir(evento, zona);
	}
	public void notificarZonaDeCobertura(Evento evento, ZonaDeCobertura zona, Muestra muestra) {
		this.getGestorDeEventos().notificar(evento, zona, muestra);
	}
	public ArrayList<Muestra> getMuestrasUltimosNDias() {
		LocalDate fechaLimite = LocalDate.now().minusDays(30);
        return this.muestras.stream()
                .filter(muestra -> muestra.getFechaDeCreacion() != null)
                .filter(muestra -> muestra.getFechaDeCreacion().isAfter(fechaLimite))
                .collect(Collectors.toCollection(ArrayList::new));
    }
	public void agregarMuestra(Muestra muestra) {
		this.muestras.add(muestra);
	}
}
