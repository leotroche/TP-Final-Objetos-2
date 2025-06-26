package organizaciones;

import eventos.Evento;
import gestores.eventos.Observer;
import gestores.funcionalidades.FuncionalidadExterna;
import gestores.funcionalidades.GestorDeFuncionalidades;
import muestras.Muestra;
import ubicaciones.Ubicacion;
import zonas.ZonaDeCobertura;

public class Organizacion implements Observer {
	private int cantEmpleados;
	private GestorDeFuncionalidades gestorDeFuncionalidades;
	private Ubicacion ubicacion;
	private TipoDeOrganizacion tipoDeOrganizacion;

	public Organizacion(
			TipoDeOrganizacion tipoDeOrganizacion, Ubicacion ubicacion,
			int cantDeEmpleados, GestorDeFuncionalidades gestorDeFuncionalidades
			) {
		this.setCantEmpleados(cantDeEmpleados);
		this.setGestorDeFuncionalidades(gestorDeFuncionalidades);
		this.setUbicacion(ubicacion);
		this.setTipoDeOrganizacion(tipoDeOrganizacion);
	}

	// ------------------------------------------------------------

	public TipoDeOrganizacion getTipoDeOrganizacion() {
		return this.tipoDeOrganizacion;
	}

	public Ubicacion getUbicacion() {
		return this.ubicacion;
	}

	public int getCantEmpleados() {
		return this.cantEmpleados;
	}

	private GestorDeFuncionalidades getGestorDeFuncionalidades() {
		return this.gestorDeFuncionalidades;
	}

	private void setTipoDeOrganizacion(TipoDeOrganizacion tipoDeOrganizacion) {
		this.tipoDeOrganizacion = tipoDeOrganizacion;
	}

	private void setUbicacion(Ubicacion ubicacion) {
		this.ubicacion = ubicacion;
	}

	private void setCantEmpleados(int cantEmpleados) {
		this.cantEmpleados = cantEmpleados;
	}

	private void setGestorDeFuncionalidades(GestorDeFuncionalidades gestorDeFuncionalidades) {
		this.gestorDeFuncionalidades = gestorDeFuncionalidades;
	}

	// ------------------------------------------------------------
	// Metodos de eventos
	// ------------------------------------------------------------

	@Override
	public void update(Evento evento, ZonaDeCobertura zonaDeCobertura, Muestra muestra) {
		this.getGestorDeFuncionalidades().ejecutarPara(evento, this, zonaDeCobertura, muestra);
	}

	// ------------------------------------------------------------
	// Metodos de estrategia de funcionalidades
	// ------------------------------------------------------------

	public void cambiarFuncionalidadPara(Evento evento, FuncionalidadExterna funcionalidad) {
		this.getGestorDeFuncionalidades().cambiarPara(evento, funcionalidad);
	}
}
