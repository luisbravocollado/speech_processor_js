
package pdl;



public class TS {
	private String lexema;
	private String tipo;
	private int desplazamiento;//ver exactamente que es el desplazamiento
	// private Funcion fun;
	//private int num;
	public  TS (String lex) {//A�adimos como parametro el tipe (constructor para los lexemas)
		this.lexema=lex;
		//this.tipo=tipe;
	} 
	/*public TS (String lex,Funcion fun) {para la funcion
		this.lexema=lex;
		tipo="funcion";
		this.fun=fun;
	}*/

	//tabla para los lexemas
	public String generar() {
		return  "*LEXEMA :"+"'"+lexema+"'\n  ATRIBUTOS:\n  " +"tipo:"+tipo+" \n   +desplazamiento:\n";
	}
	/*public String generar2() {
		return  "*LEXEMA :"+"'"+lexema+"'\n  ATRIBUTOS:\n  " +"tipo:"+tipo+" \n   +desplazamiento:\n"+desplazamiento+"\n   +numParams:\n"+num+"tipo";
	}*/
	public String getlexema() {
		return lexema;
	}
	public void setDesplazamiento(int des) {
		desplazamiento=des;
	}
	public String getTipo() {
		return tipo;
	}

	//tabla para la funcion









	//falta juntar las dos tablas


}
