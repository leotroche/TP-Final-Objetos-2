package muestras;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import muestras.estados.EstadoDeVerificacion;
import muestras.estados.EstadoNoVerificado;
import muestras.estados.EstadoVerificado;
import muestras.observer.ObserverMuestra;
import muestras.observer.SubjectMuestra;
import ubicaciones.Ubicacion;
import usuarios.Usuario;

public class Muestra implements SubjectMuestra {
private String foto;
private Ubicacion ubicacion;
private Usuario autor;
private TipoDeInsecto tipoDeInsecto;
private LocalDate fechaDeCreacion;

private List<Opinion> opiniones = new ArrayList<>();
private List<ObserverMuestra> observadoresDeMuestraValidada = new ArrayList<>();
private EstadoDeVerificacion estadoDeVerificacion;

public Muestra(String foto, Ubicacion ubicacion, Usuario autor, TipoDeInsecto tipoDeInsecto) {
this.setFoto(foto);
this.setUbicacion(ubicacion);
this.setAutor(autor);
this.setTipoDeInsecto(tipoDeInsecto);
this.setFechaDeCreacion(LocalDate.now());

this.setEstadoDeVerificacion(new EstadoNoVerificado());
this.agregarOpinion(new Opinion(autor, tipoDeInsecto, false));
}

	// ------------------------------------------------------------

	public String getFoto() {
		return this.foto;
	}

	private void setFoto(String foto) {
		this.foto = foto;
	}

	public TipoDeInsecto getTipoDeInsecto() {
		return this.tipoDeInsecto;
	}

	public void setTipoDeInsecto(TipoDeInsecto tipoDeInsecto) {
		this.tipoDeInsecto = tipoDeInsecto;
	}

	public Ubicacion getUbicacion() {
		return this.ubicacion;
	}

	private void setUbicacion(Ubicacion ubicacion) {
		this.ubicacion = ubicacion;
	}

	public Usuario getAutor() {
		return this.autor;
	}

	private void setAutor(Usuario usuario) {
		this.autor = usuario;
	}


	public LocalDate getFechaDeCreacion() {
		return this.fechaDeCreacion;
	}

	private void setFechaDeCreacion(LocalDate fecha) {
		this.fechaDeCreacion = fecha;
	}

	// ------------------------------------------------------------
	// Metodos de estado de verificacion
	// ------------------------------------------------------------

	public List<Opinion> getOpiniones() {
		return this.opiniones;
	}

	public EstadoDeVerificacion getEstadoDeVerificacion() {
		return this.estadoDeVerificacion;
	}

	public void setEstadoDeVerificacion(EstadoDeVerificacion estadoDeVerificacion) {
		this.estadoDeVerificacion = estadoDeVerificacion;
	}

	public void agregarOpinion(Opinion opinion) {
		if (!this.getOpiniones().contains(opinion)) {
			this.getEstadoDeVerificacion().agregarOpinion(this, opinion);
		}
	}

	public void doAgregarOpinion(Opinion opinion) {
		this.getOpiniones().add(opinion);
	}

	public void recibioOpinionDeExperto() {
		this.getEstadoDeVerificacion().recibioOpinionDeExperto(this);
	}

	public TipoDeInsecto obtenerResultadoActual() {
		return this.getEstadoDeVerificacion().obtenerResultadoActual(this);
	}

	public void verificarMuestraSiCorresponde() {
		TipoDeInsecto tipo = this.obtenerUltimaOpinion().getTipoDeInsecto();

		if (this.cantidadDeOpinionesConTipo(tipo) > 1) {
			// Si la muestra tiene más de una opinión del mismo tipo, se verifica
			this.setEstadoDeVerificacion(new EstadoVerificado());

			// Notificamos a las zonas de cobertura que la muestra fue validada
			this.notificarMuestraValidada();
		}
	}

	// ------------------------------------------------------------

	public int cantidadDeOpinionesConTipo(TipoDeInsecto tipoDeInsecto) {
		return this.getOpiniones().stream()
				.filter(opinion -> opinion.getTipoDeInsecto() == tipoDeInsecto)
				.toList()
				.size();
	}

	public List<Opinion> opinionesDeExpertos() {
		return this.getOpiniones().stream()
				.filter(opinion -> opinion.getEsUnExperto())
				.toList();
	}

	public TipoDeInsecto tipoMasFrecuenteEnOpiniones() {
		// Obtengo el tipo de insecto con más opiniones. (NINGUNA si no hay opiniones)
		return this.getOpiniones().stream()
				.collect(Collectors.groupingBy(Opinion::getTipoDeInsecto, Collectors.counting()))
				.entrySet().stream()
				.max(Map.Entry.comparingByValue())
				.map(Map.Entry::getKey)
				.orElse(TipoDeInsecto.NINGUNA);
	}

	// ------------------------------------------------------------

	public Opinion obtenerUltimaOpinion() {
		return this.opiniones.get(this.opiniones.size() - 1);
	}

	public LocalDate getFechaDeUltimaVotacion() {
		return this.obtenerUltimaOpinion().getFechaDeVotacion();
	}

	// ------------------------------------------------------------
	// Metodos de observer
	// ------------------------------------------------------------

	public List<ObserverMuestra> getObservadoresDeMuestraValidada() {
		return this.observadoresDeMuestraValidada;
	}

	@Override
	public void suscribirMuestraValidada(ObserverMuestra zona) {
		if (!this.getObservadoresDeMuestraValidada().contains(zona)) {
			this.getObservadoresDeMuestraValidada().add(zona);
		}
	}

	@Override
	public void desuscribirMuestraValidada(ObserverMuestra zona) {
		if (this.getObservadoresDeMuestraValidada().contains(zona)) {
			this.getObservadoresDeMuestraValidada().remove(zona);
		}
	}

	@Override
	public void notificarMuestraValidada() {
		for (ObserverMuestra observer : this.getObservadoresDeMuestraValidada()) {
			observer.updateMuestraValidada(this);
		}
	}
}
