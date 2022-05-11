package it.polito.tdp.metroparis.model;

import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.event.ConnectedComponentTraversalEvent;
import org.jgrapht.event.EdgeTraversalEvent;
import org.jgrapht.event.TraversalListener;
import org.jgrapht.event.VertexTraversalEvent;
import org.jgrapht.graph.DefaultEdge;

public class RegistraAlberoDiVisita implements TraversalListener<Fermata, DefaultEdge> {
	
	private Graph<Fermata, DefaultEdge> grafo;
	private Map<Fermata, Fermata> alberoInverso;

	public RegistraAlberoDiVisita(Graph<Fermata, DefaultEdge> grafo, Map<Fermata, Fermata> alberoInverso) {
		super();
		this.grafo = grafo;
		this.alberoInverso = alberoInverso;
	}

	@Override
	public void connectedComponentFinished(ConnectedComponentTraversalEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void connectedComponentStarted(ConnectedComponentTraversalEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void edgeTraversed(EdgeTraversalEvent<DefaultEdge> e) {
		
		/*
		 * Trovo il source e il target del ramo,
		 * quindi devo controllare se ho gia salvato i
		 * vertici
		 */
		
		Fermata source = grafo.getEdgeSource(e.getEdge());
		Fermata target = grafo.getEdgeTarget(e.getEdge());
	
		if(!alberoInverso.containsKey(target)) {
			alberoInverso.put(target, source);
			System.out.println(target+" si raggiunge da "+source);
		} else if(!alberoInverso.containsKey(source)) {
			alberoInverso.put(source, target);
			System.out.println(source+" si raggiunge da "+target);

		}
	}

	@Override
	public void vertexTraversed(VertexTraversalEvent<Fermata> e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void vertexFinished(VertexTraversalEvent<Fermata> e) {
		// TODO Auto-generated method stub
		
	}

}
