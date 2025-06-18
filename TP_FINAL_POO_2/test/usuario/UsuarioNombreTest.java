package usuario;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UsuarioNombreTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testGetNombre() {
		Usuario user = new Usuario("Juan Gabriel");
		assertEquals("Juan Gabriel", user.getNombre());
	}
	
	@Test
	void testSetNombre() {
		Usuario user = new Usuario("Juan Gabriel");
		user.setNombre("Pedro Pascal");
        assertEquals("Pedro Pascal", user.getNombre());
    }
}

