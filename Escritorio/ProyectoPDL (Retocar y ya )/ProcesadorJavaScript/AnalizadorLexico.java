
	package pdl;
	 
	import java.util.HashMap;
	import java.util.Iterator;
	import java.util.LinkedList;
	import java.util.Map;
	
	
	public class AnalizadorLexico {
		private String [] palres= {"int","bool","string","prompt","print","true","false","var","return","for","function"};//tenemos el for habra que meterlo
		private String fichero="";
		private String tokens="";
		private String linea="";  
		private String errores="";
		private int posicion=0;
		private String palabra="";
		private char caracter;
		private boolean tokennohecho;  
		private static nuevatabla ts ;
		private static LinkedList<String> nom=new LinkedList<String> ();
		private int x=1; 
		public AnalizadorLexico(String fichero) {
			ts=new nuevatabla();
			this.fichero=fichero;
			nom=new LinkedList<String> (); 
		}
		
		public void inicioanalisis() { 
			boolean funcion =false;  
			boolean comentarios=true; 
			String[]lineas=fichero.split("\n");
			for(int i=0;i<lineas.length;i++) {//aqui leemos las lineas
				posicion=0;
				while(posicion<lineas[i].length()) {//recorremos las palabras
					boolean resultado;	
					palabra="";
					leer(lineas[i]);
					tokennohecho=true;
					//--------------------------------------------------------TOKENS
					if(comentarios==true) {
						if(caracter!=' '&&caracter!='	')  {
							//--------------------------------------------------------DIGITOS
	
							if('0'<=caracter&&caracter<='9'&&tokennohecho) {//comprobar1
								concatenar();
								digitos(lineas[i]);	
								
								
								double a =Math.pow(2, 15);
	                            double cc =Double.parseDouble(palabra);
	                            
								if(cc<a) {//cambiar por funcion booleana
									linea+=String.valueOf(i+1)+"\n";tokens+="<ENT,"+palabra+">\n";
								}
								else  {
									errores+="Error la representación del número excede los 2 bytes en la línea "+i+"\n";
									}
								tokennohecho=false;
							}
							//--------------------------------------------------------letras
							if(('a'<=caracter&&caracter<='z')||('A'<=caracter&&caracter<='Z')&&tokennohecho) {
								concatenar();
								letras(lineas[i]); 
								//cambiar por funcion booleana
								int p=contiene(palres,palabra);
	
								if(p>=0) { 
									linea+=String.valueOf(i+1)+"\n";tokens+="<"+palres[p]+",->\n";
	
								}
								else  { 
									p=buscar (); 
									if(p!=-1) {
										
										linea+=String.valueOf(i+1)+"\n";tokens+="<ID,"+p+">\n"; 
										
									}else {    
										linea+=String.valueOf(i+1)+"\n";tokens+="<ID,"+x+">\n";//0 POR PONER ALGO SERIA LA SIGUIENTE ACIA DE LA TABLA
										id met=new id("<ID,"+x+">",palabra); 
										ts.meter_id(met);
										nom.addLast( palabra); 
										x++;}
								}
								tokennohecho=false;
	
	
							}
	
							//--------------------------------------------------------CADENA
							if(caracter=='\''&&tokennohecho) {
								resultado=cadenas(lineas[i]);
	
								if(resultado==true) {linea+=String.valueOf(i+1)+"\n";tokens+="<CADENA,"+palabra+">\n";
								}
								else {
									errores+="Error cadena incorrecta en la linea " +i+"\n";
									}
								tokennohecho=false;
	
							}
	
	
	
	
							//--------------------------------------------------------
							if(caracter=='+'&&tokennohecho) {linea+=String.valueOf(i+1)+"\n";tokens+="<MAS,->\n";
							tokennohecho=false;}
							if(caracter=='('&&tokennohecho) {linea+=String.valueOf(i+1)+"\n";tokens+="<PARAB,->\n";
							tokennohecho=false;}
							if(caracter==')'&&tokennohecho) {linea+=String.valueOf(i+1)+"\n";tokens+="<PARCERR,->\n";
							tokennohecho=false;}
							if(caracter=='{'&&tokennohecho) {linea+=String.valueOf(i+1)+"\n";tokens+="<LLAVEAB,->\n";
							tokennohecho=false;}
							if(caracter=='}'&&tokennohecho) {linea+=String.valueOf(i+1)+"\n";tokens+="<LLAVECERR,->\n";
							if(funcion==true) { 
							funcion=false;}
							tokennohecho=false;}
							if(caracter==','&&tokennohecho) {linea+=String.valueOf(i+1)+"\n";tokens+="<COMA,->\n";
							tokennohecho=false;}
							if(caracter=='<'&&tokennohecho) {linea+=String.valueOf(i+1)+"\n";tokens+="<MENOR,->\n";
							tokennohecho=false;}
							if(caracter==';'&&tokennohecho) {linea+=String.valueOf(i+1)+"\n";tokens+="<PUNTOCOMA,->\n";
							tokennohecho=false;}
							if(caracter=='&'&&tokennohecho) {
								leer(lineas[i]);
								if(caracter=='&') {linea+=String.valueOf(i+1)+"\n";tokens+="<AND,->\n";
								tokennohecho=false;}
								if(caracter=='=') {linea+=String.valueOf(i+1)+"\n";tokens+="<ESPECIAL,->\n";
								tokennohecho=false;}
	
							}
							if(caracter=='='&&tokennohecho) {
	
								leer(lineas[i]);
								if(caracter=='=') {linea+=String.valueOf(i+1)+"\n";tokens+="<DOSIGUAL,->\n";
								tokennohecho=false;}
								else {linea+=String.valueOf(i+1)+"\n";tokens+="<IGUAL,->\n";
								posicion--;
								tokennohecho=false;}
							}
	
	
						}else {tokennohecho=false;}
	
	
						//--------------------------------------------------------Si empezamos a leer un comentario
						if(caracter=='/'&&comentarios==true) {
							leer(lineas[i]);
							if(caracter=='*') {comentarios=false;tokennohecho=false;}
						}
	
						if(tokennohecho) {
							errores+="Error caracter inválido en la linea "+i+"\n";}}
					//--------------------------------------------------------SI terminamos de leer un comentario
					else {
						if(caracter=='*'&&comentarios==false) {
							leer(lineas[i]);
							if(caracter=='/') {leer(lineas[i]);
							comentarios=true;}
						}}
	
				}
				}
			linea+=String.valueOf(lineas.length+1)+"\n";tokens+="<eof,->\n";
	
		}
	
	public void sacar() {
		Iterator<id> a=ts.getid().iterator();
		while(a.hasNext()) {
			id estoy=a.next();
			System.out.println("Nombre :  "+estoy.nombre+"   token  : "+estoy.token+"    tipo  : "+estoy.getTipo());
		}
	}
	
	public nuevatabla recorrer() {
		return ts;
			}
	
		//------------------------------------------------------------------------------------------------------------
		//------------------------------------------------------------------------------------------------------------
		//------------------------------------------------------------------------------------------------------------
		//------------------------------------------------------------------------------------------------------------
		public void leer(String linea) {
			if(linea.length()>posicion) {
				caracter=linea.charAt(posicion);
			}else {
				caracter=' ';
			}
			posicion++;
		} 
		 
		public void  digitos(String linea) {
			leer(linea); 
			if(posicion<=linea.length()) {
				if('0'<=caracter&&caracter<='9') {//comprobar
					concatenar();
					digitos(linea);
	
				}else {posicion--;}
	
			}
	
		}
		//------------------------------------------------------------------------------------------------------------
		public void letras(String lineas) {
			leer(lineas);
			if(posicion<=lineas.length()) {//si encontramos un espacio fuera 
				if(posicion<=lineas.length()) {
					if(('a'<=caracter&&caracter<='z')||('A'<=caracter&&caracter<='Z')||caracter=='_'||'0'<=caracter&&caracter<='9') {//comprobar
						concatenar();
						letras(lineas);
					}else {posicion--;}}
			}
	
	
		}
	
		
		//------------------------------------------------------------------------------------------------------------
		public boolean cadenas(String lineas) {
			boolean resultado=false;
			leer(lineas);
			if(lineas.length()>posicion) {
				if(caracter!='\'') {
					concatenar();
					cadenas(lineas);
	
				}
				if(caracter=='\''){
					resultado=true;}
			}
			return resultado;
	
		}
		public String getTokens() {
			return tokens;
	
		}
		public String getlineas() {
			return linea; 
		}
		public String getErrores() {
			return errores;
		} 
		//------------------------------------------------------------------------------------------------------------
		public int contiene(String[]a,String b) {
			int resultado=-1;
			for(int c=0;c<a.length;c++) {
				if(a[c].equals(b)) {resultado=c;}
			}
			return resultado;
		}
	
		public int buscar () {
			Iterator<String> it = nom.iterator(); 
			int contador=-1;
			int aux=0;
			while (it.hasNext()) {
				String hol=it.next(); 
				aux++;
				if(hol.equals(palabra)) {contador=aux;}
			}
			return contador;
		} 
		
		
		//------------------------------------------------------------------------------------------------------------
		public void concatenar() {
			palabra+=caracter;
		} 
		
			public LinkedList<String> n(){
				return nom;
			}
		//------------------------------------------------------------------------------------------------------------
		//------------------------------------------------------------------------------------------------------------
		//------------------------------------------------------------------------------------------------------------
		//------------------------------------------------------------------------------------------------------------
	
	
	}
