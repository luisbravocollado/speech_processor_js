
package pdl;

import java.util.LinkedList;

public class funcion{ 
	private String token;
	private String nombre;
	private String tipo;
	private int contador; 
	private boolean creado;
	private  LinkedList<id> parametros=new LinkedList<id> ();
	private  LinkedList<id> ids=new LinkedList<id> ();
	
	public funcion(String token) { 
		creado=false;
		contador=0;
		this.token=token;
		this.nombre=nombre;
		this.tipo="unknown"; 
	}
	public boolean isCreado() {
		return creado;
	}
	public void setCreado(boolean creado) {
		this.creado = creado;
	}
	public boolean comprobararg(String tipo) {
		boolean sol=false; 
		if(contador <parametros.size()) {   
		sol=parametros.get(contador).getTipo().equals(tipo);contador++;} 
		else {contador=0;}
		return sol;
	}
	public void reset() {
		if(contador <parametros.size()) {
		contador++;}
		else {contador=0;}
	}
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public void setnombre(String tipo) {
		this.nombre = tipo;
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

	public LinkedList<id> getParametros() {
		return parametros;
	}

	public void setParametros(LinkedList<id> parametros) {
		this.parametros = parametros;
	}

	public LinkedList<id> getIds() {
		return ids;
	}

	public void setIds( id  ids) {
		this.ids.addLast(ids);
	}

	public void meterParametro(id a) {
		a.setIniciado(true);
		parametros.addLast(a);
	}
	public void metervar(id a) {
		parametros.add(a);
	}
}
