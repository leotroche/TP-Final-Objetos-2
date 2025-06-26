package zonas;


import java.util.ArrayList;
import java.util.List;

import eventos.Evento;
import gestores.eventos.GestorDeEventos;
import gestores.eventos.Observer;
import muestras.Muestra;
import organizaciones.Organizacion;
import ubicaciones.Ubicacion;

public class ZonaDeCobertura implements Observer {
	private String nombre;
	private Ubicacion epicentro;
	private double radio;
	private GestorDeEventos gestorDeEventos;
	private List<Muestra> muestras = new ArrayList<>();

	public ZonaDeCobertura(String nombre, Ubicacion epicentro, double radioKm, GestorDeEventos gestorDeEventos) {
		this.setNombre(nombre);
		this.setEpicentro(epicentro);
		this.setRadio(radioKm);
		this.setGestorDeEventos(gestorDeEventos);
	}

	// --------------------------------------------------------------------------------

	public String getNombre() {
		return this.nombre;
	}

	public Ubicacion getEpicentro() {
		return this.epicentro;
	}

	public double getRadio() {
		return this.radio;
	}

	private GestorDeEventos getGestorDeEventos() {
		return this.gestorDeEventos;
	}

	private void setNombre(String nombre) {
		this.nombre = nombre;
	}

	private void setEpicentro(Ubicacion epicentro) {
		this.epicentro = epicentro;
	}

	private void setRadio(double radio) {
		this.radio = radio;
	}

	private void setGestorDeEventos(GestorDeEventos gestorDeEventos) {
		this.gestorDeEventos = gestorDeEventos;
	}

	// --------------------------------------------------------------------------------

	public List<Muestra> getMuestras() {
		return this.muestras;
	}

	public void agregarMuestra(Muestra muestra) {
		this.muestras.add(muestra);
	}

	public boolean perteneceALaZona(Muestra muestra) {
		return this.getEpicentro().calcularDistanciaKm(muestra.getUbicacion()) <= this.getRadio();
	}

	public boolean seSolapaCon(ZonaDeCobertura otraZona) {
		double distanciaEntreCentros = this.getEpicentro().calcularDistanciaKm(otraZona.getEpicentro());
		return distanciaEntreCentros < (this.getRadio() + otraZona.getRadio());
	}

	public List<ZonaDeCobertura> zonasSolapadas(List<ZonaDeCobertura> zonas) {
		return zonas.stream()
				.filter(otraZona -> this.seSolapaCon(otraZona))
				.toList();
	}

	// --------------------------------------------------------------------------------
	// Métodos de eventos
	// --------------------------------------------------------------------------------

	public void suscribirOrganizacion(Evento evento, Organizacion organizacion) {
		this.getGestorDeEventos().suscribir(evento, organizacion);
	}

	public void desuscribirOrganizacion(Evento evento, Organizacion organizacion) {
		this.getGestorDeEventos().desuscribir(evento, organizacion);
	}

	public void notificarOrganizaciones(Evento evento, ZonaDeCobertura zonaDeCobertura, Muestra muestra) {
		this.getGestorDeEventos().notificar(evento, zonaDeCobertura, muestra);
	}

	@Override
	public void update(Evento evento, ZonaDeCobertura zonaDeCobertura, Muestra muestra) {
		if (this.perteneceALaZona(muestra)) {
			this.agregarMuestra(muestra);
			this.notificarOrganizaciones(evento, zonaDeCobertura, muestra);
		}
	}
}