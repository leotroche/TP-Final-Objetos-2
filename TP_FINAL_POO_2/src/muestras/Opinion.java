package muestras;

import java.time.LocalDate;

import usuarios.Usuario;

public class Opinion{
	private Usuario autor;
	private TipoDeInsecto tipoDeInsecto;
	private LocalDate fechaDeVotacion;
	private boolean esUnExperto;

	public Opinion(Usuario user, TipoDeInsecto insecto, boolean esOpinionDeExperto) {
		this.setAutor(user);
		this.setTipoDeInsecto(insecto);
		this.setFechaDeVotacion(LocalDate.now());
		this.setEsUnExperto(esOpinionDeExperto);
	}

	public Usuario getAutor() {
		return this.autor;
	}

	public TipoDeInsecto getTipoDeInsecto() {
		return this.tipoDeInsecto;
	}

	public boolean getEsUnExperto() {
		return this.esUnExperto;
	}

	public LocalDate getFechaDeVotacion() {
		return this.fechaDeVotacion;
	}

	public void setAutor(Usuario usuario) {
		this.autor = usuario;
	}

	public void setTipoDeInsecto(TipoDeInsecto insecto) {
		this.tipoDeInsecto = insecto;
	}

	private void setEsUnExperto(boolean esOpinionDeExperto) {
		this.esUnExperto = esOpinionDeExperto;
	}

	private void setFechaDeVotacion(LocalDate date) {
		this.fechaDeVotacion = date;
	}
}
