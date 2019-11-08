package vrp.program;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import vrp.model.*;


public class VRPProgram {

	public static int CAR_LIMIT ;
	private static int[][] savings;
	public static int[][] distances;
	private static Node[] nodes;
	private static String[] adds;
	private static ArrayList<Route> routes;
	private static int nCount;
	private static int[] amounts;
	
	
	
	
        // read file cost
        public static void readData(String file) throws FileNotFoundException{
            File fp = new File(file);
            Scanner sc = new Scanner(fp);
            sc.hasNext();
            nCount = sc.nextInt();
            distances = new int[nCount][nCount];
            for(int i = 0 ; i<nCount ; i++){
                for(int j = 0 ; j< nCount ; j++ ){
                    sc.hasNext();
                    int dis = sc.nextInt();
                    distances[i][j] = dis;
                    System.out.print(dis);
                }
                System.out.println("");
            }
            
            
        }

	
	// readfile weight
        public static void readWeight(String file) throws FileNotFoundException{
            File fp = new File(file);
            Scanner l = new Scanner(fp);
            l.hasNext();
            CAR_LIMIT = l.nextInt();
            amounts = new int[nCount];
            nodes = new Node[nCount];
            for(int i = 0 ; i< nCount ; i++){
                l.hasNext();
                amounts[i] = l.nextInt();
                Node n = new Node(i);
		n.amount = amounts[i];

		nodes[i] = n;
                System.out.println(n.amount);
            }
            
        }
	public static String clarkWright(){
		routes = new ArrayList<Route>();
		
		//I create N nodes. Each node will be inserted into a route.
		//each route will contain 2 edges - from the depo to the edge and back
		for(int i=1;i<nCount;i++){
			
			Node n = nodes[i];
			
				//creating the two edges
				Edge e  = new Edge(nodes[0],n,distances[0][n.index]);
				Edge e2 = new Edge(n,nodes[0],distances[0][n.index]);
			
				Route r = new Route(nCount);
				//40 omezeni kamionu
				r.allowed = CAR_LIMIT;
				r.add(e);
				r.add(e2);
				r.actual += n.amount;
				
				routes.add(r);	
		}
		
		
		Printer.printRoutes(routes);
		// tinh toan saving va sap xep giam dan
		ArrayList<Saving> sList = computeSaving(distances, nCount, savings,nodes);
	
		Collections.sort(sList);
		
		//
		while(!sList.isEmpty()){
			Saving actualS = sList.get(0);
			
			Node n1 = actualS.from;
			Node n2 = actualS.to;
			
			Route r1 = n1.route;
			Route r2 = n2.route;
			
			int from = n1.index;
			int to = n2.index;
			
			
			if(actualS.val>0 && r1.actual+r2.actual<r1.allowed && !r1.equals(r2)){
				
				//moznozt jedna z uzlu do kteryho se de se de do cile
				
				Edge outgoingR2 = r2.outEdges[to];
				Edge incommingR1 = r1.inEdges[from];
				
				
				if(outgoingR2!=null && incommingR1 != null){
					boolean succ = r1.merge(r2, new Edge(n1,n2,distances[n1.index][n2.index]));
					if(succ){
						routes.remove(r2);
					}
				}else{
					System.out.println("Problem");
				}
				
			}
			
			sList.remove(0);
			Printer.printRoutes(routes);
			
		}
		StringBuilder sb = new StringBuilder();
		sb.append(routes.size() + "\r\n");
		
		
		
		return sb.toString();
	}
	
	
	public static ArrayList<Saving> computeSaving(int[][] dist,int n,int[][] sav,Node[] nodesField){
		sav = new int[n][n];
		ArrayList<Saving> sList = new ArrayList<Saving>();
		for(int i=1;i<n;i++){
			for(int j=i+1;j<n;j++){
				sav[i][j] = dist[0][i] + dist[j][0] - dist[i][j];
				Node n1 = nodesField[i];
				Node n2 = nodesField[j];
				Saving s = new Saving(sav[i][j],n1, n2);
				sList.add(s);
			}
		}
		return sList;		
	}
}
