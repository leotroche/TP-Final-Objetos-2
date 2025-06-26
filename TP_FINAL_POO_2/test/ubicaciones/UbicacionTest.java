package ubicaciones;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import muestras.Muestra;

class UbicacionTest {
	private Ubicacion buenosAires;
	private Ubicacion ushuaia;

	// Ubicaciones cercanas a Buenos Aires
	private Ubicacion obelisco;
	private Ubicacion retiro;
	private Ubicacion tigre;
	private Ubicacion laPlata;
	private List<Ubicacion> ubicacionesCercanas;

	// Muestras
	private Muestra muestraObelisco;
	private Muestra muestraRetiro;
	private Muestra muestraTigre;
	private Muestra muestraLaPlata;
	private List<Muestra> muestras;

	private final double TOLERANCIA_KM = 1.0;

	// --------------------------------------------------------------------------------

	@BeforeEach
	void setUp() {
		// Buenos Aires: Latitud: -34.599722, Longitud: -58.381944
		buenosAires = new Ubicacion(-34.599722, -58.381944);

		// Ushuaia: Latitud: -54.807222, Longitud: -68.304444
		ushuaia = new Ubicacion(-54.807222, -68.304444);

		// Ubicaciones cercanas a Buenos Aires
		obelisco = new Ubicacion(-34.6037389, -58.3815704); // Aprox. 0.45 km
		retiro = new Ubicacion(-34.594722, -58.375833);     // Aprox. 0.79 km
		tigre = new Ubicacion(-34.416667, -58.583333);      // Aprox. 27.5 km
		laPlata = new Ubicacion(-34.933333, -57.95);        // Aprox. 54.5 km

		ubicacionesCercanas = Arrays.asList(obelisco, retiro, tigre, laPlata);

		// Mocks & Stubs de Muestra
		muestraObelisco = mock(Muestra.class);
		muestraRetiro = mock(Muestra.class);
		muestraTigre = mock(Muestra.class);
		muestraLaPlata = mock(Muestra.class);

		when(muestraObelisco.getUbicacion()).thenReturn(obelisco); // Aprox. 0.45 km
		when(muestraRetiro.getUbicacion()).thenReturn(retiro);     // Aprox. 0.79 km
		when(muestraTigre.getUbicacion()).thenReturn(tigre);       // Aprox. 27.5 km
		when(muestraLaPlata.getUbicacion()).thenReturn(laPlata);   // Aprox. 54.5 km

		muestras = Arrays.asList(muestraObelisco, muestraRetiro, muestraTigre, muestraLaPlata);
	}

	// --------------------------------------------------------------------------------

	@Test
	void getLatitud() {
		// Exercise & Verify
		assertEquals(-34.599722, buenosAires.getLatitud());
	}

	@Test
	void getLongitud() {
		// Exercise & Verify
		assertEquals(-58.381944, buenosAires.getLongitud());
	}

	// --------------------------------------------------------------------------------

	@Test
	void calcularDistanciaKm() {
		// Exercise
		double distancia = buenosAires.calcularDistanciaKm(ushuaia);

		// Verify: 2374.4 km
		assertEquals(2374.4, distancia, TOLERANCIA_KM);
	}

	@Test
	void calcularDistanciaMetros() {
		// Exercise
		double distancia = buenosAires.calcularDistanciaMetros(ushuaia);

		// Verify: 2374400.0 m
		assertEquals(2374400.0, distancia, TOLERANCIA_KM * 1000);
	}

	// --------------------------------------------------------------------------------

	@Test
	void filtrarUbicacionesPorDistanciaKm_ConResultados() {
		// Exercise
		List<Ubicacion> cercanas = buenosAires.filtrarUbicacionesPorDistanciaKm(ubicacionesCercanas, 10);

		// Assert
		assertEquals(2, cercanas.size());
		assertTrue(cercanas.contains(obelisco));
		assertTrue(cercanas.contains(retiro));
	}

	@Test
	void filtrarUbicacionesPorDistanciaKm_SinResultados() {
		// Exercise
		List<Ubicacion> cercanas = ushuaia.filtrarUbicacionesPorDistanciaKm(ubicacionesCercanas, 10);

		// Assert
		assertTrue(cercanas.isEmpty());
	}

	// --------------------------------------------------------------------------------

	@Test
	void filtrarUbicacionesPorDistanciaMetros_ConResultados() {
		// Exercise
		List<Ubicacion> cercanas = buenosAires.filtrarUbicacionesPorDistanciaMetros(ubicacionesCercanas, 1000);

		// Assert
		assertEquals(2, cercanas.size());
		assertTrue(cercanas.contains(obelisco));
		assertTrue(cercanas.contains(retiro));
	}

	@Test
	void filtrarUbicacionesPorDistanciaMetros_SinResultados() {
		// Exercise
		List<Ubicacion> cercanas = ushuaia.filtrarUbicacionesPorDistanciaMetros(ubicacionesCercanas, 1000);

		// Assert
		assertTrue(cercanas.isEmpty());
	}

	// --------------------------------------------------------------------------------

	@Test
	void filtrarMuestrasPorDistanciaKm_ConResultados() {
		// Exercise
		List<Muestra> cercanas = buenosAires.filtrarMuestrasPorDistanciaKm(muestras, 10);

		// Assert
		assertEquals(2, cercanas.size());
		assertTrue(cercanas.contains(muestraObelisco));
		assertTrue(cercanas.contains(muestraRetiro));
	}

	@Test
	void filtrarMuestrasPorDistanciaKm_SinResultados() {
		// Exercise
		List<Muestra> cercanas = ushuaia.filtrarMuestrasPorDistanciaKm(muestras, 10);

		// Assert
		assertTrue(cercanas.isEmpty());
	}

	// --------------------------------------------------------------------------------

	@Test
	void filtrarMuestrasPorDistanciaMetros_ConResultados() {
		// Exercise
		List<Muestra> cercanas = buenosAires.filtrarMuestrasPorDistanciaMetros(muestras, 1000);

		// Assert
		assertEquals(2, cercanas.size());
		assertTrue(cercanas.contains(muestraObelisco));
		assertTrue(cercanas.contains(muestraRetiro));
	}

	@Test
	void filtrarMuestrasPorDistanciaMetros_SinResultados() {
		// Exercise
		List<Muestra> cercanas = ushuaia.filtrarMuestrasPorDistanciaMetros(muestras, 1000);

		// Assert
		assertTrue(cercanas.isEmpty());
	}
}
