package organizacion;

import eventos.Evento;
import gestores.eventos.Observer;
import gestores.funcionalidades.FuncionalidadExterna;
import gestores.funcionalidades.GestorDeFuncionalidades;
import muestra.Muestra;
import ubicacion.Ubicacion;

public class Organizacion implements Observer {
	
	private Integer cantEmpleados;
	private GestorDeFuncionalidades gestorDeFuncionalidades;
	private Ubicacion ubicacion;
	private TipoDeOrganizacion tipoDeOrganizacion;
	
	
	public Organizacion(Integer cantEmpleados, GestorDeFuncionalidades gestor, Ubicacion ubicacion, TipoDeOrganizacion tipo) {
		this.setCantEmpleados(cantEmpleados);
		this.setGestorDeFuncionalidades(gestor);
		this.setUbicacion(ubicacion);
		this.setTipoDeOrganizacion(tipo);
	}
	
	
	public Integer getCantEmpleados() {
		return cantEmpleados;
	}

	private GestorDeFuncionalidades getGestorDeFuncionalidades() {
		return gestorDeFuncionalidades;
	}
	
	public Ubicacion getUbicacion() {
		return ubicacion;
	}
	
	public TipoDeOrganizacion getTipoDeOrganizacion() {
		return tipoDeOrganizacion;
	}
	
	private void setCantEmpleados(Integer cantEmpleados) {
		this.cantEmpleados = cantEmpleados;
	}

	private void setGestorDeFuncionalidades(GestorDeFuncionalidades gestorDeFuncionalidades) {
		this.gestorDeFuncionalidades = gestorDeFuncionalidades;
	}

	private void setUbicacion(Ubicacion ubicacion) {
		this.ubicacion = ubicacion;
	}

	private void setTipoDeOrganizacion(TipoDeOrganizacion tipoDeOrganizacion) {
		this.tipoDeOrganizacion = tipoDeOrganizacion;
	}

	@Override
	public void update(Evento evento, ZonaDeCobertura zonaDeCobertura, Muestra muestra) {
		this.getGestorDeFuncionalidades().ejecutarPara(evento, this, zonaDeCobertura, muestra);
	}
	
	public void cambiarFuncionalidadPara(Evento evento, FuncionalidadExterna funcionalidad) {
		this.gestorDeFuncionalidades.cambiarPara(evento, funcionalidad);
	}
	
}
