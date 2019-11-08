package vrp.program;

import vrp.model.*;

import java.util.ArrayList;

public class Printer {

	
	public static void printSaving(Saving s){
		int from = s.from.index;
		int to = s.to.index;
		System.out.println("Saving - From: " + from + " To: " + to + " Val: " + s.val);
	}
	
	
	
	
	public static void printRoute(Route r){
		System.out.print("Route: ");
		Edge edge = r.outEdges[0];
		
		System.out.print("(" + edge.n1.index + "->" + edge.n2.index + ")");
		
		do{
			edge = r.outEdges[edge.n2.index];
			System.out.print("(" + edge.n1.index + "->" + edge.n2.index + ")");
		}while(edge.n2.index!=0);
		
		
		System.out.print(" Amount: " + r.actual + " Cost: " + r.totalCost);
		
		System.out.println("");
	}
	

	/*
	 * @param r
	 * @param adds
	
	/**
	 * @param routes
	 */
	public static void printRoutes(ArrayList<Route> routes){
		int totalCost = 0;
		for(Route r:routes){
			printRoute(r);
			totalCost+= r.totalCost;
		}
		
		System.out.println("Total cost of the routes: " + totalCost);
	}
	
	
	
}
