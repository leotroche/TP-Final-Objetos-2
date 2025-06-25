package usuario;

import java.util.ArrayList;

import muestra.Muestra;
import paginaWeb.PaginaWeb;
import usuario.estado.conocimiento.*;
import varios.Opinion;

public class Usuario {
	private EstadoDeConocimiento estadoDeConocimiento;
	private boolean tieneConocimientoValido;
	
	public Usuario() {
		this.setEstadoDeConocimiento(new EstadoBasico());
	}
	
	public Usuario(boolean tieneConocimientoValido) {
		this.setEstadoDeConocimiento(new EstadoEspecialista());
		this.setTieneConocimientoValido(tieneConocimientoValido);
	}
	
	private void setTieneConocimientoValido(boolean tieneConocimientoValido) {
		this.tieneConocimientoValido = tieneConocimientoValido;
	}
	
	public boolean getTieneConocimientoValido() {
		return this.tieneConocimientoValido;
	}

	public EstadoDeConocimiento getEstadoDeConocimiento() {
		return this.estadoDeConocimiento;
	}

	private void setEstadoDeConocimiento(EstadoDeConocimiento estado) {
		this.estadoDeConocimiento = estado;
	}

	public void opinarSobreMuestra(Muestra muestra, Opinion opinion, PaginaWeb paginaWeb) {
		this.estadoDeConocimiento.opinarSobreMuestra(this,muestra, opinion);
		this.promocionarSiCorresponde(paginaWeb);
	}
	
	public void opinarSobreMuestraEnProceso(Muestra muestra, Opinion opinion, PaginaWeb paginaWeb) {
		this.estadoDeConocimiento.opinarSobreMuestraEnProceso(this,muestra, opinion);
		this.promocionarSiCorresponde(paginaWeb);
	}

	public void enviarMuestra(Muestra muestra, PaginaWeb paginaWeb) {
		paginaWeb.agregarMuestra(muestra);
		this.promocionarSiCorresponde(paginaWeb);
	}

	public void promocionarSiCorresponde(PaginaWeb paginaWeb) {
		if (tieneConocimientoValido){
			this.setEstadoDeConocimiento(new EstadoEspecialista());
		}else if (this.tieneMasDe10Envios(paginaWeb) && this.tieneMasDe20Opiniones(paginaWeb)) {
			this.setEstadoDeConocimiento(new EstadoExperto());
		} else {
			this.setEstadoDeConocimiento(new EstadoBasico());
		}
	}

	private boolean tieneMasDe20Opiniones(PaginaWeb paginaWeb) {
		ArrayList<Muestra> muestras = paginaWeb.getMuestrasUltimosNDias();
		Integer cantOpiniones = 0;
		for (Muestra m : muestras) {
			for (Opinion o : m.getOpiniones()) {
				if (o.getAutor() == this) {
					cantOpiniones ++;
				}
			}
		}
		return (cantOpiniones >= 20);
	}

	private boolean tieneMasDe10Envios(PaginaWeb paginaWeb) {
		ArrayList<Muestra> muestras = paginaWeb.getMuestrasUltimosNDias();
		Integer cantEnvios = 0;
		for (Muestra m : muestras) {
			if (m.getUsuario() == this) {
				cantEnvios++;
			}
		}
		return (cantEnvios >= 10);
	}
	
}
