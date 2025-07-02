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
	void getAutorTest() {
		assertEquals(usuario, opinion.getAutor());
	}

	@Test
	void getTipoDeInsectoTest() {
		assertEquals(tipoDeInsecto, opinion.getTipoDeInsecto());
	}

	@Test
	void getEsOpinionDeExpertoTest() {
		assertTrue(opinion.getEsOpinionDeExperto());
	}

	@Test
	void getFechaDeVotacionTest() {
		assertEquals(LocalDate.now(), opinion.getFechaDeVotacion());
	}
}
