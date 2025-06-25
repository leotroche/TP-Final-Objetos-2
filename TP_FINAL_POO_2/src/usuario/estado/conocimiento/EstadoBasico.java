package usuario.estado.conocimiento;

import muestra.Muestra;
import paginaWeb.PaginaWeb;
import usuario.Usuario;
import varios.Opinion;

public class EstadoBasico implements EstadoDeConocimiento {
	public void opinarSobreMuestra(Usuario usuario, Muestra muestra, Opinion opinion) {
		muestra.doAgregarOpinion(opinion);
	}

	@Override
	public void opinarSobreMuestraEnProceso(Usuario usuario, Muestra muestra, Opinion opinion) {
	}

	@Override
	public void promocionar(Usuario usuario) {
		usuario.setEstadoDeConocimiento(new EstadoExperto());
	}

	@Override
	public void degradar(Usuario usuario) {
	}
}
