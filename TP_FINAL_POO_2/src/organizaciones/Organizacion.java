package organizaciones;

import gestores.eventos.Observer;
import ubicaciones.Ubicacion;

public class Organizacion implements Observer {
	private int cantEmpleados;
	private Ubicacion ubicacion;
	private TipoDeOrganizacion tipoDeOrganizacion;

	public Organizacion(TipoDeOrganizacion tipoDeOrganizacion, Ubicacion ubicacion, int cantDeEmpleados) {
		this.setCantEmpleados(cantDeEmpleados);
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

	private void setTipoDeOrganizacion(TipoDeOrganizacion tipoDeOrganizacion) {
		this.tipoDeOrganizacion = tipoDeOrganizacion;
	}

	private void setUbicacion(Ubicacion ubicacion) {
		this.ubicacion = ubicacion;
	}

	private void setCantEmpleados(int cantEmpleados) {
		this.cantEmpleados = cantEmpleados;
	}
}
