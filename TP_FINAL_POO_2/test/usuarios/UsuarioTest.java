package usuarios;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import muestras.Muestra;
import muestras.Opinion;
import usuarios.estados.EstadoDeConocimiento;
import usuarios.estados.EstadoEspecialista;
import usuarios.estados.EstadoExperto;
import web.PaginaWeb;

class UsuarioTest {
	private Muestra muestra;
	private Opinion opinion;

	@BeforeEach
	void setUp() {
		muestra = mock(Muestra.class);
		opinion = mock(Opinion.class);
	}

	@Test
	void getTieneConocimientoValido() {
		Usuario usuario = new Usuario(true); // Usuario con estado de conocimiento especialista
		assertTrue(usuario.getTieneConocimientoValido());
	}

	@Test
	void usuarioConConocimientoValidoTieneEstadoEspecialista() {
		Usuario usuario = new Usuario(true);
		assertInstanceOf(EstadoEspecialista.class, usuario.getEstadoDeConocimiento());
	}

	@Test
	void usuarioSinConocimientoValidoTieneEstadoBasico() {
		Usuario usuario = new Usuario(false);
		assertInstanceOf(usuarios.estados.EstadoBasico.class, usuario.getEstadoDeConocimiento());
	}

	// ------------------------------------------------------------

	@Test
	void unUsuarioInicialmenteNoTieneMuestrasEnviadas() {
		Usuario usuario = new Usuario(false);
		assertTrue(usuario.getMuestrasEnviadas().isEmpty());
	}

	@Test
	void unUsuarioInicialmenteNoTieneOpinionesDadas() {
		Usuario usuario = new Usuario(false);
		assertTrue(usuario.getOpinionesDadas().isEmpty());
	}

	@Test
	void agregarMuestraEnviada() {
		Usuario usuario = new Usuario(false);
		usuario.agregarMuestraEnviada(muestra);
		assertTrue(usuario.getMuestrasEnviadas().contains(muestra));
	}

	@Test
	void agregarOpinionDada() {
		Usuario usuario = new Usuario(false);
		usuario.agregarOpinionDada(opinion);
		assertTrue(usuario.getOpinionesDadas().contains(opinion));
	}

	// ------------------------------------------------------------

	@Test
	void opinarSobreMuestraDelegaAEstadoDeConocimientoYPromocionaSiCorresponde() {
		Usuario usuario = spy(new Usuario(true));
		EstadoDeConocimiento estado = mock(EstadoDeConocimiento.class);
		usuario.setEstadoDeConocimiento(estado);

		usuario.opinarSobreMuestra(muestra, opinion);

		verify(estado).opinarSobreMuestra(usuario, muestra, opinion);
		verify(usuario).promocionarSiCorresponde();
	}

	@Test
	void opinarSobreMuestraEnProcesoDelegaAEstadoDeConocimientoYPromocionaSiCorresponde() {
		Usuario usuario = spy(new Usuario(true));
		EstadoDeConocimiento estado = mock(EstadoDeConocimiento.class);
		usuario.setEstadoDeConocimiento(estado);

		usuario.opinarSobreMuestraEnProceso(muestra, opinion);

		verify(estado).opinarSobreMuestraEnProceso(usuario, muestra, opinion);
		verify(usuario).promocionarSiCorresponde();

	}

	@Test
	void promocionaASiMismoAEstadoExpertoSiCumpleCondiciones() {
		Usuario usuario = new Usuario(false); // Estado inicial es Basico

		// Simulamos que tiene suficientes envíos y opiniones
		for (int i = 0; i < 10; i++) {
			usuario.agregarMuestraEnviada(mock(Muestra.class));
		}
		for (int i = 0; i < 20; i++) {
			usuario.agregarOpinionDada(mock(Opinion.class));
		}

		usuario.promocionarSiCorresponde();

		assertInstanceOf(EstadoExperto.class, usuario.getEstadoDeConocimiento());
	}

	@Test
	void noPromocionaASiMismoAEstadoExpertoSiNoCumpleCondiciones() {
		Usuario usuario = new Usuario(false); // Estado inicial es Basico

		// Simulamos que no tiene suficientes envíos y opiniones
		for (int i = 0; i < 5; i++) {
			usuario.agregarMuestraEnviada(mock(Muestra.class));
		}
		for (int i = 0; i < 10; i++) {
			usuario.agregarOpinionDada(mock(Opinion.class));
		}

		usuario.promocionarSiCorresponde();

		assertInstanceOf(usuarios.estados.EstadoBasico.class, usuario.getEstadoDeConocimiento());
	}

	// ------------------------------------------------------------

	@Test
	void enviarMuestraAWebAgregaMuestraAPaginaWebYPromocionaSiCorresponde() {
		Usuario usuario = spy(new Usuario(true));
		PaginaWeb web = mock(PaginaWeb.class);
		Muestra muestra = mock(Muestra.class);

		usuario.enviarMuestra(muestra, web);

		verify(web).agregarMuestra(muestra);
		verify(usuario).agregarMuestraEnviada(muestra);
		verify(usuario).promocionarSiCorresponde();
	}

	// ------------------------------------------------------------

	@Test
	void muestrasDeLosUltimosNDias() {
		Usuario usuario = new Usuario(true);

		Muestra muestra1 = mock(Muestra.class);
		Muestra muestra2 = mock(Muestra.class);
		Muestra muestra3 = mock(Muestra.class);

		when(muestra1.getFechaDeCreacion()).thenReturn(LocalDate.now().minusDays(1));
		when(muestra2.getFechaDeCreacion()).thenReturn(LocalDate.now().minusDays(5));
		when(muestra3.getFechaDeCreacion()).thenReturn(LocalDate.now().minusDays(40)); // Fuera del rango

		usuario.agregarMuestraEnviada(muestra1);
		usuario.agregarMuestraEnviada(muestra2);
		usuario.agregarMuestraEnviada(muestra3);

		assertEquals(2,usuario.muestrasDeLosUltimosNDias(30).size());
	}

	@Test
	void tieneAlmenosNOpinionesDadas() {
		Usuario usuario = new Usuario(true);

		usuario.agregarOpinionDada(mock(Opinion.class));
		usuario.agregarOpinionDada(mock(Opinion.class));
		usuario.agregarOpinionDada(mock(Opinion.class));

		assertTrue(usuario.tieneAlmenosNOpinionesDadas(3));
	}

	@Test
	void noTieneAlmenosNOpinionesDadas() {
		Usuario usuario = new Usuario(true);

		usuario.agregarOpinionDada(mock(Opinion.class));
		usuario.agregarOpinionDada(mock(Opinion.class));

		assertFalse(usuario.tieneAlmenosNOpinionesDadas(3));
	}

	@Test
	void tieneAlMenosNEnviosRealizados() {
		Usuario usuario = new Usuario(true);

		usuario.agregarMuestraEnviada(mock(Muestra.class));
		usuario.agregarMuestraEnviada(mock(Muestra.class));

		assertTrue(usuario.tieneAlMenosNEnviosRealizados(2));
	}

	@Test
	void noTieneAlMenosNEnviosRealizados() {
		Usuario usuario = new Usuario(true);

		usuario.agregarMuestraEnviada(mock(Muestra.class));

		assertFalse(usuario.tieneAlMenosNEnviosRealizados(2));
	}
}
