package muestra;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
 
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import usuario.Usuario;
import varios.Opinion;
import varios.TipoDeInsecto;
 
class EstadoVerificadoTest {
	
private EstadoDeVerificacion estado;  // SUT
	
	private List<Opinion> opiniones;
	
	private Muestra muestra; // DOC
	
	private Opinion opinion; // DOC
	private Usuario usuario; // DOC
	
	private Opinion opinion2; // DOC
	private Usuario usuario2; // DOC
	
	private Opinion opinion3; // DOC
	private Usuario usuario3; // DOC
	
	private Opinion opinion4; // DOC
	private Usuario usuario4; // DOC
	
	@BeforeEach
	void setUp() throws Exception {
		
		estado = new EstadoVerificado();
		
		/////////////////////////////////////////////////////////////////////////
		/// OPINION DE USUARIO BASICO QUE SUBE LA MUESTRA TIPO PHTIA CHINCHE  ///
		/////////////////////////////////////////////////////////////////////////		
		
		opinion = mock(Opinion.class);
		usuario = mock(Usuario.class);
		when(opinion.getAutor()).thenReturn(usuario);
		when(opinion.getTipoDeInsecto()).thenReturn(TipoDeInsecto.PHTIA_CHINCHE);
		when(usuario.esUnExperto()).thenReturn(false);

		///////////////////////////////////////////////////////////
		/// OPINION DE USUARIO 2 EXPERTO TIPO VINCHUCA GUASAYANA  ///
		///////////////////////////////////////////////////////////

		opinion2 = mock(Opinion.class);
		usuario2 = mock(Usuario.class);
		when(opinion2.getAutor()).thenReturn(usuario2);
		when(opinion2.getTipoDeInsecto()).thenReturn(TipoDeInsecto.VINCHUCA_GUASAYANA);
		when(usuario2.esUnExperto()).thenReturn(true);
		
		//////////////////////////////////////////////////////////
		/// OPINION DE USUARIO 3 EXPERTO TIPO VINCHUCA GUASAYANA  ///
		//////////////////////////////////////////////////////////
		
		opinion3 = mock(Opinion.class);
		usuario3 = mock(Usuario.class);
		when(opinion3.getAutor()).thenReturn(usuario3);
		when(opinion3.getTipoDeInsecto()).thenReturn(TipoDeInsecto.VINCHUCA_GUASAYANA);
		when(usuario3.esUnExperto()).thenReturn(true);
		
		/////////////////////////////////////////////////////////
		/// OPINION DE USUARIO 4 EXPERTO TIPO VINCHUCA SORDIDA  ///
		/////////////////////////////////////////////////////////
	
		opinion4 = mock(Opinion.class);
		usuario4 = mock(Usuario.class);
		when(opinion4.getAutor()).thenReturn(usuario4);
		when(opinion4.getTipoDeInsecto()).thenReturn(TipoDeInsecto.VINCHUCA_SORDIDA);
		when(usuario4.esUnExperto()).thenReturn(false);
		
		
		opiniones = new ArrayList<Opinion>();
		opiniones = spy(opiniones);
		
		opiniones.add(opinion);
		opiniones.add(opinion2);
		opiniones.add(opinion3);
		
		muestra = mock(Muestra.class);
		when(muestra.getOpiniones()).thenReturn(opiniones);
		when(muestra.obtenerUltimaOpinion()).thenReturn(opinion3);
	}
 
	@Test
	void testUnUsuarioExpertoOpinaYNoSeAgrega() {
		estado.agregarOpinion(muestra, opinion4);
		verify(opiniones, never()).add(opinion4);
	}
	
	@Test
	void testElResultadoActualDeLaMuestraEsLaDelUltimoExpertoQueOpino() {
		TipoDeInsecto tipo = estado.obtenerResultadoActual(muestra);
		assertEquals(TipoDeInsecto.VINCHUCA_GUASAYANA, tipo);
	}
	
	@Test
	void testUnExpertoOpinaYElResultadoActualNoCambia() {
		estado.agregarOpinion(muestra, opinion4);
		TipoDeInsecto tipo = estado.obtenerResultadoActual(muestra);
		assertEquals(TipoDeInsecto.VINCHUCA_GUASAYANA, tipo);
	}

}
