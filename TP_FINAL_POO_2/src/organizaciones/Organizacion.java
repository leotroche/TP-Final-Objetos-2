package organizaciones;

import gestores.eventos.Observer;
import muestras.Muestra;
import organizaciones.funcionalidades.FuncionalidadExterna;
import ubicaciones.Ubicacion;
import zonas.ZonaDeCobertura;

public class Organizacion implements Observer {
	private TipoDeOrganizacion tipoDeOrganizacion;
	private Ubicacion ubicacion;
	private int cantEmpleados;

	private FuncionalidadExterna funcionalidadMuestraCargada;
	private FuncionalidadExterna funcionalidadMuestraValidada;

	public Organizacion(TipoDeOrganizacion tipoDeOrganizacion, Ubicacion ubicacion, int cantDeEmpleados) {
		this.setTipoDeOrganizacion(tipoDeOrganizacion);
		this.setUbicacion(ubicacion);
		this.setCantEmpleados(cantDeEmpleados);
	}

	// ------------------------------------------------------------

	public TipoDeOrganizacion getTipoDeOrganizacion() {
		return this.tipoDeOrganizacion;
	}

	private void setTipoDeOrganizacion(TipoDeOrganizacion tipoDeOrganizacion) {
		this.tipoDeOrganizacion = tipoDeOrganizacion;
	}

	public Ubicacion getUbicacion() {
		return this.ubicacion;
	}

	private void setUbicacion(Ubicacion ubicacion) {
		this.ubicacion = ubicacion;
	}

	public int getCantEmpleados() {
		return this.cantEmpleados;
	}

	private void setCantEmpleados(int cantEmpleados) {
		this.cantEmpleados = cantEmpleados;
	}

	// ------------------------------------------------------------
	// Metodos de estrategia de funcionalidades
	// ------------------------------------------------------------

	public FuncionalidadExterna getFuncionalidadMuestraCargada() {
		return this.funcionalidadMuestraCargada;
	}

	public FuncionalidadExterna getFuncionalidadMuestraValidada() {
		return this.funcionalidadMuestraValidada;
	}

	public void setFuncionalidadMuestraCargada(FuncionalidadExterna funcionalidad) {
		this.funcionalidadMuestraCargada = funcionalidad;
	}

	public void setFuncionalidadMuestraValidada(FuncionalidadExterna funcionalidad) {
		this.funcionalidadMuestraValidada = funcionalidad;
	}

	// ------------------------------------------------------------
	// Metodos de eventos
	// ------------------------------------------------------------

	@Override
	public void updateMuestraCargada(ZonaDeCobertura zonaDeCobertura, Muestra muestra) {
		if (this.getFuncionalidadMuestraCargada() != null) {
			this.getFuncionalidadMuestraCargada().nuevoEvento(this, zonaDeCobertura, muestra);
		}
	}

	@Override
	public void updateMuestraValidada(ZonaDeCobertura zonaDeCobertura, Muestra muestra) {
		if (this.getFuncionalidadMuestraValidada() != null) {
			this.getFuncionalidadMuestraValidada().nuevoEvento(this, zonaDeCobertura, muestra);
		}
	}
}
