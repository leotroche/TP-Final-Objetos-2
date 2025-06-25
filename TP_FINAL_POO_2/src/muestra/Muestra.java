package muestra;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import eventos.Evento;
import gestores.eventos.GestorDeEventos;
import ubicacion.Ubicacion;
import usuario.Usuario;
import varios.Opinion;
import varios.TipoDeInsecto;
import zonadecobertura.ZonaDeCobertura;

/*
 * Clase Muestra que los usuarios envian a traves de la aplicacion
 */
 
public class Muestra {
	
	private String foto;
	private Ubicacion ubicacion;
	private Usuario autor;
	private LocalDate fechaDeCreacion;
	private List<Opinion> opiniones = new ArrayList<>();
	private TipoDeInsecto resultadoActual; 
	private EstadoDeVerificacion estadoDeMuestra;
	private GestorDeEventos gestor;
	
	/* 
	 * Constructor que recibe como parametro:
	 * String, foto tomada por el usuario
	 * Ubicacion, ubicacion donde el usuario tomo la muestra
	 * Usuario, usuario que tomo la muestra 
	 * TipoDeInsecto, especie a la que pertenece la vinchuca fotografiada
	 */
	
	public Muestra(String foto, Ubicacion ubicacion, Usuario autor, TipoDeInsecto resultadoActual, GestorDeEventos gestor) {
		
		this.setFoto(foto);
		this.setUbicacion(ubicacion);
		this.setAutor(autor);
		this.setFechaDeCreacion(LocalDate.now());
		this.setGestorDeEventos(gestor);
		
		this.setEstadoDeVerificacion(new EstadoNoVerificado());           // Inicializo primero el estado no verificada al solo tener la opinion del autor
		this.agregarOpinion(new Opinion(autor, resultadoActual, false));  // Agrega opinion inicial del autor a la muestra
		
	}
	
	public LocalDate getFechaDeCreacion() {  // Getter fecha de creacion de la muestra, devuelva un LocalDate
		return this.fechaDeCreacion;
	}
	
	private void setFechaDeCreacion(LocalDate fecha) {
		this.fechaDeCreacion = fecha;
	}
	
	public String getFoto() {  // Getter foto de la muestra, devuelve un String
		return this.foto;
	}
	
	private void setFoto(String foto) {
		this.foto = foto;
	}
	
	public Ubicacion getUbicacion() {  // Getter ubicacion de la muestra, devuelve una Ubicacion 
		return this.ubicacion;
	}
	
	private void setUbicacion(Ubicacion ubicacion) {
		this.ubicacion = ubicacion;
	}
	
	public Usuario getUsuario() {  // Getter usuario de la muestra, devuelve un Usuario
		return this.autor;
	}
	
	private void setAutor(Usuario usuario) {
		this.autor = usuario; 
	}

	private GestorDeEventos getGestorDeEventos() {
		return this.gestor;
	}
	
	private void setGestorDeEventos(GestorDeEventos gestor) {
		this.gestor = gestor;
	}
	
	
	public List<Opinion> getOpiniones() {  // Getter opiniones de la muestra, devuelve una Lista de Opinion
		return this.opiniones; 
	}
	
	public EstadoDeVerificacion getEstadoDeVerificacion() {  // Getter estado de verificacion de la muestra, devuelve un EstadoDeVerificacion
		return this.estadoDeMuestra;
	}
 
	
	public void setEstadoDeVerificacion(EstadoDeVerificacion estadoDeMuestra) {  // Setter del estado de verificacion, cambia el estado de la muestra
		this.estadoDeMuestra = estadoDeMuestra;
	}
	
	public TipoDeInsecto obtenerResultadoActual() {  //  resultado actual de la muestra, devuelve un TipoDeInsecto
		return this.getEstadoDeVerificacion().obtenerResultadoActual(this);
	}

	public void agregarOpinion(Opinion opinion) {  // Delega el agregado de opinion al estado de la muestra
		this.getEstadoDeVerificacion().agregarOpinion(this, opinion);
	}
	
	public void doAgregarOpinion(Opinion opinion) {
		this.getOpiniones().add(opinion);
	}
	
	public void recibioOpinionDeExperto() {
		this.getEstadoDeVerificacion().recibioOpinionDeExperto(this);
	}
	
	/*
	 * Verifica si hay algun otra opinion de un experto que coincida con su tipo de insecto ademas de la suya, si la hay, la muestra pasa a estado verificado
	 */
	
	public void verificarMuestraSiCorresponde() {
		TipoDeInsecto tipoACoincidir = this.obtenerUltimaOpinion().getTipoDeInsecto();
		Stream<Opinion> opinionesDelTipoBuscado = this.getOpiniones().stream().filter(opinionActual -> opinionActual.getEsDeUnExperto() && opinionActual.getTipoDeInsecto() == tipoACoincidir);
		if (opinionesDelTipoBuscado.count() > 1) {
			this.setEstadoDeVerificacion(new EstadoVerificado());
		}
	}
	
	public LocalDate getFechaDeUltimaVotacion() {
		return this.obtenerUltimaOpinion().getFechaDeVotacion();
	}
	
	public Opinion obtenerUltimaOpinion() {
		return this.opiniones.get(this.opiniones.size() - 1);
	}
	
	
	
	public void suscribirZonaDeCobertura(Evento evento, ZonaDeCobertura zona) {
		this.getGestorDeEventos().suscribir(evento, zona);
	}
	
	public void desuscribirZonaDeCobertura(Evento evento, ZonaDeCobertura zona) {
		this.getGestorDeEventos().desuscribir(evento, zona);
	}
	
	public void notificarZonasDeCobertura(Evento evento, ZonaDeCobertura zona) {
		this.getGestorDeEventos().notificar(evento, zona, this);
	}
	

}
