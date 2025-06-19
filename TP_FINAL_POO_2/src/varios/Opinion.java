package varios;

import usuario.Usuario;

public class Opinion{
	private Usuario autor;
	private TipoDeInsecto tipoDeInsecto;
	
	public Opinion(Usuario user, TipoDeInsecto insecto) {
		this.autor = user;
		this.tipoDeInsecto = insecto;
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
	
}