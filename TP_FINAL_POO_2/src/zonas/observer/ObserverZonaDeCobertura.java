package zonas.observer;

import muestras.Muestra;
import zonas.ZonaDeCobertura;

public interface ObserverZonaDeCobertura {
	public void updateMuestraCargada(ZonaDeCobertura zonaDeCobertura, Muestra muestra);

	public void updateMuestraValidada(ZonaDeCobertura zonaDeCobertura, Muestra muestra);
}
