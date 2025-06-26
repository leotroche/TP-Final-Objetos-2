package muestras;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import usuarios.Usuario;

class OpinionTest {
	private Usuario usuario;
	private TipoDeInsecto tipoDeInsecto;

	private Opinion opinion;

	@BeforeEach
	void setUp() throws Exception {
		opinion = new Opinion(usuario, tipoDeInsecto, true);
	}

	@Test
	void getNombreAutor() {
		assertEquals(usuario, opinion.getAutor());
	}

	@Test
	void getTipoInsecto() {
		assertEquals(tipoDeInsecto, opinion.getTipoDeInsecto());
	}

	@Test
	void getFechaDeVotacion() {
		assertEquals(LocalDate.now(), opinion.getFechaDeVotacion());
	}

	@Test
	void getEsUnExperto() {
		assertTrue(opinion.getEsUnExperto());
	}
}
