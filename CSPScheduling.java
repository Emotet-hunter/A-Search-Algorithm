import java.io.*;
import java.util.ArrayList;


public class CSPScheduling {
	
	public static ArrayList<String[]> getTxt (String ruta) throws IOException {
		String linea;
		ArrayList <String[]> a = new  ArrayList<String[]>();
		
		File archivo = new File (ruta);
		FileReader fr = new FileReader(archivo);
		
		BufferedReader br = new BufferedReader (fr);
		
		while ((linea = br.readLine())!=null) {
			String [] array = linea.split(" ");
			a.add(array);
			
		}
		return a;
		
	}
	
	public static ArrayList<ArrayList<Integer>> setList (ArrayList <String[]> arr){
		ArrayList<ArrayList<Integer>> list = new ArrayList<ArrayList<Integer>>();

		boolean terminado= false;
		
		for (int i=0; i<arr.size(); i++) {
			if(terminado) {
				break;
			}
			ArrayList<Integer> list2 = new  ArrayList<Integer>();
			list.add(list2);
			
			for (int j=0; j< arr.get(i).length; j++) {
				if(terminado) {
					break;
				}
				if (isNumeric(arr.get(i)[j])) {
					Integer x = Integer.parseInt(arr.get(i)[j]);
					list2.add(x);
					
				}
				
				if(arr.get(i)[j].contains("--")) {
					list2.add(0);
				}
				
				if (arr.get(i)[j].contains("C")) {
					break;
				}
				if (arr.get(i)[j].contains("B")) {
					Integer x = Integer.parseInt(arr.get(i)[j+1].substring(1,2));
					Integer y = Integer.parseInt(arr.get(i)[j+2].substring(0,1));
					list2.add(x);
					list2.add(y);
					terminado=true;
				}
			}
		
		}
		list.remove(0);
		return list;
		
	}
	
	public static ArrayList<ArrayList<Integer>> setColegio (ArrayList <String[]> arr){
		ArrayList<ArrayList<Integer>> cole = new  ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> miaux = new ArrayList<Integer>();// [0] para nºcole y [1] para parada donde esta el cole	 
		int numcolegios=0;
		miaux.add(0);
		miaux.add(1);
		cole.add(miaux);
		cole.add(miaux);
		int num_colegio=0;
		int num_parada=0;
		for (int i=0; i<arr.size(); i++) {
			for (int j=0; j<arr.get(i).length; j++) {		
				if(arr.get(i)[j].contains("C") && arr.get(i)[j].contains(":")) {
					num_colegio=Integer.parseInt(arr.get(i)[j].substring(1,2));
					num_parada=Integer.parseInt(arr.get(i)[j+1].substring(1,2));
					miaux.set(0,num_colegio);
					miaux.set(1,num_parada);
					//System.out.println(miaux.get(0)+" "+miaux.get(1));
					
					cole.get(numcolegios).set(0,miaux.get(0));
					cole.get(numcolegios).set(1,miaux.get(1));
					numcolegios++;
					
					System.out.println("cole= "+cole.toString());
					
				}
				
			}
		}
	    
		System.out.println("cole= "+cole.toString());
		return cole;
	}
	
	public static Integer[][] setEsperando_bus (ArrayList <String[]> arr){
		Integer[][] esperando = new Integer[tam_esperando(arr)][tam_esperando(arr)];
		Integer [] arrayAux= new Integer[3];
		int numFila=0;
		int x,z= 0;
		for (int i=0; i<arr.size(); i++) {
			for (int j=0; j<arr.get(i).length; j++) {
				if(arr.get(i)[j].contains("P") && arr.get(i)[j].contains(":")) {			
					arrayAux[0]=Integer.parseInt(arr.get(i)[j].substring(1,2));
					arrayAux[1]=Integer.parseInt(arr.get(i)[j+1]);
					arrayAux[2]=Integer.parseInt(arr.get(i)[j+2].substring(1,2));
					esperando[numFila][0] =arrayAux[0];	
					esperando[numFila][1] =arrayAux[1];	
					esperando[numFila][2] =arrayAux[2];	
					numFila++;
					
				}
				if(arr.get(i)[j].contains("C") && arr.get(i)[j].contains(",")) {
					//arrayAux[0]=Integer.parseInt(arr.get(i)[j-2].substring(1,2));
					arrayAux[1]=Integer.parseInt(arr.get(i)[j+1]);
					arrayAux[2]=Integer.parseInt(arr.get(i)[j+2].substring(1,2));
					esperando[numFila][0] =arrayAux[0];	
					esperando[numFila][1] =arrayAux[1];	
					esperando[numFila][2] =arrayAux[2];	
					numFila++;
					
				}
				
			}
		}
		return esperando;
	}
	
	 public static boolean isNumeric(String cadena) {
	
	        boolean resultado;
	
	        try {
	            Integer.parseInt(cadena);
	            resultado = true;
	        } catch (NumberFormatException excepcion) {
	            resultado = false;
	        }
	
	        return resultado;
	    }

	 public static int tam_esperando(ArrayList <String[]> arr) {
		int tam=0;
		 for (int i=0; i<arr.size(); i++) {
				for (int j=0; j<arr.get(i).length; j++) {
					if((arr.get(i)[j].contains("P") && arr.get(i)[j].contains(":"))||(arr.get(i)[j].contains("C") && arr.get(i)[j].contains(","))) {			
						tam++;
						
					}
					
					
				}
			}
	
	        return tam;
	    }
	 
	 
	 public static String busqueda(int [][] grafo,ArrayList<Integer> lista_abierta,ArrayList<Integer> lista_cerrada,String sol, ArrayList<ArrayList<Integer>> pesos, ArrayList <Integer> subidos_bus, Integer[][] esperando_bus, ArrayList<ArrayList<Integer>> colegios) {
		 	String solucion=sol;
		 	Integer aux=0;
		 	int anterior = 0;
			boolean exito=false;
			ArrayList<Integer> abierta = lista_abierta;
			ArrayList<Integer> cerrada = lista_cerrada;
			int num_paradas= pesos.size();
			int [][] sucesores = grafo;
			
		    
		    for( ;!exito && abierta.size()!=0;) {
		    	System.out.println("");
		    	System.out.println("*******************");
		    	System.out.println("NUEVA ITERACION DEL ALOGRITMO ");
		   	    anterior=aux;
		    	aux=abierta.get(0);
		    	System.out.println("Sacando P"+aux+" de la lista abierta y añadiendolo a la lista cerrada");
		    	cerrada.add(aux);
			    abierta.remove(0);
			    System.out.println(abierta.size()+"  "+ aux);
			    
			    if(esperando_bus.length==0 && subidos_bus.size()==0) {
			    	exito=true;
			    }else {	 //Aqui va lo gordo
			    	
			    	//BAJAR DEL BUS
			    	for(int m=0; m<colegios.size();m++) {
				    		if(colegios.get(m).get(1) == aux) { //Hay colegio en la parada actual
				    			System.out.println("Hemos llegado a una parada con el colegio "+colegios.get(m).get(0));
				    			for(int b=0; b<subidos_bus.size();b++) {
				    				if(subidos_bus.get(b) == colegios.get(m).get(0)) {
				    					System.out.println("Autobus: "+subidos_bus.toString());
				    					System.out.println("SE BAJA UN NIÑO DEL BUS");
				    					subidos_bus.set(b,0);
				    					System.out.println("Autobus: "+subidos_bus.toString());
				    				}
				    			}
				    		}
			    	}
			    	
			    	//SUBIR AL BUS
			    	for(int m=0; m<esperando_bus.length;m++) {
			    		if(esperando_bus[m][0]==aux && esperando_bus[m][1] > 0 ) { //Niños esperando en esta parada
			    			System.out.println("NIÑO ENCONTRADO EN ESTA PARADA");
			    			boolean hueco=false;
		    			
				    			for(int n=0;n<subidos_bus.size();n++) {  //Le buscamos un hueco en el bus
				    				if(subidos_bus.get(n)==0) {  //Hueco encontrado, le subimos
				    					hueco=true;
				    					System.out.println("Se sube un niño al autobus con destino el colegio "+esperando_bus[m][2]);
				    					subidos_bus.set(n, esperando_bus[m][2]); //Niño Subido
				    					esperando_bus[m][1]=esperando_bus[m][1]-1;
				    					System.out.println("El autobus esta asi: "+subidos_bus.toString());
				    					if(esperando_bus[m][1]>0) {
				    						m--;
				    					}
				    					break;
				    				}
				    			}
				    			if(!hueco) {
				    				System.out.println("NO HAY HUECO PARA EL NIÑO, SE QUEDA ESPERANDO");
				    			}
			    			
			    		}
			    	}
			    	
			    	
			    	
			    	for(int j=0; j<num_paradas;j++) {
			    		
			    		if((pesos.get(aux-1).get(j)>0) && (sucesores[j][aux-1]==0)) { //Sucesores no antecesores
			    			
			    			
			    			
			    			//Comprobamos si estaba ya  en CERRADA
			    			if( cerrada.contains(j+1)) {
			    				System.out.println("sucesor que estaba ya en cerrada --> "+(j+1));
			    				
			    			}
			    			
			    			//Comprobamos si estaba ya en ABIERTA
			    			if(abierta.contains(j+1)) {
			    				int min=10000;
			    				for(int v=0;v<sucesores.length;v++) {
			    					if(sucesores[v][aux-1] < min && sucesores[v][aux-1] !=0) {
			    						min = sucesores[v][aux-1];
			    					}
			    				}
			    				int min2=10000;
			    				for(int v=0;v<sucesores.length;v++) {
			    					if(sucesores[v][j] < min2 && sucesores[v][j] !=0) {
			    						min2 = sucesores[v][j];
			    					}
			    				}
			    				if(min2 >= (min+pesos.get(aux-1).get(j))) {
			    				   sucesores[aux-1][j]=min+pesos.get(aux-1).get(j);
			    				   System.out.println("peso modificado ("+min+") por ser sucesor que estaba ya en abierta --> "+(j+1));
			    				}
			    				
			    				System.out.println((aux-1)+" "+(j+1)+" "+min+" "+min2);
			    				
			    				/*for(int x=0;x<num_paradas ;x++) { 
			    					if(sucesores[x][j] < min ) {
			    						min = sucesores[x][j];			
			    						System.out.println("peso modificado por ser sucesor que estaba ya en abierta --> "+(j+1)+" "+x+" "+min);
			    					}
			    				}*/
			    				
			    				
			    			}
			    			
			    			//Comprobamos si estaban ya en el GRAFO
			    			int checker=0;
			    			for(int t=0;t<num_paradas ;t++) {
			    				checker += sucesores[j][t];
			    				checker += sucesores[t][j];
			    			}
			    			if(checker==0) {
			    				sucesores[aux-1][j]=pesos.get(aux-1).get(j);
				    			abierta.add(j+1);
				    			System.out.println("Añadiendo la parada P"+(j+1)+" a la lista abierta");
				    			System.out.println("y como sucesora de P"+aux);
			    			}
			    			
			    			
			    		}
			    	}
			    }
			    for(int p=0; p<sucesores.length;p++) {
			    	for(int q=0; q<sucesores[p].length;q++) {
			    		System.out.print(sucesores[p][q]+"\t");
			    	}
			    	System.out.println();
			    }
			    abierta=ordenar_lista(abierta,sucesores);
			    return busqueda(sucesores,abierta,cerrada,solucion, pesos, subidos_bus, esperando_bus, colegios );
		    }
	   
		    return solucion;
	  	
		    }
	 
	 public static String iniciar_busqueda(int pos_inicial, ArrayList<ArrayList<Integer>> pesos, ArrayList <Integer> subidos_bus, Integer[][] esperando_bus, ArrayList<ArrayList<Integer>> colegios) {
		 	String solucion="";
			ArrayList<Integer> abierta = new  ArrayList<Integer>();
			ArrayList<Integer> cerrada = new  ArrayList<Integer>();
			int num_paradas= pesos.size();
			int [][] sucesores = new int[num_paradas][num_paradas];
		    abierta.add(pos_inicial);
		    System.out.println("busqueda iniciada");
		    return busqueda(sucesores,abierta,cerrada,solucion, pesos, subidos_bus, esperando_bus, colegios );
		    }
	 
	 public static ArrayList<Integer> ordenar_lista(ArrayList<Integer> lista, int [][] grafo) {
		 System.out.println("ORDENANDO LISTA de tam= "+lista.size());
		 ArrayList<Integer> lista_ordenada = new ArrayList<Integer>();
		 int [] menores = new int[grafo.length];
		 int mejor=0;
		 while(lista.size() != 0) {	 
			 int menorAux=10000;
			 for(int h=0; h<grafo.length ;h++) {
				 menorAux=10000;		 
				 if(lista.contains(h+1)) {			 
					 for(int k=0;k<grafo.length;k++) {	 
						 if(grafo[k][h]< menorAux && grafo[k][h] !=0) {
							 menorAux=grafo[k][h];
						 }
					
					 }
				 }
			 menores[h] = menorAux;
			 }
			 menorAux=10000;
			 for(int h=0; h< menores.length; h++) {
				 if(menores[h] < menorAux) {
					 menorAux= menores[h];
					 mejor=h+1;
				 }
			 }	
			// System.out.println("El mejor es "+mejor+" siendo"+menorAux);
		 
		/* int candidato = actual;
		 int valorcandidato = 10000;
		 while(lista.size() != 0){
			 valorcandidato = 10000;
			 //System.out.println("holi"+lista.size());
			 for(int i=0; i < grafo.length  ;i++){
				//System.out.println("holi"+grafo[actual-1][i]);
			 	if(grafo[actual-1][i]!=0 && (grafo[actual-1][i] < valorcandidato) && lista.contains(i+1)){ 
			 		System.out.println("adios"+lista.size()+" y es "+(i+1));
			 		candidato= i+1;
			 		valorcandidato= grafo[actual-1][i];
			 	}
			 	
		 	}
	 	}*/
			 if(lista.contains(mejor)){
				 System.out.println("ordenado el "+mejor);
				 lista_ordenada.add(mejor);
				 lista.remove(lista.indexOf(mejor));
			 }
		 
		 	}
		 System.out.println("Lista ABIERTA ordenada: "+lista_ordenada.toString());
		 return lista_ordenada;
	 }
	
	 
	 public static void main (String []args) throws IOException {
		//Bus
		int capacidad_bus = 0;
		int posicion_actual = 0;		
		ArrayList <Integer> subidos_bus = new  ArrayList<Integer>();
		
		
		ArrayList <String[]> resultad = new  ArrayList<String[]>();

		resultad = getTxt("problema2.prob");
		
		//Los que faltan por recoger
		Integer[][] esperando_bus = new Integer[tam_esperando(resultad)][tam_esperando(resultad)];
				
		ArrayList<ArrayList<Integer>> peso = new  ArrayList<ArrayList<Integer>>();
		peso = setList(resultad);
		posicion_actual = peso.get(peso.size()-1).get(0);
		capacidad_bus = peso.get(peso.size()-1).get(1);
		
		for(int i=0;i<capacidad_bus;i++) { //Inicializar todo el bus a 0
			subidos_bus.add(0);
		}
		
		peso.remove(peso.size()-1);
		peso.remove(peso.size()-1);
		peso.remove(peso.size()-1);
		
		
		
		
		ArrayList<ArrayList<Integer>> coles = new ArrayList<ArrayList<Integer>>();
		coles = setColegio(resultad);
		esperando_bus = setEsperando_bus(resultad);
		
		System.out.println("COSTES:");
		for (int i=0; i<peso.size(); i++) {
			for (int j=0; j<peso.get(i).size(); j++) {
				System.out.print(peso.get(i).get(j));
				System.out.print("\t");
			}
			System.out.println();
		}
		System.out.println();
		System.out.print("Colegios: \t");	
		for (int i=0; i<coles.size(); i++) {
			for(int j=0; j<coles.get(i).size();j++) {
				if(j==0) {
					System.out.print("Colegio nº "+coles.get(i).get(j));
				}else if(j==1) {
					System.out.print(" esta en la parada "+coles.get(i).get(j));
					System.out.println();
				}
			}
			
			
			
		}
		System.out.println();
		System.out.println("NIÑOS ESPERANDO:");
		for (int i=0; i<esperando_bus.length; i++) {
		
			System.out.print("En la parada "+esperando_bus[i][0]+" hay esperando "+esperando_bus[i][1]+" niños para ir al colegio "+esperando_bus[i][2]);
			System.out.println();
		}
		
		
		System.out.println();
		System.out.println("Posicion actual: "+posicion_actual);
		System.out.println("Capacidad : "+capacidad_bus);
		String yaveras = iniciar_busqueda(posicion_actual, peso, subidos_bus, esperando_bus,coles);
		System.out.println("FIN");
		}
	}