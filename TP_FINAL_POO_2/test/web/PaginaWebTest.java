package web;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import muestras.Muestra;
import usuarios.Usuario;
import zonas.ZonaDeCobertura;

class PaginaWebTest {
	private PaginaWeb web;

	private Usuario usuario;
	private Muestra muestra;


	@BeforeEach
	void setUp() {
		muestra = mock(Muestra.class);
		usuario = mock(Usuario.class);

		web = new PaginaWeb();
	}

	@Test
	void unaPaginaWebIncialmenteNoTieneUsuarios() {
		assertTrue(web.getUsuariosRegistrados().isEmpty());
	}

	@Test
	void unaPaginaWebIncialmenteNoTieneMuestras() {
		assertTrue(web.getMuestrasRegistradas().isEmpty());
	}

	@Test
	void agregarUsuarioAgregaUnUsuarioAlaPaginaWeb() {
		web.agregarUsuario(usuario);
		assertEquals(1, web.getUsuariosRegistrados().size());
	}

	@Test
	void agregarMuestraAgregaUnaMuestraAlaPaginaWeb() {
		web.agregarMuestra(muestra);
		assertEquals(1, web.getMuestrasRegistradas().size());
	}

	@Test
	void noSePuedeAgregarUnaZonaDeCoberturaQueYaEsteEnLaPagina() {
		ZonaDeCobertura zona = mock(ZonaDeCobertura.class);
		
		web.agregarZonaDeCobertura(zona);
		web.agregarZonaDeCobertura(zona);

		assertEquals(1, web.getZonasDeCoberturaRegistradas().size());
	}

	@Test
	void agregarMuestraAgregaALaZonaDeCoberturaDondePertenescan() {
		ZonaDeCobertura zona = mock(ZonaDeCobertura.class);
		
		when(zona.perteneceALaZona(muestra)).thenReturn(true);
		
		web.agregarZonaDeCobertura(zona);
		web.agregarMuestra(muestra);
		
		verify(zona, times(1)).procesarNuevaMuestra(muestra);
	}

	@Test
	void agregarMuestraNoSeAgregaALaZonaDeCoberturaDeLaPagina() {
		ZonaDeCobertura zona = mock(ZonaDeCobertura.class);
		
		when(zona.perteneceALaZona(muestra)).thenReturn(false);
		
		web.agregarZonaDeCobertura(zona);
		web.agregarMuestra(muestra);
		
		verify(zona, never()).procesarNuevaMuestra(muestra);
	}
}

