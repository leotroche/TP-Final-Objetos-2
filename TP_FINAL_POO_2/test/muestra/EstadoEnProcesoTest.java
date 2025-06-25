package muestra;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isA;
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
  
class EstadoEnProcesoTest {

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
		
		estado = new EstadoEnProceso();
		
		
		/////////////////////////////////////////////////////////////////////////
		/// OPINION DE USUARIO BASICO QUE SUBE LA MUESTRA TIPO PHTIA CHINCHE  ///
		/////////////////////////////////////////////////////////////////////////		
		
		opinion = mock(Opinion.class);
		usuario = mock(Usuario.class);
		when(opinion.getAutor()).thenReturn(usuario);
		when(opinion.getTipoDeInsecto()).thenReturn(TipoDeInsecto.PHTIA_CHINCHE);
		when(usuario.esUnExperto()).thenReturn(false);

		///////////////////////////////////////////////////////////
		/// OPINION DE USUARIO 2 EXPERTO TIPO VINCHUCA INFESTANS  ///
		///////////////////////////////////////////////////////////

		opinion2 = mock(Opinion.class);
		usuario2 = mock(Usuario.class);
		when(opinion2.getAutor()).thenReturn(usuario2);
		when(opinion2.getTipoDeInsecto()).thenReturn(TipoDeInsecto.VINCHUCA_INFESTANS);
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
		/// OPINION DE USUARIO 4 EXPERTO TIPO VINCHUCA SORDIDA  ///
		/////////////////////////////////////////////////////////
	
		opinion4 = mock(Opinion.class);
		usuario4 = mock(Usuario.class);
		when(opinion4.getAutor()).thenReturn(usuario4);
		when(opinion4.getTipoDeInsecto()).thenReturn(TipoDeInsecto.VINCHUCA_SORDIDA);
		when(usuario4.esUnExperto()).thenReturn(true);
		
		/////////////////////////////////////////////////////////
		/// OPINION DE USUARIO 5 EXPERTO TIPO VINCHUCA SORDIDA  ///
		/////////////////////////////////////////////////////////		
		
		opinion5 = mock(Opinion.class);
		usuario5 = mock(Usuario.class);
		when(opinion5.getAutor()).thenReturn(usuario5);
		when(opinion5.getTipoDeInsecto()).thenReturn(TipoDeInsecto.VINCHUCA_SORDIDA);
		when(usuario5.esUnExperto()).thenReturn(true);
		

		opiniones = new ArrayList<Opinion>();
		opiniones = spy(opiniones);
		
		
		opiniones.add(opinion);
		opiniones.add(opinion2);
		
		muestra = mock(Muestra.class);
		when(muestra.getOpiniones()).thenReturn(opiniones);
		when(muestra.obtenerUltimaOpinion()).thenReturn(opinion2); 
	}

	@Test
	void testLaOpinionDeUsuarioBasicoNoSeAgrega() {
		estado.agregarOpinion(muestra, opinion3);
		verify(opiniones, never()).add(opinion3);
	}
	
	@Test 
	void testLaOpinionDeUsuarioExpertoSeAgrega() {
		estado.agregarOpinion(muestra, opinion4);
		verify(opiniones).add(opinion4);
	}
	
	@Test 
	void testLaMuestraNoRecibioOpinionesYElTipoActualEsElDelQueCambioElEstado() {
		TipoDeInsecto tipo = estado.obtenerResultadoActual(muestra);
		assertEquals(TipoDeInsecto.VINCHUCA_INFESTANS, tipo);
	}

	@Test
	void testLaOpinionDeUsuarioExpertoNoCoincideConLaYaExistenteYElEstadoNoCambia() {
		estado.agregarOpinion(muestra, opinion4);
		verify(muestra, never()).setEstadoDeVerificacion(isA(EstadoVerificado.class));
	}
	
	@Test
	void testLaOpinionDeUsuarioExpertoNoCoincideYElResultadoActualEsNinguno() {
		estado.agregarOpinion(muestra, opinion4);
		TipoDeInsecto tipo = estado.obtenerResultadoActual(muestra);
		assertEquals(TipoDeInsecto.NINGUNA, tipo);
	}
	
	@Test 
	void testLaOpinionDeDosUsuariosExpertoNuevosCoincideYElEstadoCambiaAVerificado() {
		estado.agregarOpinion(muestra, opinion4);
		estado.agregarOpinion(muestra, opinion5);
		verify(muestra).setEstadoDeVerificacion(isA(EstadoVerificado.class));
	}


}
