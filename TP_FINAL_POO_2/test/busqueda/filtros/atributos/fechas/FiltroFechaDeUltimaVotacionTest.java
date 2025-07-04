package busqueda.filtros.atributos.fechas;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import busqueda.filtros.Filtro;
import busqueda.filtros.atributos.fechas.comparacion.ComparadorDeFechas;
import muestras.Muestra;

class FiltroFechaDeUltimaVotacionTest {
	private LocalDate fecha;

	private Muestra muestra;
	private List<Muestra> muestras;

	private ComparadorDeFechas comparador;

	@BeforeEach
	void setUp() {
		// Fechas
		fecha = LocalDate.of(2025, 1, 1);

		// Muestras simuladas
		muestra = mock(Muestra.class);
		muestras = List.of(
				mock(Muestra.class),
				mock(Muestra.class),
				muestra,
				mock(Muestra.class),
				mock(Muestra.class)
				);

		// Configuración de muestras simuladas
		when(muestra.getFechaDeUltimaVotacion()).thenReturn(fecha);

		// Comparador de fechas simulado
		comparador = mock(ComparadorDeFechas.class);
	}

	// --------------------------------------------------------------------------------

	@Test
	void retornaTrueSiLaFechaCoincide() {
		FiltroFecha filtro = new FiltroFechaDeUltimaVotacion(fecha, comparador);

		when(comparador.comparar(muestra.getFechaDeUltimaVotacion(), fecha)).thenReturn(true);

		assertTrue(filtro.cumple(muestra));
	}

	// --------------------------------------------------------------------------------

	@Test
	void retornaFalseSiLaFechaNoCoincide() {
		Filtro filtro = new FiltroFechaDeUltimaVotacion(fecha, comparador);

		when(comparador.comparar(muestra.getFechaDeUltimaVotacion(), fecha)).thenReturn(false);

		assertFalse(filtro.cumple(muestra));
	}

	// --------------------------------------------------------------------------------

	@Test
	void filtraMuestrasQueCoincidenConLaFecha() {
		Filtro filtro = new FiltroFechaDeUltimaVotacion(fecha, comparador);

		// Simulamos que la primera muestra cumple el filtro
		when(comparador.comparar(muestra.getFechaDeUltimaVotacion(), fecha)).thenReturn(true);

		List<Muestra> muestrasFiltradas = filtro.filtrar(muestras);

		assertEquals(1, muestrasFiltradas.size());
		assertTrue(muestrasFiltradas.contains(muestra));
	}

	// --------------------------------------------------------------------------------

	@Test
	void retornaListaVaciaSiNingunaMuestraCoincide() {
		Filtro filtro = new FiltroFechaDeUltimaVotacion(fecha, comparador);

		// Simulamos que ninguna muestra cumple el filtro
		when(comparador.comparar(muestra.getFechaDeUltimaVotacion(), fecha)).thenReturn(false);

		List<Muestra> muestrasFiltradas = filtro.filtrar(muestras);

		assertTrue(muestrasFiltradas.isEmpty());
	}

	// --------------------------------------------------------------------------------

	@Test
	void setComparadorCambiaComparadorYFiltraCorrectamente() {
		FiltroFecha filtro = new FiltroFechaDeUltimaVotacion(fecha, comparador);

		ComparadorDeFechas nuevoComparador = mock(ComparadorDeFechas.class);

		when(nuevoComparador.comparar(muestra.getFechaDeUltimaVotacion(), fecha)).thenReturn(true);

		filtro.setComparador(nuevoComparador);

		List<Muestra> muestrasFiltradas = filtro.filtrar(muestras);

		assertEquals(1, muestrasFiltradas.size());
		assertTrue(muestrasFiltradas.contains(muestra));
	}
}
