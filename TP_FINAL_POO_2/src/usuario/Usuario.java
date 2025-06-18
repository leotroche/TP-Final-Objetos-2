package usuario;


public class Usuario {
	private String nombre;
	private EstadoDeConocimiento estadoDeConocimiento;
	
	public Usuario(String nombre) {
		this.setNombre(nombre);
	}
	
	public void setNombre(String n) {
		this.nombre = n;
	}
	
	public String getNombre() {
		return this.nombre;
	}
	
	public EstadoDeConocimiento getEstadoDeConocimiento() {
		return this.estadoDeConocimiento;
	}
	
	public void setEstadoDeConocimiento(EstadoDeConocimiento estado) {
		this.estadoDeConocimiento = estado;
	}
}
