

package pdl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
public class escribeFichero  {
		static String rutaarchivo="";
		static String texto="";
		static File txt;
		private static String ruta="";
	public static void leer() {
		Scanner lectura = new Scanner(System.in);
		System.out.println("Porfavor introduzca la ruta del archivo de texto.");
		rutaarchivo=lectura.nextLine();
		int cont=-1;
		for(int a=rutaarchivo.length()-1;a>=0&&cont==-1;a--) {
			if(rutaarchivo.charAt(a)=='/') {cont=a;
			ruta=rutaarchivo.substring(0,a);}
			
		}
	}public static String sacartexto() throws IOException  {
		 txt=new File(rutaarchivo);
	    /*Si existe el fichero*/
			boolean ruta=false;
			while(ruta==false) {
	    if(txt.exists()){
	        BufferedReader Flee= new BufferedReader(new FileReader(txt));
	        Flee.lines();
	        String Slinea;
	        while((Slinea=Flee.readLine())!=null) {
	        	texto+=Slinea+"\n";             
	        }
	        System.out.println("*********Fichero leido correctamente**********");
	        Flee.close();
	        ruta=true;
	      }else{
	        System.out.println("*********El fichero no se encuentra en esa ubicacion , porfavor introduce la ruta correctamente *********");
	      }
	    
			}
	
		return texto;}
	public static String directorio(String j) {//aqui creamos la carpeta pdl si no existe
		String devolver="";
		boolean noencontrado=true;
		for(int a=j.length()-1;a>=0&&noencontrado==true;a-- ) {
			if(j.charAt(a)=='\\') {
				ruta=j.substring(0, a+1)+"PDL";
				noencontrado=false;
			}
		}
		return devolver;
	}
	public static void crearcarpeta () {
		File directorio = new File(ruta+"/"+"PDL");
		ruta+="/"+"PDL/";

		if(!directorio.exists()) {
		directorio.mkdirs();}
		}
	
	
	
	public static void crear(String a,String meter) throws IOException {
		String rut;
        rut =ruta+a;
        File archivo = new File(rut);
        BufferedWriter bw;
        if(archivo.exists()) {
bw = new BufferedWriter(new FileWriter(archivo));
            
            bw.write(meter);
        } else {bw = new BufferedWriter(new FileWriter(archivo));
        
        bw.write(meter);
        }
        bw.close();
		
		}
	public static void borrar(String borrar) {
		File directorio = new File(ruta+"/"+borrar);
		directorio.mkdirs();
		if(directorio.exists()) {
			directorio.delete();
		}
	}
	
	public static void main(String[]args) throws IOException {
		String texto=
"function int suma(int a,int b) {"
+ "var int i=0; "

+ "return i;}"

+ "suma(3,3);"
; 
			AnalizadorLexico a=new AnalizadorLexico (texto);
			a.inicioanalisis();
			a.recorrer();
			a.sacar();    
			System.out.println("1ºAnalisis léxico\n"+a.getTokens() ); 
			//creamos un objeto analisis lexico y le metemos como atributo sacartexto()
			Analizadorsintactico b=new Analizadorsintactico (a.getTokens(),a.getlineas(),a.recorrer());
			b.Sp();  
			
			crear("parse",b.parse());
			System.out.println("\n2ºAnalisis Síntactico\n"); 
			System.out.println(b.getParse()+" "); 
			System.out.println(b.getErrores()+" ");  
System.out.println("\n\n3º tabla de simbolos \n");
System.out.println(b.getErroresS());
		
			
			//creamos y metmos los tokens del analisis crear("tokens",objeto.getstokens());

			}
	}
