

package pdl;

public class token_id {
	private String token;
	private String id_name;
	private int desplazamiento;
	private String tipo;
	private boolean []funcion=new boolean[2] ; //posicion 1 true si es funcion , 2 si es funcion controla si se ha echo la tabla
	private int linea;
	public token_id(String token,String id_name) {
		this.token= token;
		this.id_name=id_name;
	}
	public boolean getToken(String id_name) {
		return this.id_name.equals(id_name);
	} 
	public String getId_name() {
		return id_name;
	}
	public void setId_name(String id_name) {
		this.id_name = id_name;
	}
	public int getDesplazamiento() {
		return desplazamiento;
	}
	public void setDesplazamiento(int desplazamiento) {
		this.desplazamiento = desplazamiento;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public boolean[] getFuncion() {
		return funcion;
	}
	public void setFuncion(boolean[] funcion) {
		this.funcion = funcion;
	}
	public int getLinea() {
		return linea;
	}
	public void setLinea(int linea) {
		this.linea = linea;
	}
	

}
