package varios;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import usuario.Usuario;



class OpinionTest {
	private Usuario user;
	TipoDeInsecto vinchuca = TipoDeInsecto.VINCHUCA_INFESTANS;
	@BeforeEach
	void setUp() throws Exception {
		user.setNombre("Juan Gabriel");
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
	void testSetNombreAutor() {
		Usuario user2 = new Usuario("Maximillion Pegasus");
		Opinion opinion = new Opinion(user, vinchuca);
		opinion.setAutor(user2);
		assertEquals(vinchuca, opinion.getTipoDelInsecto());
	}
	
	void testSetTipoInsecto() {
		TipoDeInsecto insecto = TipoDeInsecto.CHINCHE_FOLIADA;
		Opinion opinion = new Opinion(user, vinchuca);
		opinion.setTipoDeInsecto(insecto);
		assertEquals(insecto, opinion.getTipoDelInsecto());
	}
}