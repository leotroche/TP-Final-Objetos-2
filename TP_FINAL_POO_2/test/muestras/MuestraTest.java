package muestras;

import static org.junit.Assert.assertEquals;
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

import eventos.Evento;
import gestores.eventos.GestorDeEventos;
import muestras.estados.EstadoDeVerificacion;
import muestras.estados.EstadoVerificado;
import ubicaciones.Ubicacion;
import usuarios.Usuario;
import zonas.ZonaDeCobertura;

class MuestraTest {
	private Muestra muestra;

	private String foto;
	private Ubicacion ubicacion;
	private Usuario autor;
	private TipoDeInsecto tipoDeInsecto;
	private GestorDeEventos gestorDeEventos;

	@BeforeEach
	void setUp() {
		foto = "FOTO";
		ubicacion = mock(Ubicacion.class);
		autor = mock(Usuario.class);
		tipoDeInsecto = mock(TipoDeInsecto.class);
		gestorDeEventos = mock(GestorDeEventos.class);

		muestra = new Muestra(foto, ubicacion, autor, tipoDeInsecto, gestorDeEventos);
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
	void agregarOpinionDelegaAEstadoDeVerificacion() {
		Muestra muestraSpy = spy(muestra);
		EstadoDeVerificacion estadoMock = mock(EstadoDeVerificacion.class);

		doReturn(estadoMock).when(muestraSpy).getEstadoDeVerificacion();

		Opinion opinion = mock(Opinion.class);
		muestraSpy.agregarOpinion(opinion);

		verify(estadoMock).agregarOpinion(muestraSpy, opinion);
	}

	@Test
	void doAgregarOpinionAgregaOpinionALista() {
		Opinion opinion = mock(Opinion.class);
		muestra.doAgregarOpinion(opinion);

		assertEquals(1, muestra.getOpiniones().size());
		assertTrue(muestra.getOpiniones().contains(opinion));
	}

	@Test
	void recibioOpinionDeExpertoDelegaAEstadoDeVerificacion() {
		Muestra muestraSpy = spy(muestra);
		EstadoDeVerificacion estadoMock = mock(EstadoDeVerificacion.class);

		doReturn(estadoMock).when(muestraSpy).getEstadoDeVerificacion();

		muestraSpy.recibioOpinionDeExperto();

		verify(estadoMock).recibioOpinionDeExperto(muestraSpy);
	}

	@Test
	void obtenerResultadoActualDelegaAEstadoDeVerificacion() {
		Muestra muestraSpy = spy(muestra);
		EstadoDeVerificacion estadoMock = mock(EstadoDeVerificacion.class);
		TipoDeInsecto tipoMock = mock(TipoDeInsecto.class);

		doReturn(estadoMock).when(muestraSpy).getEstadoDeVerificacion();
		doReturn(tipoMock).when(estadoMock).obtenerResultadoActual(muestraSpy);

		TipoDeInsecto resultado = muestraSpy.obtenerResultadoActual();

		assertEquals(tipoMock, resultado);
	}

	// ------------------------------------------------------------

	@Test
	void verificarMuestraSiCorrespondeVerificaYNotifica() {
		Muestra muestraSpy = spy(muestra);
		Opinion opinion = mock(Opinion.class);
		TipoDeInsecto tipoDeInsectoMock = mock(TipoDeInsecto.class);

		when(opinion.getTipoDeInsecto()).thenReturn(tipoDeInsectoMock);

		muestraSpy.doAgregarOpinion(opinion);
		muestraSpy.doAgregarOpinion(opinion);

		when(muestraSpy.cantidadDeOpinionesConTipo(tipoDeInsectoMock)).thenReturn(2);

		muestraSpy.verificarMuestraSiCorresponde();

		verify(muestraSpy).setEstadoDeVerificacion(any(EstadoVerificado.class));
		verify(muestraSpy).notificarZonasDeCobertura(Evento.MUESTRA_VALIDADA, null, muestraSpy);
	}

	@Test
	void verificarMuestraSiCorrespondeNoVerificaNiNotificaSiNoHaySuficientesOpiniones() {
		Muestra muestraSpy = spy(muestra);
		Opinion opinion = mock(Opinion.class);
		TipoDeInsecto tipoDeInsectoMock = mock(TipoDeInsecto.class);

		when(opinion.getTipoDeInsecto()).thenReturn(tipoDeInsectoMock);

		muestraSpy.doAgregarOpinion(opinion);

		when(muestraSpy.cantidadDeOpinionesConTipo(tipoDeInsectoMock)).thenReturn(1);

		muestraSpy.verificarMuestraSiCorresponde();

		verify(muestraSpy, never()).setEstadoDeVerificacion(any());
		verify(muestraSpy, never()).notificarZonasDeCobertura(any(), any(), any());
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

	@Test
	void opinionesDeExpertosFiltraSoloExpertos() {
		Opinion opinion1 = mock(Opinion.class);
		Opinion opinion2 = mock(Opinion.class);
		Opinion opinion3 = mock(Opinion.class);

		when(opinion1.getEsUnExperto()).thenReturn(true);
		when(opinion2.getEsUnExperto()).thenReturn(false);
		when(opinion3.getEsUnExperto()).thenReturn(true);

		muestra.doAgregarOpinion(opinion1);
		muestra.doAgregarOpinion(opinion2);
		muestra.doAgregarOpinion(opinion3);

		List<Opinion> expertos = muestra.opinionesDeExpertos();

		assertEquals(2, expertos.size());
		assertTrue(expertos.contains(opinion1));
		assertTrue(expertos.contains(opinion3));
		assertFalse(expertos.contains(opinion2));
	}

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
	// Metodos de eventos
	// ------------------------------------------------------------

	@Test
	void suscribirZonaDeCoberturaDelegaAGestorDeEventos() {
		Evento evento = mock(Evento.class);
		ZonaDeCobertura zonaDeCobertura = mock(ZonaDeCobertura.class);

		muestra.suscribirZonaDeCobertura(evento, zonaDeCobertura);

		verify(gestorDeEventos).suscribir(evento, zonaDeCobertura);
	}

	@Test
	void desuscribirZonaDeCoberturaDelegaAGestorDeEventos() {
		Evento evento = mock(Evento.class);
		ZonaDeCobertura zonaDeCobertura = mock(ZonaDeCobertura.class);

		muestra.desuscribirZonaDeCobertura(evento, zonaDeCobertura);

		verify(gestorDeEventos).desuscribir(evento, zonaDeCobertura);
	}

	@Test
	void notificarZonasDeCoberturaDelegaAGestorDeEventos() {
		Evento evento = mock(Evento.class);
		ZonaDeCobertura zonaDeCobertura = mock(ZonaDeCobertura.class);

		muestra.notificarZonasDeCobertura(evento, zonaDeCobertura, muestra);

		verify(gestorDeEventos).notificar(evento, zonaDeCobertura, muestra);
	}
}
