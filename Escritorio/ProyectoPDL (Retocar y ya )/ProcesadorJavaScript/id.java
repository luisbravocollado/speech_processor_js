


package pdl;

public class id {
	public String token;
	public String nombre;
	public String tipo;
	public boolean iniciado;
	public int desplazamiento;
	
	
	public id(String token,String nombre) { 
		this.token=token;
		this.nombre=nombre;
		this.tipo="unknown"; 
		iniciado=false;
		
	}
	
	public boolean isIniciado() {
		return iniciado;
	}

	public void setIniciado(boolean iniciado) {
		this.iniciado = iniciado;
	}

	public String getTipo() {
		return tipo;
	}
	public String getnombre() {
		return nombre;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public int getDesplazamiento() {
		return desplazamiento;
	}

	public void setDesplazamiento(int desplazamiento) {
		this.desplazamiento = desplazamiento;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	
}
