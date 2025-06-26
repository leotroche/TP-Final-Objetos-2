package muestras;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import eventos.Evento;
import gestores.eventos.GestorDeEventos;
import muestras.estados.EstadoDeVerificacion;
import muestras.estados.EstadoNoVerificado;
import muestras.estados.EstadoVerificado;
import ubicaciones.Ubicacion;
import usuarios.Usuario;
import zonas.ZonaDeCobertura;

public class Muestra {

	private String foto;
	private Ubicacion ubicacion;
	private Usuario autor;
	private TipoDeInsecto tipoDeInsecto;
	private GestorDeEventos gestorDeEventos;

	private LocalDate fechaDeCreacion;

	private List<Opinion> opiniones = new ArrayList<>();
	private EstadoDeVerificacion estadoDeVerificacion;

	public Muestra(String foto, Ubicacion ubicacion, Usuario autor, TipoDeInsecto tipoDeInsecto,
			GestorDeEventos gestorDeEventos) {

		this.setFoto(foto);
		this.setUbicacion(ubicacion);
		this.setAutor(autor);
		this.setTipoDeInsecto(tipoDeInsecto);
		this.setFechaDeCreacion(LocalDate.now());
		this.setGestorDeEventos(gestorDeEventos);

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

	private GestorDeEventos getGestorDeEventos() {
		return this.gestorDeEventos;
	}

	private void setGestorDeEventos(GestorDeEventos gestorDeEventos) {
		this.gestorDeEventos = gestorDeEventos;
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
		this.getEstadoDeVerificacion().agregarOpinion(this, opinion);
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
			this.setEstadoDeVerificacion(new EstadoVerificado());
			this.notificarZonasDeCobertura(Evento.MUESTRA_VALIDADA, null, this);
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
	// Metodos de eventos
	// ------------------------------------------------------------

	public void suscribirZonaDeCobertura(Evento evento, ZonaDeCobertura zona) {
		this.getGestorDeEventos().suscribir(evento, zona);
	}

	public void desuscribirZonaDeCobertura(Evento evento, ZonaDeCobertura zona) {
		this.getGestorDeEventos().desuscribir(evento, zona);
	}

	public void notificarZonasDeCobertura(Evento evento, ZonaDeCobertura zona, Muestra muestra) {
		this.getGestorDeEventos().notificar(evento, zona, this);
	}
}
