package organizaciones;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import muestras.Muestra;
import organizaciones.funcionalidades.FuncionalidadExterna;
import ubicaciones.Ubicacion;
import zonas.ZonaDeCobertura;

class OrganizacionTest {
	private Organizacion organizacion;

	private TipoDeOrganizacion tipoDeOrganizacion;
	private Ubicacion ubicacion;
	private int cantEmpleados;

	@BeforeEach
	void setUp() {
		tipoDeOrganizacion = mock(TipoDeOrganizacion.class);
		ubicacion = mock(Ubicacion.class);
		cantEmpleados = 10;

		organizacion = new Organizacion(tipoDeOrganizacion, ubicacion, cantEmpleados);
	}

	// ------------------------------------------------------------

	@Test
	void getTipoDeOrganizacionTest() {
		assertEquals(tipoDeOrganizacion, organizacion.getTipoDeOrganizacion());
	}

	@Test
	void getUbicacionTest() {
		assertEquals(ubicacion, organizacion.getUbicacion());
	}

	@Test
	void getCantEmpleadosTest() {
		assertEquals(cantEmpleados, organizacion.getCantEmpleados());
	}

	// ------------------------------------------------------------

	@Test
	void getFuncionalidadMuestraCargadaPorDefectoEsNula() {
		assertNull(organizacion.getFuncionalidadMuestraCargada());
	}

	@Test
	void getFuncionalidadMuestraValidadaPorDefectoEsNula() {
		assertNull(organizacion.getFuncionalidadMuestraValidada());
	}

	// ------------------------------------------------------------

	@Test
	void setFuncionalidadMuestraCargadaCambiaLaFuncionalidad() {
		FuncionalidadExterna funcionalidad = mock(FuncionalidadExterna.class);

		organizacion.setFuncionalidadMuestraCargada(funcionalidad);

		assertEquals(funcionalidad, organizacion.getFuncionalidadMuestraCargada());
	}

	@Test
	void setFuncionalidadMuestraValidadaCambiaLaFuncionalidad() {
		FuncionalidadExterna funcionalidad = mock(FuncionalidadExterna.class);

		organizacion.setFuncionalidadMuestraValidada(funcionalidad);

		assertEquals(funcionalidad, organizacion.getFuncionalidadMuestraValidada());
	}

	// ------------------------------------------------------------

	@Test
	void updateMuestraCargadaNoEjecutaFuncionalidadSiNoEstaCargada() {
		ZonaDeCobertura zonaDeCobertura = mock(ZonaDeCobertura.class);
		Muestra muestra = mock(Muestra.class);
		FuncionalidadExterna funcionalidad = mock(FuncionalidadExterna.class);

		organizacion.updateMuestraCargada(zonaDeCobertura, muestra);

		verify(funcionalidad, never()).nuevoEvento(organizacion, zonaDeCobertura, muestra);
	}

	@Test
	void updateMuestraCargadaEjecutaFuncionalidadCargada() {
		ZonaDeCobertura zonaDeCobertura = mock(ZonaDeCobertura.class);
		Muestra muestra = mock(Muestra.class);
		FuncionalidadExterna funcionalidad = mock(FuncionalidadExterna.class);

		organizacion.setFuncionalidadMuestraCargada(funcionalidad);

		organizacion.updateMuestraCargada(zonaDeCobertura, muestra);

		verify(funcionalidad).nuevoEvento(organizacion, zonaDeCobertura, muestra);
	}

	// ------------------------------------------------------------

	@Test
	void updateMuestraValidadaNoEjecutaFuncionalidadSiNoEstaCargada() {
		ZonaDeCobertura zonaDeCobertura = mock(ZonaDeCobertura.class);
		Muestra muestra = mock(Muestra.class);
		FuncionalidadExterna funcionalidad = mock(FuncionalidadExterna.class);

		organizacion.updateMuestraValidada(zonaDeCobertura, muestra);

		verify(funcionalidad, never()).nuevoEvento(organizacion, zonaDeCobertura, muestra);
	}

	@Test
	void updateMuestraValidadaEjecutaFuncionalidadCargada() {
		ZonaDeCobertura zonaDeCobertura = mock(ZonaDeCobertura.class);
		Muestra muestra = mock(Muestra.class);
		FuncionalidadExterna funcionalidad = mock(FuncionalidadExterna.class);

		organizacion.setFuncionalidadMuestraValidada(funcionalidad);

		organizacion.updateMuestraValidada(zonaDeCobertura, muestra);

		verify(funcionalidad).nuevoEvento(organizacion, zonaDeCobertura, muestra);
	}
}
