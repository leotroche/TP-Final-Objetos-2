package web;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import muestras.Muestra;
import usuarios.Usuario;
import zonas.ZonaDeCobertura;

class PaginaWebTest {
	private PaginaWeb web;

	@BeforeEach
	void setUp() {
		web = new PaginaWeb();
	}

	// ------------------------------------------------------------

	@Test
	void unaPaginaWebIncialmenteNoTieneUsuariosRegistrados() {
		assertTrue(web.getUsuariosRegistrados().isEmpty());
	}

	@Test
	void agregarUsuarioAgregaUnUsuarioAlaPaginaWeb() {
		Usuario usuario = mock(Usuario.class);

		web.agregarUsuario(usuario);
		assertEquals(1, web.getUsuariosRegistrados().size());
	}

	@Test
	void agregarUsuarioNoAgregaElMismoUsuarioDosVeces() {
		Usuario usuario = mock(Usuario.class);

		web.agregarUsuario(usuario);
		web.agregarUsuario(usuario);
		assertEquals(1, web.getUsuariosRegistrados().size());
	}

	// ------------------------------------------------------------

	@Test
	void unaPaginaWebIncialmenteNoTieneMuestrasRegistradas() {
		assertTrue(web.getMuestrasRegistradas().isEmpty());
	}

	@Test
	void agregarMuestraAgregaUnaMuestraAlaPaginaWeb() {
		Muestra muestra = mock(Muestra.class);

		web.agregarMuestra(muestra);
		assertEquals(1, web.getMuestrasRegistradas().size());
	}

	@Test
	void agregarMuestraNoAgregaLaMismaMuestraDosVeces() {
		Muestra muestra = mock(Muestra.class);

		web.agregarMuestra(muestra);
		web.agregarMuestra(muestra);
		assertEquals(1, web.getMuestrasRegistradas().size());
	}

	@Test
	void agregarMuestraDelegaElProcesamientoDeLaMuestraALasZonasDeCoberturaRegistradas() {
		ZonaDeCobertura zona = mock(ZonaDeCobertura.class);
		Muestra muestra = mock(Muestra.class);

		web.agregarZonaDeCobertura(zona);
		web.agregarMuestra(muestra);

		assertEquals(1, web.getZonasDeCoberturaRegistradas().size());
		assertEquals(1, web.getMuestrasRegistradas().size());

		// Verificamos que la zona procese la muestra
		verify(zona).procesarNuevaMuestra(muestra);
	}

	// ------------------------------------------------------------

	@Test
	void unaPaginaWebIncialmenteNoTieneZonasDeCoberturaRegistradas() {
		assertTrue(web.getZonasDeCoberturaRegistradas().isEmpty());
	}

	@Test
	void agregarZonaDeCoberturaAgregaUnaZonaDeCoberturaAlaPaginaWeb() {
		ZonaDeCobertura zona = mock(ZonaDeCobertura.class);

		web.agregarZonaDeCobertura(zona);
		assertEquals(1, web.getZonasDeCoberturaRegistradas().size());
	}

	@Test
	void agregarZonaDeCoberturaNoAgregaLaMismaZonaDosVeces() {
		ZonaDeCobertura zona = mock(ZonaDeCobertura.class);

		web.agregarZonaDeCobertura(zona);
		web.agregarZonaDeCobertura(zona);
		assertEquals(1, web.getZonasDeCoberturaRegistradas().size());
	}
}
