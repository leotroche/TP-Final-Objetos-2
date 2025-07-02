package usuarios;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import muestras.Muestra;
import muestras.Opinion;
import usuarios.estados.EstadoBasico;
import usuarios.estados.EstadoDeConocimiento;
import usuarios.estados.EstadoEspecialista;
import usuarios.estados.EstadoExperto;
import web.PaginaWeb;

public class Usuario {
	private EstadoDeConocimiento estadoDeConocimiento;
	private boolean tieneConocimientoValido;
	private List<Muestra> muestrasEnviadas = new ArrayList<>();
	private List<Opinion> opinionesDadas = new ArrayList<>();

	public Usuario(boolean tieneConocimientoValido) {
		this.setEstadoDeConocimiento(tieneConocimientoValido ? new EstadoEspecialista() : new EstadoBasico());
		this.setTieneConocimientoValido(tieneConocimientoValido);
	}

	// ------------------------------------------------------------

	public boolean getTieneConocimientoValido() {
		return this.tieneConocimientoValido;
	}

	private void setTieneConocimientoValido(boolean tieneConocimientoValido) {
		this.tieneConocimientoValido = tieneConocimientoValido;
	}

	// ------------------------------------------------------------

	public List<Muestra> getMuestrasEnviadas() {
		return this.muestrasEnviadas;
	}

	public void agregarMuestraEnviada(Muestra muestra) {
		this.getMuestrasEnviadas().add(muestra);
	}

	public List<Opinion> getOpinionesDadas() {
		return this.opinionesDadas;
	}

	public void agregarOpinionDada(Opinion opinion) {
		this.getOpinionesDadas().add(opinion);
	}

	// ------------------------------------------------------------
	// Metodos de estado de conocimiento
	// ------------------------------------------------------------

	public EstadoDeConocimiento getEstadoDeConocimiento() {
		return this.estadoDeConocimiento;
	}

	public void setEstadoDeConocimiento(EstadoDeConocimiento estado) {
		this.estadoDeConocimiento = estado;
	}

	public void opinarSobreMuestra(Muestra muestra, Opinion opinion) {
		this.getEstadoDeConocimiento().opinarSobreMuestra(this, muestra, opinion);
		this.agregarOpinionDada(opinion);
		this.promocionarSiCorresponde();
	}

	public void opinarSobreMuestraEnProceso(Muestra muestra, Opinion opinion) {
		this.getEstadoDeConocimiento().opinarSobreMuestraEnProceso(this, muestra, opinion);
		this.agregarOpinionDada(opinion);
		this.promocionarSiCorresponde();
	}

	public void promocionarSiCorresponde() {
		boolean esEspecialista = this.getTieneConocimientoValido();

		if (!esEspecialista &&
				this.tieneAlMenosNEnviosRealizadosEnLosUltimosNDias(10, 30) &&
				this.tieneAlmenosNOpinionesDadasEnLosUltimosNDias(20, 30)) {

			this.setEstadoDeConocimiento(new EstadoExperto());
		} else {
			this.setEstadoDeConocimiento(new EstadoBasico());
		}
	}


	// ------------------------------------------------------------

	public void enviarMuestra(Muestra muestra, PaginaWeb web) {
		web.agregarMuestra(muestra);
		this.agregarMuestraEnviada(muestra);
		this.promocionarSiCorresponde();
	}

	public List<Muestra> muestrasDeLosUltimosNDias(int cantidad) {
		return this.getMuestrasEnviadas().stream()
				.filter(m -> m.getFechaDeCreacion().isAfter(LocalDate.now().minusDays(cantidad)))
				.toList();
	}

	public List<Opinion> opinionesDadasEnLosUltimosNDias(int cantidad) {
		return this.getOpinionesDadas().stream()
				.filter(o -> o.getFechaDeVotacion().isAfter(LocalDate.now().minusDays(cantidad)))
				.toList();
	}

	public boolean tieneAlmenosNOpinionesDadasEnLosUltimosNDias(int cantidad, int dias) {
		return opinionesDadasEnLosUltimosNDias(dias).size() >= cantidad;
	}

	public boolean tieneAlMenosNEnviosRealizadosEnLosUltimosNDias(int cantidad, int dias) {
		return this.muestrasDeLosUltimosNDias(dias).size() >= cantidad;
	}
}
