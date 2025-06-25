package muestra;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gestores.eventos.GestorDeEventos;
import ubicacion.Ubicacion;
import usuario.Usuario;
import varios.Opinion;
import varios.TipoDeInsecto;
  
class MuestraTest {
	
	private Muestra muestra; // SUT
	private Opinion opinion; // DOC
	private Ubicacion ubicacion; // DOC
	private Usuario usuario; // DOC
	private GestorDeEventos gestor; // SUT
	
	
	private Opinion opinion2; // DOC
	private Usuario usuario2; // DOC
	
	private Opinion opinion3; // DOC
	private Usuario usuario3; // DOC
	
	private Opinion opinion4; // DOC
	private Usuario usuario4; // DOC
	
	private EstadoDeVerificacion estado;

	@BeforeEach
	void setUp() throws Exception {
		
		usuario = mock(Usuario.class);                                                                   // Usuario que sube la muestra
		ubicacion = mock(Ubicacion.class);                                                               // Ubicacion del usuario que sube la muestra
		gestor = mock(GestorDeEventos.class);
		muestra = new Muestra("foto", ubicacion, usuario, TipoDeInsecto.VINCHUCA_GUASAYANA, gestor);    // Muestra que sube el usuario (tipo de insecto arbitrario)
		opinion = mock(Opinion.class);                                                                  // Opinion del usuario que sube la muestra
		estado = spy(EstadoNoVerificado.class);
		when(opinion.getAutor()).thenReturn(usuario);
		when(opinion.getEsDeUnExperto()).thenReturn(false);                                             // La opinion de los que suben la muestra siempre es de usuarios basicos
		muestra.setEstadoDeVerificacion(estado);
		
		//////////////////////////////////////////////////////////
		/// OPINION 2 DE USUARIO BASICO TIPO IMAGEN POCO CLARA ///
		//////////////////////////////////////////////////////////
		
		opinion2 = mock(Opinion.class);
		usuario2 = mock(Usuario.class);
		when(opinion2.getAutor()).thenReturn(usuario2);
		when(opinion2.getEsDeUnExperto()).thenReturn(false);
		when(opinion2.getTipoDeInsecto()).thenReturn(TipoDeInsecto.IMAGEN_POCO_CLARA);
		
		////////////////////////////////////////////////////////////
		/// OPINION 3 DE USUARIO EXPERTO TIPO VINCHUCA INFESTANS ///
		////////////////////////////////////////////////////////////
		
		opinion3 = mock(Opinion.class);
		usuario3 = mock(Usuario.class);
		when(opinion3.getAutor()).thenReturn(usuario3);
		when(opinion3.getEsDeUnExperto()).thenReturn(true);		
		when(opinion3.getTipoDeInsecto()).thenReturn(TipoDeInsecto.VINCHUCA_INFESTANS);
		
		////////////////////////////////////////////////////////////
		/// OPINION 4 DE USUARIO EXPERTO TIPO VINCHUCA GUASATANA ///
		////////////////////////////////////////////////////////////
		
		opinion4 = mock(Opinion.class);
		usuario4 = mock(Usuario.class);
		when(opinion4.getAutor()).thenReturn(usuario4);
		when(opinion4.getEsDeUnExperto()).thenReturn(true);
		when(opinion4.getTipoDeInsecto()).thenReturn(TipoDeInsecto.VINCHUCA_INFESTANS);
	}

	
	@Test
	void testUnaMuestraRecibeUnaOpinionDeUsuarioBasicoYDelegaAlUsuarioDeLaMisma() {
		muestra.agregarOpinion(opinion2);
		verify(usuario2).opinarSobreMuestra(muestra, opinion2);
	}
	
	@Test
	void testDoAgregarOpinionAgregaOpinionALaListaDeOpiniones() {
		muestra.doAgregarOpinion(opinion2);
		assertTrue(muestra.getOpiniones().contains(opinion2));
	}
	
	@Test
	void testUsuarioExpertoOpinaYElEstadoEsNotificado() {
		muestra.recibioOpinionDeExperto();
		verify(estado).recibioOpinionDeExperto(muestra);
	}
	
	@Test
	void testSeVerificaLaMuestraSiCorresponde() {
		muestra.doAgregarOpinion(opinion3);
		muestra.doAgregarOpinion(opinion4);
		muestra.setEstadoDeVerificacion(new EstadoEnProceso());
		muestra.verificarMuestraSiCorresponde();
		verify(muestra).setEstadoDeVerificacion(isA(EstadoVerificado.class));
		
		
	}
	
	@Test 
	void testGetFoto() {
		assertEquals("foto", muestra.getFoto());
	}
	
	@Test 
	void testGetUbicacion() {
		assertEquals(ubicacion, muestra.getUbicacion());
	}
	
	@Test
	void testGetUsuario() {
		assertEquals(usuario, muestra.getUsuario());
	}
	
//	@Test 
//	void testDosUsuariosOpinanYLaUltimaOpinionEsDelQueOpinoAlFinal() {
//		muestra.agregarOpinion(opinion2);
//		muestra.agregarOpinion(opinion3);
//		assertEquals(opinion3, muestra.obtenerUltimaOpinion());
	
//}
	
	
	

}
