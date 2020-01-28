package pdl;
import java.util.Iterator;
import java.util.LinkedList;

public class Analizadorsintactico {  
	static id izq;
	static funcion izq1;
	static id dcha;
	static id dchaaux;
	static funcion dcha1;
	static String errsemantico=""; 
	static String errsintactico="";
	static String parse=""; 
	static String operador="";
	static String asig="";
	private static String tiporeturn=""; 
	static String tipo=""; 
	static String tipofuncion="";
	static boolean exit=false;
	static String []token;
	static int con=0;
	static int confor=0;
	static boolean and=false;
	static String []linea; 
	private static nuevatabla tabla;
	private static boolean bucle=false;
	private static boolean llamada=false;
	private static String tipototal=""; 
	private static boolean iniciado=true; 
	static int contador=0; 
   /* ***********************************************/

	public Analizadorsintactico(String tokens,String lineas, nuevatabla tss) {
		tabla=tss;
		token=tokens.split("\n");
		parse+="Des ";
		linea=lineas.split("\n");
		dchaaux=new id("","");

	} 
	public static void Sp() {//Sp--->W 


		if(!exit) {	
			if(equal("function")||equal("eof")||equal("var")||equal("for")||equal("int")||equal("print")||equal("prompt")||equal("return")||equal("id")) {
				parse+="1"+" ";
				W();}


		}
	}

	public static void W() { 
		if(!exit) {	
			if(equal("function")) {//W--->FW 
				parse+="3"+" ";
				F(); 
				W();
			}
			else {
				if(equal("var")||equal("for")|equal("print")||equal("prompt")||equal("return")||equal("id")) {//W--->SW 
					parse+="2"+" ";
					S();
					W();

				}



				else {   if(token[contador].equals("<eof,->")){
					
					parse+="4"+" "; 
//tabla.actualizar();
					tabla.sacarlista();} else {
				errsintactico+="no reconido";
			}

		}}}}


	public static void S() {// S--->var T id Gp; 
		if(!exit) {	
			if(equal("var") ) {
				parse+="5"+" ";
				validar("var");
				T();   
				izq=tabla.buscarid(token[contador]);
				
				existe();
				tabla.metertipo(token[contador], tipo);
				validar("id");
				Gp();
				validar(";");
			}else {
				if(equal("for") ) {//S--->for(I ; E ;I ){A}  
					
					parse+="6"+" "; 
					validar("for");
					validar("(");
					I();  
					tipototal="";
					validar(";");
					E();
					if(!dcha.getTipo().equals("bool")) {
						errsemantico+="\nError Semántico en la línea:"+linea[contador]+" no es una condición en el for ni una asignación ";
					}
					tipototal="";
					validar(";"); 
					I();
					System.out.println(errsemantico);
					validar(")");
					validar("{");
					tabla.setBucle(true); 
					confor++;
					tabla.añadirfor();
					A();
					tabla.borrarfor();
					confor--; 
					tabla.setBucle(false); 
					validar("}"); 
				}else {//S--->N
					if((equal("print")||equal("prompt")||equal("return")||equal("id")) ) {
						parse+="7"+" ";
						N();
					} 
					else {//S--->lambda
						parse+="8"+" ";
					}
				}
			}
		}}
	public static void F() {//F--->function Fp id(P){A}
		if(!exit) {	
			if(equal("function")) {
				if(bucle) {//con el error sintactico que sale tiene que ser suficiente
					errsemantico+="no se puede crear una funcion dentro de un for"; 
				}
				parse+="9"+" ";
				validar("function");
				Fp(); 
				existe();
				tabla.meterfun(token[contador],tipo);
				izq1=tabla.funcion(token[contador], tipo);
				tabla.setAux(token[contador]);
				validar("id"); 
				validar("(");
				P(); 
				validar(")");
				tabla.setdentro(true);
				validar("{"); 
				iniciado=true;
				A(); 
				validar("}"); 
				if(!izq1.getTipo().equals("void")) { 
				if(!izq1.getTipo().equals(tiporeturn)) {
					errsemantico+= "\nError semántico en la línea: "+linea[contador]  +":la función debería devolver "+izq1.getTipo()+" pero devuelve un tipo desconocido o mal declarado";
					//errsemantico+="return mal echo"+tiporeturn+"   tipo"+izq1.getTipo();
					}
					}
				tabla.setdentro(false);  
				izq1.setCreado(true);
				izq1=new funcion("");
				

			}

		}

	}


	public static void A() {
		if(!exit) {	
			if(equal("var")||equal("for")||equal("print")||equal("prompt")||equal("return")||equal("id")) {//A--->SA 
				parse+="10"+" ";
				S(); 
				A();
			}
			else {//A--->lambda

				parse+="11"+" ";
			}
		}
	}


	public static void Fp(){
		if(!exit) {	
			if((equal("int")||equal("bool")||equal("string")) ) {//FŽ-->T
				parse+="13"+" ";
				T();  

			}
			else {//Fp-->lambda 
				tipo="void";
				parse+="12"+" ";

			}
		}
	}


	public static void T() { 
		if(!exit) {	
			if(equal("bool") ) {//T--->bool
				parse+="15"+" ";
				tipo="bool";
				validar("bool");

			}
			else {
				if(equal("string") ) {//T--->string
					parse+="16"+" ";
					validar("string");

					tipo="string";

				}else {
					if(equal("int") ) {//T--->int
						parse+="14"+" ";
						validar("int");

						tipo="int";

					}
					else {
						if(!exit){
							errsintactico+="\nError Sintáctico en la linea  "+linea[contador]  +":No se ha definido el tipo(int,bool,string) de la variable  ";
							exit=true;
						}
					}
				}
			}
		}}





	public static void P(){
		if(!exit) {	
			if((equal("int")||equal("bool")||equal("string"))) {//P-->TidTp
				parse+="18"+" ";
				T(); 
				tabla.parametro(token[contador],tipo());
				validar("id");
				Tp();

			}
			else {//P-->lambda 
				parse+="17"+" ";

			}
		}}





	public static void Tp() {
		if(!exit) {	
			if(equal(",")) {//Tp--->,TidTp
				parse+="20"+" ";
				validar(","); 
				T();

				tabla.parametro(token[contador],tipo());
				validar("id");
				Tp();

			}
			else {//Tp--->lambda
				parse+="19"+" ";

			}

		}
	}

	public static void I() {
		if(!exit) {	
			if(equal("id")) {//I--->id G
				parse+="21"+" "; 
				izq=tabla.buscarid(token[contador]); 
				if(bucle) {existe();}
				validar("id");
				G();
			}


			else {
				if(!exit){
					errsintactico+="\nError Sintáctico en la linea  "+linea[contador] +":el bucle for está mal declarado    "   ;
					exit=true;
				}
			}

		}
	}




	public static void G() { 
		if(!exit) {	
			if(equal("=") ) {//G-->=E 
				dcha=new id ("","");
				asig="="; 
				parse+="23"+" ";
				validar("="); 
				E();   
				tabla.isiniciadovar(izq, iniciado); 
				iniciado=true;
				if(and) {
					and=false;
					if(!dchaaux.getTipo().equals(dcha.getTipo())) {
						errsemantico+="\nError semántico en la línea: "+linea[contador]+" condicion construida de forma incorrecta";
					}
					
				}
	comparar(); 
			}
			else{
				if(equal("&=") ) {//G--> &=E 
					asig="&=";
					parse+="22"+" ";
					
					validar("&=");
			
					/*else {
					System.out.println("guay");
				}*/
					E(); 
					tabla.isiniciadovar(izq, iniciado); 
					iniciado=false;
					comparar(); 
				}
				else {
					if(equal("(") ) {
						parse+="24"+" ";
					
						llamada=true;
						H();
						System.out.println("con : "+con);
						System.out.println("n par : "+izq1.getParametros().size());
						if(con<izq1.getParametros().size()) {
							errsemantico+="\nError semántico en la línea: "+linea[contador]+" faltan parametros en la llamada a la función ";}
						izq1.reset();
						izq1=new funcion("");
						llamada=false;}
					
					else {
						
					if(!exit){
						errsintactico+="\nError Sintáctico en la linea  "+linea[contador] +": operador incorrecto     " ;  
						exit=true;
					}}
				}
			}
		}}





	public static void Gp() {
		if(!exit) {	
			if((equal("=")||equal("&=")) ) {//Gp-->G
				parse+="26"+" ";
				G();

			}
			else {//Gp-->lambda
				parse+="25"+" ";
			}
		}
	}


	public static void E() { 
		if(!exit) {	
			if((equal("id")||equal("numero")||equal("cadena")||equal("true")||equal("false"))) {
				parse+="27"+" ";
				R();
				Ep();
			}
			else {
				if(equal("(")){
					parse+="28"+" ";
					validar("(");
					E();
					validar(")");
					Ep();

				}
				else {
					if(!exit){
						errsintactico+="\nError Sintáctico en la linea  "+linea[contador] +":no se ha asignado un valor a la variable "+linea[contador] ; 
						exit=true; }
				}

			}
		}

	}


	public static void Ep() { 
		if(!exit) {	
			if(equal("&&") ) {
				dchaaux.setTipo("bool");
				dcha=new id("","");
			operador="&&";
				parse+="29"+" ";
				validar("&&");
				E();
			}
			else {
				if(equal("==")){

					operador="==";
					parse+="31"+" ";
					tipototal="bool";
					validar("==");
					E();
				}
				else {
					if(equal("+")) {
						operador="+";
						parse+="33"+" ";
				
						validar("+");
						E();
					}
					else {
						if(equal("<")){
			
							operador="<";
							parse+="32"+" ";
							validar("<");
							R();
						}

						else 
						{ 
							parse+="30"+" ";
						}
					}
				}
			}
		}
	}

	public static void H() { 
		if(!exit) {	
			if(equal("(")) {
			if(llamada)	{
			izq1=tabla.buscarfun(izq.getNombre());
			con=0;
			dcha1=new funcion("");
			dcha=new id(izq1.getToken(),izq1.getNombre());
			dcha.setTipo(dcha1.getTipo());
			dcha.setIniciado(true);
			} 
				parse+="35"+" ";
				validar("(");
				C();
				validar(")");

			}
			else {
				
				parse+="34"+" ";

			}
		}
	}
	public static void R() { 
		if(!exit) {	 
			if(equal("id")) { 
				parse+="36"+" "; 
				String tipo=tabla.buscarid(token[contador]).getTipo();
				if(tipo.equals("function")) {
					izq1=tabla.buscarfun(tabla.buscarid(token[contador]).getNombre());}
               actualizar(tipo);   
			if(!dcha.getTipo().equals("function")) {iniciado&=dcha.isIniciado();}
				validar("id");
				llamada=true;
				H(); 
				llamada=false; 
			}
			else { 
				if(equal("numero")) {
                  actualizar("int");
					parse+="37"+" ";  
					llamada("int");
					validar("numero");
					iniciado&=true;
					
				}
				else {
					if(equal("cadena")) {
					      actualizar("string");
						parse+="38"+" "; 
						validar("cadena"); 
						llamada("string");
						iniciado&=true;
					}
					else {
						if(equal("true")) {
							 actualizar("bool");
							parse+="39"+" "; 
							validar("true"); 
							llamada("bool");
							iniciado&=true;
						}
						else {
							if(equal("false")) {
							
								actualizar("bool"); 
								parse+="40"+" ";
								validar("false"); 
								llamada("bool");
								iniciado&=true;
							} 

						}
					}
				}
			}
		}
		}
	public static void C() { 
		if(!exit) {	 
			if(equal("id")||equal("numero")||equal("cadena")||equal("true")||equal("false"))  {//modificar
				parse+="42"+" "; 
				con++;
				E();  
				Cp(); 	
			} 
			else {
				parse+="41"+" ";

			}
		}}
	public static void Cp() {
		if(equal(",")) {
			parse+="43"+" ";
			validar(",");
		con++;
			E();
			Cp();

		}
		else {
			parse+="44"+" ";

		}
	}


	public static void N() {
		if(!exit) {	
			if(equal("id")) {
				izq=tabla.buscarid(token[contador]);
				parse+="48"+" ";
				I();
				validar(";"); 
			}
			else {
				if(equal("prompt")) {
					parse+="45"+" ";
					validar("prompt");
					validar("(");
				if(tabla.buscarid(token[contador]).getTipo().equals("bool")) {
					errsemantico+="\nError semántico en la línea: "+linea[contador]+" la variable del prompt no puede ser de tipo lógico";
				}
				if(tabla.buscarid(token[contador]).getTipo().equals("unknown")) {
					errsemantico+="\nError semántico en la línea: "+linea[contador]+" no se ha declarado la variable del prompt";
				}
					validar("id");
					validar(")");
					validar(";");
				



				}
				else {
					if(equal("print")) {
						parse+="46"+" ";
						
						validar("print");
						validar("(");
						E();
						validar(")");
						validar(";");


					}else {
						if(equal("return")) {
							if(izq1.getTipo().equals("void")) {
								errsemantico+="\nError semántico en la línea: "+linea[contador]+" las funciones de tipo void no tiene retorno";
							}
							parse+="47"+" ";
							validar("return");
							K();
							
							
							//if(!iniciado) {//duda de si hay que ponerlo
								//errsemantico+="se intenta devolver un return vacio";
							//}
							validar(";");
							

						} 


					}
				}
			}
		}
	}
	public static void K() {
		if(!exit) {	
			if((equal("id")||equal("int")||equal("cadena")||equal("true")||equal("false"))) { 
				parse+="49"+" ";
				E(); 
				tiporeturn=dcha.getTipo();
				

			}
			else {
				iniciado=false;
				parse+="50"+" ";

			}
		}
	}




	//********************************************************************************************************************************
	//********************************************************************************************************************************
	//********************************************************************************************************************************





	public static void validar(String t) {
		if(exit!=true) {
			boolean error=true;
			if(t.equals("id")||t.equals("numero")||t.equals("cadena")) {
				if(t.equals("id")) {
		
					if((token[contador].substring(1, token[contador].indexOf( ',')).equals("ID"))) {
						//////System.out.println("mi token es:"+token[contador]);
						error=false;
					}
				}
				if(t.equals("numero")) {
					
					if((token[contador].substring(1, token[contador].indexOf( ',')).equals("ENT"))) {
						//////System.out.println("mi token es:"+token[contador]);
						error=false;
					}
				}
				if(t.equals("cadena")) {
					if((token[contador].substring(1, token[contador].indexOf( ',')).equals("CADENA"))) {
						//////System.out.println("mi token es:"+token[contador]);
						error=false;
					}
				}


			}
			else {
				if(equal(t)) { 
					error=false;
				}
			}
			if(error) {//////System.out.println("mi token es:"+token[contador]);
				if(!exit){AnalizadorLexico aa=new AnalizadorLexico (t);

				aa.inicioanalisis();
				String []array =aa.getTokens().split("\n");

				errsintactico+="\nError Sintáctico en la linea  "+linea[contador] +" :   se recibío el token  "+token[contador]+" pero se esperaba "+ array[0];
				exit=true;}
				//caso error
			}
			if(contador<token.length-1) {
				contador++;} 
		}}
	public static boolean equal(String a) {
		//crear objeto analizador lexico e iniciarlo en texto a 
		boolean solucion;
		AnalizadorLexico aa=new AnalizadorLexico (a);
		aa.inicioanalisis();
		String []array =aa.getTokens().split("\n");
		//System.out.println(array[0]);
		solucion=array[0].equals(token[contador]);
		if(a.equals("eof")) {

			solucion=array[1].equals(token[contador]);
		}
		if(a.equals("numero")) {

			solucion=token[contador].substring(1, token[contador].indexOf( ',')).equals("ENT");
		}
		if(a.equals("id")) {

			solucion=token[contador].substring(1, token[contador].indexOf( ',')).equals("ID");
		}
		if(a.equals("cadena")) {

			solucion=token[contador].substring(1, token[contador].indexOf( ',')).equals("CADENA");
		}
		//coger solo el primer token y compararlo con el que tenemos.
		return solucion; 
	}
	
	public static String tipo() {  
		String sol="";
		boolean encontrado=false;
		int posicion=-1;
		for(int i=0;i<token.length&&encontrado==false;i++) {
			if(token[contador].equals(token[i])) {
				posicion=i-1;
				encontrado=true;
			}
		}
		if(posicion==-1) {encontrado=true;
		sol="none";}else {
			if(token[posicion].equals("<int,->")||token[posicion].equals("<string,->") ||token[posicion].equals("<bool,->")) {
				encontrado=true;
				sol=token[posicion].substring(1, token[posicion].indexOf( ','));
			}else {
				sol="none";
			}} 
		return sol;
	}
	
	
	public static String parse() {
		return parse+" ";
	}
	public static String getParse() {
		return parse; 
	}
	public static String getErrores() {
		return errsintactico;
	}
	public static String getErroresS() {
		return errsemantico;
	}
	public static void actualizar(String a) {
		boolean error=false;  
		if(dcha==null) {dcha=new id("","");}
		if(dcha.getTipo().equals("unknown")) {dcha=new id("","");
		dcha.setTipo(a);}
		if(a.equals("function")) {
			if(izq1.getTipo().equals(dcha.getTipo())) {
				a=dcha.getTipo();
				izq1=new funcion("");
			}
}
		//if(dcha.getTipo().equals("unknown")) { 
	//	} 
		//else { 
		System.out.println(a+"<------string dcha--->"+dcha.getTipo());
		String sa="";
			switch(operador) {
			case "<":
				if(!(dcha.getTipo().equals("int")&&a.equals("int"))) {
					error=true;
					if(error) {sa="uso incorrecto del operador < solo se pueden comparar numeros";}
				}
				else {
					dcha.setTipo("bool"); 
				}
				break;
			case"+":
				if(!(dcha.getTipo().equals("int")&&a.equals("int")||(dcha.getTipo().equals("string")&&a.equals("string")))) {
					error=true;
					if(error) {sa="uso incorrecto del operador +";}
				}else {
					dcha.setTipo(a);
				}
				break;
			case "==":
				if(!dcha.getTipo().equals(a)) {
					error=true;
				}
				else {
					dcha.setTipo("bool");
				}
				break;
			case "&&":
				if(!(dcha.getTipo().equals("bool")&&a.equals("bool"))) {
					if((dchaaux.getTipo().equals("unknown"))) {
						error=true;}
					else {
						and=true;
					}
					if(error) {sa="uso incorrecto del operador && solo valores logicos";
					dcha.setTipo("bool");}
				}else {
					dcha.setTipo("bool");
				}
				break;
			default:   ;
			}

			operador="";
			if(error) {
				errsemantico+="\nError Semántico en la línea "+linea[contador] +":"+sa;
			}    }
	
	
	public static  void existe() {
		if(llamada) {
		if(!bucle) {
		if(!tabla.buscarid(token[contador]).getTipo().equals("unknown")&&!tabla.buscarid(token[contador]).getTipo().equals("for")) {
			errsemantico+="\nError semántico en la línea: "+linea[contador]+" la variable ha sido creada anteriormente";
		}else {
			if(iniciado&&izq!=null) {
tabla.add(token[contador], izq.getNombre());
			}
		}
		}else {
			if(tabla.buscarid(token[contador]).getTipo().equals("unknown")&&tabla.buscarid(token[contador]).getTipo().equals("for")) {
				errsemantico+="\nError semántico en la línea: "+linea[contador]+"variable no ha sido declarada";
			}	
		}
	}else {

		if(!bucle) {
		if(!tabla.buscarid(token[contador]).getTipo().equals("unknown")&&!tabla.buscarid(token[contador]).getTipo().equals("for")) {
			errsemantico+="\nError semántico en la línea: "+linea[contador]+" la variable ha sido creada anteriormente";
		}else {
			if(iniciado&&izq!=null) {
tabla.add(token[contador], izq.getNombre());
			}
		}
		}else {
			if(tabla.buscarid(token[contador]).getTipo().equals("unknown")&&tabla.buscarid(token[contador]).getTipo().equals("for")) {
				errsemantico+="\nError semántico en la línea: "+linea[contador]+"variable no ha sido declarada";
			}	
		}
	} 
	}

	public static void comparar() {
		boolean err=false; 
		String sa="";
		if(izq!=null) { 
        err=!(izq.getTipo()).equals(dcha.getTipo());
        if(err&&sa.equals("")) {
        	if(izq.getTipo().equals("unknown")||dcha.getTipo().equals("unknown")) {
        	sa="variable no ha sido declarada";}
        	else {
        		sa="se asignó "+dcha.getTipo()+" cuando debería haberse asignado "+izq.getTipo();
        	}
        }
        System.out.println(izq.getTipo()+" <---izq  ddkdkdkdk--->dcha"+dcha.getTipo());
		if(asig.equals("&=")&&!err) {
			err=!izq.getTipo().equals("bool");
			System.out.println(err);
		} 
	}
		else{
			err=true;
		}
		if(err) { 
			errsemantico+="\nError Semántico en linea "+linea[contador] +":"+sa;
		}
		dcha=new id("","");
		izq=new id("","");}
	public static void llamada(String comp) {  
		if(llamada) { 
		if(izq1.isCreado()&&!izq1.comprobararg(comp)) { 
			errsemantico+="\nError semántico en la línea: "+linea[contador]+" la llamada a la función es incorrecta";
		} 
	}} 
}