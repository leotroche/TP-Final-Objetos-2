package busqueda.filtros.atributos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import busqueda.filtros.Filtro;
import busqueda.filtros.atributos.FiltroEstadoDeVerificacion;
import muestras.Muestra;
import muestras.estados.EstadoDeVerificacion;
import muestras.estados.EstadoVerificado;

class FiltroEstadoVerificacionTest {
	private EstadoDeVerificacion estado;

	private Muestra muestra;
	private List<Muestra> muestras;

	@BeforeEach
	void setUp() {
		// Muestras simuladas
		muestra = mock(Muestra.class);
		muestras = List.of(
				mock(Muestra.class),
				mock(Muestra.class),
				muestra,
				mock(Muestra.class),
				mock(Muestra.class)
				);

		// Estados de verificación simulados
		estado = mock(EstadoVerificado.class);
	}

	// --------------------------------------------------------------------------------

	@Test
	void retornaTrueSiElEstadoCoincide() {
		Filtro filtro = new FiltroEstadoDeVerificacion(estado);

		when(muestra.getEstadoDeVerificacion()).thenReturn(estado);

		assertTrue(filtro.cumple(muestra));
	}

	// --------------------------------------------------------------------------------

	@Test
	void retornaFalseSiElEstadoNoCoincide() {
		Filtro filtro = new FiltroEstadoDeVerificacion(estado);

		assertFalse(filtro.cumple(muestra));
	}

	// --------------------------------------------------------------------------------

	@Test
	void filtraMuestrasQueCoincidenConElEstado() {
		Filtro filtro = new FiltroEstadoDeVerificacion(estado);

		// Simulamos que la primera muestra cumple el filtro
		when(muestra.getEstadoDeVerificacion()).thenReturn(estado);

		List<Muestra> muestrasFiltradas = filtro.filtrar(muestras);

		assertEquals(1, muestrasFiltradas.size());
		assertTrue(muestrasFiltradas.contains(muestra));
	}

	// --------------------------------------------------------------------------------

	@Test
	void retornaListaVaciaSiNingunaMuestraCoincide() {
		Filtro filtro = new FiltroEstadoDeVerificacion(estado);

		List<Muestra> muestrasFiltradas = filtro.filtrar(muestras);

		assertTrue(muestrasFiltradas.isEmpty());
	}
}
