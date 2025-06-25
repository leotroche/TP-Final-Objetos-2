package muestra;
 
import varios.Opinion;
import varios.TipoDeInsecto;

public interface EstadoDeVerificacion {

	public void agregarOpinion(Muestra muestra, Opinion opinion);
	
	void recibioOpinionDeExperto(Muestra muestra);
	
	public TipoDeInsecto obtenerResultadoActual(Muestra muestra);
}


 
