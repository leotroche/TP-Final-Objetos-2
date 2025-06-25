package gestores.eventos;

import eventos.Evento;
import muestra.Muestra;
import zona.cobertura.ZonaDeCobertura;

public interface Observer {
	public void update(Evento evento, ZonaDeCobertura zonaDeCobertura, Muestra muestra);
}
