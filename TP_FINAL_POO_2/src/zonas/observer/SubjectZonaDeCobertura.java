package zonas.observer;

import muestras.Muestra;

public interface SubjectZonaDeCobertura {
	void suscribirMuestraCargada(ObserverZonaDeCobertura observer);
	void desuscribirMuestraCargada(ObserverZonaDeCobertura observer);
	void notificarMuestraCargada(Muestra muestra);

	void suscribirMuestraValidada(ObserverZonaDeCobertura observer);
	void desuscribirMuestraValidada(ObserverZonaDeCobertura observer);
	void notificarMuestraValidada(Muestra muestra);
}
