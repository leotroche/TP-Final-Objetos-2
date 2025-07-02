package web;

import java.util.ArrayList;
import java.util.List;

import muestras.Muestra;
import usuarios.Usuario;
import zonas.ZonaDeCobertura;

public class PaginaWeb {
	private List<Usuario> usuariosRegistrados= new ArrayList<>();
	private List<Muestra> muestrasRegistradas = new ArrayList<>();
	private List<ZonaDeCobertura> zonasDeCoberturaRegistradas = new ArrayList<>();

	// ------------------------------------------------------------

	public List<Usuario> getUsuariosRegistrados() {
		return this.usuariosRegistrados;
	}

	public void agregarUsuario(Usuario usuario) {
		if (!getUsuariosRegistrados().contains(usuario)) {
			this.getUsuariosRegistrados().add(usuario);
		}
	}

	// ------------------------------------------------------------

	public List<Muestra> getMuestrasRegistradas() {
		return this.muestrasRegistradas;
	}

	public void agregarMuestra(Muestra muestra) {
		if (!getMuestrasRegistradas().contains(muestra)) {
			// Agregamos la muestra al historial de muestras de la pÃ¡gina web
			this.getMuestrasRegistradas().add(muestra);

			// Delegamos el procesamiento de la muestra a las zonas de cobertura
			for (ZonaDeCobertura zona : this.getZonasDeCoberturaRegistradas()) {
				zona.procesarNuevaMuestra(muestra);
			}
		}
	}

	// ------------------------------------------------------------

	public List<ZonaDeCobertura> getZonasDeCoberturaRegistradas() {
		return this.zonasDeCoberturaRegistradas;
	}

	public void agregarZonaDeCobertura(ZonaDeCobertura zonaDeCobertura) {
		if (!getZonasDeCoberturaRegistradas().contains(zonaDeCobertura)) {
			this.getZonasDeCoberturaRegistradas().add(zonaDeCobertura);
		}
	}
}
