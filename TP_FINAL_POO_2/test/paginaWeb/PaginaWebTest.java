package paginaWeb;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import eventos.Evento;
import gestores.eventos.GestorDeEventos;
import muestra.Muestra;
import ubicacion.Ubicacion;
import usuario.Usuario;
import varios.TipoDeInsecto;
import zona.cobertura.ZonaDeCobertura;

import static org.mockito.Mockito.*;


class PaginaWebTest {
	private GestorDeEventos gestorDeEventosMock;
    private PaginaWeb pagina;
	@BeforeEach
    void setUp() {
		gestorDeEventosMock = mock(GestorDeEventos.class);
        pagina = new PaginaWeb(gestorDeEventosMock);
    }

	@Test
	void paginaWebGetUsuariosTest() {
		assertEquals(0, pagina.getUsuarios().size());
	}
	
	@Test
	void paginaWebSetUsuariosTest() {
		Usuario user1 = null;
		ArrayList<Usuario> usuarios = new ArrayList<>();
		usuarios.add(user1);
		pagina.setUsuarios(usuarios);
		assertEquals(1, pagina.getUsuarios().size());
	}
	
	@Test
	void paginaWebAgregarUsuarioTest() {
		Usuario user1 = null;
		pagina.agregarUsuario(user1);
		assertEquals(1, pagina.getUsuarios().size());
	}
	
	@Test
	void paginaWebGetMuestrasTest() {
		assertEquals(0, pagina.getMuestras().size());
	}
	
	@Test
	void paginaWebSetMuestrasTest() {
		Muestra muestra = null;
		ArrayList<Muestra> muestras = new ArrayList<>();
		muestras.add(muestra);
		pagina.setMuestras(muestras);
		assertEquals(1, pagina.getMuestras().size());
	}
	
	@Test
	void paginaWebAgregarMuestraTest() {
		Muestra muestra = null;
		pagina.agregarMuestra(muestra);
		assertEquals(1, pagina.getMuestras().size());
	}
	
	@Test
	void paginaWebSuscribirZonaDeCoberturaTest() {
		Evento eventoMock = mock(Evento.class);
        ZonaDeCobertura zona = null;
        pagina.subscribirZonaDeCobertura(eventoMock, zona);
        verify(gestorDeEventosMock).suscribir(eventoMock, zona);
	}
	
	@Test
	void paginaWebDesuscribirZonaDeCoberturaTest() {
		Evento eventoMock = mock(Evento.class);
        ZonaDeCobertura zona = null;
        pagina.subscribirZonaDeCobertura(eventoMock, zona);
        pagina.desubscribirZonaDeCobertura(eventoMock, zona);
        verify(gestorDeEventosMock).desuscribir(eventoMock, zona);
	}
	
	@Test
	void paginaWebNotificarZonaDeCoberturaTest() {
		Evento eventoMock = mock(Evento.class);
        ZonaDeCobertura zona = null;
        Muestra muestra = null;
        pagina.notificarZonaDeCobertura(eventoMock, zona, muestra);
        verify(gestorDeEventosMock).notificar(eventoMock, zona, muestra);
	}
	
	@Test
	void paginaWebMuestrasUltimosNDiasTest() {
		Ubicacion ubicacion = new Ubicacion(20,30);
		Usuario usuarioMock = mock(Usuario.class);
		TipoDeInsecto tipoInsectoMock = mock(TipoDeInsecto.class);
		Muestra muestra = new Muestra("M2",ubicacion, usuarioMock, tipoInsectoMock);
		muestra.setFechaDeCreacion(LocalDate.now().minusDays(10));
		Muestra muestra2 = new Muestra("M2",ubicacion, usuarioMock, tipoInsectoMock);;
		muestra2.setFechaDeCreacion(LocalDate.now().minusDays(40));
		ArrayList<Muestra> muestras = new ArrayList<>();
		muestras.add(muestra);
		muestras.add(muestra2);
		pagina.setMuestras(muestras);
		assertEquals(1, pagina.getMuestrasUltimosNDias().size());
	}
}
