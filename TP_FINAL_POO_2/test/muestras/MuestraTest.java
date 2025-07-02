package muestras;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import muestras.estados.EstadoDeVerificacion;
import muestras.estados.EstadoNoVerificado;
import muestras.estados.EstadoVerificado;
import muestras.observer.ObserverMuestra;
import ubicaciones.Ubicacion;
import usuarios.Usuario;

class MuestraTest {
private Muestra muestra;

private String foto;
private Ubicacion ubicacion;
private Usuario autor;
private TipoDeInsecto tipoDeInsecto;

	@BeforeEach
	void setUp() {
		foto = "FOTO";
		ubicacion = mock(Ubicacion.class);
		autor = mock(Usuario.class);
		tipoDeInsecto = mock(TipoDeInsecto.class);

		muestra = new Muestra(foto, ubicacion, autor, tipoDeInsecto);
	}

	// ------------------------------------------------------------

	@Test
	void getFoto() {
		assertEquals(foto, muestra.getFoto());
	}

	@Test
	void getUbicacion() {
		assertEquals(ubicacion, muestra.getUbicacion());
	}

	@Test
	void getAutor() {
		assertEquals(autor, muestra.getAutor());
	}

	@Test
	void getTipoDeInsecto() {
		assertEquals(tipoDeInsecto, muestra.getTipoDeInsecto());
	}

	@Test
	void getFechaDeCreacionEsLocalDateNow() {
		assertEquals(LocalDate.now(), muestra.getFechaDeCreacion());
	}

	// ------------------------------------------------------------
	// Estados de Verificacion
	// ------------------------------------------------------------


	@Test
	void unaMuestraInicialmenteNoTieneOpiniones() {
		assertTrue(muestra.getOpiniones().isEmpty());
	}

	@Test
	void getEstadoDeVerificacionInicialmenteEsEstadoNoVerificado() {
		assertTrue(muestra.getEstadoDeVerificacion() instanceof EstadoNoVerificado);
	}

	@Test
	void setEstadoDeVerificacionCambiaElEstadoCorrectamente() {
		EstadoDeVerificacion nuevoEstado = mock(EstadoDeVerificacion.class);
		muestra.setEstadoDeVerificacion(nuevoEstado);

		assertEquals(nuevoEstado, muestra.getEstadoDeVerificacion());
	}

	// ------------------------------------------------------------

	@Test
	void agregarOpinionDelegaAEstadoDeVerificacion() {
		Muestra muestraSpy = spy(muestra);
		EstadoDeVerificacion estado = mock(EstadoDeVerificacion.class);

		doReturn(estado).when(muestraSpy).getEstadoDeVerificacion();

		Opinion opinion = mock(Opinion.class);
		muestraSpy.agregarOpinion(opinion);

		verify(estado).agregarOpinion(muestraSpy, opinion);
	}

	@Test
	void agregarOpinionNoAgregaLaMismaOpinionDosVeces() {
		Muestra muestraSpy = spy(muestra);
		Opinion opinion = mock(Opinion.class);

		// Simulamos que la muestra ya tiene esa opinión
		doReturn(List.of(opinion)).when(muestraSpy).getOpiniones();

		// Intentamos agregar la misma opinión nuevamente
		muestraSpy.agregarOpinion(opinion);

		// Verificamos que no se llame a doAgregarOpinion
		verify(muestraSpy, never()).doAgregarOpinion(opinion);
	}

	// ------------------------------------------------------------

	@Test
	void doAgregarOpinionAgregaOpinionALista() {
		Opinion opinion = mock(Opinion.class);
		muestra.doAgregarOpinion(opinion);

		assertEquals(1, muestra.getOpiniones().size());
		assertTrue(muestra.getOpiniones().contains(opinion));
	}

	// ------------------------------------------------------------

	@Test
	void recibioOpinionDeExpertoDelegaAEstadoDeVerificacion() {
		Muestra muestraSpy = spy(muestra);
		EstadoDeVerificacion estado = mock(EstadoDeVerificacion.class);

		doReturn(estado).when(muestraSpy).getEstadoDeVerificacion();

		muestraSpy.recibioOpinionDeExperto();

		verify(estado).recibioOpinionDeExperto(muestraSpy);
	}

	// ------------------------------------------------------------

	@Test
	void obtenerResultadoActualDelegaAEstadoDeVerificacion() {
		Muestra muestraSpy = spy(muestra);
		EstadoDeVerificacion estado = mock(EstadoDeVerificacion.class);

		doReturn(estado).when(muestraSpy).getEstadoDeVerificacion();

		muestraSpy.obtenerResultadoActual();

		verify(estado).obtenerResultadoActual(muestraSpy);
	}

	// ------------------------------------------------------------

	@Test
	void verificarMuestraCambiaEstadoAVerificadoSiHayOpinionesCoincidentes() {
		Muestra muestraSpy = spy(muestra);

		Opinion opinion = mock(Opinion.class);
		TipoDeInsecto tipoDeInsecto = mock(TipoDeInsecto.class);

		doReturn(tipoDeInsecto).when(opinion).getTipoDeInsecto();

		muestraSpy.doAgregarOpinion(opinion);
		muestraSpy.doAgregarOpinion(opinion);

		doReturn(opinion).when(muestraSpy).obtenerUltimaOpinion();
		doReturn(2).when(muestraSpy).cantidadDeOpinionesConTipo(tipoDeInsecto);

		muestraSpy.verificarMuestraSiCorresponde();

		verify(muestraSpy).setEstadoDeVerificacion(any(EstadoVerificado.class));
	}

	// ------------------------------------------------------------

	@Test
	void verificarMuestraNotificaObservadoresSiHayOpinionesCoincidentes() {
		Muestra muestraSpy = spy(muestra);

		Opinion opinion = mock(Opinion.class);
		TipoDeInsecto tipoDeInsecto = mock(TipoDeInsecto.class);

		doReturn(tipoDeInsecto).when(opinion).getTipoDeInsecto();

		muestraSpy.doAgregarOpinion(opinion);
		muestraSpy.doAgregarOpinion(opinion);

		doReturn(opinion).when(muestraSpy).obtenerUltimaOpinion();
		doReturn(2).when(muestraSpy).cantidadDeOpinionesConTipo(tipoDeInsecto);

		muestraSpy.verificarMuestraSiCorresponde();

		verify(muestraSpy).notificarMuestraValidada();
	}

	// ------------------------------------------------------------

	@Test
	void verificarMuestraNoCambiaEstadoSiNoHayOpinionesCoincidentes() {
		Muestra muestraSpy = spy(muestra);

		Opinion opinion = mock(Opinion.class);
		TipoDeInsecto tipoDeInsecto = mock(TipoDeInsecto.class);

		doReturn(tipoDeInsecto).when(opinion).getTipoDeInsecto();

		muestraSpy.doAgregarOpinion(opinion);

		doReturn(opinion).when(muestraSpy).obtenerUltimaOpinion();
		doReturn(1).when(muestraSpy).cantidadDeOpinionesConTipo(tipoDeInsecto);

		muestraSpy.verificarMuestraSiCorresponde();

		verify(muestraSpy, never()).setEstadoDeVerificacion(any(EstadoVerificado.class));
	}

	// ------------------------------------------------------------

	@Test
	void verificarMuestraNoNotificaObservadoresSiNoHayOpinionesCoincidentes() {
		Muestra muestraSpy = spy(muestra);

		Opinion opinion = mock(Opinion.class);
		TipoDeInsecto tipoDeInsecto = mock(TipoDeInsecto.class);

		doReturn(tipoDeInsecto).when(opinion).getTipoDeInsecto();

		muestraSpy.doAgregarOpinion(opinion);

		doReturn(opinion).when(muestraSpy).obtenerUltimaOpinion();
		doReturn(1).when(muestraSpy).cantidadDeOpinionesConTipo(tipoDeInsecto);

		muestraSpy.verificarMuestraSiCorresponde();

		verify(muestraSpy, never()).notificarMuestraValidada();
	}

	// ------------------------------------------------------------

	@Test
	void cantidadDeOpinionesConTipoCuentaCorrectamente() {
		TipoDeInsecto tipoDeInsecto1 = mock(TipoDeInsecto.class);
		TipoDeInsecto tipoDeInsecto2 = mock(TipoDeInsecto.class);

		Opinion opinion1 = mock(Opinion.class);
		Opinion opinion2 = mock(Opinion.class);
		Opinion opinion3 = mock(Opinion.class);

		when(opinion1.getTipoDeInsecto()).thenReturn(tipoDeInsecto1);
		when(opinion2.getTipoDeInsecto()).thenReturn(tipoDeInsecto1);
		when(opinion3.getTipoDeInsecto()).thenReturn(tipoDeInsecto2);

		muestra.doAgregarOpinion(opinion1);
		muestra.doAgregarOpinion(opinion2);
		muestra.doAgregarOpinion(opinion3);

		assertEquals(2, muestra.cantidadDeOpinionesConTipo(tipoDeInsecto1));
		assertEquals(1, muestra.cantidadDeOpinionesConTipo(tipoDeInsecto2));
	}

	// ------------------------------------------------------------

	@Test
	void opinionesDeExpertosFiltraSoloExpertos() {
		Opinion opinion1 = mock(Opinion.class);
		Opinion opinion2 = mock(Opinion.class);
		Opinion opinion3 = mock(Opinion.class);

		when(opinion1.getEsOpinionDeExperto()).thenReturn(true);
		when(opinion2.getEsOpinionDeExperto()).thenReturn(false);
		when(opinion3.getEsOpinionDeExperto()).thenReturn(true);

		muestra.doAgregarOpinion(opinion1);
		muestra.doAgregarOpinion(opinion2);
		muestra.doAgregarOpinion(opinion3);

		List<Opinion> expertos = muestra.opinionesDeExpertos();

		assertEquals(2, expertos.size());
		assertTrue(expertos.contains(opinion1));
		assertTrue(expertos.contains(opinion3));
		assertFalse(expertos.contains(opinion2));
	}

	// ------------------------------------------------------------

	@Test
	void tipoMasFrecuenteEnOpinionesDevuelveElCorrecto() {
		TipoDeInsecto tipoDeInsecto1 = mock(TipoDeInsecto.class);
		TipoDeInsecto tipoDeInsecto2 = mock(TipoDeInsecto.class);

		Opinion opinion1 = mock(Opinion.class);
		Opinion opinion2 = mock(Opinion.class);
		Opinion opinion3 = mock(Opinion.class);
		Opinion opinion4 = mock(Opinion.class);

		when(opinion1.getTipoDeInsecto()).thenReturn(tipoDeInsecto1);
		when(opinion2.getTipoDeInsecto()).thenReturn(tipoDeInsecto1);
		when(opinion3.getTipoDeInsecto()).thenReturn(tipoDeInsecto2);
		when(opinion4.getTipoDeInsecto()).thenReturn(tipoDeInsecto1);

		muestra.doAgregarOpinion(opinion1);
		muestra.doAgregarOpinion(opinion2);
		muestra.doAgregarOpinion(opinion3);
		muestra.doAgregarOpinion(opinion4);

		assertEquals(tipoDeInsecto1, muestra.tipoMasFrecuenteEnOpiniones());
	}

	// ------------------------------------------------------------

	@Test
	void obtenerUltimaOpinionDevuelveLaUltimaOpinionAgregada() {
		Opinion opinion1 = mock(Opinion.class);
		Opinion opinion2 = mock(Opinion.class);

		muestra.doAgregarOpinion(opinion1);
		muestra.doAgregarOpinion(opinion2);

		assertEquals(opinion2, muestra.obtenerUltimaOpinion());
	}

	// ------------------------------------------------------------

	@Test
	void getFechaDeUltimaVotacionDevuelveLaFechaDeLaUltimaOpinion() {
		Opinion opinion1 = mock(Opinion.class);
		Opinion opinion2 = mock(Opinion.class);

		LocalDate fecha1 = LocalDate.of(2024, 1, 1);
		LocalDate fecha2 = LocalDate.of(2025, 6, 20);

		when(opinion1.getFechaDeVotacion()).thenReturn(fecha1);
		when(opinion2.getFechaDeVotacion()).thenReturn(fecha2);

		muestra.doAgregarOpinion(opinion1);
		muestra.doAgregarOpinion(opinion2);

		assertEquals(fecha2, muestra.getFechaDeUltimaVotacion());
	}

	// ------------------------------------------------------------
	// Metodos de Observer
	// ------------------------------------------------------------

	@Test
	void unaMuestraInicialmenteNoTieneObservadoresDeMuestraValidada() {
		assertTrue(muestra.getObservadoresDeMuestraValidada().isEmpty());
	}

	// ------------------------------------------------------------

	@Test
	void suscribirMuestraValidadaSuscribeCorrectamenteAUnObservador() {
		ObserverMuestra observer = mock(ObserverMuestra.class);

		muestra.suscribirMuestraValidada(observer);

		assertEquals(1, muestra.getObservadoresDeMuestraValidada().size());
		assertTrue(muestra.getObservadoresDeMuestraValidada().contains(observer));
	}

	// ------------------------------------------------------------

	@Test
	void suscribirMuestraValidadaNoAgregaDosVecesAlMismoObservador() {
		ObserverMuestra observer = mock(ObserverMuestra.class);

		muestra.suscribirMuestraValidada(observer);
		muestra.suscribirMuestraValidada(observer);

		assertEquals(1, muestra.getObservadoresDeMuestraValidada().size());
	}

	// ------------------------------------------------------------

	@Test
	void desuscribirMuestraValidadaDesuscribeCorrectamenteAUnObservador() {
		ObserverMuestra observer = mock(ObserverMuestra.class);

		muestra.suscribirMuestraValidada(observer);
		muestra.desuscribirMuestraValidada(observer);

		assertTrue(muestra.getObservadoresDeMuestraValidada().isEmpty());
		assertFalse(muestra.getObservadoresDeMuestraValidada().contains(observer));
	}

	// ------------------------------------------------------------

	@Test
	void desuscribirMuestraValidadaNoHaceNadaSiElObservadorNoEstaSuscrito() {
		ObserverMuestra observer = mock(ObserverMuestra.class);

		muestra.desuscribirMuestraValidada(observer);

		assertTrue(muestra.getObservadoresDeMuestraValidada().isEmpty());
		assertFalse(muestra.getObservadoresDeMuestraValidada().contains(observer));
	}

	// ------------------------------------------------------------

	@Test
	void notificarMuestraValidadaNotificaCorrectamenteALosObservadores() {
		ObserverMuestra observer1 = mock(ObserverMuestra.class);
		ObserverMuestra observer2 = mock(ObserverMuestra.class);

		muestra.suscribirMuestraValidada(observer1);
		muestra.suscribirMuestraValidada(observer2);

		muestra.notificarMuestraValidada();

		verify(observer1).updateMuestraValidada(muestra);
		verify(observer2).updateMuestraValidada(muestra);
	}
}
