package usuario;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import muestra.Muestra;
import paginaWeb.PaginaWeb;
import ubicacion.Ubicacion;
import usuario.estado.conocimiento.*;
import varios.Opinion;
import varios.TipoDeInsecto;

class UsuarioTest {
	private Usuario usuario;
	@BeforeEach
    void setUp() {
		usuario = new Usuario();
    }
	

    @Test
    void testGetEstadoBasico() {
        assertTrue(usuario.getEstadoDeConocimiento() instanceof EstadoBasico);
    }
    
    @Test
    void testGetEstadoEspecialista() {
    	Usuario user = new Usuario(true);
        assertTrue(user.getEstadoDeConocimiento() instanceof EstadoEspecialista);
    }
    
    @Test
    void testVerificarEnvioDeMuestra() {
    	Muestra muestraMock = mock(Muestra.class);
    	PaginaWeb paginaMock = mock(PaginaWeb.class);
    	usuario.enviarMuestra(muestraMock, paginaMock);
    	verify(paginaMock).agregarMuestra(muestraMock);
    }
    
    
    @Test
    void testPromocionCambioAEstadoDeConocimientoExperto() {
    	Usuario usuario = new Usuario();
    	PaginaWeb pagina = new PaginaWeb(null);
    	ArrayList<Muestra> muestras = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            Muestra muestra = new Muestra("foto", new Ubicacion(1.0, 2.0), usuario, TipoDeInsecto.VINCHUCA_INFESTANS);
            muestra.setFechaDeCreacion(LocalDate.now().minusDays(5));
            muestras.add(muestra);
            pagina.agregarMuestra(muestra);
        }
        for (int i = 0; i < 20; i++) {
            Opinion opinion = new Opinion(usuario, TipoDeInsecto.VINCHUCA_INFESTANS, false);
            muestras.get(i % muestras.size()).getOpiniones().add(opinion);
        }
        
        Muestra nuevaMuestra = new Muestra("fotoNueva", new Ubicacion(1.0, 2.0), usuario, TipoDeInsecto.VINCHUCA_SORDIDA);
        usuario.enviarMuestra(nuevaMuestra, pagina);
        assertTrue(usuario.getEstadoDeConocimiento() instanceof EstadoExperto);
    }
    
    @Test
    void testOpinarSobreMuestraSiendoUsarioBasico() {
    	Muestra muestraMock = mock(Muestra.class);
    	Opinion opinionMock = mock(Opinion.class);
    	PaginaWeb paginaMock = mock(PaginaWeb.class);
    	usuario.opinarSobreMuestra(muestraMock, opinionMock, paginaMock);
    	verify(muestraMock).agregarOpinion(opinionMock);
    }
    
    @Test
    void testOpinarSobreMuestraSiendoUsarioExperto() {
    	Usuario user=new Usuario(true);
    	Muestra muestraMock = mock(Muestra.class);
    	Opinion opinionMock = mock(Opinion.class);
    	PaginaWeb paginaMock = mock(PaginaWeb.class);
    	user.opinarSobreMuestra(muestraMock, opinionMock, paginaMock);
    	verify(muestraMock).recibirOpinionDeExperto(opinionMock);
    }
}
