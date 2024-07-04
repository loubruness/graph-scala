import graph._

def detectCycle[V](graph: Graph[V, DirectedEdge[V]] | Graph[V, ExtUndirectedEdge[V]] | Graph[V, WeightedEdge[V]]): Boolean = {

    // secondary function we use to establish the sets of potential routes that exist in the graph
  def visit(vertex: V, parent: Option[V], visited: Set[V]): Boolean = {

    //if the examined vertex is in one of the roads that have already been visited, there's a loop
    if (visited.contains(vertex)) true

    else {
      val newVisited = visited + vertex

      //continue for as long as there are neighbors left
      graph.neighbors(vertex).exists { neighbor =>
        // special precaution for undirected graphs, so we do not revisit the parent
        if (Some(neighbor) == parent) false
        else visit(neighbor, Some(vertex), newVisited)
      }
    }
  }

  graph.vertices.exists(vertex => visit(vertex, None, Set.empty[V]))
}



// TEST
val directedGraphCyclic = DirectedGraph(
  vertices = Set(1, 2, 3, 4),
  edges = Set(DirectedEdge(1, 2), DirectedEdge(2, 3), DirectedEdge(3, 4), DirectedEdge(4, 2))
)

val directedGraphAcyclic = DirectedGraph(
  vertices = Set(1, 2, 3, 4),
  edges = Set(DirectedEdge(1, 2), DirectedEdge(2, 3), DirectedEdge(3, 4))
)

println(detectCycle(directedGraphCyclic)) // true
println(detectCycle(directedGraphAcyclic)) // false


val undirectedGraphCyclic = ExtUndirectedGraph(
  vertices = Set(1, 2, 3, 4),
  edges = Set(ExtUndirectedEdge(1, 2), ExtUndirectedEdge(2, 3), ExtUndirectedEdge(3, 4), ExtUndirectedEdge(4, 1))
)

val undirectedGraphAcyclic = ExtUndirectedGraph(
  vertices = Set(1, 2, 3, 4),
  edges = Set(ExtUndirectedEdge(1, 2), ExtUndirectedEdge(2, 3), ExtUndirectedEdge(3, 4))
)

println(detectCycle(undirectedGraphCyclic)) // true
println(detectCycle(undirectedGraphAcyclic)) // false


val weightedGraphCyclic = WeightedGraph(
  vertices = Set(1, 2, 3, 4),
  edges = Set(WeightedEdge(1, 2, 1.0), WeightedEdge(2, 3, 2.0), WeightedEdge(3, 4, 3.0), WeightedEdge(4, 2, 4.0))
)

val weightedGraphAcyclic = WeightedGraph(
  vertices = Set(1, 2, 3, 4),
  edges = Set(WeightedEdge(1, 2, 1.0), WeightedEdge(2, 3, 2.0), WeightedEdge(3, 4, 3.0))
)

println(detectCycle(weightedGraphCyclic)) // true
println(detectCycle(weightedGraphAcyclic)) // false