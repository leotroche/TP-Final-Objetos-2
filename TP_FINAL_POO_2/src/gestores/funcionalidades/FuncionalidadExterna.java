package gestores.funcionalidades;

import muestra.Muestra;
import organizacion.Organizacion;
import organizacion.ZonaDeCobertura;

public interface FuncionalidadExterna {
	public void nuevoEvento(Organizacion organizacion, ZonaDeCobertura zonaDeCobertura, Muestra muestra);
}
