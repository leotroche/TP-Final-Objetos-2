package usuario.estado.conocimiento;

import muestra.Muestra;
import usuario.Usuario;
import varios.Opinion;

public class EstadoEspecialista implements EstadoDeConocimiento {

	@Override
	public void opinarSobreMuestra(Usuario usuario, Muestra muestra, Opinion opinion) {
		muestra.doAgregarOpinion(opinion);
		muestra.recibirOpinionDeExperto(opinion);
	}
	
	@Override
	public void opinarSobreMuestraEnProceso(Usuario usuario, Muestra muestra, Opinion opinion) {
		muestra.doAgregarOpinion(opinion);
		muestra.recibirOpinionDeExperto(opinion);
	}

	@Override
	public void promocionar(Usuario usuario) {
	}

	@Override
	public void degradar(Usuario usuario) {
	}

}
