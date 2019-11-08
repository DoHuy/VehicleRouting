package vrp.model;

import java.util.ArrayList;

public class Route {
	
	public int allowed;
	public int actual;
	public int totalCost;
	
	
	public int[] nodes;
	public Edge[] inEdges;
	public Edge[] outEdges;
	
	
	ArrayList<Edge> edges;
	
	public Route(int nodesNumber){
		edges = new ArrayList<Edge>();
		nodes = new int[nodesNumber];
		inEdges = new Edge[nodesNumber];
		outEdges = new Edge[nodesNumber];
	}
	
	public void add(Edge e){ // them 1 cung vao tuyen duong
		edges.add(e);
		
		outEdges[e.n1.index] = e;
		inEdges[e.n2.index] = e;
		
		e.n1.route = this;
		e.n2.route = this;
		
		totalCost+= e.val; // tinh tong qang duong cua route qua tung lan them edge
	}
	
	public void removeEdgeToNode(int index){
		Edge e = inEdges[index];
		outEdges[e.n1.index] = null;
		
		totalCost-= e.val;
		
		edges.remove(e);
		inEdges[index] = null;
	}
	
	public void removeEdgeFromNode(int index){
		Edge e = outEdges[index];
		inEdges[e.n2.index] = null;
		
		totalCost-=e.val;
		edges.remove(e);
		outEdges[index] = null;
	}
	
	public int predecessor(int nodeIndex){ // cung tiền nhiệm
		return inEdges[nodeIndex].n1.index;
	}
	
	
	public int successor(int nodeIndex){ // cung kế thừa 
		return outEdges[nodeIndex].n2.index;
	}
	
	public boolean merge(Route r2,Edge mergingEdge){

		int from = mergingEdge.n1.index; // 1
		int to = mergingEdge.n2.index;   // 2
		
		int predecessorI = this.predecessor(from); // 0
		int predecessorJ = r2.predecessor(to);     // 0
		
		int successorI = this.successor(from);  // 5
		int successorJ = r2.successor(to); //  0
		
		// một lựa chọn
                // cạnh kéo dài từ nút, mà trong Route đầu tiên di chuyển trở lại nút 0
                // trong con đường thứ hai là trái cửa hàng nút tiền nhiệm J = 0
		if(successorI == 0 && predecessorJ == 0){
			this.removeEdgeToNode(0);
			r2.removeEdgeFromNode(0);
			for(Edge e:r2.edges){
				this.add(e);
			}
			this.actual+= r2.actual;
			this.add(mergingEdge);
			return true;
		// sử dụng hai
                // nếu cạnh đi theo hướng ngược lại
                // nút và các tuyến đường sđầu tiên trong thứ hai sau chứng khoán
                // phải bật cạnh
		}else if(successorJ == 0 && predecessorI == 0){
			mergingEdge.reverse();
			this.removeEdgeFromNode(0);
			r2.removeEdgeToNode(0);
			for(Edge e:r2.edges){
				this.add(e);
			}
			this.actual+= r2.actual;
			this.add(mergingEdge);
			return true;
		}
		
		return false;
	}
}
