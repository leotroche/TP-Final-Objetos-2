package usuarios.estados;

import muestras.Muestra;
import muestras.Opinion;
import usuarios.Usuario;

public interface  EstadoDeConocimiento {
	void opinarSobreMuestra(Usuario usuario, Muestra muestra, Opinion opinion);

	void opinarSobreMuestraEnProceso(Usuario usuario, Muestra muestra, Opinion opinion);
}
