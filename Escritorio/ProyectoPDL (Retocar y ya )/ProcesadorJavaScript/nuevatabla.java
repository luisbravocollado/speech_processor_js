

package pdl;

import java.util.Iterator;
import java.util.LinkedList;

public class nuevatabla {
	private  LinkedList<id> auxfor;//guardamos las variables de los for
	private  LinkedList<LinkedList<id>> idfor; //meterse cuando estamos en los bucles
	private  LinkedList<id> idaux;// guardamos ids de las funciones cuando se pueden volver a usar
	private  LinkedList<id> id;// tabla principal
	private  LinkedList<funcion> fun;//guardamos tablas de las funciones
	private String tabla;// sacar la tabla final
	private  boolean dentro;// si estamos dentro de una funcion es true sino false
	private  boolean bucle;// si estamos dentro de un bucle true y sino false
	private String aux="";//cuando estas en una funcion es el nombre de la funcion
	private int y=0;
	public nuevatabla() {
		auxfor=new LinkedList<id>();
		idfor=new LinkedList<LinkedList<id>>();
		 idaux=new LinkedList<id>();
		tabla="";
		dentro=false;
		bucle=false;
		id=new LinkedList<id> ();
		fun=new LinkedList<funcion> ();
		
	}
	public void setBucle(boolean bucle) {//se usa en el sintactico si nos encontramos un for
		this.bucle = bucle;
		if(bucle) {y++;}
		else {y--;}
	}
	
	public void setAux(String aux) {//si estas dentro de una funcion,el nombre de la funcion para saber donde se guardan sus variables
		this.aux = aux;
	}
	public void setdentro(boolean a) {//para saber si estamos dentro de la funcion o no
		dentro=a;
	}
	public void meter_id(id a) {//para guardar los tokens y los nombres en el a.lexico
		id.addLast(a);
	}
	public LinkedList<id> getid( ) {//borrar despues
		return id;
	}
/********************************************************/
	public funcion buscarfun(String nombre) {//buscar una funcion por el nombre UTILIZADO
		funcion sol=new funcion("");
		Iterator <funcion> d=fun.iterator();
		boolean noencontrado=true; 
		while(d.hasNext()&&noencontrado) { 
			  sol=d.next();
			if(sol.getNombre().equals(nombre)) {
				noencontrado=false; 
			}

		}
		return sol;
	}
	/********************************************************/
//AQUI VA ACTUALIZAR ESTA COPIADO EN LA CLASE f
	public void add(String t,String n) {
		id meter=buscartotal(t);
		meter.setTipo(n);
		
	}
	/********************************************************/
	
	
	public id principal(String token) {//le das el token y te devuelve el id ,si tiene desconocido lo usamos
		id sol=new id("",""); 
		Iterator <id> d=id.iterator(); 
		boolean noencontrado=true;
		int posicion=0;
		id i;
		while(d.hasNext()&&noencontrado) {
			i=d.next(); 
			if(i.getToken().equals(token)) {
				System.out.println(i.getTipo()+"<----meter TIPO ---->"+i.getnombre());sol=i;noencontrado=false; 
			 }
		}  
		return sol;
		
	}
	/********************************************************/
	public id idaux(String token ,String tipo) {//lo mismo que el de arriba pero para la lista de auxiliares
		Iterator <id> d=idaux.iterator();
		id meter=new id("","");
		boolean noencontrado=true;
		int posicion=0;
		id i=new id("","");
		while(d.hasNext()&&noencontrado) {
			i=d.next();
			if(i.token.equals(token)) {noencontrado=true;
			meter=i;
			meter.setTipo(tipo);
			 }
		}
		return meter;
	}
	public id auxfor(String token) {//lo mismo que el de arriba pero para la lista de auxiliares
		Iterator <id> d=auxfor.iterator();
		id meter=new id("","");
		boolean noencontrado=true;
		int posicion=0;
		id i=new id("","");
		while(d.hasNext()&&noencontrado) {
			i=d.next();
			if(i.getToken().equals(token)) {noencontrado=false; }
		}

		System.out.println(i.getTipo()+"<------nombre  tipo------->"+i.getnombre());
		return i;
	}
	/********************************************************/
	//BORRAMOS ENAUX PERO EN CLASE f
	
	public boolean yadeclarado(String token) {//PARA EL FOR
		boolean esta=false; 
		Iterator <funcion> d=fun.iterator(); 
		funcion i;
		while(d.hasNext()) {
			i=d.next();
			if(i.getToken().equals(aux)) {
				Iterator <id> dd=i.getIds().iterator();
				while(dd.hasNext()) {
					if(dd.next().equals(token)) {
						esta=true; 
					}
					
				}
				
			}
		}
		return esta;
	} 
	/********************************************************/
	public void metertipo(String token,String tipo) { //TODO
		if(bucle) {
			
			id meter;
			meter=principal(token); 
			System.out.println(meter.getTipo()+"<----meter TIPO ---->"+tipo);
			//nunca se mete?
			if(meter.getTipo().equals("unknown")) {//la hn cogido 
				meter=idaux(token,tipo); 
				if(dentro) {//si estamos dentro de una funcion comprobamos que no haya sido declarada
					if(yadeclarado(token)) {//no fue declarado en esa funcion
						meter.setTipo("unknown");
					//	auxfor.addLast(meter); 
						idaux.addLast(meter);
						fun.getLast().setIds(meter);
					}
					
					else {
						idaux.addLast(meter);
						meter=buscartotal(token);
						meter.setTipo(tipo);
						fun.getLast().setIds(meter);
					}
				}
				else { 
				
					idaux.addLast(meter);
				//	auxfor.addLast(meter); 
					meter.setTipo("for"); 
				}
			}
			//?¿
			else {
				if(dentro) {
					funcion a=buscarfun(token);
					meter.setTipo("unknown");
				a.setIds(meter);
				id.remove(meter);
				meter.setTipo(tipo);
				meterenfor(meter);} 
				
				else {
					
					
				meter.setTipo("unknown");
				id.remove(meter); 
				idaux.addLast(meter);
				id r=new id(meter.getToken(),meter.getNombre());
				id r1=new id(meter.getToken(),meter.getNombre());
				r.setTipo(tipo);
				r1.setDesplazamiento(y);
				auxfor.addLast(r);
				meterenfor(r);
				r1.setTipo("for");
				id.addLast(r1);   
				}
			}
			//si no ha sido declarada en la tabla principal
			//miramos si esta en aux
			//si esta en aux miramos que no estemos en la misma funcion
			//si el for esta en una funcion añadimos a esa funcion
			//id pero con tipo for
			//añades el id que tiene el tipo for a la funcion si estas en ella y si no a la tabla principal
			
		}
		else {
			if(!dentro) {//buscamos en laprincipal
				Iterator <id> d=id.iterator();
				boolean noencontrado=true;
				int posicion=0;
				id i=new id("","");
				while(d.hasNext()&&noencontrado) {
					i=d.next();
					if(i.token.equals(token)) {noencontrado=false;i.setTipo(tipo);
					}
				}
				
				
				if(noencontrado) {
					id hi=(auxfor(token));
					System.out.println(hi.getTipo()+"<------nombre jjjjjjjj tipo------->"+hi.getnombre());
					hi.setTipo(tipo);
					id.addLast(hi); 
				}

			}
			else {

				if(idfun(token,tipo)) { 
					fun.getLast().setIds(idaux(token,tipo));
				}
			}



		}
	}
	/********************************************************/
	public boolean idfun(String token,String tipo) {	//FUNCION
		Iterator <id> d=id.iterator(); 
	boolean noencontrado=true;
	int posicion=0;
	id i=new id("ss","");
	while(d.hasNext()&&noencontrado) {
		i=d.next();
		if(i.token.equals(token)) {noencontrado=false;
		auxfor.addLast(i);
		i.setTipo(tipo);
		fun.getLast().setIds(i); 
		id.remove(id.indexOf(i));}
	} 
	if(noencontrado) {
		Iterator <funcion> dd=fun.iterator(); 
		funcion auxx;
		id a;
		while(dd.hasNext()&&noencontrado) {
			
			auxx=dd.next();
	 
		if(auxx.getToken().equals(aux)) { 
			Iterator <id> ko=auxx.getIds().iterator();
			while(ko.hasNext()&&noencontrado) { 
				i=ko.next();
			if(i.token.equals(token)) {noencontrado=false;
			a=i;
			
			}}
			ko=auxx.getParametros().iterator();
			while(ko.hasNext()&&noencontrado) { 
				i=ko.next(); 
			if(i.token.equals(token)) {noencontrado=false;
			a=new id(token,i.getnombre());
			a.setTipo(tipo);
			fun.getLast().setIds(a); 
			}}
			}
		}
	
	
	}
	return noencontrado;
	}
	/********************************************************/
	public void isiniciadovar(id a,boolean g) {//para comprar si esta iniciado una variable
		a.setIniciado(g);
	}
	/********************************************************/
	public void parametro(String token,String tipo) {	//FUNCION
		Iterator <id> d=id.iterator();
		LinkedList<id> ifh=id; 
	boolean noencontrado=true;
	int posicion=0;
	id i=new id("","");
	while(d.hasNext()&&noencontrado) {
		i=d.next();
		if(i.token.equals(token)) {noencontrado=false;i.setTipo( tipo);
		fun.getLast().meterParametro(i);
		id.remove(i);}
	} 
	if(noencontrado) {
		fun.getLast().meterParametro(buscarfun(token,tipo)); 
	}
	}
	/********************************************************/
	public funcion funcion(String token,String tipo) {//FUNCION
		funcion meter=new funcion(token );
		funcion i;
		Iterator <funcion> d=fun.iterator();
		boolean noencontrado=true;
		while(d.hasNext()&&noencontrado) {
			i=d.next();  
			if(i.getToken().equals(token)) {noencontrado=false;
			meter=i; 
			} 
		} 
		return meter;
	}
	/********************************************************/
	public id buscarfun(String token,String tipo) {//FUNCIONES
		id meter=new id(token,"maria");
		id i;
		Iterator <funcion> d=fun.iterator();
		boolean noencontrado=true;
		while(d.hasNext()&&noencontrado) {
			Iterator <id> ko=d.next().getParametros().iterator(); 
			while(ko.hasNext()&&noencontrado) {
				i=ko.next();
			if(i.token.equals(token)) {noencontrado=false;
			meter=i;
			
			}} 
		}
		d=fun.iterator();
		while(d.hasNext()&&noencontrado) {
			Iterator <id> ko=d.next().getIds().iterator();
			while(ko.hasNext()&&noencontrado) {
				System.out.println("sacaos");
				i=ko.next();
			if(i.token.equals(token)) {noencontrado=false;
			meter=i;
			
			}} 
		}
		
		return meter;
	}
	/********************************************************/
	public void meterfun(String token,String tipo) {//FUNCIONES
		Iterator <id> d=id.iterator();
		funcion  aux=new funcion(token);
		boolean noencontrado=true;
		int posicion=0;
		id i=new id("","");
		while(d.hasNext()&&noencontrado) {
			i=d.next();
			if(i.token.equals(token)) {noencontrado=true;i.setTipo("function");aux.setnombre(i.getnombre());}
		} 
		aux.setTipo(tipo);
		fun.addLast(aux);
	} 
	/********************************************************/
	public void listaprincipal() {//TABLA
		Iterator <id> d=id.iterator();
		id a;
		while(d.hasNext()) {
			a=d.next();
			if(a.tipo.equals("for")) {
				
				Iterator <id> dd=auxfor.iterator();
				id aa;
				while(dd.hasNext()) {
					
					aa=dd.next();
					if(a.getToken().equals(aa.getToken())) {
						lexema(aa.nombre,aa.tipo,aa.desplazamiento,true);
					}
					
				
				}
			}
			else {
			lexema(a.nombre,a.tipo,a.desplazamiento,true);}
			
		}
	}
	
	public void listafunciones() {//TABLA
		Iterator <funcion> d=fun.iterator();
		funcion a;
		id aux;
		while(d.hasNext()) {
			a=d.next();
			tabla+="Tabla de la función   "+a.getNombre()+"    #  "+ (fun.indexOf(a)+1)+"\n";
			Iterator<id> param=a.getParametros().iterator();
			while(param.hasNext()) {
				aux=param.next();
				lexema(aux.nombre,aux.tipo,aux.desplazamiento,false); 
			}
			Iterator<id> id=a.getIds().iterator();
			while(id.hasNext()) {
				aux=id.next();
				lexema(aux.nombre,aux.tipo,aux.desplazamiento,false);   
			}
			
		}
	}
	/********************************************************/
	public void sacarlista() {//TABLA
		System.out.println("la nueva lista");
		listaprincipal();
		listafunciones();
		System.out.println(tabla);
		
	}
	/********************************************************/
	public  void lexema(String nombre,String tipo,int desp,boolean principal) { //TABLA
	desp=des(tipo);
			tabla+="*LEXEMA : "+nombre+"\n";
			if(principal) {tabla+="ATRIBUTOS: \n";}
			tabla+="     +tipo :"+ tipo+"\n";
			if(!tipo.equals("function")) {tabla+="     +desp :"+desp+"\n"; 
			} else {
				Iterator <funcion> d=fun.iterator();
				funcion a;
				while(d.hasNext()) {
					a=d.next();
				if(a.getNombre().equals(nombre)) {
					lexema1(a.getParametros(),a.getTipo());
				}
					
				}
			}
	} 
	/********************************************************/
public void lexema1( LinkedList<id> parametros,String tipo) {//TABLA
	tabla+="     +numeroparametros :"+parametros.size()+"\n";
	Iterator <id> d=parametros.iterator();
	id a;
	while(d.hasNext()) {
		a=d.next();
		tabla+="     +tipoparametro :"+ a.tipo+"\n"; 
	}
	tabla+="     +tiporetorno :"+tipo+"\n"; 
}
/********************************************************/
public static int des(String a) {//TABLA
	int ret=0;
	switch(a) {
	case "int":ret=2;break;
	case "bool":
	case "string":ret=1;break;
    

	}
	return ret;

}
/********************************************************/


public id buscarid(String token) { //
	boolean noencontrado=true;
	id a=new id (token,"");
	a.setTipo("unknown");
	if(bucle) {
		Iterator < id> c=id.iterator();
		while(c.hasNext()) { 
			a=c.next(); 
			System.out.println("buscamos :"+token+"cond2 : "+( a.getDesplazamiento()+""+y));
			if(a.token.equals(token)&&a.getDesplazamiento()>=y) {
				Iterator <id> dd=auxfor.iterator();
				id aa;
				while(dd.hasNext()) {
					
					aa=dd.next();
					if(a.getToken().equals(aa.getToken())) {
						a=aa;
					}
					
				
				}
				noencontrado=false;
				
			} 
		} 
			
			
	} 
	if(!dentro) {
	Iterator <id> d=id.iterator(); 
	while(d.hasNext()&&noencontrado) {
		a=d.next();
	if(a.token.equals(token)) {
		noencontrado=false; 
	} }  
	if(idaux(token,"unknown").getToken().equals(token)) {
		noencontrado=false;  	} 
	
	
	
	}
	else{
		Iterator <id> ddd=id.iterator(); 
		while(ddd.hasNext()&&noencontrado) {
			a=ddd.next();
		if(a.getToken() .equals(token)&&!a.getTipo().equals("")) {
			noencontrado=false;
		} }
	id i;
	Iterator <funcion> d=fun.iterator(); 
	funcion auxx;
	while(d.hasNext()&&noencontrado) {
		
		auxx=d.next();
 
	if(auxx.getToken().equals(aux)) { 
		Iterator <id> ko=auxx.getIds().iterator();
		while(ko.hasNext()&&noencontrado) { 
			i=ko.next();
		if(i.token.equals(token)) {noencontrado=false;
		a=i;
		
		}}
		ko=auxx.getParametros().iterator();
		while(ko.hasNext()&&noencontrado) { 
			i=ko.next();
		if(i.token.equals(token)) {noencontrado=false;
		a=i; }} } } }  
	if(noencontrado) {a=new id("","");
	a.setTipo("unknown");
	}
	return a;
}
/********************************************************/
public id buscartotal(String token) {//PARA BUSCAR UN TOKEN USAMOS EN FUN Y TABLA
	id sol=new id("","");
	Iterator <id> ddd=id.iterator(); 
	boolean noencontrado=true;
	while(ddd.hasNext()&&noencontrado) { 
		sol=ddd.next();
	if(sol.getToken() .equals(token)) {
		noencontrado=false; }}
id i;
Iterator <funcion> d=fun.iterator(); 
funcion auxx;
while(d.hasNext()&&noencontrado) { 
	auxx=d.next(); 
	Iterator <id> ko=auxx.getIds().iterator();
	while(ko.hasNext()&&noencontrado) { 
		i=ko.next();
	if(i.token.equals(token)) {noencontrado=false;
	sol=i; 
	}}
	ko=auxx.getParametros().iterator();
	while(ko.hasNext()&&noencontrado) { 
		i=ko.next();
	if(i.token.equals(token)) {noencontrado=false;
	sol=i; 
	}}}
	return sol;
}
/********************************************************/
public void añadirfor( ) {
	//añadimos las anteriore 
	Iterator <LinkedList<id>> ko=idfor.iterator(); 
	if(idfor.size()==0) {
		idfor.addLast(new LinkedList<id>());
	}
	else {
		 LinkedList<id>nueva=new LinkedList<id>();
		Iterator<LinkedList<id>> a=idfor.iterator();
		LinkedList<id> pi;
	while(a.hasNext()) {  
		pi=a.next();
		Iterator < id> c=pi.iterator();
		while(c.hasNext()) {  
id elem=c.next();
nueva.add(elem);
		}
	}idfor.addLast(nueva);}
	 } 
/********************************************************/
public void borrarfor( ) {
	idfor.remove(idfor.getLast());
	Iterator<LinkedList<id>> a=idfor.iterator();
	LinkedList<id> pi;
while(a.hasNext()) { 
	System.out.println("semve************************************");
	pi=a.next();
	Iterator < id> c=pi.iterator();
	while(c.hasNext()) {  


	}
}
	
}
/********************************************************/
public void meterenfor(id a) { 
		 idfor.getLast().addLast(a);   
		 }
/********************************************************/
public id buscarfor(String token) {
	id y=new id ("","");
	id aux;
	boolean encontrado=true;
	Iterator < id> c=idfor.getLast().iterator();
	while(c.hasNext()) {  
		aux=c.next();
		if(aux.getToken().equals(token)) {
			encontrado=false;
			y=aux;
			
		}
	}
	return y;
}
/**************************/
 
	
} 
