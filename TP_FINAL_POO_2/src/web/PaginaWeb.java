package web;

import java.util.ArrayList;
import java.util.List;


import muestras.Muestra;
import usuarios.Usuario;
import zonas.ZonaDeCobertura;

public class PaginaWeb {
	private List<Usuario> usuariosRegistrados= new ArrayList<>();
	private List<Muestra> muestrasRegistradas = new ArrayList<>();
	private List<ZonaDeCobertura> zonasDeCobertura = new ArrayList<>();

	// ------------------------------------------------------------

	public List<Usuario> getUsuariosRegistrados() {
		return this.usuariosRegistrados;
	}

	public void agregarUsuario(Usuario usuario) {
		this.usuariosRegistrados.add(usuario);
	}
	
	public List<ZonaDeCobertura> getZonasDeCoberturaRegistradas() {
		return (this.zonasDeCobertura);
	}

	public List<Muestra> getMuestrasRegistradas() {
		return this.muestrasRegistradas;
	}

	public void agregarMuestra(Muestra muestra) {
		this.getMuestrasRegistradas().add(muestra);
		for(ZonaDeCobertura zona : this.getZonasDeCoberturaRegistradas()) {
			if (zona.perteneceALaZona(muestra)) {
				zona.procesarNuevaMuestra(muestra);
			}
		}
	}
	

	public void agregarZonaDeCobertura(ZonaDeCobertura zona) {
		if (!this.zonasDeCobertura.contains(zona)) {
			this.zonasDeCobertura.add(zona);
		}
	}
	
}

