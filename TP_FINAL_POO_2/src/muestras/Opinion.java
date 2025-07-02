package muestras;

import java.time.LocalDate;

import usuarios.Usuario;

public class Opinion{
private Usuario autor;
private TipoDeInsecto tipoDeInsecto;
private LocalDate fechaDeVotacion;
private boolean esOpinionDeExperto;

public Opinion(Usuario usuario, TipoDeInsecto tipoDeInsecto, boolean esOpinionDeExperto) {
this.setAutor(usuario);
this.setTipoDeInsecto(tipoDeInsecto);
this.setEsOpinionDeExperto(esOpinionDeExperto);
this.setFechaDeVotacion(LocalDate.now());
}

public Usuario getAutor() {
return this.autor;
}

private void setAutor(Usuario usuario) {
this.autor = usuario;
}

public TipoDeInsecto getTipoDeInsecto() {
return this.tipoDeInsecto;
}

private void setTipoDeInsecto(TipoDeInsecto tipoDeInsecto) {
this.tipoDeInsecto = tipoDeInsecto;
}

public boolean getEsOpinionDeExperto() {
return this.esOpinionDeExperto;
}

private void setEsOpinionDeExperto(boolean esOpinionDeExperto) {
this.esOpinionDeExperto = esOpinionDeExperto;
}

public LocalDate getFechaDeVotacion() {
return this.fechaDeVotacion;
}

private void setFechaDeVotacion(LocalDate fechaDeVotacion) {
this.fechaDeVotacion = fechaDeVotacion;
}
}
