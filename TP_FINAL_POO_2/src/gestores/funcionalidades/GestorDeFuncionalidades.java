package gestores.funcionalidades;

import java.util.ArrayList;
import java.util.List;

import eventos.Evento;
import muestras.Muestra;
import organizaciones.Organizacion;
import zonas.*;

public class GestorDeFuncionalidades {
	private List<RegistroDeFuncionalidad> registros = new ArrayList<>();

	private List<RegistroDeFuncionalidad> getRegistros() {
		return this.registros;
	}

	public boolean hayFuncionalidadPara(Evento evento) {
		return this.getRegistros().stream().anyMatch(a -> a.correspondeA(evento));
	}

	public void cambiarPara(Evento evento, FuncionalidadExterna funcionalidad) {
		this.getRegistros().removeIf(a -> a.correspondeA(evento));
		this.getRegistros().add(new RegistroDeFuncionalidad(evento, funcionalidad));
	}

	public void ejecutarPara(Evento evento, Organizacion organizacion, ZonaDeCobertura zonaDeCobertura,	Muestra muestra) {
		for (RegistroDeFuncionalidad registro : this.getRegistros()) {
			if (registro.correspondeA(evento)) {
				registro.ejecutar(organizacion, zonaDeCobertura, muestra);
			}
		}
	}
}
