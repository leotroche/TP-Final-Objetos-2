package varios;

import java.time.LocalDate;

import usuario.Usuario;

public class Opinion{
	private Usuario autor;
	private TipoDeInsecto tipoDeInsecto;
	private LocalDate fechaDeVotacion;
	private boolean esOpinionDeExperto;
	
	public Opinion(Usuario user, TipoDeInsecto insecto, boolean esOpinionDeExperto) {
		this.setAutor(user);
		this.setTipoDeInsecto(insecto);
		this.setFechaDeVotacion(LocalDate.now());
		this.setEsOpinionDeExperto(esOpinionDeExperto);
	}

	private void setEsOpinionDeExperto(boolean esOpinionDeExperto) {
		this.esOpinionDeExperto = esOpinionDeExperto;
	}
	
	public boolean getEsOpinionDeExperto() {
		return esOpinionDeExperto;
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