package busqueda.filtro.atributo.fecha.comparacion;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ComparadorFechaAnteriorTest {
	private ComparadorDeFechas comparador;

	private LocalDate fecha_2025;
	private LocalDate fecha_2099;

	@BeforeEach
	void setUp() {
		comparador = new ComparadorFechaAnterior();

		fecha_2025 = LocalDate.of(2025, 1, 1);
		fecha_2099 = LocalDate.of(2099, 1, 1);
	}

	@Test
	void retornaTrueSiLaPrimeraFechaEsAnterior() {
		assertTrue(comparador.comparar(fecha_2025, fecha_2099));
	}

	@Test
	void retornaFalseSiLaPrimeraFechaNoEsAnterior() {
		assertFalse(comparador.comparar(fecha_2099, fecha_2025));
	}
}
