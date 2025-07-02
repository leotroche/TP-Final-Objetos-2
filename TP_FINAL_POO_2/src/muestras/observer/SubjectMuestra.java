package muestras.observer;

public interface SubjectMuestra {
	public void suscribirMuestraValidada(ObserverMuestra observer);
	public void desuscribirMuestraValidada(ObserverMuestra observer);
	public void notificarMuestraValidada();
}
