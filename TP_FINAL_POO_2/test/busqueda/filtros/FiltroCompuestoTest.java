package busqueda.filtros;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import busqueda.filtros.logica.LogicaDeComposicion;
import muestras.Muestra;

class FiltroCompuestoTest {
	private LogicaDeComposicion logicaAnd;
	private LogicaDeComposicion logicaOr;

	private Muestra muestra;
	private List<Muestra> muestras;

	private Filtro filtroA;
	private Filtro filtroB;
	private Filtro filtroC;
	private Filtro filtroD;

	@BeforeEach
	void setUp() {
		// Lógica de composición simulada
		logicaAnd = mock(LogicaDeComposicion.class);
		logicaOr = mock(LogicaDeComposicion.class);

		// Muestras simuladas
		muestra = mock(Muestra.class);
		muestras = List.of(
				mock(Muestra.class),
				mock(Muestra.class),
				muestra,
				mock(Muestra.class),
				mock(Muestra.class)
				);

		// Filtros simulados
		filtroA = mock(Filtro.class);
		filtroB = mock(Filtro.class);
		filtroC = mock(Filtro.class);
		filtroD = mock(Filtro.class);
	}

	// ------------------------------------------------------------
	// Escenario 1: AND(filtroA, filtroB)
	// ------------------------------------------------------------

	@Test
	void filtrarMuestrasCuandoAmbosFiltrosSeCumplenConLogicaAnd() {
		FiltroCompuesto filtro = new FiltroCompuesto(logicaAnd);
		filtro.agregarFiltro(filtroA);
		filtro.agregarFiltro(filtroB);

		when(logicaAnd.evaluar(filtro.getFiltros(), muestra)).thenReturn(true);

		List<Muestra> muestrasFiltradas = filtro.filtrar(muestras);

		assertEquals(1, muestrasFiltradas.size());
		assertTrue(muestrasFiltradas.contains(muestra));
	}

	// ------------------------------------------------------------
	// Escenario 2: OR(filtroA, filtroB)
	// ------------------------------------------------------------

	@Test
	void filtrarMuestrasCuandoAlgunoDeLosFiltrosSeCumpleConLogicaOr() {
		FiltroCompuesto filtro = new FiltroCompuesto(logicaOr);
		filtro.agregarFiltro(filtroA);
		filtro.agregarFiltro(filtroB);

		when(logicaOr.evaluar(filtro.getFiltros(), muestra)).thenReturn(true);

		List<Muestra> muestrasFiltradas = filtro.filtrar(muestras);

		assertEquals(1, muestrasFiltradas.size());
		assertTrue(muestrasFiltradas.contains(muestra));
	}

	// ------------------------------------------------------------
	// Escenario 3: Cambio de lógica
	// ------------------------------------------------------------

	@Test
	void filtraMuestrasConCambioDeLogica() {
		FiltroCompuesto filtro = new FiltroCompuesto(logicaAnd);
		filtro.agregarFiltro(filtroA);
		filtro.agregarFiltro(filtroB);

		// No cumple con AND
		when(logicaAnd.evaluar(filtro.getFiltros(), muestra)).thenReturn(false);

		// Cumple con OR
		when(logicaOr.evaluar(filtro.getFiltros(), muestra)).thenReturn(true);

		filtro.setLogica(logicaOr);

		List<Muestra> muestrasFiltradas = filtro.filtrar(muestras);

		// verify
		assertEquals(1, muestrasFiltradas.size());
		assertTrue(muestrasFiltradas.contains(muestra));
	}

	// ------------------------------------------------------------
	// Escenario 4: Eliminación de filtros
	// ------------------------------------------------------------

	@Test
	void filtraMuestrasConEliminacionDeFiltro() {
		FiltroCompuesto filtro = new FiltroCompuesto(logicaAnd);
		filtro.agregarFiltro(filtroA); // Cumple
		filtro.agregarFiltro(filtroB); // No cumple

		// No cumple con AND
		when(logicaAnd.evaluar(filtro.getFiltros(), muestra)).thenReturn(false);

		// Se elimina el filtroB
		filtro.eliminarFiltro(filtroB);

		// Ahora cumple con AND
		when(logicaAnd.evaluar(filtro.getFiltros(), muestra)).thenReturn(true);

		List<Muestra> muestrasFiltradas = filtro.filtrar(muestras);

		assertEquals(1, muestrasFiltradas.size());
		assertTrue(muestrasFiltradas.contains(muestra));
	}

	// ------------------------------------------------------------
	// Escenario 5: Cumplen A y B, pero no C
	//
	// AND(
	//   FiltroA,
	//   OR(filtroB, filtroC)
	// )
	//
	// La muestra pasa el filtro compuesto
	// ------------------------------------------------------------

	@Test
	void filtraMuestrasConFiltrosCompuestosAndOr() {
		FiltroCompuesto filtroOr = new FiltroCompuesto(logicaOr);
		FiltroCompuesto filtroAnd = new FiltroCompuesto(logicaAnd);

		filtroOr.agregarFiltro(filtroB); // Cumple
		filtroOr.agregarFiltro(filtroC); // No Cumple

		when(logicaOr.evaluar(filtroOr.getFiltros(), muestra)).thenReturn(true);

		filtroAnd.agregarFiltro(filtroA);  // Cumple
		filtroAnd.agregarFiltro(filtroOr); // Cumple

		when(logicaAnd.evaluar(filtroAnd.getFiltros(), muestra)).thenReturn(true);

		List<Muestra> muestrasFiltradas = filtroAnd.filtrar(muestras);

		assertEquals(1, muestrasFiltradas.size());
		assertTrue(muestrasFiltradas.contains(muestra));
	}

	// ------------------------------------------------------------
	// Escenario 6: Cumplen A y B, pero no C
	//
	// OR(
	//   filtroA,
	//   AND(filtroB, filtroC)
	// )
	//
	// La muestra no pasa el filtro compuesto
	// ------------------------------------------------------------

	@Test
	void filtraMuestrasConFiltrosCompuestosOrAnd() {
		FiltroCompuesto filtroOr = new FiltroCompuesto(logicaOr);
		FiltroCompuesto filtroAnd = new FiltroCompuesto(logicaAnd);

		filtroAnd.agregarFiltro(filtroB);
		filtroAnd.agregarFiltro(filtroC);

		when(logicaOr.evaluar(filtroOr.getFiltros(), muestra)).thenReturn(false);

		filtroOr.agregarFiltro(filtroA);
		filtroOr.agregarFiltro(filtroAnd);

		when(logicaAnd.evaluar(filtroAnd.getFiltros(), muestra)).thenReturn(true);

		List<Muestra> muestrasFiltradas = filtroOr.filtrar(muestras);

		assertEquals(0, muestrasFiltradas.size());
		assertTrue(muestrasFiltradas.isEmpty());
	}

	// ------------------------------------------------------------
	// Escenario 7: Cumplen A y B, pero no C ni D
	//
	// AND(
	//   filtroA,
	//   OR(filtroB, AND(filtroC, filtroD))
	// )
	//
	// La muestra pasa el filtro compuesto
	// ------------------------------------------------------------

	@Test
	void filtraMuestrasConFiltrosCompuestosAndOrAnd() {
		FiltroCompuesto filtroOr = new FiltroCompuesto(logicaOr);
		FiltroCompuesto filtroAndExterno = new FiltroCompuesto(logicaAnd);
		FiltroCompuesto filtroAndInterno = new FiltroCompuesto(logicaAnd);

		filtroAndInterno.agregarFiltro(filtroC); // No cumple
		filtroAndInterno.agregarFiltro(filtroD); // No cumple

		when(logicaAnd.evaluar(filtroAndInterno.getFiltros(), muestra)).thenReturn(false);

		filtroOr.agregarFiltro(filtroB);          // Cumple
		filtroOr.agregarFiltro(filtroAndInterno); // No cumple

		when(logicaOr.evaluar(filtroOr.getFiltros(), muestra)).thenReturn(true);

		filtroAndExterno.agregarFiltro(filtroA);  // Cumple
		filtroAndExterno.agregarFiltro(filtroOr); // Cumple

		when(logicaAnd.evaluar(filtroAndExterno.getFiltros(), muestra)).thenReturn(true);

		List<Muestra> muestrasFiltradas = filtroAndExterno.filtrar(muestras);

		assertEquals(1, muestrasFiltradas.size());
		assertTrue(muestrasFiltradas.contains(muestra));
	}

	// ------------------------------------------------------------
	// Escenario 8: Cumplen A y B, pero no C ni D
	//
	// OR(
	//   filtroA,
	//   AND(filtroB, OR(filtroC, filtroD))
	// )
	//
	// La muestra no pasa el filtro compuesto
	// ------------------------------------------------------------

	@Test
	void filtraMuestrasConFiltrosCompuestosOrAndOr() {
		FiltroCompuesto filtroAnd = new FiltroCompuesto(logicaOr);
		FiltroCompuesto filtroOrInterno = new FiltroCompuesto(logicaOr);
		FiltroCompuesto filtroOrExterno = new FiltroCompuesto(logicaAnd);

		filtroOrInterno.agregarFiltro(filtroC); // No cumple
		filtroOrInterno.agregarFiltro(filtroD); // No cumple

		when(logicaOr.evaluar(filtroOrInterno.getFiltros(), muestra)).thenReturn(false);

		filtroAnd.agregarFiltro(filtroB);         // Cumple
		filtroAnd.agregarFiltro(filtroOrInterno); // No cumple

		when(logicaAnd.evaluar(filtroAnd.getFiltros(), muestra)).thenReturn(false);

		filtroOrExterno.agregarFiltro(filtroA);   // Cumple
		filtroOrExterno.agregarFiltro(filtroAnd); // No cumple

		when(logicaOr.evaluar(filtroOrExterno.getFiltros(), muestra)).thenReturn(false);

		List<Muestra> muestrasFiltradas = filtroOrExterno.filtrar(muestras);

		assertEquals(0, muestrasFiltradas.size());
		assertTrue(muestrasFiltradas.isEmpty());
	}

	// ------------------------------------------------------------
	// Escenario 9: Cumple A y OR vacío
	//
	// AND(filtroA, OR())
	//
	// La muestra pasa el filtro compuesto
	// ------------------------------------------------------------

	@Test
	void filtraMuestrasConOrVacioDentroDeAnd() {
		FiltroCompuesto filtroOr = new FiltroCompuesto(logicaOr);
		FiltroCompuesto filtroAnd = new FiltroCompuesto(logicaAnd);

		filtroAnd.agregarFiltro(filtroA);  // Cumple
		filtroAnd.agregarFiltro(filtroOr); // Cumple

		when(logicaAnd.evaluar(filtroAnd.getFiltros(), muestra)).thenReturn(true);

		List<Muestra> muestrasFiltradas = filtroAnd.filtrar(muestras);

		assertEquals(1, muestrasFiltradas.size());
		assertTrue(muestrasFiltradas.contains(muestra));
	}

	// ------------------------------------------------------------
	// Escenario 10: Cumple A y AND vacío
	//
	// OR(filtroA, AND())
	// ------------------------------------------------------------

	@Test
	void filtraMuestrasConAndVacioDentroDeOr() {
		FiltroCompuesto filtroOr = new FiltroCompuesto(logicaOr);
		FiltroCompuesto filtroAnd = new FiltroCompuesto(logicaAnd);

		filtroOr.agregarFiltro(filtroA);  // Cumple
		filtroOr.agregarFiltro(filtroAnd); // Cumple

		when(logicaOr.evaluar(filtroOr.getFiltros(), muestra)).thenReturn(true);

		List<Muestra> muestrasFiltradas = filtroOr.filtrar(muestras);

		assertEquals(1, muestrasFiltradas.size());
		assertTrue(muestrasFiltradas.contains(muestra));
	}
}
