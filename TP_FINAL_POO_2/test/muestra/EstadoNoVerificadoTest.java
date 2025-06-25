package muestra;
 
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
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
  
class EstadoNoVerificadoTest {

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
	
	private Opinion opinion5; // DOC
	private Usuario usuario5; // DOC
	
	@BeforeEach
	void setUp() throws Exception {
		
		estado = new EstadoNoVerificado();
		
		
		/////////////////////////////////////////////////////////////////////////
		/// OPINION DE USUARIO BASICO QUE SUBE LA MUESTRA TIPO PHTIA CHINCHE  ///
		/////////////////////////////////////////////////////////////////////////		
		
		opinion = mock(Opinion.class);
		usuario = mock(Usuario.class);
		when(opinion.getAutor()).thenReturn(usuario);
		when(opinion.getTipoDeInsecto()).thenReturn(TipoDeInsecto.PHTIA_CHINCHE);
		when(usuario.esUnExperto()).thenReturn(false);

		///////////////////////////////////////////////////////////
		/// OPINION DE USUARIO 2 BASICO TIPO IMAGEN POCO CLARA  ///
		///////////////////////////////////////////////////////////

		opinion2 = mock(Opinion.class);
		usuario2 = mock(Usuario.class);
		when(opinion2.getAutor()).thenReturn(usuario2);
		when(opinion2.getTipoDeInsecto()).thenReturn(TipoDeInsecto.IMAGEN_POCO_CLARA);
		when(usuario2.esUnExperto()).thenReturn(false);
		
		//////////////////////////////////////////////////////////
		/// OPINION DE USUARIO 3 BASICO TIPO VINCHUCA SORDIDA  ///
		//////////////////////////////////////////////////////////
		
		opinion3 = mock(Opinion.class);
		usuario3 = mock(Usuario.class);
		when(opinion3.getAutor()).thenReturn(usuario3);
		when(opinion3.getTipoDeInsecto()).thenReturn(TipoDeInsecto.VINCHUCA_SORDIDA);
		when(usuario3.esUnExperto()).thenReturn(false);
		
		/////////////////////////////////////////////////////////
		/// OPINION DE USUARIO 4 BASICO TIPO VINCHUCA SORDIDA  ///
		/////////////////////////////////////////////////////////
	
		opinion4 = mock(Opinion.class);
		usuario4 = mock(Usuario.class);
		when(opinion4.getAutor()).thenReturn(usuario4);
		when(opinion4.getTipoDeInsecto()).thenReturn(TipoDeInsecto.VINCHUCA_SORDIDA);
		when(usuario4.esUnExperto()).thenReturn(false);
		
		/////////////////////////////////////////////////////////
		/// OPINION DE USUARIO 5 EXPERTO TIPO VINCHUCA GUASAYANA  ///
		/////////////////////////////////////////////////////////		
		
		opinion5 = mock(Opinion.class);
		usuario5 = mock(Usuario.class);
		when(opinion5.getAutor()).thenReturn(usuario5);
		when(opinion5.getTipoDeInsecto()).thenReturn(TipoDeInsecto.VINCHUCA_SORDIDA);
		when(usuario5.esUnExperto()).thenReturn(true);
		

		opiniones = new ArrayList<Opinion>();
		opiniones = spy(opiniones);
		
		
		opiniones.add(opinion);
		
		muestra = mock(Muestra.class);
		when(muestra.getOpiniones()).thenReturn(opiniones);
		
	}
 
	@Test
	void testLaOpinionDeUsuarioBasicoQueOpinaEsAgregaga() {	
		estado.agregarOpinion(muestra, opinion2);
		verify(opiniones).add(opinion2);
	}
	
	@Test
	void testLaOpinionDeUsuarioExpertoEsAgregadaYCambiaElEstado() {
		estado.agregarOpinion(muestra, opinion5);
		verify(muestra).setEstadoDeVerificacion(isA(EstadoEnProceso.class));
	}
	
	@Test
	void testLaMuestraNoRecibioOpinionesYElResultadoEsElDelQueLaSubio() {
		TipoDeInsecto tipo = estado.obtenerResultadoActual(muestra);
		assertEquals(TipoDeInsecto.PHTIA_CHINCHE, tipo);
	}
	
	@Test
	void testLaMuestraRecibioOpinionUnOpinionDeBasicoConOtroTipoDeInsectoYElResultadoActualCambioANinguno() {
		estado.agregarOpinion(muestra, opinion2);
		TipoDeInsecto tipo = estado.obtenerResultadoActual(muestra);
		assertEquals(TipoDeInsecto.NINGUNA, tipo);
	}
	
	@Test
	void testLaMuestraRecibeOpinionDeDosUsuariosBasicosConElMismoTipoYElResultadoActualCambiaAEseMismo() {
		estado.agregarOpinion(muestra, opinion3);
		estado.agregarOpinion(muestra, opinion4);
		TipoDeInsecto tipo = estado.obtenerResultadoActual(muestra);
		assertEquals(TipoDeInsecto.VINCHUCA_SORDIDA, tipo);
	}
	
}
