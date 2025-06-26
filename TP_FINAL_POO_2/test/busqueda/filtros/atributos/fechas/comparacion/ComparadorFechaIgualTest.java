package busqueda.filtros.atributos.fechas.comparacion;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ComparadorFechaIgualTest {
	private ComparadorDeFechas comparador;

	private LocalDate fecha_2025;
	private LocalDate fecha_2099;

	@BeforeEach
	void setUp() {
		comparador = new ComparadorFechaIgual();

		fecha_2025 = LocalDate.of(2025, 1, 1);
		fecha_2099 = LocalDate.of(2099, 1, 1);
	}

	@Test
	void retornaTrueSiLasFechasSonIguales() {
		assertTrue(comparador.comparar(fecha_2025, fecha_2025));
	}

	@Test
	void retornaFalseSiLasFechasSonDistintas() {
		assertFalse(comparador.comparar(fecha_2025, fecha_2099));
	}
}
