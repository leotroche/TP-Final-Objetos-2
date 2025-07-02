package zonas;

import java.util.ArrayList;
import java.util.List;

import muestras.Muestra;
import muestras.observer.ObserverMuestra;
import ubicaciones.Ubicacion;
import zonas.observer.ObserverZonaDeCobertura;
import zonas.observer.SubjectZonaDeCobertura;

public class ZonaDeCobertura implements SubjectZonaDeCobertura, ObserverMuestra {
	private String nombre;
	private Ubicacion epicentro;
	private double radio;

	private List<Muestra> muestras = new ArrayList<>();
	private List<ObserverZonaDeCobertura> observadoresDeMuestraCargada = new ArrayList<>();
	private List<ObserverZonaDeCobertura> observadoresDeMuestraValidada = new ArrayList<>();

	public ZonaDeCobertura(String nombre, Ubicacion epicentro, double radio) {
		this.setNombre(nombre);
		this.setEpicentro(epicentro);
		this.setRadio(radio);
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

	private void setNombre(String nombre) {
		this.nombre = nombre;
	}

	private void setEpicentro(Ubicacion epicentro) {
		this.epicentro = epicentro;
	}

	private void setRadio(double radio) {
		this.radio = radio;
	}

	// --------------------------------------------------------------------------------

	public List<Muestra> getMuestras() {
		return this.muestras;
	}

	public void agregarMuestra(Muestra muestra) {
		this.getMuestras().add(muestra);
	}

	public boolean perteneceALaZona(Muestra muestra) {
		return this.getEpicentro().calcularDistanciaKm(muestra.getUbicacion()) <= this.getRadio();
	}

	public boolean seSolapaCon(ZonaDeCobertura otraZona) {
		double distanciaEntreCentros = this.getEpicentro().calcularDistanciaKm(otraZona.getEpicentro());
		return distanciaEntreCentros < (this.getRadio() + otraZona.getRadio());
	}

	public List<ZonaDeCobertura> zonasSolapadas(List<ZonaDeCobertura> zonas) {
		return zonas.stream().filter(otraZona -> this.seSolapaCon(otraZona)).toList();
	}

	public void procesarNuevaMuestra(Muestra muestra) {
		if (this.perteneceALaZona(muestra)) {
			// Agregamos la muestra a la zona de cobertura
			this.agregarMuestra(muestra);

			// Suscribimos el obsevador a la muestra (para evento muestra validada)
			muestra.suscribirMuestraValidada(this);

			// Notificamos a los observadores suscritos que se ha cargado una nueva muestra
			this.notificarMuestraCargada(muestra);
		}
	}

	// --------------------------------------------------------------------------------
	// Metodos de Observer
	// --------------------------------------------------------------------------------

	public List<ObserverZonaDeCobertura> getObservadoresDeMuestraCargada() {
		return this.observadoresDeMuestraCargada;
	}

	@Override
	public void suscribirMuestraCargada(ObserverZonaDeCobertura organizacion) {
		if (!this.getObservadoresDeMuestraCargada().contains(organizacion)) {
			this.getObservadoresDeMuestraCargada().add(organizacion);
		}
	}

	@Override
	public void desuscribirMuestraCargada(ObserverZonaDeCobertura organizacion) {
		if (this.getObservadoresDeMuestraCargada().contains(organizacion)) {
			this.getObservadoresDeMuestraCargada().remove(organizacion);
		}
	}

	@Override
	public void notificarMuestraCargada(Muestra muestra) {
		for (ObserverZonaDeCobertura observer : this.getObservadoresDeMuestraCargada()) {
			observer.updateMuestraCargada(this, muestra);
		}
	}

	// --------------------------------------------------------------------------------

	public List<ObserverZonaDeCobertura> getObservadoresDeMuestraValidada() {
		return this.observadoresDeMuestraValidada;
	}

	@Override
	public void suscribirMuestraValidada(ObserverZonaDeCobertura organizacion) {
		if (!this.getObservadoresDeMuestraValidada().contains(organizacion)) {
			this.getObservadoresDeMuestraValidada().add(organizacion);
		}
	}

	@Override
	public void desuscribirMuestraValidada(ObserverZonaDeCobertura organizacion) {
		if (this.getObservadoresDeMuestraValidada().contains(organizacion)) {
			this.getObservadoresDeMuestraValidada().remove(organizacion);
		}
	}

	@Override
	public void notificarMuestraValidada(Muestra muestra) {
		for (ObserverZonaDeCobertura observer : this.getObservadoresDeMuestraValidada()) {
			observer.updateMuestraValidada(this, muestra);
		}
	}

	// --------------------------------------------------------------------------------

	@Override
	public void updateMuestraValidada(Muestra muestra) {
		this.notificarMuestraValidada(muestra);
	}
}
