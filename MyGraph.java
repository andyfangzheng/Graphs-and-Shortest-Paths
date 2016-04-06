import java.util.*;

/**
 * Kanghui Liu, Fangzheng Sun
 * 05/24/2015
 *
 * A representation of a graph. Assumes that we do not have negative cost edges
 * in the graph.
 */
public class MyGraph implements Graph {
   
   private Map<Vertex, Set<Edge>> graph; //link vertex to its corrresponding edges.
   private Set<Edge>  edgesCollection;
   private List<Vertex> vertexCollection;
   
   //The constructor takes a collection of vertex and a collection of edge as parameters
   //and initializes the graph. The information of given vertexs and edges are stored.
   //It throws IllegalArgumentException if the collections or vertexs or edges are null, 
   //negative weight appears, there exist a edge that not links to or is not 
   //from the collection of vertex or two edges with the same source and destination
   //but different weights.
   public MyGraph(Collection<Vertex> v, Collection<Edge> e) {
      isEmpty(v, "there is no vertice in the collection");
      isEmpty(e, "there is no edge in the colletion");
      graph = new HashMap<Vertex, Set<Edge>>();
      edgesCollection = new HashSet<Edge>();
      vertexCollection = new ArrayList<Vertex>();
      //put vertex into graph, set it as key, associate new set of edge as value 
      for (Vertex vertex : v) {
         vertexCollection.add(vertex);
         graph.put(vertex, new HashSet<Edge>());   
      }
      for (Edge edge : e) {
         //throw negative weight
         if (edge.getWeight() < 0) {
            throw new IllegalArgumentException("edge should not have negative weight");
         }
         //throw exception if not exist
         if (!graph.containsKey(edge.getSource()) ||  
               !graph.containsKey(edge.getDestination())) {
            throw new IllegalArgumentException("edges' vertices don't exist "
                  + "in the vertice colletion ");
         }
         //throw away different weight for same edges, otherwise add it to the graph
         for (Edge checkEdge : e) {
            if (edge.getSource().equals(checkEdge.getSource()) &&
                  edge.getDestination().equals(checkEdge.getDestination()) &&
                  edge.getWeight() != checkEdge.getWeight()) {
               throw new IllegalArgumentException("Same edges but with different weight");
            }
            //add the edge to the collection and 
            //add the edge to the certain map 
            edgesCollection.add(edge);
            graph.get(edge.getSource()).add(edge);
         }      
      }
   
   }
   
   //This private method checks whether the vertex exist or not.
   //It throws NoSuchElementException if the vertex does not exist.
   private void hasVertex(Vertex v) {
      if (!graph.containsKey(v)) {
         throw new NoSuchElementException(v + "vertex doesn't exist");
      }
   }
   
   //This private method checks if the collection being passed is empty or not. 
   //It throws IllegalArgumentException if the collection is empty.
   private void isEmpty(Collection<?> object, String words) {
      if (object.isEmpty()) {
         throw new IllegalArgumentException(words);
      }
   }
   //This method return the collection of vertices of this graph.
   @Override
   public Collection<Vertex> vertices() {
      return vertexCollection;
   }
   
   //This method returns the collection of edges of this graph.
   @Override
   public Collection<Edge> edges() {
      return edgesCollection;
   }
   
   //This method returns a collection of vertices adjacent to a given vertex v.
   //It returns an empty collection if there are no adjacent vertices.
   //It throws an NoSuchElementException if if v does not exist.
   @Override
   public Collection<Vertex> adjacentVertices(Vertex v) {
      hasVertex(v);
      Set<Vertex> adjacentV = new HashSet<Vertex>();
      Collection<Edge> collect = graph.get(v);
      for ( Edge edge : collect) {
         adjacentV.add(edge.getDestination());
      }
      return adjacentV;
   }
   
   //This method tests whether vertex b is adjacent to vertex a. 
   //It returns cost of edge if there is a directed edge from a to b in the
   //graph, return -1 otherwise.
   //It throws an NoSuchElementException if if a or b does not exist.
   @Override
   public int edgeCost(Vertex a, Vertex b) {
      hasVertex(a);
      hasVertex(b);
      Collection<Edge> collect = graph.get(a);
      for ( Edge edge : collect) {
         if (edge.getDestination().equals(b)) {
            return edge.getWeight();
         }    
      }
      return -1;
   }
   
   //This method returns the shortest path from a to b in the graph, or null 
   //if there is no such path containing a and b Useing Dijkstra's algorithm.
   //It throws an NoSuchElementException if if a or b does not exist.
   public Path shortestPath(Vertex a, Vertex b) {
      hasVertex(a);
      hasVertex(b);
      //strange case: a is equal to b
      List<Vertex> copyV = new ArrayList<Vertex>(); 
      if (a.equals(b)) {
         copyV.add(a);
         return new Path(copyV, 0);
      }
      
      for (Vertex v : vertices()) {
    		copyV.add(new Vertex(v));
    	}
            
      Set<Vertex> knownV = new HashSet();//vertexs that have been checked
      PriorityQueue<Vertex> pq = new PriorityQueue<Vertex>();
      Vertex begin = new Vertex(a.getLabel(), 0, null);//the starting vertex, cost 0
      pq.add(begin);
      copyV.set(copyV.indexOf(a), begin);//reset the starting vertex
      Vertex end = null;//first set the end vertex to be null
      for ( int i = 0; i < vertices().size(); i++) {
         Vertex current = pq.remove();//set the current vertex as the first 
                                      //vertex in the priority queue
         if (!knownV.contains(current)) {
            knownV.add(current);
            //get the set of edge that is associate with the vertex
            Set<Edge> edgesAssociatedWithVertex = graph.get(current);
            for (Edge e : edgesAssociatedWithVertex) {
               Vertex desination = e.getDestination();
               int newCost = e.getWeight() + current.getCost();
               int destCost = copyV.get(copyV.indexOf(desination)).getCost();
               //compare the cost of two different paths, if the new cost is smaller
               //than the original one, set the the new cost to be the optimal one for now,
               if (newCost < destCost) {
                  Vertex temp = new Vertex (desination.getLabel(), newCost, current);
                  if(temp.equals(b)) {
                     end = temp;
                  }
                  copyV.set(copyV.indexOf(desination), temp);
                  pq.add(temp);
               }
            }
         }
      }
      //check the end vertex, if no such path, return null.
      if (end == null) {
         return null;
      }
      List<Vertex> result = new LinkedList<Vertex>();  // store result to return
      int cost = copyV.get(copyV.indexOf(end)).getCost();	
      result.add(end);
    	// find shortest path
      while(end.getPath() != null) {
         result.add(new Vertex(end.getPath()));
         end = end.getPath();
      }
      Collections.reverse(result);
      return new Path(result, cost);    
   }
}