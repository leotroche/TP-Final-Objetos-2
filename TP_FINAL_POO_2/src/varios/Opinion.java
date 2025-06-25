package varios;

import java.time.LocalDate;

import usuario.Usuario;

public class Opinion{
	private Usuario autor;
	private TipoDeInsecto tipoDeInsecto;
	private LocalDate fechaDeVotacion;
	
	public Opinion(Usuario user, TipoDeInsecto insecto) {
		this.setAutor(user);
		this.setTipoDeInsecto(insecto);
		this.setFechaDeVotacion(LocalDate.now());
	}

	private void setFechaDeVotacion(LocalDate date) {
		this.fechaDeVotacion = date;
	}

	public Usuario getAutor() {
		return this.autor;
	}

	public TipoDeInsecto getTipoDelInsecto() {
		return this.tipoDeInsecto;
	}

	public void setAutor(Usuario usuario) {
		this.autor = usuario;
	}

	public void setTipoDeInsecto(TipoDeInsecto insecto) {
		this.tipoDeInsecto = insecto;
	}

	public LocalDate getFechaDeVotacion() {
		return this.fechaDeVotacion;
	}
	
}