package zonas;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import muestras.Muestra;
import ubicaciones.Ubicacion;
import zonas.observer.ObserverZonaDeCobertura;

class ZonaCoberturaTest {
	private ZonaDeCobertura zona;

	private Ubicacion epicentro;
	private String nombre;
	private double radio;

	@BeforeEach
	void setUp() {
		nombre = "Zona 1";
		epicentro = mock(Ubicacion.class);
		radio = 50.0;

		zona = new ZonaDeCobertura(nombre, epicentro, radio);
	}

	// ------------------------------------------------------------

	@Test
	void getNombreTest() {
		assertEquals(nombre, zona.getNombre());
	}

	@Test
	void getEpicentroTest() {
		assertEquals(epicentro, zona.getEpicentro());
	}

	@Test
	void getRadioTest() {
		assertEquals(radio, zona.getRadio());
	}

	// ------------------------------------------------------------

	@Test
	void unaZonaDeCoberturaInicialmenteNoTieneMuestras() {
		assertTrue(zona.getMuestras().isEmpty());
	}

	@Test
	void agregarMuestraAgregaUnaMuestraALaZona() {
		Muestra muestra = mock(Muestra.class);

		zona.agregarMuestra(muestra);

		assertEquals(1, zona.getMuestras().size());
		assertTrue(zona.getMuestras().contains(muestra));
	}

	// ------------------------------------------------------------

	@Test
	void perteneceALaZonaDevuelveTrueCuandoEstaDentroDelRadio() {
		Muestra muestra = mock(Muestra.class);
		Ubicacion ubicacion = mock(Ubicacion.class);

		when(muestra.getUbicacion()).thenReturn(ubicacion);
		when(epicentro.calcularDistanciaKm(ubicacion)).thenReturn(5.0);

		assertTrue(zona.perteneceALaZona(muestra));
	}

	@Test
	void perteneceALaZonaDevuelveFalseCuandoEstaFueraDelRadio() {
		Muestra muestra = mock(Muestra.class);
		Ubicacion ubicacion = mock(Ubicacion.class);

		when(muestra.getUbicacion()).thenReturn(ubicacion);
		when(epicentro.calcularDistanciaKm(ubicacion)).thenReturn(100.0);

		assertFalse(zona.perteneceALaZona(muestra));
	}

	// ------------------------------------------------------------

	@Test
	void seSolapaConDevuelveTrueCuandoLasZonasSeSuperponen() {
		Ubicacion otroEpicentro = mock(Ubicacion.class);
		ZonaDeCobertura otraZona = new ZonaDeCobertura("Zona 2", otroEpicentro, 10.0);

		when(epicentro.calcularDistanciaKm(otroEpicentro)).thenReturn(15.0);

		assertTrue(zona.seSolapaCon(otraZona));
	}

	@Test
	void seSolapaConDevuelveFalseCuandoLasZonasNoSeSuperponen() {
		Ubicacion otroEpicentro = mock(Ubicacion.class);
		ZonaDeCobertura otraZona = new ZonaDeCobertura("Zona 2", otroEpicentro, 10.0);

		when(epicentro.calcularDistanciaKm(otroEpicentro)).thenReturn(100.0);

		assertFalse(zona.seSolapaCon(otraZona));
	}

	// ------------------------------------------------------------

	@Test
	void zonasSolapadasDevuelveZonasQueSeSuperponen() {
		Ubicacion epicentro1 = mock(Ubicacion.class);
		Ubicacion epicentro2 = mock(Ubicacion.class);

		ZonaDeCobertura zona1 = new ZonaDeCobertura("Zona 1", epicentro1, 20.0);
		ZonaDeCobertura zona2 = new ZonaDeCobertura("Zona 2", epicentro2, 20.0);

		// Se superpone
		when(epicentro.calcularDistanciaKm(epicentro1)).thenReturn(20.0);

		// No se superpone
		when(epicentro.calcularDistanciaKm(epicentro2)).thenReturn(100.0);

		List<ZonaDeCobertura> zonas = List.of(zona1, zona2);

		assertEquals(1, zona.zonasSolapadas(zonas).size());
		assertTrue(zona.zonasSolapadas(zonas).contains(zona1));
		assertFalse(zona.zonasSolapadas(zonas).contains(zona2));
	}

	// ------------------------------------------------------------

	@Test
	void procesarNuevaMuestraAgregaYSuscribeYNotificaSiPerteneceALaZona() {
		ZonaDeCobertura zonaSpy = spy(zona);

		Muestra muestra = mock(Muestra.class);
		Ubicacion ubicacion = mock(Ubicacion.class);

		when(muestra.getUbicacion()).thenReturn(ubicacion);
		when(epicentro.calcularDistanciaKm(ubicacion)).thenReturn(10.0);

		// Exercise
		zonaSpy.procesarNuevaMuestra(muestra);

		// Verify
		verify(zonaSpy).agregarMuestra(muestra);
		verify(muestra).suscribirMuestraValidada(zonaSpy);
		verify(zonaSpy).notificarMuestraCargada(muestra);
	}

	@Test
	void procesarNuevaMuestraNoHaceNadaCuandoLaMuestraNoPerteneceALaZona() {
		ZonaDeCobertura zonaSpy = spy(zona);

		Muestra muestra = mock(Muestra.class);
		Ubicacion ubicacion = mock(Ubicacion.class);

		when(muestra.getUbicacion()).thenReturn(ubicacion);
		when(epicentro.calcularDistanciaKm(ubicacion)).thenReturn(100.0);

		// Exercise
		zonaSpy.procesarNuevaMuestra(muestra);

		// Verify
		verify(zonaSpy, never()).agregarMuestra(muestra);
		verify(muestra, never()).suscribirMuestraValidada(zonaSpy);
		verify(zonaSpy, never()).notificarMuestraCargada(muestra);
	}

	// ------------------------------------------------------------
	// Metodos de Observer (Muestra Cargada)
	// ------------------------------------------------------------

	@Test
	void unaZonaDeCoberturaInicialmenteNoTieneObservadoresDeMuestraCargada() {
		assertTrue(zona.getObservadoresDeMuestraCargada().isEmpty());
	}

	// ------------------------------------------------------------

	@Test
	void suscribirMuestraCargadaSuscribeCorrectamenteAUnObservador() {
		ObserverZonaDeCobertura observer = mock(ObserverZonaDeCobertura.class);

		zona.suscribirMuestraCargada(observer);

		assertEquals(1, zona.getObservadoresDeMuestraCargada().size());
		assertTrue(zona.getObservadoresDeMuestraCargada().contains(observer));
	}

	@Test
	void suscribirMuestraCargadaNoAgregaDosVecesAlMismoObservador() {
		ObserverZonaDeCobertura observer = mock(ObserverZonaDeCobertura.class);

		zona.suscribirMuestraCargada(observer);
		zona.suscribirMuestraCargada(observer);

		assertEquals(1, zona.getObservadoresDeMuestraCargada().size());
		assertTrue(zona.getObservadoresDeMuestraCargada().contains(observer));
	}

	// ------------------------------------------------------------

	@Test
	void desuscribirMuestraCargadaDesuscribeCorrectamenteAUnObservador() {
		ObserverZonaDeCobertura observer = mock(ObserverZonaDeCobertura.class);
		zona.suscribirMuestraCargada(observer);

		zona.desuscribirMuestraCargada(observer);

		assertTrue(zona.getObservadoresDeMuestraCargada().isEmpty());
		assertFalse(zona.getObservadoresDeMuestraCargada().contains(observer));
	}

	@Test
	void desuscribirMuestraCargadaNoHaceNadaSiElObservadorNoEstaSuscrito() {
		ObserverZonaDeCobertura observer = mock(ObserverZonaDeCobertura.class);

		zona.desuscribirMuestraCargada(observer);

		assertTrue(zona.getObservadoresDeMuestraCargada().isEmpty());
		assertFalse(zona.getObservadoresDeMuestraCargada().contains(observer));
	}

	// ------------------------------------------------------------

	@Test
	void notificarMuestraCargadaNotificaCorrectamenteALosObservadores() {
		ObserverZonaDeCobertura observer1 = mock(ObserverZonaDeCobertura.class);
		ObserverZonaDeCobertura observer2 = mock(ObserverZonaDeCobertura.class);
		Muestra muestra = mock(Muestra.class);

		zona.suscribirMuestraCargada(observer1);
		zona.suscribirMuestraCargada(observer2);

		zona.notificarMuestraCargada(muestra);

		verify(observer1).updateMuestraCargada(zona, muestra);
		verify(observer2).updateMuestraCargada(zona, muestra);
	}

	// ------------------------------------------------------------
	// Metodos de Observer (Muestra Validada)
	// ------------------------------------------------------------

	@Test
	void unaZonaDeCoberturaInicialmenteNoTieneObservadoresDeMuestraValidada() {
		assertTrue(zona.getObservadoresDeMuestraValidada().isEmpty());
	}

	// ------------------------------------------------------------

	@Test
	void suscribirMuestraValidadaSuscribeCorrectamenteAUnObservador() {
		ObserverZonaDeCobertura observer = mock(ObserverZonaDeCobertura.class);

		zona.suscribirMuestraValidada(observer);

		assertEquals(1, zona.getObservadoresDeMuestraValidada().size());
		assertTrue(zona.getObservadoresDeMuestraValidada().contains(observer));
	}

	@Test
	void suscribirMuestraValidadaNoAgregaDosVecesAlMismoObservador() {
		ObserverZonaDeCobertura observer = mock(ObserverZonaDeCobertura.class);

		zona.suscribirMuestraValidada(observer);
		zona.suscribirMuestraValidada(observer);

		assertEquals(1, zona.getObservadoresDeMuestraValidada().size());
		assertTrue(zona.getObservadoresDeMuestraValidada().contains(observer));
	}

	// ------------------------------------------------------------

	@Test
	void desuscribirMuestraValidadaDesuscribeCorrectamenteAUnObservador() {
		ObserverZonaDeCobertura observer = mock(ObserverZonaDeCobertura.class);
		zona.suscribirMuestraValidada(observer);

		zona.desuscribirMuestraValidada(observer);

		assertTrue(zona.getObservadoresDeMuestraValidada().isEmpty());
		assertFalse(zona.getObservadoresDeMuestraValidada().contains(observer));
	}

	@Test
	void desuscribirMuestraValidadaNoHaceNadaSiElObservadorNoEstaSuscrito() {
		ObserverZonaDeCobertura observer = mock(ObserverZonaDeCobertura.class);

		zona.desuscribirMuestraValidada(observer);

		assertTrue(zona.getObservadoresDeMuestraValidada().isEmpty());
		assertFalse(zona.getObservadoresDeMuestraValidada().contains(observer));
	}

	// ------------------------------------------------------------

	@Test
	void notificarMuestraValidadaNotificaCorrectamenteALosObservadores() {
		ObserverZonaDeCobertura observer1 = mock(ObserverZonaDeCobertura.class);
		ObserverZonaDeCobertura observer2 = mock(ObserverZonaDeCobertura.class);
		Muestra muestra = mock(Muestra.class);

		zona.suscribirMuestraValidada(observer1);
		zona.suscribirMuestraValidada(observer2);

		zona.notificarMuestraValidada(muestra);

		verify(observer1).updateMuestraValidada(zona, muestra);
		verify(observer2).updateMuestraValidada(zona, muestra);
	}

	// ------------------------------------------------------------

	@Test
	void updateMuestraValidadaDelegaEnNotificarMuestraValidada() {
		ZonaDeCobertura zonaSpy = spy(zona);
		Muestra muestra = mock(Muestra.class);

		zonaSpy.updateMuestraValidada(muestra);

		verify(zonaSpy).notificarMuestraValidada(muestra);
	}
}