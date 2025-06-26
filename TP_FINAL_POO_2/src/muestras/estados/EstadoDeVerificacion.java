package muestras.estados;

import muestras.Muestra;
import muestras.Opinion;
import muestras.TipoDeInsecto;

public interface EstadoDeVerificacion {

	public void agregarOpinion(Muestra muestra, Opinion opinion);

	void recibioOpinionDeExperto(Muestra muestra);

	public TipoDeInsecto obtenerResultadoActual(Muestra muestra);
}
