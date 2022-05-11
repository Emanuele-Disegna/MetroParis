package it.polito.tdp.metroparis.model;

import java.util.ArrayList;
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
	private List<Fermata> fermate;
	private MetroDAO dao;
	private Map<Integer, Fermata> fermateMappa;
	
	
	private void creaGrafo() {
		grafo = new SimpleDirectedGraph<Fermata, DefaultEdge>(DefaultEdge.class);
		
		Graphs.addAllVertices(grafo, fermate);
		
		for(CoppiaId coppia : dao.getAllFermateConnesse()) {
			grafo.addEdge(fermateMappa.get(coppia.getIdPartenza()), 
					fermateMappa.get(coppia.getIdArrivo()));
		}
		
		/*
		System.out.println(this.grafo) ;
		System.out.println("Vertici = " + this.grafo.vertexSet().size());
		System.out.println("Archi   = " + this.grafo.edgeSet().size());
		*/
		
	}
	
	
	private Map<Fermata, Fermata> visitaGrafo(Fermata partenza) {
		GraphIterator<Fermata, DefaultEdge> visita =
				new BreadthFirstIterator<>(grafo, partenza);
		//oppure DepthFirstIterator<>(grafo, partenza);		
		
		//Creo la mappa di visita
		Map<Fermata, Fermata> alberoInverso = new HashMap<Fermata, Fermata>();
		alberoInverso.put(partenza, null);//Chiudo la radice dell'albero
		
		
		//Prima di far partire l'iteratore aggiungo un Listener
		visita.addTraversalListener(new RegistraAlberoDiVisita(grafo, alberoInverso));
		
		while(visita.hasNext()) {
			Fermata f = visita.next();
		}
		
		return alberoInverso;
	}
	
	public List<Fermata> getAllFermate() {
		if(fermate==null) {
			dao = new MetroDAO();
			fermate = dao.getAllFermate();
			fermateMappa = new HashMap<Integer, Fermata>();
			for(Fermata f : fermate) {
				fermateMappa.put(f.getIdFermata(), f);
			}
		}
		return fermate;
	}
	
	public List<Fermata> calcolaPercorso(Fermata partenza, Fermata arrivo){
		//Creiamo il grafo come prima cosa
		creaGrafo();
		Map<Fermata, Fermata> alberoInverso = visitaGrafo(partenza);
		
		List<Fermata> percorso = new ArrayList<>();
		Fermata corrente = arrivo;
		
		while(corrente != null) {
			percorso.add(0, corrente);
			corrente = alberoInverso.get(corrente);
		}
		
		return percorso;
	}
	
	
}
