package muestra;

import java.time.LocalDate;

import varios.TipoDeInsecto;

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
	
	/* 
	 * Constructor que recibe como parametro:
	 * String, foto tomada por el usuario
	 * Ubicacion, ubicacion donde el usuario tomo la muestra
	 * Usuario, usuario que tomo la muestra 
	 * TipoDeInsecto, especie a la que pertenece la vinchuca fotografiada
	 */
	
	public Muestra(String foto, Ubicacion ubicacion, Usuario autor, TipoDeInsecto resultadoActual) {
		
		this.foto = foto;
		this.ubicacion = ubicacion;
		this.autor = autor;
		this.fechaDeCreacion = LocalDate.now();
		
		this.estadoDeMuestra = new EstadoNoVerificado();           // Inicializo primero el estado no verificada al solo tener la opinion del autor
		this.agregarOpinion(new Opinion(autor, resultadoActual));  // Agrega opinion inicial del autor a la muestra
		
	}
	
	public LocalDate getFechaDeCreacion() {  // Getter fecha de creacion de la muestra, devuelva un LocalDate
		return this.fechaDeCreacion;
	}
	
	public String getFoto() {  // Getter foto de la muestra, devuelve un String
		return this.foto;
	}
	
	public Ubicacion getUbicacion() {  // Getter ubicacion de la muestra, devuelve una Ubicacion 
		return this.ubicacion;
	}
	
	public Usuario getUsuario() {  // Getter usuario de la muestra, devuelve un Usuario
		return this.autor;
	}

	public TipoDeInsecto getResultadoActual() {  // Getter resultado actual de la muestra, devuelve un TipoDeInsecto
		return this.resultadoActual;
	}
	
	public List<Opinion> getOpiniones() {  // Getter opiniones de la muestra, devuelve una Lista de Opinion
		return this.opiniones; 
	}
	
	public EstadoDeVerificacion getEstadoDeVerificacion() {  // Getter estado de verificacion de la muestra, devuelve un EstadoDEVerificacion
		return this.estadoDeMuestra;
	}

	public LocalDate getFechaDeUltimaVotacion() {  
		// TODO Auto-generated method stub
		return null;
	}
	
	public void setEstadoDeVerificacion(EstadoDeVerificacon estadoDeMuestra) {  // Setter del estado de verificacion, cambia el estado de la muestra
		return this.estadoDeMuestra = estadoDeMuestra;
	}
	

	public void agregarOpinion(Opinion opinion) {  // Delega el agregado de opinion al estado de la muestra
		this.getEstadoDeVerificacion().agregarOpinion(this, opinion);
	}
	
	

}
