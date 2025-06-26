package usuarios.estados;

import muestras.Muestra;
import muestras.Opinion;
import usuarios.Usuario;

public class EstadoEspecialista implements EstadoDeConocimiento {
	@Override
	public void opinarSobreMuestra(Usuario usuario, Muestra muestra, Opinion opinion) {
		muestra.doAgregarOpinion(opinion);
		muestra.recibioOpinionDeExperto();
	}
	
	@Override
	public void opinarSobreMuestraEnProceso(Usuario usuario, Muestra muestra, Opinion opinion) {
		muestra.doAgregarOpinion(opinion);
		muestra.recibioOpinionDeExperto();
	}
}
