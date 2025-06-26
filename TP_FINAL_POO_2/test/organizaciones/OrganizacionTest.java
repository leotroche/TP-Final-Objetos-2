package organizaciones;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import eventos.Evento;
import gestores.funcionalidades.FuncionalidadExterna;
import gestores.funcionalidades.GestorDeFuncionalidades;
import muestras.Muestra;
import ubicaciones.Ubicacion;
import zonas.ZonaDeCobertura;

class OrganizacionTest {
	private Organizacion organizacion;

	private TipoDeOrganizacion tipoDeOrganizacion;
	private Ubicacion ubicacion;
	private int cantEmpleados;
	private GestorDeFuncionalidades gestorDeFuncionalidades;

	@BeforeEach
	void setUp() {
		tipoDeOrganizacion = mock(TipoDeOrganizacion.class);
		ubicacion = mock(Ubicacion.class);
		cantEmpleados = 10;
		gestorDeFuncionalidades = mock(GestorDeFuncionalidades.class);

		organizacion = new Organizacion(tipoDeOrganizacion, ubicacion, cantEmpleados, gestorDeFuncionalidades);
	}

	@Test
	void getTipoDeOrganizacion() {
		assertEquals(tipoDeOrganizacion, organizacion.getTipoDeOrganizacion());
	}

	@Test
	void getUbicacion() {
		assertEquals(ubicacion, organizacion.getUbicacion());
	}

	@Test
	void getCantEmpleados() {
		assertEquals(cantEmpleados, organizacion.getCantEmpleados());
	}

	@Test
	void cambiarFuncionalidadParaDelegaAlGestorDeFuncionalidades() {
		Evento evento = mock(Evento.class);
		FuncionalidadExterna funcionalidad = mock(FuncionalidadExterna.class);

		organizacion.cambiarFuncionalidadPara(evento,funcionalidad);

		verify(gestorDeFuncionalidades).cambiarPara(evento, funcionalidad);
	}

	@Test
	void updateDelegaLaEjecucionDeLaFuncionalidadEnElGestorDeFuncionalidades() {
		Evento evento = mock(Evento.class);
		ZonaDeCobertura zonaDeCobertura = mock(ZonaDeCobertura.class);
		Muestra muestra = mock(Muestra.class);

		organizacion.update(evento, zonaDeCobertura, muestra);

		verify(gestorDeFuncionalidades).ejecutarPara(evento, organizacion, zonaDeCobertura, muestra);
	}
}
