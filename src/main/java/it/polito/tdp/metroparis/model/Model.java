package it.polito.tdp.metroparis.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.traverse.BreadthFirstIterator;
import org.jgrapht.traverse.GraphIterator;

import it.polito.tdp.metroparis.db.MetroDAO;

public class Model {
	
	private Graph<Fermata, DefaultEdge> grafo;
	
	public void creaGrafo() {
		/*
		 * Legge le informazioni del db e popola
		 * il grafo stesso
		 */
		
		//Istanziamo il grafo
		grafo = new SimpleDirectedGraph<Fermata, DefaultEdge>(DefaultEdge.class);
		
		//Istanziamo il dao
		MetroDAO dao = new MetroDAO();
		
		List<Fermata> fermate = dao.getAllFermate();
		Map<Integer, Fermata> fermateMappa = new HashMap<Integer, Fermata>();
		
		for(Fermata f : fermate) {
			fermateMappa.put(f.getIdFermata(), f);
		}
		
		//Aggiungiamo tutti i vertici al grafo
		Graphs.addAllVertices(grafo, fermate);
		//Le fermate ora esistono ma sono isolate
		//Dobbiamo creare gli archi
		
		//Creiamo gli archi
		
		//METODO 1
		/*
		for(Fermata partenza : fermate) {
			for(Fermata arrivo : fermate) {
				//Esiste almeno una connessione tra due stazioni?
				if(dao.isFermateConnesse(partenza, arrivo)) {
					grafo.addEdge(partenza, arrivo);
				}
			}
		}*/
		
		//METODO 2a: dato ciascun vertice, trova i vertici ad esso adiacenti
		/*
		for(Fermata partenza : fermate) {
			List<Integer> idConnesse = dao.getIdFermateConnesse(partenza);
			
			for(Integer id : idConnesse) {
				Fermata arrivo = null;
				for(Fermata f : fermate) {
					if(f.getIdFermata()==id) {
						arrivo = f;
						break;
					}
				}
				grafo.addEdge(partenza, arrivo);
			}
		}*/ 
		
		//METODO 2b
		/*
		for(Fermata partenza : fermate) {
			for(Fermata arrivo : dao.getFermateConnesse(partenza)) {
				grafo.addEdge(partenza, arrivo);
			}
		}*/
		
		//METODO 2c
		/*
		for(Fermata partenza : fermate) {
			for(Integer id : dao.getIdFermateConnesse(partenza)) {
				Fermata arrivo = fermateMappa.get(id);
				grafo.addEdge(partenza, arrivo);
			}
		}*/
		
		//METODO 3: delegare tutto il lavoro al db
		for(CoppiaId coppia : dao.getAllFermateConnesse()) {
			grafo.addEdge(fermateMappa.get(coppia.getIdPartenza()), 
					fermateMappa.get(coppia.getIdArrivo()));
		}
		
		
		System.out.println(this.grafo) ;
		System.out.println("Vertici = " + this.grafo.vertexSet().size());
		System.out.println("Archi   = " + this.grafo.edgeSet().size());
	
		visitaGrafo(fermate.get(0));
	}
	
	
	public void visitaGrafo(Fermata partenza) {
		GraphIterator<Fermata, DefaultEdge> visita =
				new BreadthFirstIterator<>(grafo, partenza);
		//oppure DepthFirstIterator<>(grafo, partenza);		
		while(visita.hasNext()) {
			Fermata f = visita.next();
			System.out.println(f);
		}
	
	}
}
