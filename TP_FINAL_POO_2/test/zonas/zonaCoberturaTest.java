package zonas;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import eventos.Evento;

import java.util.List;

import gestores.eventos.GestorDeEventos;
import gestores.funcionalidades.GestorDeFuncionalidades;
import muestras.Muestra;
import organizaciones.Organizacion;
import organizaciones.TipoDeOrganizacion;
import ubicaciones.Ubicacion;
import zonas.ZonaDeCobertura;

class zonaCoberturaTest {
	ZonaDeCobertura zona;
	GestorDeEventos gestor = mock(GestorDeEventos.class);
	Ubicacion epicentro = mock(Ubicacion.class);
	
	@BeforeEach
    void setUp() {
		zona = new ZonaDeCobertura("Quilmes", epicentro, 50, gestor);
    }
	
	@Test
	void zonaDeCoberturaGetNombretest() {
		assertEquals("Quilmes", zona.getNombre());
	}
	
	@Test
	void zonaDeCoberturaGetEpicentrotest() {
		assertEquals(epicentro, zona.getEpicentro());
	}
	
	@Test
	void zonaDeCoberturaGetRadiotest() {
		assertEquals(50, zona.getRadio());
	}
		
	@Test
	void zonaDeCoberturaGetMuestrastest() {
		assertEquals(0, zona.getMuestras().size());
	}
	
	@Test
	void zonaDeCoberturaPerteneceALaZonatest() {
		Muestra muestraMock = mock(Muestra.class);
		zona.agregarMuestra(muestraMock);
		assertEquals(1, zona.getMuestras().size());
	}
	
	@Test
	void testPerteneceALaZona_trueCuandoEstaDentroDelRadio() {
		Muestra muestraMock = mock(Muestra.class);
		Ubicacion ubicacionMuestra = mock(Ubicacion.class);
		when(muestraMock.getUbicacion()).thenReturn(ubicacionMuestra);
		when(epicentro.calcularDistanciaKm(ubicacionMuestra)).thenReturn(30.0);
		assertTrue(zona.perteneceALaZona(muestraMock));
	}
	
	@Test
	void testPerteneceALaZona_falseCuandoEstaFueraDelRadio() {
		Muestra muestraMock = mock(Muestra.class);
		Ubicacion ubicacionMuestra = mock(Ubicacion.class);
		when(muestraMock.getUbicacion()).thenReturn(ubicacionMuestra);
		when(epicentro.calcularDistanciaKm(ubicacionMuestra)).thenReturn(80.0);
		assertFalse(zona.perteneceALaZona(muestraMock));
	}
	
	@Test
    void testSeSolapaCon_trueCuandoLasZonasSeSuperponen() {
        Ubicacion epicentroMock = mock(Ubicacion.class);
        ZonaDeCobertura otraZona = new ZonaDeCobertura("Berazategui", epicentroMock, 10.0, gestor);

        when(epicentro.calcularDistanciaKm(epicentroMock)).thenReturn(15.0);

        assertTrue(zona.seSolapaCon(otraZona));
    }

    @Test
    void testSeSolapaCon_falseCuandoLasZonasNoSeSuperponen() {
        Ubicacion epicentroMock = mock(Ubicacion.class);
        ZonaDeCobertura otraZona = new ZonaDeCobertura("Moron", epicentroMock, 20.0, gestor);

        when(epicentro.calcularDistanciaKm(epicentroMock)).thenReturn(90.0);

        assertFalse(zona.seSolapaCon(otraZona));
    }
    
    @Test
    void testZonaDeCoberturasZonasSolapadasTiene1() {
        Ubicacion epicentroMock = mock(Ubicacion.class);
        ZonaDeCobertura otra1 = new ZonaDeCobertura("Moron", epicentroMock, 20.0, gestor);
        Ubicacion epicentroMock2 = mock(Ubicacion.class);
        ZonaDeCobertura otra2 = new ZonaDeCobertura("Berazategui", epicentroMock2, 10.0, gestor);

        when(epicentro.calcularDistanciaKm(epicentroMock)).thenReturn(90.0);
        when(epicentro.calcularDistanciaKm(epicentroMock2)).thenReturn(15.0);
        List<ZonaDeCobertura> zonas = List.of(otra1, otra2);
        assertEquals(1, zona.zonasSolapadas(zonas).size());
    }
    
    @Test
    void testZonaDeCoberturaSuscribirOrganizacion() {
        Evento evento = mock(Evento.class);
        Organizacion organizacion = new Organizacion(
            	mock(TipoDeOrganizacion.class),
            	mock(Ubicacion.class),
            	10,
            	mock(GestorDeFuncionalidades.class)
            );
            zona.suscribirOrganizacion(evento, organizacion);
            verify(gestor).suscribir(evento, organizacion);
    }
    
    @Test
    void testZonaDeCoberturaDesuscribirOrganizacion() {
        Evento evento = mock(Evento.class);
        Organizacion organizacion = new Organizacion(
            	mock(TipoDeOrganizacion.class),
            	mock(Ubicacion.class),
            	10,
            	mock(GestorDeFuncionalidades.class)
            );
            zona.suscribirOrganizacion(evento, organizacion);
            zona.desuscribirOrganizacion(evento, organizacion);
            verify(gestor).desuscribir(evento, organizacion);
    }
    
    @Test
    void testZonaDeCoberturaNotificarOrganizaciones() {
        Evento evento = mock(Evento.class);
        Muestra muestraMock = mock(Muestra.class);
        zona.notificarOrganizaciones(evento,zona, muestraMock);
        verify(gestor).notificar(evento,zona, muestraMock);
    }
    
    @Test
    void testZonaDeCoberturaUpdate() {
        Evento evento = mock(Evento.class);
        Muestra muestraMock = mock(Muestra.class);
        Ubicacion ubicacionMuestra = mock(Ubicacion.class);
        when(muestraMock.getUbicacion()).thenReturn(ubicacionMuestra);
        when(epicentro.calcularDistanciaKm(ubicacionMuestra)).thenReturn(30.0);
        zona.update(evento,zona,muestraMock);
        assertTrue(zona.getMuestras().contains(muestraMock));
    }
    
    @Test
    void testZonaDeCoberturaUpdateEsFalse() {
        Evento evento = mock(Evento.class);
        Muestra muestraMock = mock(Muestra.class);
        Ubicacion ubicacionMuestra = mock(Ubicacion.class);
        when(muestraMock.getUbicacion()).thenReturn(ubicacionMuestra);
        when(epicentro.calcularDistanciaKm(ubicacionMuestra)).thenReturn(100.0);
        zona.update(evento,zona,muestraMock);
        assertFalse(zona.getMuestras().contains(muestraMock));
    }
}
