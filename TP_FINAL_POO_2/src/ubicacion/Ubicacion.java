package ubicacion;

import java.util.List;

import muestra.Muestra;

public class Ubicacion {
	private double latitud;
	private double longitud;

	/**
	 * Crea una nueva ubicación geográfica especificando latitud y longitud en grados decimales.
	 * <p>
	 * La latitud debe estar en el rango [-90, 90], por ejemplo: -34.599722.<br>
	 * La longitud debe estar en el rango [-180, 180], por ejemplo: -58.381944.
	 * </p>
	 *
	 * @param latitud  latitud en grados decimales (sur es negativo, norte positivo)
	 * @param longitud longitud en grados decimales (oeste es negativo, este positivo)
	 */
	public Ubicacion(double latitud, double longitud) {
		this.setLatitud(latitud);
		this.setLongitud(longitud);
	}

	// --------------------------------------------------------------------------------

	public double getLatitud() {
		return this.latitud;
	}

	public double getLongitud() {
		return this.longitud;
	}

	private void setLatitud(double latitud) {
		if (latitud < -90 || latitud > 90) {
			// throw new IllegalArgumentException("Latitud debe estar entre -90 y 90 grados.");
		}
		this.latitud = latitud;
	}

	private void setLongitud(double longitud) {
		if (longitud < -180 || longitud > 180) {
			// throw new IllegalArgumentException("Longitud debe estar entre -180 y 180 grados.");
		}
		this.longitud = longitud;
	}

	// --------------------------------------------------------------------------------

	public double calcularDistanciaKm(Ubicacion otraUbicacion) {
		final double RADIO_TIERRA_KM = 6371.0;

		double lat1 = Math.toRadians(this.getLatitud());
		double lon1 = Math.toRadians(this.getLongitud());

		double lat2 = Math.toRadians(otraUbicacion.getLatitud());
		double lon2 = Math.toRadians(otraUbicacion.getLongitud());

		double dLat = lat2 - lat1;
		double dLon = lon2 - lon1;

		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
				Math.cos(lat1) * Math.cos(lat2) *
				Math.sin(dLon / 2) * Math.sin(dLon / 2);

		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

		return RADIO_TIERRA_KM * c;
	}

	public double calcularDistanciaMetros(Ubicacion otraUbicacion) {
		return this.calcularDistanciaKm(otraUbicacion) * 1000;
	}

	// --------------------------------------------------------------------------------

	public List<Ubicacion> filtrarUbicacionesPorDistanciaKm(List<Ubicacion> ubicaciones,	double maxKm) {
		return ubicaciones.stream()
				.filter(ubicacion -> this.calcularDistanciaKm(ubicacion) <= maxKm)
				.toList();
	}

	public List<Ubicacion> filtrarUbicacionesPorDistanciaMetros(List<Ubicacion> ubicaciones, double maxMetros) {
		return this.filtrarUbicacionesPorDistanciaKm(ubicaciones, maxMetros / 1000);
	}

	// --------------------------------------------------------------------------------

	public List<Muestra> filtrarMuestrasPorDistanciaKm(List<Muestra> muestras, double maxKm) {
		return muestras.stream()
				.filter(muestra -> this.calcularDistanciaKm(muestra.getUbicacion()) <= maxKm)
				.toList();
	}

	public List<Muestra> filtrarMuestrasPorDistanciaMetros(List<Muestra> muestras, double maxMetros) {
		return filtrarMuestrasPorDistanciaKm(muestras, maxMetros / 1000);
	}
}
