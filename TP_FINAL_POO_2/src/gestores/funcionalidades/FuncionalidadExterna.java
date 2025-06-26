package gestores.funcionalidades;

import muestras.Muestra;
import organizaciones.Organizacion;
import zonas.ZonaDeCobertura;

public interface FuncionalidadExterna {
	public void nuevoEvento(Organizacion organizacion, ZonaDeCobertura zonaDeCobertura, Muestra muestra);
}
