


package pdl;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class tablasimbolos {
	private static String []tokens; 
	private static String tabla ;
	private static String aux="";
	private static int id=0;
	private static LinkedList<String> metidos=new LinkedList<String> ();
	private static boolean funcion=false; 
	private static boolean bucle=false;
	private static LinkedList<String> metidosfun=new LinkedList<String> ();
	private static LinkedList<String> metidosbucle=new LinkedList<String> ();
	//crear lista tablas funciones
	private static int numero; 
	private static LinkedList< String> nomid; 
	private static LinkedList< String> nomfun;
	
	
	public static LinkedList< String> lista() {
		return nomid;
		
	}
	public  tablasimbolos(String a,  LinkedList< String> n) {
		tokens=a.split("\n");
		nomfun=new LinkedList<String> ();
		nomid=n;
		tabla="";
		numero=0;}
	public static String s() { 
		return tabla;
	}
	public static void   idfun(String a) {
		nomid.remove(nomid.indexOf(a));
		nomfun.addFirst(a);
		
	}
	public static String lexico() { 
		LinkedList<String> funciones=new LinkedList<String> ();

		tabla+=tablas("PRINCIPAL","");
		//mirar tokens
		for(int i=0;i<tokens.length;i++) {
			if(izq(i).equals("function")) {//demomento sin etiqueta funcion
				//metemos en lista lexema();

				aux+=tablas("de la FUNCION ", nomid.get(id));
				String retorno="";
				i++;
				if(izq(i).equals("int")||izq(i).equals("bool")||izq(i).equals("string")) {//condicion token es int string o bool
					retorno+="     +TipoRetorno: "+tipo(izq(i))+"\n";//aqui meteriamos el tipo
				}
				if(retorno!="") {
					i++;
				} 
				lexema("function",izq(i),des(izq(i)));//nombre tipo funcion
				i++;
				i=parentesis(i);
				tabla+=retorno; 
				i=dentro(i); 
				funciones.addLast(aux);
				aux="";

				//cuando encontramos el } de la funcion actualizamos i
			}//dos funciones
			else{
				if(izq(i).equals("var")) {
				//como hemos mirado var hacemos i++
				i++; 
				lexema(tipo(izq(i)),"nombre",des(izq(i)));  
			}
		}
			}

		for(String a:funciones) {
			tabla+=a;
		}


		//rellenar resto de tablas con la lista
		return tabla;
	}


	public static  String izq (int i) {
		return tokens[i].substring(1, tokens[i].indexOf( ','));
	} 
	
	public static int parentesis(int a) {
		int contador=0;
		int retorno=0;
		String rellenar="";
		boolean start=true;
		for(int i=a;i<tokens.length&&start;i++) { 


			//LISTA : HACER LEXEMA DE LAS VARIABLES
			if(izq(i).equals("PARCERR")) {//encontramos token parentesis cerrado
				start=false;retorno=i;
			} 
			else {
				if(izq(i).equals("int")||izq(i).equals("string")||izq(i).equals("bool")) {//encontramos int bool string
					contador++;
					lexema1("nombre",tipo(izq(i)),des(izq(i)));
					rellenar+="          +TipoParam"+contador+": "+tipo(izq(i))+" \n";//coger del token


				}
			}
			retorno=i;
		}
		tabla+="     +numParams : "+contador+"\n";
		tabla+=rellenar;
		return retorno;
	} 
	public static String tipo(String a) {
		String ret="";
		switch(a) {
		case "int":ret="'ent'";break;
		case "bool":ret="'logico'";break;
		case "string":ret="'cadena'";break;

		}
		return ret;
	}
	public static int des(String a) {
		int ret=0;
		switch(a) {
		case "int":ret=2;break;
		case "bool":
		case "string":ret=1;break;


		}
		return ret;

	}

	public static void lexema(String tipo,String nombre,int des) {
		if(nomid.size()>id) {
			tabla+="*LEXEMA : "+nomid.get(id)+"\nATRIBUTOS: \n";
			tabla+="     +tipo :"+tipo+"\n";
			if(!tipo.equals("function")) {tabla+="     +desp :"+des+"\n";

			}
			id++;
		}
		else {
			System.out.println("no recorremos bien la lista");
			tabla+="LEXEMA :"+"no existe"+"\nATRIBUTOS \n";
			tabla+="         +tipo :"+tipo+"\n";
			if(!tipo.equals("function")) {
				tabla+="desp :"+des+"\n";
			}
			id++;
		}
	}
	public static void lexema1(String nombre,String tipo,int des) { 
		aux+="*LEXEMA : "+nomid.get(0)+"\n";
		aux+="       +tipo :"+tipo+"\n";
		aux+="       +DesParam :" +des+"\n";
		id++;
		
	}
	public static int dentro(int j) {
		boolean start=true;
		int retorno=0;
		for(int i =j;i<tokens.length&&start;i++) { 


			//LISTA : HACER LEXEMA DE LAS VARIABLES
			if(izq(i).equals("LLAVECERR")) {//encontramos token parentesis cerrado
				start=false;retorno=i;
			} 
			else {
				if(izq(i).equals("int")||izq(i).equals("string")||izq(i).equals("bool")) {//encontramos int bool string
					lexema1("nombre",tipo(izq(i)),des(izq(i)));

				}
			}
		}
		return retorno;
	}

	public static String tablas(String a,String b) {

		numero++;
		return "\nTABLA "+a +b+" # "+numero+":\n";
	}
 public static String met(String a) {
	 String encontrado="";
	 if(metidos.contains(a)) {  
		encontrado=nomid.get(metidos.indexOf(a));
	}
	else { 
		metidos.addLast(a); 
	}
	 return encontrado;}
	
	public static String meter(String a) {//mejorar

		String encontrado=""; 
		if(!funcion) {
			if(!bucle) {
				if(metidos.contains(a)) {  
					encontrado=nomid.get(metidos.indexOf(a));
				}
				else { 
					metidos.addLast(a);
					encontrado=nomid.get(metidos.indexOf(a));
				}}
			else {
				if(metidos.contains(a)) { //bucle 
					encontrado=nomid.get(metidos.indexOf(a));
				}
				else { if(metidosbucle.contains(a)) {  //arreglar no soporta mismos nombres en funcion
					encontrado=nomid.get(metidosbucle.indexOf(a)+metidos.size()+metidosfun.size());
				}
				metidosbucle.addLast(a);
				}	

			}
		}else {
			if(metidos.contains(a)) {  //arreglar no soporta mismos nombres en funcion
				encontrado=nomid.get(metidos.indexOf(a));
			}
			else { 
				if(metidosfun.indexOf(a)!=-1) {  //arreglar no soporta mismos nombres en funcion
					if(metidos.indexOf(a)!=-1) {encontrado=nomid.get(metidos.indexOf(a));}
					else {encontrado="no";}
				}
				metidosfun.addLast(a);
			}
		}



		return encontrado;
	}
	public static void set(boolean j) {
		funcion=j;
	}
	public static void setbucle(boolean j) {
		bucle=j;
	}
	public static boolean getbucle( ) {
		return bucle;
	}
	public static boolean get( ) {
		return funcion;
	}

}
