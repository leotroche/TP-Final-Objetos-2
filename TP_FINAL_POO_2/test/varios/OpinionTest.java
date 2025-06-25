package varios;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import usuario.Usuario;



class OpinionTest {
	private Usuario user = null;
	TipoDeInsecto vinchuca = TipoDeInsecto.VINCHUCA_INFESTANS;
	
	@BeforeEach
	void setUp() throws Exception {
	}
	
	@Test
	void testGetNombreAutor() {
		Opinion opinion = new Opinion(user, vinchuca);
		assertEquals(user, opinion.getAutor());
	}
	
	@Test
	void testGetTipoInsecto() {
		Opinion opinion = new Opinion(user, vinchuca);
		assertEquals(vinchuca, opinion.getTipoDelInsecto());
	}
	
	@Test
	void testgetFechaDeVotacion() {
		Opinion opinion = new Opinion(user, vinchuca);
		assertEquals(LocalDate.now(), opinion.getFechaDeVotacion());
	}
	
}