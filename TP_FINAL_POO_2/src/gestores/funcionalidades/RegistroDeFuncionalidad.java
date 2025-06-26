package gestores.funcionalidades;

import eventos.Evento;
import muestras.Muestra;
import organizaciones.Organizacion;
import zonas.ZonaDeCobertura;

public class RegistroDeFuncionalidad {
	private Evento evento;
	private FuncionalidadExterna funcionalidad;

	public RegistroDeFuncionalidad(Evento evento, FuncionalidadExterna funcionalidad) {
		this.setEvento(evento);
		this.setFuncionalidad(funcionalidad);
	}

	private Evento getEvento() {
		return this.evento;
	}

	private FuncionalidadExterna getFuncionalidad() {
		return this.funcionalidad;
	}

	private void setEvento(Evento evento) {
		this.evento = evento;
	}

	private void setFuncionalidad(FuncionalidadExterna funcionalidad) {
		this.funcionalidad = funcionalidad;
	}

	public boolean correspondeA(Evento evento) {
		return this.getEvento().equals(evento);
	}

	public void ejecutar(Organizacion organizacion, ZonaDeCobertura zonaDeCobertura, Muestra muestra) {
		if (this.getFuncionalidad() != null) {
			this.getFuncionalidad().nuevoEvento(organizacion, zonaDeCobertura, muestra);
		}
	}
}
