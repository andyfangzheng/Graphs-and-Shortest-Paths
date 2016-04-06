/**
 * Kanghui Liu, Fangzheng Sun
 * 05/24/2015
 *
 * The class Vertex is a Representation of a graph vertex
 */
public class Vertex implements Comparable<Vertex> {
	private final String label;   // label attached to this vertex
	private final int cost; //the cost of the vertex
	private final Vertex tracingVertex;

	/**
	 * Construct a new vertex
	 * @param label the label attached to this vertex
	 */
	public Vertex(String label) {
      this(label,Integer.MAX_VALUE, null);
	}
	
	/**
	 * Construct a new vertex
	 * @param label the label attached to this vertex, type String
	 * @param cost the cost attached to this vertex, type int
	 * @param path the path pointing to this vertex, type Vertex
	 */
	public Vertex(String label, int cost, Vertex tracingVertex) {
		if(label == null)
			throw new IllegalArgumentException("null");
		this.label = label;
		this.cost = cost;
		this.tracingVertex = tracingVertex;
	}
	
	/**
	 * Construct a new vertex from another (deep copy)
	 * @param v a vertex object.
	 */
	public Vertex(Vertex v) {
		this(v.label, v.cost, v.tracingVertex);	
	}

	/**
	 * Get a vertex label
	 * @return the label attached to this vertex
	 */
	public String getLabel() {
		return label;
	}
	
	/**
	 * Gets cost associated with vertex object
	 * @return the cost associated with this object
	 */
	public int getCost() {
		return cost;
	}
	
	/**
	 * Gets path to this vertex object
	 * @return the path that points to this object
	 */
	public Vertex getPath() {
		return tracingVertex;
	}
	
	/**
	 * A string representation of this object
	 * @return the label attached to this vertex
	 */
	public String toString() {
		return label;
	}

	// hashes on label
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((label == null) ? 0 : label.hashCode());
		return result;
	}

	// compares labels
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Vertex other = (Vertex) obj;
		if (label == null) {
                    return other.label == null;
		} else {
		    return label.equals(other.label);
		}
	}
	
   //This method compares the distance of this vertex to other vertex. 
   //If this vertex costs more than other's, it returns positive value.
   //If cheaper, returns negative value. If equal, returns 0.
	public int compareTo(Vertex other) {
		return this.cost - other.cost;
	}
}