package peval3Final;

public class comprobarCorreo {
	private String usuario, contraseña, destino, origen, mensaje, asunto;

	public comprobarCorreo(String usuario, String contraseña, String destino, String origen, String mensaje,
			String asunto) {
		this.usuario = usuario;
		this.contraseña = contraseña;
		this.destino = destino;
		this.origen = origen;
		this.mensaje = mensaje;
		this.asunto = asunto;
	}

	public comprobarCorreo() {
		
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getContraseña() {
		return contraseña;
	}

	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}

	public String getDestino() {
		return destino;
	}

	public void setDestino(String destino) {
		this.destino = destino;
	}

	public String getOrigen() {
		return origen;
	}

	public void setOrigen(String origen) {
		this.origen = origen;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getAsunto() {
		return asunto;
	}

	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}

}