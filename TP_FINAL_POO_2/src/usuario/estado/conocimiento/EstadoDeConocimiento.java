package usuario.estado.conocimiento;

import muestra.Muestra;
import usuario.Usuario;
import varios.Opinion;

public interface  EstadoDeConocimiento {

	void opinarSobreMuestra(Usuario usuario, Muestra muestra, Opinion opinion);

	void opinarSobreMuestraEnProceso(Usuario usuario, Muestra muestra, Opinion opinion);

	void promocionar(Usuario usuario);

	void degradar(Usuario usuario);
}
