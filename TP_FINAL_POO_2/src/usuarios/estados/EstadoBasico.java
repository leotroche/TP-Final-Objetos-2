package usuarios.estados;

import muestras.Muestra;
import muestras.Opinion;
import usuarios.Usuario;
import web.PaginaWeb;

public class EstadoBasico implements EstadoDeConocimiento {
	public void opinarSobreMuestra(Usuario usuario, Muestra muestra, Opinion opinion) {
		muestra.doAgregarOpinion(opinion);
	}

	@Override
	public void opinarSobreMuestraEnProceso(Usuario usuario, Muestra muestra, Opinion opinion) {
		// En el estado basico, no se puede opinar sobre una muestra en proceso
	}
}
